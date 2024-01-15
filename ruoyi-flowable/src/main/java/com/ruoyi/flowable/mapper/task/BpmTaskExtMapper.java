package com.ruoyi.flowable.mapper.task;


import com.ruoyi.flowable.domain.task.BpmTaskExt;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 工作流的流程任务的拓展Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface BpmTaskExtMapper{
    /**
     * 查询工作流的流程任务的拓展
     *
     * @param id 工作流的流程任务的拓展主键
     * @return 工作流的流程任务的拓展
     */
    public BpmTaskExt selectBpmTaskExtById(Long id);

    /**
     * 查询工作流的流程任务的拓展列表
     *
     * @param bpmTaskExt 工作流的流程任务的拓展
     * @return 工作流的流程任务的拓展集合
     */
    public List<BpmTaskExt> selectBpmTaskExtList(BpmTaskExt bpmTaskExt);

    /**
     * 新增工作流的流程任务的拓展
     *
     * @param bpmTaskExt 工作流的流程任务的拓展
     * @return 结果
     */
    public int insertBpmTaskExt(BpmTaskExt bpmTaskExt);

    /**
     * 修改工作流的流程任务的拓展
     *
     * @param bpmTaskExt 工作流的流程任务的拓展
     * @return 结果
     */
    public int updateBpmTaskExt(BpmTaskExt bpmTaskExt);

    /**
     * 删除工作流的流程任务的拓展
     *
     * @param id 工作流的流程任务的拓展主键
     * @return 结果
     */
    public int deleteBpmTaskExtById(Long id);

    /**
     * 批量删除工作流的流程任务的拓展
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBpmTaskExtByIds(Long[] ids);

    /**
     * 根据ids查询工作流的流程任务的拓展
     *
     * @param ids 工作流的流程任务的拓展主键
     * @return 工作流的流程任务的拓展
     */
    public List<BpmTaskExt> selectListByTaskIds(Set<String> ids);

    public BpmTaskExt findByTaskId(@Param("taskId") String taskId);
}
