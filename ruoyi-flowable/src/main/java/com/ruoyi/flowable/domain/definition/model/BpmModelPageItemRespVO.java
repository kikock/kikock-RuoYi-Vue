package com.ruoyi.flowable.domain.definition.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmModelPageItemRespVO extends BpmModelBaseVO {

    /**
     * 编号
     */
    private String id;

    /**
     * 请假表单
     */
    private String formName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

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
