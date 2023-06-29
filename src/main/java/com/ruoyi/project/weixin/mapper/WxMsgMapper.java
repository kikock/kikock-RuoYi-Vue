package com.ruoyi.project.weixin.mapper;

import java.util.List;
import com.ruoyi.project.weixin.domain.WxMsg;

/**
 * 微信消息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-20
 */
public interface WxMsgMapper 
{
    /**
     * 查询微信消息
     * 
     * @param id 微信消息主键
     * @return 微信消息
     */
    public WxMsg selectWxMsgById(String id);

    /**
     * 查询微信消息列表
     * 
     * @param wxMsg 微信消息
     * @return 微信消息集合
     */
    public List<WxMsg> selectWxMsgList(WxMsg wxMsg);

    /**
     * 新增微信消息
     * 
     * @param wxMsg 微信消息
     * @return 结果
     */
    public int insertWxMsg(WxMsg wxMsg);

    /**
     * 修改微信消息
     * 
     * @param wxMsg 微信消息
     * @return 结果
     */
    public int updateWxMsg(WxMsg wxMsg);

    /**
     * 删除微信消息
     * 
     * @param id 微信消息主键
     * @return 结果
     */
    public int deleteWxMsgById(String id);

    /**
     * 批量删除微信消息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxMsgByIds(String[] ids);
}
