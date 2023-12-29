<script  setup name ='XTextButton'>


const props = defineProps({
  modelValue: false,
  loading: false,
  preIcon: '',
  postIcon: '',
  title: '',
  type: 'primary',
  circle: false,
  round: false,
  plain: false,
  onClick: {
    type: Function,
    default: () => {
      console.log('点击方法');
    }
  }
})
const getBindValue = computed(() => {
  const delArr= ['title', 'preIcon', 'postIcon', 'onClick']
  const attrs = useAttrs()
  const obj = { ...attrs, ...props }
  for (const key in obj) {
    if (delArr.indexOf(key) !== -1) {
      delete obj[key]
    }
  }
  return obj
})
</script>

<template>
  <el-button link v-bind="getBindValue" @click="onClick">
    <svg-icon v-if="preIcon"  :icon-class="preIcon" class="mr10" />
    {{ title ? title : '' }}
    <svg-icon v-if="postIcon"  :icon-class="postIcon" class="mr10" />
  </el-button>
</template>
<style lang="scss" scoped>
:deep(.el-button.is-text) {
  padding: 8px 4px;
  margin-left: 0;
}

:deep(.el-button.is-link) {
  padding: 8px 4px;
  margin-left: 0;
}
</style>
