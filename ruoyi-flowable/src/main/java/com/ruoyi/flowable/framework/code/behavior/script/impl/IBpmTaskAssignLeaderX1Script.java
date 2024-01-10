package com.ruoyi.flowable.framework.code.behavior.script.impl;

import org.flowable.engine.delegate.DelegateExecution;
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
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        return calculateTaskCandidateUsers(execution, 1);
    }
}
