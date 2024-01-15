package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.NumberUtils;
import com.google.common.base.Joiner;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.vo.SysUserSimpleVo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmForm;
import com.ruoyi.flowable.domain.definition.vo.BpmModelMetaInfoVo;
import com.ruoyi.flowable.domain.definition.vo.BpmModelVo;
import com.ruoyi.flowable.domain.task.BpmTaskExt;
import com.ruoyi.flowable.domain.task.vo.BpmTaskItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.enums.BpmProcessInstanceResultEnum;
import com.ruoyi.flowable.framework.utils.BpmnModelUtils;
import com.ruoyi.flowable.mapper.task.BpmTaskExtMapper;
import com.ruoyi.flowable.service.definition.IBpmModelService;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
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
    private RuntimeService runtimeService;
    @Resource
    private IBpmModelService modelService;
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
            throw new ServiceException("审批任务失败，原因：审批人不存在请重新登录再试!", HttpStatus.ERROR);
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
//        更新处理人
        taskService.setAssignee(task.getId(), String.valueOf(loginUser.getUserId()));

        // 被委派人处理完成任务
        taskService.resolveTask(task.getId(),instance.getProcessVariables());

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
            throw new ServiceException("审批任务失败，原因：审批人不存在请重新登录再试!", HttpStatus.ERROR);
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
        //更新处理人
        taskService.setAssignee(task.getId(), String.valueOf(loginUser.getUserId()));
        // 更新流程实例为不通过
        processInstanceService.updateProcessInstanceExtReject(task.getProcessInstanceId(), reqVO.getReason());
        // 更新任务拓展表为不通过
        bpmTaskExt.setResult(BpmProcessInstanceResultEnum.REJECT.getResult());
        bpmTaskExt.setReason(reqVO.getReason());
        bpmTaskExt.setAssigneeUserId(loginUser.getUserId());
        taskExtMapper.updateBpmTaskExt(bpmTaskExt);
    }

    @Override
    public void updateTaskAssignee(BpmTaskReqVO reqVO){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)){
            throw new ServiceException("转派任务失败，原因：审批人不存在请重新登录再试!", HttpStatus.ERROR);
        }
        // 校验任务存在
        // 获取流程任务
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw new ServiceException("转派任务失败，原因：该任务不处于未审批状态!", HttpStatus.ERROR);
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
        //转办
        taskService.setAssignee(task.getId(),reqVO.getAssigneeUserId());
//        taskService.setOwner(task.getId(), String.valueOf(loginUser.getUserId()));

    }

    @Override
    public List<BpmModelVo> getReturnTaskList(String taskId){
        // 1. 校验当前任务 task 存在
        Task task = getTask(taskId);
        if (task == null) {
            throw new ServiceException("流程任务不存在!", HttpStatus.ERROR);
        }
        BpmnModel bpmnModel = modelService.getBpmnModelByDefinitionId(task.getProcessDefinitionId());
        FlowElement source = BpmnModelUtils.getFlowElementById(bpmnModel, task.getTaskDefinitionKey());
        if (source == null) {
            throw new ServiceException("流程任务不存在!", HttpStatus.ERROR);
        }

        // 2.1 查询该任务的前置任务节点的 key 集合
        List<UserTask> previousUserList = BpmnModelUtils.getPreviousUserTaskList(source, null, null);
        if (CollUtil.isEmpty(previousUserList)) {
            return Collections.emptyList();
        }
        // 2.2 过滤：只有串行可到达的节点，才可以回退。类似非串行、子流程无法退回
        previousUserList.removeIf(userTask -> !BpmnModelUtils.isSequentialReachable(source, userTask, null));
        return CollectionUtils.convertList(previousUserList, model -> {
            BpmModelVo modelVo = new BpmModelVo();
            modelVo.setName(model.getName());
            modelVo.setKey(model.getId());
            return modelVo;
        });

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnTask(BpmTaskReqVO reqVO){
        // 1.1 当前任务 task
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw new ServiceException("回退任务失败，原因：该任务不处于未审批状态!", HttpStatus.ERROR);
        }
        if (task.isSuspended()) {
            throw new ServiceException("回退任务失败，原因：当前任务处于挂起状态，不能操作!", HttpStatus.ERROR);
        }
        // 1.2 校验源头和目标节点的关系，并返回目标元素
        // 获取流程模型信息
        BpmnModel bpmnModel = modelService.getBpmnModelByDefinitionId(task.getProcessDefinitionId());
        // 获取当前任务节点元素
        FlowElement source = BpmnModelUtils.getFlowElementById(bpmnModel, task.getTaskDefinitionKey());
        //获取跳转的节点元素
        FlowElement target = BpmnModelUtils.getFlowElementById(bpmnModel,  reqVO.getTargetDefinitionKey());
        if (target == null) {
            throw new ServiceException("回退任务失败，原因：目标节点不存在!", HttpStatus.ERROR);
        }

        // 2.2 只有串行可到达的节点，才可以回退。类似非串行、子流程无法退回
        if (!BpmnModelUtils.isSequentialReachable(source, target, null)) {
            throw new ServiceException("回退任务失败，原因：目标节点是在并行网关上或非同一路线上，不可跳转!", HttpStatus.ERROR);
        }
        // 2. 调用 flowable 框架的回退逻辑
        returnTask0(task, target, reqVO);
        // 3. 更新任务扩展表
        BpmTaskExt bpmTaskExt   = taskExtMapper.findByTaskId(task.getId());
        bpmTaskExt.setTaskId(task.getId());
        bpmTaskExt.setResult(BpmProcessInstanceResultEnum.BACK.getResult());
        bpmTaskExt.setReason(reqVO.getReason());
        bpmTaskExt.setEndTime(DateUtils.getNowDate());
        taskExtMapper.updateBpmTaskExt(bpmTaskExt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopProcess(BpmTaskReqVO reqVO){
        // 1.1 当前任务 task
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)){
            throw new ServiceException("审批任务终止失败，原因：审批人不存在请重新登录再试!", HttpStatus.ERROR);
        }
        // 获取流程任务
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw new ServiceException("审批任务终止失败，原因：该任务不处于未审批状态!", HttpStatus.ERROR);
        }
