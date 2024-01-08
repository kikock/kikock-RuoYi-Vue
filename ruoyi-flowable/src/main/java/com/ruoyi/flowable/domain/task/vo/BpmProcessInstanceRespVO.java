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

    private String id;

    private String name;

    private String category;

    private Integer status;

    private Integer result;

    private Date createTime;

    private Date endTime;

    private String formVariables;

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
        private Long id;
        private String nickname;
        private Long deptId;
        private String deptName;
    }

    @Data
    public static class ProcessDefinition {
        private String id;
        private Integer formType;
        private Long formId;
        private String formConf;
        private List<String> formFields;
        private String formCustomCreatePath;
        private String formCustomViewPath;
        private String bpmnXml;
    }

}
