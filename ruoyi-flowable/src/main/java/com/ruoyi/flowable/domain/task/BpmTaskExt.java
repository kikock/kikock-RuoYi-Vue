package com.ruoyi.flowable.domain.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 工作流的流程任务的拓展对象 bpm_task_ext
 *
 * @author kikock
 * @date 2023-12-22
 */
public class BpmTaskExt extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 任务的审批人
     */
    @Excel(name = "任务的审批人")
    private Long assigneeUserId;

    /**
     * 任务的名字
     */
    @Excel(name = "任务的名字")
    private String name;

    /**
     * 任务的编号
     */
    @Excel(name = "任务的编号")
    private String taskId;

    /**
     * 任务的结果
     */
    @Excel(name = "任务的结果")
    private Integer result;

    /**
     * 审批建议
     */
    @Excel(name = "审批建议")
    private String reason;

    /**
     * 任务的结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务的结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 流程实例的编号
     */
    @Excel(name = "流程实例的编号")
    private String processInstanceId;

    /**
     * 流程定义的编号
     */
    @Excel(name = "流程定义的编号")
    private String processDefinitionId;

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

    public void setAssigneeUserId(Long assigneeUserId){
        this.assigneeUserId = assigneeUserId;
    }

    public Long getAssigneeUserId(){
        return assigneeUserId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    public String getTaskId(){
        return taskId;
    }

    public void setResult(Integer result){
        this.result = result;
    }

    public Integer getResult(){
        return result;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return reason;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId(){
        return processInstanceId;
    }

    public void setProcessDefinitionId(String processDefinitionId){
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionId(){
        return processDefinitionId;
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
                .append("assigneeUserId", getAssigneeUserId())
                .append("name", getName())
                .append("taskId", getTaskId())
                .append("result", getResult())
                .append("reason", getReason())
                .append("endTime", getEndTime())
                .append("processInstanceId", getProcessInstanceId())
                .append("processDefinitionId", getProcessDefinitionId())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
