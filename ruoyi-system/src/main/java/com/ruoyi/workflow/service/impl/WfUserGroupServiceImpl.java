package com.ruoyi.workflow.service.impl;


import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.workflow.domain.WfUserGroup;
import com.ruoyi.workflow.mapper.WfUserGroupMapper;
import com.ruoyi.workflow.service.IWfUserGroupService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
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
    @Transactional
    public int insertWfUserGroup(WfUserGroup WfUserGroup){
        WfUserGroup.setCreateTime(DateUtils.getNowDate());
        int i = wfUserGroupMapper.insertWfUserGroup(WfUserGroup);
        if (i > 0) {
            //保存成功,更新关联数据
            String memberUserIds = WfUserGroup.getMemberUserIds();
            List<String> ids = StrUtil.split(memberUserIds, ",");
            wfUserGroupMapper.addSysUserGroup(WfUserGroup.getId(),ids);
        }
        return i;

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
        int i = wfUserGroupMapper.updateWfUserGroup(WfUserGroup);
        if(i>0){
            //先删除 再关联数据
            wfUserGroupMapper.delSysUserGroup(WfUserGroup.getId());
            String memberUserIds = WfUserGroup.getMemberUserIds();
            List<String> ids = StrUtil.split(memberUserIds, ",");
            wfUserGroupMapper.addSysUserGroup(WfUserGroup.getId(),ids);
        }
        return i;

    }

    /**
     * 批量删除用户组
     *
     * @param ids 需要删除的用户组主键
     * @return 结果
     */
    @Override
    public int deleteWfUserGroupByIds(Long[] ids){
        Arrays.stream(ids).map(id ->  wfUserGroupMapper.delSysUserGroup(id));
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

    @Override
    public List<String> getGroupIds(String userId){
        return wfUserGroupMapper.getGroupIds(userId);
    }

}
