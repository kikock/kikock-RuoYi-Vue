import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'
import CryptoJS from 'crypto-js';



const key = 'jnvpucsymqrhfkgo'

// 加密
export function encrypt(txt,publicKey) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// cookies前端加密
export function cookiesEncrypt(txt) {
  return CryptoJS.AES.encrypt(txt, key).toString();
}
// cookies前端解密
export function cookiesDecrypt(txt) {
  var bytes  = CryptoJS.AES.decrypt(txt, key);
  return bytes.toString(CryptoJS.enc.Utf8);
}

