<template>
  <div class="app-container">
    <!-- 审批任务块 -->
    <el-card
        v-for="(item, index) in runningTasks"
        :key="index"
        v-loading="processInstanceLoading"
        class="box-card"
    >
      <template #header>
        <span class="el-icon-picture-outline">审批任务【{{ item.name }}】</span>
      </template>
      <el-col :offset="6" :span="16">
        <el-form
            :ref="'form' + index"
            :model="auditForms[index]"
            :rules="auditRule"
            label-width="100px"
        >
          <el-form-item v-if="processInstance && processInstance.name" label="流程名">
            {{ processInstance.name }}
          </el-form-item>
          <el-form-item v-if="processInstance && processInstance.startUser" label="流程发起人">
            {{ processInstance.startUser.nickname }}
            <el-tag style="margin-left: 10px" type="info">{{ processInstance.startUser.deptName }}</el-tag>
          </el-form-item>
          <el-form-item label="审批建议" prop="reason">
            <el-input
                v-model="auditForms[index].reason"
                placeholder="请输入审批建议"
                type="textarea"
            />
          </el-form-item>
        </el-form>
        <div style="margin-bottom: 20px; margin-left: 10%; font-size: 14px">
          <el-button type="success" @click="handleAudit(item, true)">
            <el-icon><Select /></el-icon>
            通过
          </el-button>
          <el-button type="danger" @click="handleAudit(item, false)">
            <el-icon><Close /></el-icon>
            不通过
          </el-button>
          <el-button type="primary" @click="handleStopProcess(item)">
            <el-icon><Edit /></el-icon>
            终止
          </el-button>
          <el-button type="primary" @click="openTaskUpdateAssigneeForm(item.id)">
            <el-icon><Edit /></el-icon>
            转办
          </el-button>
