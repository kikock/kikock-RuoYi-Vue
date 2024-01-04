package com.ruoyi.web.controller.flowable;


import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.definition.vo.BpmProcessDefinitionVo;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Tag(name = "管理后台 - 流程定义")
@RestController
@RequestMapping("/bpm/process-definition")
@Validated
public class BpmProcessDefinitionController extends BaseController {

    @Resource
    private IBpmProcessDefinitionService bpmDefinitionService;

//    @GetMapping("/page")
//    @Operation(summary = "获得流程定义分页")
//    @PreAuthorize("@ss.hasPermission('bpm:process-definition:query')")
//    @Anonymous
//    public TableDataInfo getProcessDefinitionPage(BpmProcessDefinitionVo pageReqVO) {
//        startPage();
//        return getDataTable(bpmDefinitionService.getProcessDefinitionList(pageReqVO));
//    }

    @GetMapping ("/list")
    @Operation(summary = "获得流程定义列表")
//    @PreAuthorize("@ss.hasPermission('bpm:process-definition:query')")
    @Anonymous
    public AjaxResult getProcessDefinitionList(BpmProcessDefinitionVo listReqVO) {
        return success(bpmDefinitionService.getProcessDefinitionList(listReqVO));
    }
    @GetMapping ("/get-bpmn-xml")
    @Operation(summary = "获得流程定义的 BPMN XML")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('bpm:process-definition:query')")
    @Anonymous
    public AjaxResult getProcessDefinitionBpmnXML(@RequestParam("id") String id) {
        String bpmnXML = bpmDefinitionService.getProcessDefinitionBpmnXML(id);
        return success(bpmnXML);
    }
}
