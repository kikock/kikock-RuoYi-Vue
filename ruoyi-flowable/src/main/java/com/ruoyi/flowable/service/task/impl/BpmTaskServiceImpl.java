package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.flowable.domain.task.BpmTaskExt;
import com.ruoyi.flowable.domain.task.vo.BpmTaskItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.mapper.task.BpmTaskExtMapper;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
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
            return Collections.emptyList();
        }
        // 获得 ProcessInstance Map
        Set<String> ids = tasks.stream().map(TaskInfo::getProcessInstanceId).collect(Collectors.toSet());
        List<ProcessInstance> processList = processInstanceService.getProcessInstances(ids);
        Map<String, ProcessInstance> processInstanceMap = convertMap(processList, ProcessInstance::getProcessInstanceId);
        // 获得 User Map
        Set<Long> starUserIds = convertSet(processInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId()));
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
        Set<Long> starUserIds = convertSet(historicProcessInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId()));
        List<SysUser> userList = sysUserService.getUserList(starUserIds);
        Map<Long, SysUser> userMap = convertMap(userList, SysUser::getUserId);
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
                SysUser sysUser = userMap.get(StrUtil.isNotEmpty(processInstance.getStartUserId()) ? Long.valueOf(processInstance.getStartUserId()) : null);
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
    public BpmTaskItemRespVO.ProcessInstance convert(HistoricProcessInstance processInstance, SysUser startUser) {
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
            processInstance1.setStartUserNickname( startUser.getNickName() );
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
        // 获得 User Map
        Set<Long> userIds = convertSet(tasks, task -> parseLong(task.getAssignee()));
        userIds.add(parseLong(processInstance.getStartUserId()));
        List<SysUser> userList = sysUserService.getUserList(userIds);
        Map<Long, SysUser> userMap = convertMap(userList, SysUser::getUserId);
        // 获得 Dept Map
        List<Long> deptIds = convertList(userMap.values(), SysUser::getDeptId);
        List<SysDept> sysDepts = deptService.selectBatchIds(deptIds);
        Map<Long, SysDept> deptMap = convertMap(sysDepts, SysDept::getDeptId);
        // 拼接数据
        return convertList(tasks, task -> {
            BpmTaskItemRespVO respVO = convert3(task);
            BpmTaskExt taskExtDO = bpmTaskExtDOMap.get(task.getId());
            copyTo(taskExtDO, respVO);
            if (processInstance != null) {
                SysUser startUser = userMap.get(parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            SysUser assignUser = userMap.get(parseLong(task.getAssignee()));
            if (assignUser != null) {
                respVO.setAssigneeUser(convert3(assignUser));
                SysDept dept = deptMap.get(assignUser.getDeptId());
                if (dept != null) {
                    respVO.getAssigneeUser().setDeptName(dept.getDeptName());
                }
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
    public BpmTaskItemRespVO.User convert3(SysUser bean) {
        if ( bean == null ) {
            return null;
        }

        BpmTaskItemRespVO.User user = new BpmTaskItemRespVO.User();

        user.setId( bean.getDeptId() );
        user.setNickname( bean.getNickName() );
        user.setDeptId( bean.getDeptId() );
        return user;
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
