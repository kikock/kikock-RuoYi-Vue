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


    <el-table v-loading="loading" :data="copyList" >
      <el-table-column label="抄送编号" align="center" prop="copyId"/>
      <el-table-column label="标题" align="center" prop="title" :show-overflow-tooltip="true"/>
      <el-table-column label="流程名称" align="center" prop="processName" :show-overflow-tooltip="true"/>
      <el-table-column label="发起人" align="center" prop="originatorName"/>
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
              link
              type="primary"
              icon="View"
              @click="handleFlowRecord(scope.row)"
              v-hasPermi="['workflow:process:query']"
          >详情
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

<script setup name="copyUser">
import {listCopyProcess, listOwnProcess} from "@/api/workflow/process"
import {listCategory} from '@/api/workflow/category';
import {reactive} from 'vue'
const router = useRouter();
const {proxy} = getCurrentInstance();
// 流程抄送表格数据
const copyList = ref([]);
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
    processId: undefined,
    processName: undefined,
    categoryId: undefined,
    taskId: undefined,
    userId: undefined,
    originatorName: undefined,
  },
  rules: {
    copyId: [
      {required: true, message: "抄送主键不能为空", trigger: "blur"}
    ],
    processId: [
      {required: true, message: "流程主键不能为空", trigger: "blur"}
    ],
    processName: [
      {required: true, message: "流程名称不能为空", trigger: "blur"}
    ],
    categoryId: [
      {required: true, message: "流程分类主键不能为空", trigger: "blur"}
    ],
    taskId: [
      {required: true, message: "任务主键不能为空", trigger: "blur"}
    ],
    userId: [
      {required: true, message: "用户主键不能为空", trigger: "blur"}
    ]
  },
});
const {queryParams, form, rules} = toRefs(data);

/** 查询流程分类列表 */
function getCategoryList() {
  listCategory().then(response => categoryOptions.value = response.rows)
}


/** 查询流程抄送列表 */
function getList() {
  loading.value = true;
  listCopyProcess(queryParams.value).then(response => {
    copyList.value = response.rows;
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

/** 查看详情 */
function handleFlowRecord(row) {
  console.log(row);
  router.push({
    path: '/workflow/process/detail/' + row.instanceId,
    query: {
      processed: false
    }
  })
}

getCategoryList()
getList();
</script>
