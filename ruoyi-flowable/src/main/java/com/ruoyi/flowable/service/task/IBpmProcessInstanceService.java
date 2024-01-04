package com.ruoyi.flowable.service.task;

import com.ruoyi.flowable.domain.task.vo.BpmTaskPageReqVO;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBpmProcessInstanceService {
    /**
     * 获得流程实例的分页
     *
     * @param pageReqVO 分页请求
     * @return 流程实例的分页
     */
    List<?> getMyProcessInstancePage(BpmTaskPageReqVO pageReqVO);
    /**
     * 创建流程实例（提供给前端）
     *
     * @param userId 用户编号
     * @param createReqVO 创建信息
     * @return 实例的编号
     */
    String createProcessInstance(Long userId, BpmTaskPageReqVO createReqVO);
    /**
     * 获得流程实例列表
     *
     * @param ids 流程实例的编号集合
     * @return 流程实例列表
     */
    List<ProcessInstance> getProcessInstances(Set<String> ids);
    /**
     * 获得历史的流程实例列表
     *
     * @param ids 流程实例的编号集合
     * @return 历史的流程实例列表
     */
    List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids);

}
