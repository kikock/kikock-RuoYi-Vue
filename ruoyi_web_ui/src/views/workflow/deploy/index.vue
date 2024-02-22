<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程标识" prop="processKey">
        <el-input
            v-model="queryParams.processKey"
            placeholder="请输入流程标识"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程名称" prop="processName">
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
      <el-form-item label="状态" prop="state">
        <el-select v-model="queryParams.state" clearable placeholder="请选择状态">
          <el-option :key="1" label="激活" value="active"/>
          <el-option :key="2" label="挂起" value="suspended"/>
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
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['workflow:deploy:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row :gutter="10" class="mb8">
      <el-table v-loading="loading" fit :data="deployList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="流程标识" align="center" prop="processKey" :show-overflow-tooltip="true"/>
        <el-table-column label="流程名称" align="center" :show-overflow-tooltip="true">
          <template #default="scope">
            <el-button type="text" @click="handleProcessView(scope.row)">
              <span>{{ scope.row.processName }}</span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="流程分类" align="center" prop="categoryName" :formatter="categoryFormat"/>
        <el-table-column label="流程版本" align="center">
          <template #default="scope">
            <el-tag type="danger">
              v{{ scope.row.version }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-tag type="success" v-if="!scope.row.suspended">激活</el-tag>
            <el-tag type="warning" v-if="scope.row.suspended">挂起</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="部署时间" align="center" prop="deploymentTime" width="180"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button
                type="text"
                icon="PriceTag"
                @click.native="handlePublish(scope.row)"
                v-hasPermi="['workflow:deploy:list']"
            >版本管理
            </el-button>
            <el-button
                type="text"
                icon="Delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['workflow:deploy:remove']"
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
    </el-row>

    <!-- 流程模型图的预览 -->
    <el-dialog title="流程图" v-model="showBpmnModelOpen" width="60%" append-to-body>
      <my-process-viewer key="designer" :value="bpmnXML" v-bind="bpmnControlForm" />
    </el-dialog>


    <!-- 版本管理 -->
    <el-dialog title="版本管理" v-model="publish.open" width="70%">
      <el-table v-loading="publish.loading" :data="publish.dataList">
        <el-table-column label="流程标识" align="center" prop="processKey" :show-overflow-tooltip="true"/>
        <el-table-column label="流程名称" align="center" :show-overflow-tooltip="true">
          <template #default="scope">
            <el-button type="text" @click="handleProcessView(scope.row)">
              <span>{{ scope.row.processName }}</span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="流程版本" align="center">
          <template #default="scope">
            <el-tag type="danger">v{{ scope.row.version }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-tag type="success" v-if="!scope.row.suspended">激活</el-tag>
            <el-tag type="warning" v-if="scope.row.suspended">挂起</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button
                v-if="!scope.row.suspended"
                type="text"
                icon="VideoPause"
                @click="handleChangeState(scope.row, 'suspended')"
                v-hasPermi="['workflow:deploy:status']"
            >挂起
            </el-button>
            <el-button
                v-if="scope.row.suspended"
                type="text"
                icon="VideoPlay"
                @click="handleChangeState(scope.row, 'active')"
                v-hasPermi="['workflow:deploy:status']"
            >激活
            </el-button>
            <el-button
                type="text"
                icon="Delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['workflow:deploy:remove']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
          v-show="publishTotal > 0"
          :total="publishTotal"
          :page.sync="publishQueryParams.pageNum"
          :limit.sync="publishQueryParams.pageSize"
          @pagination="getPublishList"
      />
    </el-dialog>

  </div>
</template>

<script setup name="flowDeploy">

import {listCategory} from '@/api/workflow/category'
import {changeState, delDeploy, getBpmnXml, listDeploy, listPublish} from '@/api/workflow/deploy'
import {reactive} from 'vue'
import MyProcessViewer from '@/components/workflow/package/designer/ProcessViewer.vue'
const {proxy} = getCurrentInstance();

// 表格数据
const deployList = ref([]);
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
    processKey: null,
    processName: null,
    category: null,
    state: null
  },
  publish: {
    open: false,
    loading: false,
    dataList: []
  },
  publishTotal: 0,
  publishQueryParams: {
    pageNum: 1,
    pageSize: 10,
    processKey: ""
  },
});
const {queryParams, form, publish, publishTotal, publishQueryParams} = toRefs(data);


/** 查询流程分类列表 */
function getCategoryList() {
  listCategory().then(response => categoryOptions.value = response.rows)
}

/** 查询流程部署列表 */
function getList() {
  loading.value = true;
  listDeploy(queryParams.value).then(response => {
    deployList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    processKey: null,
    processName: null,
    category: null,
    state: null
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

/** 查看流程图 */
function handleProcessView(row) {
  let modelId = row.definitionId;
  // 发送请求，获取xml
  getBpmnXml(modelId).then(response => {
    bpmnXML.value = response.bpmnXml
  })
  showBpmnModelOpen.value = true;
}

function getPublishList() {
  publish.value.loading = true;
  listPublish(publishQueryParams.value).then(response => {
    publish.value.dataList = response.rows;
    publishTotal.value = response.total;
    publish.value.loading = false;
  })
}

function handlePublish(row) {
  publishQueryParams.value.processKey = row.processKey;
  publish.value.open = true;
  getPublishList();
}

/** 挂起/激活流程 */
function handleChangeState(row, state) {
  const params = {
    definitionId: row.definitionId,
    state: state
  }
  changeState(params).then(res => {
    proxy.$modal.msgSuccess(res.msg);
    getPublishList();
  });
}

function handleDelete(row) {
  const deploymentIds = row.deploymentId || this.ids;
  proxy.$modal.confirm('是否确认删除选中的数据？').then(() => {
    loading.value = true;;
    return delDeploy(deploymentIds);
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

getCategoryList()
getList()
</script>

<style scoped>
.pagination-container .el-pagination{
  right: 45px;
}
</style>
