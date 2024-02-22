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
        <el-input
            v-model="queryParams.processName"
            placeholder="请输入流程标识"
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
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['workflow:process:startExport']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row :gutter="10" class="mb8">
    <el-table v-loading="loading" fit :data="processList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
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
          <el-tag size="success">v{{scope.row.version }}</el-tag>
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
              icon="VideoPlay"
              v-hasPermi="['workflow:process:start']"
              @click.native="handleStart(scope.row)"
          >发起
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


      <my-process-viewer key="designer"  :value="bpmnXML"/>
    </el-dialog>


  </div>
</template>

<script setup name="flowWork">
import {getBpmnXml, listProcess} from "@/api/workflow/process";
import {listCategory} from '@/api/workflow/category'
import MyProcessViewer from '@/components/workflow/package/designer/ProcessViewer.vue'
import {reactive} from 'vue'
const router = useRouter();
// 表格数据
const processList = ref([]);
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
const filterCategoryText = ref("");
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
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    processKey: undefined,
    processName: undefined,
    category: undefined
  },
  finishedInfo: {
    finishedSequenceFlowSet: [],
    finishedTaskSet: [],
    unfinishedTaskSet: [],
    rejectedTaskSet: []
  },
  historyProcNodeList:[]
});
const {queryParams,finishedInfo,historyProcNodeList} = toRefs(data);


/** 查询流程分类列表 */
function getCategoryList() {
  listCategory().then(response => categoryOptions.value = response.rows)
}

/** 查询流程定义列表 */
function getList() {
  loading.value = true;
  listProcess(queryParams.value).then(response => {
    processList.value = response.rows;
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

/** 查看流程图 */
function handleProcessView(row) {
  let modelId = row.definitionId;
  // 发送请求，获取xml
  getBpmnXml(modelId).then(response => {
    bpmnXML.value = response.bpmnXml
  })
  showBpmnModelOpen.value = true;
}

//发起流程
function handleStart(row) {
  router.push({
    path: '/workflow/process/start/' + row.deploymentId,
    query: {
      definitionId: row.definitionId,
    }
  })
}

/** 导出按钮操作 */
function handleExport() {
  download('workflow/process/startExport', {
    ...queryParams.value
  }, `wf_start_process_${new Date().getTime()}.xlsx`)
}

function categoryFormat(row, column) {
  return categoryOptions.value.find(k => k.code === row.category)?.categoryName ?? '';
}

getCategoryList()
getList()
</script>

<style scoped>

</style>
