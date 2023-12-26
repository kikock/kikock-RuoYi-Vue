package com.ruoyi.flowable.service.definition.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.definition.BpmUserGroup;
import com.ruoyi.flowable.mapper.definition.BpmUserGroupMapper;
import com.ruoyi.flowable.service.definition.IBpmUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户组Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmUserGroupServiceImpl implements IBpmUserGroupService{
    @Autowired
    private BpmUserGroupMapper bpmUserGroupMapper;

    /**
     * 查询用户组
     *
     * @param id 用户组主键
     * @return 用户组
     */
    @Override
    public BpmUserGroup selectBpmUserGroupById(Long id){
        return bpmUserGroupMapper.selectBpmUserGroupById(id);
    }

    /**
     * 查询用户组列表
     *
     * @param bpmUserGroup 用户组
     * @return 用户组
     */
    @Override
    public List<BpmUserGroup> selectBpmUserGroupList(BpmUserGroup bpmUserGroup){
        return bpmUserGroupMapper.selectBpmUserGroupList(bpmUserGroup);
    }

    /**
     * 新增用户组
     *
     * @param bpmUserGroup 用户组
     * @return 结果
     */
    @Override
    public int insertBpmUserGroup(BpmUserGroup bpmUserGroup){
        bpmUserGroup.setCreateTime(DateUtils.getNowDate());
        return bpmUserGroupMapper.insertBpmUserGroup(bpmUserGroup);
    }

    /**
     * 修改用户组
     *
     * @param bpmUserGroup 用户组
     * @return 结果
     */
    @Override
    public int updateBpmUserGroup(BpmUserGroup bpmUserGroup){
        bpmUserGroup.setUpdateTime(DateUtils.getNowDate());
        return bpmUserGroupMapper.updateBpmUserGroup(bpmUserGroup);
    }

    /**
     * 批量删除用户组
     *
     * @param ids 需要删除的用户组主键
     * @return 结果
     */
    @Override
    public int deleteBpmUserGroupByIds(Long[] ids){
        return bpmUserGroupMapper.deleteBpmUserGroupByIds(ids);
    }

    /**
     * 删除用户组信息
     *
     * @param id 用户组主键
     * @return 结果
     */
    @Override
    public int deleteBpmUserGroupById(Long id){
        return bpmUserGroupMapper.deleteBpmUserGroupById(id);
    }
}
