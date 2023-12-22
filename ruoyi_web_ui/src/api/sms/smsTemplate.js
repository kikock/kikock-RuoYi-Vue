import request from '@/utils/request'

// 查询短信模板列表
export function listSmsTemplate(query) {
  return request({
    url: '/sms/smsTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询短信模板详细
export function getSmsTemplate(id) {
  return request({
    url: '/sms/smsTemplate/' + id,
    method: 'get'
  })
}

// 新增短信模板
export function addSmsTemplate(data) {
  return request({
    url: '/sms/smsTemplate',
    method: 'post',
    data: data
  })
}

// 修改短信模板
export function updateSmsTemplate(data) {
  return request({
    url: '/sms/smsTemplate',
    method: 'put',
    data: data
  })
}

// 删除短信模板
export function delSmsTemplate(id) {
  return request({
    url: '/sms/smsTemplate/' + id,
    method: 'delete'
  })
}
