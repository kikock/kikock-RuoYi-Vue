package com.ruoyi.flowable.domain.task.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.page.PageDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpmTaskReqVO extends PageDomain {


    /**
     * 流程实例编号
     */
    private String id;
    /**
     * 取消原因
     */
    private String reason;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 流程定义的编号
     */
    private String processDefinitionId;
    /**
     * 流程实例的状态
     */
    private Integer status;
    /**
     * 流程实例的结果
     */
    private Integer result;
    /**
     * 流程分类
     */
    private String category;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 开始时间
     */
    private Date starTime;   @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 流程定义的标识
     */
    private String processDefinitionKey;
    /**
     * 变量实例
     */
    private Map<String, Object> variables;

    /**
     * 业务的唯一标识
     *
     * 例如说，请假申请的编号。通过它，可以查询到对应的实例
     */
    private String businessKey;
    /**
     * 回退到的任务 Key
     *
     */
    private String targetDefinitionKey;

    /**
     * 转派 委派用户id
     *
     */
    private String assigneeUserId;


}
