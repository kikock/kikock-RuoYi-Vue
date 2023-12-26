package com.ruoyi.flowable.domain.definition.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
/**
 * 流程模型 导入文件
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmModeImportReqVO extends BpmModelCreateReqVO {
    /**
     * BPMN 文件
     */
    @NotNull(message = "BPMN 文件不能为空")
    private MultipartFile bpmnFile;

}
