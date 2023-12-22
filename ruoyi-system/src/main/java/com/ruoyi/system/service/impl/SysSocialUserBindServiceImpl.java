package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysSocialUserBind;
import com.ruoyi.system.domain.vo.SocialUserVo;
import com.ruoyi.system.mapper.SysSocialUserBindMapper;
import com.ruoyi.system.service.ISysSocialUserBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 三方用户Service业务层处理
 *
 * @author kikock
 * @date 2023-12-04
 */
@Service
public class SysSocialUserBindServiceImpl implements ISysSocialUserBindService{
    @Autowired
    private SysSocialUserBindMapper sysSocialUserBindMapper;

    /**
     * 查询三方用户
     *
     * @param id 三方用户主键
     * @return 三方用户
     */
    @Override
    public SysSocialUserBind selectSysSocialUserBindById(Long id){
        return sysSocialUserBindMapper.selectSysSocialUserBindById(id);
    }

    /**
     * 查询三方用户列表
     *
     * @param sysSocialUserBind 三方用户
     * @return 三方用户
     */
    @Override
    public List<SysSocialUserBind> selectSysSocialUserBindList(SysSocialUserBind sysSocialUserBind){
        return sysSocialUserBindMapper.selectSysSocialUserBindList(sysSocialUserBind);
    }

    /**
     * 新增三方用户
     *
     * @param sysSocialUserBind 三方用户
     * @return 结果
     */
    @Override
    public int insertSysSocialUserBind(SysSocialUserBind sysSocialUserBind){
        sysSocialUserBind.setCreateTime(DateUtils.getNowDate());
        return sysSocialUserBindMapper.insertSysSocialUserBind(sysSocialUserBind);
    }

    /**
     * 修改三方用户
     *
     * @param sysSocialUserBind 三方用户
     * @return 结果
     */
    @Override
    public int updateSysSocialUserBind(SysSocialUserBind sysSocialUserBind){
        sysSocialUserBind.setUpdateTime(DateUtils.getNowDate());
        return sysSocialUserBindMapper.updateSysSocialUserBind(sysSocialUserBind);
    }

    /**
     * 批量删除三方用户
     *
     * @param ids 需要删除的三方用户主键
     * @return 结果
     */
    @Override
    public int deleteSysSocialUserBindByIds(Long[] ids){
        return sysSocialUserBindMapper.deleteSysSocialUserBindByIds(ids);
    }

    /**
     * 删除三方用户信息
     *
     * @param id 三方用户主键
     * @return 结果
     */
    @Override
    public int deleteSysSocialUserBindById(Long id){
        return sysSocialUserBindMapper.deleteSysSocialUserBindById(id);
    }
    /**
     * 第三方平台用户唯一id获取用户信息
     *
     * @param uuid 第三方平台用户唯一id
     * @return 结果
     */
    @Override
    public SysUser selectAuthUserByUuid(String uuid){
        return sysSocialUserBindMapper.selectAuthUserByUuid(uuid);
    }
    /**
     * 用户第三方绑定状态
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public List<SocialUserVo> findSocialUserBindByUserId(Long userId){
        return sysSocialUserBindMapper.findSocialUserBindByUserId(userId);
    }
    /**
     * 第三方平台用户唯一解除绑定
     *
     * @param authId 三方唯一uuid
     * @return 结果
     */
    @Override
    public AjaxResult deleteAuthUser(String authId){
        int i = sysSocialUserBindMapper.deleteAuthUser(authId);
            if(i>0){
                return AjaxResult.success("解除绑定成功");
            }
        return AjaxResult.success("解除绑定失败");
    }
}
