package com.ruoyi.workflow.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 部署实例和表单关联视图对象
 *
 * @author KonBAI
 * @createTime 2022/7/17 18:29
 */
@Data
public class WfDeployFormVo {

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
     * 表单项的输入内容
     */
    private Map<String,Object> variables;

    /**
     * 表单所属节点集合
     */
    private boolean start =false;
}
