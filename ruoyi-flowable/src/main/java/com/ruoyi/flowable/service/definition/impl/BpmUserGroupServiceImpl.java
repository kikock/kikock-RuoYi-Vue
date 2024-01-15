package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.definition.BpmUserGroup;
import com.ruoyi.flowable.mapper.definition.BpmUserGroupMapper;
import com.ruoyi.flowable.service.definition.IBpmUserGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.utils.collection.CollectionUtils.convertMap;

/**
 * 用户组Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmUserGroupServiceImpl implements IBpmUserGroupService{
    @Resource
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

    @Override
    public List<SelectMoreVo> getSimpleList(String keywords){
        return bpmUserGroupMapper.getSimpleList(keywords);
    }
    @Override
    public List<BpmUserGroup> selectBatchIds(List<Long> ids){
        return bpmUserGroupMapper.selectBatchIds(ids);
    }
    @Override
    public void validUserGroups(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<BpmUserGroup> bpmUserGroups = selectBatchIds(ids);
        Map<Long, BpmUserGroup> userGroupMap = convertMap(bpmUserGroups, BpmUserGroup::getId);
        // 校验
        ids.forEach(id -> {
            BpmUserGroup userGroup = userGroupMap.get(id);
            if (userGroup == null) {
                throw new ServiceException(String.format("id为【%s】的用户组不存在",id), HttpStatus.ERROR);
            }
            if (!"0".equals(userGroup.getStatus().toString())) {
                throw new ServiceException(String.format("名字为【%s】的用户组已被禁用",userGroup.getName()), HttpStatus.ERROR);
            }
        });
    }
}
