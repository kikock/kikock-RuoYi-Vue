package com.ruoyi.flowable.domain.definition;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Bpm 流程定义的拓展
 * 对象 bpm_process_definition_ext
 *
 * @author kikock
 * @date 2023-12-22
 */
public class BpmProcessDefinitionExt extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 流程定义的编号
     */
    @Excel(name = "流程定义的编号")
    private String processDefinitionId;

    /**
     * 流程模型的编号
     */
    @Excel(name = "流程模型的编号")
    private String modelId;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 表单类型
     */
    @Excel(name = "表单类型")
    private Integer formType;

    /**
     * 表单编号
     */
    @Excel(name = "表单编号")
    private Long formId;

    /**
     * 表单的配置
     */
    @Excel(name = "表单的配置")
    private String formConf;

    /**
     * 表单项的数组
     */
    @Excel(name = "表单项的数组")
    private String formFields;

    /**
     * 自定义表单的提交路径
     */
    @Excel(name = "自定义表单的提交路径")
    private String formCustomCreatePath;

    /**
     * 自定义表单的查看路径
     */
    @Excel(name = "自定义表单的查看路径")
    private String formCustomViewPath;

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

    public void setProcessDefinitionId(String processDefinitionId){
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionId(){
        return processDefinitionId;
    }

    public void setModelId(String modelId){
        this.modelId = modelId;
    }

    public String getModelId(){
        return modelId;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setFormType(Integer formType){
        this.formType = formType;
    }

    public Integer getFormType(){
        return formType;
    }

    public void setFormId(Long formId){
        this.formId = formId;
    }

    public Long getFormId(){
        return formId;
    }

    public void setFormConf(String formConf){
        this.formConf = formConf;
    }

    public String getFormConf(){
        return formConf;
    }

    public void setFormFields(String formFields){
        this.formFields = formFields;
    }

    public String getFormFields(){
        return formFields;
    }

    public void setFormCustomCreatePath(String formCustomCreatePath){
        this.formCustomCreatePath = formCustomCreatePath;
    }

    public String getFormCustomCreatePath(){
        return formCustomCreatePath;
    }

    public void setFormCustomViewPath(String formCustomViewPath){
        this.formCustomViewPath = formCustomViewPath;
    }

    public String getFormCustomViewPath(){
        return formCustomViewPath;
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
                .append("processDefinitionId", getProcessDefinitionId())
                .append("modelId", getModelId())
                .append("description", getDescription())
                .append("formType", getFormType())
                .append("formId", getFormId())
                .append("formConf", getFormConf())
                .append("formFields", getFormFields())
                .append("formCustomCreatePath", getFormCustomCreatePath())
                .append("formCustomViewPath", getFormCustomViewPath())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
