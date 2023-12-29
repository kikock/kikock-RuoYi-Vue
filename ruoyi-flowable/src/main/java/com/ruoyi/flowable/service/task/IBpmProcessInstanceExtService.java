package com.ruoyi.flowable.service.task;

import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.task.BpmProcessInstanceExt;
import org.flowable.engine.repository.Deployment;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 工作流的流程实例的拓展Service接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface IBpmProcessInstanceExtService{
    /**
     * 查询工作流的流程实例的拓展
     *
     * @param id 工作流的流程实例的拓展主键
     * @return 工作流的流程实例的拓展
     */
    public BpmProcessInstanceExt selectBpmProcessInstanceExtById(Long id);

    /**
     * 查询工作流的流程实例的拓展列表
     *
     * @param bpmProcessInstanceExt 工作流的流程实例的拓展
     * @return 工作流的流程实例的拓展集合
     */
    public List<BpmProcessInstanceExt> selectBpmProcessInstanceExtList(BpmProcessInstanceExt bpmProcessInstanceExt);

    /**
     * 新增工作流的流程实例的拓展
     *
     * @param bpmProcessInstanceExt 工作流的流程实例的拓展
     * @return 结果
     */
    public int insertBpmProcessInstanceExt(BpmProcessInstanceExt bpmProcessInstanceExt);

    /**
     * 修改工作流的流程实例的拓展
     *
     * @param bpmProcessInstanceExt 工作流的流程实例的拓展
     * @return 结果
     */
    public int updateBpmProcessInstanceExt(BpmProcessInstanceExt bpmProcessInstanceExt);

    /**
     * 批量删除工作流的流程实例的拓展
     *
     * @param ids 需要删除的工作流的流程实例的拓展主键集合
     * @return 结果
     */
    public int deleteBpmProcessInstanceExtByIds(Long[] ids);

    /**
     * 删除工作流的流程实例的拓展信息
     *
     * @param id 工作流的流程实例的拓展主键
     * @return 结果
     */
    public int deleteBpmProcessInstanceExtById(Long id);

    Map<String,Deployment> getDeploymentMap(Set<String> deploymentIds);
}
