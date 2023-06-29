package com.ruoyi.project.visitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.visitor.mapper.VisitorHealthMapper;
import com.ruoyi.project.visitor.domain.VisitorHealth;
import com.ruoyi.project.visitor.service.IVisitorHealthService;

/**
 * 核酸报告填报记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-10
 */
@Service
public class VisitorHealthServiceImpl implements IVisitorHealthService
{
    @Autowired
    private VisitorHealthMapper visitorHealthMapper;

    /**
     * 查询核酸报告填报记录
     *
     * @param id 核酸报告填报记录主键
     * @return 核酸报告填报记录
     */
    @Override
    public VisitorHealth selectVisitorHealthById(String id)
    {
        return visitorHealthMapper.selectVisitorHealthById(id);
    }

    /**
     * 查询核酸报告填报记录列表
     *
     * @param visitorHealth 核酸报告填报记录
     * @return 核酸报告填报记录
     */
    @Override
    public List<VisitorHealth> selectVisitorHealthList(VisitorHealth visitorHealth)
    {
        return visitorHealthMapper.selectVisitorHealthList(visitorHealth);
    }

    /**
     * 新增核酸报告填报记录
     *
     * @param visitorHealth 核酸报告填报记录
     * @return 结果
     */
    @Override
    public int insertVisitorHealth(VisitorHealth visitorHealth)
    {
        visitorHealth.setCreateTime(DateUtils.getNowDate());
        return visitorHealthMapper.insertVisitorHealth(visitorHealth);
    }

    /**
     * 修改核酸报告填报记录
     *
     * @param visitorHealth 核酸报告填报记录
     * @return 结果
     */
    @Override
    public int updateVisitorHealth(VisitorHealth visitorHealth)
    {
        return visitorHealthMapper.updateVisitorHealth(visitorHealth);
    }

    /**
     * 批量删除核酸报告填报记录
     *
     * @param ids 需要删除的核酸报告填报记录主键
     * @return 结果
     */
    @Override
    public int deleteVisitorHealthByIds(String[] ids)
    {
        return visitorHealthMapper.deleteVisitorHealthByIds(ids);
    }

    /**
     * 删除核酸报告填报记录信息
     *
     * @param id 核酸报告填报记录主键
     * @return 结果
     */
    @Override
    public int deleteVisitorHealthById(String id)
    {
        return visitorHealthMapper.deleteVisitorHealthById(id);
    }

    @Override
    public List<VisitorHealth> getVisitorHealthList(String time,String wxId) {
        return visitorHealthMapper.getVisitorHealthList(time,wxId);
    }
}
