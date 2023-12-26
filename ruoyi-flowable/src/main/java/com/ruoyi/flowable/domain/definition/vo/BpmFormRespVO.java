package com.ruoyi.flowable.domain.definition.vo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmFormRespVO extends BpmFormBaseVO {

    private Long id;

    @NotNull(message = "表单的配置不能为空")
    private String conf;

    @NotNull(message = "表单项的数组不能为空")
    private List<String> fields;

    private LocalDateTime createTime;

}
