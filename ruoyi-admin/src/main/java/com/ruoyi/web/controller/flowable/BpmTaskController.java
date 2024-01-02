package com.ruoyi.web.controller.flowable;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.definition.BpmUserGroup;
import com.ruoyi.flowable.domain.task.vo.BpmTaskTodoPageReqVO;
import com.ruoyi.flowable.service.task.IBpmTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.utils.PageUtils.startPage;

@RestController
@RequestMapping("/bpm/task")
@Validated
public class BpmTaskController extends BaseController {
    @Resource
    private IBpmTaskService bpmTaskService;


    // 获取我的代办任务
    @GetMapping("todo-page")
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public TableDataInfo taskToDoList(BpmTaskTodoPageReqVO pageReqVO){
        startPage();
        return getDataTable(bpmTaskService.getToDotask(getUserId(),pageReqVO));
    }
}
