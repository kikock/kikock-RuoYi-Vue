<template>
  <div class="app-container">
    <el-tabs
        v-model="activeName"
        type="border-card"
        @tab-change="handleTabsClick"
        tab-position="top" >

      <el-tab-pane label="任务办理" name="approval" v-if="processed === true" >
        <el-card v-loading="formOpen" class="box-card" >
          <template #header>
            <span class="el-icon-document">{{detailForm.nodeName}}  【{{ detailForm.title }}】</span>
          </template>
          <!-- 情况一：自定义流程表单 -->
          <el-col v-if="detailForm.formType === 0"  :offset="6" :span="16" >
            <form-create
                v-model:api="fcApi"
                v-model="detailForm.value"
                :option="detailForm.option"
                :rule="detailForm.rule"
                @mounted="onMountedFormCreateApproval" />
          </el-col>
          <el-col v-if="detailForm.formType === 1" :offset="6" :span="16" >
              <BusinessFormComponent/>
          </el-col>
        </el-card>
        <el-card class="box-card">
          <template #header>
            <span class="el-icon-document">审批流程</span>
          </template>
          <el-row>
            <el-col :span="20" :offset="2">
              <el-form ref="taskFormRef" :model="taskForm" :rules="rules" label-width="120px">
                <el-form-item label="审批意见" prop="comment">
                  <el-input v-model="taskForm.comment" type="textarea" :rows="5"  placeholder="请输入 审批意见"></el-input>
                </el-form-item>
                <el-form-item label="抄送人" prop="copyUserIds">
                  <el-tag
                      :key="index"
                      v-for="(item, index) in copyUser"
                      closable
                      :disable-transitions="false"
                      @close="handleClose('copy', item)">
                    {{ item.nickName }}
                  </el-tag>
                  <el-button class="button-new-tag" type="primary" icon="plus" circle
                             @click="onSelectCopyUsers"/>
                </el-form-item>
                <el-form-item label="指定审批人" prop="copyUserIds">
                  <el-tag
                      :key="index"
                      v-for="(item, index) in nextUser"
                      closable
                      :disable-transitions="false"
                      @close="handleClose('next', item)">
                    {{ item.nickName }}
                  </el-tag>
                  <el-button class="button-new-tag" type="primary" icon="plus"  circle
                             @click="onSelectNextUsers"/>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
          <el-row :gutter="10" type="flex" justify="center">
            <el-col :span="1.5">
              <el-button icon="check" type="success" @click="handleComplete">通过</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="ChatLineSquare" type="primary" @click="handleDelegate">委派</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="Pointer" type="success" @click="handleTransfer">转办</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="RefreshLeft" type="warning" @click="handleReturn">退回</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="close" type="danger" @click="handleReject">拒绝</el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-tab-pane>
      <!-- 表单信息 -->
      <el-tab-pane label="表单信息" name="form"  >
        <el-card v-loading="formOpen" class="box-card" v-for="(formInfo, index) in processFormList" :key="index" >
          <template #header>
            <span class="el-icon-document">{{formInfo.nodeName}}  【{{ formInfo.title }}】</span>
          </template>
          <!-- 情况一：自定义流程表单 -->
          <el-col v-if="formInfo.formType === 0"  :offset="6" :span="16" >
            <form-create
                v-model:api="fApi"
                v-model="formInfo.value"
                :option="formInfo.option"
                :rule="formInfo.rule"
                @mounted="onMountedFormCreate" />
          </el-col>
          <el-col v-if="formInfo.formType === 1" :offset="6" :span="16" >
            <component :is="formInfo.BusinessFormComponent" />
          </el-col>


        </el-card>
      </el-tab-pane>

      <!--    流程历史  -->
      <el-tab-pane label="流转记录" name="record">
        <el-card v-loading="recordOpen" class="box-card">
          <template #header>
            <span class="el-icon-document">流转记录</span>
          </template>
          <el-col :span="20" :offset="2">
            <div class="block">
              <el-timeline>
                <el-timeline-item v-for="(item,index) in historyProcNodeList" :key="index" :icon="setIcon(item.endTime)"
                                  :color="setColor(item.endTime)">
                  <p style="font-weight: 700">{{ item.activityName }}</p>
                  <el-card v-if="item.activityType === 'startEvent'" class="box-card" shadow="hover">
                    {{ item.assigneeName }} 在 {{ item.createTime }} 发起流程
                  </el-card>
                  <el-card v-if="item.activityType === 'userTask'" class="box-card" shadow="hover">
                    <el-descriptions :column="5" >
                      <el-descriptions-item label="实际办理人">{{ item.assigneeName || '-' }}</el-descriptions-item>
                      <el-descriptions-item label="候选办理人">{{ item.candidate || '-' }}</el-descriptions-item>
                      <el-descriptions-item label="接收时间">{{ item.createTime || '-' }}</el-descriptions-item>
                      <el-descriptions-item label="办结时间">{{ item.endTime || '-' }}</el-descriptions-item>
                      <el-descriptions-item label="耗时">{{ item.duration || '-' }}</el-descriptions-item>
                    </el-descriptions>
                    <div v-if="item.commentList && item.commentList.length > 0">
                      <div v-for="(comment, index) in item.commentList" :key="index">
                        <el-divider content-position="left">
                          <el-row :gutter="20">
                            <el-col :span="5">
                              <div/>
                              <dict-tag :options="wf_comment_type" :value="comment.type"/>
                            </el-col>
                            <el-col :span="6">
                              <div/>
                              <el-tag type="info" effect="plain">{{ comment.time }}</el-tag>
                            </el-col>
                          </el-row>
                        </el-divider>
                        <span>{{ comment.fullMessage }}</span>
                      </div>
                    </div>
                  </el-card>
                  <el-card v-if="item.activityType === 'endEvent'" class="box-card" shadow="hover">
                    {{ item.createTime }} 结束流程
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-col>
        </el-card>

      </el-tab-pane>

      <el-tab-pane label="流程跟踪" name="track">
        <el-card v-loading="trackOpen" class="box-card">
          <template #header>
            <span class="el-icon-picture-outline">流程图</span>
          </template>
          <my-process-viewer key="designer" :value="bpmnXML" v-bind="bpmnControlForm"
                             :flowWorkData="processData"
          />
        </el-card>


      </el-tab-pane>
    </el-tabs>

