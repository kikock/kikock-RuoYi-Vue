package com.ruoyi.flowable.domain.task.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class BpmProcessInstancePageItemRespVO {

    @Excel(name ="流程实例的编号")
    private String id;

    @Excel(name ="流程名称")
    private String name;

    @Excel(name ="流程定义的编号")
    private String processDefinitionId;

    @Excel(name ="流程分类-参见 bpm_model_category 数据字典")
    private String category;

    @Excel(name ="流程实例的状态-参见 bpm_process_instance_status")
    private Integer status;

    @Excel(name ="流程实例的结果-参见 bpm_process_instance_result")
    private Integer result;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name ="结束时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

    /**
     * 当前任务
     */
    private List<Task> tasks;

    @Data
    public static class Task {

        @Excel(name ="流程任务的编号")
        private String id;

        @Excel(name ="任务名称")
        private String name;

    }

}