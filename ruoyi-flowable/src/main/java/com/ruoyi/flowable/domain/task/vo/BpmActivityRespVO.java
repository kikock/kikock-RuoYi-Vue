package com.ruoyi.flowable.domain.task.vo;


import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class BpmActivityRespVO {

    @Excel(name ="流程活动的标识")
    private String key;
    @Excel(name = "流程活动的类型")
    private String type;

    @Excel(name = "流程活动的开始时间")
    private Date startTime;
    @Excel(name = "流程活动的结束时间")
    private Date endTime;

    @Excel(name = "关联的流程任务的编号-关联的流程任务，只有 UserTask 等类型才有")
    private String taskId;

}
