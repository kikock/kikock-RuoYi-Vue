package com.ruoyi.workflow.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.workflow.domain.WfUserGroup;
import com.ruoyi.workflow.mapper.WfUserGroupMapper;
import com.ruoyi.workflow.service.IWfUserGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户组Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class WfUserGroupServiceImpl implements IWfUserGroupService{
    @Resource
    private WfUserGroupMapper wfUserGroupMapper;

    /**
     * 查询用户组
     *
     * @param id 用户组主键
     * @return 用户组
     */
    @Override
    public WfUserGroup selectWfUserGroupById(Long id){
        return wfUserGroupMapper.selectWfUserGroupById(id);
    }

    /**
     * 查询用户组列表
     *
     * @param WfUserGroup 用户组
     * @return 用户组
     */
    @Override
    public List<WfUserGroup> selectWfUserGroupList(WfUserGroup WfUserGroup){
        return wfUserGroupMapper.selectWfUserGroupList(WfUserGroup);
    }

    /**
     * 新增用户组
     *
     * @param WfUserGroup 用户组
     * @return 结果
     */
    @Override
    public int insertWfUserGroup(WfUserGroup WfUserGroup){
        WfUserGroup.setCreateTime(DateUtils.getNowDate());
        return wfUserGroupMapper.insertWfUserGroup(WfUserGroup);
    }

    /**
     * 修改用户组
     *
     * @param WfUserGroup 用户组
     * @return 结果
     */
    @Override
    public int updateWfUserGroup(WfUserGroup WfUserGroup){
        WfUserGroup.setUpdateTime(DateUtils.getNowDate());
        return wfUserGroupMapper.updateWfUserGroup(WfUserGroup);
    }

    /**
     * 批量删除用户组
     *
     * @param ids 需要删除的用户组主键
     * @return 结果
     */
    @Override
    public int deleteWfUserGroupByIds(Long[] ids){
        return wfUserGroupMapper.deleteWfUserGroupByIds(ids);
    }

    /**
     * 删除用户组信息
     *
     * @param id 用户组主键
     * @return 结果
     */
    @Override
    public int deleteWfUserGroupById(Long id){
        return wfUserGroupMapper.deleteWfUserGroupById(id);
    }

    @Override
    public List<SelectMoreVo> getSimpleList(String keywords){
        return wfUserGroupMapper.getSimpleList(keywords);
    }
    @Override
    public List<WfUserGroup> selectBatchIds(List<Long> ids){
        return wfUserGroupMapper.selectBatchIds(ids);
    }

}
