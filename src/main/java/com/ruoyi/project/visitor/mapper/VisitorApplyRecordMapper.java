package com.ruoyi.project.visitor.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.project.miniapp.controller.vo.VisitorApplyVo;
import com.ruoyi.project.miniapp.controller.vo.WxDeptVo;
import com.ruoyi.project.miniapp.controller.vo.WxUserInfoVo;
import com.ruoyi.project.visitor.domain.VisitorApplyRecord;

/**
 * 访客申请记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-09-19
 */
public interface VisitorApplyRecordMapper
{
    /**
     * 查询访客申请记录
     *
     * @param id 访客申请记录主键
     * @return 访客申请记录
     */
    public VisitorApplyRecord selectVisitorApplyRecordById(String id);

    /**
     * 查询访客申请记录列表
     *
     * @param visitorApplyRecord 访客申请记录
     * @return 访客申请记录集合
     */
    public List<VisitorApplyRecord> selectVisitorApplyRecordList(VisitorApplyRecord visitorApplyRecord);

    /**
     * 新增访客申请记录
     *
     * @param visitorApplyRecord 访客申请记录
     * @return 结果
     */
    public int insertVisitorApplyRecord(VisitorApplyRecord visitorApplyRecord);

    /**
     * 修改访客申请记录
     *
     * @param visitorApplyRecord 访客申请记录
     * @return 结果
     */
    public int updateVisitorApplyRecord(VisitorApplyRecord visitorApplyRecord);

    /**
     * 删除访客申请记录
     *
     * @param id 访客申请记录主键
     * @return 结果
     */
    public int deleteVisitorApplyRecordById(String id);

    /**
     * 批量删除访客申请记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisitorApplyRecordByIds(String[] ids);

    /**
     *  根据部门id 获取所有下级部门
     *
     * @param deptId 部门id
     * @return 部门信息集合
     */
    public List<WxDeptVo> findDeptList(Long deptId);
    /**
     *  根据部门id 获取部门下用户
     *
     * @param deptId 部门id
     * @return 部门信息集合
     */
    public List<WxUserInfoVo> findUserList(Long deptId);

    /**
     *  获取微信用户
     *
     * @param userId 用户id
     * @return 微信信息集合
     */
    public WxUserInfoVo findWxInfoByUserId(Long userId);

    /**
     *  获取微信用户
     *
     * @param wxId 用户id
     * @return 微信信息集合
     */
    public WxUserInfoVo findWxInfoByWxId(String wxId);
    /**
     *  访客申请单保存
     *
     * @param vo 申请单数据
     * @return
     */
    public int saveRegisterInfo(VisitorApplyVo vo);

    /**
     *  访客申请单保存更新所有订单姓名
     * @param name 姓名
     * @param wxUserId 微信id
     *
     * @return
     */
    public int batchUpdateName(String name,String wxUserId);

    /**
     * 查询访客申请记录列表
     *
     * @param wxUserId wx用户id
     * @param visitTime 申请时间
     * @param applyState 申请状态
     * @return 访客申请记录集合
     */
    public List<VisitorApplyRecord> getMakingListByWxUserId(String wxUserId, String visitTime, String applyState,
                                                            String guardId,String reviewedById,List<String> ids);

}
