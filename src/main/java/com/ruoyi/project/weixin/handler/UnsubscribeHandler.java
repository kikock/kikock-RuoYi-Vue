package com.ruoyi.project.weixin.handler;

import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @desc:  取消关注事件
 * @author: cao_wencao
 * @date: 2019-09-02 17:17
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Autowired
    private ISysWxUserService sysWxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // 可以更新本地数据库为取消关注状态
        SysWxUser sysWxUser = sysWxUserService.selectSysWxUserByOpenId(openId);
        if (Objects.nonNull(sysWxUser)) {
            sysWxUser.setSubscribe("0");
            sysWxUser.setCancelSubscribeTime(new Date());
            sysWxUserService.updateSysWxUser(sysWxUser);
        }
        return null;
    }

}