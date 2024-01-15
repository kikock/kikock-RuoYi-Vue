import request from '@/utils/request'

// 查询用户组列表
export function listGroup(query) {
    return request({
        url: '/bpm/group/list',
        method: 'get',
        params: query
    })
}

// 查询用户组详细
export function getGroup(id) {
    return request({
        url: '/bpm/group/' + id,
        method: 'get'
    })
}

// 新增用户组
export function addGroup(data) {
    return request({
        url: '/bpm/group',
        method: 'post',
        data: data
    })
}

// 修改用户组
export function updateGroup(data) {
    return request({
        url: '/bpm/group',
        method: 'put',
        data: data
    })
}

// 删除用户组
export function delGroup(id) {
    return request({
        url: '/bpm/group/' + id,
        method: 'delete'
    })
}
