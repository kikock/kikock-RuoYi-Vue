package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ObjectUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmProcessDefinitionExt;
import com.ruoyi.flowable.domain.definition.vo.BpmProcessDefinitionVo;
import com.ruoyi.flowable.domain.task.BpmProcessInstanceExt;
import com.ruoyi.flowable.domain.task.vo.BpmProcessInstancePageItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmProcessInstanceRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.enums.BpmProcessInstanceDeleteReasonEnum;
import com.ruoyi.flowable.mapper.task.BpmProcessInstanceExtMapper;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.flowable.service.task.impl.BpmTaskServiceImpl.parseLong;


@Service
public class BpmProcessInstanceServiceImpl implements IBpmProcessInstanceService {
    @Resource
    private BpmProcessInstanceExtMapper processInstanceExtMapper;
    @Resource
    private IBpmProcessDefinitionService processDefinitionService;
    @Resource
    private RuntimeService runtimeService;
    //    @Resource
//    @Lazy // 解决循环依赖
//    private IBpmTaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    @Lazy // 解决循环依赖
    private TaskService taskService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysDeptService deptService;
    @Override
    public List<?> getMyProcessInstancePage(BpmTaskReqVO pageReqVO) {
        // 通过 BpmProcessInstanceExtDO 表，先查询到对应的分页
        List<BpmProcessInstanceExt> list = processInstanceExtMapper.selectPage(pageReqVO);
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<String> processInstanceIds = list.stream().map(a -> a.getProcessInstanceId()).collect(Collectors.toList());
        // 获得流程 Task list
        List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
        // 转换返回map
        Map<String, List<Task>> taskMap = CollectionUtils.convertMultiMap(tasks, Task::getProcessInstanceId);
        List<BpmProcessInstancePageItemRespVO> list1 = new ArrayList<>(list.size());
        for (BpmProcessInstanceExt bean : list) {
            BpmProcessInstancePageItemRespVO bpmProcessInstancePageItemRespVO = new BpmProcessInstancePageItemRespVO();
            bpmProcessInstancePageItemRespVO.setId(bean.getProcessInstanceId());
            bpmProcessInstancePageItemRespVO.setName(bean.getName());
            bpmProcessInstancePageItemRespVO.setProcessDefinitionId(bean.getProcessDefinitionId());
            bpmProcessInstancePageItemRespVO.setCategory(bean.getCategory());
            bpmProcessInstancePageItemRespVO.setStatus(bean.getStatus());
            bpmProcessInstancePageItemRespVO.setResult(bean.getResult());
            bpmProcessInstancePageItemRespVO.setCreateTime(bean.getCreateTime());
            bpmProcessInstancePageItemRespVO.setEndTime(bean.getEndTime());
            list1.add(bpmProcessInstancePageItemRespVO);
        }
        list1.forEach(respVO -> respVO.setTasks(getTask((taskMap.get(respVO.getId())))));
        return list1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstancebyProcessDefinitionId(Long userId, BpmTaskReqVO createReqVO) {
        // 获得流程定义
        ProcessDefinition definition = processDefinitionService.getProcessDefinition(createReqVO.getProcessDefinitionId());
        // 发起流程
        return createProcessInstance0(userId, definition, createReqVO.getVariables(), null);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstanceByProcessDefinitionKey(Long userId, BpmTaskReqVO createReqVO) {
        // 获得流程定义
        ProcessDefinition definition = processDefinitionService.getActiveProcessDefinition(createReqVO.getProcessDefinitionKey());
        // 发起流程
        return createProcessInstance0(userId, definition, createReqVO.getVariables(), createReqVO.getBusinessKey());
    }
    @Override
    public BpmProcessInstanceRespVO getProcessInstanceVO(String id) {
        // 获得流程实例
        HistoricProcessInstance processInstance = getHistoricProcessInstance(id);
        if (processInstance == null) {
            return null;
        }
        BpmProcessInstanceExt processInstanceExt = processInstanceExtMapper.selectBpmProcessInstanceExtByProcessDefinitionId(id);
        Assert.notNull(processInstanceExt, "流程实例拓展({}) 不存在", id);

        // 获得流程定义
        ProcessDefinition processDefinition = processDefinitionService
                .getProcessDefinition(processInstance.getProcessDefinitionId());
        Assert.notNull(processDefinition, "流程定义({}) 不存在", processInstance.getProcessDefinitionId());
        BpmProcessDefinitionExt processDefinitionExt = processDefinitionService.getProcessDefinitionExt(
                processInstance.getProcessDefinitionId());
        Assert.notNull(processDefinitionExt, "流程定义拓展({}) 不存在", id);
        String bpmnXml = processDefinitionService.getProcessDefinitionBpmnXML(processInstance.getProcessDefinitionId());

        // 获得 User
        SysUser startUser = sysUserService.selectUserById(parseLong(processInstance.getStartUserId()));
        SysDept dept = null;
        if (startUser != null) {
            dept = deptService.selectDeptById(startUser.getDeptId());
        }
        // 拼接返回结果   processInstance 流程定义 processInstanceExt 流程定义扩展 processDefinition 实例信息 processDefinitionExt 实例扩展信息
        //          bpmnXml 流程 xml数据
        return convertToVo(processInstance, processInstanceExt,processDefinition,processDefinitionExt,bpmnXml,startUser,dept);

    }

    @Override
    public void cancelProcessInstance(Long userId, BpmTaskReqVO cancelReqVO) {
        // 校验流程实例存在
        ProcessInstance instance = getProcessInstance(cancelReqVO.getId());
        if (instance == null) {
            return;
//            throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS);
        }
        // 只能取消自己的
        if (!Objects.equals(instance.getStartUserId(), String.valueOf(userId))) {
            return;
//          throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_SELF);
        }

        // 通过删除流程实例，实现流程实例的取消,
        // 删除流程实例，正则执行任务 ACT_RU_TASK. 任务会被删除。通过历史表查询
        runtimeService.deleteProcessInstance(cancelReqVO.getId(), BpmProcessInstanceDeleteReasonEnum.CANCEL_TASK.format(cancelReqVO.getReason()));
    }

    public BpmProcessInstanceRespVO convertToVo(HistoricProcessInstance processInstance, BpmProcessInstanceExt processInstanceExt,
                                             ProcessDefinition processDefinition, BpmProcessDefinitionExt processDefinitionExt,
                                             String bpmnXml, SysUser startUser, SysDept dept) {
        BpmProcessInstanceRespVO processInstanceRespVO = new BpmProcessInstanceRespVO();
//        processInstance 流程定义设置  processDefinitionExt 实例扩展信息
        //          bpmnXml 流程 xml数据
        if ( Objects.nonNull(processInstance)) {
            processInstanceRespVO.setId( processInstance.getId() );
            processInstanceRespVO.setName( processInstance.getName() );
            processInstanceRespVO.setEndTime( processInstance.getEndTime());
            processInstanceRespVO.setBusinessKey( processInstance.getBusinessKey() );
        }
//        processInstanceExt 流程定义扩展设置
        if ( Objects.nonNull(processInstanceExt)) {
            processInstanceRespVO.setName(processInstanceExt.getName());
            processInstanceRespVO.setCategory(processInstanceExt.getCategory());
            processInstanceRespVO.setStatus(processInstanceExt.getStatus());
            processInstanceRespVO.setResult(processInstanceExt.getResult());
            processInstanceRespVO.setCreateTime(processInstanceExt.getCreateTime());
            processInstanceRespVO.setEndTime(processInstanceExt.getEndTime());
            if (processInstanceRespVO.getFormVariables() != null) {
                String formVariables = processInstanceExt.getFormVariables();
                if (formVariables != null) {
                    processInstanceRespVO.setFormVariables(formVariables);
                } else {
                    processInstanceRespVO.setFormVariables(null);
                }
            } else {
                String map = processInstanceExt.getFormVariables();
                if (map != null) {
                    processInstanceRespVO.setFormVariables(map);
                }
            }
        }

//        processDefinition 实例信息设置

        BpmProcessInstanceRespVO.ProcessDefinition processDefinitionVo = new BpmProcessInstanceRespVO.ProcessDefinition();
        if(Objects.nonNull(processDefinition)){
            processDefinitionVo.setId(processDefinition.getId());
            processDefinitionVo.setBpmnXml(bpmnXml);
        }
        processInstanceRespVO.setProcessDefinition(processDefinitionVo);
        //用户信息
        BpmProcessInstanceRespVO.User user = new BpmProcessInstanceRespVO.User();

        if(Objects.nonNull(startUser)){
            user.setId( startUser.getUserId() );
            user.setNickname( startUser.getNickName() );
            user.setDeptId( startUser.getDeptId() );
        }
        processInstanceRespVO.setStartUser(user);
        return processInstanceRespVO;
    }

    public BpmProcessInstanceRespVO.User convertToUser(SysUser bean) {
        if ( bean == null ) {
            return null;
        }

        BpmProcessInstanceRespVO.User user = new BpmProcessInstanceRespVO.User();

        user.setId( bean.getUserId() );
        user.setNickname( bean.getNickName() );
        user.setDeptId( bean.getDeptId() );
        return user;
    }


    @Override
    public ProcessInstance getProcessInstance(String id) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
    }

    @Override
    public List<ProcessInstance> getProcessInstances(Set<String> ids) {
        return runtimeService.createProcessInstanceQuery().processInstanceIds(ids).list();
    }

    @Override
    public List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceIds(ids).list();
    }

