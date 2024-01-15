package com.ruoyi.flowable.framework.code.behavior.script.impl;

import com.ruoyi.common.utils.collection.SetUtils;
import com.ruoyi.flowable.enums.BpmTaskRuleScriptEnum;
import com.ruoyi.flowable.framework.code.behavior.script.IBpmTaskAssignScript;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 分配给发起人审批的 Script 实现类
 *
 * @author 芋道源码
 */
@Component
public class IBpmTaskAssignStartUserScript implements IBpmTaskAssignScript{

    @Autowired
    @Lazy
    private RuntimeService runtimeService;
    @Override
    public Set<Long> calculateTaskCandidateUsers(String processInstanceId){
        ProcessInstance processInstance =   runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String startUserId = processInstance.getStartUserId();
        return SetUtils.asSet(Long.valueOf(startUserId));
    }
    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.START_USER;
    }
}