<!--          <el-button type="primary" @click="openTaskUpdateDelegateForm(item.id)">-->
<!--            <el-icon><Position /></el-icon>-->
<!--            委派-->
<!--          </el-button>-->
<!--          <el-button type="primary" @click="handleSign(item)">-->
<!--            <el-icon><Plus /></el-icon>-->
<!--            加签-->
<!--          </el-button>-->
<!--          <el-button type="warning" @click="handleBack(item)">-->
<!--            <el-icon><Back /></el-icon>-->
<!--            回退-->
<!--          </el-button>-->
        </div>
      </el-col>
    </el-card>

    <!-- 申请信息 -->
    <el-card v-loading="processInstanceLoading" class="box-card">
      <template #header>
        <span class="el-icon-document">申请信息【{{ processInstance.name }}】</span>
      </template>
      <!-- 情况一：流程表单 -->
      <el-col v-if="processInstance?.processDefinition?.formType === 10" :offset="6" :span="16">
        <FormCreate ref="fApi"
                    v-model="detailForm.value"
                    :option="detailForm.option"
                    :rule="detailForm.rule"/>
      </el-col>
      <!-- 情况二：业务表单 -->
      <div v-if="processInstance?.processDefinition?.formType === 20">
        <BusinessFormComponent :id="processInstance.businessKey"/>
      </div>
    </el-card>

    <!-- 审批记录 -->
    <el-card class="box-card" v-loading="tasksLoad">
      <template #header>
        <span class="el-icon-picture-outline">审批记录</span>
      </template>
      <el-col :offset="4" :span="16">
        <div class="block">
          <el-timeline>
              <el-timeline-item
                  v-for="(item, index) in tasks"
                  :key="index"
                  :icon="getTimelineItemIcon(item)"
                  :type="getTimelineItemType(item)"
              >

                <p style="font-weight: 700;display: flex; align-items: center;">
                    任务：{{ item.name }} 审批状态:{{item.result}}
                    <dict-tag  style="margin-left: 5px" :options="bpm_process_instance_result" :value="item.result"/>
                </p>
                <el-card :body-style="{ padding: '10px' }">
                  <label v-if="item.assigneeUser" style="font-weight: normal; margin-right: 30px;">
                    审批人：{{ item.assigneeUser.name }}
                    <el-tag type="info" size="small">{{ item.assigneeUser.deptName }}</el-tag>
                  </label>
                  <label v-else style="font-weight: normal; margin-right: 30px;">
                    审批人:
                    <span v-for="(user, index) in item.candidateUsers" :key="index">
                  【{{ user.name }}】
                  <el-tag type="info" size="small">{{ user.deptName }}</el-tag>
                  </span>
                  </label>
                  <label style="font-weight: normal" v-if="item.createTime">创建时间：</label>
                  <label style="color:#8a909c; font-weight: normal">{{ parseTime(item.createTime) }}</label>
                  <label v-if="item.endTime" style="margin-left: 30px;font-weight: normal">审批时间：</label>
                  <label v-if="item.endTime" style="color:#8a909c;font-weight: normal"> {{
                      parseTime(item.endTime)
                    }}</label>
                  <label v-if="item.durationInMillis" style="margin-left: 30px;font-weight: normal">耗时：</label>
                  <label v-if="item.durationInMillis" style="color:#8a909c;font-weight: normal">
                    {{ getDate(item.durationInMillis) }} </label>
                  <p v-if="item.reason">
                    <el-tag :type="getTimelineItemType(item)">{{ item.reason }}</el-tag>
                  </p>
                </el-card>
              </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
    </el-card>
    <!--  流程图  -->
    <el-card v-loading="processInstanceLoading" class="box-card">
      <template #header>
        <span class="el-icon-picture-outline">流程图</span>
      </template>
      <MyProcessViewer
          :id="`${id}`"
          key="designer"
          :value="bpmnXML"
          v-bind="bpmnControlForm"
          :activityData="activityList"
          :processInstanceData="processInstance"
          :taskData="tasks"/>
    </el-card>


    <!-- 转派审批人 -->
    <el-dialog v-model="taskUpdateAssigneeDialog" append-to-body title="转派/委派任务" width="600">
      <el-form ref="taskUpdateAssigneeFromRef"
               :model="taskUpdateAssigneeFrom"
               :rules="taskUpdateAssigneeRules"
               label-width="110px"
               >
        <el-form-item label="转派/委派审批人" prop="assigneeUserId">
          <select-more
              search-pld-text="请输入用户名称筛选"
              select-pld-text="请选择接收人"
              v-model="taskUpdateAssigneeFrom.assigneeUserId"
              :showId="false"
              url="/system/user/simpleList"
              >
          </select-more>
        </el-form-item>
        <el-form-item v-if="isDetail" label="委派理由" prop="reason">
          <el-input v-model="taskUpdateAssigneeFrom.reason" placeholder="请输入委派理由" type="textarea"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button :disabled="formLoading" type="primary" @click="taskUpdateAssigneeSubmit()">确 定</el-button>
        <el-button @click="taskUpdateAssigneeDialog = false">取 消</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup name="BpmProcessInstanceDetail">
import {getCurrentInstance, reactive, ref, toRefs} from 'vue'

import * as ProcessInstanceApi from '@/api/bpm/processInstance'
import * as TaskApi from '@/api/bpm/task'
import {setConfAndFields2} from "@/utils/formCreate";
import {getProcessDefinitionBpmnXML} from '@/api/bpm/definition'
import {getDate} from "@/utils/dateUtils";
//导入 form-create
import formCreate from "@form-create/element-ui";
import {registerComponent} from '@/utils/ruoyi'
import MyProcessViewer from '@/components/bpmnProcessDesigner/package/designer/ProcessViewer.vue'
import {getActivityList} from '@/api/bpm/activity'
import {approveTask, rejectTask, stopProcess,doBackStep} from '@/api/bpm/task'
import SelectMore from '@/components/SelectMore/index.vue'
//获取 formCreate 组件
const FormCreate = formCreate.$form();
const {query} = useRoute() // 查询参数
const {proxy} = getCurrentInstance()
const {bpm_process_instance_result} = proxy.useDict("bpm_process_instance_result");
const id = query.id // 流程实例的编号
// 流程实例的加载中
// ========== 流程实例信息 ==========
const processInstanceLoading = ref(false)
const processInstance = ref({}) // 流程实例信息
const bpmnXML = ref('') // BPMN XML
const tasksLoad = ref(true) // 任务的加载中
const tasks = ref([]) // 任务列表
// ========== 审批信息 ==========
const runningTasks = ref([]) // 运行中的任务
const auditData = reactive({
  auditForms: {}, //审批任务的表单
  auditRule: {
    reason: [
      {required: true, message: "审批建议不能为空", trigger: "blur"}
    ],
  }
});
// 自定义流程表单详情
const detailForm = ref({
  rule: [],
  option: {},
  value: {}
})
const {auditRule, auditForms} = toRefs(auditData);
// ========== 申请信息 ==========
const fApi = ref() //
/** 加载流程实例 */
const BusinessFormComponent = ref(null) // 异步组件
// ========== 流程图 ==========
const bpmnControlForm = ref({
  prefix: "flowable"
});
const activityList = ref([]) // 任务列表


