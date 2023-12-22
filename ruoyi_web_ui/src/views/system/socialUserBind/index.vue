<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="系统用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入系统用户名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="绑定平台" prop="socialType">
        <el-select v-model="queryParams.socialType" placeholder="请选择绑定平台" clearable>
          <el-option
            v-for="dict in sys_social_type"
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
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:socialUserBind:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="socialUserBindList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="系统用户名称" align="center" prop="user.userName" />
      <el-table-column label="第三方账号名称" align="center" prop="userName" />
      <el-table-column label="第三方账号昵称" align="center" prop="nickName" />
      <el-table-column label="绑定平台" align="center" prop="socialType">
        <template #default="scope">
          <dict-tag :options="sys_social_type" :value="scope.row.socialType"/>
        </template>
      </el-table-column>
      <el-table-column label="第三方平台唯一id" align="center" prop="socialUuid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:socialUserBind:remove']">强制解绑</el-button>
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

    <!-- 添加或修改三方用户对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="socialUserBindRef" :model="form" :rules="rules" label-width="80px">
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

<script setup name="SocialUserBind">
import { listSocialUserBind, getSocialUserBind, delSocialUserBind, addSocialUserBind, updateSocialUserBind } from "@/api/system/socialUserBind";
import {getCurrentInstance, reactive, ref} from 'vue'
import {socialUnbind} from '@/api/system/socialApp'
const { proxy } = getCurrentInstance();
const { sys_social_type } = proxy.useDict('sys_social_type');

const socialUserBindList = ref([]);
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
    userName: null,
    nickName: null,
    socialType: null,
  },
  rules: {
    userId: [
      { required: true, message: "系统用户id不能为空", trigger: "blur" }
    ],
    socialType: [
      { required: true, message: "绑定平台不能为空", trigger: "change" }
    ],
    socialUuid: [
      { required: true, message: "第三方平台用户唯一id不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询三方用户列表 */
function getList() {
  loading.value = true;
  listSocialUserBind(queryParams.value).then(response => {
    socialUserBindList.value = response.rows;
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
    userName: null,
    nickName: null,
    socialType: null,
    socialUuid: null,
    email: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("socialUserBindRef");
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
  title.value = "添加三方用户";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSocialUserBind(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改三方用户";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["socialUserBindRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSocialUserBind(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSocialUserBind(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认解绑该用户第三方绑定账号？').then(function() {
    socialUnbind(row.socialUuid).then(resp => {
        if(resp.code==200){
          console.log(resp);
          getList();
          proxy.$modal.msgSuccess(resp.msg);
        }
    });
  })
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/socialUserBind/export', {
    ...queryParams.value
  }, `socialUserBind_${new Date().getTime()}.xlsx`)
}

getList();
</script>
