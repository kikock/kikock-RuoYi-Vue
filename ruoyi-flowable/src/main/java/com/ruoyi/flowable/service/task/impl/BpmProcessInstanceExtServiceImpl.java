package com.ruoyi.flowable.service.task.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.task.BpmProcessInstanceExt;
import com.ruoyi.flowable.mapper.task.BpmProcessInstanceExtMapper;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作流的流程实例的拓展Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmProcessInstanceExtServiceImpl implements IBpmProcessInstanceExtService{
    @Autowired
    private BpmProcessInstanceExtMapper bpmProcessInstanceExtMapper;

    /**
     * 查询工作流的流程实例的拓展
     *
     * @param id 工作流的流程实例的拓展主键
     * @return 工作流的流程实例的拓展
     */
    @Override
    public BpmProcessInstanceExt selectBpmProcessInstanceExtById(Long id){
        return bpmProcessInstanceExtMapper.selectBpmProcessInstanceExtById(id);
    }

    /**
     * 查询工作流的流程实例的拓展列表
     *
     * @param bpmProcessInstanceExt 工作流的流程实例的拓展
     * @return 工作流的流程实例的拓展
     */
    @Override
    public List<BpmProcessInstanceExt> selectBpmProcessInstanceExtList(BpmProcessInstanceExt bpmProcessInstanceExt){
        return bpmProcessInstanceExtMapper.selectBpmProcessInstanceExtList(bpmProcessInstanceExt);
    }

    /**
     * 新增工作流的流程实例的拓展
     *
     * @param bpmProcessInstanceExt 工作流的流程实例的拓展
     * @return 结果
     */
    @Override
    public int insertBpmProcessInstanceExt(BpmProcessInstanceExt bpmProcessInstanceExt){
        bpmProcessInstanceExt.setCreateTime(DateUtils.getNowDate());
        return bpmProcessInstanceExtMapper.insertBpmProcessInstanceExt(bpmProcessInstanceExt);
    }

    /**
     * 修改工作流的流程实例的拓展
     *
     * @param bpmProcessInstanceExt 工作流的流程实例的拓展
     * @return 结果
     */
    @Override
    public int updateBpmProcessInstanceExt(BpmProcessInstanceExt bpmProcessInstanceExt){
        bpmProcessInstanceExt.setUpdateTime(DateUtils.getNowDate());
        return bpmProcessInstanceExtMapper.updateBpmProcessInstanceExt(bpmProcessInstanceExt);
    }

    /**
     * 批量删除工作流的流程实例的拓展
     *
     * @param ids 需要删除的工作流的流程实例的拓展主键
     * @return 结果
     */
    @Override
    public int deleteBpmProcessInstanceExtByIds(Long[] ids){
        return bpmProcessInstanceExtMapper.deleteBpmProcessInstanceExtByIds(ids);
    }

    /**
     * 删除工作流的流程实例的拓展信息
     *
     * @param id 工作流的流程实例的拓展主键
     * @return 结果
     */
    @Override
    public int deleteBpmProcessInstanceExtById(Long id){
        return bpmProcessInstanceExtMapper.deleteBpmProcessInstanceExtById(id);
    }
}
