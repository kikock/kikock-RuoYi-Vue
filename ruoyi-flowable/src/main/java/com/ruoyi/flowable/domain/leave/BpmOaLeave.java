package com.ruoyi.flowable.domain.leave;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * OA 请假申请对象 bpm_oa_leave
 *
 * @author kikock
 * @date 2023-12-22
 */
@Data
public class BpmOaLeave extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 请假表单主键
     */
    private Long id;
    /**
     * 流程定义key 请假默认 leave
     */
    private String process_key="leave";
    /**
     * 申请人的用户编号
     */
    @Excel(name = "申请人的用户编号")
    private Long userId;

    /**
     * 请假类型
     */
    @Excel(name = "请假类型")
    private Integer type;

    /**
     * 请假原因
     */
    @Excel(name = "请假原因")
    private String reason;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 40, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 40, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

    /**
     * 请假天数
     */
    @Excel(name = "请假天数")
    private Integer day;

    /**
     * 请假结果
     */
    @Excel(name = "请假结果")
    private Integer result;

    /**
     * 流程实例的编号
     */
    @Excel(name = "流程实例的编号")
    private String processInstanceId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

}
