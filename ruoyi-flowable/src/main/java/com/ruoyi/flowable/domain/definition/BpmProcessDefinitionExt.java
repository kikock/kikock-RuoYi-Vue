package com.ruoyi.flowable.domain.definition;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Bpm 流程定义的拓展
 * 对象 bpm_process_definition_ext
 *
 * @author kikock
 * @date 2023-12-22
 */
@Data
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


}
