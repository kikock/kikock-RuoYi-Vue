package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.task.BpmProcessInstanceExt;
import com.ruoyi.flowable.domain.task.vo.BpmProcessInstancePageItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskPageReqVO;
import com.ruoyi.flowable.mapper.task.BpmProcessInstanceExtMapper;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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
    @Override
    public List<?> getMyProcessInstancePage(BpmTaskPageReqVO pageReqVO) {
        if(pageReqVO.getCreateTime() != null){
            pageReqVO.setStarTime(pageReqVO.getCreateTime()[0]);
            pageReqVO.setEndTime(pageReqVO.getCreateTime()[1]);
        }
        // 通过 BpmProcessInstanceExtDO 表，先查询到对应的分页
        List<BpmProcessInstanceExt> list=processInstanceExtMapper.selectPage(pageReqVO);
        if (CollUtil.isEmpty(list)) {
            return  new ArrayList<>();
        }
        List<String> processInstanceIds = list.stream().map(a -> a.getProcessDefinitionId()).collect(Collectors.toList());
        // 获得流程 Task list
        List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
        // 转换返回map
        Map<String, List<Task>> taskMap = CollectionUtils.convertMultiMap(tasks, Task::getProcessInstanceId);
        List<BpmProcessInstancePageItemRespVO> list1 =new ArrayList<>(list.size());
        for ( BpmProcessInstanceExt bean : list ) {
            BpmProcessInstancePageItemRespVO bpmProcessInstancePageItemRespVO = new BpmProcessInstancePageItemRespVO();
            bpmProcessInstancePageItemRespVO.setId( bean.getProcessInstanceId() );
            bpmProcessInstancePageItemRespVO.setName( bean.getName() );
            bpmProcessInstancePageItemRespVO.setProcessDefinitionId( bean.getProcessDefinitionId() );
            bpmProcessInstancePageItemRespVO.setCategory( bean.getCategory() );
            bpmProcessInstancePageItemRespVO.setStatus( bean.getStatus() );
            bpmProcessInstancePageItemRespVO.setResult( bean.getResult() );
            bpmProcessInstancePageItemRespVO.setCreateTime( bean.getCreateTime() );
            bpmProcessInstancePageItemRespVO.setEndTime( bean.getEndTime() );
            list1.add( bpmProcessInstancePageItemRespVO );
        }
        list1.forEach(respVO -> respVO.setTasks(getTask((taskMap.get(respVO.getId())))));
        return list1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstance(Long userId, BpmTaskPageReqVO createReqVO) {
        // 获得流程定义
        ProcessDefinition definition = processDefinitionService.getProcessDefinition(createReqVO.getProcessDefinitionId());
        // 发起流程
        return createProcessInstance0(userId, definition, createReqVO.getVariables(), null);
    }

    @Override
    public List<ProcessInstance> getProcessInstances(Set<String> ids) {
        return runtimeService.createProcessInstanceQuery().processInstanceIds(ids).list();
    }

    @Override
    public List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceIds(ids).list();
    }

    private static List<BpmProcessInstancePageItemRespVO.Task> getTask(List<Task> tasks) {
        List<BpmProcessInstancePageItemRespVO.Task> listTask = new ArrayList<BpmProcessInstancePageItemRespVO.Task>( tasks.size() );
        for ( Task task : tasks) {
            BpmProcessInstancePageItemRespVO.Task task1 = new BpmProcessInstancePageItemRespVO.Task();
            task1.setId( task.getId() );
            task1.setName( task.getName() );
            listTask.add( task1);
        }
        return listTask;
    }
    private String createProcessInstance0(Long userId, ProcessDefinition definition,
                                          Map<String, Object> variables, String businessKey) {
        // 校验流程定义
        if (definition == null) {
//            throw exception(PROCESS_DEFINITION_NOT_EXISTS);
            return "流程定义不存在";
        }
        if (definition.isSuspended()) {
//            throw exception(PROCESS_DEFINITION_IS_SUSPENDED);
            return "流程定义处于挂起状态";
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
        bpmProcessInstanceExt.setProcessInstanceId(instance.getId());
        bpmProcessInstanceExt.setFormVariables(variables.toString());
        processInstanceExtMapper.updateBpmProcessInstanceExt(bpmProcessInstanceExt);
        return instance.getId();
    }

}
