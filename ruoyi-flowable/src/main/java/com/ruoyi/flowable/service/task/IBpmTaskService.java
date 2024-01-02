package com.ruoyi.flowable.service.task;

import com.ruoyi.flowable.domain.task.vo.BpmTaskTodoPageReqVO;
import liquibase.pro.packaged.T;

import java.util.List;

public interface IBpmTaskService {
    List<T> getToDotask(Long userId, BpmTaskTodoPageReqVO pageReqVO);

}
