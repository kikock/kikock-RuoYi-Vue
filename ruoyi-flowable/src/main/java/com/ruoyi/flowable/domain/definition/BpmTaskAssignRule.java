package com.ruoyi.flowable.domain.definition;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Bpm 任务规则对象 bpm_task_assign_rule
 *
 * @author kikock
 * @date 2023-12-22
 */
public class BpmTaskAssignRule extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 流程模型的编号
     */
    @Excel(name = "流程模型的编号")
    private String modelId;

    /**
     * 流程定义的编号
     */
    @Excel(name = "流程定义的编号")
    private String processDefinitionId;

    /**
     * 流程任务定义的 key
     */
    @Excel(name = "流程任务定义的 key")
    private String taskDefinitionKey;

    private String taskDefinitionName;
    /**
     * 规则类型
     */
    @Excel(name = "规则类型")
    private Integer type;

    /**
     * 规则值，JSON 数组
     */
    @Excel(name = "规则值，JSON 数组")
    private String options;

    /**
     * 规则值数组，一般关联指定表的编号
     * 根据 type 不同，对应的值是不同的：
     *
     */
    private Set<Long> optionIds;



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

    public void setModelId(String modelId){
        this.modelId = modelId;
    }

    public String getModelId(){
        return modelId;
    }

    public void setProcessDefinitionId(String processDefinitionId){
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionId(){
        return processDefinitionId;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey){
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getTaskDefinitionKey(){
        return taskDefinitionKey;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }

    public void setOptions(String options){
        this.options = options;
    }

    public String getOptions(){
        return options;
    }

    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }

    public Set<Long> getOptionIds(){
        return optionIds;
    }

    public String getTaskDefinitionName(){
        return taskDefinitionName;
    }

    public void setTaskDefinitionName(String taskDefinitionName){
        this.taskDefinitionName = taskDefinitionName;
    }

    public void setOptionIds(Set<Long> optionIds){
        this.optionIds = optionIds;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("modelId", getModelId())
                .append("processDefinitionId", getProcessDefinitionId())
                .append("taskDefinitionKey", getTaskDefinitionKey())
                .append("type", getType())
                .append("options", getOptions())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
