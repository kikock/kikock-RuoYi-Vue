package com.ruoyi.project.miniapp.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginBody;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.miniapp.config.WxMaConfiguration;
import com.ruoyi.project.miniapp.controller.vo.MiniappLoginVo;

import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;
import com.ruoyi.project.weixin.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /** 默认用户密码 */
    @Value("${mock.passWord}")
    private String passWord;
    @Autowired
    private ISysWxUserService sysWxUserService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private TokenService tokenService;


    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public String login(@PathVariable String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }

    /**
     * 微信公众号首次登录并绑定微信方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 检测用户名是否存在
        SysUser user = userMapper.checkUserNameUnique(loginBody.getUsername());
        if (Objects.isNull(user)) {
          return AjaxResult.error("该用户不存在!");
        }
        SysUser sysUser = userMapper.selectUserByUserName(loginBody.getUsername());

        //验证密码
        if(SecurityUtils.matchesPassword(loginBody.getPassword(), sysUser.getPassword())){
            LoginUser loginUser = new LoginUser();
            sysUser.setLoginDate(new Date());
            loginUser.setUser(sysUser);
            String token = "";
            // String userType = sysUser.getUserType();
            Map<String,Object> data = new HashMap<>(5);
            data.put("login", false);
            //判断人员类型
            // if(userType.equals(loginBody.getCode())){
            //     data.put("login", true);
            //     token = tokenService.createToken(loginUser);
            //     logger.info("【微信小程序用户密码登录token】{}",token);
            // }
            data.put("token",token);
            data.put("wxId","");
            data.put("userId",sysUser.getUserId());
            // data.put("wxType", userType);
            ajax.put("data",data);
        }else {
            return AjaxResult.error("密码错误请重试!");
        }
        return ajax;
    }


    /**
     * <pre>
     * 微信小程序手机号码登录
     * </pre>
     */
    @PostMapping("/phone")
    public AjaxResult phone(@PathVariable String appid, @RequestBody MiniappLoginVo vo) {
        logger.info("获取微信用户绑定手机号参数:{},",JsonUtils.toJson(vo));
        String code = vo.getCode();
        String encryptedData = vo.getEncryptedData();
        String iv = vo.getIv();
        //登录返回用户数据
        SysUser user = new SysUser();
        //是否完善资料数据
        boolean isInformation=false;
        AjaxResult ajax = AjaxResult.success();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            logger.info("【微信小程序登录后的session信息】{}",JsonUtils.toJson(session));
            String sessionKey = session.getSessionKey();
            // 解密
            WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
            logger.info("【用户绑定手机号信息】{}",JsonUtils.toJson(phoneNoInfo));
            if (Objects.isNull(phoneNoInfo)) {
                return AjaxResult.error("获取用户绑定手机号失败!");
            }
            return AjaxResult.success(phoneNoInfo);
        } catch (Exception ex) {
            logger.error("【获取用户绑定手机号异常】{}", ex.getMessage());
            throw new RuntimeException("获取用户绑定手机号异常,登录失败:"+ex.getMessage());
        }

    }

    /**
     * 微信小程序微信登录
     * @param appid 微信小程序appid
     * @param vo 小程序登录vo
     * @return 结果
     */
    @PostMapping("/oauthInfo")
    public AjaxResult oauthInfo(@PathVariable String appid, @RequestBody MiniappLoginVo vo) {
        logger.info("微信小程序登录参数:{},",JsonUtils.toJson(vo));
        String code = vo.getCode();
        String encryptedData = vo.getEncryptedData();
        String iv = vo.getIv();
        String rawData = vo.getRawData();
        String signature = vo.getSignature();
        //登录返回用户数据
        SysUser user = new SysUser();
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isBlank(code)) {
            return AjaxResult.error("jsCode为空!");
        }
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            logger.info("【微信小程序登录后的session信息】{}",JsonUtils.toJson(session));
            String sessionKey = session.getSessionKey();
            // 用户信息校验
            if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
                return AjaxResult.error("用户校验失败!");
            }
            // 解密用户信息
            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            userInfo.setOpenId(session.getOpenid());
            logger.info("【微信小程序用户信息】{}",JsonUtils.toJson(userInfo));
            // 查询
            SysWxUser sysWxUser = sysWxUserService.selectSysWxUserByOpenId(userInfo.getOpenId());
            sysWxUser = convertSysWxUser(userInfo, sysWxUser);
            if (StringUtils.isNotBlank(sysWxUser.getId())) {
                sysWxUserService.updateSysWxUser(sysWxUser);
            } else {
                sysWxUserService.insertSysWxUser(sysWxUser);
            }
            LoginUser loginUser = new LoginUser();
            user.setUserName(sysWxUser.getNickName());
            user.setUserId(sysWxUser.getUserId());
            user.setLoginDate(new Date());
            loginUser.setUser(user);
            String token = tokenService.createToken(loginUser);
            logger.info("【微信小程序用户授权登录token】{}",token);
            Map<String,Object> data = new HashMap<>(4);
            data.put("token",token);
            data.put("wxId",sysWxUser.getId());
            data.put("userId",sysWxUser.getUserId());
            ajax.put("data",data);
        } catch (Exception ex) {
            logger.error("【微信小程序授权异常】{}", ex.getMessage());
            throw new RuntimeException("微信小程序授权异常:"+ex.getMessage());
        }
        return ajax;
    }

    /**
     * 微信用户转为系统微信用户
     * @param userInfo 微信用户
     * @param sysWxUser 系统微信用户
     * @return 系统微信用户
     */
    private SysWxUser convertSysWxUser(WxMaUserInfo userInfo,SysWxUser sysWxUser) {
        if (Objects.isNull(sysWxUser)) {
            sysWxUser = new SysWxUser();
            sysWxUser.setOpenId(userInfo.getOpenId());
        }
        sysWxUser.setAppType("1");
        sysWxUser.setSubscribeTime(new Date());
        sysWxUser.setNickName(userInfo.getNickName());
        sysWxUser.setSex(userInfo.getGender()=="1"?"1":"0");
        sysWxUser.setCity(userInfo.getCity());
        sysWxUser.setCountry(userInfo.getCountry());
        sysWxUser.setProvince(userInfo.getProvince());
        sysWxUser.setLanguage(userInfo.getLanguage());
        sysWxUser.setHeadimgUrl(userInfo.getAvatarUrl());
        sysWxUser.setUnionId(userInfo.getUnionId());
        sysWxUser.setUserName(StringUtils.isNotBlank(sysWxUser.getUserName()) ? sysWxUser.getUserName() : userInfo.getNickName());
        sysWxUser.setWxType("100");
        sysWxUser.setWhole("0");
        return sysWxUser;
    }

    /**
     * 注册用户(微信用户授权)
     * @param sysWxUser 微信用户对象
     */
    private void registerUser(SysWxUser sysWxUser) {
        SysUser sysUser = userMapper.checkUserNameUnique(sysWxUser.getNickName());
        if (Objects.isNull(sysUser)) {
             sysUser = new SysUser();
            sysUser.setUserName(sysWxUser.getNickName());
            sysUser.setNickName(sysWxUser.getNickName());
            sysUser.setPassword(SecurityUtils.encryptPassword(passWord));
            userMapper.insertUser(sysUser);
            // sysUser.setUserType("100");
            sysWxUser.setUserId(sysUser.getUserId());
        }
    }
}
