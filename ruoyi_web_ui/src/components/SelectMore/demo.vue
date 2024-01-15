<template>
  <el-form :model="formData" :rules="rules" label-width="130px" v-loading="formData.editLoading">

    <h4>单选</h4>
    <el-form-item label="订单：" prop="order">
      <select-more v-model="formData.order" url="/mapi/v1/release-task-report/put-name">
      </select-more>
    </el-form-item>

    <h4>编辑回填</h4>
    <el-form-item label="广告主：" prop="adver">
      <select-more v-model="formData.adver" url="/mapi/v1/release-task-report/put-name"
                   :edit-data="formData.editAdverData">
      </select-more>
    </el-form-item>

    <h4>联动关系</h4>
    <el-form-item label="广告位：" prop="adunit">
      <select-more v-model="formData.adunit" url="/mapi/v1/release-task-report/put-name"
                   :other-params="adunitOtherParams" :edit-data="formData.editAdunitData">
      </select-more>
    </el-form-item>

    <h4>多选样式1</h4>
    <el-form-item label="投放：" prop="invest">
      <select-more v-model="formData.invest" multiple multiple-show-text url="/mapi/v1/release-task-report/put-name"
                   :edit-data="formData.editInvestData">
      </select-more>
    </el-form-item>

    <h4>多选样式2</h4>
    <el-form-item label="投放2：" prop="invest2">
      <select-more v-model="formData.invest2" multiple url="/mapi/v1/release-task-report/put-name">
      </select-more>
    </el-form-item>

  </el-form>
</template>

<script setup>
import SelectMore from '@/components/SelectMore/index.vue'

const formData = reactive({
  order: '',
  adver: '',
  invest: [],
  invest2: [],
  adunit: '',
  editAdverData: [],
  editInvestData: [],
  editAdunitData: [],
  editLoading: true // 判断是编辑页需要先禁用选择框，等回填有值后再把禁用去掉
})

const rules = reactive({
  order: [
    { required: true, message: '请选择订单', trigger: 'change' }
  ],
  adver: [
    { required: true, message: '请选择广告主', trigger: 'change' }
  ],
  invest: [
    { required: true, message: '请选择投放', trigger: 'change' }
  ],
  invest2: [
    { required: true, message: '请选择投放2', trigger: 'change' }
  ],
  adunit: [
    { required: true, message: '请选择广告位', trigger: 'change' }
  ],
})

// 依赖其它选项传参
const adunitOtherParams = computed(() => {
  return {
    no_policy: 0,
    investId: formData.adver
  }
})

// 模拟接口回填信息
setTimeout(async () => {
  formData.editAdverData = [{ // 广告主回填
    id: 63787,
    name: 'vv-iptv'
  }]

  await nextTick() // 有联动关系时，需要等前一个的更新后，再更新当前值
  formData.editAdunitData = [{ // 广告位回填
    id: 63843,
    name: 'vv-联合控量-test'
  }]

  formData.editInvestData = [{ // 投放回填
    id: 63787,
    name: 'vv-iptv'
  }, {
    id: 63843,
    name: 'vv-联合控量-test'
  }]

  await nextTick()
  formData.editLoading = false
}, 500)
</script>
