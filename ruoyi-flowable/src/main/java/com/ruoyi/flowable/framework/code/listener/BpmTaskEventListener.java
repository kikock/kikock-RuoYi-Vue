package com.ruoyi.flowable.framework.code.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableSet;
import com.ruoyi.flowable.domain.task.BpmTaskExt;
import com.ruoyi.flowable.service.task.IBpmActivityService;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.delegate.event.FlowableActivityCancelledEvent;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.task.api.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 监听 {@link Task} 的开始与完成，创建与更新对应的 {@link BpmTaskExt} 记录
 *
 * @author jason
 */
@Component
@Slf4j
public class BpmTaskEventListener extends AbstractFlowableEngineEventListener {

    @Resource
    @Lazy // 解决循环依赖
    private IBpmTaskService taskService;

    @Resource
    @Lazy // 解决循环依赖
    private IBpmActivityService activityService;

    public static final Set<FlowableEngineEventType> TASK_EVENTS = ImmutableSet.<FlowableEngineEventType>builder()
            .add(FlowableEngineEventType.TASK_CREATED)
            .add(FlowableEngineEventType.TASK_ASSIGNED)
            .add(FlowableEngineEventType.TASK_COMPLETED)
            .add(FlowableEngineEventType.ACTIVITY_CANCELLED)
            .build();

    public BpmTaskEventListener(){
        super(TASK_EVENTS);
    }

    @Override
    protected void taskCreated(FlowableEngineEntityEvent event) {
        taskService.createTaskExt((Task) event.getEntity());
    }

    @Override
    protected void taskCompleted(FlowableEngineEntityEvent event) {
        taskService.updateTaskExtComplete((Task)event.getEntity());
    }

    @Override
    protected void taskAssigned(FlowableEngineEntityEvent event) {
        taskService.updateTaskExtAssign((Task)event.getEntity());
    }

    @Override
    protected void activityCancelled(FlowableActivityCancelledEvent event) {
        List<HistoricActivityInstance> activityList = activityService.getHistoricActivityListByExecutionId(event.getExecutionId());
        if (CollUtil.isEmpty(activityList)) {
            log.error("[activityCancelled][使用 executionId({}) 查找不到对应的活动实例]", event.getExecutionId());
            return;
        }
        // 遍历处理
        activityList.forEach(activity -> {
            if (StrUtil.isEmpty(activity.getTaskId())) {
                return;
            }
            taskService.updateTaskExtCancel(activity.getTaskId());
        });
    }

}
