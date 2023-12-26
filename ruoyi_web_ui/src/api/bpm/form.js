import request from '@/utils/request'

// 查询工作流的单定义列表
export function listForm(query) {
    return request({
        url: '/bpm/form/list',
        method: 'get',
        params: query
    })
}

// 查询工作流的单定义详细
export function getForm(id) {
    return request({
        url: '/bpm/form/' + id,
        method: 'get'
    })
}

// 新增工作流的单定义
export function addForm(data) {
    return request({
        url: '/bpm/form',
        method: 'post',
        data: data
    })
}

// 修改工作流的单定义
export function updateForm(data) {
    return request({
        url: '/bpm/form',
        method: 'put',
        data: data
    })
}

// 删除工作流的单定义
export function delForm(id) {
    return request({
        url: '/bpm/form/' + id,
        method: 'delete'
    })
}
