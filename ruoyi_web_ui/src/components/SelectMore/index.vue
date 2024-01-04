<template>
  <div>
  <el-select class="selectMoreCss" ref="selectMoreRef" v-model="selectVal" :class="mulSelectTextCls" :multiple="multiple"
             v-show-mul-text-directive="multipleShowText" :collapse-tags="multiple && !multipleShowText"
             :collapse-tags-tooltip="multiple && !multipleShowText" :placeholder="selectPlaceholder" :popper-class="onlyId"
             :disabled="disabled" v-load-more-directive="getList" v-search-directive @visible-change="visibleChange"
             clearable readonly>
    <el-option v-for="item in list" :key="item[value]" :label="optionText(item[value], item[label])"
               :value="item[value]">
      <!-- option文字 -->
      <p v-if="multiple" :title="optionText(item[value], item[label])">
        <label class="el-checkbox">
          <span class="el-checkbox__inner"></span>
          <span class="option-text" v-text="optionText(item[value], item[label])"></span>
        </label>
      </p>
      <p v-else class="option-text" v-text="optionText(item[value], item[label])"
         :title="optionText(item[value], item[label])"></p>

    </el-option>
    <el-option v-if="showLoading" value="" disabled>
      <el-icon  class="is-loading loading-icon" ><Loading /></el-icon>正在加载中...
    </el-option>
  </el-select>

  <!-- 多选时的显示文字 -->
  <div v-if="multipleShowText" :class="`more-sel-text-${onlyId}`" class="more-sel-text">
    <el-tooltip effect="dark" placement="right-start" :offset="40" :disabled="!selectedArrText">
      <template #content>
        <p class="tool-tip-text">{{ selectedArrText }}</p>
      </template>
      <div class="text-wrap">
        <input class="el-input__inner" readonly type="text" :value="selectedArrText">
      </div>
    </el-tooltip>
  </div>

  <!-- select里input搜索框 -->
  <el-form @submit.prevent @click.stop :class="`more-filter-${onlyId}`" class="more-filter">
    <el-form-item>
      <el-input v-model.trim="keywords" clearable :placeholder="searchPlaceholder">
      </el-input>
    </el-form-item>
  </el-form>
  </div>
</template>

<script setup name="moreSelect">
import {useDirectivesEffect, useListEffect, useTextEffect} from './js/index'

const props = defineProps({
  url: { // 远程地址
    required: true,
    type: String,
    default: ''
  },
  /*
   当需要编辑回填时，整体页面需要加个loading，让该组件不能点击。
   或判断是编辑页面，将该组件置为disabled，等有editName后，再把disabled置为false
  */
  editData: { // 编辑回填选中的列表，编辑时必须
    type: Array,
    default() {
      return []
    }
  },
  disabled: { // 是否禁用
    type: Boolean,
    default: false,
  },
  value: { // value配置项
    type: String,
    default: 'id'
  },
  label: { // label配置项
    type: String,
    default: 'name'
  },
  /*
   当额外传参是动态变化的，需要用响应式的方式传进来
  */
  otherParams: { // 接口传参
    type: Object,
    default() {
      return {}
    }
  },
  multiple: {
    type: Boolean, // 是否多选
    default: false,
  },
  multipleShowText: { // 是否把多选结果显示成文字
    type: Boolean,
    default: false,
  },
  pageSize: { // 每次传参个数
    type: Number,
    default: 20,
  },
  keyName: { // 搜索条件的key名
    type: String,
    default: 'keywords'
  },
  pageNumName: { // 搜索条件的当前页名
    type: String,
    default: 'pageNum'
  },
  pageSizeName: { // 搜索条件的每页个数名
    type: String,
    default: 'pageSize'
  },
  showId: { // 是否显示id
    type: Boolean,
    default: true,
  },
  searchPldText: { // 搜索显示文字
    type: String,
    default: ''
  },
  selectPldText: { // 下拉框显示文字
      type: String,
      default: ''
  },
  modelValue: { // v-model的隐藏传参，无需手动传
    type: [Number, String, Array]
  },
})

const emit = defineEmits(['change', 'visibleChange', 'update:modelValue'])

const selectMoreRef = ref()

// 动态配置项
const { optionText, searchPlaceholder, selectPlaceholder } = useTextEffect(props)

// 指令
const { onlyId, vLoadMoreDirective, vSearchDirective, vShowMulTextDirective } = useDirectivesEffect()

// 多选样式
const mulSelectTextCls = computed(() => {
  return {
    'more-wrap': props.multiple,
    'more-wrap-text': props.multipleShowText,
    [`more-wrap-text-${onlyId}`]: props.multipleShowText
  }
})

// 搜索和列表
const { selectVal, selectedArrText, keywords, showLoading, list, getList, visibleChange } = useListEffect(props, emit, selectMoreRef)
</script>

<style lang="scss" scoped>
.more-filter {
  padding: 10px 10px 0;
  min-width: 230px;

  .el-form-item {
    margin-bottom: 0
  }
}

.option-text {
  margin-left: 5px;
  margin-top: 0px;
  overflow: hidden;
  white-space: nowrap;
  word-break: keep-all;
  text-overflow: ellipsis;
  max-width: 300px;
}

.loading-icon {
  margin-right: 5px;
  vertical-align: middle;
}

// 多选时显示原有的标签样式
.more-wrap :deep(.el-select-tags-wrapper.has-prefix) {
  display: flex;
  flex-wrap: nowrap;
}

// 多选时显示文字样式
.more-wrap-text {
  :deep(.el-select__tags) {
    display: none;
  }

  .more-sel-text {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 30px;
    z-index: var(--el-index-normal);

    .text-wrap {
      padding: 1px 11px;
    }
  }
}

.tool-tip-text {
  max-width: 600px;
  max-height: 300px;
  overflow-y: auto;
}
.el-select-dropdown.is-multiple .el-select-dropdown__item{
  height: 50px;
}
// 多选options样式
.el-select-dropdown.is-multiple .el-select-dropdown__item.selected {
  &:after {
    display: none;
  }

  .el-checkbox .el-checkbox__inner {
    background-color: var(--el-checkbox-checked-bg-color);
    border-color: var(--el-checkbox-checked-input-border-color);
    &:after {
      transform: rotate(45deg) scaleY(1);
    }
  }

}
</style>
<style>
/* 多选样式选中文字弹出样式 */
.el-select__collapse-tags {
  max-width: 500px;
  max-height: 200px;
  overflow: auto;
  padding-right: 10px;
}
</style>
