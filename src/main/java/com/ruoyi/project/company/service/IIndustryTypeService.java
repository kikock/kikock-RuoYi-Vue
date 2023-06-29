package com.ruoyi.project.company.service;

import java.util.List;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.company.domain.IndustryType;

/**
 * 行业类型Service接口
 * 
 * @author ruoyi
 * @date 2022-05-25
 */
public interface IIndustryTypeService 
{
    /**
     * 查询行业类型
     * 
     * @param id 行业类型主键
     * @return 行业类型
     */
    public IndustryType selectIndustryTypeById(String id);

    /**
     * 查询行业类型列表
     * 
     * @param industryType 行业类型
     * @return 行业类型集合
     */
    public List<IndustryType> selectIndustryTypeList(IndustryType industryType);

    /**
     * 新增行业类型
     * 
     * @param industryType 行业类型
     * @return 结果
     */
    public int insertIndustryType(IndustryType industryType);

    /**
     * 修改行业类型
     * 
     * @param industryType 行业类型
     * @return 结果
     */
    public int updateIndustryType(IndustryType industryType);

    /**
     * 批量删除行业类型
     * 
     * @param ids 需要删除的行业类型主键集合
     * @return 结果
     */
    public int deleteIndustryTypeByIds(String[] ids);

    /**
     * 删除行业类型信息
     * 
     * @param id 行业类型主键
     * @return 结果
     */
    public int deleteIndustryTypeById(String id);

    /**
     * 构建前端所需要下拉树结构
     * @param types 行业类型列表
     * @return 下拉树结构列表
     */
    List<IndustryType> buildIndustryTypeTree(List<IndustryType> types);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param types 行业类型列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildMenuTreeSelect(List<IndustryType> types);
}
