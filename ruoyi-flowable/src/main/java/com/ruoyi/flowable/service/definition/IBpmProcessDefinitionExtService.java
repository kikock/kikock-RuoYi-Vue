package com.ruoyi.flowable.service.definition;

import com.ruoyi.flowable.domain.definition.BpmProcessDefinitionExt;

import java.util.List;

/**
 * Bpm 流程定义的拓展
 * Service接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface IBpmProcessDefinitionExtService{
    /**
     * 查询Bpm 流程定义的拓展
     *
     * @param id Bpm 流程定义的拓展
     *           主键
     * @return Bpm 流程定义的拓展
     */
    public BpmProcessDefinitionExt selectBpmProcessDefinitionExtById(Long id);

    /**
     * 查询Bpm 流程定义的拓展
     * 列表
     *
     * @param bpmProcessDefinitionExt Bpm 流程定义的拓展
     * @return Bpm 流程定义的拓展
     * 集合
     */
    public List<BpmProcessDefinitionExt> selectBpmProcessDefinitionExtList(BpmProcessDefinitionExt bpmProcessDefinitionExt);

    /**
     * 新增Bpm 流程定义的拓展
     *
     * @param bpmProcessDefinitionExt Bpm 流程定义的拓展
     * @return 结果
     */
    public int insertBpmProcessDefinitionExt(BpmProcessDefinitionExt bpmProcessDefinitionExt);

    /**
     * 修改Bpm 流程定义的拓展
     *
     * @param bpmProcessDefinitionExt Bpm 流程定义的拓展
     * @return 结果
     */
    public int updateBpmProcessDefinitionExt(BpmProcessDefinitionExt bpmProcessDefinitionExt);

    /**
     * 批量删除Bpm 流程定义的拓展
     *
     * @param ids 需要删除的Bpm 流程定义的拓展
     *            主键集合
     * @return 结果
     */
    public int deleteBpmProcessDefinitionExtByIds(Long[] ids);

    /**
     * 删除Bpm 流程定义的拓展
     * 信息
     *
     * @param id Bpm 流程定义的拓展
     *           主键
     * @return 结果
     */
    public int deleteBpmProcessDefinitionExtById(Long id);
}
