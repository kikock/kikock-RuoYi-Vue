package com.ruoyi.workflow.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.workflow.domain.WfUserGroup;
import com.ruoyi.workflow.service.IWfUserGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
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

    public Set<String> getApproverUsers(DelegateExecution execution){
        Set<String> candidateUserIds = new LinkedHashSet<>();
        FlowElement flowElement = execution.getCurrentFlowElement();
        if (ObjectUtil.isNotEmpty(flowElement) && flowElement instanceof UserTask) {
            List<Long> userIds = new ArrayList<>();
            UserTask userTask = (UserTask) flowElement;
            String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
            log.debug("审批用户获取:{}",userTask.getName());
            if ("USERS".equals(dataType) && CollUtil.isNotEmpty(userTask.getCandidateUsers())) {
                // 添加候选用户id
                candidateUserIds.addAll(userTask.getCandidateUsers());
            } else if (CollUtil.isNotEmpty(userTask.getCandidateGroups())) {
                // 获取组的ID，角色ID集合或部门ID集合
                List<String> groupIds = userTask.getCandidateGroups();
                List<Long> longList = Convert.convert(List.class, groupIds);
                List<WfUserGroup> wfUserGroups = wfUserGroupService.selectBatchIds(longList);
                if (CollUtil.isNotEmpty(wfUserGroups) && wfUserGroups.size() >0) {
                    List<String> collect = wfUserGroups.stream().map(wfUserGroup -> wfUserGroup.getMemberUserIds()).collect(Collectors.toList());
                    collect.stream().forEach(ids -> {
                        List<String> stringArray =  StrUtil.split(ids, ",");
                        candidateUserIds.addAll(stringArray);
                    });
                }

                if ("ROLES".equals(dataType)) {
                    // 通过角色id，获取所有用户id集合



                } else if ("DEPTS".equals(dataType)) {
                    // 通过部门id，获取所有用户id集合
//                    LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery(SysUser.class).select(SysUser::getUserId).in(SysUser::getDeptId, groups);
//                    userIds = SimpleQuery.list(lqw, SysUser::getUserId);
                }
                // 添加候选用户id
                userIds.forEach(id -> candidateUserIds.add(String.valueOf(id)));
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
            log.debug("审批用户获取:{}",userTask.getName());
            if ("USERS".equals(dataType) && CollUtil.isNotEmpty(userTask.getCandidateUsers())) {
                // 添加候选用户id
                candidateUserIds.addAll(userTask.getCandidateUsers());
            } else if (CollUtil.isNotEmpty(userTask.getCandidateGroups())) {
                // 获取组的ID，角色ID集合或部门ID集合
                List<String> groupIds = userTask.getCandidateGroups();
                List<Long> longList = Convert.convert(List.class, groupIds);
                List<WfUserGroup> wfUserGroups = wfUserGroupService.selectBatchIds(longList);
                if (CollUtil.isNotEmpty(wfUserGroups) && wfUserGroups.size() >0) {
                    List<String> collect = wfUserGroups.stream().map(wfUserGroup -> wfUserGroup.getMemberUserIds()).collect(Collectors.toList());
                    collect.stream().forEach(ids -> {
                        List<String> stringArray =  StrUtil.split(ids, ",");
                        candidateUserIds.addAll(stringArray);
                    });
                }

                if ("ROLES".equals(dataType)) {
                    // 通过角色id，获取所有用户id集合



                } else if ("DEPTS".equals(dataType)) {
                    // 通过部门id，获取所有用户id集合
//                    LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery(SysUser.class).select(SysUser::getUserId).in(SysUser::getDeptId, groups);
//                    userIds = SimpleQuery.list(lqw, SysUser::getUserId);
                }
                // 添加候选用户id
                userIds.forEach(id -> candidateUserIds.add(String.valueOf(id)));
            }
        }
        return candidateUserIds;
    }
}
