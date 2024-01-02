package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ObjectUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;
import com.ruoyi.flowable.framework.utils.BpmnModelUtils;
import com.ruoyi.flowable.mapper.definition.BpmTaskAssignRuleMapper;
import com.ruoyi.flowable.service.definition.IBpmModelService;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Bpm 任务规则Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmTaskAssignRuleServiceImpl implements IBpmTaskAssignRuleService{
    @Resource
    private BpmTaskAssignRuleMapper bpmTaskAssignRuleMapper;
    @Autowired
    private IBpmModelService bpmModelService;

    @Autowired
    private IBpmProcessDefinitionService bpmProcessDefinitionService;


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
        // 获得规则
        List<BpmTaskAssignRule> rules = Collections.emptyList();
        BpmnModel model = null;
        if (StrUtil.isNotEmpty(bpmTaskAssignRule.getModelId())) {
            //数据库规则
            rules = bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleList(bpmTaskAssignRule);
            //流程数据规则
            model = bpmModelService.getBpmnModel(bpmTaskAssignRule.getModelId());
        } else if (StrUtil.isNotEmpty(bpmTaskAssignRule.getProcessDefinitionId())) {
            //数据库规则
            rules = bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleList(bpmTaskAssignRule);
            //流程数据规则
            model = bpmProcessDefinitionService.getBpmnModel(bpmTaskAssignRule.getProcessDefinitionId());
        }
        if (model == null) {
            return Collections.emptyList();
        }
//        // 获得用户任务，只有用户任务才可以设置分配规则
        List<UserTask> userTasks = BpmnModelUtils.getBpmnModelElements(model, UserTask.class);
        if (CollUtil.isEmpty(userTasks)) {
            return Collections.emptyList();
        }
//        // 转换数据
        Map<String, BpmTaskAssignRule> ruleMap = CollectionUtils.convertMap(rules, BpmTaskAssignRule::getTaskDefinitionKey);

        return CollectionUtils.convertList(userTasks, task -> {
            BpmTaskAssignRule respVO = ruleMap.get(task.getId());
            if (respVO == null) {
                respVO =new BpmTaskAssignRule();
                respVO.setTaskDefinitionKey(task.getId());
            }
            respVO.setTaskDefinitionName(task.getName());
            return respVO;
        });
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
