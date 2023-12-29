import request from '@/utils/request'


// 查询流程模板
export function listModel(query) {
  return request({
    url: '/bpm/model/list',
    method: 'get',
    params: query
  })
}

export function getModel(id) {
  return request({
    url: '/bpm/model/' + id,
    method: 'get'
  })
}

export function updateModelFlowChart(data) {
  return request({
    url: '/bpm/model/flowChart/update',
    method: 'POST',
    data: data
  })
}

// 任务状态修改
export function updateModelState(id, state) {
  return request({
    url: '/bpm/model/update-state',
    method: 'put',
    data: {
      id,
      state
    }
  })
}

export function createModel(data) {
  return request({
    url: '/bpm/model/create',
    method: 'POST',
    data: data
  })
}

export function deleteModel(id) {
  return request({
    url: '/bpm/model/delete?id=' + id,
    method: 'DELETE'
  })
}

export function deployModel(id) {
  return request({
    url: '/bpm/model/deploy?id=' + id,
    method: 'POST'
  })
}
