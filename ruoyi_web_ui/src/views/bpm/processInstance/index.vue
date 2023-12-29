<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务名称" prop="type">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入流程名称"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="所属流程" prop="processDefinitionId">
        <el-input
            v-model="queryParams.processDefinitionId"
            placeholder="请输入流程定义的编号"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="流程分类" prop="category">
        <el-select
            v-model="queryParams.category"
            placeholder="请选择流程分类"
            clearable
            class="!w-240px"
        >
          <el-option
              v-for="dict in bpm_model_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable class="!w-240px">
          <el-option
              v-for="dict in bpm_process_instance_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="结果" prop="result">
        <el-select
            v-model="queryParams.result"
            placeholder="请选择结果"
            clearable
            class="!w-240px"
        >
          <el-option
              v-for="dict in bpm_process_instance_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="提交时间" prop="createTime">
        <el-date-picker
            v-model="queryParams.createTime"
            value-format="YYYY-MM-DD HH:mm:ss"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        <el-button
            type="primary"
            icon="Plus"
            plain
            v-hasPermi="['bpm:process:query']"
            @click="handleCreate"
        >发起流程
        </el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="leaveList" @selection-change="handleSelectionChange">
      <el-table-column label="流程编号" align="center" prop="id" width="300px" />
      <el-table-column label="流程名称" align="center" prop="name" />
      <el-table-column label="流程分类" align="center" prop="category">
        <template #default="scope">
          <dict-tag :type="bpm_model_category" :value="scope.row.category" />
        </template>
      </el-table-column>
      <el-table-column label="当前审批任务" align="center" prop="tasks">
        <template #default="scope">
          <el-button type="primary" v-for="task in scope.row.tasks" :key="task.id" link>
            <span>{{ task.name }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status">
        <template #default="scope">
          <dict-tag :type="bpm_process_instance_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="结果" prop="result">
        <template #default="scope">
          <dict-tag :type="bpm_process_instance_result" :value="scope.row.result" />
        </template>
      </el-table-column>
      <el-table-column label="提交时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
              link
              type="primary"
              v-hasPermi="['bpm:process:cancel']"
              @click="handleDetail(scope.row)"
          >
            详情
          </el-button>
          <el-button
              link
              type="primary"
              v-if="scope.row.result === 1"
              v-hasPermi="['bpm:process:query']"
              @click="handleCancel(scope.row)"
          >
            取消
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
</template>

<script setup name="BpmProcessInstance">
import {addLeave, delLeave, listLeave, updateLeave} from "@/api/bpm/leave";
import {getCurrentInstance, reactive, ref,toRefs} from 'vue'
import router from "@/router";
const {proxy} = getCurrentInstance();
const {bpm_oa_leave_type} = proxy.useDict("bpm_oa_leave_type");
const {bpm_process_instance_result} = proxy.useDict("bpm_process_instance_result");
const {bpm_process_instance_status} = proxy.useDict("bpm_process_instance_status");
const {bpm_model_category} = proxy.useDict("bpm_model_category");
const leaveList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    // id: null,
    name: '',
    processDefinitionId: '',
    category: '',
    status: '',
    createTime: '',
    // day: null,
    // result: null,
    // processInstanceId: null,
  },
  rules: {
    userId: [
      {required: true, message: "申请人的用户编号不能为空", trigger: "blur"}
    ],
    type: [
      {required: true, message: "请假类型不能为空", trigger: "change"}
    ],
    reason: [
      {required: true, message: "请假原因不能为空", trigger: "blur"}
    ],
    startTime: [
      {required: true, message: "开始时间不能为空", trigger: "blur"}
    ],
    endTime: [
      {required: true, message: "结束时间不能为空", trigger: "blur"}
    ],
    day: [
      {required: true, message: "请假天数不能为空", trigger: "blur"}
    ],
    result: [
      {required: true, message: "请假结果不能为空", trigger: "blur"}
    ],
    createTime: [
      {required: true, message: "创建时间不能为空", trigger: "blur"}
    ],
    updateTime: [
      {required: true, message: "更新时间不能为空", trigger: "blur"}
    ],
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询OA 请假申请列表 */
function getList() {
  loading.value = true;
  listLeave(queryParams.value).then(response => {
    leaveList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    userId: null,
    type: null,
    reason: null,
    startTime: null,
    endTime: null,
    day: null,
    result: null,
    processInstanceId: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("leaveRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}
/** 发起按钮操作 */
function handleCreate() {
  router.push({name: 'BpmProcessInstanceCreate'})
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/**
 * 流程详情
 * @param row
 */
function handleDetail  (row) {
  router.push({name: 'BpmProcessInstanceDetail', query: {id: row.id}})
}

/**
 * 取消流程
 * @param row
 */
function handleCancel (row){
  // 二次确认
  // const { value } =  ElMessageBox.prompt('请输入取消原因', '取消流程', {
  //   confirmButtonText: t('common.ok'),
  //   cancelButtonText: t('common.cancel'),
  //   inputPattern: /^[\s\S]*.*\S[\s\S]*$/, // 判断非空，且非空格
  //   inputErrorMessage: '取消原因不能为空'
  // })
  // 发起取消
  //  ProcessInstanceApi.cancelProcessInstance(row.id, value)
  // message.success('取消成功')
  // 刷新列表
   getList()
}



getList();
</script>
