package com.ruoyi.flowable.domain.task.vo;


import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpmProcessInstanceRespVO {

    @Excel(name = "流程实例的编号")
    private String id;

    @Excel(name = "流程名称")
    private String name;

    @Excel(name = "流程分类-参见 bpm_model_category 数据字典")
    private String category;

    @Excel(name = "流程实例的状态-参见 bpm_process_instance_status")
    private Integer status;

    @Excel(name = "流程实例的结果-参见 bpm_process_instance_result")
    private Integer result;

    @Excel(name = "提交时间")
    private Date createTime;

    @Excel(name = "结束时间")
    private Date endTime;

    @Excel(name = "提交的表单值")
    private String formVariables;

    @Excel(name = "业务的唯一标识-例如说，请假申请的编号")
    private String businessKey;

    /**
     * 发起流程的用户
     */
    private User startUser;

    /**
     * 流程定义
     */
    private ProcessDefinition processDefinition;

    @Data
    public static class User {

        @Excel(name = "用户编号")
        private Long id;
        @Excel(name = "用户昵称")
        private String nickname;

        @Excel(name = "部门编号")
        private Long deptId;
        @Excel(name = "部门名称")
        private String deptName;

    }

    @Data
    public static class ProcessDefinition {

        @Excel(name = "编号")
        private String id;
        @Excel(name = "表单类型-参见 bpm_model_form_type 数据字典")
        private Integer formType;
        @Excel(name = "表单编号-在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时，必须非空")
        private Long formId;
        @Excel(name = "表单的配置-JSON 字符串。在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时，必须非空")
        private String formConf;
        @Excel(name = "表单项的数组-JSON 字符串的数组。在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时，必须非空")
        private List<String> formFields;
        @Excel(name = "自定义表单的提交路径，使用 Vue 的路由地址-在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时，必须非空")
        private String formCustomCreatePath;
        @Excel(name = "自定义表单的查看路径，使用 Vue 的路由地址-在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时，必须非空")
        private String formCustomViewPath;
        @Excel(name = "BPMN XML")
        private String bpmnXml;

    }

}