<!--    选择制定审批人和抄送人-->
    <el-dialog :title="userData.title"  v-model="userData.open"  width="60%" append-to-body>
      <el-row type="flex" :gutter="20">
        <!--部门数据-->
        <el-col :span="6" :xs="24">
          <el-card class="box-card" shadow="hover" style="height: 100%">
            <template #header>
              <span class="el-icon-document">部门列表</span>
            </template>
          <div class="head-container">
            <el-input
                v-model="deptName"
                placeholder="请输入部门名称"
                clearable
                prefix-icon="Search"
                style="margin-bottom: 20px"
            />
          </div>
          <div class="head-container">
            <el-tree
                :data="deptOptions"
                :props="{ label: 'label', children: 'children' }"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="deptTreeRef"
                node-key="id"
                highlight-current
                default-expand-all
                @node-click="handleNodeClick"
            />
          </div>
          </el-card>
        </el-col>
        <!--部门数据-->
        <el-col :span="18">
          <el-card class="box-card" shadow="hover" style="height: 100% ; width: 100%">
            <template #header>
              <span class="el-icon-document">用户列表</span>
            </template>
          <el-table ref="userTable"
                    :key="userData.type"
                    height="500"
                    v-loading="userLoading"
                    :data="userList"
                    highlight-current-row
                    @current-change="changeCurrentUser"
                    @selection-change="handleSelectionChange">
            <el-table-column v-if="userData.type === 'copy' || userData.type === 'next'" width="55" type="selection" />
            <el-table-column label="用户名" align="center" prop="nickName" />
            <el-table-column label="手机" align="center" prop="phonenumber" />
            <el-table-column label="部门" align="center" prop="dept.deptName" />
          </el-table>
          <pagination
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getList"
          />
          </el-card>
        </el-col>
      </el-row>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitUserData">确 定</el-button>
          <el-button @click="userData.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!--退回流程-->
  </div>
</template>

<script setup name="WorkDetail">
import {detailProcess} from '@/api/workflow/process'
import {setMyConfAndFields, setMyConfAndFields2} from "@/utils/formCreate";
import {complete, delegate, rejectTask, returnList, returnTask, transfer} from '@/api/workflow/task'
import { listUser,deptTreeSelect } from "@/api/system/user";
import {reactive} from 'vue'
//导入 form-create
import FormCreate from "@form-create/element-ui";
import MyProcessViewer from '@/components/workflow/package/designer/ProcessViewer.vue'
import {registerComponent} from '@/utils/ruoyi'
//获取 formCreate 组件
const formCreate = FormCreate.$form();
const {proxy} = getCurrentInstance();
const {wf_comment_type} = proxy.useDict('wf_comment_type');
const router = useRouter();

