<template>
  <div style="margin-top: 16px">
    <el-form-item label="消息实例">
      <div
        style="
          display: flex;
          align-items: center;
          justify-content: space-between;
          flex-wrap: nowrap;
        "
      >
        <el-select v-model="bindMessageId" @change="updateTaskMessage">
          <el-option
            v-for="key in Object.keys(messageMap)"
            :value="key"
            :label="messageMap[key]"
            :key="key"
          />
        </el-select>
        <XButton
          type="primary"
          preIcon="add"
          style="margin-left: 8px"
          @click="openMessageModel"
        />
      </div>
    </el-form-item>
    <el-dialog
      v-model="messageModelVisible"
      :close-on-click-modal="false"
      title="创建新消息"
      width="400px"
      append-to-body
      destroy-on-close
    >
      <el-form :model="newMessageForm" size="small" label-width="90px">
        <el-form-item label="消息ID">
          <el-input v-model="newMessageForm.id" clearable />
        </el-form-item>
        <el-form-item label="消息名称">
          <el-input v-model="newMessageForm.name" clearable />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button size="small" type="primary" @click="createNewMessage">确 认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script  setup>
import {getCurrentInstance} from 'vue'

const props = defineProps({
  id: String,
  type: String
})
const {proxy} = getCurrentInstance();


const bindMessageId = ref('')
const newMessageForm = ref({})
const messageMap = ref({})
const messageModelVisible = ref(false)
const bpmnElement = ref()
const bpmnMessageRefsMap = ref()
const bpmnRootElements = ref()

const bpmnInstances = () => (window).bpmnInstances
const getBindMessage = () => {
  bpmnElement.value = bpmnInstances().bpmnElement
  bindMessageId.value = bpmnElement.value.businessObject?.messageRef?.id || '-1'
}
const openMessageModel = () => {
  messageModelVisible.value = true
  newMessageForm.value = {}
}
const createNewMessage = () => {
  if (messageMap.value[newMessageForm.value.id]) {
    proxy.$modal.msgError('该消息已存在，请修改id后重新保存');
    return
  }
  const newMessage = bpmnInstances().moddle.create('bpmn:Message', newMessageForm.value)
  bpmnRootElements.value.push(newMessage)
  messageMap.value[newMessageForm.value.id] = newMessageForm.value.name
  bpmnMessageRefsMap.value[newMessageForm.value.id] = newMessage
  messageModelVisible.value = false
}
const updateTaskMessage = (messageId) => {
  if (messageId === '-1') {
    bpmnInstances().modeling.updateProperties(toRaw(bpmnElement.value), {
      messageRef: null
    })
  } else {
    bpmnInstances().modeling.updateProperties(toRaw(bpmnElement.value), {
      messageRef: bpmnMessageRefsMap.value[messageId]
    })
  }
}

onMounted(() => {
  bpmnMessageRefsMap.value = Object.create(null)
  bpmnRootElements.value = bpmnInstances().modeler.getDefinitions().rootElements
  bpmnRootElements.value
    .filter((el) => el.$type === 'bpmn:Message')
    .forEach((m) => {
      bpmnMessageRefsMap.value[m.id] = m
      messageMap.value[m.id] = m.name
    })
  messageMap.value['-1'] = '无'
})

onBeforeUnmount(() => {
  bpmnElement.value = null
})
watch(
  () => props.id,
  () => {
    // bpmnElement.value = bpmnInstances().bpmnElement
    nextTick(() => {
      getBindMessage()
    })
  },
  { immediate: true }
)
</script>
