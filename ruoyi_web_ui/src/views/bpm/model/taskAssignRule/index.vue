<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="formList">
      <el-table-column
          type="index"
          label="序号"
          align="center"
          width="50"
          :index="indexMethod">
      </el-table-column>
      <el-table-column label="任务名" align="center" prop="taskDefinitionName"/>
      <el-table-column label="任务标识" align="center" prop="taskDefinitionKey"/>
      <el-table-column label="规则类型" align="center" prop="type">
        <template #default="scope">
          <dict-tag :options="bpm_task_assign_rule_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="规则范围" align="center" prop="optionName">
        <template v-slot="scope">
          <el-tag style="margin-right: 10Px" v-if="scope.row.selectMoreVos" :key="item.id" v-for="item in scope.row.selectMoreVos">
            {{ item.name }}
          </el-tag>
          <el-tag style="margin-right: 10Px" v-if="scope.row.optionName" >
            {{ scope.row.optionName }}
          </el-tag>
        </template>

      </el-table-column>
      <el-table-column v-if="queryParams.modelId" label="操作" align="center">
        <template #default="scope">
          <el-button
              link
              type="primary"
              @click="openForm(scope.row)"
              v-hasPermi="['bpm:task-assign-rule:update']"
          >
            修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />
    <!-- 修改弹窗  -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="taskAssignRuleFormRef" :model="form" :rules="rules" label-width="120px"  v-loading="editLoading" >
        <el-form-item label="任务名称" prop="taskDefinitionName">
          <el-input v-model="form.taskDefinitionName" placeholder="请输入流程名称" :disabled="true"/>
        </el-form-item>
        <el-form-item label="任务标识" prop="taskDefinitionKey">
          <el-input v-model="form.taskDefinitionKey" placeholder="请输入流程名称" :disabled="true"/>
        </el-form-item>
        <el-form-item label="规则类型" prop="type">
          <el-select v-model="form.type" clearable>
            <el-option
                v-for="dict in bpm_task_assign_rule_type"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.type == 10" label="指定角色" prop="roleIds">
          <select-more
              search-pld-text="请输入角色名称筛选"
              select-pld-text="请选择角色"
              v-model="form.roleIds"
              :edit-data="form.selectMoreVos"
              :showId="false"
              url="/system/role/simpleList"
              multiple>
          </select-more>
        </el-form-item>
        <el-form-item v-if="form.type == 20 || form.type == 21" label="指定部门" prop="deptIds" span="24">
          <el-tree-select
              v-model="form.deptIds"
              :data="deptOptions"
              value-key="id"
              node-key="id"
              multiple
              :render-after-expand="false"
              show-checkbox
              check-strictly
              check-on-click-node
          />
        </el-form-item>
        <el-form-item v-if="form.type == 22" label="指定岗位" prop="postIds" span="24">
          <select-more
              search-pld-text="请输入岗位名称筛选"
              select-pld-text="请选择岗位"
              v-model="form.postIds"
              :edit-data="form.selectMoreVos"
              :showId="false"
              url="/system/post/simpleList"
              multiple>
          </select-more>
        </el-form-item>
        <el-form-item
            v-if="form.type == 30 || form.type == 31 || form.type == 32"
            label="指定用户"
            prop="userIds"
            span="24"
        >
          <select-more
              search-pld-text="请输入用户名称筛选"
              select-pld-text="请选择用户"
              v-model="form.userIds"
              :edit-data="form.selectMoreVos"
              :showId="false"
              url="/system/user/simpleList"
              multiple>
          </select-more>
        </el-form-item>
        <el-form-item v-if="form.type == 40" label="指定用户组" prop="userGroupIds">
          <select-more
              search-pld-text="请输入用户组名称筛选"
              select-pld-text="请选择自定义用户组"
              v-model="form.userGroupIds"
              :edit-data="form.selectMoreVos"
              :showId="false"
              url="/bpm/group/simpleList"
              multiple>
          </select-more>

        </el-form-item>
        <el-form-item v-if="form.type == 50" label="指定脚本" prop="scripts">
          <el-select v-model="form.scripts" clearable>
            <el-option
                v-for="dict in bpm_task_assign_script"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<!--用户审批权限分配规则-->
<script setup name="TaskAssignRule">
import {getCurrentInstance, reactive, ref} from 'vue'
import {createTaskAssignRule, getTaskAssignRuleList,updateTaskAssignRule} from '@/api/bpm/taskAssignRule'
import {deptTreeSelect} from "@/api/system/user";
import SelectMore from '@/components/SelectMore/index.vue'

