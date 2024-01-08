<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <XButton
            pre-icon="back2"
            title="返回"
            @click="backPage"
            type="primary"
        />
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="formList">
      <el-table-column
          type="index"
          label="序号"
          align="center"
          width="50"
          :index="indexMethod">
      </el-table-column>
      <el-table-column label="定义编号" align="center" prop="id" width="350" :show-overflow-tooltip="true"/>
      <el-table-column label="定义名称" align="center" prop="name" width="200">
        <template v-slot="scope">
          <el-button type="primary" link @click="handleBpmnDetail(scope.row)">
            <span>{{ scope.row.name }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="定义分类" align="center" prop="category" width="80">
        <template #default="scope">
          <dict-tag v-if="scope.row.category" :options="bpm_model_category" :value="scope.row.category"/>
          <el-tag v-if="!scope.row.category" type="danger">
            未配置
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="表单信息" align="center" prop="formType" width="200">
        <template #default="scope">
          <el-tag v-if="scope.row.formType === 10" type="success">
            <el-button  type="primary" link  @click="handleFromDetail(scope.row)">
              <span>{{ scope.row.formName }}</span>
            </el-button>
          </el-tag>
          <el-tag v-if="scope.row.formType === 20" type="success">
            <el-button  type="primary" link  @click="handleFromDetail(scope.row)">
              <span>{{ scope.row.formCustomCreatePath }}</span>
            </el-button>
          </el-tag>
          <el-tag v-if="scope.row.formType!=10 && scope.row.formType!=20" type="danger">
            未配置
          </el-tag>
        </template>
      </el-table-column>
        <el-table-column label="流程版本" align="center" prop="processDefinition.version" width="80">
          <template #default="scope">
            <el-tag v-if="scope.row">v{{ scope.row.version }}</el-tag>
            <el-tag type="warning" v-else>未部署</el-tag>
          </template>
        </el-table-column>
      <el-table-column label="状态" align="center" prop="version" width="80">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.suspensionState === 1">激活</el-tag>
          <el-tag type="warning" v-if="scope.row.suspensionState === 2">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="部署时间" align="center" prop="deploymentTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.deploymentTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="定义描述" align="center" prop="description" width="80"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button  link type="primary" @click="handleAssignRule(scope.row)" v-hasPermi="['bpm:form:edit']">
            规则分配
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
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 流程模型图的预览 -->
    <el-dialog title="流程图" v-model="showBpmnModelOpen" width="80%" append-to-body>
      <my-process-viewer key="designer" :value="bpmnXML" v-bind="bpmnControlForm"/>
    </el-dialog>

    <!-- 表单详情的预览 -->
    <el-dialog v-model="showBpmFromOpen" title="表单详情" width="800">
      <FormCreate :option="bpmFromOption" :rule="bpmFromRule"/>
    </el-dialog>
  </div>
</template>

<script setup name="definitionFrom">
import {getCurrentInstance, reactive, ref} from 'vue'
import {getProcessDefinitionBpmnXML, getProcessDefinitionPage} from '@/api/bpm/definition'
import {getForm} from '@/api/bpm/form'
import MyProcessViewer from '@/components/bpmnProcessDesigner/package/designer/ProcessViewer.vue'
import formCreate from "@form-create/element-ui";
import router from '@/router'
import {XButton} from '@/components/XButton'
//获取 formCreate 组件
const FormCreate = formCreate.$form();
const {query} = useRoute() // 查询参数
const {proxy} = getCurrentInstance();
const {
  bpm_model_category, bpm_model_form_type
} = proxy.useDict('bpm_model_category', 'bpm_model_form_type');
const formList = ref([]);
const loading = ref(true);
// 新增,修改只读显示状态
// BPMN 数据
const showBpmnModelOpen = ref(false);
const bpmnXML = ref("");
const bpmnControlForm = ref({
  prefix: "flowable"
});
// 表单详情 数据
const showBpmFromOpen = ref(false);
const bpmFromOption = ref({});
const bpmFromRule = ref([]);

const total = ref(0);
//流程定义弹出
const open = ref(false);
const title = ref("");


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    key: query.key, // 流程模型的编号。如果 modelId 非空，则用于流程模型的查看与配置
  },
});
const {queryParams} = toRefs(data);

/** 返回按钮 */
const backPage = () => {
  const obj = {path: "/flowable/bpm/model"};
  proxy.$tab.closeOpenPage(obj);
}

/** 序号 */
function indexMethod(index) {
  return ((queryParams.value.pageNum - 1) * queryParams.value.pageSize + index + 1)
}

/** 查询工作流的自定义表单列表 */
function getList() {
  console.log("加载流程定义数据");
  loading.value = true;
  getProcessDefinitionPage(queryParams.value).then(response => {
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


/** 流程图的详情按钮操作 */
function handleBpmnDetail(row) {
  console.log(row);
  getProcessDefinitionBpmnXML(row.id).then(response => {
    console.log(response);
    bpmnXML.value = response.data
    // 弹窗打开
    showBpmnModelOpen.value = true
  })
}

/** 详情按钮*/
function handleFromDetail(row) {
  getForm(row.formId).then(respose => {
    bpmFromOption.value = JSON.parse(respose.data.conf)
    bpmFromRule.value = JSON.parse(respose.data.fields);
    // 弹窗打开
    showBpmFromOpen.value = true
  })
}


/** 返回按钮 */
function cancel() {
  open.value = false;
  reset();
}


/** 分配规则操作 */
function handleAssignRule(row) {
  console.log("弹出分配规则页面");
  router.push({path: "/flowable/bpmModel/taskAssignRule", query: {processDefinitionId: row.id}});
}

/** 初始化 **/
getList()
</script>
<style>
.selectMoreCss {
  width: 95%;
}

</style>
