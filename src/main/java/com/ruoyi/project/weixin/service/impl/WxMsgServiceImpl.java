package com.ruoyi.project.weixin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.weixin.domain.WxMsg;
import com.ruoyi.project.weixin.mapper.WxMsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.weixin.service.IWxMsgService;

/**
 * 微信消息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-20
 */
@Service
public class WxMsgServiceImpl implements IWxMsgService
{
    @Autowired
    private WxMsgMapper wxMsgMapper;

    /**
     * 查询微信消息
     *
     * @param id 微信消息主键
     * @return 微信消息
     */
    @Override
    public WxMsg selectWxMsgById(String id)
    {
        return wxMsgMapper.selectWxMsgById(id);
    }

    /**
     * 查询微信消息列表
     *
     * @param wxMsg 微信消息
     * @return 微信消息
     */
    @Override
    public List<WxMsg> selectWxMsgList(WxMsg wxMsg)
    {
        return wxMsgMapper.selectWxMsgList(wxMsg);
    }

    /**
     * 新增微信消息
     *
     * @param wxMsg 微信消息
     * @return 结果
     */
    @Override
    public int insertWxMsg(WxMsg wxMsg)
    {
        wxMsg.setCreateTime(DateUtils.getNowDate());
        return wxMsgMapper.insertWxMsg(wxMsg);
    }

    /**
     * 修改微信消息
     *
     * @param wxMsg 微信消息
     * @return 结果
     */
    @Override
    public int updateWxMsg(WxMsg wxMsg)
    {
        wxMsg.setUpdateTime(DateUtils.getNowDate());
        return wxMsgMapper.updateWxMsg(wxMsg);
    }

    /**
     * 批量删除微信消息
     *
     * @param ids 需要删除的微信消息主键
     * @return 结果
     */
    @Override
    public int deleteWxMsgByIds(String[] ids)
    {
        return wxMsgMapper.deleteWxMsgByIds(ids);
    }

    /**
     * 删除微信消息信息
     *
     * @param id 微信消息主键
     * @return 结果
     */
    @Override
    public int deleteWxMsgById(String id)
    {
        return wxMsgMapper.deleteWxMsgById(id);
    }
}
