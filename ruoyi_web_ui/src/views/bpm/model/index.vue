<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程标识" prop="key">
        <el-input
            v-model="queryParams.key"
            placeholder="请输入流程标识"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程名称" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入流程名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程分类" prop="category">
        <el-select v-model="queryParams.category" placeholder="请选择流程分类" clearable>
          <el-option
              v-for="dict in bpm_model_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['bpm:model:add']"
        >新建流程
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="upload"
            @click="openImportForm"
            v-hasPermi="['bpm:model:import']"
        >导入流程
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="formList" >
      <el-table-column
          type="index"
          label="序号"
          align="center"
          width="50"
          :index="indexMethod">
      </el-table-column>
      <el-table-column label="流程标识" align="center" prop="key" width="200" />
      <el-table-column label="流程名称" align="center" prop="name" width="200"/>
      <el-table-column label="流程分类" align="center" prop="category" width="80"/>
      <el-table-column label="流程表单" align="center" prop="formType" width="200"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="部署的流程定义信息" align="center">
        <el-table-column
            label="流程版本"
            align="center"
            prop="processDefinition.version"
            width="100"
        >
          <template #default="scope">
              流程版本
          </template>
        </el-table-column>
        <el-table-column
            label="激活状态"
            align="center"
            prop="processDefinition.version"
            width="85"
        >
          <template #default="scope">
              激活状态
          </template>
        </el-table-column>
        <el-table-column label="部署时间" align="center" prop="deploymentTime" width="180">
          <template #default="scope">
    部署时间
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary"  @click="handleUpdate(scope.row)" v-hasPermi="['bpm:form:edit']">
            修改流程
          </el-button>
          <el-button link type="primary"  @click="handleDesign(scope.row)" v-hasPermi="['bpm:form:edit']">
            设计流程
          </el-button>
          <el-button link type="primary"  @click="handleAssignRule(scope.row)" v-hasPermi="['bpm:form:edit']">
            分配规则
          </el-button>
          <el-button link type="primary"  @click="handleDeploy(scope.row)" v-hasPermi="['bpm:form:edit']">
            发布流程
          </el-button>
          <el-button link type="primary"  @click="handleDefinitionList(scope.row)" v-hasPermi="['bpm:form:edit']">
            流程定义
          </el-button>
          <el-button link type="primary"  @click="handleDelete(scope.row)"
                     v-hasPermi="['bpm:form:remove']">删除
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

    <!-- 添加/修改流程  -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="bpmMpdelRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item  prop="key">
          <template #label>
            <el-popover placement="top" :width="300" trigger="hover" content="流程标识填写格式:以字母或下划线开头，后接任意字母、数字、中划线、下划线或句点">
              <template #reference>
                <el-icon><Warning/></el-icon>
              </template>
            </el-popover>
            流程标识
          </template>
          <template #default>
            <el-input v-model="form.key" placeholder="请输入流程标识" :readonly="form.id" />
          </template>
        </el-form-item>
        <el-form-item label="流程名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="流程描述" prop="description">
          <el-input type="textarea" :rows="3" v-model="form.description"></el-input>
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

<script setup name="bpmForm">
import router from "@/router";
import {getCurrentInstance, reactive, ref} from 'vue'
import {createModel,listModel} from '@/api/bpm/model'
const {proxy} = getCurrentInstance();
const {bpm_model_category} = proxy.useDict('bpm_model_category');
const formList = ref([]);
const loading = ref(true);
const showSearch = ref(true);
const total = ref(0);
//流程弹出
const open = ref(false);
const title = ref("");


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
  },
  rules: {
    name: [
      { required: true, message: "流程名称不能为空", trigger: "blur" }
    ],
    key: [{
      required: true,
      validator: (rule, value, callback) => {
        // 判断是否为空
        if(value == null) {
          callback(new Error('流程标识不能为空'));
          return;
        }
        // 判断格式是否正确
        const regex = /^[a-zA-Z_][a-zA-Z0-9\-_.]*$/;
        if (!regex.test(value)) {
          callback(new Error('流程标识格式不正确，需要以字母或下划线开头'));
        } else {
          callback(); // 如果都没有问题，最后一定要加上此句
        }
      },
      trigger: 'blur'
    }]
  }
});
const {queryParams,form,rules} = toRefs(data);

/** 序号 */
function indexMethod(index){
  return ((queryParams.value.pageNum-1) * queryParams.value.pageSize + index +1)
}

/** 查询工作流的自定义表单列表 */
function getList() {
  console.log("加载数据");
  loading.value = true;
  listModel(queryParams.value).then(response => {
    formList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 表单重置
function reset() {
  form.value = {
    name: null,
    key: null,

  };
  proxy.resetForm("bpmMpdelRef");
}
/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}
/** 新增按钮操作 */
function handleAdd() {
  console.log("新增流程按钮操作");
  reset();
  open.value = true;
  title.value = "新增流程";
}
/** 导入按钮操作 */
function openImportForm() {
  console.log("导入流程按钮");

}

/** 修改按钮操作 */
function handleUpdate(row) {
  console.log("修改按钮");
}
/** 设计流程操作 */
function handleDesign(row) {
  console.log("弹出设计流程页面");
  router.push({path: "/flowable/bpmModel/editor", query: {modelId: row.id}});
}
/** 分配规则操作 */
function handleAssignRule() {
  console.log("弹出分配规则页面");
}
/** 发布流程操作 */
function handleDeploy() {
  console.log("发布流程操作操作");
}
/** 流程定义操作操作 */
function handleDefinitionList() {
  console.log("流程定义操作");
}
/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id
  proxy.$modal.confirm('是否确认删除工作流自定义表单数据？').then(function () {
    // return delForm(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}



/** 提交按钮 */
function submitForm() {
  proxy.$refs["bpmMpdelRef"].validate(valid => {
    if (valid) {
      createModel(form.value).then(response => {
        console.log(response);
        proxy.$modal.msgSuccess("新增成功");
        open.value = false;
        getList();
      });

    }
  });
}





/** 初始化 **/
getList()
</script>
