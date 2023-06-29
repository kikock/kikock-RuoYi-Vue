package com.ruoyi.project.weixin.service;

import java.util.List;
import com.ruoyi.project.weixin.domain.SysWxUser;

/**
 * 微信用户Service接口
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public interface ISysWxUserService
{
    /**
     * 查询微信用户
     *
     * @param id 微信用户主键
     * @return 微信用户
     */
    public SysWxUser selectSysWxUserById(String id);

    /**
     * 查询微信用户列表
     *
     * @param sysWxUser 微信用户
     * @return 微信用户集合
     */
    public List<SysWxUser> selectSysWxUserList(SysWxUser sysWxUser);

    /**
     * 新增微信用户
     *
     * @param sysWxUser 微信用户
     * @return 结果
     */
    public int insertSysWxUser(SysWxUser sysWxUser);

    /**
     * 修改微信用户
     *
     * @param sysWxUser 微信用户
     * @return 结果
     */
    public int updateSysWxUser(SysWxUser sysWxUser);

    /**
     * 批量删除微信用户
     *
     * @param ids 需要删除的微信用户主键集合
     * @return 结果
     */
    public int deleteSysWxUserByIds(String[] ids);

    /**
     * 删除微信用户信息
     *
     * @param id 微信用户主键
     * @return 结果
     */
    public int deleteSysWxUserById(String id);

    /**
     * 根据openId查询微信用户
     *
     * @param openId openId
     * @return 微信用户
     */
    SysWxUser selectSysWxUserByOpenId(String openId);

    /**
     * 根据手机号码查询微信用户
     *
     * @param phone phone
     * @return 微信用户
     */
    SysWxUser selectSysWxUserByPhone(String phone);
}
