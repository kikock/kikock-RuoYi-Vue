package com.ruoyi.web.controller.flowable;


import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 流程任务实例")
@RestController
@RequestMapping("/bpm/task")
@Validated
public class BpmTaskController {
    @Resource
    private IBpmTaskService bpmTaskService;


    @GetMapping("todo-page")
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public TableDataInfo  todoList(){
        return null;
    }
}
