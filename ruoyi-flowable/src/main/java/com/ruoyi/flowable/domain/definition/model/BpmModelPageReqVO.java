package com.ruoyi.flowable.domain.definition.model;

import lombok.Data;


@Data
public class BpmModelPageReqVO {
    /**
     * 标识-精准匹配
     */
    private String key;
    /**
     * 名字-模糊匹配
     */
    private String name;
    /**
     * 流程分类
     */
    private String category;

}
