package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysSocialUserBind;
import com.ruoyi.system.domain.vo.SocialUserVo;

import java.util.List;

/**
 * 三方用户Mapper接口
 *
 * @author kikock
 * @date 2023-12-04
 */
public interface SysSocialUserBindMapper{
    /**
     * 查询三方用户
     *
     * @param id 三方用户主键
     * @return 三方用户
     */
    public SysSocialUserBind selectSysSocialUserBindById(Long id);

    /**
     * 查询三方用户列表
     *
     * @param sysSocialUserBind 三方用户
     * @return 三方用户集合
     */
    public List<SysSocialUserBind> selectSysSocialUserBindList(SysSocialUserBind sysSocialUserBind);

    /**
     * 新增三方用户
     *
     * @param sysSocialUserBind 三方用户
     * @return 结果
     */
    public int insertSysSocialUserBind(SysSocialUserBind sysSocialUserBind);

    /**
     * 修改三方用户
     *
     * @param sysSocialUserBind 三方用户
     * @return 结果
     */
    public int updateSysSocialUserBind(SysSocialUserBind sysSocialUserBind);

    /**
     * 删除三方用户
     *
     * @param id 三方用户主键
     * @return 结果
     */
    public int deleteSysSocialUserBindById(Long id);

    /**
     * 批量删除三方用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysSocialUserBindByIds(Long[] ids);

    /**
     * 查询绑定用户
     *
     * @param uuid 第三方平台用户唯一id
     * @return 结果
     */
    public SysUser selectAuthUserByUuid(String uuid);
    /**
     * 用户第三方绑定状态
     *
     * @param userId 用户id
     * @return 结果
     */
    public List<SocialUserVo> findSocialUserBindByUserId(Long userId);
    /**
     * 第三方平台用户唯一解除绑定
     *
     * @param authId 三方唯一uuid
     * @return 结果
     */
    public int deleteAuthUser(String authId);
}
