<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="表单名称" prop="categoryName">
        <el-input
            v-model="queryParams.formName"
            placeholder="请输入表单名称"
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="primary"-->
<!--            plain-->
<!--            icon="Plus"-->
<!--            @click="handleAdd"-->
<!--            v-hasPermi="['workflow:form:add']"-->
<!--        >新增-->
<!--        </el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAddNew"
            v-hasPermi="['workflow:form:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['workflow:form:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" fit :data="formList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="表单主键" align="center" prop="formId"/>-->
      <el-table-column label="表单名称" align="center" prop="formName"/>
      <el-table-column label="表单类型" align="center" prop="formType">
        <template #default="scope">
          <dict-tag :options="bpm_model_form_type" :value="scope.row.formType"/>
        </template>
      </el-table-column>
      <el-table-column label="开启状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)"
                     v-hasPermi="['workflow:form:edit']">
            详情
          </el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['workflow:form:edit']">
            修改
          </el-button>
          <el-button
              link
              type="primary"
              icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['workflow:form:remove']"
          >删除
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
    <!-- 添加或修改流程表单对话框 -->
    <el-dialog :title="title" v-model="open" width="40%" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item  v-if="!form.formId" label="表单类型" prop="formType">
          <el-radio-group v-model="form.formType">
            <el-radio v-for="dict in bpm_model_form_type" :key="parseInt(dict.value)" :label="parseInt(dict.value)">
              {{dict.label}} {{form.formId}}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <div v-if="form.formType === 1">
        <el-form-item  label="表单名称" prop="formName">
          <el-input v-model="form.formName" placeholder="请输入表单名称"/>
        </el-form-item>
        <el-form-item  label="表单提交路由" prop="formCustomCreatePath" >
          <el-input v-model="form.formCreatePath" placeholder="请输入表单提交路由" style="width: 95%" />
          <el-tooltip effect="dark" content="自定义表单的提交路径，使用 Vue 的路由地址，例如：bpm/oa/leave/create" placement="top">
            <el-icon class="ml10">
              <InfoFilled/>
            </el-icon>
          </el-tooltip>
        </el-form-item>
        <el-form-item  label="表单查看路由" prop="formCustomViewPath">
          <el-input v-model="form.formViewPath" placeholder="请输入表单查看路由" style="width: 95%"  />
          <el-tooltip effect="dark" content="自定义表单的查看路径，使用 Vue 的路由地址，例如：bpm/oa/leave/view" placement="top">
            <el-icon class="ml10">
              <InfoFilled/>
            </el-icon>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in sys_normal_disable"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >
              {{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" type="textarea"/>
        </el-form-item>
        </div>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button v-if="form.formType === 0"  :loading="buttonLoading" type="primary" @click="submitForm">表单设计</el-button>
          <el-button v-if="form.formType === 1"  :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!--表单配置详情-->
    <!-- 表单详情的弹窗 -->
    <el-dialog   v-model="formConfOpen" title="表单详情" width="800">
      <FormCreate v-if="formType === 0 " :option="formData.option" :rule="formData.rule"  />
      <div v-if="formType === 1">
        <BusinessFormComponent/>
      </div>
    </el-dialog>
  </div>
</template>


<script setup name="flowForm">
import {addForm, delForm, listForm, getForm,updateForm} from "@/api/workflow/form";
import Editor from '@/components/Editor';
import {reactive, ref} from 'vue'
import router from "@/router";
import {setMyConfAndFields} from '@/utils/formCreate'
//导入 form-create
import formCreate from "@form-create/element-ui";
import {registerComponent} from '@/utils/ruoyi'
//获取 formCreate 组件
const FormCreate = formCreate.$form();
const {proxy} = getCurrentInstance();
const {sys_normal_disable,bpm_model_form_type} = proxy.useDict('sys_normal_disable','bpm_model_form_type');
// 表格数据
const formList = ref([]);
// 按钮loading
const buttonLoading = ref(true);
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

const formType = ref();

/** 加载流程实例 */
const BusinessFormComponent = ref(null) // 异步组件

// 是否显示弹出表单层
const formConfOpen = ref(false);
// 是否显示弹出表单数据
const formData = ref({
  rule: [],
  option: {}
})

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    formName: undefined,
  },
  rules: {
    categoryName: [
      {formName: true, message: "表单名称不能为空", trigger: "blur"}
    ],
    code: [
      {content: true, message: "表单内容不能为空", trigger: "blur"}
    ]
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询流程表单列表 */
function getList() {
  loading.value = true;
  listForm(queryParams.value).then(response => {
    formList.value = response.rows;
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
    formId: null,
    formName: null,
    status:0,
    formType: 0,
    formCreatePath: null,
    formViewPath: null,
    remark: null
  };
  buttonLoading.value =false
  proxy.resetForm("formRef");
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
  ids.value = selection.map(item => item.formId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 表单配置信息 */
function handleDetail(row) {
  console.log("查看表单",row);
  if(row.formType === 0){
    getForm(row.formId).then(res=>{
    setMyConfAndFields(formData,res.data.conf, res.data.fields,res.data.name);
    title.value=res.data.name;
      // 弹窗打开
      formType.value =res.data.formType
      formConfOpen.value = true
    })
  }else if(row.formType === 1){
    BusinessFormComponent.value = registerComponent(row.formViewPath)
    // 弹窗打开
    formType.value =row.formType
    formConfOpen.value = true
  }
}
/** 新增按钮操作 */
function handleAddNew() {
  // 初始化参数
  reset()
 title.value="新增流程表单"
  open.value = true
}
/** 新增按钮操作 */
function handleAdd() {
  router.push({path: "/workflow/bpmfrom/index"});
}

/** 修改按钮操作 */
function handleUpdate(row) {
  console.log(row);
  if(row.formType === 0){
    const _id = row.formId
    router.push({path: "/workflow/bpmfrom/index", query: {formId: _id}});
  }else if(row.formType === 1){
    reset();
    form.value.formId = row.formId
    form.value.formName = row.formName
    form.value.formType = row.formType
    form.value.formCreatePath = row.formCreatePath
    form.value.formViewPath = row.formViewPath
    form.value.remark = row.remark
    title.value="更新流程表单"
    open.value = true
  }


}

/** 提交按钮 */
function submitForm() {
  if(form.value.formType === 0){
    router.push({path: "/workflow/bpmfrom/index"});
  }
  if(form.value.formType === 1){
    proxy.$refs["formRef"].validate(valid => {
      if (valid) {
        if (form.value.formId != null) {
          updateForm(form.value).then(response => {
            proxy.$modal.msgSuccess("修改表单成功");
            open.value = false;
            getList();
          });
        } else {
          addForm(form.value).then(response => {
            proxy.$modal.msgSuccess("新增表单成功");
            open.value = false;
            getList();
          });
        }
      }
    });
  }

}

/** 删除按钮操作 */
function handleDelete(row) {
  const formIds = row.formId || ids.value;
  proxy.$confirm('是否确认删除选择数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    return delForm(formIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  })
}

/** 导出按钮操作 */
function handleExport() {
  proxy.$confirm('是否确认导出所有流程表单数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    proxy.download('/workflow/form/export', {
      ...queryParams.value
    }, `form_${new Date().getTime()}.xlsx`)
  })
}
getList()
</script>

<style lang="scss" scoped>
.test-form {
  margin: 15px auto;
  width: 800px;
  padding: 15px;
}
</style>
