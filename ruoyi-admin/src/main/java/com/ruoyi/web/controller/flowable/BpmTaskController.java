package com.ruoyi.web.controller.flowable;


import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 流程任务实例")
@RestController
@RequestMapping("/bpm/task")
@Validated
@Anonymous
public class BpmTaskController extends BaseController {
    @Resource
    private IBpmTaskService bpmTaskService;


    // 获取我的代办任务
    @GetMapping("todo-page")
//    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    @Anonymous
    public TableDataInfo taskToDoList(BpmTaskReqVO pageReqVO){
        startPage();
        return getDataTable(bpmTaskService.getToDotask(getUserId(),pageReqVO));
    }
    // 获取我的已办任务
    @GetMapping("done-page")
//    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    @Anonymous
    public TableDataInfo taskDoneList(BpmTaskReqVO pageReqVO){
        startPage();
        return getDataTable(bpmTaskService.getDonetask(getUserId(),pageReqVO));
    }
    @GetMapping("/list-by-process-instance-id")
    @Operation(summary = "获得指定流程实例的任务列表", description = "包括完成的、未完成的")
    @Parameter(name = "processInstanceId", description = "流程实例的编号", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public AjaxResult getTaskListByProcessInstanceId(
            @RequestParam("processInstanceId") String processInstanceId) {
        return success(bpmTaskService.getTaskListByProcessInstanceId(processInstanceId));
    }
//
//    @PutMapping("/approve")
//    @Operation(summary = "通过任务")
//    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
//    public CommonResult<Boolean> approveTask(@Valid @RequestBody BpmTaskApproveReqVO reqVO) {
//        taskService.approveTask(getLoginUserId(), reqVO);
//        return success(true);
//    }
//
//    @PutMapping("/reject")
//    @Operation(summary = "不通过任务")
//    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
//    public CommonResult<Boolean> rejectTask(@Valid @RequestBody BpmTaskRejectReqVO reqVO) {
//        taskService.rejectTask(getLoginUserId(), reqVO);
//        return success(true);
//    }
//
//    @PutMapping("/update-assignee")
//    @Operation(summary = "更新任务的负责人", description = "用于【流程详情】的【转派】按钮")
//    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
//    public CommonResult<Boolean> updateTaskAssignee(@Valid @RequestBody BpmTaskUpdateAssigneeReqVO reqVO) {
//        taskService.updateTaskAssignee(getLoginUserId(), reqVO);
//        return success(true);
//    }
//
//    @GetMapping("/get-return-list")
//    @Operation(summary = "获取所有可回退的节点", description = "用于【流程详情】的【回退】按钮")
//    @Parameter(name = "taskId", description = "当前任务ID", required = true)
//    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
//    public CommonResult<List<BpmTaskSimpleRespVO>> getReturnList(@RequestParam("taskId") String taskId) {
//        return success(taskService.getReturnTaskList(taskId));
//    }
//
//    @PutMapping("/return")
//    @Operation(summary = "回退任务", description = "用于【流程详情】的【回退】按钮")
//    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
//    public CommonResult<Boolean> returnTask(@Valid @RequestBody BpmTaskReturnReqVO reqVO) {
//        taskService.returnTask(reqVO);
//        return success(true);
//    }

}
