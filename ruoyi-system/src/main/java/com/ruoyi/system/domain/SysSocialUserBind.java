package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 三方用户对象 sys_social_user_bind
 *
 * @author kikock
 * @date 2023-12-04
 */
public class SysSocialUserBind extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 主键(自增策略)
     */
    private Long id;

    /**
     * 系统用户id
     */
    @Excel(name = "系统用户id")
    private Long userId;
    /**
     * 系统用户对象
     */
//    @Excels({
//            @Excel(name = "登录名称", targetAttr = "userName", type = Excel.Type.EXPORT),
//            @Excel(name = "电话号码", targetAttr = "phonenumber", type = Excel.Type.EXPORT)
//    })
    private SysUser user;


    /**
     * 用户登录账号
     */
    @Excel(name = "用户登录账号")
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;

    /**
     * 绑定平台
     */
    @Excel(name = "绑定平台")
    private String socialType;

    /**
     * 第三方平台用户唯一id
     */
    @Excel(name = "第三方平台用户唯一id")
    private String socialUuid;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getNickName(){
        return nickName;
    }

    public void setSocialType(String socialType){
        this.socialType = socialType;
    }

    public String getSocialType(){
        return socialType;
    }

    public void setSocialUuid(String socialUuid){
        this.socialUuid = socialUuid;
    }

    public String getSocialUuid(){
        return socialUuid;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }

    public SysUser getUser(){
        return user;
    }

    public void setUser(SysUser user){
        this.user = user;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("socialType", getSocialType())
                .append("socialUuid", getSocialUuid())
                .append("email", getEmail())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
