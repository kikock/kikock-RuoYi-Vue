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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="danger"-->
<!--            plain-->
<!--            icon="upload"-->
<!--            @click="openImportForm"-->
<!--            v-hasPermi="['bpm:model:import']"-->
<!--        >导入流程-->
<!--        </el-button>-->
<!--      </el-col>-->
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
      <el-table-column label="流程名称" align="center" prop="name" width="200">
        <template v-slot="scope">
          <el-button  type="primary" link  @click="handleBpmnDetail(scope.row)">
            <span>{{ scope.row.name }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="流程分类" align="center" prop="category" width="100">
      <template #default="scope">
        <dict-tag v-if="scope.row.category" :options="bpm_model_category" :value="scope.row.category"/>
        <el-tag v-if="!scope.row.category" type="danger" >
          未配置
        </el-tag>
      </template>
      </el-table-column>

      <el-table-column label="流程表单" align="center" prop="formType" width="200">
        <template #default="scope">
          <el-tag v-if="scope.row.formType === 10" type="success" >
            <el-button  type="primary" link  @click="handleFromDetail(scope.row)">
              <span>{{scope.row.formName}}</span>
            </el-button>
          </el-tag>
          <el-tag v-if="scope.row.formType === 20" type="success" >
            <el-button  type="primary" link  @click="handleFromDetail(scope.row)">
              <span>{{scope.row.formCustomCreatePath}}</span>
            </el-button>
          </el-tag>
          <el-tag v-if="scope.row.formType!=10 && scope.row.formType!=20" type="danger" >
            未配置
          </el-tag>
        </template>
      </el-table-column>
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
          <template v-slot="scope">
            <el-tag  v-if="scope.row.processDefinition">v{{ scope.row.processDefinition.version }}</el-tag>
            <el-tag  type="warning" v-else>未部署</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="激活状态"
            align="center"
            prop="processDefinition.version"
            width="85"
        >
          <template v-slot="scope">
            <el-switch v-if="scope.row.processDefinition" v-model="scope.row.processDefinition.suspensionState"
                       :active-value="1" :inactive-value="2" @change="handleChangeState(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column label="部署时间" align="center" prop="deploymentTime" width="180">
          <template v-slot="scope">
            <span v-if="scope.row.processDefinition">{{ parseTime(scope.row.processDefinition.deploymentTime) }}</span>
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
              <el-form-item label="流程标识" prop="key">
                <el-input
                  v-model="form.key"
                  :disabled="fromReadOnly"
                  placeholder="请输入流标标识"
                  style="width: 95%"
                />
                <el-tooltip
                  v-if="fromReadOnly"
                  class="item"
                  content="新建后，流程标识不可修改！流程标识填写格式:以字母或下划线开头，后接任意字母、数字、中划线、下划线或句点"
                  effect="light"
                  placement="top"
                >
                  <el-icon  style="margin-left: 5px" ><WarningFilled /></el-icon>
                </el-tooltip>
                <el-tooltip v-else class="item" content="流程标识不可修改！流程标识填写格式:以字母或下划线开头，后接任意字母、数字、中划线、下划线或句点" effect="light" placement="top">
                  <el-icon  style="margin-left: 5px" ><WarningFilled /></el-icon>
                </el-tooltip>
              </el-form-item>

        <el-form-item label="流程名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入流程名称"  style="width: 95%"  :disabled="fromReadOnly" />
        </el-form-item>
        <el-form-item v-if="fromReadOnly" label="流程分类" prop="category">
          <el-select
              v-model="form.category"
              clearable
              placeholder="请选择流程分类"
              style="width: 95%"
          >
            <el-option
                v-for="dict in bpm_model_category"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="流程描述" prop="description">
          <el-input type="textarea" :rows="3" v-model="form.description"  style="width: 95%" ></el-input>
        </el-form-item>
              <div v-if="fromReadOnly">
                <el-form-item label="表单类型" prop="formType">
                  <el-radio-group v-model="form.formType">
                    <el-radio
                      v-for="dict in bpm_model_form_type"
                      :key="dict.value"
                      :label="parseInt(dict.value)"
                    >
                      {{ dict.label }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item v-if="form.formType === 10" label="流程表单" prop="formId">
                  <div style="width: 95%" >
                  <select-more v-model="form.selectMoreName"  value="id" label="name"  url="/bpm/form/simpleList" @change="setFlowFormId" ></select-more>
                  </div>
                </el-form-item>
                <el-form-item
                  v-if="form.formType === 20"
                  label="表单提交路由"
                  prop="formCustomCreatePath"
                >
                  <el-input
                    v-model="form.formCustomCreatePath"
                    placeholder="请输入表单提交路由"
                    style="width: 95%"
                  />
                  <el-tooltip
                    class="item"
                    content="自定义表单的提交路径，使用 Vue 的路由地址，例如说：/bpm/oa/leave/create"
                    effect="light"
                    placement="top"
                  >
                    <el-icon  style="margin-left: 5px" ><WarningFilled /></el-icon>
                  </el-tooltip>
                </el-form-item>
                <el-form-item
                  v-if="form.formType === 20"
                  label="表单查看路由"
                  prop="formCustomViewPath"
                >
                  <el-input
                    v-model="form.formCustomViewPath"
                    placeholder="请输入表单查看路由"
                    style="width: 95%"
                  />
                  <el-tooltip
                    class="item"
                    content="自定义表单的查看路径，使用 Vue 的路由地址，例如说：/bpm/oa/leave/view"
                    effect="light"
                    placement="top"
                  >
                    <el-icon  style="margin-left: 5px" ><WarningFilled /></el-icon>
                  </el-tooltip>
                </el-form-item>
              </div>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 流程模型图的预览 -->
    <el-dialog title="流程图" v-model="showBpmnModelOpen" width="80%" append-to-body>
      <my-process-viewer key="designer" :value="bpmnXML" v-bind="bpmnControlForm" />
    </el-dialog>

    <!-- 表单详情的预览 -->
    <el-dialog   v-model="showBpmFromOpen" title="表单详情" width="800">
      <FormCreate :option="bpmFromOption" :rule="bpmFromRule"  />
    </el-dialog>
  </div>
</template>

<script setup name="bpmForm">
import router from "@/router";
import {getCurrentInstance, reactive, ref} from 'vue'
import {
  createModel,
  getModel,
  listModel,
  updateModel,
  deployModel,
  updateModelState,
  deleteModel
} from '@/api/bpm/model'
//导入 form-create
import formCreate from "@form-create/element-ui";
//获取 formCreate 组件
const FormCreate = formCreate.$form();
import SelectMore from '@/components/SelectMore/index.vue'
import MyProcessViewer from '@/components/bpmnProcessDesigner/package/designer/ProcessViewer.vue'
import {getForm} from '@/api/bpm/form'
const {proxy} = getCurrentInstance();
const {bpm_model_category,bpm_model_form_type
} = proxy.useDict('bpm_model_category','bpm_model_form_type');
const formList = ref([]);
const loading = ref(true);
// 新增,修改只读显示状态
const fromReadOnly=ref(false)
const showSearch = ref(true);
const total = ref(0);
//流程弹出
const open = ref(false);
const title = ref("");
// BPMN 数据
const showBpmnModelOpen =ref(false);
const bpmnXML =ref("");
const bpmnControlForm =ref({
  prefix: "flowable"
});
// 表单详情 数据
const showBpmFromOpen =ref(false);
const bpmFromOption =ref({});
const bpmFromRule =ref([]);

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
    }],
    category: [
      { required: true, message: "流程分类不能为空", trigger: "blur" }
    ],
    formType: [
      { required: true, message: "表单类型不能为空", trigger: "blur" }
    ],
    formId: [
      { required: true, message: "请选择流程表单Id", trigger: "blur" }
    ],
    formCustomCreatePath: [
      { required: true, message: "请选择流程表单提交路由", trigger: "blur" }
    ],
    formCustomViewPath: [
      { required: true, message: "请选择流程表单查看路由", trigger: "blur" }
    ],
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
  fromReadOnly.value=false
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

