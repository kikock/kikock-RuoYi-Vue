import request from '@/utils/request'

// 查询短信渠道列表
export function listSmsChannel(query) {
  return request({
    url: '/sms/smsChannel/list',
    method: 'get',
    params: query
  })
}

// 查询短信渠道详细
export function getSmsChannel(id) {
  return request({
    url: '/sms/smsChannel/' + id,
    method: 'get'
  })
}

// 新增短信渠道
export function addSmsChannel(data) {
  return request({
    url: '/sms/smsChannel',
    method: 'post',
    data: data
  })
}

// 修改短信渠道
export function updateSmsChannel(data) {
  return request({
    url: '/sms/smsChannel',
    method: 'put',
    data: data
  })
}

// 删除短信渠道
export function delSmsChannel(id) {
  return request({
    url: '/sms/smsChannel/' + id,
    method: 'delete'
  })
}
