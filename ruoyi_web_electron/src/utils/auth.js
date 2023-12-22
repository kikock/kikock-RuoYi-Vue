const ElectronStore = require('electron-store');
const electronStore = new ElectronStore({encryptionKey: 'r23rfdsa'});
const TokenKey = 'Admin-Token'

export function getToken() {
  return electronStore.get(TokenKey)
}

export function setToken(token) {
  return electronStore.set(TokenKey, token)
}

export function removeToken() {
  return electronStore.delete(TokenKey)
}
