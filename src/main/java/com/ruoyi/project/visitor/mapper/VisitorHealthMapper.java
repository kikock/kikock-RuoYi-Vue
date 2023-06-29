package com.ruoyi.project.visitor.mapper;

import com.ruoyi.project.visitor.domain.VisitorHealth;

import java.util.List;

/**
 * 核酸报告填报记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-10-10
 */
public interface VisitorHealthMapper {
    /**
     * 查询核酸报告填报记录
     *
     * @param id 核酸报告填报记录主键
     * @return 核酸报告填报记录
     */
    public VisitorHealth selectVisitorHealthById(String id);

    /**
     * 查询核酸报告填报记录列表
     *
     * @param visitorHealth 核酸报告填报记录
     * @return 核酸报告填报记录集合
     */
    public List<VisitorHealth> selectVisitorHealthList(VisitorHealth visitorHealth);

    /**
     * 新增核酸报告填报记录
     *
     * @param visitorHealth 核酸报告填报记录
     * @return 结果
     */
    public int insertVisitorHealth(VisitorHealth visitorHealth);

    /**
     * 修改核酸报告填报记录
     *
     * @param visitorHealth 核酸报告填报记录
     * @return 结果
     */
    public int updateVisitorHealth(VisitorHealth visitorHealth);

    /**
     * 删除核酸报告填报记录
     *
     * @param id 核酸报告填报记录主键
     * @return 结果
     */
    public int deleteVisitorHealthById(String id);

    /**
     * 批量删除核酸报告填报记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisitorHealthByIds(String[] ids);

    /**
     * 删除核酸报告填报记录
     *
     * @param   createTime 核酸报告填报时间
     * @return 结果
     */
    public List<VisitorHealth> getVisitorHealthList(String createTime,String wxId);


}
