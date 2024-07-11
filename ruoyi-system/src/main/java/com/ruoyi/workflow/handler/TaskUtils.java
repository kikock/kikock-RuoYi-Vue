package com.ruoyi.workflow.handler;

/**
 * @project_name: RuoYi-Vue-master
 * @description: 单实例获取用户
 * @create_name: kikock
 * @create_date: 2024-07-02 16:04
 **/

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.flowable.common.constant.TaskConstants;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.workflow.service.IWfUserGroupService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 工作流任务工具类
 *
 * @author konbai
 * @createTime 2022/4/24 12:42
 */
@Slf4j
public class TaskUtils{
    public static String getUserId(){
        return String.valueOf(SecurityUtils.getUserId());
    }

    /**
     * 获取用户信息
     *
     * @return candidateGroup
     */
    public static List<String> getCandidateGroup(){
        List<String> list = new ArrayList<>();
        LoginUser user = SecurityUtils.getLoginUser();
        if (ObjectUtil.isNotNull(user)) {
            //     用户候选组
            if (ObjectUtil.isNotNull(user.getUserId())) {
                list.add(TaskConstants.USERS_GROUP_PREFIX + user.getUserId());
            }
            //     部门候选组
            if (ObjectUtil.isNotNull(user.getDeptId())) {
                list.add(TaskConstants.DEPTS_GROUP_PREFIX + user.getDeptId());
            }
//     角色候选组
            if (ObjectUtil.isNotEmpty(user.getUser().getRoles())) {
                user.getUser().getRoles().forEach(role -> list.add(TaskConstants.ROLES_GROUP_PREFIX + role.getRoleId()));
            }
            //     岗位候选组
            if (ObjectUtil.isNotEmpty(user.getUserId())) {
                ISysPostService postService = SpringUtils.getBean(ISysPostService.class);
                List<Long> postIds = postService.selectPostListByUserId(user.getUserId());
                postIds.stream().forEach(postId -> list.add(TaskConstants.POSTS_GROUP_PREFIX + postId));
            }
            //     自定义用户组候选组
            if (ObjectUtil.isNotEmpty(user.getUserId())) {
                IWfUserGroupService wfUserGroupService = SpringUtils.getBean(IWfUserGroupService.class);
                List<String> groupIds = wfUserGroupService.getGroupIds(String.valueOf(user.getUserId()));
                groupIds.stream().forEach(groupId -> list.add(TaskConstants.USERGROUP_GROUP_PREFIX + groupId));
            }
        }
        log.info("获取审批信息：{}", list.toString());
        return list;
    }
}

