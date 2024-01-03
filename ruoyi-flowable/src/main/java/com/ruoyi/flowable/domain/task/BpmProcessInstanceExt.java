package com.ruoyi.flowable.domain.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 工作流的流程实例的拓展对象 bpm_process_instance_ext
 *
 * @author kikock
 * @date 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmProcessInstanceExt extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 发起流程的用户编号
     */
    @Excel(name = "发起流程的用户编号")
    private Long startUserId;

    /**
     * 流程实例的名字
     */
    @Excel(name = "流程实例的名字")
    private String name;

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
     * 流程分类
     */
    @Excel(name = "流程分类")
    private String category;

    /**
     * 流程实例的状态
     */
    @Excel(name = "流程实例的状态")
    private Integer status;

    /**
     * 流程实例的结果
     */
    @Excel(name = "流程实例的结果")
    private Integer result;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 表单值
     */
    @Excel(name = "表单值")
    private String formVariables;

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

    public void setStartUserId(Long startUserId){
        this.startUserId = startUserId;
    }

    public Long getStartUserId(){
        return startUserId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
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

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setResult(Integer result){
        this.result = result;
    }

    public Integer getResult(){
        return result;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setFormVariables(String formVariables){
        this.formVariables = formVariables;
    }

    public String getFormVariables(){
        return formVariables;
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
                .append("startUserId", getStartUserId())
                .append("name", getName())
                .append("processInstanceId", getProcessInstanceId())
                .append("processDefinitionId", getProcessDefinitionId())
                .append("category", getCategory())
                .append("status", getStatus())
                .append("result", getResult())
                .append("endTime", getEndTime())
                .append("formVariables", getFormVariables())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
