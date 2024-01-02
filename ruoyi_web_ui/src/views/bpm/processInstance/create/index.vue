<template>
  <div class="container">
    <!-- 第一步，通过流程定义的列表，选择对应的流程 -->
    <div v-if="!selectProcessInstance">
      <el-table v-loading="loading" :data="list">
        <el-table-column label="流程名称" align="center" prop="name"/>
        <el-table-column label="流程分类" align="center" >
          <template #default="scope">
            <dict-tag :options="bpm_model_category" :value="scope.row.category"/>
          </template>
        </el-table-column>
        <el-table-column label="流程版本" align="center" prop="version"/>
        <el-table-column label="流程描述" align="center" prop="description"/>
        <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button
                  link
                  type="primary"
                  v-hasPermi="['bpm:process:cancel']"
                  @click="handleSelect(scope.row)"
              >
                选择
              </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 第二步，填写表单，进行流程的提交 -->
    <div v-else>
      <el-card class="box-card">
        <div class="clearfix">
          <span class="el-icon-document">申请信息【{{ selectProcessInstance.name }}】</span>
          <el-button style="float: right" type="primary" @click="selectProcessInstance = undefined">
            <!--          <Icon icon="ep:delete" /> 选择其它流程-->
          </el-button>
        </div>
        <el-col :span="16" :offset="6" style="margin-top: 20px">
          <form-create
              :rule="detailForm.rule"
              v-model:api="fApi"
              :option="detailForm.option"
              @submit="submitForm"
          />
        </el-col>
      </el-card>
      <!-- 流程图预览 -->
<!--      <ProcessInstanceBpmnViewer :bpmn-xml="bpmnXML as any"/>-->
    </div>
  </div>
</template>
<script setup name="BpmProcessInstanceCreate">
import { listLeave, getLeave, delLeave, addLeave, updateLeave } from "@/api/bpm/leave";
import * as DefinitionApi from '@/api/bpm/definition'
import * as ProcessInstanceApi from '@/api/bpm/processInstance'
import {setConfAndFields2} from '@/utils/formCreate';
import {addLeave, delLeave, listLeave, updateLeave} from "@/api/bpm/leave";
//导入 form-create
import formCreate from "@form-create/element-ui";
import ProcessInstanceBpmnViewer from '../detail/ProcessInstanceBpmnViewer.vue'
import {getCurrentInstance, reactive, ref} from 'vue'
import router from "@/router";
import {ElNotification} from "element-plus";
import {listLeave} from "@/api/bpm/leave";
//获取 formCreate 组件
const FormCreate = formCreate.$form();
import {ApiAttrs} from '@form-create/element-ui/types/config'

const {proxy} = getCurrentInstance();
const {bpm_model_category} = proxy.useDict("bpm_model_category");

// ========== 列表相关 ==========
const loading = ref(true) // 列表的加载中
const list = ref([]) // 列表的数据
const queryParams = reactive({
  suspensionState: 1
})

/** 查询列表 */
function getList () {
    loading.value = true;
    listLeave(queryParams.value)
        .then(response => {
      leaveList.value = response.rows;
      total.value = response.total;
      loading.value = false;
    })
        .finally(error=>{loading.value = false;});
  }


// ========== 表单相关 ==========
const bpmnXML = ref(null) // BPMN 数据
const fApi = ref < > ()
const detailForm = ref({
  // 流程表单详情
  rule: [],
  option: {}
})
const selectProcessInstance = ref() // 选择的流程实例

/** 处理选择流程的按钮操作 **/
function handleSelect(row) {
  // 设置选择的流程
  selectProcessInstance.value = row
  // 情况一：流程表单
  if (row.formType == 10) {
    // 设置表单
    setConfAndFields2(detailForm, row.formConf, row.formFields)
    // 加载流程图
     DefinitionApi.getProcessDefinitionBpmnXML(row.id).then(response=>{
       bpmnXML.value =response;
    })
    // 情况二：业务表单
  } else if (row.formCustomCreatePath) {
    router.push({
      path: row.formCustomCreatePath
    })
    // 这里暂时无需加载流程图，因为跳出到另外个 Tab；
  }
}

/** 提交按钮 */
function submitForm(formData) {
  if (!fApi.value || !selectProcessInstance.value) {
    return
  }
  // 提交请求
  fApi.value.btn.loading(true)
  ProcessInstanceApi.createProcessInstance({
    processDefinitionId: selectProcessInstance.value.id,
    variables: formData
  }).then(response => {
    // 提示
    ElNotification({
      title: 'Success',
      message: '流程发起成功',
      type: 'success',
    })
    router.go(-1)
  }).finally(error => {
    fApi.value.btn.loading(false)
  })

}

/** 初始化 */

getList()

</script>
