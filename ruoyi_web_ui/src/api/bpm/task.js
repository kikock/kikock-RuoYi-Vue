import request from '@/utils/request'

export function getTodoTaskPage(query) {
  return request({
    url: '/bpm/task/todo-page',
    method: 'get',
    params: query
  })
}

export function getDoneTaskPage(query) {
  return request({
    url: '/bpm/task/done-page',
    method: 'get',
    params: query
  })
}

export function completeTask(data) {
  return request({
    url: '/bpm/task/complete',
    method: 'PUT',
    data: data
  })
}
/**
 * 通过审批
 */
export function approveTask(data) {
  return request({
    url: '/bpm/task/approve',
    method: 'PUT',
    data: data
  })
}
/**
 * 不通过审批
 */
export function rejectTask(data) {
  return request({
    url: '/bpm/task/reject',
    method: 'PUT',
    data: data
  })
}

/**
 * 驳回
 */
export function doBackStep(data) {
  return request({
    url: '/bpm/task/doBackStep',
    method: 'PUT',
    data: data
  })
}
/**
 * 转办
 */
export const delegateTask = async (data) => {
  return request({
    url: '/bpm/task/delegate',
    method: 'PUT',
    data: data
  })
}

/**
 * 委派
 */
export const stopProcess = async (data) => {
  return request({
    url: '/bpm/task/stopProcess',
    method: 'PUT',
    data: data
  })
}
export function updateTaskAssignee(data) {
  return request({
    url: '/bpm/task/update-assignee',
    method: 'PUT',
    data: data
  })
}

export function getTaskListByProcessInstanceId(processInstanceId) {
  return request({
    url: '/bpm/task/list-by-process-instance-id?processInstanceId=' + processInstanceId,
    method: 'get',
  })
}
