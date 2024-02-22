<template>
  <div class="my-process-designer">
    <div class="my-process-designer__container">
      <div class="my-process-designer__canvas" style="height: 760px" ref="bpmnCanvas"></div>
      <!-- 自定义箭头样式，用于成功状态下流程连线箭头 -->
      <defs ref="customSuccessDefs">
        <marker id="sequenceflow-end-white-success" viewBox="0 0 20 20" refX="11" refY="10" markerWidth="10" markerHeight="10" orient="auto">
          <path class="success-arrow" d="M 1 5 L 11 10 L 1 15 Z" style="stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1;"></path>
        </marker>
        <marker id="conditional-flow-marker-white-success" viewBox="0 0 20 20" refX="-1" refY="10" markerWidth="10" markerHeight="10" orient="auto">
          <path class="success-conditional" d="M 0 10 L 8 6 L 16 10 L 8 14 Z" style="stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1;"></path>
        </marker>
      </defs>
      <!-- 自定义箭头样式，用于失败状态下流程连线箭头 -->
      <defs ref="customFailDefs">
        <marker id="sequenceflow-end-white-fail" viewBox="0 0 20 20" refX="11" refY="10" markerWidth="10" markerHeight="10" orient="auto">
          <path class="fail-arrow" d="M 1 5 L 11 10 L 1 15 Z" style="stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1;"></path>
        </marker>
        <marker id="conditional-flow-marker-white-fail" viewBox="0 0 20 20" refX="-1" refY="10" markerWidth="10" markerHeight="10" orient="auto">
          <path class="fail-conditional" d="M 0 10 L 8 6 L 16 10 L 8 14 Z" style="stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1;"></path>
        </marker>
      </defs>
      <!-- 已完成节点悬浮弹窗 -->
      <el-dialog class="comment-dialog" :title="dlgTitle || '审批记录'"  v-model="dialogVisible">
        <el-row>
          <el-table :data="taskCommentList"  border header-cell-class-name="table-header-gray">
            <el-table-column label="序号" header-align="center" align="center" type="index" width="55px" />
            <el-table-column label="候选办理" prop="candidate" width="150px" align="center"/>
            <el-table-column label="实际办理" prop="assigneeName" width="100px" align="center"/>
            <el-table-column label="处理时间" prop="createTime" width="140px" align="center"/>
            <el-table-column label="办结时间" prop="finishTime" width="140px" align="center" />
            <el-table-column label="耗时" prop="duration" width="100px" align="center"/>
            <el-table-column label="审批意见" align="center" prop="fullMessage">
            </el-table-column>
          </el-table>
        </el-row>
      </el-dialog>
      <div style="position: absolute; top: 8%; right: 40px; width: 100%;">
        <el-row type="flex" justify="end">
          <el-button-group class="ml-4">
            <el-button  icon="plus"  :disabled="defaultZoom <= 0.3"  @click="processZoomIn()" />
            <el-button >{{ Math.floor(defaultZoom * 10 * 10) + "%" }}</el-button>
            <el-button icon="Minus" :disabled="defaultZoom >= 3.9"  @click="processZoomOut()" />
            <el-button icon="Refresh" @click="processReZoom()"  />
          </el-button-group>
        </el-row>
      </div>



    </div>
  </div>
</template>

<script  setup name="MyProcessViewer2222">
// {{scope.commentList&&scope.commentList[0]?JSON.parse(scope.commentList)[0].fullMessage:''}}
import 'bpmn-js/dist/assets/diagram-js.css' // 左边工具栏以及编辑节点的样式
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css' // 右侧框样式
import '@/components/workflow/package/theme/index.scss'
import MoveCanvasModule from 'diagram-js/lib/navigation/movecanvas';
import BpmnViewer from 'bpmn-js/lib/Viewer'
import DefaultEmptyXML from './plugins/defaultEmpty'
import {getCurrentInstance} from 'vue'


const props = defineProps({
  value: {
    // BPMN XML 字符串
    type: String,
    default: ''
  },
  prefix: {
    // 使用哪个引擎
    type: String,
    default: 'camunda'
  },
  flowWorkData:{
    type: Object,
    default: () => {}
  },
  activityData: {
    // 活动的数据。传递时，可高亮流程
    type: Array,
    default: () => []
  },
  processInstanceData: {
    // 流程实例的数据。传递时，可展示流程发起人等信息
    type: Object,
    default: () => {}
  },
  taskData: {
    // 任务实例的数据。传递时，可展示 UserTask 审核相关的信息
    type: Array,
    default: () => []
  }
})

provide('configGlobal', props)

const emit = defineEmits(['destroy'])
const {proxy} = getCurrentInstance()
let bpmnModeler
const xml = ref('')
const activityLists = ref([])
const bpmnCanvas = ref()
// const element = ref()
const taskCommentList = ref([])
const defaultZoom = ref(1)

