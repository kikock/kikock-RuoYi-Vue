package com.ruoyi.web.controller.flowable;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.definition.BpmModel;
import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台 - 任务分配规则")
@RestController
@RequestMapping("/bpm/taskAssignRule")
@Validated
public class BpmTaskAssignRuleController  extends BaseController{

    @Resource
    private IBpmTaskAssignRuleService taskAssignRuleService;

    /**
     * 任务分配规则列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BpmTaskAssignRule BpmTaskAssignRule){
        List<BpmTaskAssignRule> list = taskAssignRuleService.selectBpmTaskAssignRuleList(BpmTaskAssignRule);
        return getDataTable(list);
    }
    @PostMapping("/create")
    @Operation(summary = "创建任务分配规则")
    @PreAuthorize("@ss.hasPermission('bpm:task-assign-rule:create')")
    public AjaxResult createTaskAssignRule(@Valid @RequestBody BpmTaskAssignRule reqVO) {
        return AjaxResult.success();
    }

    @PutMapping("/update")
    @Operation(summary = "更新任务分配规则")
    @PreAuthorize("@ss.hasPermission('bpm:task-assign-rule:update')")
    public AjaxResult updateTaskAssignRule(@Valid @RequestBody BpmTaskAssignRule reqVO) {
        return AjaxResult.success();
    }
}
