<template>
  <div class="panel-tab__content">
    <el-form label-width="90px" :model="elementBaseInfo" :rules="rules">
      <div v-if="elementBaseInfo.$type == 'bpmn:Process'">
        <el-form-item label="流程标识" prop="id">
          <el-input
            v-model="elementBaseInfo.id"
            placeholder="请输入流标标识"
            :disabled="idEditDisabled || elementBaseInfo.$type === 'bpmn:Process'"
            @change="updateBaseInfo('id')"
          />
        </el-form-item>
        <el-form-item label="流程名称" prop="name">
          <el-input
            v-model="elementBaseInfo.name"
            placeholder="请输入流程名称"
            clearable
            @change="updateBaseInfo('name')"
          />
        </el-form-item>
        <el-form-item label="版本标签">
          <el-input v-model="elementBaseInfo.versionTag" clearable @change="updateBaseInfo('versionTag')" />
        </el-form-item>
        <el-form-item label="可执行">
          <el-switch
              v-model="elementBaseInfo.isExecutable"
              active-text="是"
              inactive-text="否"
              @change="updateBaseInfo('isExecutable')"
          ></el-switch>

        </el-form-item>
      </div>
      <div v-else>
        <el-form-item label="ID">
          <el-input v-model="elementBaseInfo.id" clearable @change="updateBaseInfo('id')" />
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="elementBaseInfo.name" clearable @change="updateBaseInfo('name')" />
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>
<script  setup>

const props = defineProps({
  businessObject: {
    type: Object,
    default: () => {}
  },
  model: {
    type: Object,
    default: () => {}
  },
  idEditDisabled: {
    type: Boolean,
    default: true
  }
})
const bpmnElement = ref()
const elementBaseInfo = ref({})
// 流程表单的下拉框的数据
// const forms = ref([])
// 流程模型的校验
const rules = reactive({
  id: [{ required: true, message: '流程标识不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '流程名称不能为空', trigger: 'blur' }]
})

const bpmnInstances = () => (window)?.bpmnInstances
const resetBaseInfo = () => {
  bpmnElement.value = window?.bpmnInstances?.bpmnElement;
  elementBaseInfo.value = JSON.parse(JSON.stringify(bpmnElement.value.businessObject));
  if(elementBaseInfo.value.$type === 'bpmn:Process' && props.model && props.model.modelKey && props.model.modelName){
    console.log("模型key和模型名称:",props.model.modelKey,props.model.modelName);
    elementBaseInfo.value.id = props.model.modelKey
  }
}
const handleKeyUpdate = (value) => {
  // 校验 value 的值，只有 XML NCName 通过的情况下，才进行赋值。否则，会导致流程图报错，无法绘制的问题
  if (!value) {
    return
  }
  if (!value.match(/[a-zA-Z_][\-_.0-9a-zA-Z$]*/)) {
    console.log('key 不满足 XML NCName 规则，所以不进行赋值')
    return
  }
  console.log('key 满足 XML NCName 规则，所以进行赋值')
  // 在 BPMN 的 XML 中，流程标识 key，其实对应的是 id 节点
  elementBaseInfo.value['id'] = value
  setTimeout(() => {
    updateBaseInfo('id')
  }, 100)
}
const handleNameUpdate = (value) => {
  if (!value) {
    return
  }
  elementBaseInfo.value['name'] = value
  setTimeout(() => {
    updateBaseInfo('name')
  }, 100)
}
const updateBaseInfo = (key) => {
  const attrObj = Object.create(null);
  attrObj[key] = elementBaseInfo.value[key];
  if (key === "id") {
    bpmnInstances().modeling.updateProperties(toRaw(bpmnElement.value), {
      id: elementBaseInfo.value[key],
      di: { id: `${elementBaseInfo.value[key]}_di` }
    });
  } else {
    bpmnInstances().modeling.updateProperties(toRaw(bpmnElement.value), attrObj)
  }
}

// onMounted(() => {
//   // 针对上传的 bpmn 流程图时，需要延迟 1 秒的时间，保证 key 和 name 的更新
//   setTimeout(() => {
//     handleKeyUpdate(props.model.key)
//     handleNameUpdate(props.model.name)
//   }, 1000)
// })

watch(
  () => props.businessObject,
  (val) => {
    if (val) {
      // nextTick(() => {
      resetBaseInfo()
      // })
    }
  }
)
onBeforeUnmount(() => {
  bpmnElement.value = null
})
</script>
