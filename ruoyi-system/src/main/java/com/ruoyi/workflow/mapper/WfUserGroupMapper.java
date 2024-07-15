package com.ruoyi.workflow.mapper;


import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.workflow.domain.WfUserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户组Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface WfUserGroupMapper{
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
     * 删除用户组
     *
     * @param id 用户组主键
     * @return 结果
     */
    public int deleteWfUserGroupById(Long id);

    /**
     * 批量删除用户组
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfUserGroupByIds(Long[] ids);


    public List<SelectMoreVo> getSimpleList(String keywords);

    public List<WfUserGroup> selectBatchIds(List<Long> ids);

    public int addSysUserGroup(@Param("groupId") Long groupId, @Param("list")List<String> list);

    public  int delSysUserGroup(Long id);


    public  List<String> getGroupIds(@Param("userId") String userId);

    public  List<String> getUserIdsByGroupId(@Param("groupId") String groupId);
}
