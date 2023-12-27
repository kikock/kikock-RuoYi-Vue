<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="组名" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入组名"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input
            v-model="queryParams.description"
            placeholder="请输入描述"
            clearable
            @keyup.enter="handleQuery"
        />
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
            v-hasPermi="['bpm:group:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['bpm:group:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['bpm:group:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['bpm:group:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="groupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id"/>
      <el-table-column label="组名" align="center" prop="name"/>
      <el-table-column label="描述" align="center" prop="description"/>
      <el-table-column label="成员" align="center">
        <template #default="scope">
          <span v-for="userId in scope.row.memberUserIds" :key="userId" style="margin-left: 2px">
            {{ userList.find((user) => user.id == userId)?.name }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" >
        <template #default="scope">
          <span v-if="scope.row.status==0">正常</span>
          <span v-else>停用</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['bpm:group:edit']">
            修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['bpm:group:remove']">删除
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

    <!-- 添加或修改用户组对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="groupRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组名" prop="name">
          <el-input v-model="form.name" placeholder="请输入组名"/>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入描述"/>
        </el-form-item>
        <el-form-item label="成员" prop="memberUserIds">
          <el-select v-model="form.memberUserIds" multiple placeholder="请选这成员">
            <el-option
                v-for="user in userList"
                :key="user.id"
                :label="user.name"
                :value="user.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
              <el-radio :label='0'  size="large">启用</el-radio>
              <el-radio :label='1' size="large">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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

<script setup name="Group">
import {addGroup, delGroup, getGroup, listGroup, updateGroup} from "@/api/bpm/group";
import {listUserSimple} from "@/api/system/user";
import {getCurrentInstance, reactive, ref} from 'vue'

const {proxy} = getCurrentInstance();

const groupList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const userList = ref([]) // 用户列表
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    description: null,
    memberUserIds: null,
    status: null,
  },
  rules: {
    name: [
      {required: true, message: "组名不能为空", trigger: "blur"}
    ],
    description: [
      {required: true, message: "描述不能为空", trigger: "blur"}
    ],
    memberUserIds: [
      {required: true, message: "成员编号数组不能为空", trigger: "blur"}
    ],
    status: [
      {required: true, message: "状态不能为空", trigger: "change"}
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

/** 查询用户组列表 */
function getList() {
  loading.value = true;
  listGroup(queryParams.value).then(response => {
    groupList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/**查询用户**/
function getUserList() {
  listUserSimple().then(response => {// 加载用户列表
    userList.value = response.rows;
  })
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
    description: null,
    memberUserIds: null,
    status: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("groupRef");
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
  title.value = "添加用户组";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getGroup(_id).then(response => {
    form.value = response.data;
    form.value.memberUserIds=form.value.memberUserIds.split(",").map(Number);
    open.value = true;
    title.value = "修改用户组";
    console.log(form.value)
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["groupRef"].validate(valid => {
    if (valid) {
      form.value.memberUserIds = form.value.memberUserIds.toString();
      if (form.value.id != null) {
        updateGroup(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addGroup(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除用户组编号为"' + _ids + '"的数据项？').then(function () {
    return delGroup(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('bpm/group/export', {
    ...queryParams.value
  }, `group_${new Date().getTime()}.xlsx`)
}

getList();
getUserList();
</script>
