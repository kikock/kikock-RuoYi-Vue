package com.ruoyi.flowable.domain.definition.vo;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 动态表单 Base VO，提供给添加、修改、详细的子 VO 使用
*/
@Data
public class BpmFormBaseVO {

    @NotNull(message = "表单名称不能为空")
    private String name;

    @NotNull(message = "表单状态不能为空")
    private Integer status;

    private String remark;

}
