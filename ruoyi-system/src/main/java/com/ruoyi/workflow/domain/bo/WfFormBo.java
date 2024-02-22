package com.ruoyi.workflow.domain.bo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程表单业务对象
 *
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WfFormBo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @NotNull(message = "表单ID不能为空", groups = { EditGroup.class })
    private Long formId;

    /**
     * 表单名称
     */
    @NotBlank(message = "表单名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String formName;

    /**
     * 开启状态
     */
    @NotBlank(message = "启用状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;
    /**
     * 表单的配置
     */
    @NotBlank(message = "表单的配置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String conf;
    /**
     * 表单项的数组
     */
    @NotBlank(message = "表单项的数组不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fields;

    /**
     * 备注
     */
    private String remark;
}
