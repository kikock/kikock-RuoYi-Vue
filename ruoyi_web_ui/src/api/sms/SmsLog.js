import request from '@/utils/request'

// 查询短信日志列表
export function listSmsLog(query) {
  return request({
    url: '/sms/SmsLog/list',
    method: 'get',
    params: query
  })
}

// 查询短信日志详细
export function getSmsLog(id) {
  return request({
    url: '/sms/SmsLog/' + id,
    method: 'get'
  })
}

// 新增短信日志
export function addSmsLog(data) {
  return request({
    url: '/sms/SmsLog',
    method: 'post',
    data: data
  })
}

// 修改短信日志
export function updateSmsLog(data) {
  return request({
    url: '/sms/SmsLog',
    method: 'put',
    data: data
  })
}

// 删除短信日志
export function delSmsLog(id) {
  return request({
    url: '/sms/SmsLog/' + id,
    method: 'delete'
  })
}
