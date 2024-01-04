// import { debounce } from '@/utils/tools'
// import request from '@/utils/request'

import request from '@/utils/request' //
const debounce = (func, wait) => {
  let timeId;
  return function (...args) {
    let _this = this;
    clearTimeout(timeId);
    timeId = setTimeout(function () {
      func.apply(_this, args);
    }, wait);
  }
}
// 当前组件唯一id
let idIndex = 0

// 指令相关
export const useDirectivesEffect = () => {
  // 保证当前组件唯一
  const onlyId = `more_${idIndex++}_${new Date().getTime()}`

  // 监听滚动到底部时，执行
  const vLoadMoreDirective = {
    mounted(el, binding) {
      const selectDropDownWrap = document.querySelector(`.el-popper.${onlyId} .el-select-dropdown .el-select-dropdown__wrap`)
      selectDropDownWrap?.addEventListener('scroll', function () {
        const scrollToBottom = Math.floor(this.scrollHeight - this.scrollTop) <= this.clientHeight
        if (scrollToBottom) {
          binding.value()
        }
      })
    }
  }

  // 下拉框内插入搜索框
  const vSearchDirective = {
    mounted(el, binding) {
      const selectDropDown = document.querySelector(`.el-popper.${onlyId} .el-select-dropdown`)
      const searchDom = document.querySelector(`.more-filter-${onlyId}`)
      searchDom && selectDropDown?.prepend(searchDom)
    }
  }

  // 显示多选选中的值
  const vShowMulTextDirective = {
    mounted(el, binding) {
      if (binding.value) {
        const mulSelectDom = document.querySelector(`.more-wrap-text-${onlyId} .select-trigger`)
        const textDom = document.querySelector(`.more-sel-text-${onlyId}`)
        textDom && mulSelectDom?.prepend(textDom)
      }
    }
  }

  return { onlyId, vLoadMoreDirective, vSearchDirective, vShowMulTextDirective }
}

// 页面数据
export const useListEffect = (props, emit, selectMoreRef) => {
  const list = ref([]) // 列表数据
  const keywords = ref('') // 搜索关键字
  const searchSet = reactive({
    init: true, // 是否是第一次加载
    pageNum: 1, // 当前页数
    loading: false, // 正在请求接口
    hasMore: false,  // 有更多数据
  })

  // 请求接口获取数据
  let controller // 接口api
  const getList = async () => {
    if (searchSet.loading) {
      return false
    }

    // 中断上次的请求。防止加载分页数据时，搜索内容的结果是上一次的分页内容
    controller && controller.abort()
    controller = new AbortController()

    searchSet.loading = true
    const pageNum = searchSet.pageNum++;
    const pageSize = props.pageSize;
    const params = Object.assign({}, props.otherParams, {
      [props.pageNumName]: pageNum,
      [props.pageSizeName]: pageSize,
      [props.keyName]: keywords.value
    })

    const result = await request.post(props.url, params, {
      signal: controller.signal
    })

    if (result.code === 'ERR_CANCELED') { // 已取消不再往下执行
      return false
    }

    searchSet.loading = false
    const { total, rows = [] } = result
    searchSet.hasMore = pageNum * pageSize < total
    list.value = list.value.concat(rows)
  }

  // 展示加载更多选项
  const showLoading = computed(() => {
    return searchSet.hasMore || searchSet.loading
  })

  // select框的值
  const selectVal = computed({
    get: function () {
      return props.modelValue
    },
    set: async function (val) {
      console.log("select框的值", val);
      emit('update:modelValue', val)
      let row =null;
      list.value.forEach(item => {
        if (item.id === val) {
          row =item;
        }
      })
      console.log("单选择内容", row);
      emit('change', row)
    }
  })

  // 多选时选中的文字
  const selectedArrText = props.multiple ? computed(() => {
    let text = []
    const selectedArr = toRaw(selectMoreRef?.value?.selected)
    selectedArr?.forEach((item) => {
      text.push(item.currentLabel)
    })
    return text.join(',')
  }) : ''

  // 重置请求数据
  const resetList = () => {
    // 重置请求状态
    searchSet.pageNum = 1
    searchSet.loading = false
    searchSet.isFinish = false
    list.value = []

    // 请求数据
    getList()
  }

  // 展示时请求接口
  const visibleChange = (visible) => {
    emit('visibleChange', visible)
    if (visible && searchSet.init) {
      searchSet.init = false
      resetList()
    }
  }

  // 搜索
  watch(keywords, debounce(resetList, 300))

  // 监听传参改变，需要把已选值置空，当再次展开时，重新请求接口
  watch(() => props.otherParams, () => {
    searchSet.init = true
    selectVal.value = props.multiple ? [] : ''
  }, { deep: true })

  // 编辑回填
  watch(() => props.editData, (editData) => {
    editData = toRaw(editData)
    if (editData?.length) { // 编辑回填的已选择的数组
      // 回填选项
      list.value = editData
      // 回填id
      let editIdArr = []
      editData.forEach((item) => {
        editIdArr.push(item[props.value])
      })
      selectVal.value = props.multiple ? editIdArr : editIdArr[0]
    }
  })

  return { selectVal, selectedArrText, keywords, showLoading, list, getList, visibleChange }
}

// 页面配置文字
export const useTextEffect = (props) => {

  const optionText = (id, name) => {
    return props.showId ? `【${id}】${name}` : name
  }

  const searchPlaceholder = computed(() => props.searchPldText ? props.searchPldText : (props.showId ? '模糊搜索ID或名称' : '模糊搜索名称'))

  const selectPlaceholder = computed(() => props.selectPldText ? props.selectPldText : '请选择')

  return { optionText, searchPlaceholder, selectPlaceholder }
}
