package com.ruoyi.flowable.domain.task.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import java.time.LocalDateTime;



@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskTodoPageReqVO extends PageDomain {

    @Excel(name = "流程名称")
    private String name;

    @Excel(name = "创建时间")
    private LocalDateTime[] createTime;

}
