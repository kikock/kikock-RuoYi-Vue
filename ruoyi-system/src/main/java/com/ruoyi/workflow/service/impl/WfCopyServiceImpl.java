package com.ruoyi.workflow.service.impl;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.workflow.domain.WfCopy;
import com.ruoyi.workflow.domain.bo.WfTaskBo;
import com.ruoyi.workflow.mapper.WfCopyMapper;
import com.ruoyi.workflow.service.IWfCopyService;
import jakarta.annotation.Resource;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 流程抄送Service业务层处理
 *
 * @author kikock
 * @date 2024-02-02
 */
@Service
public class WfCopyServiceImpl implements IWfCopyService{
    @Autowired
    private WfCopyMapper wfCopyMapper;
    @Resource
    private  HistoryService historyService;


    /**
     * 查询流程抄送
     *
     * @param copyId 流程抄送主键
     * @return 流程抄送
     */
    @Override
    public WfCopy selectWfCopyByCopyId(Long copyId){
        return wfCopyMapper.selectWfCopyByCopyId(copyId);
    }

    /**
     * 查询流程抄送列表
     *
     * @param wfCopy 流程抄送
     * @return 流程抄送
     */
    @Override
    public List<WfCopy> selectWfCopyList(WfCopy wfCopy){
        return wfCopyMapper.selectWfCopyList(wfCopy);
    }

    /**
     * 新增流程抄送
     *
     * @param wfCopy 流程抄送
     * @return 结果
     */
    @Override
    public int insertWfCopy(WfCopy wfCopy){
        wfCopy.setCreateTime(DateUtils.getNowDate());
        return wfCopyMapper.insertWfCopy(wfCopy);
    }

    /**
     * 修改流程抄送
     *
     * @param wfCopy 流程抄送
     * @return 结果
     */
    @Override
    public int updateWfCopy(WfCopy wfCopy){
        wfCopy.setUpdateTime(DateUtils.getNowDate());
        return wfCopyMapper.updateWfCopy(wfCopy);
    }

    /**
     * 批量删除流程抄送
     *
     * @param copyIds 需要删除的流程抄送主键
     * @return 结果
     */
    @Override
    public int deleteWfCopyByCopyIds(Long[] copyIds){
        return wfCopyMapper.deleteWfCopyByCopyIds(copyIds);
    }

    /**
     * 删除流程抄送信息
     *
     * @param copyId 流程抄送主键
     * @return 结果
     */
    @Override
    public int deleteWfCopyByCopyId(Long copyId){
        return wfCopyMapper.deleteWfCopyByCopyId(copyId);
    }
    /**
     * 抄送
     *
     * @param taskBo
     * @return
     */
    @Override
    public AjaxResult makeCopy(WfTaskBo taskBo){
        if (StringUtils.isBlank(taskBo.getCopyUserIds())) {
            // 若抄送用户为空，则不需要处理，返回成功
            return AjaxResult.success("无抄送用户,直接返回成功");
        }
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(taskBo.getProcInsId()).singleResult();
        String[] ids = taskBo.getCopyUserIds().split(",");
        List<WfCopy> copyList = new ArrayList<>(ids.length);
        Long originatorId = SecurityUtils.getUserId();
        String originatorName = SecurityUtils.getUsername();
        String title = historicProcessInstance.getProcessDefinitionName() + "-" + taskBo.getTaskName();
        for (String id : ids) {
            Long userId = Long.valueOf(id);
            WfCopy copy = new WfCopy();
            copy.setTitle(title);
            copy.setProcessId(historicProcessInstance.getProcessDefinitionId());
            copy.setProcessName(historicProcessInstance.getProcessDefinitionName());
            copy.setDeploymentId(historicProcessInstance.getDeploymentId());
            copy.setInstanceId(taskBo.getProcInsId());
            copy.setTaskId(taskBo.getTaskId());
            copy.setUserId(userId);
            copy.setOriginatorId(originatorId);
            copy.setOriginatorName(originatorName);
            copy.setCreateBy(originatorName);
            copy.setCreateTime(DateUtils.getNowDate());
            copyList.add(copy);
        }
        return AjaxResult.success(wfCopyMapper.insertBatchWfCopy(copyList));
    }
}
