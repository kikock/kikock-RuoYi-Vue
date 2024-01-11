package com.ruoyi.flowable.framework.code.behavior;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.google.common.base.Joiner;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.variable.api.persistence.entity.VariableInstance;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.collection.CollectionUtils.convertList;

/**
 * 自定义的【单个】流程任务的 assignee 负责人的分配
 * 第一步，基于分配规则，计算出分配任务的【单个】候选人。如果找不到，则直接报业务异常，不继续执行后续的流程；
 * 第二步，随机选择一个候选人，则选择作为 assignee 负责人。
 *
 * @author 芋道源码
 */
@Slf4j
public class BpmUserTaskActivityBehavior extends UserTaskActivityBehavior {

    @Setter
    private IBpmTaskAssignRuleService bpmTaskRuleService;

    public BpmUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }

    @Override
    protected void handleAssignments(TaskService taskService, String assignee, String owner,
        List<String> candidateUsers, List<String> candidateGroups, TaskEntity task, ExpressionManager expressionManager,
        DelegateExecution execution, ProcessEngineConfigurationImpl processEngineConfiguration) {
        //获得任务的候选用户
        Set<Long> list = calculateTaskCandidateUsers(execution);
        candidateUsers = (List<String>) CollUtil.addAll(candidateUsers, list.stream().map(Object::toString).collect(Collectors.toList()));
        // 设置作为负责人
        if (Strings.isBlank(assignee) && CollUtil.isEmpty(candidateUsers) && CollUtil.isEmpty(candidateGroups)) {
            //没有任何可以办理的人员或角色组
            throw new RuntimeException("找不到任何符合条件的下一步经办人！流程无法继续办理！");
        }
        if (CollectionUtils.isNotEmpty(candidateUsers) && candidateUsers.size() == 1) {
            //只有一个符合条件的主管，直接将任务分配给该主管
            assignee = candidateUsers.get(0);
            candidateUsers = new ArrayList<>(0);
        }
        super.handleAssignments(taskService, assignee, owner, candidateUsers, candidateGroups, task, expressionManager, execution,processEngineConfiguration);
    }

    private Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        // 情况一，如果是多实例的任务，例如说会签、或签等情况，则从 Variable 中获取。它的任务处理人在 BpmParallelMultiInstanceBehavior 中已经被分配了
        if (super.multiInstanceActivityBehavior != null) {
            return Collections.singleton(execution.getVariable(super.multiInstanceActivityBehavior.getCollectionElementVariable(), Long.class));
        }
        // 情况二，如果非多实例的任务，则计算任务处理人
        // 第一步，先计算可处理该任务的处理人们返回
//        return  bpmTaskRuleService.calculateTaskCandidateUsers(execution);
        return  bpmTaskRuleService.calculateTaskCandidateUsers2(execution.getEventName(),execution.getProcessDefinitionId(),execution.getCurrentActivityId());
    }

}
