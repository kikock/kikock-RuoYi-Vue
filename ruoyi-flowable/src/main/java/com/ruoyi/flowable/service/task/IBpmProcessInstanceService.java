package com.ruoyi.flowable.service.task;

import com.ruoyi.flowable.domain.task.vo.BpmTaskPageReqVO;

import java.util.List;

public interface IBpmProcessInstanceService {
    List<?> getMyProcessInstancePage(BpmTaskPageReqVO pageReqVO);

    String createProcessInstance(Long userId, BpmTaskPageReqVO createReqVO);
}
