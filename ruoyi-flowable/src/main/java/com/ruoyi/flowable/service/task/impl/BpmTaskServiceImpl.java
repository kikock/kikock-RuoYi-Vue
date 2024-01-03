package com.ruoyi.flowable.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.task.vo.BpmTaskPageReqVO;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import liquibase.pro.packaged.T;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;

import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class BpmTaskServiceImpl implements IBpmTaskService {
    @Resource
    private TaskService taskService;
//    @Resource
//    private BpmProcessInstanceService processInstanceService;
    @Override
    public List<T> getToDotask(Long userId, BpmTaskPageReqVO pageVO) {
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

//        // 获得 ProcessInstance Map
//        Map<String, ProcessInstance> processInstanceMap =processInstanceService.getProcessInstanceMap(convertSet(tasks, Task::getProcessInstanceId));
//        // 获得 User Map
//        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertSet(processInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId())));
        return null;
    }

    @Override
    public Map<String, List<Task>> getTaskMapByProcessInstanceIds(List<String> processInstanceIds) {
        return null;
    }
}
