package com.ruoyi.flowable.service.leave.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.leave.BpmOaLeave;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.mapper.leave.BpmOaLeaveMapper;
import com.ruoyi.flowable.service.leave.IBpmOaLeaveService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OA 请假申请Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmOaLeaveServiceImpl implements IBpmOaLeaveService{
    @Resource
    private BpmOaLeaveMapper bpmOaLeaveMapper;
    @Resource
    @Lazy // 解决循环依赖
    private IBpmProcessInstanceService processInstanceService;

    /**
     * 查询OA 请假申请
     *
     * @param id OA 请假申请主键
     * @return OA 请假申请
     */
    @Override
    public BpmOaLeave selectBpmOaLeaveById(Long id){
        return bpmOaLeaveMapper.selectBpmOaLeaveById(id);
    }

    /**
     * 查询OA 请假申请列表
     *
     * @param bpmOaLeave OA 请假申请
     * @return OA 请假申请
     */
    @Override
    public List<BpmOaLeave> selectBpmOaLeaveList(BpmOaLeave bpmOaLeave){
        return bpmOaLeaveMapper.selectBpmOaLeaveList(bpmOaLeave);
    }

    /**
     * 新增OA 请假申请
     *
     * @param bpmOaLeave OA 请假申请
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBpmOaLeave(BpmOaLeave bpmOaLeave){
        // 插入 OA 请假单
        //请假天数
        bpmOaLeave.setDay(DateUtils.differentDaysByMillisecond(bpmOaLeave.getEndTime(), bpmOaLeave.getStartTime()));
        //0 申请中 1 通过 2不通过
        bpmOaLeave.setResult(0);
        bpmOaLeave.setCreateTime(DateUtils.getNowDate());
        bpmOaLeave.setProcess_key("leave001");
        int i = bpmOaLeaveMapper.insertBpmOaLeave(bpmOaLeave);
        if (i > 0) {
            // 发起 BPM 流程
            Map<String,Object> processInstanceVariables = new HashMap<>();
            processInstanceVariables.put("day", bpmOaLeave.getDay());
            BpmTaskReqVO bpmTaskReqVO = new BpmTaskReqVO();
            bpmTaskReqVO.setBusinessKey(String.valueOf(bpmOaLeave.getId()));
            bpmTaskReqVO.setVariables(processInstanceVariables);
            bpmTaskReqVO.setProcessDefinitionKey(bpmOaLeave.getProcess_key());
            String processInstanceId = processInstanceService.createProcessInstanceByProcessDefinitionKey(bpmOaLeave.getUserId(), bpmTaskReqVO);
        }
        // 将工作流的编号，更新到 OA 请假单中
//        bpmOaLeaveMapper.updateById();
        return 1;
    }

    /**
     * 修改OA 请假申请
     *
     * @param bpmOaLeave OA 请假申请
     * @return 结果
     */
    @Override
    public int updateBpmOaLeave(BpmOaLeave bpmOaLeave){
        bpmOaLeave.setUpdateTime(DateUtils.getNowDate());
        return bpmOaLeaveMapper.updateBpmOaLeave(bpmOaLeave);
    }

    /**
     * 批量删除OA 请假申请
     *
     * @param ids 需要删除的OA 请假申请主键
     * @return 结果
     */
    @Override
    public int deleteBpmOaLeaveByIds(Long[] ids){
        return bpmOaLeaveMapper.deleteBpmOaLeaveByIds(ids);
    }

    /**
     * 删除OA 请假申请信息
     *
     * @param id OA 请假申请主键
     * @return 结果
     */
    @Override
    public int deleteBpmOaLeaveById(Long id){
        return bpmOaLeaveMapper.deleteBpmOaLeaveById(id);
    }
}
