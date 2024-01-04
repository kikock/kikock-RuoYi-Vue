package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.NumberUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.task.BpmTaskExt;
import com.ruoyi.flowable.domain.task.vo.BpmTaskPageItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskPageReqVO;
import com.ruoyi.flowable.mapper.task.BpmTaskExtMapper;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import com.ruoyi.flowable.service.task.IBpmTaskService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private BpmTaskExtMapper bpmTaskExtMapper;
    @Override
    public List<?> getToDotask(Long userId, BpmTaskPageReqVO pageVO) {
        // 查询待办任务
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(String.valueOf(userId)) // 分配给自己
                .orderByTaskCreateTime().desc(); // 创建时间倒序
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        if (ArrayUtil.get(pageVO.getCreateTime(), 0) != null) {
            taskQuery.taskCreatedAfter(pageVO.getCreateTime()[0]);
        }
        if (ArrayUtil.get(pageVO.getCreateTime(), 1) != null) {
            taskQuery.taskCreatedBefore(pageVO.getCreateTime()[1]);
        }
        // 执行查询
        List<Task> tasks = taskQuery.listPage((pageVO.getPageNum()-1)*pageVO.getPageSize(), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            return new ArrayList<>();
        }
        // 获得 ProcessInstance Map
        Set<String> ids = tasks.stream().map(TaskInfo::getProcessInstanceId).collect(Collectors.toSet());
        List<ProcessInstance> processList = processInstanceService.getProcessInstances(ids);
        Map<String, ProcessInstance> processInstanceMap = convertMap(processList, ProcessInstance::getProcessInstanceId);
        // 获得 User Map
        List<String> starUserIds = processList.stream().map(ProcessInstance::getStartUserId).collect(Collectors.toList());
        List<SysUser> userList = sysUserService.getUserList(starUserIds);
        Map<Long, SysUser> userMap = convertMap(userList, SysUser::getUserId);
        //拼接集合
        return convertList(tasks, task -> {
            BpmTaskPageItemRespVO bpmTaskTodoPageItemRespVO = convert1(task);
            ProcessInstance processInstance = processInstanceMap.get(task.getProcessInstanceId());
            if (processInstance != null) {
                SysUser sysUser = userMap.get(StrUtil.isNotEmpty(processInstance.getStartUserId()) ? Long.valueOf(processInstance.getStartUserId()) : null);
                bpmTaskTodoPageItemRespVO.setProcessInstance(getProcessInstance(processInstance, sysUser));
            }
            return bpmTaskTodoPageItemRespVO;
        });
    }

    private static BpmTaskPageItemRespVO convert1(Task task) {
        BpmTaskPageItemRespVO bpmTaskTodoPageItemRespVO = new BpmTaskPageItemRespVO();
        bpmTaskTodoPageItemRespVO.setSuspensionState( task.isSuspended()? SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode() );
        bpmTaskTodoPageItemRespVO.setId( task.getId() );
        bpmTaskTodoPageItemRespVO.setName( task.getName() );
        bpmTaskTodoPageItemRespVO.setClaimTime( task.getClaimTime());
        bpmTaskTodoPageItemRespVO.setCreateTime(task.getCreateTime() );
        return bpmTaskTodoPageItemRespVO;
    }

    private static BpmTaskPageItemRespVO.ProcessInstance getProcessInstance(ProcessInstance processInstance, SysUser startUser) {
        BpmTaskPageItemRespVO.ProcessInstance processInstance1 = new BpmTaskPageItemRespVO.ProcessInstance();

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
    public List<?> getDoentask(Long userId, BpmTaskPageReqVO pageVO) {
        // 查询已办任务
        HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().finished() // 已完成
                .taskAssignee(String.valueOf(userId)) // 分配给自己
                .orderByHistoricTaskInstanceEndTime().desc(); // 审批时间倒序
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        if (ArrayUtil.get(pageVO.getCreateTime(), 0) != null) {
            taskQuery.taskCreatedAfter(pageVO.getCreateTime()[0]);
        }
        if (ArrayUtil.get(pageVO.getCreateTime(), 1) != null) {
            taskQuery.taskCreatedBefore(pageVO.getCreateTime()[1]);
        }
        // 执行查询
        List<HistoricTaskInstance> tasks = taskQuery.listPage((pageVO.getPageNum()-1)*pageVO.getPageSize(), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            return new ArrayList<>();
        }

        // 获得 TaskExtDO Map
        List<BpmTaskExt> bpmTaskExtDOs =bpmTaskExtMapper.selectListByTaskIds(convertSet(tasks, HistoricTaskInstance::getId));
        Map<String, BpmTaskExt> bpmTaskExtDOMap = convertMap(bpmTaskExtDOs, BpmTaskExt::getTaskId);
        // 获得 ProcessInstance Map
        Set<String> ids = convertSet(tasks, HistoricTaskInstance::getProcessInstanceId);
        List<HistoricProcessInstance> historicProcessInstances = processInstanceService.getHistoricProcessInstances(ids);
        Map<String, HistoricProcessInstance> historicProcessInstanceMap = convertMap(historicProcessInstances, HistoricProcessInstance::getId);
        // 获得 User Map
        List<String> starUserIds = historicProcessInstances.stream().map(HistoricProcessInstance::getStartUserId).collect(Collectors.toList());
        List<SysUser> userList = sysUserService.getUserList(starUserIds);
        Map<Long, SysUser> userMap = convertMap(userList, SysUser::getUserId);
        // 拼接结果
        return convertList(tasks, task -> {
            BpmTaskPageItemRespVO respVO = convert2(task);
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

    private BpmTaskPageItemRespVO convert2(HistoricTaskInstance task) {
        if ( task == null ) {
            return null;
        }
        BpmTaskPageItemRespVO bpmTaskDonePageItemRespVO = new BpmTaskPageItemRespVO();
        bpmTaskDonePageItemRespVO.setId( task.getId() );
        bpmTaskDonePageItemRespVO.setName( task.getName() );
        bpmTaskDonePageItemRespVO.setClaimTime( task.getClaimTime()  );
        bpmTaskDonePageItemRespVO.setCreateTime( task.getCreateTime()  );
        bpmTaskDonePageItemRespVO.setEndTime(  task.getEndTime()  );
        bpmTaskDonePageItemRespVO.setDurationInMillis( task.getDurationInMillis() );
        return bpmTaskDonePageItemRespVO;
    }
    public BpmTaskPageItemRespVO.ProcessInstance convert(HistoricProcessInstance processInstance, SysUser startUser) {
        if ( processInstance == null && startUser == null ) {
            return null;
        }

        BpmTaskPageItemRespVO.ProcessInstance processInstance1 = new BpmTaskPageItemRespVO.ProcessInstance();

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
}
