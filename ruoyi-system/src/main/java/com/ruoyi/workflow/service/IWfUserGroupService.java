package com.ruoyi.workflow.service;


import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.workflow.domain.WfUserGroup;

import java.util.List;

/**
 * 用户组Service接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface IWfUserGroupService{
    /**
     * 查询用户组
     *
     * @param id 用户组主键
     * @return 用户组
     */
    public WfUserGroup selectWfUserGroupById(Long id);

    /**
     * 查询用户组列表
     *
     * @param WfUserGroup 用户组
     * @return 用户组集合
     */
    public List<WfUserGroup> selectWfUserGroupList(WfUserGroup WfUserGroup);

    /**
     * 新增用户组
     *
     * @param WfUserGroup 用户组
     * @return 结果
     */
    public int insertWfUserGroup(WfUserGroup WfUserGroup);

    /**
     * 修改用户组
     *
     * @param WfUserGroup 用户组
     * @return 结果
     */
    public int updateWfUserGroup(WfUserGroup WfUserGroup);

    /**
     * 批量删除用户组
     *
     * @param ids 需要删除的用户组主键集合
     * @return 结果
     */
    public int deleteWfUserGroupByIds(Long[] ids);

    /**
     * 删除用户组信息
     *
     * @param id 用户组主键
     * @return 结果
     */
    public int deleteWfUserGroupById(Long id);

    public List<SelectMoreVo> getSimpleList(String keywords);

    public List<WfUserGroup> selectBatchIds(List<Long> ids);

}