const processData = ref(null);
// tabs 显示状态
const processed = ref(false);
const  activeName = ref('form');

//############任务办理参数###############
//任务办理表单状态
const taskFormOpen = ref(false);
// 流程实例信息
const processInstance = ref({})
const approvalType = ref(false);
//按钮权限设置
const fApi = ref()
// 自定义流程表单详情
const detailForm = ref({
  formType: 0,
  rule: [],
  option: {},
  value: {},
  title:'',
  nodeName:''
})
const fcApi = ref()

//############表单信息参数###############
/** 加载流程实例 业务表单组件*/
const BusinessFormComponent = ref(null)
// 自定义表单和业务表单标识
const formType = ref(false);
//表单显示状态
const formOpen = ref(true);
// 表单名称
const processFormList = ref([]) // 流程变量数据


//############流程记录###############
// 流传信息展示
const recordOpen = ref(true);
const historyProcNodeList = ref([])


//############流程图###############
//流程图显示状态
const trackOpen = ref(true);
const bpmnXML = ref('') // BPMN XML
const bpmnControlForm = ref({
  prefix: "flowable"
});

//############审批用户,抄送用户######
const deptName = ref("");
const deptOptions = ref(undefined);
const copyUser = ref([])
const nextUser = ref([])
const userMultipleSelection = ref([])
const userDialogTitle = ref('')
const userOpen = ref(false)
const userLoading = ref(false)
const userList = ref(undefined);
const currentUserId = ref(null);
const total = ref(0);
const data = reactive({
  taskForm: {
    comment: "", // 意见内容
    procInsId: "", // 流程实例编号
    taskId: "",// 流程任务编号
    copyUserIds: "", // 抄送人Id
    vars: "",
    targetKey: ""
  },
  rules: {
    comment: [{required: true, message: '请输入审批意见', trigger: 'blur'}],
  },
  // 查询参数
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deptId: 100
  },
  finishedInfo: {
    finishedSequenceFlowSet: [],
    finishedTaskSet: [],
    unfinishedTaskSet: [],
    rejectedTaskSet: []
  },
  userData: {
    title: '',
    type: '',
    open: false,
  }
});
const {queryParams, taskForm, rules,finishedInfo,userData} = toRefs(data);

function initData() {
  taskForm.value.procInsId = proxy.$route.params && proxy.$route.params.procInsId;
  taskForm.value.taskId = proxy.$route.query && proxy.$route.query.taskId;
  processed.value = proxy.$route.query && eval(proxy.$route.query.processed || false);
  activeName.value = processed.value === true ? 'approval' : 'form'
  console.log("taskForm:", taskForm.value);
  console.log("processed:" + processed.value);
  // 流程任务重获取变量表单
  getProcessDetails(taskForm.value.procInsId, taskForm.value.taskId);
  // this.loadIndex = this.taskForm.procInsId;
}

// 关闭标签
function handleClose(type, tag) {
  let userObj = userMultipleSelection.value.find(item => item.userId === tag.id);
  userMultipleSelection.value.splice(userMultipleSelection.value.indexOf(userObj), 1);
  if (type === 'copy') {
    copyUser.value = userMultipleSelection.value;
    // 设置抄送人ID
    if (copyUser.value && copyUser.value.length > 0) {
      const val = copyUser.value.map(item => item.id);
      taskForm.value.copyUserIds = val instanceof Array ? val.join(',') : val;
    } else {
      taskForm.value.copyUserIds = '';
    }
  } else if (type === 'next') {
    nextUser.value = userMultipleSelection.value;
    // 设置抄送人ID
    if (nextUser.value && nextUser.value.length > 0) {
      const val = nextUser.value.map(item => item.id);
      taskForm.value.nextUserIds = val instanceof Array ? val.join(',') : val;
    } else {
      taskForm.value.nextUserIds = '';
    }
  }
}

/** 流程变量赋值 */
function handleCheckChange(val) {
  if (val instanceof Array) {
    taskForm.value.values = {
      "approval": val.join(',')
    }
  } else {
    taskForm.value.values = {
      "approval": val
    }
  }
}

