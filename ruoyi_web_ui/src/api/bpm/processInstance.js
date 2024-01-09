import request from '@/utils/request'

export function getMyProcessInstancePage(query) {
  return request({
    url: '/bpm/process-instance/my-page',
    method: 'get',
    params: query
  })
}

export function createProcessInstance(data) {
  return request({
    url: '/bpm/process-instance/create',
    method: 'POST',
    data: data
  })
}

export function cancelProcessInstance(id, reason) {
  return request({
    url: '/bpm/process-instance/cancel',
    method: 'DELETE',
    data: {
      id,
      reason
    }
  })
}
// 获得指定流程实例
export function getProcessInstance(query) {
  return request({
    url: '/bpm/process-instance/findById',
    method: 'get',
    params: query
  })
}
