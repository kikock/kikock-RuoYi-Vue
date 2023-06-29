package com.ruoyi.project.miniapp.controller.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @project_name: jx-sp-service
 * @description: 微信小程序部门信息
 * @create_name: kikock
 * @create_date: 2022-09-22 16:08
 **/
public class WxDeptVo {
    /**
     * 部门ID
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;
    /**
     * 祖籍列表
     */
    private String ancestors;

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 临时储存全称  xx公司/xx部门
     */
    private String fullName;
    /**
     * 父部门--自连接 多对一
     */
    private WxDeptVo parent;
    /**
     * 子部门--自连接 一对多
     */
    private List<WxDeptVo> children ;

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public WxDeptVo getParent() {
        return parent;
    }

    public void setParent(WxDeptVo parent) {
        this.parent = parent;
    }

    public List<WxDeptVo> getChildren() {
        return children;
    }

    public void setChildren(List<WxDeptVo> children) {
        this.children = children;
    }
}
