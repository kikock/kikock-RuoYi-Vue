<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="请假类型" prop="type">
        <el-select
            v-model="queryParams.type"
            placeholder="请选择请假类型"
            clearable
            class="!w-240px"
        >
          <el-option
              v-for="dict in bpm_oa_leave_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="申请时间" prop="createTime">
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
      <el-form-item label="结果" prop="result">
        <el-select
            v-model="queryParams.result"
            placeholder="请输入请假结果"
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
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['system:leave:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="Edit"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['system:leave:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="Delete"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['system:leave:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:leave:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="leaveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请编号" align="center" prop="id" />
      <el-table-column label="请假类型" align="center ">
        <template #default="scope">
          <dict-tag :options="bpm_oa_leave_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="原因" align="center" prop="reason" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" >
        <template #default="scope">
          <dict-tag :options="bpm_process_instance_result" :value="scope.row.result"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:leave:query']">详情</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:leave:remove']">删除</el-button>
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

    <!-- 添加OA 请假申请对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="leaveRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="请假类型" prop="type">
          <el-select
              v-model="form.type"
              placeholder="请选择请假类型"
              clearable
              class="!w-240px"
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
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假原因" prop="reason">
          <el-input v-model="form.reason" placeholder="请输入请假原因" />
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

<script setup name="Leave">
import { listLeave, getLeave, delLeave, addLeave, updateLeave } from "@/api/bpm/leave";
import {getCurrentInstance, reactive, ref} from 'vue'
const { proxy } = getCurrentInstance();
const {bpm_oa_leave_type} = proxy.useDict("bpm_oa_leave_type");
const {bpm_process_instance_result} = proxy.useDict("bpm_process_instance_result");
const {bpm_process_instance_status} = proxy.useDict("bpm_process_instance_status");
const leaveList = ref([]);
const open = ref(false);
const openTwo = ref(false);
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
    userId: null,
    type: null,
    reason: null,
    startTime: null,
    endTime: null,
    day: null,
    result: null,
    processInstanceId: null,
  },
  rules: {
    userId: [
      { required: true, message: "申请人的用户编号不能为空", trigger: "blur" }
    ],
    type: [
      { required: true, message: "请假类型不能为空", trigger: "change" }
    ],
    reason: [
      { required: true, message: "请假原因不能为空", trigger: "blur" }
    ],
    startTime: [
      { required: true, message: "开始时间不能为空", trigger: "blur" }
    ],
    endTime: [
      { required: true, message: "结束时间不能为空", trigger: "blur" }
    ],
    day: [
      { required: true, message: "请假天数不能为空", trigger: "blur" }
    ],
    result: [
      { required: true, message: "请假结果不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
    updateTime: [
      { required: true, message: "更新时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

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
  proxy.$modal.confirm('是否确认删除OA 请假申请编号为"' + _ids + '"的数据项？').then(function() {
    return delLeave(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/leave/export', {
    ...queryParams.value
  }, `leave_${new Date().getTime()}.xlsx`)
}

getList();
</script>
