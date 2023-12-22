package com.ruoyi.common.core.domain.vo;


import java.io.Serializable;

/**
 * 用户对象 SysUserVo
 *
 * @author ck
 */
public class SysUserVo implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门负责人
     */
    private String deptLeader;
    /**
     * 部门负责人电话
     */
    private Long deptLeaderPhone;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户数据来源 D 钉钉  W 微信  S 系统创建
     */
    private String sourceAdd;
    /**
     * 用户绑定的钉钉id
     */
    private String dingOpenId;
    /**
     * 用户绑定的微信id
     */
    private String weChatOpenId;
}
