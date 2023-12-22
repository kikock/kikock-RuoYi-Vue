<template>
  <div v-loading="loading" style="height: 100%;width: 100%"> 正在加载中....</div>
</template>
<script setup name="oauthBack">
import useUserStore from '@/store/modules/user'
import {onErrorCaptured, onMounted, ref} from 'vue';
import { getToken } from '@/utils/auth'
import Cookies from 'js-cookie';
import {useRouter} from 'vue-router'
const userStore = useUserStore()
const loading = ref(true);
const route = useRoute();
const router = useRouter();
onMounted(() => {
  const formBody = {
    uuid: Cookies.get('oauth-uuid'),
    source: Cookies.get('oauth-source'),
    bind : Cookies.get('oauth-bind'),
    userId: Cookies.get("oauth-userId"),
    token : Cookies.get("oauth-uuid"),
    authCallback: {
      code: getQueryParam('code'), // 使用一个函数来获取路由参数，例如 'code'
      state: getQueryParam('state'),
      auth_code: getQueryParam('auth_code'),
      authorization_code: getQueryParam('authorization_code')
    }
  };
  userStore.oauthLogin(formBody.source,formBody,true).then((res) => {
    console.log("跳转:",res);
    router.push({ path: res.url ,query: res });
  }).catch((error) => {
    console.log("错误:",error);
    loading.value = false;
    router.push({ path: "/" ,query: { msg :error} });
  });

});

onErrorCaptured(() => {
  alert("销毁")
  loading.value = false; // 在组件被销毁时确保 loading 状态被设置回 false
});

// 辅助函数，用于获取路由参数，例如 'code' 或 'redirect'
function getQueryParam(paramName) {
  const name = route && route.query[paramName]
  if (!name) {
      return ""
  }
  console.log(name);
  return name;
}
</script>
