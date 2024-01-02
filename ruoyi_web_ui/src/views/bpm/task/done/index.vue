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
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="任务编号" prop="id" width="300px" />
      <el-table-column align="center" label="任务名称" prop="name" />
      <el-table-column align="center" label="所属流程" prop="processInstance.name" />
      <el-table-column align="center" label="流程发起人" prop="processInstance.startUserNickname" />
      <el-table-column align="center" label="原因" prop="reason"/>
      <el-table-column align="center" label="创建时间" prop="createTime" width="180"/>
      <el-table-column align="center" label="状态">
        <template #default="scope">
          <dict-tag :options="bpm_process_instance_result" :value="scope.row.result"/>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template #default="scope">
          <el-button v-hasPermi="['system:leave:query']" icon="Edit" link type="primary"
                     @click="openDetail(scope.row)">详情
          </el-button>
          <el-button v-hasPermi="['system:leave:remove']" icon="Delete" link type="primary"
                     @click="handleAudit(scope.row)">流程
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        v-model:limit="queryParams.pageSize"
        v-model:page="queryParams.pageNum"
        :total="total"
        @pagination="getList"
    />

    <!-- 添加OA 请假申请对话框 -->
    <el-dialog v-model="open" :title="title" append-to-body width="500px">
      <el-form ref="leaveRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="请假类型" prop="type">
          <el-select
              v-model="form.type"
              class="!w-240px"
              clearable
              placeholder="请选择请假类型"
          >
            <el-option
                v-for="dict in bpm_oa_leave_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="form.startTime"
                          clearable
                          placeholder="请选择开始时间"
                          type="date"
                          value-format="YYYY-MM-DD">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="form.endTime"
                          clearable
                          placeholder="请选择结束时间"
                          type="date"
                          value-format="YYYY-MM-DD">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假原因" prop="reason">
          <el-input v-model="form.reason" placeholder="请输入请假原因"/>
        </el-form-item>
        <!--        <el-form-item label="请假天数" prop="day">-->
        <!--          <el-input v-model="form.day" placeholder="请输入请假天数" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="请假结果" prop="result">-->
        <!--          <el-input v-model="form.result" placeholder="请输入请假结果" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="流程实例的编号" prop="processInstanceId">-->
        <!--          <el-input v-model="form.processInstanceId" placeholder="请输入流程实例的编号" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="删除标志" prop="delFlag">-->
        <!--          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="备注" prop="remark">-->
        <!--          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />-->
        <!--        </el-form-item>-->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script name="BpmTodoTask" setup>
import {addLeave, delLeave, listLeave, updateLeave} from "@/api/bpm/leave";
import {getCurrentInstance, reactive, ref,toRefs} from 'vue';

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
/** 详情操作 */
const detailRef = ref()
const openDetail = (row) => {
  detailRef.value.open(row.id)
}

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

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加OA 请假申请";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  // const _id = row.id || ids.value
  // getLeave(_id).then(response => {
  //   form.value = response.data;
  //   open.value = true;
  //   title.value = "修改OA 请假申请";
  // });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["leaveRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateLeave(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addLeave(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除OA 请假申请编号为"' + _ids + '"的数据项？').then(function () {
    return delLeave(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/leave/export', {
    ...queryParams.value
  }, `leave_${new Date().getTime()}.xlsx`)
}

getList();
</script>
