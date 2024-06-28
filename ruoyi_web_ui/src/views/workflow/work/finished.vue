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
            placeholder="请输入流程名称"
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

    <el-table v-loading="loading" :data="finishedList" >
      <el-table-column label="任务编号" align="center" prop="taskId" :show-overflow-tooltip="true"/>
      <el-table-column label="流程名称" align="center" prop="procDefName" :show-overflow-tooltip="true"/>
      <el-table-column label="任务节点" align="center" prop="taskName"/>
      <el-table-column label="流程发起人" align="center">
        <template #default="scope">
          <label>{{ scope.row.startUserName }}</label>
        </template>
      </el-table-column>
      <el-table-column label="接收时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="审批时间" align="center" prop="finishTime" width="180"/>
      <el-table-column label="耗时" align="center" prop="duration" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
              link
              type="primary"
              icon="View"
              v-hasPermi="['workflow:process:query']"
              @click.native="handleFlowRecord(scope.row)"
          >流转记录
          </el-button>
          <el-button
              link
              type="primary"
              icon="RefreshLeft"
              @click="handleRevoke(scope.row)"
              v-hasPermi="['workflow:process:revoke']"
          >撤回
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
    />
  </div>
</template>

<script setup name="Finished">

import { listCategory } from '@/api/workflow/category';
import {listFinishedProcess} from '@/api/workflow/process';
import {revokeProcess} from "@/api/workflow/finished";
import {reactive} from 'vue'
const {proxy} = getCurrentInstance();
const router = useRouter();
// 表格数据
const finishedList = ref([]);
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
const processTotal = ref(0);
const src = ref("");
const definitionList = ref([]);
// 日期范围
const dateRange = ref([]);


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    category: null,
    key: null,
    tenantId: null,
    deployTime: null,
    derivedFrom: null,
    derivedFromRoot: null,
    parentDeploymentId: null,
    engineVersion: null
  },
  rules: {},
});
const {queryParams, form, rules} = toRefs(data);

/** 查询流程分类列表 */
function getCategoryList() {
  listCategory().then(response => categoryOptions.value = response.rows)
}

/** 查询流程定义列表 */
function getList() {
  loading.value = true;
  listFinishedProcess(queryParams.value).then(response => {
    finishedList.value = response.rows;
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
/** 流程流转记录 */
function handleFlowRecord(row) {
  router.push({
    path: '/workflow/process/detail/' + row.procInsId,
    query: {
      processed: false
    }
  })
}

/** 撤回任务 */
function handleRevoke(row) {
  const params = {
    procInsId: row.procInsId,
    taskId: row.taskId
  };
  revokeProcess(params).then(res => {
    proxy.$modal.msgSuccess(res.msg);
    getList();
  });
}

getCategoryList()
getList();
</script>
