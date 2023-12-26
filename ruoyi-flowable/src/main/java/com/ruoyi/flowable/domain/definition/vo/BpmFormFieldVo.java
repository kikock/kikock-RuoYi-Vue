package com.ruoyi.flowable.domain.definition.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Bpm 表单的 Field 表单项 vo
 * 字段的定义，可见 https://github.com/JakHuang/form-generator/issues/46 文档
 */
@Data
public class BpmFormFieldVo{

    /**
     * 表单标题
     */
    private String label;
    /**
     * 表单字段的属性名，可自定义
     */
    @JsonProperty(value = "vModel")
    private String vModel;

}
