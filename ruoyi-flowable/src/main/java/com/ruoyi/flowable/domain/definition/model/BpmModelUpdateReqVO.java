package com.ruoyi.flowable.domain.definition.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BpmModelUpdateReqVO {
    /**
     * 编号
     */
    @NotEmpty(message = "编号不能为空")
    private String id;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 流程分类
     */
    private String category;
    /**
     * BPMN XML
     */
    private String bpmnXml;
    /**
     * 表单类型
     */
    private Integer formType;
    /**
     * 表单编号
     */
    private Long formId;
    /**
     * 自定义表单的提交路径
     */
    private String formCustomCreatePath;
    /**
     * 自定义表单的查看路径
     */
    private String formCustomViewPath;

}
