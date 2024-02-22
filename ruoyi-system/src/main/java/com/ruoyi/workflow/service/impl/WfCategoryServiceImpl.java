package com.ruoyi.workflow.service.impl;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.workflow.domain.WfCategory;
import com.ruoyi.workflow.mapper.WfCategoryMapper;
import com.ruoyi.workflow.service.IWfCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 流程分类Service业务层处理
 *
 * @author kikock
 * @date 2024-02-02
 */
@Service
public class WfCategoryServiceImpl implements IWfCategoryService{
    @Autowired
    private WfCategoryMapper wfCategoryMapper;

    /**
     * 查询流程分类
     *
     * @param categoryId 流程分类主键
     * @return 流程分类
     */
    @Override
    public WfCategory selectWfCategoryByCategoryId(Long categoryId){
        return wfCategoryMapper.selectWfCategoryByCategoryId(categoryId);
    }

    /**
     * 查询流程分类列表
     *
     * @param wfCategory 流程分类
     * @return 流程分类
     */
    @Override
    public List<WfCategory> selectWfCategoryList(WfCategory wfCategory){
        return wfCategoryMapper.selectWfCategoryList(wfCategory);
    }

    /**
     * 新增流程分类
     *
     * @param wfCategory 流程分类
     * @return 结果
     */
    @Override
    public int insertWfCategory(WfCategory wfCategory){
        wfCategory.setCreateTime(DateUtils.getNowDate());
        return wfCategoryMapper.insertWfCategory(wfCategory);
    }

    /**
     * 修改流程分类
     *
     * @param wfCategory 流程分类
     * @return 结果
     */
    @Override
    public int updateWfCategory(WfCategory wfCategory){
        wfCategory.setUpdateTime(DateUtils.getNowDate());
        return wfCategoryMapper.updateWfCategory(wfCategory);
    }

    /**
     * 批量删除流程分类
     *
     * @param categoryIds 需要删除的流程分类主键
     * @return 结果
     */
    @Override
    public int deleteWfCategoryByCategoryIds(Long[] categoryIds){
        return wfCategoryMapper.deleteWfCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除流程分类信息
     *
     * @param categoryId 流程分类主键
     * @return 结果
     */
    @Override
    public int deleteWfCategoryByCategoryId(Long categoryId){
        return wfCategoryMapper.deleteWfCategoryByCategoryId(categoryId);
    }
    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    @Override
    public int deleteWithValidByIds(Long[] ids, Boolean isValid){
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return wfCategoryMapper.deleteWfCategoryByCategoryIds(ids);
    }
    /**
     * 校验分类编码是否唯一
     *
     * @param category 流程分类
     * @return 结果
     */
    @Override
    public boolean checkCategoryCodeUnique(WfCategory category){
        List<WfCategory> wfCategories = wfCategoryMapper.selectWfCategoryList(category);
        if (CollectionUtils.isNotEmpty(wfCategories)&& wfCategories.size()>0){
            return false;
        };
        return true;
    }
}
