<template>
  <div class="panel-tab__content">
    <el-form label-width="80px">
      <el-form-item label="表单" prop="formKey" >
        <select-more
            search-pld-text="请输入表单名称筛选"
            select-pld-text="请选择表单"
            v-model="formKey"
            label="name"
            :showId="false"
            @change="updateElementFormKey"
            url="/workflow/form/simpleList">
        </select-more>
      </el-form-item>

      <el-form-item label="节点表单" prop="localScope" >

        <el-switch :disabled="type === 'StartEvent'" v-model="localScope" active-value="true" inactive-value="false" active-text="是" inactive-text="否" @change="updateElementFormScope()" />
      </el-form-item>
    </el-form>
  </div>
</template>

<script  setup>

const props = defineProps({
  id: String,
  type: String
})
const prefix = inject('prefix')
const width = inject('width')

const formKey = ref('')
const localScope = ref("false")
const businessKey = ref('')
const optionModelTitle = ref('')
const fieldList = ref([])
const formFieldForm = ref({})
const fieldType = ref({
  long: '长整型',
  string: '字符串',
  boolean: '布尔类',
  date: '日期类',
  enum: '枚举类',
  custom: '自定义类型'
})
const bpmnELement = ref()
const elExtensionElements = ref()
const formData = ref()
const otherExtensions = ref()
const bpmnInstances = () => (window)?.bpmnInstances
const resetFormList = () => {
  bpmnELement.value = bpmnInstances().bpmnElement
  formKey.value = bpmnELement.value.businessObject.formValue;
  // 获取元素扩展属性 或者 创建扩展属性
  elExtensionElements.value =
      bpmnELement.value.businessObject.get('extensionElements') ||
      bpmnInstances().moddle.create('bpmn:ExtensionElements', { values: [] })
  // 获取元素表单配置 或者 创建新的表单配置
  formData.value =
      elExtensionElements.value.values.filter((ex) => ex.$type === `${prefix}:FormData`)?.[0] ||
      bpmnInstances().moddle.create(`${prefix}:FormData`, { fields: [] })

  // 业务标识 businessKey， 绑定在 formData 中
  businessKey.value = formData.value.businessKey
  // 保留剩余扩展元素，便于后面更新该元素对应属性
  otherExtensions.value = elExtensionElements.value.values.filter(
      (ex) => ex.$type !== `${prefix}:FormData`
  )
  // 复制原始值，填充表格
  fieldList.value = JSON.parse(JSON.stringify(formData.value.fields || []))

  localScope.value =  bpmnELement.value.businessObject.localScope;
  // 更新元素扩展属性，避免后续报错
  updateElementExtensions()
}
const updateElementFormKey = (val) => {
  let key = '';
  let name = '';
  if(val){
    key = "key_"+val.id
    name = val.name
  }
  // flowable:formKey="key_1754674143739817985"
  bpmnInstances().modeling.updateProperties(toRaw(bpmnELement.value), {
    formKey: key,
    formValue:name,
  })
}
const updateElementFormScope = (e) => {
  bpmnInstances().modeling.updateProperties(toRaw(bpmnELement.value), {  localScope: localScope.value });
}
const updateElementExtensions = () => {
  // 更新回扩展元素
  const newElExtensionElements = bpmnInstances().moddle.create(`bpmn:ExtensionElements`, {
    values: otherExtensions.value.concat(formData.value)
  })
  // 更新到元素上
  bpmnInstances().modeling.updateProperties(toRaw(bpmnELement.value), {
    extensionElements: newElExtensionElements
  })
}

watch(
    () => props.id,
    (val) => {
      val &&
      val.length &&
      nextTick(() => {
        resetFormList()
      })
    },
    {immediate: true}
)
</script>
