package com.ruoyi.flowable.service.leave.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.leave.BpmOaLeave;
import com.ruoyi.flowable.mapper.leave.BpmOaLeaveMapper;
import com.ruoyi.flowable.service.leave.IBpmOaLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OA 请假申请Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmOaLeaveServiceImpl implements IBpmOaLeaveService{
    @Autowired
    private BpmOaLeaveMapper bpmOaLeaveMapper;

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
    public int insertBpmOaLeave(BpmOaLeave bpmOaLeave){
        bpmOaLeave.setCreateTime(DateUtils.getNowDate());
        return bpmOaLeaveMapper.insertBpmOaLeave(bpmOaLeave);
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
