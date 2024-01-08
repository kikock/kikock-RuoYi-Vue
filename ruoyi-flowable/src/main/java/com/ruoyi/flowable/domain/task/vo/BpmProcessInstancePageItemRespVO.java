package com.ruoyi.flowable.domain.task.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class BpmProcessInstancePageItemRespVO {

    private String id;

    private String name;

    private String processDefinitionId;

    private String category;

    private Integer status;

    private Integer result;

    private Date createTime;
    private Date endTime;
    /**
     * 当前任务
     */
    private List<Task> tasks;
    @Data
    public static class Task {
        private String id;
        private String name;
    }

}
