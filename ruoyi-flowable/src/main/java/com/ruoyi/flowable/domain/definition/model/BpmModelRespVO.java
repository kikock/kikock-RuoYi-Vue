package com.ruoyi.flowable.domain.definition.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmModelRespVO extends BpmModelBaseVO {
    /**
     * 编号
     */
    private String id;
    /**
     * BPMN XML
     */
    private String bpmnXml;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
