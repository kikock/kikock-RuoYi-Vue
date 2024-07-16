package com.ruoyi.web.controller.workflow;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.workflow.domain.bo.WfTaskBo;
import com.ruoyi.workflow.service.IWfInstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 工作流流程实例管理
 *
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/instance")
@Tag(name = "流程实例管理")
public class WfInstanceController {

    private final IWfInstanceService instanceService;

    /**
     * 激活或挂起流程实例
     *
     * @param state 1:激活,2:挂起
     * @param instanceId 流程实例ID
     */
    @PostMapping(value = "/updateState")
    @Operation(summary = "激活或挂起流程实例")
    @Parameters({
            @Parameter(name = "state", description = "状态（1:激活 2:挂起）", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "instanceId", description = "流程实例ID", required = true, in = ParameterIn.QUERY)
    })
    public R updateState(@RequestParam Integer state, @RequestParam String instanceId) {
        instanceService.updateState(state, instanceId);
        return R.ok();
    }

    /**
     * 结束流程实例
     *
     * @param bo 流程任务业务对象
     */
    @PostMapping(value = "/stopProcessInstance")
    @Operation(summary = "结束流程实例")
    public R stopProcessInstance(@RequestBody WfTaskBo bo) {
        instanceService.stopProcessInstance(bo);
        return R.ok();
    }

    /**
     * 删除流程实例
     *
     * @param instanceId 流程实例ID
     * @param deleteReason 删除原因
     */
    @Deprecated
    @DeleteMapping(value = "/delete")
    @Operation(summary = "删除流程实例")
    public R delete(@RequestParam String instanceId, String deleteReason) {
        instanceService.delete(instanceId, deleteReason);
        return R.ok();
    }

    /**
     * 查询流程实例详情信息
     *
     * @param procInsId 流程实例ID
     * @param deployId 流程部署ID
     */
    @GetMapping("/detail")
    @Operation(summary = "查询流程实例详情信息")
    @Parameters({
            @Parameter(name = "procInsId", description = "流程实例ID", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "deployId", description = "流程部署ID", required = true, in = ParameterIn.QUERY)
    })
    public R detail(String procInsId, String deployId) {
        return R.ok(instanceService.queryDetailProcess(procInsId, deployId));
    }
}
