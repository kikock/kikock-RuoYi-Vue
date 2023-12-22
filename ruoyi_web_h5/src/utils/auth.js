const TokenKey = 'App-Token'

export function getToken() {
  let val = uni.getStorageSync(TokenKey)
  if (!val) {
    return null
  }
  console.log(val);
  val = JSON.parse(val)
  if (val.expire && Date.now() / 1000 - val.time > val.expire) {
    uni.removeStorageSync(key)
    return null
  }
  return val.data
}

/**
 *
 * @param {需要存储的缓存值} value
 * @param {过期时间，默认0表示永久有效} expire
 */
export function setToken(token,expire) {
  let obj = {
    data: token, //存储的数据
    time: Date.now() / 1000, //记录存储的时间戳
    expire: expire //记录过期时间，单位秒
  }
  return uni.setStorageSync(TokenKey, JSON.stringify(obj))
}


export function removeToken() {
  return uni.removeStorageSync(TokenKey)
}