/** 流程图的详情按钮操作 */
function handleBpmnDetail(row) {
  getModel(row.id).then(response => {
    bpmnXML.value = response.data.bpmnXml
    // 弹窗打开
    showBpmnModelOpen.value = true
  })
}
/** 详情按钮*/
function handleFromDetail(row) {
  // 流程表单
  if (row.formId) {
    getForm(row.formId).then(respose=>{
      bpmFromOption.value = JSON.parse(respose.data.conf)
      bpmFromRule.value = JSON.parse(respose.data.fields);
      // 弹窗打开
      showBpmFromOpen.value = true
    })
    // 业务表单
  } else if (row.formCustomCreatePath) {
    router.push({path: row.formCustomCreatePath});
  }
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  open.value = true;
  title.value = "修改流程";
  const modelId = row.id
  if (!modelId) {
    proxy.$modal.msgSuccess("错误缺少模型 modelId 编号");
    return
  }
  getModel(modelId).then(response => {
    form.value = response.data;
    open.value = true;
    if (response.data.formId) {
      form.value.selectMoreName = `【${response.data.formId}】${response.data.formName}`
    }
    fromReadOnly.value=true
    title.value = "修改流程";
    console.log(form.value);
  });
}
/** 导入按钮操作 */
function openImportForm() {
  console.log("导入流程按钮");

}
/** 更新状态操作 */
function handleChangeState(row) {
  const id = row.id;
  let state = row.processDefinition.suspensionState;
  let statusState = state === 1 ? '激活' : '挂起';

  proxy.$modal.confirm('是否确认' + statusState + '流程名字为"' + row.name + '"的数据？').then(function () {
    return updateModelState(id, state);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess(statusState + "成功");
  }).catch(() => {
    // 取消后，进行恢复按钮
    row.processDefinition.suspensionState = (state === 1 ? 2 : 1);
  });
}
/** 设计流程操作 */
function handleDesign(row) {
  console.log("弹出设计流程页面");
  router.push({path: "/flowable/bpmModel/editor", query: {modelId: row.id}});
}
/** 分配规则操作 */
function handleAssignRule(row) {
  console.log("弹出分配规则页面");
  router.push({path: "/flowable/bpmModel/taskAssignRule", query: {modelId: row.id}});
}
/** 发布流程操作 */
function handleDeploy(row) {
  console.log("发布流程操作操作");
  proxy.$modal.confirm('是否发布该流程项？').then(function() {
    deployModel(row.id).then(response => {
      getList();
      proxy.$modal.msgSuccess("部署成功");
    })
  }).catch(() => {});
}
/** 流程定义操作操作 */
function handleDefinitionList(row) {
  console.log("弹出流程定义tab页面");
  router.push({path: "/flowable/bpmModel/definition", query: {key: row.key}});
}
/** 删除按钮操作 */
function handleDelete(row) {
  proxy.$modal.confirm('是否确认删除工作流自定义表单数据？').then(function () {
    deleteModel(row.id).then(response => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
  }).then(() => {

  }).catch(() => {
  });
}
/** 表单参数设置 */
function setFlowFormId(row) {
  console.log("设置表单",row);
  form.value.formId=""
  form.value.formName=""
  if(row){
    form.value.formId=row.id
    form.value.formName=row.name
  }

}


/** 提交按钮 */
function submitForm() {
  console.log("提交表单",form.value);
  proxy.$refs["bpmMpdelRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateModel(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        createModel(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
          proxy.$alert('<strong>新建模型成功！</strong>后续需要执行如下 4 个步骤：' +
              '<div>1. 点击【修改流程】按钮，配置流程的分类、表单信息</div>' +
              '<div>2. 点击【设计流程】按钮，绘制流程图</div>' +
              '<div>3. 点击【分配规则】按钮，设置每个用户任务的审批人</div>' +
              '<div>4. 点击【发布流程】按钮，完成流程的最终发布</div>' +
              '注意，每次流程修改后，都需要点击【发布流程】按钮，才能正式生效！！！',
              '重要提示', {
                dangerouslyUseHTMLString: true,
                type: 'success'
              });

        });
      }
    }
  });
}





/** 初始化 **/
getList()
</script>
<style>
.selectMoreCss {
  width: 95%;
}

</style>