const dialogVisible = ref(false)
const dlgTitle = ref(undefined)
// 当前任务id
const selectTaskId = ref(undefined)
// 已完成流程元素
const processNodeInfo = ref(undefined)
const initBpmnModeler = () => {
  if (bpmnModeler) return
  bpmnModeler = new BpmnViewer({
    additionalModules: [
      // 移动整个画布
      MoveCanvasModule
    ],
    container: bpmnCanvas.value,
    bpmnRenderer: {}
  })
}

/* 创建新的流程图 */
const createNewDiagram = async (xml) => {
  // 将字符串转换成图显示出来
  let newId = `Process_${new Date().getTime()}`
  let newName = `业务流程_${new Date().getTime()}`
  let xmlString = xml || DefaultEmptyXML(newId, newName, props.prefix)
  try {
    let { warnings } = await bpmnModeler.importXML(xmlString)
    if (warnings && warnings.length) {
      warnings.forEach((warn) => console.warn(warn))
    }
    // 高亮流程图
    const canvas = bpmnModeler.get('canvas')
    canvas.zoom('fit-viewport', 'auto')
  } catch (e) {
    console.error(e)
  }
}
const initModelListeners = () => {
  const EventBus = bpmnModeler.get('eventBus')
  EventBus.on('element.click', function(eventObj) {
    let element = eventObj ? eventObj.element : null
    elementClick(element)
  });
}

const elementClick = (element) => {
  console.log("点击弹窗");
  selectTaskId.value = undefined;
  dlgTitle.value = undefined;
  if (processNodeInfo.value == null || processNodeInfo.value.finishedTaskSet == null) return;
  if (element == null || processNodeInfo.value.finishedTaskSet.indexOf(element.id) === -1) {
    return;
  }
  selectTaskId.value = element.id;
  dlgTitle.value = element.businessObject ? element.businessObject.name : undefined;
  // 计算当前悬浮任务审批记录，如果记录为空不显示弹窗 taskData
  taskCommentList.value = (props.flowWorkData.historyProcNodeList || []).filter(item => {
    //审批意见
    let mes = item.commentList && item.commentList[0]?item.commentList[0].fullMessage:''
    item.fullMessage = mes;
    return item.activityId === selectTaskId.value;
  });
  console.log("当前审批记录",taskCommentList.value);
  dialogVisible.value = true;
}
const processReZoom = () => {
  defaultZoom.value = 1
  bpmnModeler.get('canvas').zoom('fit-viewport', 'auto')
}
const processZoomIn = (zoomStep = 0.1) => {
  let newZoom = Math.floor(defaultZoom.value * 100 + zoomStep * 100) / 100
  if (newZoom > 4) {
    throw new Error('[Process Designer Warn ]: The zoom ratio cannot be greater than 4')
  }
  defaultZoom.value = newZoom
  bpmnModeler.get('canvas').zoom(defaultZoom.value)
}
const processZoomOut = (zoomStep = 0.1) => {
  let newZoom = Math.floor(defaultZoom.value * 100 - zoomStep * 100) / 100
  if (newZoom < 0.2) {
    throw new Error('[Process Designer Warn ]: The zoom ratio cannot be less than 0.2')
  }
  defaultZoom.value = newZoom
  bpmnModeler.get('canvas').zoom(defaultZoom.value)
}


onMounted(() => {
  xml.value = props.value
  // 初始化
  initBpmnModeler()
  createNewDiagram(xml.value)
  // 初始模型的监听器
  initModelListeners()
})
onBeforeUnmount(() => {
  // this.$once('hook:beforeDestroy', () => {
  // })
  if (bpmnModeler) bpmnModeler.destroy()
  emit('destroy', bpmnModeler)
  bpmnModeler = null
})

watch(
    () => props.value,
    (newValue) => {
      xml.value = newValue
      // 监听数据
      createNewDiagram(xml.value)
    }
)
watch(
  () => props.flowWorkData,
  (newActivityData) => {
    processNodeInfo.value = props.flowWorkData.flowViewer
  }
)
</script>

<style>
/** 处理中 */
.highlight-todo.djs-connection > .djs-visual > path {
  stroke: #1890ff !important;
  stroke-dasharray: 4px !important;
  fill-opacity: 0.2 !important;
}

.highlight-todo.djs-shape .djs-visual > :nth-child(1) {
  fill: #1890ff !important;
  stroke: #1890ff !important;
  stroke-dasharray: 4px !important;
  fill-opacity: 0.2 !important;
}

:deep(.highlight-todo.djs-connection > .djs-visual > path) {
  stroke: #1890ff !important;
  stroke-dasharray: 4px !important;
  fill-opacity: 0.2 !important;
  marker-end: url('#sequenceflow-end-_E7DFDF-_E7DFDF-803g1kf6zwzmcig1y2ulm5egr');
}

:deep(.highlight-todo.djs-shape .djs-visual > :nth-child(1)) {
  fill: #1890ff !important;
  stroke: #1890ff !important;
  stroke-dasharray: 4px !important;
  fill-opacity: 0.2 !important;
}

/** 通过 */
.highlight.djs-shape .djs-visual > :nth-child(1) {
  fill: green !important;
  stroke: green !important;
  fill-opacity: 0.2 !important;
}

