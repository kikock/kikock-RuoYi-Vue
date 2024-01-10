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
            <el-tag size="small" type="info">{{ processInstance.startUser.deptName }}</el-tag>
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
            <!--            <Icon icon="ep:select" />-->
            通过
          </el-button>
          <el-button type="danger" @click="handleAudit(item, false)">
            <!--            <Icon icon="ep:close" />-->
            不通过
          </el-button>
          <el-button type="primary" @click="openTaskUpdateAssigneeForm(item.id)">
            <!--            <Icon icon="ep:edit" />-->
            转办
          </el-button>
          <el-button type="primary" @click="handleDelegate(item)">
            <!--            <Icon icon="ep:position" />-->
            委派
          </el-button>
          <el-button type="primary" @click="handleSign(item)">
            <!--            <Icon icon="ep:plus" />-->
            加签
          </el-button>
          <el-button type="warning" @click="handleBack(item)">
            <!--            <Icon icon="ep:back" />-->
            回退
          </el-button>
        </div>
      </el-col>
    </el-card>


  </div>
</template>
<script setup name="BpmProcessInstanceDetail">
import {getCurrentInstance, ref} from 'vue'
import {getProcessInstance} from '@/api/bpm/processInstance'

const {query} = useRoute() // 查询参数
const {proxy} = getCurrentInstance()
// 当前登录用户id
// 流程实例的加载中
// ========== 流程实例信息 ==========
const processInstanceLoading = ref(false)
const processInstance = ref({}) // 流程实例信息
const bpmnXML = ref('') // BPMN XML
const tasksLoad = ref(true) // 任务的加载中
const runningTasks = ref([]) // 任务列表

// ========== 审批信息 ==========


// ========== 申请信息 ==========

function getDetailData() {
  // 获得流程实例相关
  processInstanceLoading.value = true;
  if (query.id) {
    console.log("开始加载数据!");
    getProcessInstance({
           id: query.id
        }).then(response => {
      console.log("查询到流程信息", response);
      if (!response.data) {
        proxy.$message.error('查询不到流程信息！');
        return;
      }
      console.log(response);
      // 设置流程信息
        processInstance.value = response.data;
      //
      //   //将业务表单，注册为动态组件
        const path = processInstance.value.processDefinition.formCustomViewPath;
        console.log("注册业务表单,动态组件");
      // 工厂函数执行 resolve 回调
      Vue.component('async-biz-form-component', function (resolve) {
        // 这个特殊的 `require` 语法将会告诉 webpack
        // 自动将你的构建代码切割成多个包, 这些包
        // 会通过 Ajax 请求加载
        require([`@/views${path}`], resolve);
      })
      //
      //   // 设置表单信息
      //   if (this.processInstance.processDefinition.formType === 10) {
      //     this.detailForm = {
      //       ...JSON.parse(this.processInstance.processDefinition.formConf),
      //       disabled: true, // 表单禁用
      //       formBtns: false, // 按钮隐藏
      //       fields: decodeFields(this.processInstance.processDefinition.formFields)
      //     }
      //     // 设置表单的值
      //     this.detailForm.fields.forEach(item => {
      //       const val = this.processInstance.formVariables[item.__vModel__]
      //       if (val) {
      //         item.__config__.defaultValue = val
      //       }
      //     });
      //   }
      //
      //   // 加载流程图
      //   getProcessDefinitionBpmnXML(this.processInstance.processDefinition.id).then(response => {
      //     this.bpmnXML = response.data
      //   });
      //   // 加载活动列表
      //   getActivityList({
      //     processInstanceId: this.processInstance.id
      //   }).then(response => {
      //     this.activityList = response.data;
      //   });
      //
      //   // 取消加载中
      //   this.processInstanceLoading = false;
      // });
      // // 获得流程任务列表（审批记录）
      // this.tasksLoad = true;
      // this.runningTasks = [];
      // this.auditForms = [];
      // getTaskListByProcessInstanceId(this.id).then(response => {
      //   // 审批记录
      //   this.tasks = [];
      //   // 移除已取消的审批
      //   response.data.forEach(task => {
      //     if (task.result !== 4) {
      //       this.tasks.push(task);
      //     }
      //   });
      //   // 排序，将未完成的排在前面，已完成的排在后面；
      //   this.tasks.sort((a, b) => {
      //     // 有已完成的情况，按照完成时间倒序
      //     if (a.endTime && b.endTime) {
      //       return b.endTime - a.endTime;
      //     } else if (a.endTime) {
      //       return 1;
      //     } else if (b.endTime) {
      //       return -1;
      //       // 都是未完成，按照创建时间倒序
      //     } else {
      //       return b.createTime - a.createTime;
      //     }
      //   });
      //
      //   // 需要审核的记录
      //   const userId = store.getters.userId;
      //   this.tasks.forEach(task => {
      //     if (task.result !== 1) { // 只有待处理才需要
      //       return;
      //     }
      //     if (!task.assigneeUser || task.assigneeUser.id !== userId) { // 自己不是处理人
      //       return;
      //     }
      //     this.runningTasks.push({...task});
      //     this.auditForms.push({
      //       reason: ''
      //     })
      //   });
      //
      //   // 取消加载中
      //   this.tasksLoad = false;
    });
  }
  processInstanceLoading.value = false;
}

getDetailData()
</script>
