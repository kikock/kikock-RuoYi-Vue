package com.ruoyi.flowable.service.definition.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.definition.BpmProcessDefinitionExt;
import com.ruoyi.flowable.mapper.definition.BpmProcessDefinitionExtMapper;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Bpm 流程定义的拓展
 * Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmProcessDefinitionExtServiceImpl implements IBpmProcessDefinitionExtService{
    @Autowired
    private BpmProcessDefinitionExtMapper bpmProcessDefinitionExtMapper;

    /**
     * 查询Bpm 流程定义的拓展
     *
     * @param id Bpm 流程定义的拓展
     *           主键
     * @return Bpm 流程定义的拓展
     */
    @Override
    public BpmProcessDefinitionExt selectBpmProcessDefinitionExtById(Long id){
        return bpmProcessDefinitionExtMapper.selectBpmProcessDefinitionExtById(id);
    }

    /**
     * 查询Bpm 流程定义的拓展
     * 列表
     *
     * @param bpmProcessDefinitionExt Bpm 流程定义的拓展
     * @return Bpm 流程定义的拓展
     */
    @Override
    public List<BpmProcessDefinitionExt> selectBpmProcessDefinitionExtList(BpmProcessDefinitionExt bpmProcessDefinitionExt){
        return bpmProcessDefinitionExtMapper.selectBpmProcessDefinitionExtList(bpmProcessDefinitionExt);
    }

    /**
     * 新增Bpm 流程定义的拓展
     *
     * @param bpmProcessDefinitionExt Bpm 流程定义的拓展
     * @return 结果
     */
    @Override
    public int insertBpmProcessDefinitionExt(BpmProcessDefinitionExt bpmProcessDefinitionExt){
        bpmProcessDefinitionExt.setCreateTime(DateUtils.getNowDate());
        return bpmProcessDefinitionExtMapper.insertBpmProcessDefinitionExt(bpmProcessDefinitionExt);
    }

    /**
     * 修改Bpm 流程定义的拓展
     *
     * @param bpmProcessDefinitionExt Bpm 流程定义的拓展
     * @return 结果
     */
    @Override
    public int updateBpmProcessDefinitionExt(BpmProcessDefinitionExt bpmProcessDefinitionExt){
        bpmProcessDefinitionExt.setUpdateTime(DateUtils.getNowDate());
        return bpmProcessDefinitionExtMapper.updateBpmProcessDefinitionExt(bpmProcessDefinitionExt);
    }

    /**
     * 批量删除Bpm 流程定义的拓展
     *
     * @param ids 需要删除的Bpm 流程定义的拓展
     *            主键
     * @return 结果
     */
    @Override
    public int deleteBpmProcessDefinitionExtByIds(Long[] ids){
        return bpmProcessDefinitionExtMapper.deleteBpmProcessDefinitionExtByIds(ids);
    }

    /**
     * 删除Bpm 流程定义的拓展
     * 信息
     *
     * @param id Bpm 流程定义的拓展
     *           主键
     * @return 结果
     */
    @Override
    public int deleteBpmProcessDefinitionExtById(Long id){
        return bpmProcessDefinitionExtMapper.deleteBpmProcessDefinitionExtById(id);
    }
}
