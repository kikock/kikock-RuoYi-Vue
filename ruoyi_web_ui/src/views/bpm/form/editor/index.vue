<template>
  <div>
    <ElCard shadow="never">
      <template #header>
        <div class="flex items-center">
          <span class="text-16px font-700">流程自定义表单</span>
        </div>
      </template>
      <div>
        <slot></slot>
      </div>
      <!-- 表单设计器 -->
      <FcDesigner ref="designer" height="800px">
        <template #handle>
          <el-button
              icon="CircleCheck"
              round
              size="small"
              type="primary"
              @click="handleSave"
          >保存
          </el-button>
        </template>
      </FcDesigner>
    </ElCard>
    <!-- 表单保存的弹窗 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存表单" width="600">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="表单名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入表单名"/>
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
      </el-form>
      <template #footer>
        <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script name="BpmFromEditor" setup>
import FcDesigner from '@form-create/designer'
import {getCurrentInstance} from 'vue'
import {encodeConf, encodeFields, setConfAndFields} from '@/utils/formCreate'
import {addForm, getForm, updateForm} from '@/api/bpm/form'

const route = useRoute();
const {proxy} = getCurrentInstance();
const {sys_normal_disable} = proxy.useDict('sys_normal_disable');
const designer = ref() // 表单设计器
const dialogVisible = ref(false) // 弹窗是否展示
const formLoading = ref(false) // 表单的加载中：提交的按钮禁用

const data = reactive({
  form: {
    id: null,
    name: null,
    status: 0,
  },
  rules: {
    name: [
      {required: true, message: "表单名不能为空", trigger: "blur"}
    ],
    status: [
      {required: true, message: "开启状态不能为空", trigger: "change"}
    ],
  }
});

const {form, rules} = toRefs(data);


const formRef = ref() // 表单 Ref

/** 处理保存按钮 */
function handleSave() {
  dialogVisible.value = true
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    name: null,
    status: null,
    conf: null,
    fields: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("formRef");
}

/** 提交表单 */
function submitForm() {

  const data = form.value
  data.conf = encodeConf(designer) // 表单配置
  data.fieldsArr = encodeFields(designer) // 表单字段
  // 提交请求
  formLoading.value = true
  // 校验表单
  proxy.$refs["formRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        data.fields = '';
        updateForm(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          close();
        });
      } else {
        addForm(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          close();
        });
      }
    }
  });
  dialogVisible.value = false
}

/** 关闭按钮 */
function close() {
  const obj = {path: "/flowable/bpm/processform"};
  proxy.$tab.closeOpenPage(obj);
}


/** 初始化 **/
function initData() {
  reset()
  const id = route.query && route.query.id;
  if (id) {
    getForm(id).then(response => {
      form.value = response.data;
      setConfAndFields(designer, response.data.conf, response.data.fields)
    });
  }
}

initData()
</script>
<style>
._fc-l, ._fc-m, ._fc-r {
  border-top: 1px solid #ececec;
  box-sizing: border-box;
  background-color: white;
}
</style>
