package com.ruoyi.flowable.domain.task.vo;


import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class BpmActivityRespVO {

    private String key;
    private String type;
    private Date startTime;
    private Date endTime;
    private String taskId;

}
