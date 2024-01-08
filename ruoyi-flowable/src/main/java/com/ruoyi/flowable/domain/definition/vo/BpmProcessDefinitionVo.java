package com.ruoyi.flowable.domain.definition.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 流程信息
 */
@Data
public class BpmProcessDefinitionVo {

    /**
     * 流程模型的编号
     */
    private String id;
    /**
     * 流程版本
     */
    private Integer version;
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
     * 参见 bpm_model_category 数据字典
     */
    @NotEmpty(message = "流程分类不能为空")
    private String category;
    /**
     * 表单类型
     */
    private Integer formType;
    /**
     * 动态表单编号
     */
    private Long formId;
    /**
     * 表单的配置
     */
    private String formConf;
    /**
     * 表单项的数组
     */
    private List<String> formFields;

    /**
     * 自定义表单的提交路径，使用 Vue 的路由地址
     * 在表单类型为
     */
    private String formCustomCreatePath;
    /**
     * 自定义表单的查看路径，使用 Vue 的路由地址
     * 在表单类型为
     */
    private String formCustomViewPath;

    public boolean isNormalFormTypeValid(){
        //TODO 如果非业务表单，则直接通过  写死 流程表单类型 10 业务表单直接跳过 20 流程自定义表单进行判断是否存在表单
        if (!Objects.equals(formType, 10)) {
            return true;
        }
        return formId != null && StrUtil.isNotEmpty(formConf) && CollUtil.isNotEmpty(formFields);
    }

    public boolean isNormalCustomTypeValid(){
        //TODO 如果非业务表单，则直接通过   写死 流程表单类型 10 业务表单直接跳过 20 流程自定义表单跳过
        if (!Objects.equals(formType, 20)) {
            return true;
        }
        return StrUtil.isNotEmpty(formCustomCreatePath) && StrUtil.isNotEmpty(formCustomViewPath);
    }

    /**
     * 流程模型的编号
     */
    private String modelId;
    /**
     * 流程标识
     */
    private String key;
    /**
     * BPMN XML
     */
    private byte[] bpmnBytes;

    private String formName;
    /**
     * 部署时间
     */
    private LocalDateTime deploymentTime;

    /**
     * 中断状态
     */
    private Integer suspensionState;
}
