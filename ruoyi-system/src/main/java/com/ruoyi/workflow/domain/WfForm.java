package com.ruoyi.workflow.domain;


import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 流程表单对象 wf_form
 *
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WfForm extends BaseEntity{
    private static final long serialVersionUID = 1L;
    /**
     * 表单主键
     */
    private Long formId;

    /**
     * 表单名称
     */
    private String formName;
    /**
     * 表单类型 0 流程表单(自定义设计表单) 1 业务表单(路由链接表单)
     */
    private Integer formType;
    /**
     * 表单提交链接
     */
    private String formCreatePath;
    /**
     * 表单查看链接
     */
    private String formViewPath;

    /**
     * 表单内容
     */
    private String content;
    /**
     * 开启状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /** 表单的配置 */
    @NotNull(message = "表单的配置不能为空")
    private String conf;

    /** 表单项的数组 */
    private String fields;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    @NotNull(message = "表单项的数组不能为空")
    private List<String> fieldsArr;
}
