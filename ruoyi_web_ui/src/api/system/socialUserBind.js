import request from '@/utils/request'

// 查询三方用户列表
export function listSocialUserBind(query) {
return request({
url: '/system/socialUserBind/list',
method: 'get',
params: query
})
}

// 查询三方用户详细
export function getSocialUserBind(id) {
return request({
url: '/system/socialUserBind/' + id,
method: 'get'
})
}

// 新增三方用户
export function addSocialUserBind(data) {
return request({
url: '/system/socialUserBind',
method: 'post',
data: data
})
}

// 修改三方用户
export function updateSocialUserBind(data) {
return request({
url: '/system/socialUserBind',
method: 'put',
data: data
})
}

// 删除三方用户
export function delSocialUserBind(id) {
return request({
url: '/system/socialUserBind/' + id,
method: 'delete'
})
}
