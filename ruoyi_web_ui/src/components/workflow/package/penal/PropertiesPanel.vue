<template>
  <div class="process-panel__container" :style="{ width: `${width}px` }">
    <el-collapse v-model="activeTab">
      <el-collapse-item name="base">
        <!-- class="panel-tab__title" -->
        <template #title>
          <el-icon class="mr5">
            <InfoFilled/>
          </el-icon>
          常规
        </template
        >
        <ElementBaseInfo
            :id-edit-disabled="idEditDisabled"
            :business-object="elementBusinessObject"
            :type="elementType"
            :model="model"
        />
      </el-collapse-item>
      <el-collapse-item name="condition" v-if="elementType === 'Process'" key="message">
        <template #title>
          <el-icon class="mr5">
            <Comment/>
          </el-icon>
          消息与信号
        </template>
        <signal-and-massage/>
      </el-collapse-item>
      <el-collapse-item name="condition" v-if="conditionFormVisible" key="condition">
        <template #title>
          <el-icon class="mr5">
            <Promotion/>
          </el-icon>
          流转条件
        </template>
        <flow-condition :business-object="elementBusinessObject" :type="elementType"/>
      </el-collapse-item>
      <el-collapse-item name="condition" v-if="formVisible" key="form">
        <template #title>
          <el-icon class="mr5">
            <Checked/>
          </el-icon>
          表单
        </template>
        <element-form :id="elementId" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="task" v-if="elementType.indexOf('Task') !== -1" key="task">
        <template #title>
          <el-icon class="mr5 ">
            <Checked/>
          </el-icon>
          任务
        </template>
        <element-task :id="elementId" :type="elementType"/>
      </el-collapse-item>
      <el-collapse-item
          name="multiInstance"
          v-if="elementType.indexOf('Task') !== -1"
          key="multiInstance"
      >
        <template #title>
          <el-icon class="mr5">
            <HelpFilled/>
          </el-icon>
          多实例
        </template>
        <element-multi-instance :business-object="elementBusinessObject" :type="elementType"/>
      </el-collapse-item>
      <el-collapse-item name="listeners" key="listeners">
        <template #title>
          <el-icon class="mr5">
            <BellFilled/>
          </el-icon>
          执行监听器
        </template>
        <element-listeners :id="elementId" :type="elementType"/>
      </el-collapse-item>
      <el-collapse-item name="taskListeners" v-if="elementType === 'UserTask'" key="taskListeners">
        <template #title>
          <el-icon class="mr5">
            <BellFilled/>
          </el-icon>
          任务监听器
        </template>
        <user-task-listeners :id="elementId" :type="elementType"/>
      </el-collapse-item>
      <el-collapse-item name="extensions" key="extensions">
        <template #title>
          <el-icon class="mr5">
            <CirclePlusFilled/>
          </el-icon>
          扩展属性
        </template>
        <element-properties :id="elementId" :type="elementType"/>
      </el-collapse-item>
      <el-collapse-item name="other" key="other">
        <template #title>
          <el-icon class="mr5">
            <Promotion/>
          </el-icon>
          其他
        </template>
        <element-other-config :id="elementId"/>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<script setup name="MyPropertiesPanel">
import ElementForm from './form/ElementForm'
import ElementBaseInfo from './base/ElementBaseInfo'
import ElementOtherConfig from './other/ElementOtherConfig'
import ElementTask from './task/ElementTask'
import ElementMultiInstance from './multi-instance/ElementMultiInstance'
import FlowCondition from './flow-condition/FlowCondition'
import SignalAndMassage from './signal-message/SignalAndMessage'
import ElementListeners from './listeners/ElementListeners'
import ElementProperties from './properties/ElementProperties'
import UserTaskListeners from './listeners/UserTaskListeners'

/**
 * 侧边栏
 * @Author MiyueFE
 * @Home https://github.com/miyuesc
 * @Date 2021年3月31日18:57:51
 */
const props = defineProps({
  bpmnModeler: {
    type: Object,
    default: () => {
    }
  },
  prefix: {
    type: String,
    default: 'camunda'
  },
  width: {
    type: Number,
    default: 480
  },
  idEditDisabled: {
    type: Boolean,
    default: false
  },
  model: Object // 流程模型的数据
})

const activeTab = ref('base')
const elementId = ref('')
const elementType = ref('')
const elementBusinessObject = ref({}) // 元素 businessObject 镜像，提供给需要做判断的组件使用
const conditionFormVisible = ref(false) // 流转条件设置
const formVisible = ref(false) // 表单配置
const bpmnElement = ref()

provide('prefix', props.prefix)
provide('width', props.width)
const bpmnInstances = () => (window)?.bpmnInstances

// 监听 props.bpmnModeler 然后 initModels
const unwatchBpmn = watch(
    () => props.bpmnModeler,
    () => {
      // 避免加载时 流程图 并未加载完成
      if (!props.bpmnModeler) {
        console.log('缺少props.bpmnModeler')
        return
      }
      const w = window
      w.bpmnInstances = {
        modeler: props.bpmnModeler,
        modeling: props.bpmnModeler.get('modeling'),
        moddle: props.bpmnModeler.get('moddle'),
        eventBus: props.bpmnModeler.get('eventBus'),
        bpmnFactory: props.bpmnModeler.get('bpmnFactory'),
        elementFactory: props.bpmnModeler.get('elementFactory'),
        elementRegistry: props.bpmnModeler.get('elementRegistry'),
        replace: props.bpmnModeler.get('replace'),
        selection: props.bpmnModeler.get('selection')
      }

      console.log(bpmnInstances(), 'window.bpmnInstances')
      getActiveElement()
      unwatchBpmn()
    },
    {
      immediate: true
    }
)

const getActiveElement = () => {
  // 初始第一个选中元素 bpmn:Process
  initFormOnChanged(null)
  props.bpmnModeler.on('import.done', (e) => {
    initFormOnChanged(null)
  })
  // 监听选择事件，修改当前激活的元素以及表单
  props.bpmnModeler.on('selection.changed', ({newSelection}) => {
    initFormOnChanged(newSelection[0] || null)
  })
  props.bpmnModeler.on('element.changed', ({element}) => {
    // 保证 修改 "默认流转路径" 类似需要修改多个元素的事件发生的时候，更新表单的元素与原选中元素不一致。
    if (element && element.id === elementId.value) {
      initFormOnChanged(element)
    }
  })
}
// 初始化数据
const initFormOnChanged = (element) => {
  let activatedElement = element
  if (!activatedElement) {
    activatedElement =
        bpmnInstances().elementRegistry.find((el) => el.type === 'bpmn:Process') ??
        bpmnInstances().elementRegistry.find((el) => el.type === 'bpmn:Collaboration')
  }
  if (!activatedElement) return
  console.log('businessObject: ', activatedElement.businessObject)
  bpmnInstances().bpmnElement = activatedElement
  bpmnElement.value = activatedElement
  elementId.value = activatedElement.id
  elementType.value = activatedElement.type.split(':')[1] || ''
  elementBusinessObject.value = JSON.parse(JSON.stringify(activatedElement.businessObject))
  conditionFormVisible.value = !!(
      elementType.value === 'SequenceFlow' &&
      activatedElement.source &&
      activatedElement.source.type.indexOf('StartEvent') === -1
  )
  formVisible.value = elementType.value === 'UserTask' || elementType.value === 'StartEvent'
}

onBeforeUnmount(() => {
  const w = window
  w.bpmnInstances = null
})

watch(
    () => elementId.value,
    () => {
      activeTab.value = 'base'
    }
)
</script>
