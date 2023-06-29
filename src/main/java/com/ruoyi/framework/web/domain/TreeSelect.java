package com.ruoyi.framework.web.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.project.company.domain.IndustryType;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.weixin.domain.SysWxMenu;
import io.swagger.annotations.ApiModelProperty;

/**
 * Treeselect树结构实体类
 * 
 * @author ruoyi
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    @ApiModelProperty(value = "节点ID")
    private Long id;

    /** 节点名称 */
    @ApiModelProperty(value = "节点名称")
    private String label;

    /** 节点ID */
    @ApiModelProperty(value = "节点ID")
    private String key;

    /** 子节点 */
    @ApiModelProperty(value = "子节点")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysWxMenu menu)
    {
        this.key = menu.getId();
        this.label = menu.getName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(IndustryType type) {
        this.key = type.getId();
        this.label = type.getName();
        this.children = type.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