// ========== 转派审批人 ==========
/** 转派审批人 */
const taskUpdateAssigneeDialog = ref(false) // 弹窗的是否展示
const formLoading = ref(false) // 表单的加载中
const isDetail = ref(false) // 表单的加载中
const taskUpdateAssigneeFromRef = ref() // 表单的加载中
const taskUpdateAssigneeFrom = ref({
  id: '',
  assigneeUserId: undefined
})
const taskUpdateAssigneeRules = ref({
  assigneeUserId: [{ required: true, message: '接收人不能为空', trigger: 'change' }]
})


/** 获得详情 */
const getDetailData = () =>{
  // 1. 获得流程实例相关
  getProcessInstanceData()
  // 2. 获得流程任务列表（审批记录）
  getTaskListData()
}

// 获得流程实例信息
const getProcessInstanceData = async () => {
  try {
    processInstanceLoading.value = true
    const data = await ProcessInstanceApi.getProcessInstance(query.id)
    if (!data.data) {
      proxy.$message.error('查询不到流程信息！');
      return
    }
    processInstance.value = data.data
    // 设置表单信息
    const processDefinition = data.data.processDefinition
    if (processDefinition.formType === 10) {
      setConfAndFields2(detailForm, processDefinition.formConf, processDefinition.formFields, JSON.parse(processInstance.value.formVariables));
      nextTick().then(() => {
        fApi.value?.fapi?.btn.show(false)
        fApi.value?.fapi?.resetBtn.show(false)
        fApi.value?.fapi?.disabled(true)
      })
    } else {
      BusinessFormComponent.value = registerComponent(data.data.processDefinition.formCustomViewPath)
    }

    // 加载流程图
    getBpmnXml(processDefinition.id);
  } finally {
    processInstanceLoading.value = false
  }
}
const getBpmnXml = async (id) => {
  await getProcessDefinitionBpmnXML(id).then(response => {
    bpmnXML.value = response.data
  })
  await getActivityList({
    processInstanceId: query.id
  }).then(response => {
    console.log("流程列表");
    activityList.value = response.data
  })
}
/** 加载任务列表 */
const getTaskListData = async () => {
  try {
    // 获得未取消的任务
    tasksLoad.value = true
    const data = await TaskApi.getTaskListByProcessInstanceId(id)
    tasks.value = []
    // 1.1 移除已取消的审批
    data.data.forEach((task) => {
      if (task.result !== 4) {
        tasks.value.push(task)
      }
    })
    // 1.2 排序，将未完成的排在前面，已完成的排在后面；
    tasks.value.sort((a, b) => {
      // 有已完成的情况，按照完成时间倒序
      if (a.endTime && b.endTime) {
        return b.endTime - a.endTime
      } else if (a.endTime) {
        return 1
      } else if (b.endTime) {
        return -1
        // 都是未完成，按照创建时间倒序
      } else {
        return b.createTime - a.createTime
      }
    })

    // 获得需要自己审批的任务
    runningTasks.value = []
    auditForms.value = []
    loadRunningTask(tasks.value)
  } finally {
    tasksLoad.value = false
  }
}
/**
 * 设置 runningTasks 中的任务
 */
const loadRunningTask = (tasks) => {
  tasks.forEach((task) => {
    if (task.children) {
      loadRunningTask(task.children)
    }
    // 2.1 只有待处理才需要
    console.log("只有待处理才需要", task.result);
    if (task.result !== 1 && task.result !== 6) {
      return
    }

    task.candidateUsers.forEach(item => {
      if(item.id === task.userId){
        // 2.4 添加到处理任务
        runningTasks.value.push({...task})
      }

    })
    auditForms.value.push({
      reason: ''
    })
  })
}

