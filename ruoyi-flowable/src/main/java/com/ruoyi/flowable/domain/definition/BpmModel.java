package com.ruoyi.flowable.domain.definition;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 流程模型 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class BpmModel extends BaseEntity{
    /**
     * 编号
     */
    private String id;
    /**
     * 表单名称
     */
    private String formName;
    /**
     * 流程标识
     */
    @NotEmpty(message = "流程标识不能为空")
    private String key;

    /**
     * 流程名称
     */
    @NotEmpty(message = "流程名称不能为空")
    private String name;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 流程分类-参见 bpm_model_category 数据字典
     */
    @NotEmpty(message = "流程分类不能为空")
    private String category;

    /**
     * 表单类型-参见 bpm_model_form_type 数据字典
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
    /**
     * 最新部署的流程定义
     */
    private ProcessDefinition processDefinition;

    @Data
    public static class ProcessDefinition {
        /**
         * 编号
         */
        private String id;
        /**
         * 版本
         */
        private Integer version;
        /**
         * 部署时间
         */
        private LocalDateTime deploymentTime;
        /**
         * 中断状态
         */
        private Integer suspensionState;

    }
}