const {query} = useRoute() // 查询参数
const loading = ref(false) // 列表的加载中
const {proxy} = getCurrentInstance();
const {
  bpm_task_assign_rule_type,
  bpm_task_assign_script
} = proxy.useDict('bpm_task_assign_rule_type', 'bpm_task_assign_script');
const total = ref(0);
//流程弹出
const open = ref(false);
const title = ref("");
const formList = ref([]) // 列表的数据
const editLoading = ref(false);// 判断是编辑页需要先禁用选择框，等回填有值后再把禁用去掉


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    modelId: query.modelId, // 流程模型的编号。如果 modelId 非空，则用于流程模型的查看与配置
    processDefinitionId: query.processDefinitionId // 流程定义的编号。如果 processDefinitionId 非空，则用于流程定义的查看，不支持配置
  },
  rules: {
    type: [{required: true, message: '规则类型不能为空', trigger: 'change'}],
    roleIds: [{required: true, message: '指定角色不能为空', trigger: 'change'}],
    deptIds: [{required: true, message: '指定部门不能为空', trigger: 'change'}],
    postIds: [{required: true, message: '指定岗位不能为空', trigger: 'change'}],
    userIds: [{required: true, message: '指定用户不能为空', trigger: 'change'}],
    userGroupIds: [{required: true, message: '指定用户组不能为空', trigger: 'change'}],
    scripts: [{required: true, message: '指定脚本不能为空', trigger: 'change'}]
  }
});

const {queryParams, form, rules} = toRefs(data);
const deptOptions = ref([]) // 部门树
/** 列表序号 */
function indexMethod(index) {
  return ((queryParams.value.pageNum - 1) * queryParams.value.pageSize + index + 1)
}

/** 查询列表 */
function getList() {
  loading.value = true;
  getTaskAssignRuleList(queryParams.value).then(response => {
    formList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 表单重置
function reset() {
  form.value = {
    type: Number(undefined),
    modelId: '',
    options: [],
  };
  proxy.resetForm("taskAssignRuleFormRef");
}

/** 添加/修改操作 */
function openForm(row) {
  console.log(row);
  reset();
  form.value = {
    ...row,
    modelId: queryParams.value.modelId,
    options: [],
    roleIds: [],
    roleEditData:[],
    deptIds: [],
    postIds: [],
    postEditData:[],
    userIds: [],
    userEditData:[],
    userGroupIds: [],
    gropEditData:[],
    scripts: []
  }
  form.value = row
  if (row.type === 10) {
    const ids =[]
    form.value.selectMoreVos.forEach(item => {
      ids.push(item.id)
    })
    form.value.roleIds=ids
  } else if (row.type === 20 || row.type === 21) {
    //部门
    form.value.deptIds = row.options;
    const ids =[]
    form.value.selectMoreVos.forEach(item => {
      ids.push(item)
    })
    form.value.deptIds=ids
  } else if (row.type === 22) {
    //岗位
    form.value.postIds = row.options;
    const ids =[]
    form.value.selectMoreVos.forEach(item => {
      ids.push(item.id)

    })
    form.value.postIds=ids
  } else if (row.type === 30 || row.type === 31 || row.type === 32) {
    // 用户
    form.value.userIds = row.options;
    const ids =[]
    form.value.selectMoreVos.forEach(item => {
      ids.push(item.id)

    })
    form.value.userIds=ids

  } else if (row.type === 40) {
    form.value.userGroupIds = row.options;
    const ids =[]
    form.value.selectMoreVos.forEach(item => {
      ids.push(item.id)

    })
    form.value.userGroupIds=ids
  } else if (row.type === 50) {
    form.value.scripts = row.options;
  }
  open.value = true;
  title.value = "选择审批人";
}

/** 取消按钮 */
function cancel() {
  console.log("表单参数",form.value);
  open.value = false;
  reset();
}
/** 确定按钮 */
function submitForm() {
  console.log("提交表单",form.value);
  proxy.$refs["taskAssignRuleFormRef"].validate(valid => {
    if (valid) {
      // 将 roleIds 等选项赋值到 options 中
      if (form.value.type === 10) {
        form.value.optionIds = form.value.roleIds;
        console.log("角色",form.value.optionIds);
      } else if (form.value.type === 20 || form.value.type === 21) {
        form.value.optionIds = form.value.deptIds;
        console.log("部门",form.value.optionIds);
      } else if (form.value.type === 22) {
        form.value.optionIds = form.value.postIds;
        console.log("岗位",form.value.optionIds);
      } else if (form.value.type === 30 || form.value.type === 31 || form.value.type === 32) {
        form.value.optionIds = form.value.userIds;
        console.log("用户",form.value.optionIds);
      } else if (form.value.type === 40) {
        form.value.optionIds = form.value.userGroupIds;
        console.log("用户组",form.value.optionIds);
      } else if (form.value.type === 50) {
        form.value.options = form.value.scripts;
        console.log("自定义",form.value.options);
      }

      // 新增
      if (!form.value.id) {
        form.value.modelId = queryParams.value.modelId; // 模型编号
        createTaskAssignRule(form.value).then(response => {
          proxy.$modal.msgSuccess("添加成功");
          open.value = false;
          reset();
          getList();
        });
        // 修改
      } else {
        form.value.taskDefinitionKey = undefined; // 无法修改
        updateTaskAssignRule(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          reset();
          getList();
        });
      }
    }
  });

}

/** 查询部门下拉树结构 */
function getDeptTree() {
  deptTreeSelect().then(response => {
    deptOptions.value = response.data
})
}
/** 初始化 */

getList()

/** 部门树 */
getDeptTree()
</script>
<style>
.selectMoreCss {
  width: 100%;
}

</style>
