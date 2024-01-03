package com.ruoyi.flowable.domain.task.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.page.PageDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpmTaskPageReqVO extends PageDomain {
    @Excel(name = "流程名称")
    private String name;

    @Excel(name = "用户id")
    private Long userId;

    @Excel(name = "流程定义的编号")
    private String processDefinitionId;

    @Excel(name =  "流程实例的状态-参见 bpm_process_instance_status")
    private Integer status;

    @Excel(name =  "流程实例的结果-参见 bpm_process_instance_result" )
    private Integer result;

    @Excel(name =  "流程分类-参见 bpm_model_category 数据字典" )
    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date[] createTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date starTime;   @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;
    @Excel(name =  "变量实例")
    private Map<String, Object> variables;
    @Excel(name =  "业务的唯一标识")
    private String businessKey;
}
