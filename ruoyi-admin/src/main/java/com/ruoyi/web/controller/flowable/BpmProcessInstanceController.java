package com.ruoyi.web.controller.flowable;


import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.task.vo.BpmTaskPageReqVO;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@Tag(name = "管理后台 - 流程实例")
@RestController
@RequestMapping("/bpm/process-instance")
@Validated
public class BpmProcessInstanceController extends BaseController {

    @Resource
    private IBpmProcessInstanceService processInstanceService;

    @GetMapping("/my-page")
    @Operation(summary = "获得我的实例分页列表", description = "在【我的流程】菜单中，进行调用")
//    @PreAuthorize("@ss.hasPermission('bpm:process:list')")
    @Anonymous
    public TableDataInfo getMyProcessInstancePage(@Valid BpmTaskPageReqVO pageReqVO) {
        startPage();
        pageReqVO.setUserId(getUserId());
        return getDataTable(processInstanceService.getMyProcessInstancePage(pageReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "新建流程实例")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance:query')")
    public AjaxResult createProcessInstance(@Valid @RequestBody BpmTaskPageReqVO createReqVO) {
        return success(processInstanceService.createProcessInstance(getUserId(), createReqVO));
    }

//    @GetMapping("/get")
//    @Operation(summary = "获得指定流程实例", description = "在【流程详细】界面中，进行调用")
//    @Parameter(name = "id", description = "流程实例的编号", required = true)
//    @PreAuthorize("@ss.hasPermission('bpm:process-instance:query')")
//    public CommonResult<BpmProcessInstanceRespVO> getProcessInstance(@RequestParam("id") String id) {
//        return success(processInstanceService.getProcessInstanceVO(id));
//    }
//
//    @DeleteMapping("/cancel")
//    @Operation(summary = "取消流程实例", description = "撤回发起的流程")
//    @PreAuthorize("@ss.hasPermission('bpm:process-instance:cancel')")
//    public CommonResult<Boolean> cancelProcessInstance(@Valid @RequestBody BpmProcessInstanceCancelReqVO cancelReqVO) {
//        processInstanceService.cancelProcessInstance(getLoginUserId(), cancelReqVO);
//        return success(true);
//    }
}
