package com.ruoyi.system.domain.vo;

import lombok.Data;

/**
 * 三方用户三方账号数据
 *
 * @author
 */
@Data
public class SocialUserVo{
    /**
     * 社交平台的名id
     */
    private String id;
    /**
     * 社交平台的名称
     */
    private String name;
    /**
     * 平台应用appKey
     */
    private String type;
    /**
     * 平台绑定id
     */
    private String authId;

    /**
     * 开启状态
     */
    private Integer status;
}
