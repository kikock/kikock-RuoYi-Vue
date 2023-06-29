package com.ruoyi.project.weixin.mapper;

import java.util.List;
import com.ruoyi.project.weixin.domain.SysWxUser;

/**
 * 微信用户Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public interface SysWxUserMapper
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
     * 删除微信用户
     *
     * @param id 微信用户主键
     * @return 结果
     */
    public int deleteSysWxUserById(String id);

    /**
     * 批量删除微信用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysWxUserByIds(String[] ids);

    /**
     * 根据openId查询微信用户
     *
     * @param openId openId
     * @return 微信用户
     */
    SysWxUser selectSysWxUserByOpenId(String openId);


    /**
     * 根据phone查询微信用户
     *
     * @param phone phone
     * @return 微信用户
     */
    SysWxUser selectSysWxUserByPhone(String phone);

}
