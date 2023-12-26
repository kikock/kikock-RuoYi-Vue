package com.ruoyi.flowable.domain.definition.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BpmModelCreateReqVO {

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

}
