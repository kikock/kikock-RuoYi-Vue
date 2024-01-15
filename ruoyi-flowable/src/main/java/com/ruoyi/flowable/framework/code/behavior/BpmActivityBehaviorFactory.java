package com.ruoyi.flowable.framework.code.behavior;

import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的 ActivityBehaviorFactory 实现类，目的如下：
 * 1. 自定义 {@link #createUserTaskActivityBehavior(UserTask)}：实现自定义的流程任务的 assignee 负责人的分配
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmActivityBehaviorFactory extends DefaultActivityBehaviorFactory {

    @Autowired
    private IBpmTaskAssignRuleService bpmTaskRuleService;

    @Override
    public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask) {
        BpmUserTaskActivityBehavior bpmUserTaskActivityBehavior = new BpmUserTaskActivityBehavior(userTask);
        bpmUserTaskActivityBehavior.setBpmTaskRuleService(bpmTaskRuleService);
        return bpmUserTaskActivityBehavior;
    }

    @Override
    public ParallelMultiInstanceBehavior createParallelMultiInstanceBehavior(Activity activity,
                                                                             AbstractBpmnActivityBehavior innerActivityBehavior) {

        BpmParallelMultiInstanceBehavior bpmParallelMultiInstanceBehavior = new BpmParallelMultiInstanceBehavior(activity, innerActivityBehavior);
        bpmParallelMultiInstanceBehavior.setBpmTaskRuleService(bpmTaskRuleService);
        return bpmParallelMultiInstanceBehavior;

    }

}
