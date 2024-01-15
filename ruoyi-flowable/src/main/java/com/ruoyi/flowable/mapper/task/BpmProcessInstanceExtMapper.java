package com.ruoyi.flowable.mapper.task;


import com.ruoyi.flowable.domain.task.BpmProcessInstanceExt;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 工作流的流程实例的拓展Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface BpmProcessInstanceExtMapper{
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
     * 删除工作流的流程实例的拓展
     *
     * @param id 工作流的流程实例的拓展主键
     * @return 结果
     */
    public int deleteBpmProcessInstanceExtById(Long id);

    /**
     * 批量删除工作流的流程实例的拓展
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBpmProcessInstanceExtByIds(Long[] ids);

    List<BpmProcessInstanceExt> selectPage(BpmTaskReqVO pageReqVO);

    public BpmProcessInstanceExt selectBpmProcessInstanceExtByProcessDefinitionId(@Param("processDefinitionId") String processDefinitionId);

    public int updateBpmProcessInstanceExtByProcessDefinitionId(BpmProcessInstanceExt bpmProcessInstanceExt);
}
