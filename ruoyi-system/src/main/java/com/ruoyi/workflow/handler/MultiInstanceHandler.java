package com.ruoyi.workflow.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.common.constant.TaskConstants;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.workflow.domain.WfUserGroup;
import com.ruoyi.workflow.service.IWfUserGroupService;
import liquibase.pro.packaged.A;
import liquibase.pro.packaged.I;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 多实例处理类 发起流程获取用户组
 *
 * @author KonBAI
 */
@AllArgsConstructor
@Component("multiInstanceHandler")
@Slf4j
public class MultiInstanceHandler{

    @Autowired
    private IWfUserGroupService wfUserGroupService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysPostService postService;

    public Set<String> getApproverUsers(DelegateExecution execution){
        Set<String> candidateUserIds = new LinkedHashSet<>();
        FlowElement flowElement = execution.getCurrentFlowElement();
        if (ObjectUtil.isNotEmpty(flowElement) && flowElement instanceof UserTask) {
            List<String> userIds = new ArrayList<>();
            UserTask userTask = (UserTask) flowElement;
            log.debug("审批任务[{}]用户获取开始",userTask.getName());
            String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
            if ("USERS".equals(dataType) && CollUtil.isNotEmpty(userTask.getCandidateUsers())) {
                // 添加候选用户id
                candidateUserIds.addAll(userTask.getCandidateUsers());
            } else if (CollUtil.isNotEmpty(userTask.getCandidateGroups())) {
                List<String> groupIds = userTask.getCandidateGroups();
                List<String> ids = new ArrayList<>();
                for (String gid : groupIds) {
                    if (gid.startsWith(TaskConstants.ROLES_GROUP_PREFIX)) {
                        String roleId = StringUtils.stripStart(gid, TaskConstants.ROLES_GROUP_PREFIX);
                        ids.add(roleId);
                        candidateUserIds.addAll(roleService.getUserIdsByRoleId(roleId));
                    } else if (gid.startsWith(TaskConstants.DEPTS_GROUP_PREFIX)) {
                        String deptId = StringUtils.stripStart(gid, TaskConstants.DEPTS_GROUP_PREFIX);
                        ids.add(deptId);
                        candidateUserIds.addAll(deptService.getUserIdsByDeptId(deptId));
                    } else if (gid.startsWith(TaskConstants.POSTS_GROUP_PREFIX)) {
                        String postId = StringUtils.stripStart(gid, TaskConstants.POSTS_GROUP_PREFIX);
                        ids.add(postId);
                        candidateUserIds.addAll(postService.getUserIdsByPostId(postId));
                    } else if (gid.startsWith(TaskConstants.USERS_GROUP_PREFIX)) {
                        String userId = StringUtils.stripStart(gid, TaskConstants.USERS_GROUP_PREFIX);
                        ids.add(userId);
                        candidateUserIds.add(userId);
                    } else if (gid.startsWith(TaskConstants.USERGROUP_GROUP_PREFIX)) {
                        String groupId = StringUtils.stripStart(gid, TaskConstants.USERGROUP_GROUP_PREFIX);
                        ids.add(groupId);
                        candidateUserIds.addAll(wfUserGroupService.getUserIdsByGroupId(groupId));
                    }
                }
                log.debug("审批类型:{}:[{}],审批用户id:{}",TaskConstants.ROLES_GROUP_PREFIX,candidateUserIds.toString());
            }

        }
        return candidateUserIds;
    }


    public Set<String> getUserIds(DelegateExecution execution){
        Set<String> candidateUserIds = new LinkedHashSet<>();
        FlowElement flowElement = execution.getCurrentFlowElement();
        if (ObjectUtil.isNotEmpty(flowElement) && flowElement instanceof UserTask) {
            List<Long> userIds = new ArrayList<>();
            UserTask userTask = (UserTask) flowElement;
            String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
            log.debug("审批任务[{}]用户获取开始",userTask.getName());
            if ("USERS".equals(dataType) && CollUtil.isNotEmpty(userTask.getCandidateUsers())) {
                // 添加候选用户id
                candidateUserIds.addAll(userTask.getCandidateUsers());
            } else if (CollUtil.isNotEmpty(userTask.getCandidateGroups())) {
                List<String> groupIds = userTask.getCandidateGroups();
                List<Long> ids = new ArrayList<>();
                for (String gid : groupIds) {
                    if (gid.startsWith(TaskConstants.ROLES_GROUP_PREFIX)) {
                        String roleId = StringUtils.stripStart(gid, TaskConstants.ROLES_GROUP_PREFIX);
                        candidateUserIds.addAll(roleService.getUserIdsByRoleId(roleId));
                    } else if (gid.startsWith(TaskConstants.DEPTS_GROUP_PREFIX)) {
                        String deptId = StringUtils.stripStart(gid, TaskConstants.DEPTS_GROUP_PREFIX);
                        candidateUserIds.addAll(deptService.getUserIdsByDeptId(deptId));
                    } else if (gid.startsWith(TaskConstants.POSTS_GROUP_PREFIX)) {
                        String postId = StringUtils.stripStart(gid, TaskConstants.POSTS_GROUP_PREFIX);
                        candidateUserIds.addAll(postService.getUserIdsByPostId(postId));
                    } else if (gid.startsWith(TaskConstants.USERS_GROUP_PREFIX)) {
                        String userId = StringUtils.stripStart(gid, TaskConstants.USERS_GROUP_PREFIX);
                        candidateUserIds.add(userId);
                    } else if (gid.startsWith(TaskConstants.USERGROUP_GROUP_PREFIX)) {
                        String groupId = StringUtils.stripStart(gid, TaskConstants.USERGROUP_GROUP_PREFIX);
                        candidateUserIds.addAll(wfUserGroupService.getUserIdsByGroupId(groupId));
                    }
                }
                log.debug("审批类型:{}:[{}],审批用户id:{}",TaskConstants.ROLES_GROUP_PREFIX,candidateUserIds.toString());
            }
        }
        return candidateUserIds;
    }
}
