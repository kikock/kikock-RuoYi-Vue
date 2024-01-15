package com.ruoyi.flowable.domain.definition;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Bpm 任务规则对象 bpm_task_assign_rule
 *
 * @author kikock
 * @date 2023-12-22
 */
@Data
public class BpmTaskAssignRule extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 流程模型的编号
     */
    @Excel(name = "流程模型的编号")
    @NotEmpty(message = "流程模型的编号不能为空")
    private String modelId;

    /**
     * 流程定义的编号
     */
    @Excel(name = "流程定义的编号")
    private String processDefinitionId;
    /**
     * 流程任务定义的 key
     */
    @Excel(name = "流程任务定义的 key")
    private String taskDefinitionKey;

    private String taskDefinitionName;
    /**
     * 规则类型
     */
    @Excel(name = "规则类型")
    @NotNull(message = "规则类型不能为空")
    private Integer type;

    /**
     * 规则值，JSON 数组
     */
    @Excel(name = "规则值，JSON 数组")
    private String options;

    /**
     * 规则值数组，一般关联指定表的编号
     * 根据 type 不同，对应的值是不同的：
     *
     */
    private List<Long> optionIds;

    /**
     *
     * 回显内容
     *
     */
    private List<SelectMoreVo> selectMoreVos;
    /**
     *
     * 列表显示名称
     *
     */
    private String optionName;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;


}
