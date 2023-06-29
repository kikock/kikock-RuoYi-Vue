package com.ruoyi.project.visitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.visitor.mapper.VisitorApplyRecordMapper;
import com.ruoyi.project.visitor.domain.VisitorApplyRecord;
import com.ruoyi.project.visitor.service.IVisitorApplyRecordService;

/**
 * 访客申请记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
@Service
public class VisitorApplyRecordServiceImpl implements IVisitorApplyRecordService 
{
    @Autowired
    private VisitorApplyRecordMapper visitorApplyRecordMapper;

    /**
     * 查询访客申请记录
     * 
     * @param id 访客申请记录主键
     * @return 访客申请记录
     */
    @Override
    public VisitorApplyRecord selectVisitorApplyRecordById(String id)
    {
        return visitorApplyRecordMapper.selectVisitorApplyRecordById(id);
    }

    /**
     * 查询访客申请记录列表
     * 
     * @param visitorApplyRecord 访客申请记录
     * @return 访客申请记录
     */
    @Override
    public List<VisitorApplyRecord> selectVisitorApplyRecordList(VisitorApplyRecord visitorApplyRecord)
    {
        return visitorApplyRecordMapper.selectVisitorApplyRecordList(visitorApplyRecord);
    }

    /**
     * 新增访客申请记录
     * 
     * @param visitorApplyRecord 访客申请记录
     * @return 结果
     */
    @Override
    public int insertVisitorApplyRecord(VisitorApplyRecord visitorApplyRecord)
    {
        visitorApplyRecord.setCreateTime(DateUtils.getNowDate());
        return visitorApplyRecordMapper.insertVisitorApplyRecord(visitorApplyRecord);
    }

    /**
     * 修改访客申请记录
     * 
     * @param visitorApplyRecord 访客申请记录
     * @return 结果
     */
    @Override
    public int updateVisitorApplyRecord(VisitorApplyRecord visitorApplyRecord)
    {
        return visitorApplyRecordMapper.updateVisitorApplyRecord(visitorApplyRecord);
    }

    /**
     * 批量删除访客申请记录
     * 
     * @param ids 需要删除的访客申请记录主键
     * @return 结果
     */
    @Override
    public int deleteVisitorApplyRecordByIds(String[] ids)
    {
        return visitorApplyRecordMapper.deleteVisitorApplyRecordByIds(ids);
    }

    /**
     * 删除访客申请记录信息
     * 
     * @param id 访客申请记录主键
     * @return 结果
     */
    @Override
    public int deleteVisitorApplyRecordById(String id)
    {
        return visitorApplyRecordMapper.deleteVisitorApplyRecordById(id);
    }
}
