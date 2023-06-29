package com.ruoyi.project.visitor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 访客申请记录对象 visitor_apply_record
 *
 * @author ruoyi
 * @date 2022-09-19
 */
public class VisitorApplyRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    @ApiModelProperty(value = "主键")
    private String id;

    /** 申请人id */
    @Excel(name = "申请人id")
    @ApiModelProperty(value = "申请人id")
    private String wxUserId;

    /** 申请人名称 */
    @Excel(name = "申请人名称")
    @ApiModelProperty(value = "申请人名称")
    private String wxUserName;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    /** 申请状态 字典 apply_state */
    @Excel(name = "申请状态 字典 apply_state")
    @ApiModelProperty(value = "申请状态 字典 apply_state")
    private String applyState;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "访问时间")
    private Date visitTime;

    /** 访问部门id */
    @Excel(name = "访问部门id")
    @ApiModelProperty(value = "访问部门id")
    private Long deptId;

    /** 访问部门名称 */
    @Excel(name = "访问部门名称")
    @ApiModelProperty(value = "访问部门名称")
    private String deptName;

    /** 访问事项 */
    @Excel(name = "访问事项")
    @ApiModelProperty(value = "访问事项")
    private String accessMatters;

    /** 访问人数 */
    @Excel(name = "访问人数")
    @ApiModelProperty(value = "访问人数")
    private Long accessPeopleNum;

    /** 审核人id */
    @Excel(name = "审核人id")
    @ApiModelProperty(value = "审核人id")
    private Long reviewedById;

    /** 审核人名称 */
    @Excel(name = "审核人名称")
    @ApiModelProperty(value = "审核人名称")
    private String reviewedByName;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "审核时间")
    private Date reviewedByTime;

    /** 审核失败原因 */
    @Excel(name = "审核失败原因")
    @ApiModelProperty(value = "审核失败原因")
    private String reviewedFailed;

    /** 接待人id */
    @Excel(name = "接待人id")
    @ApiModelProperty(value = "接待人id")
    private Long receptionistId;

    /** 接待人名称 */
    @Excel(name = "接待人名称")
    @ApiModelProperty(value = "接待人名称")
    private String receptionistName;

    /** 接待时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "接待时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "接待时间")
    private Date receptionistTime;

    /** 健康码状态 字典 health_code_state */
    @Excel(name = "健康码状态 字典 health_code_state")
    @ApiModelProperty(value = "健康码状态 字典 health_code_state")
    private String healthCodeState;

    /** 访问人体温 */
    @Excel(name = "访问人体温")
    @ApiModelProperty(value = "访问人体温")
    private String temperature;

    /** 访问二维码路径 */
    @Excel(name = "访问二维码路径")
    @ApiModelProperty(value = "访问二维码路径")
    private String qrCodeUrl;

    /** 访问二维码状态 字典 qr_code_state */
    @Excel(name = "访问二维码状态 字典 qr_code_state")
    @ApiModelProperty(value = "访问二维码状态 字典 qr_code_state")
    private String qrCodeState;

    /** 离开时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离开时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "离开时间")
    private Date leaveTime;

    /** 作废原因 */
    @Excel(name = "作废原因")
    @ApiModelProperty(value = "作废原因")
    private String invalidReason;
    /** 接待门卫id */
    @ApiModelProperty(value = "接待门卫id")
    private String guardId;
    /** 接待门卫 */
    @Excel(name = "接待门卫")
    @ApiModelProperty(value = "接待门卫")
    private String guardName;

    public String getGuardId() {
        return guardId;
    }

    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setWxUserId(String wxUserId)
    {
        this.wxUserId = wxUserId;
    }

    public String getWxUserId()
    {
        return wxUserId;
    }
    public void setWxUserName(String wxUserName)
    {
        this.wxUserName = wxUserName;
    }

    public String getWxUserName()
    {
        return wxUserName;
    }
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime()
    {
        return applyTime;
    }
    public void setApplyState(String applyState)
    {
        this.applyState = applyState;
    }

    public String getApplyState()
    {
        return applyState;
    }
    public void setVisitTime(Date visitTime)
    {
        this.visitTime = visitTime;
    }

    public Date getVisitTime()
    {
        return visitTime;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setAccessMatters(String accessMatters)
    {
        this.accessMatters = accessMatters;
    }

    public String getAccessMatters()
    {
        return accessMatters;
    }
    public void setAccessPeopleNum(Long accessPeopleNum)
    {
        this.accessPeopleNum = accessPeopleNum;
    }

    public Long getAccessPeopleNum()
    {
        return accessPeopleNum;
    }
    public void setReviewedById(Long reviewedById)
    {
        this.reviewedById = reviewedById;
    }

    public Long getReviewedById()
    {
        return reviewedById;
    }
    public void setReviewedByName(String reviewedByName)
    {
        this.reviewedByName = reviewedByName;
    }

    public String getReviewedByName()
    {
        return reviewedByName;
    }
    public void setReviewedByTime(Date reviewedByTime)
    {
        this.reviewedByTime = reviewedByTime;
    }

    public Date getReviewedByTime()
    {
        return reviewedByTime;
    }
    public void setReviewedFailed(String reviewedFailed)
    {
        this.reviewedFailed = reviewedFailed;
    }

    public String getReviewedFailed()
    {
        return reviewedFailed;
    }
    public void setReceptionistId(Long receptionistId)
    {
        this.receptionistId = receptionistId;
    }

    public Long getReceptionistId()
    {
        return receptionistId;
    }
    public void setReceptionistName(String receptionistName)
    {
        this.receptionistName = receptionistName;
    }

    public String getReceptionistName()
    {
        return receptionistName;
    }
    public void setReceptionistTime(Date receptionistTime)
    {
        this.receptionistTime = receptionistTime;
    }

    public Date getReceptionistTime()
    {
        return receptionistTime;
    }
    public void setHealthCodeState(String healthCodeState)
    {
        this.healthCodeState = healthCodeState;
    }

    public String getHealthCodeState()
    {
        return healthCodeState;
    }
    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public String getTemperature()
    {
        return temperature;
    }
    public void setQrCodeUrl(String qrCodeUrl)
    {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getQrCodeUrl()
    {
        return qrCodeUrl;
    }
    public void setQrCodeState(String qrCodeState)
    {
        this.qrCodeState = qrCodeState;
    }

    public String getQrCodeState()
    {
        return qrCodeState;
    }
    public void setLeaveTime(Date leaveTime)
    {
        this.leaveTime = leaveTime;
    }

    public Date getLeaveTime()
    {
        return leaveTime;
    }
    public void setInvalidReason(String invalidReason)
    {
        this.invalidReason = invalidReason;
    }

    public String getInvalidReason()
    {
        return invalidReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("wxUserId", getWxUserId())
            .append("wxUserName", getWxUserName())
            .append("applyTime", getApplyTime())
            .append("applyState", getApplyState())
            .append("visitTime", getVisitTime())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("accessMatters", getAccessMatters())
            .append("accessPeopleNum", getAccessPeopleNum())
            .append("reviewedById", getReviewedById())
            .append("reviewedByName", getReviewedByName())
            .append("reviewedByTime", getReviewedByTime())
            .append("reviewedFailed", getReviewedFailed())
            .append("receptionistId", getReceptionistId())
            .append("receptionistName", getReceptionistName())
            .append("receptionistTime", getReceptionistTime())
            .append("healthCodeState", getHealthCodeState())
            .append("temperature", getTemperature())
            .append("qrCodeUrl", getQrCodeUrl())
            .append("qrCodeState", getQrCodeState())
            .append("leaveTime", getLeaveTime())
            .append("invalidReason", getInvalidReason())
            .append("createTime", getCreateTime())
            .toString();
    }
}
