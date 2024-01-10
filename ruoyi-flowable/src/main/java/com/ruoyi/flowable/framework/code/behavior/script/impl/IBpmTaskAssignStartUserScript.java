package com.ruoyi.flowable.framework.code.behavior.script.impl;

import com.alibaba.excel.util.NumberUtils;
import com.ruoyi.common.utils.collection.SetUtils;
import com.ruoyi.flowable.framework.code.behavior.script.IBpmTaskAssignScript;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
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

    @Resource
    @Lazy // 解决循环依赖
    private IBpmProcessInstanceService bpmProcessInstanceService;

    @Override
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        ProcessInstance processInstance = bpmProcessInstanceService.getProcessInstance(execution.getProcessInstanceId());
        String startUserId = processInstance.getStartUserId();
        return SetUtils.asSet(Long.valueOf(startUserId));
    }
}
