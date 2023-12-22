package com.ruoyi.system.domain.vo;

import lombok.Data;

/**
 * 三方用户三方账号数据
 *
 * @author
 */
@Data
public class SocialAppVo{
    /**
     * 社交平台id
     */
    private String id;
    /**
     * 社交平台标题
     */
    private String title;
    /**
     * 图标大小
     */
    private Integer type=20;
    /**
     * 平台类型
     */
    private String source;

    /**
     * 图标地址
     */
    private String img;
}
