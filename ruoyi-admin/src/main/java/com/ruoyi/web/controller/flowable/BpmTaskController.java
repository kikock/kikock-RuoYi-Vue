package com.ruoyi.web.controller.flowable;


import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 流程任务实例")
@RestController
@RequestMapping("/bpm/task")
@Validated
@Anonymous
public class BpmTaskController extends BaseController{
    @Resource
    private IBpmTaskService bpmTaskService;


    // 获取我的代办任务
    @GetMapping("todo-page")
//    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    @Anonymous
    public TableDataInfo taskToDoList(BpmTaskReqVO pageReqVO){
        startPage();
        return getDataTable(bpmTaskService.getToDotask(getUserId(), pageReqVO));
    }

    // 获取我的已办任务
    @GetMapping("done-page")
    @Anonymous
    public TableDataInfo taskDoneList(BpmTaskReqVO pageReqVO){
        startPage();
        return getDataTable(bpmTaskService.getDonetask(getUserId(), pageReqVO));
    }

    @GetMapping("/list-by-process-instance-id")
    public AjaxResult getTaskListByProcessInstanceId(
            @RequestParam("processInstanceId") String processInstanceId){
        return success(bpmTaskService.getTaskListByProcessInstanceId(processInstanceId));
    }

    /**
     * 通过任务 【通过】按钮
     */
    @PutMapping("/approve")
    public AjaxResult approveTask(@RequestBody BpmTaskReqVO reqVO){
        bpmTaskService.approveTask(reqVO);
        return success(true);
    }

    /**
     * 通过任务 【不通过】按钮
     */
    @PutMapping("/reject")
    public AjaxResult rejectTask(@RequestBody BpmTaskReqVO reqVO){
        bpmTaskService.rejectTask(reqVO);
        return success(true);
    }

    /**
     * 将流程任务分配给指定用户 【转派】按钮
     */
    @PutMapping("/update-assignee")
    public AjaxResult updateTaskAssignee(@RequestBody BpmTaskReqVO reqVO){
        bpmTaskService.updateTaskAssignee(reqVO);
        return success(true);
    }

    /**
     * 获取所有可回退的节点
     */
    @GetMapping("/get-return-list")
    public AjaxResult getReturnList(@RequestParam("taskId") String taskId){
        return success().put("data", bpmTaskService.getReturnTaskList(taskId));
    }

    /**
     * 将任务回退到指定的 【回退】按钮
     */
    @PutMapping("/doBackStep")
    public AjaxResult returnTask(@RequestBody BpmTaskReqVO reqVO){
        bpmTaskService.returnTask(reqVO);
        return success(true);
    }
    /**
     * 委派任务【委派】按钮
     *
     * @return
     */
    @PutMapping(value = "/delegate")
    public AjaxResult delegate(@RequestBody BpmTaskReqVO reqVO) {
        bpmTaskService.delegate(reqVO);
        return success(true);
    }

    /**
     * 终止流程【终止】按钮
     *
     * @return
     */
    @PutMapping(value = "/stopProcess")
    public AjaxResult stopProcess(@RequestBody BpmTaskReqVO reqVO) {
        bpmTaskService.stopProcess(reqVO);
        return success(true);
    }

}
