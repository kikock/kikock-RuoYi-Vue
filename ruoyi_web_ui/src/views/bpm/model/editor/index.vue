<template>
  <div v-loading="loading">
    <!-- 流程设计器，负责绘制流程等 -->
    <my-process-designer
        key="designer"
        v-model="xmlString"
        :value="xmlString"
        v-bind="controlForm"
        keyboard
        ref="processDesigner"
        @init-finished="initModeler"
        :additionalModel="controlForm.additionalModel"
        @save="save"
        @backModel="backModel"
    />

    <!-- 流程属性器，负责编辑每个流程节点的属性 -->
    <MyPropertiesPanel
        key="penal"
        :bpmnModeler="modeler"
        :prefix="controlForm.prefix"
        class="process-panel"
        :model="model"
    />
  </div>
</template>

<script setup name="model_editor">
// 自定义元素选中时的弹出菜单（修改 默认任务 为 用户任务）
import CustomContentPadProvider from '@/components/bpmnProcessDesigner/package/designer/plugins/content-pad'
// 自定义左侧菜单（修改 默认任务 为 用户任务）
import CustomPaletteProvider from '@/components/bpmnProcessDesigner/package/designer/plugins/palette'
import MyProcessDesigner from '@/components/bpmnProcessDesigner/package/designer/ProcessDesigner.vue'
import {getCurrentInstance} from 'vue'
import {createModel, getModel, updateModelFlowChart} from '@/api/bpm/model'
const route = useRoute();// 路由的查询
const router = useRouter();
const {proxy} = getCurrentInstance();
const loading  =ref(true) // 加载状态
const xmlString = ref("") // BPMN XML
const modeler = ref(null) // BPMN Modeler
const controlForm = ref({
  simulation: true,
  labelEditing: false,
  labelVisible: false,
  prefix: 'flowable',
  headerButtonSize: 'mini',
  additionalModel: [CustomContentPadProvider, CustomPaletteProvider]
})
const model = ref() // 流程模型的信息

/** 初始化 modeler */
const initModeler = (item) => {
  setTimeout(() => {
    modeler.value = item
    // console.log("initModeler",item);
  }, 10)
}
/** 返回 */
const backModel = () => {
  close()
}
/** 添加/修改模型 */
const save = async (bpmnXml) => {
  const data = {
    ...model.value,
    bpmnXml: bpmnXml // bpmnXml 只是初始化流程图，后续修改无法通过它获得
  }
  // console.log("保存数据:",data);
  // 提交
  if (data.id) {
    console.log("修改数据");
    updateModelFlowChart(data).then(response => {
      proxy.$modal.msgSuccess("保存成功");
      close()
    });

  } else {
    createModel(data).then(response => {
      proxy.$modal.msgSuccess("保存成功");
      close()
    });
  }
  // 跳转回去
  // close()
}

/** 关闭按钮 */
const close = () => {
  router.push({ path: '/flowable/bpm/model' })
}
/** 初始化数据 */
const getFromData =  () => {
  const modelId = route.query.modelId;
  if (!modelId) {
    proxy.$modal.msgSuccess("错误缺少模型 modelId 编号");
    loading.value = false;
    close()
    return
  }

  getModel(modelId).then(response => {
    xmlString.value = response.data.bpmnXml
    model.value = {
      ...response.data,
      bpmnXml: undefined // 清空 bpmnXml 属性
    }
    loading.value = false;
  });
}
getFromData()
</script>
<style lang="scss">
.process-panel__container {
  position: absolute;
  top: 50px;
  right: 15px;
}
</style>
