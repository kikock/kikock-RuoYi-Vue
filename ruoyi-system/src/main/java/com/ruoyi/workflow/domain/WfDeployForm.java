package com.ruoyi.workflow.domain;


import lombok.Data;

import java.util.Map;

/**
 * 流程实例关联表单对象 sys_instance_form
 *
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@Data
public class WfDeployForm {
    private static final long serialVersionUID = 1L;

    /**
     * 流程部署主键
     */
    private String deployId;

    /**
     * 表单Key
     */
    private String formKey;

    /**
     * 节点Key
     */
    private String nodeKey;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 表单内容
     */
    private String content;

    /**
     * 表单的配置
     */
    private String conf;

    /**
     * 表单项的数组
     */
    private String fields;
    /**
     * 表单项的内容
     */
    private Map<String,Object> variables;

    public WfDeployForm(String deployId, String formKey, String nodeKey){
        this.deployId = deployId;
        this.formKey = formKey;
        this.nodeKey = nodeKey;
    }

    public WfDeployForm(){

    }
}
