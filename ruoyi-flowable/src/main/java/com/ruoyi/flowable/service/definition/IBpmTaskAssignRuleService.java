package com.ruoyi.flowable.service.definition;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;
import org.flowable.engine.delegate.DelegateExecution;

import java.util.List;
import java.util.Set;

/**
 * Bpm 任务规则Service接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface IBpmTaskAssignRuleService{
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
     * 批量删除Bpm 任务规则
     *
     * @param ids 需要删除的Bpm 任务规则主键集合
     * @return 结果
     */
    public int deleteBpmTaskAssignRuleByIds(Long[] ids);

    /**
     * 删除Bpm 任务规则信息
     *
     * @param id Bpm 任务规则主键
     * @return 结果
     */
    public int deleteBpmTaskAssignRuleById(Long id);

    public int createTaskAssignRule(BpmTaskAssignRule reqVO);

    public int updateTaskAssignRule(BpmTaskAssignRule reqVO);


    /**
     * 校验流程模型的任务分配规则全部都配置了
     * 目的：如果有规则未配置，会导致流程任务找不到负责人，进而流程无法进行下去！
     *
     * @param id 流程模型编号
     */
   public void checkTaskAssignRuleAllConfig(String id);

    public boolean isTaskAssignRulesEquals(String id, String id1);
    /**
     * 复制任务分配规则
     *
     */
    public void copyTaskAssignRules(String id, String id1);

    /**
     * 计算当前执行任务的处理人
     *
     * @param execution 执行任务
     * @return 处理人的编号数组
     */
    Set<Long> calculateTaskCandidateUsers(DelegateExecution execution);
    /**
     * 计算当前执行任务的处理人
     *
     * @param name 执行名称
     * @param processDefinitionId 任务id
     * @param taskDefinitionKey 任务标识
     *
     * @return 处理人的编号数组
     */
    Set<Long> calculateTaskCandidateUsers2(String name, String processDefinitionId, String taskDefinitionKey);
}