    @Override
    public void createProcessInstanceExt(ProcessInstance instance) {

    }

    @Override
    public void updateProcessInstanceExtCancel(FlowableCancelledEvent event) {

    }

    @Override
    public void updateProcessInstanceExtComplete(ProcessInstance instance) {

    }

    @Override
    public void updateProcessInstanceExtReject(String id, String reason) {

    }

    private static List<BpmProcessInstancePageItemRespVO.Task> getTask(List<Task> tasks) {
        List<BpmProcessInstancePageItemRespVO.Task> listTask = new ArrayList<BpmProcessInstancePageItemRespVO.Task>(tasks.size());
        for (Task task : tasks) {
            BpmProcessInstancePageItemRespVO.Task task1 = new BpmProcessInstancePageItemRespVO.Task();
            task1.setId(task.getId());
            task1.setName(task.getName());
            listTask.add(task1);
        }
        return listTask;
    }

    private String createProcessInstance0(Long userId, ProcessDefinition definition, Map<String, Object> variables, String businessKey) {
        // 校验流程定义
        if (definition == null) {
            throw new ServiceException("流程定义不存在!", HttpStatus.ERROR) ;
        }
        if (definition.isSuspended()) {
            throw new ServiceException("流程定义处于挂起状态!", HttpStatus.ERROR) ;
        }
        // 创建流程实例
        ProcessInstance instance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionId(definition.getId())
                .businessKey(businessKey)
                .name(definition.getName().trim())
                .variables(variables)
                .start();
        // 设置流程名字
        runtimeService.setProcessInstanceName(instance.getId(), definition.getName());
        // 补全流程实例的拓展表
        BpmProcessInstanceExt bpmProcessInstanceExt = new BpmProcessInstanceExt();
        bpmProcessInstanceExt.setStartUserId(userId);
        bpmProcessInstanceExt.setCategory(definition.getCategory());
        bpmProcessInstanceExt.setBusinessKey(businessKey);
        bpmProcessInstanceExt.setProcessDefinitionId(definition.getId());
        bpmProcessInstanceExt.setName(definition.getName());
        bpmProcessInstanceExt.setProcessInstanceId(instance.getId());
        bpmProcessInstanceExt.setFormVariables(variables.toString());
//        1 处理中 2 通过 3 不通过 4 已取消
        bpmProcessInstanceExt.setResult(1);
//        1 进行 2已完成
        bpmProcessInstanceExt.setStatus(1);
        processInstanceExtMapper.insertBpmProcessInstanceExt(bpmProcessInstanceExt);
        return instance.getId();
    }

    @Override
    public HistoricProcessInstance getHistoricProcessInstance(String id) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    }

}
