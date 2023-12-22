import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}
// 获取公钥
export function getPublicKey() {
  return request({
    url: '/getPublicKey',
    method: 'get',
  })
}
// 获取第三方登录数据
export function getOauthData() {
  return request({
    url: '/system/socialApp/getOauthData',
    headers: {
      isToken: false
    },
    method: 'get',
  })
}
// 获取回调地址--第三方登录过渡页面
export function getOauthUrl(param) {
  return request({
    url: '/oauth/render/'+param,
    headers: {
      isToken: false
    },
    method: 'get',
  })
}

export function oauthLogin(source,data) {
  return request({
    url: '/oauth/callback/'+source,
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

