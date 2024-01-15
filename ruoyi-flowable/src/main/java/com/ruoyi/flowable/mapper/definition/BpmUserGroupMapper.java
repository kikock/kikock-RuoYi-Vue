package com.ruoyi.flowable.mapper.definition;


import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.flowable.domain.definition.BpmUserGroup;

import java.util.List;

/**
 * 用户组Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface BpmUserGroupMapper{
    /**
     * 查询用户组
     *
     * @param id 用户组主键
     * @return 用户组
     */
    public BpmUserGroup selectBpmUserGroupById(Long id);

    /**
     * 查询用户组列表
     *
     * @param bpmUserGroup 用户组
     * @return 用户组集合
     */
    public List<BpmUserGroup> selectBpmUserGroupList(BpmUserGroup bpmUserGroup);

    /**
     * 新增用户组
     *
     * @param bpmUserGroup 用户组
     * @return 结果
     */
    public int insertBpmUserGroup(BpmUserGroup bpmUserGroup);

    /**
     * 修改用户组
     *
     * @param bpmUserGroup 用户组
     * @return 结果
     */
    public int updateBpmUserGroup(BpmUserGroup bpmUserGroup);

    /**
     * 删除用户组
     *
     * @param id 用户组主键
     * @return 结果
     */
    public int deleteBpmUserGroupById(Long id);

    /**
     * 批量删除用户组
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBpmUserGroupByIds(Long[] ids);


    public List<SelectMoreVo> getSimpleList(String keywords);

    public List<BpmUserGroup> selectBatchIds(List<Long> ids);
}
