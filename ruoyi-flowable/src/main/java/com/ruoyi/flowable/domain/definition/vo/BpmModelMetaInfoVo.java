package com.ruoyi.flowable.domain.definition.vo;

import lombok.Data;

/**
 * BPM 流程 MetaInfo vo
 * 主要用于 { Model#setMetaInfo(String)} 的存储
 *
 */
@Data
public class BpmModelMetaInfoVo{

    /**
     * 流程描述
     */
    private String description;
    /**
     * 表单类型
     */
    private Integer formType;
    /**
     * 表单编号
     */
    private Long formId;
    /**
     * 自定义表单的提交路径，使用 Vue 的路由地址
     */
    private String formCustomCreatePath;
    /**
     * 自定义表单的查看路径，使用 Vue 的路由地址
     */
    private String formCustomViewPath;

}