//        获取拓展表数据
        BpmTaskExt bpmTaskExt   = taskExtMapper.findByTaskId(task.getId());
        String userList = bpmTaskExt.getUserList();
        //判断当前审批用户是否在候选人中
        List<String> strings = Arrays.asList(bpmTaskExt.getUserList().split(","));
        boolean isCandidates = strings.stream()
                .anyMatch(s -> s.equals(loginUser.getUserId().toString())); // 将 id 转换为字符串并检查是否匹配
        if (!isCandidates) {
            throw new ServiceException("审批任务终止失败，原因：该任务的审批人不是你!", HttpStatus.ERROR);
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (Objects.isNull(processInstance)) {
            throw new ServiceException("审批任务终止失败，原因：不存在运行的流程实例!", HttpStatus.ERROR);
        }
            //2、执行终止
        List<Execution> executions = runtimeService.createExecutionQuery().parentId(task.getProcessInstanceId()).list();
        bpmTaskExt.setTaskId(task.getId());
        bpmTaskExt.setResult(BpmProcessInstanceResultEnum.STOP.getResult());
        bpmTaskExt.setReason(reqVO.getReason());
        bpmTaskExt.setEndTime(DateUtils.getNowDate());
        bpmTaskExt.setAssigneeUserId(loginUser.getUserId());
        taskExtMapper.updateBpmTaskExt(bpmTaskExt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegate(BpmTaskReqVO reqVO){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)){
            throw new ServiceException("委派任务失败，原因：审批人不存在请重新登录再试!", HttpStatus.ERROR);
        }
        // 校验任务存在
        // 获取流程任务
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw new ServiceException("委派任务失败，原因：该任务不处于未审批状态!", HttpStatus.ERROR);
        }
//        获取拓展表数据
        BpmTaskExt bpmTaskExt   = taskExtMapper.findByTaskId(task.getId());
        String userList = bpmTaskExt.getUserList();
        //判断当前审批用户是否在候选人中
        List<String> strings = Arrays.asList(bpmTaskExt.getUserList().split(","));
        boolean isCandidates = strings.stream()
                .anyMatch(s -> s.equals(loginUser.getUserId().toString())); // 将 id 转换为字符串并检查是否匹配
        if (!isCandidates) {
            throw new ServiceException("委派任务失败，原因：该任务的审批人不是你!", HttpStatus.ERROR);
        }
        // 校验流程实例存在
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw new ServiceException("委派任务失败，原因：流程实例不存在!", HttpStatus.ERROR);
        }
        //2.设置审批人就是当前登录人
        taskService.setAssignee(task.getId(), String.valueOf(loginUser.getUserId()));
        //3.执行委派
        taskService.delegateTask(task.getId(), reqVO.getAssigneeUserId());
    }

    public static Long parseLong(String str) {
        return StrUtil.isNotEmpty(str) ? Long.valueOf(str) : null;
    }


    private Task getTask(String id) {
        return taskService.createTaskQuery().taskId(id).singleResult();
    }

    /**
     * 执行回退逻辑
     *
     * @param currentTask          当前回退的任务
     * @param targetElement 需要回退到的目标任务
     * @param reqVO         前端参数封装
     */
    public void returnTask0(Task currentTask, FlowElement targetElement, BpmTaskReqVO reqVO) {
        // 1. 获得所有需要回撤的任务 taskDefinitionKey，用于稍后的 moveActivityIdsToSingleActivityId 回撤
        // 1.1 获取所有正常进行的任务节点 Key
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(currentTask.getProcessInstanceId()).list();
        List<String> runTaskKeyList = convertList(taskList, Task::getTaskDefinitionKey);
        // 1.2 通过 targetElement 的出口连线，计算在 runTaskKeyList 有哪些 key 需要被撤回
        // 为什么不直接使用 runTaskKeyList 呢？因为可能存在多个审批分支，例如说：A -> B -> C 和 D -> F，而只要 C 撤回到 A，需要排除掉 F
        List<UserTask> returnUserTaskList = BpmnModelUtils.iteratorFindChildUserTasks(targetElement, runTaskKeyList, null, null);
        List<String> returnTaskKeyList = convertList(returnUserTaskList, UserTask::getId);

        // 2. 给当前要被回退的 task 数组，设置回退意见
        taskList.forEach(task -> {
            // 需要排除掉，不需要设置回退意见的任务
            if (!returnTaskKeyList.contains(task.getTaskDefinitionKey())) {
                return;
            }
            taskService.addComment(task.getId(), currentTask.getProcessInstanceId(),
                    BpmProcessInstanceResultEnum.BACK.getResult().toString(), reqVO.getReason());
        });

        // 3. 执行驳回
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(currentTask.getProcessInstanceId())
                .moveActivityIdsToSingleActivityId(returnTaskKeyList, // 当前要跳转的节点列表( 1 或多)
                        reqVO.getTargetDefinitionKey()) // targetKey 跳转到的节点(1)
                .changeState();
    }

}
