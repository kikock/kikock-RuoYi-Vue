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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Delete"
            @click="handleDelete"
            v-hasPermi="['workflow:process:startExport']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['workflow:process:ownExport']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ownProcessList" >
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="流程编号" align="center" prop="procInsId" :show-overflow-tooltip="true"/>-->
      <el-table-column label="流程名称" align="center" prop="procDefName" :show-overflow-tooltip="true"/>
      <el-table-column label="流程类别" align="center" prop="category" :formatter="categoryFormat" />
      <el-table-column label="流程版本" align="center" width="80px">
       <template #default="scope">
          <el-tag>v{{ scope.row.procDefVersion }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="当前节点" align="center" prop="taskName"/>
      <el-table-column label="提交时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="流程状态" align="center" width="100">
        <template #default="scope">
          <dict-tag :options="wf_process_status" :value="scope.row.processStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" prop="duration" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
       <template #default="scope">
         <el-button
             link
             type="primary"
             icon="View"
             v-hasPermi="['workflow:process:query']"
             @click.native="handleFlowRecord(scope.row)"
         >详情
         </el-button>
         <el-button
             link
             type="primary"
             icon="Delete"
             v-hasPermi="['workflow:process:remove']"
             @click.native="handleDelete(scope.row)"
         >删除
         </el-button>
         <el-button
             link
             type="primary"
             icon="CircleClose"
             v-hasPermi="['workflow:process:cancel']"
             @click.native="handleStop(scope.row)"
         >终止
         </el-button>
          <el-button
              link
              type="primary"
            icon="RefreshRight"
            v-hasPermi="['workflow:process:start']"
            @click.native="handleAgain(scope.row)"
          >重新发起</el-button>
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

<script setup  name="WorkOwn">
// wf_process_status
import {listOwnProcess, stopProcess, delProcess} from '@/api/workflow/process';
import { listCategory } from '@/api/workflow/category';
import {reactive} from 'vue'
const {proxy} = getCurrentInstance();
const { wf_process_status } = proxy.useDict('wf_process_status');
const router = useRouter();

// 表格数据
const ownProcessList = ref([]);
// 按钮loading
const processLoading = ref(true);
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
    processKey: undefined,
    processName: undefined,
    category: undefined
  },
  rules: {
  },
});
const {queryParams,form,rules} = toRefs(data);




/** 查询流程分类列表 */
function getCategoryList() {
  listCategory().then(response => categoryOptions.value = response.rows)
}
    /** 查询流程定义列表 */
   function getList() {
      loading.value = true;
      listOwnProcess(queryParams.value).then(response => {
        ownProcessList.value = response.rows;
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

   function handleAgain(row) {
     router.push({
       path: '/workflow/process/start/' + row.deployId,
       query: {
         definitionId: row.procDefId,
         procInsId: row.procInsId
       }
     })
    }
    /**  取消流程申请 */
   function handleStop(row){
      const params = {
        procInsId: row.procInsId
      }
      stopProcess(params).then( res => {
        proxy.$modal.msgSuccess(res.msg);
        getList();
      });
    }
    /** 流程流转记录 */
   function handleFlowRecord(row) {
     console.log(row.procInsId);
      router.push({
        path: '/workflow/process/detail/' + row.procInsId,
        query: {
          processed:false,
        }
      })
    }
    /** 删除按钮操作 */
   function handleDelete(row) {
     console.log(row);
      const infoIds = row.procInsId || ids.value;
      proxy.$modal.confirm('是否确认删除选择的流程数据项?').then(function () {
        return delProcess(infoIds);
      }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
    /** 导出按钮操作 */
   function handleExport() {
      download('workflow/process/ownExport', {
        ...queryParams.value
      }, `wf_own_process_${new Date().getTime()}.xlsx`)
    }
function categoryFormat(row, column) {
  return categoryOptions.value.find(k => k.code === row.category)?.categoryName ?? '';
}
getCategoryList()
getList();
</script>
