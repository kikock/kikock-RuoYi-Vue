<template>
  <div class="app-container">
    <el-card class="box-card">
      <template #header>
        <span class="el-icon-document">发起流程</span>
      </template>
      <el-col :span="18" :offset="3">
        <!--        <div class="form-conf" v-if="formOpen">-->
        <!--          <parser :key="new Date().getTime()" :form-conf="formData" @submit="submit" ref="parser" @getData="getData"/>-->
        <!--        </div>-->

        <div class="form-conf" v-if="formOpen">
          <FormCreate :rule="detailForm.rule"  v-model:api="fApi" :option="detailForm.option" @submit="submitForm"/>
        </div>

      </el-col>
    </el-card>
  </div>
</template>

<script setup name="WorkStart">
import {getProcessForm,startProcess} from '@/api/workflow/process'
//导入 form-create
import formCreate from "@form-create/element-ui";
//获取 formCreate 组件
const FormCreate = formCreate.$form();

const router = useRouter();
const {proxy} = getCurrentInstance();
const deployId = ref('')
const definitionId = ref('')
const procInsId = ref('')
const formOpen = ref(false);
const fApi = ref();
const detailForm = ref({
  // 流程表单详情
  rule: [],
  option: {}
})

function initData() {
  console.log("deployId:" + proxy.$route.params.deployId);
  console.log("definitionId:" + proxy.$route.query.definitionId);
  console.log("procInsId:" + proxy.$route.query.procInsId);
  deployId.value = proxy.$route.params && proxy.$route.params.deployId;
  definitionId.value = proxy.$route.query &&  proxy.$route.query.definitionId;
  procInsId.value =proxy.$route.query &&  proxy.$route.query.procInsId;
  getProcessForm({
    definitionId: definitionId.value,
    deployId: deployId.value,
    procInsId: procInsId.value
  }).then(res => {
    if (res.data) {
      detailForm.value.option = JSON.parse(res.data.conf);
      detailForm.value.rule = JSON.parse(res.data.fields);
      // 打开重置按钮
      detailForm.value.option.resetBtn.show=true
      formOpen.value =true
    }
  })
}

/** 提交按钮 */
function submitForm(formData) {
  console.log("发起流程", formData);
  if (formData && definitionId.value) {
    console.log(formData);
    // 启动流程并将表单数据加入流程变量
    startProcess(definitionId.value, formData).then(res => {
      proxy.$modal.msgSuccess(res.msg);
      //跳转到我的流程
      console.log("跳转到我的流程");
      const obj = {path: '/work/own' };
      proxy.$tab.closeOpenPage(obj);
    })
  }
}

initData();
</script>

<style lang="scss" scoped>
.form-conf {
  margin: 15px auto;
  width: 80%;
  padding: 15px;
}
</style>
