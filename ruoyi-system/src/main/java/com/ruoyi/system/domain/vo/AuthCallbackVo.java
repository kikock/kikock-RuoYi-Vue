package com.ruoyi.system.domain.vo;

import com.ruoyi.common.utils.StringUtils;
import lombok.Data;
import me.zhyd.oauth.model.AuthCallback;

/**
 * 授权回调时的参数类增加uuid和三方标识
 *
 * @author ruoyi
 */
@Data
public class AuthCallbackVo{
    /**
     * 回调参数
     */
    private AuthCallback authCallback;
    /**
     * 回调uuid
     */
    private String uuid;
    /**
     * 回调标识
     */
    private String source;
    /**
     * 用户token
     */
    private String token;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 登录/绑定
     */
    private boolean bind =false;
}
