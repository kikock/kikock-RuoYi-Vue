package com.ruoyi.flowable.service.task;

import com.ruoyi.flowable.domain.task.vo.BpmTaskPageReqVO;
import liquibase.pro.packaged.T;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

public interface IBpmTaskService {
    List<T> getToDotask(Long userId, BpmTaskPageReqVO pageReqVO);

    Map<String, List<Task>> getTaskMapByProcessInstanceIds(List<String> processInstanceIds);
}
