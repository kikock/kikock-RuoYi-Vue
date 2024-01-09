package com.ruoyi.flowable.domain.task;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 工作流的流程实例的拓展对象 bpm_process_instance_ext
 *
 * @author kikock
 * @date 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmProcessInstanceExt extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 发起流程的用户编号
     */
    private Long startUserId;

    /**
     * 流程实例的名字
     */
    private String name;
    /**
     * 流程实例id
     */
    private String businessKey;
    /**
     * 流程实例的编号
     */
    private String processInstanceId;

    /**
     * 流程定义的编号
     */
    private String processDefinitionId;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程实例的状态
     */
    private Integer status;

    /**
     * 流程实例的结果
     */
    private Integer result;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 表单值
     */
    private String formVariables;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

}
