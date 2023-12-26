package com.ruoyi.flowable.domain.definition.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmFormCreateReqVO extends BpmFormBaseVO {

    @NotNull(message = "表单的配置不能为空")
    private String conf;

    @NotNull(message = "表单项的数组不能为空")
    private List<String> fields;

}
