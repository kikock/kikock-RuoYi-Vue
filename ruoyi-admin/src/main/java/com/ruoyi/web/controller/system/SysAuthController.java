package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.social.SocialUtil;
import com.ruoyi.framework.social.properties.SocialProperties;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.SysSocialUserBind;
import com.ruoyi.system.domain.vo.AuthCallbackVo;
import com.ruoyi.system.service.ISysSocialAppService;
import com.ruoyi.system.service.ISysSocialUserBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 第三方认证授权处理
 *
 * @author ruoyi
 */
@Slf4j
@RestController
@AllArgsConstructor
@ConditionalOnProperty(value = "social.enabled", havingValue = "true")
@Api(value = "第三方登陆", tags = "第三方登陆端点")
public class SysAuthController extends BaseController{
    private final SocialProperties socialProperties;

    @Autowired
    private ISysSocialUserBindService socialUserBindService;

    @Autowired
    private ISysSocialAppService sysSocialAppService;

    @Autowired
    private SysLoginService loginService;
    @Autowired
    private TokenService tokenService;

    /**
     * 授权完毕跳转
     */
    @ApiOperation(value = "授权登录（RequestMapping）")
    @RequestMapping("/oauth/render/{source}")
    public AjaxResult renderAuth(@PathVariable("source") String source, HttpServletResponse response){
        AjaxResult ajax = AjaxResult.success();
        AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
        String uuid = AuthStateUtils.createState();
        String authorizeUrl = authRequest.authorize(uuid);
        ajax.put("authorizeUrl", authorizeUrl);
        ajax.put("uuid", uuid);
        ajax.put("source", source);
        return ajax;
    }

    /**
     * 获取认证信息
     */
    @ApiOperation(value = "获取认证信息（RequestMapping）")
    @RequestMapping("/oauth/callback/{source}")
    public AjaxResult login(@PathVariable("source") String source, @RequestBody AuthCallbackVo callbackVo){
        AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
        AuthResponse<AuthUser> response = authRequest.login(callbackVo.getAuthCallback());
        if (response.ok()) {
            SysUser user = socialUserBindService.selectAuthUserByUuid(source + response.getData().getUuid());
            //已经登录
            if (callbackVo.isBind()) {
                // 已经登录--->绑定系统账号
                SysSocialUserBind authUser = new SysSocialUserBind();
                authUser.setSocialUuid(source + response.getData().getUuid());
                authUser.setUserId(callbackVo.getUserId());
                authUser.setNickName(response.getData().getNickname());
                authUser.setUserName(response.getData().getUsername());
                authUser.setEmail(response.getData().getEmail());
                authUser.setSocialType(source);
                socialUserBindService.insertSysSocialUserBind(authUser);
                tokenService.delLoginUser(callbackVo.getUuid());
                return loginService.loginSocial(callbackVo.getUserId()).put("url", callbackVo.isBind() ? "/user/profile" : "/").put("msg", "绑定社交账号成功");
            }
//                未登录
            if (StringUtils.isNotNull(user)) {
                return loginService.loginSocial(user.getUserId()).put("url", callbackVo.isBind() ? "/user/profile" : "/");
            } else {
                return AjaxResult.error("社交账号授权登录失败,请到个人中心绑定账号后操作!").put("url", callbackVo.isBind() ? "/user/profile" : "/");
            }
        } else {
            return AjaxResult.error("社交账号授权登录失败");
        }
    }

    /**
     * 撤销授权
     */
    @RequestMapping("/oauth/unbind/{authId}")
    public AjaxResult unbindAuthUser(@PathVariable("authId") String authId){
        return socialUserBindService.deleteAuthUser(authId);
    }

}
