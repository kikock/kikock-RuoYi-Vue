package com.ruoyi.flowable.service.task;

import com.ruoyi.flowable.domain.task.vo.BpmActivityRespVO;
import org.flowable.engine.history.HistoricActivityInstance;

import java.util.List;

public interface IBpmActivityService {
    /**
     * 获得指定流程实例的活动实例列表
     *
     * @param processInstanceId 流程实例的编号
     * @return 活动实例列表
     */
    List<BpmActivityRespVO> getActivityListByProcessInstanceId(String processInstanceId);

    /**
     * 获得执行编号对应的活动实例
     *
     * @param executionId 执行编号
     * @return 活动实例
     */
    List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId);
}
