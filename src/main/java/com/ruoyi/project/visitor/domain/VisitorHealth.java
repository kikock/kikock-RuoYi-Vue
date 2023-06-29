package com.ruoyi.project.visitor.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 核酸报告填报记录对象 visitor_health
 *
 * @author ruoyi
 * @date 2022-10-10
 */
public class VisitorHealth extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 微信访客id
     */
    @Excel(name = "微信访客id")
    @ApiModelProperty(value = "微信访客id")
    private String wxId;

    /**
     * 用户类型
     */
    @Excel(name = "用户类型")
    @ApiModelProperty(value = "用户类型")
    private String wxType;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 电话
     */
    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String userPhone;

    /**
     * 证件号码
     */
    @Excel(name = "证件号码")
    @ApiModelProperty(value = "证件号码")
    private String userIdCard;

    /**
     * 健康码地址
     */
    @Excel(name = "健康码地址")
    @ApiModelProperty(value = "健康码地址")
    private String healthUrl;

    /**
     * 核酸地址
     */
    @Excel(name = "核酸地址")
    @ApiModelProperty(value = "核酸地址")
    private String monitorUrl;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxType(String wxType) {
        this.wxType = wxType;
    }

    public String getWxType() {
        return wxType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }

    public String getHealthUrl() {
        return healthUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("wxId", getWxId())
                .append("wxType", getWxType())
                .append("userName", getUserName())
                .append("userPhone", getUserPhone())
                .append("userIdCard", getUserIdCard())
                .append("healthUrl", getHealthUrl())
                .append("monitorUrl", getMonitorUrl())
                .append("createTime", getCreateTime())
                .toString();
    }
}
