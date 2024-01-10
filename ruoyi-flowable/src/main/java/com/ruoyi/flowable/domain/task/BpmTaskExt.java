package com.ruoyi.flowable.domain.task;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 工作流的流程任务的拓展对象 bpm_task_ext
 *
 * @author kikock
 * @date 2023-12-22
 */
@Data
public class BpmTaskExt extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 任务的审批人
     */
    private Long assigneeUserId;

    /**
     * 任务的审批候选人
     */
    private List<Long> userList;


    /**
     * 任务的名字
     */
    private String name;

    /**
     * 任务的编号
     */
    private String taskId;

    /**
     * 任务的结果
     */
    private Integer result;

    /**
     * 审批建议
     */
    private String reason;

    /**
     * 任务的结束时间
     */
    private Date endTime;

    /**
     * 流程实例的编号
     */
    private String processInstanceId;

    /**
     * 流程定义的编号
     */
    private String processDefinitionId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

}
