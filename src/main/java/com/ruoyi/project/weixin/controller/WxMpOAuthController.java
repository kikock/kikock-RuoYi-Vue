package com.ruoyi.project.weixin.controller;

import com.ruoyi.common.utils.HttpTools;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author cao_wencao
 * @ClassName: WxMpOAuthController
 * @Description: 微信网页授权
 * @date 2018年7月4日 下午1:27:58
 */
@RestController
@RequestMapping("/wechat/oauth")
@Slf4j
public class WxMpOAuthController {
    /** 默认用户密码 */
    @Value("${mock.passWord}")
    private String passWord;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private ISysWxUserService sysWxUserService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/oauthInfo")
    public AjaxResult oauthInfo(@RequestParam String code) throws Exception {
        AjaxResult ajax = AjaxResult.success();
        try{
            log.info("code======"+code);
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            // 获取openId
            String openId = wxMpOAuth2AccessToken.getOpenId();
            log.info("openId={}", openId);
            //第二个参数为null时默认为zh_CN
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId, null);
            log.info("网页授权获取用户信息 ：" + wxMpUser.toString());
            //将微信粉丝信息wxMpUser保存到数据库
            SysWxUser sysWxUser = sysWxUserService.selectSysWxUserByOpenId(wxMpUser.getOpenId());
            sysWxUser = convertSysWxUser(wxMpUser, sysWxUser);
            if (StringUtils.isNotBlank(sysWxUser.getId())) {
                if (Objects.isNull(sysWxUser.getUserId())) {
                    registerUser(sysWxUser);
                }
                sysWxUserService.updateSysWxUser(sysWxUser);
            } else {
                registerUser(sysWxUser);
                sysWxUserService.insertSysWxUser(sysWxUser);
            }
            LoginUser loginUser = new LoginUser();
            SysUser user = new SysUser();
            user.setUserName(openId);
            user.setLoginDate(new Date());
            loginUser.setUser(user);
            String token = tokenService.createToken(loginUser);
            log.info("token===="+token);
            ajax.put("data",token);
        } catch (Exception ex) {
            log.error("【微信网页授权】{}", ex.getMessage());
            throw new RuntimeException("网页授权失败！");
        }
        return ajax;
    }


    @GetMapping("/oauthInfos")
    public void oauthInfos(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String backUrl = request.getParameter("backUrl");
        backUrl = URLEncoder.encode(backUrl, "UTF-8");
        String host = request.getRequestURL().toString();

        host = host.replaceAll("/oauthInfo", "/getInfo") + "?backUrl=" + backUrl;
        // String redirect_uri = URLEncoder.encode(host, "UTF-8");
        // 构建授权URL，方法内会自动encode
        String redirectUri = wxMpService.oauth2buildAuthorizationUrl(host, WxConsts.OAuth2Scope.SNSAPI_BASE, "1");
        // 日志
        log.info("redirectUri地址={}", redirectUri);
        response.sendRedirect(redirectUri);
    }

    @GetMapping("/getInfos")
    public void getInfos(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String backUrl = "";
        String code = request.getParameter("code");
        log.info("code======"+code);
        backUrl = request.getParameter("backUrl");
        backUrl = URLDecoder.decode(backUrl, "UTF-8");

        // 通过code换取access token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        String openId = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            // 获取openId
            openId = wxMpOAuth2AccessToken.getOpenId();
            log.info("openId={}", openId);
            //第二个参数为null时默认为zh_CN
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId, null);
            //将微信粉丝信息wxMpUser保存到数据库
            SysWxUser sysWxUser = sysWxUserService.selectSysWxUserByOpenId(wxMpUser.getOpenId());
            sysWxUser = convertSysWxUser(wxMpUser, sysWxUser);
            if (StringUtils.isNotBlank(sysWxUser.getId())) {
                sysWxUserService.updateSysWxUser(sysWxUser);
            } else {
                sysWxUserService.insertSysWxUser(sysWxUser);
            }
            log.info("网页授权获取用户信息 ：" + wxMpUser.toString());
            Map<String, Object> params = HttpTools.getUrlParamMap(HttpTools.getUrlParam(backUrl));
            // 重新组装参数
            params.put("curoauth", openId);
            String baseUrl = HttpTools.getUrlBase(backUrl);
            backUrl = baseUrl + "?" + HttpTools.getUrlParamsByMap(params);
            log.info("链接微信授权成功 结束======" + backUrl);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e.getMessage());
            throw new RuntimeException("网页授权失败！");
        }
        response.sendRedirect(backUrl);
    }

    /**
     * 微信用户转为系统微信用户
     * @param userWxInfo 微信用户
     * @param sysWxUser 系统微信用户
     */
    private SysWxUser convertSysWxUser(WxMpUser userWxInfo,SysWxUser sysWxUser) {

        if (Objects.nonNull(sysWxUser)) {
            sysWxUser.setSubscribeNum(sysWxUser.getSubscribeNum()+1);
        } else {
            sysWxUser = new SysWxUser();
            sysWxUser.setSubscribeNum(1L);
            sysWxUser.setOpenId(userWxInfo.getOpenId());
        }
        sysWxUser.setAppType("2");
        sysWxUser.setSubscribe("1");
        sysWxUser.setSubscribeScene(userWxInfo.getSubscribeScene());
        sysWxUser.setSubscribeTime(new Date());
        sysWxUser.setNickName(userWxInfo.getNickname());
        sysWxUser.setSex(userWxInfo.getSex()==1?"1":"0");
        sysWxUser.setCity(userWxInfo.getCity());
        sysWxUser.setCountry(userWxInfo.getCountry());
        sysWxUser.setProvince(userWxInfo.getProvince());
        sysWxUser.setLanguage(userWxInfo.getLanguage());
        sysWxUser.setHeadimgUrl(userWxInfo.getHeadImgUrl());
        sysWxUser.setUnionId(userWxInfo.getUnionId());
        sysWxUser.setGroupId(userWxInfo.getGroupId()+"");
        Long[] tagIds = userWxInfo.getTagIds();
        if (tagIds != null && tagIds.length > 0) {
            sysWxUser.setTagidList(StringUtils.join(tagIds));
        }
        sysWxUser.setQrSceneStr(userWxInfo.getQrSceneStr());
        return sysWxUser;
    }

    /**
     * 注册用户
     * @param sysWxUser 微信用户对象
     */
    private void registerUser(SysWxUser sysWxUser) {
        SysUser sysUser = userMapper.checkUserNameUnique(sysWxUser.getOpenId());
        if (Objects.isNull(sysUser)) {
             sysUser = new SysUser();
            sysUser.setUserName(sysWxUser.getOpenId());
            sysUser.setNickName(sysWxUser.getOpenId());
            sysUser.setPassword(SecurityUtils.encryptPassword(passWord));
            userMapper.insertUser(sysUser);
            sysWxUser.setUserId(sysUser.getUserId());
        }
    }

}