.highlight.djs-shape .djs-visual > :nth-child(2) {
  fill: green !important;
}

.highlight.djs-shape .djs-visual > path {
  fill: green !important;
  fill-opacity: 0.2 !important;
  stroke: green !important;
}

.highlight.djs-connection > .djs-visual > path {
  stroke: green !important;
}

.highlight:not(.djs-connection) .djs-visual > :nth-child(1) {
  fill: green !important; /* color elements as green */
}

:deep(.highlight.djs-shape .djs-visual > :nth-child(1)) {
  fill: green !important;
  stroke: green !important;
  fill-opacity: 0.2 !important;
}

:deep(.highlight.djs-shape .djs-visual > :nth-child(2)) {
  fill: green !important;
}

:deep(.highlight.djs-shape .djs-visual > path) {
  fill: green !important;
  fill-opacity: 0.2 !important;
  stroke: green !important;
}

:deep(.highlight.djs-connection > .djs-visual > path) {
  stroke: green !important;
}

/** 不通过 */
.highlight-reject.djs-shape .djs-visual > :nth-child(1) {
  fill: red !important;
  stroke: red !important;
  fill-opacity: 0.2 !important;
}

.highlight-reject.djs-shape .djs-visual > :nth-child(2) {
  fill: red !important;
}

.highlight-reject.djs-shape .djs-visual > path {
  fill: red !important;
  fill-opacity: 0.2 !important;
  stroke: red !important;
}

.highlight-reject.djs-connection > .djs-visual > path {
  stroke: red !important;
}

.highlight-reject:not(.djs-connection) .djs-visual > :nth-child(1) {
  fill: red !important; /* color elements as green */
}

:deep(.highlight-reject.djs-shape .djs-visual > :nth-child(1)) {
  fill: red !important;
  stroke: red !important;
  fill-opacity: 0.2 !important;
}

:deep(.highlight-reject.djs-shape .djs-visual > :nth-child(2)) {
  fill: red !important;
}

:deep(.highlight-reject.djs-shape .djs-visual > path) {
  fill: red !important;
  fill-opacity: 0.2 !important;
  stroke: red !important;
}

:deep(.highlight-reject.djs-connection > .djs-visual > path) {
  stroke: red !important;
}

/** 已取消 */
.highlight-cancel.djs-shape .djs-visual > :nth-child(1) {
  fill: grey !important;
  stroke: grey !important;
  fill-opacity: 0.2 !important;
}

.highlight-cancel.djs-shape .djs-visual > :nth-child(2) {
  fill: grey !important;
}

.highlight-cancel.djs-shape .djs-visual > path {
  fill: grey !important;
  fill-opacity: 0.2 !important;
  stroke: grey !important;
}

.highlight-cancel.djs-connection > .djs-visual > path {
  stroke: grey !important;
}

.highlight-cancel:not(.djs-connection) .djs-visual > :nth-child(1) {
  fill: grey !important; /* color elements as green */
}

:deep(.highlight-cancel.djs-shape .djs-visual > :nth-child(1)) {
  fill: grey !important;
  stroke: grey !important;
  fill-opacity: 0.2 !important;
}

:deep(.highlight-cancel.djs-shape .djs-visual > :nth-child(2)) {
  fill: grey !important;
}

:deep(.highlight-cancel.djs-shape .djs-visual > path) {
  fill: grey !important;
  fill-opacity: 0.2 !important;
  stroke: grey !important;
}

:deep(.highlight-cancel.djs-connection > .djs-visual > path) {
  stroke: grey !important;
}

/** 回退 */
.highlight-return.djs-shape .djs-visual > :nth-child(1) {
  fill: #e6a23c !important;
  stroke: #e6a23c !important;
  fill-opacity: 0.2 !important;
}

.highlight-return.djs-shape .djs-visual > :nth-child(2) {
  fill: #e6a23c !important;
}

.highlight-return.djs-shape .djs-visual > path {
  fill: #e6a23c !important;
  fill-opacity: 0.2 !important;
  stroke: #e6a23c !important;
}

.highlight-return.djs-connection > .djs-visual > path {
  stroke: #e6a23c !important;
}

.highlight-return:not(.djs-connection) .djs-visual > :nth-child(1) {
  fill: #e6a23c !important; /* color elements as green */
}

:deep(.highlight-return.djs-shape .djs-visual > :nth-child(1)) {
  fill: #e6a23c !important;
  stroke: #e6a23c !important;
  fill-opacity: 0.2 !important;
}

:deep(.highlight-return.djs-shape .djs-visual > :nth-child(2)) {
  fill: #e6a23c !important;
}

:deep(.highlight-return.djs-shape .djs-visual > path) {
  fill: #e6a23c !important;
  fill-opacity: 0.2 !important;
  stroke: #e6a23c !important;
}

:deep(.highlight-return.djs-connection > .djs-visual > path) {
  stroke: #e6a23c !important;
}

.element-overlays {
  width: 200px;
  padding: 8px;
  color: #fafafa;
  background: rgb(0 0 0 / 60%);
  border-radius: 4px;
  box-sizing: border-box;
}
</style>