function getProcessDetails(procInsId, taskId) {
  const params = {procInsId: procInsId, taskId: taskId}
  detailProcess(params).then(res => {
    console.log("获取审批流程数据",res.data);
    const data = res.data;
    processData.value = data;
    //审批节点
    detailForm.value.nodeName = 0
    //审批表单内容
    if (data.newTaskFormData) {
      //审批节点
      detailForm.value.nodeName = data.newTaskFormData.nodeName;
      //表单名称
      detailForm.value.title = data.newTaskFormData.formName
      if(data.newTaskFormData.formType === 0 ){
        approvalType.value = true
        detailForm.value.formType = 0
        setMyConfAndFields(detailForm, data.newTaskFormData.conf, data.newTaskFormData.fields, data.newTaskFormData.variables,data.newTaskFormData.formName);
        detailForm.value.option.form.disabled =true
        detailForm.value.option.submitBtn.show=false
        detailForm.value.option.resetBtn.show=false
      }else if(data.newTaskFormData.formType === 1 ){
        approvalType.value = true
        detailForm.value.formType = 1
        BusinessFormComponent.value = registerComponent(data.newTaskFormData.formViewPath)
      }

    }
    //历史审批表单
    if (data.processFormAll && data.processFormAll.length>0) {
      data.processFormAll.forEach(item =>{
        const detailData = {
          rule: [],
          option: {},
          value: {},
          title: '',
          BusinessFormComponent: null,
          formType : 0,
          nodeName:'',
        }
        //审批节点
        detailData.nodeName = item.nodeName;
        //表单名称
        detailData.title = item.formName
        if(item.formType === 0 ) {
          detailData.formType = 0
          setMyConfAndFields2(detailData, item.conf, item.fields, item.variables, item.formName);
          processFormList.value.push(detailData);
        }else if(item.formType === 1 ){
          /** 加载流程实例 业务表单组件*/
          detailData.formType = 1
          detailData.BusinessFormComponent = registerComponent(item.formViewPath)
          processFormList.value.push(detailData);

        }
      })
    }
    formOpen.value = false
  })
}

function onSelectCopyUsers() {
  userMultipleSelection.value = copyUser.value;
  onSelectUsers('添加抄送人', 'copy')
}

function onSelectNextUsers() {
  userMultipleSelection.value =nextUser.value;
  onSelectUsers('指定审批人', 'next')
}

function onSelectUsers(title, type) {
  userData.value.title = title;
  userData.value.type = type;
  getDeptTree()
  getList()
  userData.value.open = true;
}
// 节点单击事件
function handleNodeClick(data) {
  queryParams.value.deptId = data.id;
  getList();
}
/** 查询用户列表 */
function getList() {
  // 获取列表
  userLoading.value = true;
  listUser(queryParams.value).then(res => {
    userLoading.value = false;
    userList.value = res.rows;
    total.value = res.total;
    toggleSelection(userMultipleSelection.value)
  });
}
function toggleSelection(selection) {
  console.log("toggleSelection:",selection);
  // if (selection && selection.length > 0) {
  //   this.$nextTick(()=> {
  //     selection.forEach(item => {
  //       let row = this.userList.find(k => k.userId === item.userId);
  //       this.$refs.userTable.toggleRowSelection(row);
  //     })
  //   })
  // } else {
  //   this.$nextTick(() => {
  //     this.$refs.userTable.clearSelection();
  //   });
  // }
}
// 筛选节点
function filterNode(value, data) {
  if (!value) return true;
  return data.label.indexOf(value) !== -1;
}
/** 通过任务 */
function handleComplete() {
  proxy.$refs["taskFormRef"].validate(valid => {
    if (valid) {
      console.log(taskForm.value);
      complete(taskForm.value).then(response => {
        proxy.$modal.msgSuccess(response.msg);
        goBack();
      });
    }
  });

}

/** 委派任务 */
function handleDelegate() {
  proxy.$refs["taskFormRef"].validate(valid => {
    if (valid) {
      userData.value.type = 'delegate';
      userData.value.title = '委派任务'
      userData.value.open = true;
      getDeptTree();
      getList()
    }
  })
}

/** 转办任务 */
function handleTransfer() {
  proxy.$refs["taskFormRef"].validate(valid => {
    if (valid) {
      userData.value.type = 'transfer';
      userData.value.title = '转办任务'
      userData.value.open = true;
      getDeptTree();
      getList()
    }
  })
}
/** 可退回任务列表 */
function handleReturn() {
  proxy.$refs['taskFormRef'].validate(valid => {
    if (valid) {
      returnList(taskForm.value).then(res => {
        goBack();
      })
    }
  });

}
/** 拒绝任务 */
function handleReject() {
  proxy.$refs["taskFormRef"].validate(valid => {
    if (valid) {
      proxy.$modal.confirm('拒绝审批单流程会终止，是否继续？').then(function () {
        return rejectTask(taskForm.value);
      }).then(res => {
        proxy.$modal.msgSuccess(res.msg);
        goBack();
      });
    }
  });
}

