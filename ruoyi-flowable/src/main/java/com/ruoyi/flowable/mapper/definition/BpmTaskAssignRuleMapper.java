package com.ruoyi.flowable.mapper.definition;


import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;

import java.util.List;

/**
 * Bpm 任务规则Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface BpmTaskAssignRuleMapper{
    /**
     * 查询Bpm 任务规则
     *
     * @param id Bpm 任务规则主键
     * @return Bpm 任务规则
     */
    public BpmTaskAssignRule selectBpmTaskAssignRuleById(Long id);

    /**
     * 查询Bpm 任务规则列表
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return Bpm 任务规则集合
     */
    public List<BpmTaskAssignRule> selectBpmTaskAssignRuleList(BpmTaskAssignRule bpmTaskAssignRule);

    /**
     * 新增Bpm 任务规则
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return 结果
     */
    public int insertBpmTaskAssignRule(BpmTaskAssignRule bpmTaskAssignRule);

    /**
     * 修改Bpm 任务规则
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return 结果
     */
    public int updateBpmTaskAssignRule(BpmTaskAssignRule bpmTaskAssignRule);

    /**
     * 删除Bpm 任务规则
     *
     * @param id Bpm 任务规则主键
     * @return 结果
     */
    public int deleteBpmTaskAssignRuleById(Long id);

    /**
     * 批量删除Bpm 任务规则
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBpmTaskAssignRuleByIds(Long[] ids);
}
