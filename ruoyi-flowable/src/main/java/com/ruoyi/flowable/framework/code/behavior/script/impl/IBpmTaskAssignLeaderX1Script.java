package com.ruoyi.flowable.framework.code.behavior.script.impl;

import com.ruoyi.flowable.enums.BpmTaskRuleScriptEnum;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 分配给发起人的一级 Leader 审批的 Script 实现类
 *
 * @author 芋道源码
 */
@Component
public class IBpmTaskAssignLeaderX1Script extends IBpmTaskAssignLeaderAbstractScript{

    @Override
    public Set<Long> calculateTaskCandidateUsers(String processInstanceId) {
        return calculateTaskCandidateUsers(processInstanceId, 1);
    }

    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.LEADER_X1;
    }
}