function changeCurrentUser(val) {
  console.log(val);
  currentUserId.value = val.userId
}

/** 返回页面 */
function goBack() {
  userData.value.open = false;
  const obj = {path: '/work/todo'};
  proxy.$tab.closeOpenPage(obj);


}

/** 接收子组件传的值 */
function getData(data) {
  if (data) {
    const variables = [];
    data.fields.forEach(item => {
      let variableData = {};
      variableData.label = item.__config__.label
      // 表单值为多个选项时
      if (item.__config__.defaultValue instanceof Array) {
        const array = [];
        item.__config__.defaultValue.forEach(val => {
          array.push(val)
        })
        variableData.val = array;
      } else {
        variableData.val = item.__config__.defaultValue
      }
      variables.push(variableData)
    })
    this.variables = variables;
  }
}

function submitUserData() {
  let type = userData.value.type;
  if (type === 'copy' || type === 'next') {
    if (!userMultipleSelection.value || userMultipleSelection.value.length <= 0) {
      proxy.$modal.msgError("请选择用户");
      return false;
    }
    let userIds = userMultipleSelection.value.map(k => k.userId);
    if (type === 'copy') {
      // 设置抄送人ID信息
      copyUser.value = userMultipleSelection.value;
      taskForm.value.copyUserIds = userIds instanceof Array ? userIds.join(',') : userIds;
    } else if (type === 'next') {
      // 设置下一级审批人ID信息
      nextUser.value = userMultipleSelection.value;
      taskForm.value.nextUserIds = userIds instanceof Array ? userIds.join(',') : userIds;
    }
    userData.value.open = false;
  } else {
    if (!taskForm.value.comment) {
      proxy.$modal.msgError("请输入审批意见");
      return false;
    }
    if (!currentUserId.value) {
      console.log("单选用户:",currentUserId.value);
      proxy.$modal.msgError("请选择用户");
      return false;
    }
    taskForm.value.userId = currentUserId.value;
    if (type === 'delegate') {
      delegate(taskForm.value).then(res => {
        proxy.$modal.msgSuccess(res.msg);
        goBack();
      });
    }
    if (type === 'transfer') {
      transfer(taskForm.value).then(res => {
       proxy.$modal.msgSuccess(res.msg);
        goBack();
      });
    }
  }

}
// 多选框选中数据
function handleSelectionChange(selection) {
  userMultipleSelection.value = selection
}


/** 提交退回任务 */
function submitReturn() {
  this.$refs["taskForm"].validate(valid => {
    if (valid) {
      if (!this.taskForm.targetKey) {
        this.$modal.msgError("请选择退回节点！");
      }
      returnTask(this.taskForm).then(res => {
       proxy.$modal.msgSuccess(res.msg);
        this.goBack()
      });
    }
  });
}
function handleTabsClick(e){
    if (e==='track'){
      console.log("点击流程图");
      //流程图
      bpmnXML.value = processData.value.bpmnXml;
      //流程记录
      historyProcNodeList.value = processData.value.historyProcNodeList;
      finishedInfo.value = processData.value.flowViewer;
      trackOpen.value = false
    }else if(e==="form"){
      console.log("点击表单");
      //表单内容查看
      formType.value = true
      formOpen.value = false
    }else if(e==="record"){
      console.log("点击流程记录");
      //流程记录
      historyProcNodeList.value = processData.value.historyProcNodeList;
      recordOpen.value = false
  }
}
function setIcon(val) {
  if (val) {
    return "el-icon-check";
  } else {
    return "el-icon-time";
  }
}

function setColor(val) {
  if (val) {
    return "#2bc418";
  } else {
    return "#b3bdbb";
  }
}
/** 查询部门下拉树结构 */
function getDeptTree() {
  deptTreeSelect().then(response => {
    deptOptions.value = response.data;
  });
};
function onMountedFormCreate(fcapi){
  //加载表单成功,隐藏提交重置按钮,禁用表单
  fcapi.btn.show(false)
  fcapi.resetBtn.show(false)
  fcapi.disabled(true)
}
function onMountedFormCreateApproval(e){
  //加载表单成功,隐藏提交重置按钮,禁用表单
  console.log("审批按钮隐藏");
  e.btn.show(false)
  e.resetBtn.show(false)
  e.disabled(true)
}

initData()
</script>
<style lang="scss" scoped>
.el-descriptions__cell .el-descriptions__label{
  font-weight: bold;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
  margin-bottom: 20px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.button-new-tag {
  margin-left: 10px;
}
</style>
