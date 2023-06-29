package com.ruoyi.project.visitor.service;

import java.util.List;
import com.ruoyi.project.visitor.domain.VisitorApplyRecord;

/**
 * 访客申请记录Service接口
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
public interface IVisitorApplyRecordService 
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
     * 批量删除访客申请记录
     * 
     * @param ids 需要删除的访客申请记录主键集合
     * @return 结果
     */
    public int deleteVisitorApplyRecordByIds(String[] ids);

    /**
     * 删除访客申请记录信息
     * 
     * @param id 访客申请记录主键
     * @return 结果
     */
    public int deleteVisitorApplyRecordById(String id);
}
