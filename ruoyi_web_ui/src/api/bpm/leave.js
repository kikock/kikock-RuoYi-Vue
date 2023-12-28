import request from '@/utils/request'

// 查询OA 请假申请列表
export function listLeave(query) {
return request({
url: '/bpm/oa/leave/list',
method: 'get',
params: query
})
}

// 查询OA 请假申请详细
export function getLeave(id) {
return request({
url: '/bpm/oa/leave/' + id,
method: 'get'
})
}

// 新增OA 请假申请
export function addLeave(data) {
return request({
url: '/bpm/oa/leave',
method: 'post',
data: data
})
}

// 修改OA 请假申请
export function updateLeave(data) {
return request({
url: '/bpm/oa/leave',
method: 'put',
data: data
})
}

// 删除OA 请假申请
export function delLeave(id) {
return request({
url: '/bpm/oa/leave/' + id,
method: 'delete'
})
}
