package com.ruoyi.project.company.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.company.mapper.IndustryTypeMapper;
import com.ruoyi.project.company.domain.IndustryType;
import com.ruoyi.project.company.service.IIndustryTypeService;

/**
 * 行业类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-05-25
 */
@Service
public class IndustryTypeServiceImpl implements IIndustryTypeService 
{
    @Autowired
    private IndustryTypeMapper industryTypeMapper;

    /**
     * 查询行业类型
     * 
     * @param id 行业类型主键
     * @return 行业类型
     */
    @Override
    public IndustryType selectIndustryTypeById(String id)
    {
        return industryTypeMapper.selectIndustryTypeById(id);
    }

    /**
     * 查询行业类型列表
     * 
     * @param industryType 行业类型
     * @return 行业类型
     */
    @Override
    public List<IndustryType> selectIndustryTypeList(IndustryType industryType)
    {
        return industryTypeMapper.selectIndustryTypeList(industryType);
    }

    /**
     * 新增行业类型
     * 
     * @param industryType 行业类型
     * @return 结果
     */
    @Override
    public int insertIndustryType(IndustryType industryType)
    {
        industryType.setCreateTime(DateUtils.getNowDate());
        return industryTypeMapper.insertIndustryType(industryType);
    }

    /**
     * 修改行业类型
     * 
     * @param industryType 行业类型
     * @return 结果
     */
    @Override
    public int updateIndustryType(IndustryType industryType)
    {
        industryType.setUpdateTime(DateUtils.getNowDate());
        return industryTypeMapper.updateIndustryType(industryType);
    }

    /**
     * 批量删除行业类型
     * 
     * @param ids 需要删除的行业类型主键
     * @return 结果
     */
    @Override
    public int deleteIndustryTypeByIds(String[] ids)
    {
        return industryTypeMapper.deleteIndustryTypeByIds(ids);
    }

    /**
     * 删除行业类型信息
     * 
     * @param id 行业类型主键
     * @return 结果
     */
    @Override
    public int deleteIndustryTypeById(String id)
    {
        return industryTypeMapper.deleteIndustryTypeById(id);
    }

    /**
     * 构建前端所需要下拉树结构
     * @param types 行业类型列表
     * @return 下拉树结构列表
     */
    @Override
    public List<IndustryType> buildIndustryTypeTree(List<IndustryType> types) {
        List<IndustryType> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (IndustryType type : types) {
            tempList.add(type.getId());
        }
        for (Iterator<IndustryType> iterator = types.iterator(); iterator.hasNext();) {
            IndustryType type = iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(type.getParentId()))
            {
                recursionFn(types, type);
                returnList.add(type);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = types;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param types 行业类型列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<IndustryType> types) {
        List<IndustryType> industryTypes = buildIndustryTypeTree(types);
        return industryTypes.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<IndustryType> list, IndustryType t)
    {
        // 得到子节点列表
        List<IndustryType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (IndustryType tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<IndustryType> getChildList(List<IndustryType> list, IndustryType t)
    {
        List<IndustryType> tlist = new ArrayList<>();
        Iterator<IndustryType> it = list.iterator();
        while (it.hasNext())
        {
            IndustryType n =  it.next();
            if (n.getParentId().equals(t.getId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<IndustryType> list, IndustryType t)
    {
        return getChildList(list, t).size() > 0;
    }
}
