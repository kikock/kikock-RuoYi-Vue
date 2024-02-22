package com.ruoyi.workflow.service;


import com.ruoyi.workflow.domain.WfCategory;

import java.util.Collection;
import java.util.List;

/**
 * 流程分类Service接口
 *
 * @author kikock
 * @date 2024-02-02
 */
public interface IWfCategoryService{
    /**
     * 查询流程分类
     *
     * @param categoryId 流程分类主键
     * @return 流程分类
     */
    public WfCategory selectWfCategoryByCategoryId(Long categoryId);

    /**
     * 查询流程分类列表
     *
     * @param wfCategory 流程分类
     * @return 流程分类集合
     */
    public List<WfCategory> selectWfCategoryList(WfCategory wfCategory);

    /**
     * 新增流程分类
     *
     * @param wfCategory 流程分类
     * @return 结果
     */
    public int insertWfCategory(WfCategory wfCategory);

    /**
     * 修改流程分类
     *
     * @param wfCategory 流程分类
     * @return 结果
     */
    public int updateWfCategory(WfCategory wfCategory);

    /**
     * 批量删除流程分类
     *
     * @param categoryIds 需要删除的流程分类主键集合
     * @return 结果
     */
    public int deleteWfCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除流程分类信息
     *
     * @param categoryId 流程分类主键
     * @return 结果
     */
    public int deleteWfCategoryByCategoryId(Long categoryId);
    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    int deleteWithValidByIds(Long[] ids, Boolean isValid);

    /**
     * 校验分类编码是否唯一
     *
     * @param category 流程分类
     * @return 结果
     */
    boolean checkCategoryCodeUnique(WfCategory category);
}
