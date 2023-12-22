import request from '@/utils/request'

// 根据字典类型查询字典数据信息
export function getDicts (dictType) {
  return request({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  })
}

// 钉钉用户登录
export function dingLogin (code) {
    return request({
      url: '/ding/talk/dingLogin',
      method: 'get',
      params: {code: code}
    })
}

// 获取JSAPI鉴权
export function getJsApi () {
  return request({
    url: '/ding/talk/getJsApi',
    method: 'get',
  })
}

// 查询公告列表
