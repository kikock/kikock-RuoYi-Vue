package com.ruoyi.flowable.service.definition.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;
import com.ruoyi.flowable.mapper.definition.BpmTaskAssignRuleMapper;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Bpm 任务规则Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmTaskAssignRuleServiceImpl implements IBpmTaskAssignRuleService{
    @Autowired
    private BpmTaskAssignRuleMapper bpmTaskAssignRuleMapper;

    /**
     * 查询Bpm 任务规则
     *
     * @param id Bpm 任务规则主键
     * @return Bpm 任务规则
     */
    @Override
    public BpmTaskAssignRule selectBpmTaskAssignRuleById(Long id){
        return bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleById(id);
    }

    /**
     * 查询Bpm 任务规则列表
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return Bpm 任务规则
     */
    @Override
    public List<BpmTaskAssignRule> selectBpmTaskAssignRuleList(BpmTaskAssignRule bpmTaskAssignRule){
        return bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleList(bpmTaskAssignRule);
    }

    /**
     * 新增Bpm 任务规则
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return 结果
     */
    @Override
    public int insertBpmTaskAssignRule(BpmTaskAssignRule bpmTaskAssignRule){
        bpmTaskAssignRule.setCreateTime(DateUtils.getNowDate());
        return bpmTaskAssignRuleMapper.insertBpmTaskAssignRule(bpmTaskAssignRule);
    }

    /**
     * 修改Bpm 任务规则
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return 结果
     */
    @Override
    public int updateBpmTaskAssignRule(BpmTaskAssignRule bpmTaskAssignRule){
        bpmTaskAssignRule.setUpdateTime(DateUtils.getNowDate());
        return bpmTaskAssignRuleMapper.updateBpmTaskAssignRule(bpmTaskAssignRule);
    }

    /**
     * 批量删除Bpm 任务规则
     *
     * @param ids 需要删除的Bpm 任务规则主键
     * @return 结果
     */
    @Override
    public int deleteBpmTaskAssignRuleByIds(Long[] ids){
        return bpmTaskAssignRuleMapper.deleteBpmTaskAssignRuleByIds(ids);
    }

    /**
     * 删除Bpm 任务规则信息
     *
     * @param id Bpm 任务规则主键
     * @return 结果
     */
    @Override
    public int deleteBpmTaskAssignRuleById(Long id){
        return bpmTaskAssignRuleMapper.deleteBpmTaskAssignRuleById(id);
    }
}
