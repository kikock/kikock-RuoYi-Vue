package com.ruoyi.flowable.mapper.definition;


import com.ruoyi.flowable.domain.definition.BpmProcessDefinitionExt;
import com.ruoyi.flowable.domain.definition.vo.BpmProcessDefinitionVo;

import java.util.List;

/**
 * Bpm 流程定义的拓展
 * Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface BpmProcessDefinitionExtMapper{
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
     * 删除Bpm 流程定义的拓展
     *
     * @param id Bpm 流程定义的拓展
     *           主键
     * @return 结果
     */
    public int deleteBpmProcessDefinitionExtById(Long id);

    /**
     * 批量删除Bpm 流程定义的拓展
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBpmProcessDefinitionExtByIds(Long[] ids);


    /**
     * 批量查询 流程定义的拓展信息
     *
     * @param ids 主键集合
     * @return 结果
     */
    public  List<BpmProcessDefinitionExt> selectListByProcessDefinitionIds(List<String> ids);



}