/** 处理审批通过和不通过的操作 */
const handleAudit = async (task, pass) => {
  // 1.1 获得对应表单
  const index = runningTasks.value.indexOf(task)
  const auditFormRef = proxy.$refs['form' + index][0]
  // 1.2 校验表单
  const elForm = unref(auditFormRef)
  if (!elForm) return
  const valid = await elForm.validate()
  if (!valid) return
console.log("表单校验通过");
  // 2.1 提交审批
  const data = {
    id: task.id,
    reason: auditForms.value[index].reason
  }
  if (pass) {

    await approveTask(data).then(response => {
      proxy.$modal.msgSuccess("审批通过成功");
    })
  } else {
    await rejectTask(data).then(response => {
      proxy.$modal.msgSuccess("审批不通过");
    })
  }

  getDetailData()
}


const handleStopProcess = async (task) => {
  // 1.1 获得对应表单
  const index = runningTasks.value.indexOf(task)
  const auditFormRef = proxy.$refs['form' + index][0]
  // 1.2 校验表单
  const elForm = unref(auditFormRef)
  if (!elForm) return
  const valid = await elForm.validate()
  if (!valid) return
  // 2.1 提交审批
  const data = {
    id: task.id,
    reason: auditForms.value[index].reason
  }
    await stopProcess(data).then(response => {
      proxy.$modal.msgSuccess("终止任务成功");
    })
  getDetailData();
}
const handleBack = async (task) => {
  // 1.1 获得对应表单
  const index = runningTasks.value.indexOf(task)
  const auditFormRef = proxy.$refs['form' + index][0]
  // 1.2 校验表单
  const elForm = unref(auditFormRef)
  if (!elForm) return
  const valid = await elForm.validate()
  if (!valid) return
  // 2.1 提交审批
  const data = {
    id: task.id,
    reason: auditForms.value[index].reason
  }
  await doBackStep(data).then(response => {
    proxy.$modal.msgSuccess("终止任务成功");
  })
  getDetailData();
}

/** 转办按钮 */
const openTaskUpdateAssigneeForm = (id) => {
  taskUpdateAssigneeFrom.value.id=id
  isDetail.value=false
  taskUpdateAssigneeDialog.value=true
}
/** 委派按钮 */
const openTaskUpdateDelegateForm = (id) => {
  taskUpdateAssigneeFrom.value.id=id
  isDetail.value=true
  taskUpdateAssigneeDialog.value=true
}


const taskUpdateAssigneeSubmit = async () => {
  // 校验表单
  console.log("提交表单",taskUpdateAssigneeFrom.value);
  proxy.$refs["taskUpdateAssigneeFromRef"].validate(valid => {
    if (valid) {
      // 提交请求
      formLoading.value = true
      if (isDetail.value){
        //任务 审批后返回审批人
        try {
          console.log("委派 审批后返回审批人");
          TaskApi.delegateTask(taskUpdateAssigneeFrom.value)
        } finally {
          formLoading.value = false
          taskUpdateAssigneeDialog.value=false
        }
      }else {
        //转派 审批后直接下一步
        try {
          console.log("转派 审批后直接下一步");
          TaskApi.updateTaskAssignee(taskUpdateAssigneeFrom.value)
        } finally {
          formLoading.value = false
          taskUpdateAssigneeDialog.value=false
        }
      }
      getDetailData();
    }
  });
}

const getTimelineItemIcon = (item) => {
  if (item.result === 1) {
    return 'el-icon-time';
  }
  if (item.result === 2) {
    return 'el-icon-check';
  }
  if (item.result === 3) {
    return 'el-icon-close';
  }
  if (item.result === 4) {
    return 'el-icon-remove-outline';
  }
  return '';
}
const getTimelineItemType = (item) => {
  if (item.result === 1) {
    return 'primary';
  }
  if (item.result === 2) {
    return 'success';
  }
  if (item.result === 3) {
    return 'danger';
  }
  if (item.result === 4) {
    return 'info';
  }
  return '';
}



getDetailData()
</script>
<style lang="scss">
.my-process-designer {
  height: calc(100vh - 200px);
}

.box-card {
  width: 100%;
  margin-bottom: 20px;
}
</style>
