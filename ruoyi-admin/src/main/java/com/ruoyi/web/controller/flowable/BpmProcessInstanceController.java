package com.ruoyi.web.controller.flowable;


import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.task.vo.BpmTaskReqVO;
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
@Anonymous
public class BpmProcessInstanceController extends BaseController{

    @Resource
    private IBpmProcessInstanceService processInstanceService;
    /**
     * 流程实例列表
     */
    @GetMapping("/my-page")
    @PreAuthorize("@ss.hasPermi('bpm:process:query')")
    @Anonymous
    public TableDataInfo getMyProcessInstancePage(@Valid BpmTaskReqVO pageReqVO){
        startPage();
        pageReqVO.setUserId(getUserId());
        return getDataTable(processInstanceService.getMyProcessInstancePage(pageReqVO));
    }
    /**
     * 新建流程实例
     */
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermi('bpm:process:query')")
    public AjaxResult createProcessInstance(@Valid @RequestBody BpmTaskReqVO createReqVO){
        return success(processInstanceService.createProcessInstancebyProcessDefinitionId(getUserId(), createReqVO));
    }
    /**
     * 查询流程实例
     */
    @GetMapping("/findById")
    @PreAuthorize("@ss.hasPermi('bpm:process:query')")
    public AjaxResult getProcessInstance(@RequestParam String id){
        return success(processInstanceService.getProcessInstanceVO(id));
    }

    /**
     * 取消流程实例
     */
    @DeleteMapping("/cancel")
    @PreAuthorize("@ss.hasPermi('bpm:process:cancel')")
    public AjaxResult cancelProcessInstance(@RequestBody BpmTaskReqVO cancelReqVO){
        processInstanceService.cancelProcessInstance(getUserId(), cancelReqVO);
        return success(true);
    }
}
