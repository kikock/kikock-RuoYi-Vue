<template>
  <div class="panel-tab__content">
    <el-form label-width="80px" label-position="top">
      <el-form-item label="审批人">
        <el-radio-group v-model="dataType" @change="changeDataType">
          <el-radio label="USERS">指定用户</el-radio>
          <el-radio label="ROLES">角色</el-radio>
          <el-radio label="DEPTS">部门</el-radio>
          <el-radio label="POSTS">岗位</el-radio>
          <el-radio label="USERGROUP">自定义用户组</el-radio>
          <el-radio label="INITIATOR">发起人</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="dataType === 'USERS'"
                    label="指定用户"
                    :rules="[{required: true,message: '请选择审批用户',rigger: 'blur', }]">
        <select-more
            search-pld-text="请输入用户名称筛选"
            select-pld-text="请选择用户"
            @changeMultiple="changeSelectUsers"
            v-model="selectedUser.ids"
            :showId="false"
            url="/system/user/simpleList"
            multiple>
        </select-more>
      </el-form-item>
      <el-form-item v-if="dataType === 'ROLES'"
                    label="指定角色"
                    :rules="[{required: true,message: '请选择审批角色',rigger: 'blur', }]">
        <select-more
            search-pld-text="请输入角色名称筛选"
            select-pld-text="请选择角色"
            v-model="roleIds"
            @changeMultiple="changeSelectRoles"
            :showId="false"
            url="/system/role/simpleList"
            multiple>
        </select-more>
      </el-form-item>
      <el-form-item v-if="dataType === 'DEPTS'"
                    label="指定部门"
                    :rules="[{required: true,message: '请选择审批部门',rigger: 'blur', }]">

        <el-tree-select
            v-model="deptIds"
            :data="deptOptions"
            multiple
            :render-after-expand="false"
            :props="{ value: 'id', label: 'label', children: 'children' }"
            value-key="id"
            show-checkbox
            check-strictly
            check-on-click-node
            style="width: 240px"
            @change="checkedDeptChange"
        />
      </el-form-item>
      <el-form-item v-if="dataType === 'POSTS'"
                    label="指定岗位"
                    :rules="[{required: true,message: '请选择审批岗位',rigger: 'blur', }]">
        <select-more
            search-pld-text="请输入岗位名称筛选"
            select-pld-text="请选择岗位"
            v-model="postIds"
            @changeMultiple="changeSelectPosts"
            :showId="false"
            url="/system/post/simpleList"
            multiple>
        </select-more>
      </el-form-item>
      <el-form-item v-if="dataType === 'USERGROUP'"
                    label="指定用户组"
                    :rules="[{required: true,message: '请选择审批用户组',rigger: 'blur', }]">
        <select-more
            search-pld-text="请输入用户组名称筛选"
            select-pld-text="请选择自定义用户组"
            v-model="userGroupIds"
            @changeMultiple="changeSelectUserGroups"
            :showId="false"
            url="/workflow/group/simpleList"
            multiple>
        </select-more>
      </el-form-item>
      <el-form-item label="选择内容" v-if="dataType!= 'INITIATOR'">
        <el-tag v-for="userText in selectedUser.text" :key="userText" effect="plain">
          {{ userText }}
        </el-tag>
      </el-form-item>
      <el-form-item label="多实例审批方式">
        <el-radio-group v-model="multiLoopType" @change="changeMultiLoopType">
          <el-radio label="Null">无</el-radio>
          <el-radio label="SequentialMultiInstance">会签（需所有审批人同意）</el-radio>
          <el-radio label="ParallelMultiInstance">或签（一名审批人同意即可）</el-radio>
        </el-radio-group>
      </el-form-item>

    </el-form>


    <el-row>
      <div v-show="showMultiFlog">
        <el-divider/>
        <el-row v-if="multiLoopType !== 'Null'">
          <el-tooltip content="开启后，实例需按顺序轮流审批" placement="top-start" @click.stop.prevent>
            <i class="header-icon el-icon-info"></i>
          </el-tooltip>
          <span class="custom-label">顺序审批：</span>
          <el-switch v-model="isSequential" @change="changeMultiLoopType()"/>
        </el-row>
      </div>
    </el-row>
  </div>

</template>

<script>
import {deptTreeSelect} from "@/api/system/user";
import {XButton} from '@/components/XButton'

