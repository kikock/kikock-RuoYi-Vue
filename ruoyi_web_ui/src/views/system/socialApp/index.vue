<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="应用名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入应用名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="社交平台" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择社交平台" clearable>
          <el-option
            v-for="dict in sys_social_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开启状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择开启状态" clearable>
          <el-option
            v-for="dict in sys_normal_disable"
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
          v-hasPermi="['system:socialApp:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:socialApp:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:socialApp:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="socialAppList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="应用名称" align="center" prop="name" />
      <el-table-column label="应用编码" align="center" prop="code" />
      <el-table-column label="社交平台" align="center" prop="type">
        <template #default="scope">
          <dict-tag :options="sys_social_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="corpId" align="center" prop="corpId" />
      <el-table-column label="appKey/AgentId" align="center" prop="appKey" />
      <el-table-column label="appSecret" align="center" prop="appSecret" />
      <el-table-column label="开启状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:socialApp:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:socialApp:remove']">删除</el-button>
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

    <!-- 添加或修改社交应用参数对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="socialAppRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="应用名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入应用名称" />
        </el-form-item>
        <el-form-item label="应用编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入应用编码"
          :readonly="form.id"
          />
        </el-form-item>
        <el-form-item label="社交平台" prop="type">
          <el-select v-model="form.type" placeholder="请选择社交平台">
            <el-option
              v-for="dict in sys_social_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="corpId" prop="corpId">
          <el-input v-model="form.corpId" placeholder="请输入corpId" />
        </el-form-item>
        <el-form-item label="appKey" prop="appKey">
          <el-input v-model="form.appKey" placeholder="请输入平台appKey" />
        </el-form-item>
        <el-form-item label="appSecret" prop="appSecret">
          <el-input v-model="form.appSecret" placeholder="请输入appSecret" />
        </el-form-item>
        <el-form-item label="url" prop="url">
          <el-input v-model="form.url" placeholder="请输入登录回调地址" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="3" v-model="form.remark"></el-input>
        </el-form-item>
        <el-form-item label="开启状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择开启状态">
            <el-option
              v-for="dict in sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
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

<script setup name="SocialApp">
import { listSocialApp, getSocialApp, delSocialApp, addSocialApp, updateSocialApp } from "@/api/system/socialApp";
import {getCurrentInstance, reactive, ref} from 'vue'
const { proxy } = getCurrentInstance();
const { sys_social_type, sys_normal_disable } = proxy.useDict('sys_social_type', 'sys_normal_disable');

const socialAppList = ref([]);
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
    name: null,
    type: null,
    status: null,
  },
  rules: {
    name: [
      { required: true, message: "应用名称不能为空", trigger: "blur" }
    ],
    type: [
      { required: true, message: "社交平台的类型不能为空", trigger: "change" }
    ],
    code: [
      { required: true, message: "应用编码不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "开启状态不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询社交应用参数列表 */
function getList() {
  loading.value = true;
  listSocialApp(queryParams.value).then(response => {
    socialAppList.value = response.rows;
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
    name: null,
    type: null,
    appKey: null,
    appSecret: null,
    code: null,
    corpId: null,
    status: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("socialAppRef");
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
  title.value = "添加社交应用参数";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSocialApp(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改社交应用参数";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["socialAppRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSocialApp(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSocialApp(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除社交应用参数编号为"' + _ids + '"的数据项？').then(function() {
    return delSocialApp(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/socialApp/export', {
    ...queryParams.value
  }, `socialApp_${new Date().getTime()}.xlsx`)
}

getList();
</script>
