package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.NumberUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.vo.SysUserSimpleVo;
import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;
import com.ruoyi.flowable.domain.task.BpmTaskExt;
import com.ruoyi.flowable.domain.task.vo.BpmTaskItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.enums.BpmProcessInstanceResultEnum;
import com.ruoyi.flowable.mapper.definition.BpmTaskAssignRuleMapper;
import com.ruoyi.flowable.mapper.task.BpmTaskExtMapper;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import liquibase.pro.packaged.L;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.collection.CollectionUtils.*;


@Slf4j
@Service
public class BpmTaskServiceImpl implements IBpmTaskService {
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private IBpmProcessInstanceService processInstanceService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysDeptService deptService;
    @Resource
    private BpmTaskExtMapper taskExtMapper;
    @Resource
    private IBpmTaskAssignRuleService taskAssignRuleService;

    @Override
    public List<?> getToDotask(Long userId, BpmTaskReqVO pageVO) {
        // 查询待办任务
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(String.valueOf(userId)) // 分配给自己
                .orderByTaskCreateTime().desc(); // 创建时间倒序
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        // 执行查询
        List<Task> tasks = taskQuery.listPage((pageVO.getPageNum()-1)*pageVO.getPageSize(), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            tasks = taskService.createTaskQuery().taskCandidateUser(String.valueOf(userId)).list();
        }
        if (CollUtil.isEmpty(tasks)) {
            return Collections.emptyList();
        }
        // 获得 ProcessInstance Map
        Set<String> ids = tasks.stream().map(TaskInfo::getProcessInstanceId).collect(Collectors.toSet());
        List<ProcessInstance> processList = processInstanceService.getProcessInstances(ids);
        Map<String, ProcessInstance> processInstanceMap = convertMap(processList, ProcessInstance::getProcessInstanceId);
        // 获得 User Map
        List<Long> starUserIds = convertList(processInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId()));
        List<SysUser> userList = sysUserService.getUserList(starUserIds);
        Map<Long, SysUser> userMap = convertMap(userList, SysUser::getUserId);
        //拼接集合
        return convertList(tasks, task -> {
            BpmTaskItemRespVO bpmTaskTodoPageItemRespVO = convert1(task);
            ProcessInstance processInstance = processInstanceMap.get(task.getProcessInstanceId());
            if (processInstance != null) {
                SysUser sysUser = userMap.get(StrUtil.isNotEmpty(processInstance.getStartUserId()) ? Long.valueOf(processInstance.getStartUserId()) : null);
                bpmTaskTodoPageItemRespVO.setProcessInstance(getProcessInstance(processInstance, sysUser));
            }
            return bpmTaskTodoPageItemRespVO;
        });
    }
    private static BpmTaskItemRespVO convert1(Task task) {
        BpmTaskItemRespVO bpmTaskTodoPageItemRespVO = new BpmTaskItemRespVO();
        bpmTaskTodoPageItemRespVO.setSuspensionState( task.isSuspended()? SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode() );
        bpmTaskTodoPageItemRespVO.setId( task.getId() );
        bpmTaskTodoPageItemRespVO.setName( task.getName() );
        bpmTaskTodoPageItemRespVO.setClaimTime( task.getClaimTime());
        bpmTaskTodoPageItemRespVO.setCreateTime(task.getCreateTime() );
        return bpmTaskTodoPageItemRespVO;
    }
    private static BpmTaskItemRespVO.ProcessInstance getProcessInstance(ProcessInstance processInstance, SysUser startUser) {
        BpmTaskItemRespVO.ProcessInstance processInstance1 = new BpmTaskItemRespVO.ProcessInstance();

        if ( processInstance != null ) {
            processInstance1.setId( processInstance.getId() );
            processInstance1.setName( processInstance.getName() );
            if ( processInstance.getStartUserId() != null ) {
                processInstance1.setStartUserId( Long.parseLong( processInstance.getStartUserId() ) );
            }
            processInstance1.setProcessDefinitionId( processInstance.getProcessDefinitionId() );
        }
        if ( startUser != null ) {
            processInstance1.setStartUserNickname( startUser.getNickName() );
        }
        return processInstance1;
    }


    @Override
    public List<?> getDonetask(Long userId, BpmTaskReqVO pageVO) {
        // 查询已办任务
        HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().finished() // 已完成
                .taskAssignee(String.valueOf(userId)) // 分配给自己
                .orderByHistoricTaskInstanceEndTime().desc(); // 审批时间倒序
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        // 执行查询
        List<HistoricTaskInstance> tasks = taskQuery.listPage((pageVO.getPageNum()-1)*pageVO.getPageSize(), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            return Collections.emptyList();
        }

        // 获得 TaskExtDO Map
        List<BpmTaskExt> bpmTaskExtDOs = taskExtMapper.selectListByTaskIds(convertSet(tasks, HistoricTaskInstance::getId));
        Map<String, BpmTaskExt> bpmTaskExtDOMap = convertMap(bpmTaskExtDOs, BpmTaskExt::getTaskId);
        // 获得 ProcessInstance Map
        Set<String> ids = convertSet(tasks, HistoricTaskInstance::getProcessInstanceId);
        List<HistoricProcessInstance> historicProcessInstances = processInstanceService.getHistoricProcessInstances(ids);
        Map<String, HistoricProcessInstance> historicProcessInstanceMap = convertMap(historicProcessInstances, HistoricProcessInstance::getId);
        // 获得 User Map
        List<Long> starUserIds = convertList(historicProcessInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId()));
        List<SysUserSimpleVo> userList = sysUserService.selectBatchIds(starUserIds);
        Map<Long, SysUserSimpleVo> userMap = convertMap(userList, SysUserSimpleVo::getId);
        // 拼接结果
        return convertList(tasks, task -> {
            BpmTaskItemRespVO respVO = convert2(task);
            BpmTaskExt taskExtDO = bpmTaskExtDOMap.get(task.getId());
            if (taskExtDO!=null){
                respVO.setName( taskExtDO.getName() );
                respVO.setCreateTime( taskExtDO.getCreateTime() );
                respVO.setEndTime( taskExtDO.getEndTime() );
                respVO.setResult( taskExtDO.getResult() );
                respVO.setReason( taskExtDO.getReason() );
            }
            HistoricProcessInstance processInstance = historicProcessInstanceMap.get(task.getProcessInstanceId());
            if (processInstance != null) {
                SysUserSimpleVo sysUser = userMap.get(StrUtil.isNotEmpty(processInstance.getStartUserId()) ? Long.valueOf(processInstance.getStartUserId()) : null);
                respVO.setProcessInstance(convert(processInstance, sysUser));
            }
            return respVO;
        });

    }
    private BpmTaskItemRespVO convert2(HistoricTaskInstance task) {
        if ( task == null ) {
            return null;
        }
        BpmTaskItemRespVO bpmTaskDonePageItemRespVO = new BpmTaskItemRespVO();
        bpmTaskDonePageItemRespVO.setId( task.getId() );
        bpmTaskDonePageItemRespVO.setName( task.getName() );
        bpmTaskDonePageItemRespVO.setClaimTime( task.getClaimTime()  );
        bpmTaskDonePageItemRespVO.setCreateTime( task.getCreateTime()  );
        bpmTaskDonePageItemRespVO.setEndTime(  task.getEndTime()  );
        bpmTaskDonePageItemRespVO.setDurationInMillis( task.getDurationInMillis() );
        return bpmTaskDonePageItemRespVO;
    }
    public BpmTaskItemRespVO.ProcessInstance convert(HistoricProcessInstance processInstance, SysUserSimpleVo startUser) {
        if ( processInstance == null && startUser == null ) {
            return null;
        }

        BpmTaskItemRespVO.ProcessInstance processInstance1 = new BpmTaskItemRespVO.ProcessInstance();

        if ( processInstance != null ) {
            processInstance1.setId( processInstance.getId() );
            processInstance1.setName( processInstance.getName() );
            if ( processInstance.getStartUserId() != null ) {
                processInstance1.setStartUserId( Long.parseLong( processInstance.getStartUserId() ) );
            }
            processInstance1.setProcessDefinitionId( processInstance.getProcessDefinitionId() );
        }
        if ( startUser != null ) {
            processInstance1.setStartUserNickname( startUser.getName() );
        }

        return processInstance1;
    }


    @Override
    public List<Task> getTasksByProcessInstanceIds(List<String> processInstanceIds) {
        if (CollUtil.isEmpty(processInstanceIds)) {
            return Collections.emptyList();
        }
        return taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
    }


    @Override
    public List<BpmTaskItemRespVO> getTaskListByProcessInstanceId(String processInstanceId) {
        // 获得任务列表
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().desc() // 创建时间倒序
                .list();
        if (CollUtil.isEmpty(tasks)) {
            return Collections.emptyList();
        }
        // 获得 TaskExtDO Map
        List<BpmTaskExt> bpmTaskExtDOs = taskExtMapper.selectListByTaskIds(convertSet(tasks, HistoricTaskInstance::getId));
        Map<String, BpmTaskExt> bpmTaskExtDOMap = convertMap(bpmTaskExtDOs, BpmTaskExt::getTaskId);
        // 获得 ProcessInstance Map
        HistoricProcessInstance processInstance = processInstanceService.getHistoricProcessInstance(processInstanceId);
        // 获得 处理人和候选人 user map信息
        //候选人信息
        List<String> userlists = convertList(bpmTaskExtDOs, taskExt -> taskExt.getUserList());
        //所有候选人list


        List<Long> userIds = convertList(tasks, task -> parseLong(task.getAssignee()));
        userIds.add(parseLong(processInstance.getStartUserId()));
        List<SysUserSimpleVo> userList = sysUserService.selectBatchIds(userIds);
        Map<Long, SysUserSimpleVo> userMap = convertMap(userList, SysUserSimpleVo::getId);
        // 拼接数据
        return convertList(tasks, task -> {
            System.out.println(task.getName());;
            BpmTaskItemRespVO respVO = convert3(task);
            BpmTaskExt taskExtDO = bpmTaskExtDOMap.get(task.getId());
            respVO.setName( taskExtDO.getName() );
            respVO.setCreateTime( taskExtDO.getCreateTime() );
            respVO.setEndTime( taskExtDO.getEndTime() );
            respVO.setResult( taskExtDO.getResult() );
            respVO.setReason( taskExtDO.getReason() );
            if (processInstance != null) {
                SysUserSimpleVo startUser = userMap.get(parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            SysUserSimpleVo assignUser = userMap.get(parseLong(task.getAssignee()));
            if (assignUser != null) {
                respVO.setAssigneeUser(assignUser);
            }
            return respVO;
        });
    }
    private BpmTaskItemRespVO convert3(HistoricTaskInstance bean) {
        if ( bean == null ) {
            return null;
        }
        BpmTaskItemRespVO bpmTaskRespVO = new BpmTaskItemRespVO();
        bpmTaskRespVO.setDefinitionKey( bean.getTaskDefinitionKey() );
        bpmTaskRespVO.setId( bean.getId() );
        bpmTaskRespVO.setName( bean.getName() );
        bpmTaskRespVO.setClaimTime(  bean.getClaimTime()  );
        bpmTaskRespVO.setCreateTime(  bean.getCreateTime()  );
        bpmTaskRespVO.setEndTime(  bean.getEndTime() ) ;
        bpmTaskRespVO.setDurationInMillis( bean.getDurationInMillis() );
        return bpmTaskRespVO;
    }
    public void copyTo(BpmTaskExt from, BpmTaskItemRespVO to) {
        if ( from == null ) {
            return;
        }

        to.setName( from.getName() );
        to.setCreateTime( from.getCreateTime() );
        to.setEndTime( from.getEndTime() );
        to.setResult( from.getResult() );
        to.setReason( from.getReason() );
    }


    @Override
    public void updateTaskAssignee(String id, Long userId) {

    }

    @Override
    public void createTaskExt(Task task) {
        BpmTaskExt taskExt =new BpmTaskExt();
//        创建任务拓展
        taskExt.setTaskId(task.getId());
        taskExt.setName(task.getName());
        taskExt.setProcessDefinitionId(task.getProcessDefinitionId());
        taskExt.setProcessInstanceId(task.getProcessInstanceId());
        taskExt.setCreateTime(task.getCreateTime());
        taskExt.setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
       //拓展表保存候选用户集合
        Set<Long> set = taskAssignRuleService.calculateTaskCandidateUsers2(task.getName(), task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        taskExt.setUserList(Joiner.on(",").join(set));
        taskExtMapper.insertBpmTaskExt(taskExt);
    }

    @Override
    public void updateTaskExtComplete(Task task) {

    }

    @Override
    public void updateTaskExtCancel(String taskId) {

    }

    @Override
    public void updateTaskExtAssign(Task task) {

    }

    public static Long parseLong(String str) {
        return StrUtil.isNotEmpty(str) ? Long.valueOf(str) : null;
    }
}
