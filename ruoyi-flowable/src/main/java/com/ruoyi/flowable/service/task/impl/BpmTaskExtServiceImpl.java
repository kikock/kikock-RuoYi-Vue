package com.ruoyi.flowable.service.task.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.task.BpmTaskExt;
import com.ruoyi.flowable.mapper.task.BpmTaskExtMapper;
import com.ruoyi.flowable.service.task.IBpmTaskExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作流的流程任务的拓展Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmTaskExtServiceImpl implements IBpmTaskExtService{
    @Autowired
    private BpmTaskExtMapper bpmTaskExtMapper;

    /**
     * 查询工作流的流程任务的拓展
     *
     * @param id 工作流的流程任务的拓展主键
     * @return 工作流的流程任务的拓展
     */
    @Override
    public BpmTaskExt selectBpmTaskExtById(Long id){
        return bpmTaskExtMapper.selectBpmTaskExtById(id);
    }

    /**
     * 查询工作流的流程任务的拓展列表
     *
     * @param bpmTaskExt 工作流的流程任务的拓展
     * @return 工作流的流程任务的拓展
     */
    @Override
    public List<BpmTaskExt> selectBpmTaskExtList(BpmTaskExt bpmTaskExt){
        return bpmTaskExtMapper.selectBpmTaskExtList(bpmTaskExt);
    }

    /**
     * 新增工作流的流程任务的拓展
     *
     * @param bpmTaskExt 工作流的流程任务的拓展
     * @return 结果
     */
    @Override
    public int insertBpmTaskExt(BpmTaskExt bpmTaskExt){
        bpmTaskExt.setCreateTime(DateUtils.getNowDate());
        return bpmTaskExtMapper.insertBpmTaskExt(bpmTaskExt);
    }

    /**
     * 修改工作流的流程任务的拓展
     *
     * @param bpmTaskExt 工作流的流程任务的拓展
     * @return 结果
     */
    @Override
    public int updateBpmTaskExt(BpmTaskExt bpmTaskExt){
        bpmTaskExt.setUpdateTime(DateUtils.getNowDate());
        return bpmTaskExtMapper.updateBpmTaskExt(bpmTaskExt);
    }

    /**
     * 批量删除工作流的流程任务的拓展
     *
     * @param ids 需要删除的工作流的流程任务的拓展主键
     * @return 结果
     */
    @Override
    public int deleteBpmTaskExtByIds(Long[] ids){
        return bpmTaskExtMapper.deleteBpmTaskExtByIds(ids);
    }

    /**
     * 删除工作流的流程任务的拓展信息
     *
     * @param id 工作流的流程任务的拓展主键
     * @return 结果
     */
    @Override
    public int deleteBpmTaskExtById(Long id){
        return bpmTaskExtMapper.deleteBpmTaskExtById(id);
    }
}
