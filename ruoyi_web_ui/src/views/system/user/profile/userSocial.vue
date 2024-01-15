<template>
  <el-table :data="socialUserApp" :show-header="false">
    <el-table-column label="社交平台" align="left" width="120">
      <template v-slot="scope">
        <img style="height:20px;vertical-align: middle;" :src="`/src/assets/icons/${scope.row.type}.png`"/>
        {{ scope.row.name }}
      </template>
    </el-table-column>
    <el-table-column label="操作" align="left">
      <template v-slot="scope">
        <div v-if="scope.row.status===0">
          未绑定
          <el-button size="large" link   @click="bind(scope.row)">(绑定)</el-button>
        </div>
        <div v-else>
          已绑定
          <el-button size="large" link  @click="unbind(scope.row)">(解绑)</el-button>
        </div>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {getOauthUrl} from "@/api/login";
const emit = defineEmits();
import Cookies from 'js-cookie'
import {useRouter} from 'vue-router'
import {socialUnbind} from '@/api/system/socialApp'

const {proxy} = getCurrentInstance();

const router = useRouter();
const props = defineProps({
  socialUserApp: {
    type: Array,
    default: function() {
      return []
    }
  },
  user: {
    type: Object
  },
  userUUID: {
    type: String
  }
});

function bind(row) {
  getOauthUrl(row.type).then((res) => {
    Cookies.set("oauth-uuid", res.uuid)
    Cookies.set("oauth-source", res.source)
    Cookies.set("oauth-bind", true)
    Cookies.set("oauth-userId", props.user.userId)
    Cookies.set("oauth-uuid",props.userUUID),
    window.location = res.authorizeUrl;
  });
}

function unbind(row) {
  socialUnbind(row.authId).then(resp => {
    emit("updateOauth",resp);
  });

}



</script>
