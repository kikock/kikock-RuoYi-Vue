<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模型标识" prop="modelKey">
        <el-input
            v-model="queryParams.modelKey"
            placeholder="请输入模型标识"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模型名称" prop="modelName">
        <el-input
            v-model="queryParams.modelName"
            placeholder="请输入模型名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程分类" prop="category">
        <el-select
            v-model="queryParams.category"
            placeholder="请选择流程分类"
            clearable
            style="width: 240px"
        >
          <el-option
              v-for="item in categoryOptions"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.code">
          </el-option>
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
            type="success"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['workflow:model:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['workflow:model:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" fit :data="modelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="模型标识" align="center" prop="modelKey" :show-overflow-tooltip="true"/>
      <el-table-column label="模型名称" align="center" :show-overflow-tooltip="true">
        <template #default="scope">
          <el-button link type="primary" @click="handleProcessView(scope.row)">
            <span>{{ scope.row.modelName }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="流程分类" align="center" prop="categoryName" :formatter="categoryFormat"/>
      <el-table-column label="模型版本" align="center" width="50">
        <template #default="scope">
          <el-tag type="danger">v{{ scope.row.version }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="description" :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
              link
              type="primary"
              icon="Edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['workflow:model:edit']"
          >修改
          </el-button>
          <el-button
              link
              type="primary"
              icon="EditPen"
              @click="handleDesigner(scope.row)"
              v-hasPermi="['workflow:model:designer']"
          >设计
          </el-button>
          <el-button
              link
              type="primary"
              icon="VideoPlay"
              v-hasPermi="['workflow:model:deploy']"
              @click.native="handleDeploy(scope.row)"
          >部署
          </el-button>
          <el-button
              link
              type="primary"
              icon="View"
              v-hasPermi="['workflow:model:query']"
              @click.native="handleProcessView(scope.row)"
          >流程图
          </el-button>
          <el-button
              link
              type="primary"
              icon="PriceTag"
              v-hasPermi="['workflow:model:list']"
              @click.native="handleHistory(scope.row)"
          >历史
          </el-button>
          <el-button
              link
              type="primary"
              icon="Delete"
              v-hasPermi="['workflow:model:remove']"
              @click.native="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
    />

    <!--  添加或修改模型信息对话框  -->
    <el-dialog :title="title" v-model="open" width="30%" append-to-body @close="cancel()">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模型标识" prop="modelKey">
          <el-input v-model="form.modelKey" clearable disabled/>
        </el-form-item>
        <el-form-item label="模型名称" prop="modelName">
          <el-input v-model="form.modelName" clearable :disabled="form.modelId !== undefined"/>
        </el-form-item>
        <el-form-item label="流程分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择" clearable style="width:100%">
            <el-option v-for="item in categoryOptions" :key="item.categoryId" :label="item.categoryName"
                       :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" maxlength="200"
                    show-word-limit/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel()">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 流程模型图的预览 -->
    <el-dialog title="流程图" v-model="showBpmnModelOpen" width="60%" append-to-body>
      <my-process-viewer key="designer" :value="bpmnXML" v-bind="bpmnControlForm"/>
    </el-dialog>

    <!-- 流程模型历史 -->
    <el-dialog title="流程模型历史" v-model="history.open" width="80%">
      <el-table v-loading="history.loading" fit :data="historyList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="模型标识" align="center" prop="modelKey" :show-overflow-tooltip="true"/>
        <el-table-column label="模型名称" align="center" :show-overflow-tooltip="true">
          <template #default="scope">
            <el-button
                link
                type="primary"
                @click="handleProcessView(scope.row)"
            >
              <span>{{ scope.row.modelName }}</span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="流程分类" align="center" prop="categoryName" :formatter="categoryFormat"/>
        <el-table-column label="模型版本" align="center">
          <template #default="scope">
            <el-tag >v{{ scope.row.version }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="描述" align="center" prop="description" :show-overflow-tooltip="true"/>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button
                link
                type="primary"
                icon="VideoPlay"
                v-hasPermi="['workflow:model:deploy']"
                @click.native="handleDeploy(scope.row)"
            >部署
            </el-button>
            <el-button
                link
                type="primary"
                icon="Star"
                v-hasPermi="['workflow:model:save']"
                @click.native="handleLatest(scope.row)"
            >设为最新
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="historyTotal > 0"
          :total="historyTotal"
          :page.sync="queryHistoryParams.pageNum"
          :limit.sync="queryHistoryParams.pageSize"
          @pagination="getHistoryList"
      />
    </el-dialog>


  </div>

</template>

<script setup name="flowModel">
import {
  addModel,
  delModel,
  deployModel,
  getBpmnXml,
  historyModel,
  latestModel,
  listModel,
  updateModel
} from "@/api/workflow/model";
import {listCategory} from '@/api/workflow/category'
import {reactive} from 'vue'
import router from "@/router";
import MyProcessViewer from '@/components/workflow/package/designer/ProcessViewer.vue'

const {proxy} = getCurrentInstance();
// 表格数据
const modelList = ref([]);
// 按钮loading
const buttonLoading = ref(true);
// 遮罩层
const loading = ref(true);
// 显示搜索条件
const showSearch = ref(true);
// 选中数组
const ids = ref([]);
// 非单个禁用
const single = ref(true);
// 非多个禁用
const multiple = ref(true);
// 总条数
const total = ref(0);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);

//流程分类
const categoryOptions = ref([]);

const currentRow = ref(null);

const historyList = ref([]);

const historyTotal = ref(0);

// BPMN 数据
const showBpmnModelOpen = ref(false);
const bpmnXML = ref("");
const bpmnControlForm = ref({
  prefix: "flowable"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    modelKey: null,
    modelName: null,
    category: null
  },

  rules: {
    modelKey: [
      {required: true, message: "模型标识不能为空", trigger: "blur"}
    ],
    modelName: [
      {required: true, message: "模型名称不能为空", trigger: "blur"}
    ],
    category: [
      {required: true, message: "请选择类型", trigger: "change"}
    ],
  },
  history: {
    open: false,
    loading: false
  },
  queryHistoryParams: {
    pageNum: 1,
    pageSize: 10,
    modelKey: null
  },
  //流程图预览参数
  processView: {
    title: '',
    open: false,
    index: undefined,
    xmlData: "",
  },
});

const {queryParams, form, rules, history, queryHistoryParams, processView} = toRefs(data);

/** 查询流程分类列表 */
function getCategoryList() {
  listCategory().then(response => categoryOptions.value = response.rows)
}

/** 查询流程模型列表 */
function getList() {
  loading.value = true;
  listModel(queryParams.value).then(response => {
    modelList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

function cancel() {
  reset();
  // 关闭dialog
  open.value = false
}

// 表单重置
function reset() {
  form.value = {
    modelId: undefined,
    modelKey: undefined,
    modelName: undefined,
    category: undefined,
    description: undefined
  };
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

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.modelId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 部署流程 */
function handleDeploy(row) {
  loading.value = true;
  deployModel({
    modelId: row.modelId
  }).then(response => {
    proxy.$modal.msgSuccess(response.msg);
  }).finally(() => {
    this.loading = false;
  })
}

/** 查看流程图 */
function handleProcessView(row) {
  let modelId = row.modelId;
  // 发送请求，获取xml
  getBpmnXml(modelId).then(response => {
    bpmnXML.value = response.bpmnXml
  })
  showBpmnModelOpen.value = true;
}

function getHistoryList() {
  history.value.loading = true;
  historyModel(queryHistoryParams.value).then(response => {
    historyTotal.value = response.total;
    historyList.value = response.rows;
    history.value.loading = false;
  })
}

function handleHistory(row) {
  history.value.open = true;
  queryHistoryParams.value.modelKey = row.modelKey;
  getHistoryList();
}

/** 设为最新版 */
function handleLatest(row) {
  proxy.$modal.confirm('是否确认将此版本设为最新？').then(() => {
    history.value.loading = true;
    latestModel({
      modelId: row.modelId
    }).then(response => {
      history.value.open = false;
      getList();
      proxy.$modal.msgSuccess(response.msg);
    }).finally(() => {
      history.value.loading = false;
    })
  })
}

function handleCurrentChange(data) {
  if (data) {
    currentRow.value = JSON.parse(data.content);
  }
}

function handleAdd() {
  title.value = "新增流程模型";
  const dateTime = new Date().getTime();
  form.value = {
    modelKey: `Process_${dateTime}`,
    modelName: `业务流程_${dateTime}`
  }
  open.value = true;
}

/** 修改按钮操作 */
function handleUpdate(row) {
  title.value = "修改流程模型";
  form.value = {
    modelId: row.modelId,
    modelKey: row.modelKey,
    modelName: row.modelName,
    category: row.category,
    description: row.description
  };
  open.value = true;
}

function submitForm() {
  proxy.$refs["formRef"].validate(valid => {
    if (valid) {
      if (form.value.modelId !== undefined) {
        updateModel(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addModel(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 设计按钮操作 */
function handleDesigner(row) {
  console.log("弹出设计流程页面");
  router.push({path: "/workflow/bpmModel/index", query: {modelId: row.modelId}});
}

/** 删除按钮操作 */
function handleDelete(row) {
  const modelIds = row.modelId || ids;
  proxy.$modal.confirm('是否确认删除选择的模型数据？').then(() => {
    loading.value = true;
    return delModel(modelIds);
  }).then(() => {
    loading.value = false;
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).finally(() => {
    loading.value = false;
  });
}

function categoryFormat(row, column) {
  return categoryOptions.value.find(k => k.code === row.category)?.categoryName ?? '';
}

function submitSave() {
  getList();
}

getCategoryList();
getList();
</script>
