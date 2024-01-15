package com.ruoyi.flowable.service.task;

import com.ruoyi.flowable.domain.definition.vo.BpmModelVo;
import com.ruoyi.flowable.domain.task.vo.BpmTaskItemRespVO;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import org.flowable.task.api.Task;

import javax.validation.Valid;
import java.util.List;

public interface IBpmTaskService {

    /**
     * 获得待办的流程任务分页
     *
     * @param userId    用户编号
     * @param pageReqVO 分页请求
     * @return 流程任务分页
     */
    List<?> getToDotask(Long userId, BpmTaskReqVO pageReqVO);

        /**
     * 获得已办的流程任务分页
     *
     * @param userId    用户编号
     * @param pageReqVO 分页请求
     * @return 流程任务分页
     */
    List<?> getDonetask(Long userId, BpmTaskReqVO pageReqVO);

    /**
     * 获得流程任务列表
     *
     * @param processInstanceIds 流程实例的编号数组
     * @return 流程任务列表
     */
    List<Task> getTasksByProcessInstanceIds(List<String> processInstanceIds);

    /**
     * 获得指令流程实例的流程任务列表，包括所有状态的
     *
     * @param processInstanceId 流程实例的编号
     * @return 流程任务列表
     */
    List<BpmTaskItemRespVO> getTaskListByProcessInstanceId(String processInstanceId);


    /**
     * 创建 Task 拓展记录
     *
     * @param task 任务实体
     */
    void createTaskExt(Task task);

    /**
     * 更新 Task 拓展记录为完成
     *
     * @param task 任务实体
     */
    void updateTaskExtComplete(Task task);

    /**
     * 更新 Task 拓展记录为已取消
     *
     * @param taskId 任务的编号
     */
    void updateTaskExtCancel(String taskId);

    /**
     * 更新 Task 拓展记录，并发送通知
     *
     * @param task 任务实体
     */
    void updateTaskExtAssign(Task task);

    /**
     * 通过任务
     *
     * @param reqVO  通过请求
     */
    void approveTask(BpmTaskReqVO reqVO);

    /**
     * 不通过任务
     *
     * @param reqVO  不通过请求
     */
    void rejectTask( BpmTaskReqVO reqVO);
    /**
     * 将流程任务分配给指定用户
     *
     * @param reqVO  分配请求
     */
    void updateTaskAssignee(BpmTaskReqVO reqVO);
    /**
     * 获取当前任务的可回退的流程集合
     *
     * @param taskId 当前的任务 ID
     * @return 可以回退的节点列表
     */
    List<BpmModelVo> getReturnTaskList(String taskId);

    /**
     * 将任务回退到指定的 targetDefinitionKey 位置
     *
     * @param reqVO 回退的任务key和当前所在的任务ID
     */
    void returnTask(BpmTaskReqVO reqVO);
    /**
     * 终止任务
     *
     * @param reqVO  不通过请求
     */
    void stopProcess(BpmTaskReqVO reqVO);
    /**
     * 委派任务
     *
     * @param reqVO  不通过请求
     */
    void delegate(BpmTaskReqVO reqVO);
}
