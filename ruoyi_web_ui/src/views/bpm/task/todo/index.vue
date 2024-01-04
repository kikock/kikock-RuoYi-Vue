<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryRef" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="任务名称" prop="name">
        <el-input
            v-model="queryParams.name"
            class="!w-240px"
            clearable
            placeholder="请输入任务名称"
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
            v-model="queryParams.createTime"
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            class="!w-240px"
            end-placeholder="结束日期"
            start-placeholder="开始日期"
            type="daterange"
            value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="leaveList" @selection-change="handleSelectionChange">
      <el-table-column align="center" label="任务编号" prop="id" width="300px" />
      <el-table-column align="center" label="任务名称" prop="name" />
      <el-table-column align="center" label="所属流程" prop="processInstance.name" />
      <el-table-column align="center" label="流程发起人" prop="processInstance.startUserNickname" />
      <el-table-column align="center" label="创建时间" prop="createTime" width="180"/>
      <el-table-column label="任务状态" prop="suspensionState">
        <template #default="scope">
          <el-tag v-if="scope.row.suspensionState === 1" type="success">激活</el-tag>
          <el-tag v-if="scope.row.suspensionState === 2" type="warning">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button link type="primary" @click="handleAudit(scope.row)">审批进度</el-button>
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

<script setup name="BpmDoneTask">
import * as TaskApi from '@/api/bpm/task'
import {getCurrentInstance, reactive, ref, toRefs} from 'vue'
import Router from "@/router";

const {proxy} = getCurrentInstance();
const {bpm_oa_leave_type} = proxy.useDict("bpm_oa_leave_type");
const {bpm_process_instance_result} = proxy.useDict("bpm_process_instance_result");
const {bpm_process_instance_status} = proxy.useDict("bpm_process_instance_status");
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
    name: '',
    createTime: []
  }
});
const {queryParams, form} = toRefs(data);

/** 查询OA 请假申请列表 */
function getList() {
  loading.value = true;
  TaskApi.getTodoTaskPage(queryParams.value).then(response => {
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

/** 跳转流程详情 */
function handleAudit(row) {
  Router.push({path: "/task/bpmprocessInstance/detail",query: {id: row.processInstance.id}})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/leave/export', {
    ...queryParams.value
  }, `leave_${new Date().getTime()}.xlsx`)
}

getList();
</script>
