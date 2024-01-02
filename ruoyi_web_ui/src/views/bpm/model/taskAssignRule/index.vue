<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="formList" >
      <el-table-column
          type="index"
          label="序号"
          align="center"
          width="50"
          :index="indexMethod">
      </el-table-column>
      <el-table-column label="任务名" align="center" prop="taskDefinitionName" />
      <el-table-column label="任务标识" align="center" prop="taskDefinitionKey" />
      <el-table-column label="规则类型" align="center" prop="type">
        <template #default="scope">
          <dict-tag :options="bpm_task_assign_rule_type" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="规则范围" align="center" prop="options">
        <template #default="scope">
          <el-tag class="mr-5px" :key="option" v-for="option in scope.row.options">
            {{ getAssignRuleOptionName(scope.row.type, option) }}
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
  </div>
  <!-- 添加/修改弹窗 -->
<!--  <TaskAssignRuleForm ref="formRef" @success="getList" />-->
</template>
<!--用户审批权限分配规则-->
<script  setup name="TaskAssignRule" >
import {getCurrentInstance, reactive, ref} from 'vue'
import {listModel} from '@/api/bpm/model'
import {getTaskAssignRuleList} from '@/api/bpm/taskAssignRule'

const { query } = useRoute() // 查询参数
const loading = ref(false) // 列表的加载中
const {proxy} = getCurrentInstance();
const {bpm_task_assign_rule_type,bpm_model_form_type } = proxy.useDict('bpm_task_assign_rule_type','bpm_model_form_type');
const total = ref(0);



const formList = ref([]) // 列表的数据

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    modelId: query.modelId, // 流程模型的编号。如果 modelId 非空，则用于流程模型的查看与配置
    processDefinitionId: query.processDefinitionId // 流程定义的编号。如果 processDefinitionId 非空，则用于流程定义的查看，不支持配置
  },
});

const { queryParams, form } = toRefs(data);

const roleOptions = ref([]) // 角色列表
const deptOptions = ref([]) // 部门列表
const postOptions = ref([]) // 岗位列表
const userOptions = ref([]) // 用户列表
const userGroupOptions = ref([]) // 用户组列表


/** 序号 */
function indexMethod(index){
  return ((queryParams.value.pageNum-1) * queryParams.value.pageSize + index +1)
}

/** 查询列表 */

function getList() {
  console.log("加载数据");
  loading.value = true;
  getTaskAssignRuleList(queryParams.value).then(response => {
    formList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 翻译规则范围 */
const getAssignRuleOptionName = (type, option) => {
  if (type === 10) {
    for (const roleOption of roleOptions.value) {
      if (roleOption.id === option) {
        return roleOption.name
      }
    }
  } else if (type === 20 || type === 21) {
    for (const deptOption of deptOptions.value) {
      if (deptOption.id === option) {
        return deptOption.name
      }
    }
  } else if (type === 22) {
    for (const postOption of postOptions.value) {
      if (postOption.id === option) {
        return postOption.name
      }
    }
  } else if (type === 30 || type === 31 || type === 32) {
    for (const userOption of userOptions.value) {
      if (userOption.id === option) {
        return userOption.nickname
      }
    }
  } else if (type === 40) {
    for (const userGroupOption of userGroupOptions.value) {
      if (userGroupOption.id === option) {
        return userGroupOption.name
      }
    }
  } else if (type === 50) {
    option = option + '' // 转换成 string
    for (const dictData of taskAssignScriptDictDatas) {
      if (dictData.value === option) {
        return dictData.label
      }
    }
  }
  return '未知(' + option + ')'
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (row) => {
  formRef.value.open(queryParams.modelId, row)
}
/** 初始化 */

getList()
  // 获得角色列表
  // roleOptions.value = await RoleApi.getSimpleRoleList()
  // 获得部门列表
  // deptOptions.value = await DeptApi.getSimpleDeptList()
  // 获得岗位列表
  // postOptions.value = await PostApi.getSimplePostList()
  // 获得用户列表
  // userOptions.value = await UserApi.getSimpleUserList()
  // 获得用户组列表
  // userGroupOptions.value = await UserGroupApi.getSimpleUserGroupList()
</script>
