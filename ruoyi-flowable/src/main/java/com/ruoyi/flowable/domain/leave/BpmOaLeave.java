package com.ruoyi.flowable.domain.leave;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * OA 请假申请对象 bpm_oa_leave
 *
 * @author kikock
 * @date 2023-12-22
 */
public class BpmOaLeave extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 请假表单主键
     */
    private Long id;

    /**
     * 申请人的用户编号
     */
    @Excel(name = "申请人的用户编号")
    private Long userId;

    /**
     * 请假类型
     */
    @Excel(name = "请假类型")
    private Integer type;

    /**
     * 请假原因
     */
    @Excel(name = "请假原因")
    private String reason;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

    /**
     * 请假天数
     */
    @Excel(name = "请假天数")
    private Integer day;

    /**
     * 请假结果
     */
    @Excel(name = "请假结果")
    private Integer result;

    /**
     * 流程实例的编号
     */
    @Excel(name = "流程实例的编号")
    private String processInstanceId;

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

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return reason;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setDay(Integer day){
        this.day = day;
    }

    public Integer getDay(){
        return day;
    }

    public void setResult(Integer result){
        this.result = result;
    }

    public Integer getResult(){
        return result;
    }

    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId(){
        return processInstanceId;
    }

    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("type", getType())
                .append("reason", getReason())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("day", getDay())
                .append("result", getResult())
                .append("processInstanceId", getProcessInstanceId())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
