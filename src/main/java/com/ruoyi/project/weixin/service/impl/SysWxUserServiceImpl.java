package com.ruoyi.project.weixin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.weixin.mapper.SysWxUserMapper;
import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;

/**
 * 微信用户Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Service
public class SysWxUserServiceImpl implements ISysWxUserService
{
    @Autowired
    private SysWxUserMapper sysWxUserMapper;

    /**
     * 查询微信用户
     *
     * @param id 微信用户主键
     * @return 微信用户
     */
    @Override
    public SysWxUser selectSysWxUserById(String id)
    {
        return sysWxUserMapper.selectSysWxUserById(id);
    }

    /**
     * 查询微信用户列表
     *
     * @param sysWxUser 微信用户
     * @return 微信用户
     */
    @Override
    public List<SysWxUser> selectSysWxUserList(SysWxUser sysWxUser)
    {
        return sysWxUserMapper.selectSysWxUserList(sysWxUser);
    }

    /**
     * 新增微信用户
     *
     * @param sysWxUser 微信用户
     * @return 结果
     */
    @Override
    public int insertSysWxUser(SysWxUser sysWxUser)
    {
        sysWxUser.setCreateTime(DateUtils.getNowDate());
        return sysWxUserMapper.insertSysWxUser(sysWxUser);
    }

    /**
     * 修改微信用户
     *
     * @param sysWxUser 微信用户
     * @return 结果
     */
    @Override
    public int updateSysWxUser(SysWxUser sysWxUser)
    {
        sysWxUser.setUpdateTime(DateUtils.getNowDate());
        return sysWxUserMapper.updateSysWxUser(sysWxUser);
    }

    /**
     * 批量删除微信用户
     *
     * @param ids 需要删除的微信用户主键
     * @return 结果
     */
    @Override
    public int deleteSysWxUserByIds(String[] ids)
    {
        return sysWxUserMapper.deleteSysWxUserByIds(ids);
    }

    /**
     * 删除微信用户信息
     *
     * @param id 微信用户主键
     * @return 结果
     */
    @Override
    public int deleteSysWxUserById(String id)
    {
        return sysWxUserMapper.deleteSysWxUserById(id);
    }

    /**
     * 根据openId查询微信用户
     *
     * @param openId openId
     * @return 微信用户
     */
    @Override
    public SysWxUser selectSysWxUserByOpenId(String openId) {
        return sysWxUserMapper.selectSysWxUserByOpenId(openId);
    }
    /**
     * 根据电话查询微信用户
     *
     * @param phone phone
     * @return 微信用户
     */
    @Override
    public SysWxUser selectSysWxUserByPhone(String phone) {
        return sysWxUserMapper.selectSysWxUserByPhone(phone);
    }
}
