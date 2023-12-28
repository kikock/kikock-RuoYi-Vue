<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="表单名" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入表单名"
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
            v-hasPermi="['bpm:form:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="formList" >
      <el-table-column
          type="index"
          label="序号"
          align="center"
          width="50"
          :index="indexMethod">
      </el-table-column>
      <el-table-column label="表单名" align="center" prop="name"/>
      <el-table-column label="开启状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['bpm:form:edit']">
            修改
          </el-button>
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)" v-hasPermi="['bpm:form:query']">
            详情
          </el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['bpm:form:remove']">
            删除
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
    <!-- 表单详情的弹窗 -->
    <el-dialog  :title="title" v-model="detailVisible" title="表单详情" width="800">
      <FormCreate :option="detailData.option" :rule="detailData.rule"  />
    </el-dialog>
  </div>
</template>

<script setup name="bpmForm">
import {delForm, listForm,getForm} from "@/api/bpm/form";
import {setConfAndFields2} from "@/utils/formCreate";
import router from "@/router";
import {getCurrentInstance, reactive, ref} from 'vue'
//导入 form-create
import formCreate from "@form-create/element-ui";
//获取 formCreate 组件
const FormCreate = formCreate.$form();
const {proxy} = getCurrentInstance();
const {sys_normal_disable} = proxy.useDict('sys_normal_disable');
const formList = ref([]);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const total = ref(0);
const title = ref("");
const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
  },
});
const detailVisible = ref(false)
const detailData = ref({
  rule: [],
  option: {}
})
const {queryParams} = toRefs(data);

/** 序号 */
function indexMethod(index){
  return ((queryParams.value.pageNum-1) * queryParams.value.pageSize + index +1)
}

/** 查询工作流的单定义列表 */
function getList() {
  console.log("加载数据");
  loading.value = true;
  listForm(queryParams.value).then(response => {
    formList.value = response.rows;
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


/** 新增按钮操作 */
function handleAdd() {
  router.push({path: "/flowable/bpmfrom/index"});

}

/** 修改按钮操作 */
function handleUpdate(row) {
  const _id = row.id
  router.push({path: "/flowable/bpmfrom/index", query: {id: _id}});
}

/** 详情按钮*/
function handleDetail(row) {
  getForm(row.id).then(respose=>{
    setConfAndFields2(detailData,respose.data.conf, respose.data.fields);
    console.log(detailData.value)
    title.value=respose.data.name;
    // 弹窗打开
    detailVisible.value = true
  })
}
/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除工作流自定义表单数据？').then(function () {
    return delForm(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 初始化 **/
getList()
</script>
