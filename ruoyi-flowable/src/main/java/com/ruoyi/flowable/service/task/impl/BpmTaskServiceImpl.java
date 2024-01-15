package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.NumberUtils;
import com.google.common.base.Joiner;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.vo.SysUserSimpleVo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.flowable.domain.task.BpmTaskExt;
import com.ruoyi.flowable.domain.task.vo.BpmTaskItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.enums.BpmProcessInstanceResultEnum;
import com.ruoyi.flowable.mapper.task.BpmTaskExtMapper;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.compiler.CompilerUtil.getTask;
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
        List<Task> tasks = taskQuery.list();
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
        List<HistoricTaskInstance> tasks = taskQuery.list();
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
        List<Long> users=new ArrayList<>();
        userlists.forEach(userlist->{
            String[] list = userlist.split(",");
            List<String> strings = Arrays.asList(list);
            strings.forEach(userid->{
                users.add(Long.valueOf(userid));
            });
        });


        List<Long> userIds = convertList(tasks, task -> parseLong(task.getAssignee()));
        userIds.add(parseLong(processInstance.getStartUserId()));
        userIds.addAll(users);
        List<Long> collect = userIds.stream().distinct().collect(Collectors.toList());
        List<SysUserSimpleVo> userList = sysUserService.selectBatchIds(collect);
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
            if (taskExtDO.getAssigneeUserId() != null) {
                assignUser = userMap.get(taskExtDO.getAssigneeUserId());
                respVO.setAssigneeUser(assignUser);
                respVO.setAssigneeUserId(taskExtDO.getAssigneeUserId());
            }


            List<String> strings = Arrays.asList(taskExtDO.getUserList().split(","));
            List<SysUserSimpleVo> voList=new ArrayList<>();
            strings.forEach(id-> voList.add( userMap.get( Long.valueOf(id))));
            respVO.setCandidateUsers(voList);
            respVO.setUserId(SecurityUtils.getUserId());
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
        Set<Long> set = taskAssignRuleService.calculateTaskCandidateUsers(task.getName(),
                task.getProcessDefinitionId(),
                task.getTaskDefinitionKey(),task.getProcessInstanceId());
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveTask(BpmTaskReqVO reqVO) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)){
            throw new ServiceException("审批人不存在请重新登录再试!", HttpStatus.ERROR);
        }
        // 获取流程任务
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw new ServiceException("审批任务失败，原因：该任务不处于未审批状态!", HttpStatus.ERROR);
        }
