package com.ruoyi.flowable.mapper.leave;


import com.ruoyi.flowable.domain.leave.BpmOaLeave;

import java.util.List;

/**
 * OA 请假申请Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface BpmOaLeaveMapper{
    /**
     * 查询OA 请假申请
     *
     * @param id OA 请假申请主键
     * @return OA 请假申请
     */
    public BpmOaLeave selectBpmOaLeaveById(Long id);

    /**
     * 查询OA 请假申请列表
     *
     * @param bpmOaLeave OA 请假申请
     * @return OA 请假申请集合
     */
    public List<BpmOaLeave> selectBpmOaLeaveList(BpmOaLeave bpmOaLeave);

    /**
     * 新增OA 请假申请
     *
     * @param bpmOaLeave OA 请假申请
     * @return 结果
     */
    public int insertBpmOaLeave(BpmOaLeave bpmOaLeave);

    /**
     * 修改OA 请假申请
     *
     * @param bpmOaLeave OA 请假申请
     * @return 结果
     */
    public int updateBpmOaLeave(BpmOaLeave bpmOaLeave);

    /**
     * 删除OA 请假申请
     *
     * @param id OA 请假申请主键
     * @return 结果
     */
    public int deleteBpmOaLeaveById(Long id);

    /**
     * 批量删除OA 请假申请
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBpmOaLeaveByIds(Long[] ids);
}
