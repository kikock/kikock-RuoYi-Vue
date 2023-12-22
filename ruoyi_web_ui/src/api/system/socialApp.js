import request from '@/utils/request'

// 查询社交应用参数列表
export function listSocialApp(query) {
    return request({
        url: '/system/socialApp/list',
        method: 'get',
        params: query
    })
}

// 查询社交应用参数详细
export function getSocialApp(id) {
    return request({
        url: '/system/socialApp/' + id,
        method: 'get'
    })
}

// 新增社交应用参数
export function addSocialApp(data) {
    return request({
        url: '/system/socialApp',
        method: 'post',
        data: data
    })
}

// 修改社交应用参数
export function updateSocialApp(data) {
    return request({
        url: '/system/socialApp',
        method: 'put',
        data: data
    })
}

// 删除社交应用参数
export function delSocialApp(id) {
    return request({
        url: '/system/socialApp/' + id,
        method: 'delete'
    })
}

// 取消社交绑定
export function socialUnbind(authId) {
    return request({
        url: '/oauth/unbind/'+authId,
        method: 'get',
    })
}
