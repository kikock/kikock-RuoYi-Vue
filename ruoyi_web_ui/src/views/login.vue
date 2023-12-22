<template>
  <div class="container">
    <div class="logo"></div>
    <!-- 登录区域 -->
    <div class="content">
      <!-- 配图 -->
      <div class="pic"></div>
      <!-- 表单 -->
      <div class="field">
        <!-- [移动端]标题 -->
        <h2 class="mobile-title">
          <h3 class="title">后台管理系统</h3>
        </h2>

        <!-- 表单 -->
        <div class="form-cont">
          <el-tabs class="form" v-model="loginType" style=" float:none;">
            <el-tab-pane label="账号密码登录" name="uname">
            </el-tab-pane>
          </el-tabs>

          <div>
            <el-form ref="loginRef"  :model="loginForm" :rules="loginRules" class="login-form">
              <!-- 账号密码登录 -->
                <el-form-item prop="username">
                  <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
                    <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                  </el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
                            @keyup.enter="handleLogin">
                    <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                  </el-input>
                </el-form-item>
              <el-form-item prop="code" v-if="captchaEnabled">
                <el-input
                    v-model="loginForm.code"
                    size="large"
                    auto-complete="off"
                    placeholder="验证码"
                    style="width: 63%"
                    @keyup.enter="handleLogin"
                >
                  <template #prefix><svg-icon icon-class="validCode" class="el-input__icon input-icon" /></template>
                </el-input>
                <div class="login-code">
                  <img :src="codeUrl" @click="getCode" class="login-code-img"/>
                </div>
              </el-form-item>
                <el-checkbox v-model="loginForm.rememberMe" style="margin:0 0 25px 0;">记住密码</el-checkbox>


              <!-- 下方的登录按钮 -->
              <el-form-item style="width:100%;">
                <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
                           @click.native.prevent="handleLogin">
                  <span v-if="!loading">登 录</span>
                  <span v-else>登 录 中...</span>
                </el-button>
              </el-form-item>

              <!--  社交登录 -->
              <el-form-item style="width:100%;">
                <div class="oauth-login" style="display:flex">
                  <div class="oauth-login-item" v-for="item in sysUserSocialTypeEnum" :key="item.type"  @click="doSocialLogin(item)">
                    <img :src="item.img" height="25px" width="25px" alt="登录" >
                    <span>{{item.title}}</span>
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>

    <!-- 图形验证码 -->
    <Verify ref="verify" :captcha-type="'blockPuzzle'" :img-size="{width:'400px',height:'200px'}"
            @success="handleLogin" />

    <!-- footer -->
    <div class="footer">
      Copyright © 2020-2023 kikock.tk All Rights Reserved.
    </div>
  </div>

</template>

<script setup>
import {getCodeImg, getOauthUrl, getPublicKey,getOauthData} from "@/api/login";
import Cookies from "js-cookie";
import {cookiesDecrypt, cookiesEncrypt, encrypt} from "@/utils/jsencrypt";
import useUserStore from '@/store/modules/user'
import {getCurrentInstance, ref} from 'vue'
import {useRouter} from 'vue-router'
import {SystemUserSocialTypeEnum} from '@/utils/constants'

const userStore = useUserStore()
const router = useRouter();
const { proxy } = getCurrentInstance();
const loginForm = ref({
  username: "",
  password: "",
  rememberMe: false,
  code: "",
  uuid: ""
});
const loginType = ref("uname");
const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  code: [{ required: true, trigger: "change", message: "请输入验证码" }]
};

const codeUrl = ref("");
const loading = ref(false);
const isEncryption = ref(true);
const oauthUrl=ref('')
// 验证码开关
const captchaEnabled = ref(true);
const sysUserSocialTypeEnum = ref([])


// 注册开关
const register = ref(false);
const redirect = ref(undefined);

function handleLogin() {
  proxy.$refs.loginRef.validate(valid => {
    if (valid) {
      loading.value = true;
      // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
      // 调用action的登录方法
      findPublicKey().then(res=>{
        let publicKey = res.publicKey
        // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
        if (loginForm.value.rememberMe) {
          Cookies.set("username", loginForm.value.username, { expires: 30 });
          //前端cookies 加密
          Cookies.set("password", cookiesEncrypt(loginForm.value.password), { expires: 30 });
          Cookies.set("rememberMe", loginForm.value.rememberMe, { expires: 30 });
        } else {
          // 否则移除
          Cookies.remove("username");
          Cookies.remove("password");
          Cookies.remove("rememberMe");
        }
        //后端公钥加密
        loginForm.value.password=encrypt(loginForm.value.password,publicKey)
        userStore.login(loginForm.value).then(() => {
          router.push({ path: redirect.value || "/" });
        }).catch(() => {
          loading.value = false;
          // 重新获取验证码
          if (captchaEnabled.value) {
            getCode();
          }
        });
      })
    }
  });
}

function getCode() {
  getCodeImg().then(res => {
    captchaEnabled.value = res.captchaEnabled === undefined ? true : res.captchaEnabled;
    if (captchaEnabled.value) {
      codeUrl.value = "data:image/gif;base64," + res.img;
      loginForm.value.uuid = res.uuid;
    }
  });
}

function getCookie() {
  const username = Cookies.get("username");
  const password = Cookies.get("password");
  const rememberMe = Cookies.get("rememberMe");
  loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    //前端cookies 解密
    password: password === undefined ? loginForm.value.password : cookiesDecrypt(password),
    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
  };
}
  //获取 公钥
function findPublicKey() {
  return new Promise((resolve, reject) => {
    getPublicKey()
        .then(res => {
          resolve(res)
        })
        .catch(error => {
          reject(error)
        })
  })
}

function  doSocialLogin(socialTypeEnum) {
  // 设置登录中
  loading.value = true;
    // 进行跳转
  getOauthUrl(socialTypeEnum.source).then((res) => {
    Cookies.set("oauth-uuid", res.uuid)
    Cookies.set("oauth-source", res.source)
    Cookies.set("oauth-bind", false)
    Cookies.set("oauth-userId", "")
    window.location=res.authorizeUrl;
    });
}
//初始化第三方登录数据
function  initOauthData() {

  // 进行跳转
  getOauthData().then((res) => {
    console.log(res);
    if(res.code===200){
      // 遍历 res.data
      res.data.forEach((item) => {
        // 根据每个项目的 type 构建对应的参数对象
        if (item.type === 20) {
          sysUserSocialTypeEnum.value.push({
            title: "", // 根据实际需要设置标题
            type: item.type,
            source: item.source,
            img: `/src/assets/icons/${item.source}.png`, // 根据实际路径设置图片路径
          });
        }
      });
      // 输出构建的参数数组
      console.log(sysUserSocialTypeEnum.value);
    }

  });
}
initOauthData();
getCode();
getCookie();
</script>

.<style lang="scss" scoped>
@import "@/assets/styles/login.scss";


.oauth-login {
  display: flex;
  align-items: center;
  cursor:pointer;
}
.oauth-login-item {
  display: flex;
  align-items: center;
  margin-right: 10px;
}
.oauth-login-item img {
  height: 25px;
  width: 25px;
}
.oauth-login-item span:hover {
  text-decoration: underline red;
  color: red;
}
.sms-login-mobile-code-prefix {
  :deep(.el-input__prefix) {
    top: 22%;
  }
}
</style>
