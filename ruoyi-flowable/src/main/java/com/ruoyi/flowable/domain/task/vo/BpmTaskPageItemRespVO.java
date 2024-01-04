package com.ruoyi.flowable.domain.task.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
 *  管理后台 - 流程任务的 Running 进行中的分页项 Response VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpmTaskPageItemRespVO {
    @Excel(name = "任务编号")
    private String id;//
    @Excel(name = "任务名字")
    private String name;//
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "接收时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date claimTime;//接收时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;//
    @Excel(name = "激活状态-参见 SuspensionState 枚举")
    private Integer suspensionState;//
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date endTime; //""
    @Excel(name = "持续时间")
    private Long durationInMillis;//""
    @Excel(name = "任务结果-参见 bpm_process_instance_result")
    private Integer result;//""
    @Excel(name = "审批建议")
    private String reason;//""

    /**
     * 所属流程实例
     */
    private ProcessInstance processInstance;

    @Data
    public static class ProcessInstance {
        @Excel(name = "实例id ")
        private String id;
        @Excel(name = "实例名称")
        private String name;
        @Excel(name = "实例发起人id")
        private Long startUserId;
        @Excel(name = "发起人昵称")
        private String startUserNickname;
        @Excel(name = "流程定义编号")
        private String processDefinitionId;
    }
}
