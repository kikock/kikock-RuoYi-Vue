<script>
import config from './config'
import {getToken, removeToken, setToken} from '@/utils/auth'
import * as dd from 'dingtalk-jsapi';
import {dingLogin} from '@/api/dingtalk'
import store from '@/store'

  export default {
    onLaunch: function() {
      this.initApp()
    },
    methods: {
      // 初始化应用
      initApp() {
        // 初始化应用配置
        this.initConfig()
        // 检查用户登录状态
        //#ifdef H5
        this.checkLogin()
        //#endif
      },
      initConfig() {
        // removeToken()
        this.globalData.config = config
      },
      checkLogin() {
        if (!getToken()) {
          if (dd.env.platform !== 'notInDingTalk') {
            var _this = this;
            dd.ready(function () {
              // 钉钉获取个人信息
              dd.runtime.permission.requestAuthCode({
                corpId: config.corpId,
                onSuccess: function (result) {
                  dingLogin(result.code).then(res => {
                    setToken(res.token,28800);
                    // // 设置用户信息
                    store.dispatch('GetInfo').then(res => {
                      console.log(res);
                    })
                  })
                },
                onFail: function (err) {
                  _this.$modal.msgError("获取个人信息异常:" + err.errorMessage);
                }
              })
            })
          } else {
            console.log("非钉钉环境,跳转登录页面");
            // this.$tab.reLaunch('/pages/login')
          }
        }
      },
    }
  }
</script>

<style lang="scss">
  @import '@/static/scss/index.scss'
</style>