const userTaskForm = {
  dataType: '', //候选用户类型
  assignee: '', //审批用户
  candidateUsers: '', //候选用户id集合
  candidateGroups: '', //候选组集合
  text: '', //文本显示
  // dueDate: '',
  // followUpDate: '',
  // priority: ''
}

export default {
  name: "UserTask",
  props: {
    id: String,
    type: String
  },
  components: {XButton},
  data() {
    return {
      loading: false,
      dataType: 'USERS',
      userIds: [],
      selectedUser: {
        ids: [],
        text: []
      },
      deptName: undefined,
      deptOptions: [],
      deptProps: {
        children: "children",
        label: "label"
      },
      selectMoreVos: [],
      deptTempOptions: [],
      userTableList: [],
      userTotal: 0,
      selectedUserDate: [],
      roleOptions: [],
      roleIds: [],
      postIds: [],
      userGroupIds: [],
      deptTreeData: [],
      deptIds: [],
      // 查询参数
      queryParams: {
        deptId: undefined
      },
      showMultiFlog: false,
      isSequential: false,
      multiLoopType: 'Null',
    };
  },
  watch: {
    id: {
      immediate: true,
      handler() {
        this.bpmnElement = window.bpmnInstances.bpmnElement;
        this.$nextTick(() => this.resetTaskForm());
      }
    },
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  },
  methods: {
    changeSelectUsers(rows) {
      console.log("选择元素:", rows);
      userTaskForm.dataType = 'USERS';
      if (rows) {
        this.selectedUser.text = rows.map(k => k.name) || [];
        if (rows.length === 1) {
          console.log(rows);
          let data = rows[0];
          userTaskForm.assignee = data.id;
          userTaskForm.text = data.name;
          userTaskForm.candidateUsers = null;
          this.showMultiFlog = false;
          this.multiLoopType = 'Null';
          this.changeMultiLoopType();
        } else {
          userTaskForm.candidateUsers = rows.map(k => k.id).join() || null;
          userTaskForm.text = rows.map(k => k.name).join() || null;
          userTaskForm.assignee = null;
          this.showMultiFlog = true;
        }
      }
      console.log("选择审批用户:", userTaskForm);
      this.updateElementTask()
    },
    changeSelectRoles(rows) {
      console.log("选择元素:", rows);
      this.selectedUser.text = rows.map(k => k.name) || [];
      if (rows) {
        userTaskForm.dataType = 'ROLES';
        userTaskForm.candidateGroups = rows.map(k => k.id).join() || null;
        userTaskForm.text = rows.map(k => k.name).join() || null;
      } else {
        userTaskForm.dataType = null;
        userTaskForm.candidateGroups = null;
        userTaskForm.text = null;
        this.multiLoopType = 'Null';
      }
      console.log("选择审批角色:", userTaskForm);
      this.updateElementTask();
      this.changeMultiLoopType();
    },
    changeSelectPosts(rows) {
      console.log("选择元素:", rows);
      this.selectedUser.text = rows.map(k => k.name) || [];
      if (rows) {
        userTaskForm.dataType = 'POSTS';
        userTaskForm.candidateGroups = rows.map(k => k.id).join() || null;
        userTaskForm.text = rows.map(k => k.name).join() || null;
      } else {
        userTaskForm.dataType = null;
        userTaskForm.candidateGroups = null;
        userTaskForm.text = null;
        this.multiLoopType = 'Null';
      }
      console.log("选择岗位角色:", userTaskForm);
      this.updateElementTask();
      this.changeMultiLoopType();
    },
    changeSelectUserGroups(rows) {
      console.log("选择元素:", rows);
      this.selectedUser.text = rows.map(k => k.name) || [];
      if (rows) {
        userTaskForm.dataType = 'USERGROUP';
        userTaskForm.candidateGroups = rows.map(k => k.id).join() || null;
        userTaskForm.text = rows.map(k => k.name).join() || null;
      } else {
        userTaskForm.dataType = null;
        userTaskForm.candidateGroups = null;
        userTaskForm.text = null;
        this.multiLoopType = 'Null';
      }
      console.log("选择自定义用户组:", userTaskForm);
      this.updateElementTask();
      this.changeMultiLoopType();
    },
    resetTaskForm() {
      const bpmnElementObj = this.bpmnElement?.businessObject;
      if (!bpmnElementObj) {
        return;
      }
      this.clearOptionsData()
      let taskDate = bpmnElementObj
      console.log("初始数据:", bpmnElementObj);
      this.dataType = taskDate.dataType || bpmnElementObj['dataType'] || "USERS";

      if (taskDate.dataType === 'USERS') {
        //用户
        let userIdData = bpmnElementObj['candidateUsers'] || bpmnElementObj['assignee'] || taskDate.candidateUsers || taskDate.assignee || [];
        let userText = bpmnElementObj['text'] || taskDate.text || [];
        if (userIdData && userIdData.toString().length > 0 && userText && userText.length > 0) {
          this.selectedUser.ids = userIdData?.toString().split(',');
          this.selectedUser.text = userText?.split(',');
        }
        if (this.selectedUser.ids.length > 1) {
          this.showMultiFlog = true;
        }
      } else if (taskDate.dataType === 'ROLES') {
        //角色
        let roleIdData = bpmnElementObj['candidateGroups'] || taskDate.candidateGroups || [];
        if (roleIdData && roleIdData.length > 0) {
          this.roleIds = roleIdData.split(',')
        }
        let userText = bpmnElementObj['text'] || taskDate.text || [];
        if (userText && userText.length > 0) {
          this.selectedUser.text = userText?.split(',');
        }
        this.showMultiFlog = true;
      } else if (taskDate.dataType === 'POSTS') {
        //岗位
        let postIdData = bpmnElementObj['candidateGroups'] || taskDate.candidateGroups || [];
        if (postIdData && postIdData.length > 0) {
          this.postIds = postIdData.split(',')
        }
        let userText = bpmnElementObj['text'] || taskDate.text || [];
        if (userText && userText.length > 0) {
          this.selectedUser.text = userText?.split(',');
        }
        this.showMultiFlog = true;
      } else if (taskDate.dataType === 'USERGROUP') {
        //自定义用户组
        let userGroupIdData = bpmnElementObj['candidateGroups'] || taskDate.candidateGroups || [];
        if (userGroupIdData && userGroupIdData.length > 0) {
          this.userGroupIds = userGroupIdData.split(',')
        }
        let userText = bpmnElementObj['text'] || taskDate.text || [];
        if (userText && userText.length > 0) {
          this.selectedUser.text = userText?.split(',');
        }
        this.showMultiFlog = true;
      } else if (this.dataType === 'DEPTS') {
        this.getDeptTreeData();
        let deptIdData = bpmnElementObj['candidateGroups'] || [];
        if (deptIdData && deptIdData.length > 0) {
          this.deptIds = deptIdData.split(',');
        }
        this.showMultiFlog = true;
      }
      this.getElementLoop(bpmnElementObj);
    },
    /**
     * 清空选项数据
     */
    clearOptionsData() {
      this.selectedUser.ids = [];
      this.selectedUser.text = [];
      this.roleIds = [];
      this.deptIds = [];
      this.postIds = [];
      this.userGroupIds = [];
    },
    /**
     * 跟新节点数据
     */
    updateElementTask() {
      const taskAttr = Object.create(null);
      for (let key in userTaskForm) {
        taskAttr[key] = userTaskForm[key];
      }
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, taskAttr);
    },
    /**
     * 查询部门下拉树结构
     */
    getDeptOptions() {
      return new Promise((resolve, reject) => {
        if (!this.deptOptions || this.deptOptions.length <= 0) {
          deptTreeSelect().then(response => {
            this.deptTempOptions = response.data;
            this.deptOptions = response.data;
            resolve()
          })
        } else {
          reject()
        }
      });
    },
    /**
     * 查询部门下拉树结构（含部门前缀）
     */
    getDeptTreeData() {
      function refactorTree(data) {
        return data.map(node => {
          let treeData = {id: `DEPT${node.id}`, label: node.label, parentId: node.parentId, weight: node.weight};
          if (node.children && node.children.length > 0) {
            treeData.children = refactorTree(node.children);
          }
          return treeData;
        });
      }

      return new Promise((resolve, reject) => {
        if (!this.deptTreeData || this.deptTreeData.length <= 0) {
          this.getDeptOptions().then(() => {
            console.log("部门树",this.deptOptions);
            this.deptTreeData = refactorTree(this.deptOptions);
            resolve()
          }).catch(() => {
            reject()
          })
        } else {
          resolve()
        }
      })
    },

    checkedDeptChange() {
      console.log("选择部门id:",this.deptIds);
      let groups = null;
      let text = null;
      if (this.deptIds && this.deptIds.length > 0) {
        userTaskForm.dataType = 'DEPTS';
        groups = this.deptIds.join() || null;
        let textArr = []
        let treeStarkData = JSON.parse(JSON.stringify(this.deptTreeData));
        console.log("treeStarkData=>>>",treeStarkData);
        this.deptIds.forEach(id => {
          let stark = []
          stark = stark.concat(treeStarkData);
          while (stark.length) {
            let temp = stark.shift();
            if (temp.children) {
              stark = temp.children.concat(stark);
            }
            if (id === temp.id) {
              textArr.push(temp);
            }
          }
        })
        console.log(textArr);
        text = textArr?.map(k => k.label).join() || null;
      } else {
        userTaskForm.dataType = null;
        this.multiLoopType = 'Null';
      }
      userTaskForm.candidateGroups = groups;
      userTaskForm.text = text;
      this.updateElementTask();
      this.changeMultiLoopType();
    },

    changeDataType(val) {
      if (val === 'ROLES' || val === 'DEPTS' || val === 'POSTS' || val === 'USERGROUP' || (val === 'USERS' && this.selectedUser.ids.length > 1)) {
        this.showMultiFlog = true;
      } else {
        this.showMultiFlog = false;
      }
      this.multiLoopType = 'Null';
      this.changeMultiLoopType();
      this.clearOptionsData();
      // 清空 userTaskForm 所有属性值
      Object.keys(userTaskForm).forEach(key => userTaskForm[key] = null);
      userTaskForm.dataType = val;
      if (val === 'DEPTS') {
        this.getDeptTreeData();
        if (this.deptIds && this.deptIds.length > 0) {
          userTaskForm.candidateGroups = this.deptIds.join() || null;
          let textArr = []
          let treeStarkData = JSON.parse(JSON.stringify(this.deptTreeData));
          this.deptIds.forEach(id => {
            let stark = []
            stark = stark.concat(treeStarkData);
            while(stark.length) {
              let temp = stark.shift();
              if(temp.children) {
                stark = temp.children.concat(stark);
              }
              if(id === temp.id) {
                textArr.push(temp);
              }
            }
          })
          userTaskForm.text = textArr?.map(k => k.label).join() || null;
        }
      } else if (val === 'INITIATOR') {
        userTaskForm.assignee = "${initiator}";
        userTaskForm.text = "流程发起人";
      }
      this.updateElementTask();
    },
    getElementLoop(businessObject) {
      if (!businessObject.loopCharacteristics) {
        this.multiLoopType = "Null";
        return;
      }
      this.isSequential = businessObject.loopCharacteristics.isSequential;
      if (businessObject.loopCharacteristics.completionCondition) {
        if (businessObject.loopCharacteristics.completionCondition.body === "${nrOfCompletedInstances >= nrOfInstances}") {
          this.multiLoopType = "SequentialMultiInstance";
        } else {
          this.multiLoopType = "ParallelMultiInstance";

        }
      }
    },
    changeMultiLoopType() {
      // 取消多实例配置
      if (this.multiLoopType === "Null") {
        window.bpmnInstances.modeling.updateProperties(this.bpmnElement, {loopCharacteristics: null, assignee: null});
        return;
      }
      this.multiLoopInstance = window.bpmnInstances.moddle.create("bpmn:MultiInstanceLoopCharacteristics", {isSequential: this.isSequential});
      // 更新多实例配置
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, {
        loopCharacteristics: this.multiLoopInstance,
        assignee: '${assignee}'
      });
      // 完成条件
      let completionCondition = null;
      // 会签
      if (this.multiLoopType === "SequentialMultiInstance") {
        completionCondition = window.bpmnInstances.moddle.create("bpmn:FormalExpression", {body: "${nrOfCompletedInstances >= nrOfInstances}"});
      }
      // 或签
      if (this.multiLoopType === "ParallelMultiInstance") {
        completionCondition = window.bpmnInstances.moddle.create("bpmn:FormalExpression", {body: "${nrOfCompletedInstances > 0}"});
      }
      // 更新模块属性信息
      window.bpmnInstances.modeling.updateModdleProperties(this.bpmnElement, this.multiLoopInstance, {
        collection: '${multiInstanceHandler.getUserIds(execution)}',
        elementVariable: 'assignee',
        completionCondition
      });
    },
  }
};
</script>

<style scoped lang="scss">
.el-row .el-radio-group {
  margin-bottom: 15px;

  .el-radio {
    line-height: 28px;
  }
}

.el-tag {
  margin-bottom: 10px;

  + .el-tag {
    margin-left: 10px;
  }
}

.custom-label {
  padding-left: 5px;
  font-weight: 500;
  font-size: 14px;
  color: #606266;
}

</style>