//        获取拓展表数据
        BpmTaskExt bpmTaskExt   = taskExtMapper.findByTaskId(task.getId());
        String userList = bpmTaskExt.getUserList();
        //判断当前审批用户是否在候选人中
        List<String> strings = Arrays.asList(bpmTaskExt.getUserList().split(","));
        boolean isCandidates = strings.stream()
                .anyMatch(s -> s.equals(loginUser.getUserId().toString())); // 将 id 转换为字符串并检查是否匹配
        if (!isCandidates) {
            throw new ServiceException("审批任务失败，原因：该任务的审批人不是你!", HttpStatus.ERROR);
        }

        // 校验流程实例存在
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw new ServiceException("审批任务失败，原因：流程实例不存在!", HttpStatus.ERROR);
        }

        // 完成任务，审批通过
        taskService.complete(task.getId(), instance.getProcessVariables());
        // 更新任务拓展表为通过
        bpmTaskExt.setResult(BpmProcessInstanceResultEnum.APPROVE.getResult());
        bpmTaskExt.setReason(reqVO.getReason());
        bpmTaskExt.setAssigneeUserId(loginUser.getUserId());
        taskExtMapper.updateBpmTaskExt(bpmTaskExt);
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(task.getId()).singleResult();
        if (taskEntity != null) {
            TaskHelper.changeTaskAssignee(taskEntity, String.valueOf(loginUser.getUserId()));
            //2.委派处理
//            if (DelegationState.PENDING.equals(taskEntity.getDelegationState())) {
//                //2.1生成历史记录
//                TaskEntity task = this.createSubTask(taskEntity, params.getUserCode());
//                taskService.saveTask(task);
//                taskService.complete(task.getId());
//                //2.2生成审批意见
//                this.addComment(task.getId(), params.getUserCode(), params.getProcessInstanceId(),
//                        CommentTypeEnum.SP.toString(), params.getMessage());
//                //2.3执行委派
//                taskService.resolveTask(params.getTaskId(), params.getVariables());
//            } else {
//                //3.1修改执行人 其实我这里就相当于签收了
//                taskService.setAssignee(params.getTaskId(), params.getUserCode());
//                //3.2执行任务
//                taskService.complete(params.getTaskId(), params.getVariables());
//                //3.3生成审批记录
//                this.addComment(params.getTaskId(), params.getUserCode(), params.getProcessInstanceId(),
//                        CommentTypeEnum.SP.toString(), params.getMessage());
//                //4.处理加签父任务
//                String parentTaskId = taskEntity.getParentTaskId();
//                if (StringUtils.isNotBlank(parentTaskId)) {
//                    String tableName = managementService.getTableName(TaskEntity.class);
//                    String sql = "select count(1) from " + tableName + " where PARENT_TASK_ID_=#{parentTaskId}";
//                    long subTaskCount = taskService.createNativeTaskQuery().sql(sql).parameter("parentTaskId", parentTaskId).count();
//                    if (subTaskCount == 0) {
//                        Task task = taskService.createTaskQuery().taskId(parentTaskId).singleResult();
//                        //处理前后加签的任务
//                        taskService.resolveTask(parentTaskId);
//                        if (FlowConstant.AFTER_ADDSIGN.equals(task.getScopeType())) {
//                            taskService.complete(parentTaskId);
//                        }
//                    }
//                }
//            }
//        } else {
//            returnVo = new ReturnVo<>(ReturnCode.FAIL, "没有此任务，请确认!");
//        }
//    } else {
//        returnVo = new ReturnVo<>(ReturnCode.FAIL, "请输入正确的参数!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectTask(BpmTaskReqVO reqVO) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)){
            throw new ServiceException("审批人不存在请重新登录再试!", HttpStatus.ERROR);
        }
        Task task = checkTask(loginUser.getUserId(), reqVO.getId());
        // 校验流程实例存在
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw new ServiceException("审批任务失败，原因：流程实例不存在!", HttpStatus.ERROR);
        }

        // 更新流程实例为不通过
        processInstanceService.updateProcessInstanceExtReject(instance.getProcessInstanceId(), reqVO.getReason());
        // 更新任务拓展表为不通过
        BpmTaskExt bpmTaskExt   = taskExtMapper.findByTaskId(task.getId());
        bpmTaskExt.setResult(BpmProcessInstanceResultEnum.REJECT.getResult());
        bpmTaskExt.setReason(reqVO.getReason());
        taskExtMapper.updateBpmTaskExt(bpmTaskExt);
    }

    public static Long parseLong(String str) {
        return StrUtil.isNotEmpty(str) ? Long.valueOf(str) : null;
    }
    /**
     * 校验任务是否存在， 并且是否是分配给自己的任务
     *
     * @param userId 用户 id
     * @param taskId task id
     */
    private Task checkTask(Long userId, String taskId) {
        Task task = getTask(taskId);
        if (task == null) {
            throw new ServiceException("审批任务失败，原因：该任务不处于未审批状态!", HttpStatus.ERROR);
        }
//        if (!Objects.equals(userId, NumberUtils.parseLong(task.getAssignee()))) {
//            throw new ServiceException("审批任务失败，原因：该任务的审批人不是你!", HttpStatus.ERROR);
//        }
        return task;
    }

    private Task getTask(String id) {
        return taskService.createTaskQuery().taskId(id).singleResult();
    }
}
