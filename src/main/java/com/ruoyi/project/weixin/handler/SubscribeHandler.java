package com.ruoyi.project.weixin.handler;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.weixin.builder.TextBuilder;
import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @desc:  关注事件
 * @author: cao_wencao
 * @date: 2019-09-02 17:17
 */
@Component
public class SubscribeHandler extends AbstractHandler {
    /** 默认用户密码 */
    @Value("${mock.passWord}")
    private String passWord;
    @Autowired
    private ISysWxUserService sysWxUserService;
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                    .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // 添加关注用户到本地数据库
                SysWxUser sysWxUser = sysWxUserService.selectSysWxUserByOpenId(userWxInfo.getOpenId());
                sysWxUser = convertSysWxUser(userWxInfo, sysWxUser);
                if (StringUtils.isNotBlank(sysWxUser.getId())) {
                    if (Objects.isNull(sysWxUser.getUserId())) {
                        registerUser(sysWxUser);
                    }
                    sysWxUserService.updateSysWxUser(sysWxUser);
                } else {
                    registerUser(sysWxUser);
                    sysWxUserService.insertSysWxUser(sysWxUser);
                }
            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }


        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
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
        int unique = userMapper.checkUserNameUnique(sysWxUser.getOpenId());
        if (unique == 0) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(sysWxUser.getOpenId());
            sysUser.setNickName(sysWxUser.getOpenId());
            sysUser.setPassword(SecurityUtils.encryptPassword(passWord));
            userMapper.insertUser(sysUser);
            sysWxUser.setUserId(sysUser.getUserId());
        }
    }

}

