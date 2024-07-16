package com.ruoyi.web.controller.workflow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.excel.ExcelUtils;
import com.ruoyi.flowable.core.domain.ProcessQuery;
import com.ruoyi.workflow.domain.WfCopy;
import com.ruoyi.workflow.domain.vo.*;
import com.ruoyi.workflow.service.IWfCopyService;
import com.ruoyi.workflow.service.IWfProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 工作流流程管理
 *
 * @author KonBAI
 * @createTime 2022/3/24 18:54
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/process")
@Tag(name = "流程管理")
public class WfProcessController extends BaseController{

    private final IWfProcessService processService;
    private final IWfCopyService copyService;

    /**
     * 查询可发起流程列表
     */
    @GetMapping(value = "/list")
    @PreAuthorize("@ss.hasPermi('workflow:process:startList')")
    @Operation(summary ="查询可发起流程列表")
    public TableDataInfo startProcessList(ProcessQuery processQuery){
        startPage();
        List<WfDefinitionVo> list = processService.selectPageStartProcessList(processQuery);
        return getDataTable(list);
    }

    /**
     * 我拥有的流程
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:ownList')")
    @GetMapping(value = "/ownList")
    @Operation(summary ="获取我的流程")
    public TableDataInfo ownProcessList(ProcessQuery processQuery){
        startPage();
        List<WfTaskVo> list = processService.selectPageOwnProcessList(processQuery);
        return getDataTable(list);
    }

    /**
     * 获取待办列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:todoList')")
    @GetMapping(value = "/todoList")
    @Operation(summary ="获取待办流程")
    public TableDataInfo todoProcessList(ProcessQuery processQuery){
        startPage();
        List<WfTaskVo> list = processService.selectPageTodoProcessList(processQuery);
        return getDataTable(list);
    }

    /**
     * 获取待签列表
     *
     * @param processQuery 流程业务对象
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:claimList')")
    @GetMapping(value = "/claimList")
    @Operation(summary ="获取待签流程")
    public TableDataInfo claimProcessList(ProcessQuery processQuery){
        startPage();
        List<WfTaskVo> list = processService.selectPageClaimProcessList(processQuery);
        return getDataTable(list);
    }

    /**
     * 获取已办列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:finishedList')")
    @GetMapping(value = "/finishedList")
    @Operation(summary ="获取已办流程")
    public TableDataInfo finishedProcessList(ProcessQuery processQuery){
        startPage();
        List<WfTaskVo> list = processService.selectPageFinishedProcessList(processQuery);
        return getDataTable(list);
    }

    /**
     * 获取抄送列表
     *
     * @param copyBo 流程抄送对象
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:copyList')")
    @GetMapping(value = "/copyList")
    @Operation(summary ="获取抄送流程")
    public TableDataInfo copyProcessList(WfCopy copyBo){
        startPage();
        copyBo.setUserId(getUserId());
        List<WfCopy> list = copyService.selectWfCopyList(copyBo);
        return getDataTable(list);
    }

    /**
     * 导出可发起流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:startExport')")
    @Log(title = "可发起流程", businessType = BusinessType.EXPORT)
    @PostMapping("/startExport")
    @Operation(summary ="导出可发起流程列表")
    public void startExport(@Validated ProcessQuery processQuery, HttpServletResponse response){
        List<WfDefinitionVo> list = processService.selectStartProcessList(processQuery);
        ExcelUtils.exportExcel(list, "可发起流程", WfDefinitionVo.class, response);
    }

    /**
     * 导出我拥有流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:ownExport')")
    @Log(title = "我拥有流程", businessType = BusinessType.EXPORT)
    @PostMapping("/ownExport")
    @Operation(summary ="导出我拥有流程列表")
    public void ownExport(@Validated ProcessQuery processQuery, HttpServletResponse response){
        List<WfTaskVo> list = processService.selectOwnProcessList(processQuery);
        List<WfOwnTaskExportVo> listVo = BeanUtil.copyToList(list, WfOwnTaskExportVo.class);
        for (WfOwnTaskExportVo exportVo : listVo) {
            exportVo.setStatus(ObjectUtil.isNull(exportVo.getFinishTime()) ? "进行中" : "已完成");
        }
        ExcelUtils.exportExcel(listVo, "我拥有流程", WfOwnTaskExportVo.class, response);
    }

    /**
     * 导出待办流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:todoExport')")
    @Log(title = "待办流程", businessType = BusinessType.EXPORT)
    @PostMapping("/todoExport")
    @Operation(summary ="导出待办流程列表")
    public void todoExport(@Validated ProcessQuery processQuery, HttpServletResponse response){
        List<WfTaskVo> list = processService.selectTodoProcessList(processQuery);
        List<WfTodoTaskExportVo> listVo = BeanUtil.copyToList(list, WfTodoTaskExportVo.class);
        ExcelUtils.exportExcel(listVo, "待办流程", WfTodoTaskExportVo.class, response);
    }

    /**
     * 导出待签流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:claimExport')")
    @Log(title = "待签流程", businessType = BusinessType.EXPORT)
    @PostMapping("/claimExport")
    @Operation(summary ="导出待签流程列表")
    public void claimExport(@Validated ProcessQuery processQuery, HttpServletResponse response){
        List<WfTaskVo> list = processService.selectClaimProcessList(processQuery);
        List<WfClaimTaskExportVo> listVo = BeanUtil.copyToList(list, WfClaimTaskExportVo.class);
        ExcelUtils.exportExcel(listVo, "待签流程", WfClaimTaskExportVo.class, response);
    }

    /**
     * 导出已办流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:finishedExport')")
    @Log(title = "已办流程", businessType = BusinessType.EXPORT)
    @PostMapping("/finishedExport")
    @Operation(summary ="导出已办流程列表")
    public void finishedExport(@Validated ProcessQuery processQuery, HttpServletResponse response){
        List<WfTaskVo> list = processService.selectFinishedProcessList(processQuery);
        List<WfFinishedTaskExportVo> listVo = BeanUtil.copyToList(list, WfFinishedTaskExportVo.class);
        ExcelUtils.exportExcel(listVo, "已办流程", WfFinishedTaskExportVo.class, response);
    }

    /**
     * 导出抄送流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:copyExport')")
    @Log(title = "抄送流程", businessType = BusinessType.EXPORT)
    @PostMapping("/copyExport")
    @Operation(summary ="导出抄送流程列表")
    public void copyExport(WfCopy copyBo, HttpServletResponse response){
        copyBo.setUserId(getUserId());
        List<WfCopy> list = copyService.selectWfCopyList(copyBo);
        ExcelUtils.exportExcel(list, "抄送流程", WfCopy.class, response);
    }

    /**
     * 查询流程部署关联表单信息 (废弃)
     *
     * @param definitionId 流程定义id
     * @param deployId     流程部署id
     */
    @GetMapping("/getProcessForm2")
    @Operation(summary ="查询流程部署关联表单信息(废弃)")
    @PreAuthorize("@ss.hasPermi('workflow:process:start')")
    public R<?> getForm2(@RequestParam(value = "definitionId") String definitionId,
                        @RequestParam(value = "deployId") String deployId,
                        @RequestParam(value = "procInsId", required = false) String procInsId){
        return R.ok(processService.selectFormContent(definitionId, deployId, procInsId));
    }
    /**
     * 查询流程部署关联表单信息
     *
     * @param definitionId 流程定义id
     * @param deployId     流程部署id
     * @param procInsId     流程实例ID
     *
     */
    @GetMapping("/getProcessForm")
    @PreAuthorize("@ss.hasPermi('workflow:process:start')")
    @Operation(summary ="查询流程部署关联表单信息")
    @Parameters({
            @Parameter(name = "definitionId", description = "流程定义id", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "deployId", description = "流程部署id", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "procInsId", description = "流程实例ID", required = false, in = ParameterIn.QUERY)
    })
    public R<?> getForm(@RequestParam(value = "definitionId") String definitionId,
                        @RequestParam(value = "deployId") String deployId,
                        @RequestParam(value = "procInsId", required = false) String procInsId){
        return R.ok(processService.selectDeployFormContent(definitionId, deployId, procInsId));
    }
    /**
     * 根据流程定义id启动流程实例
     *
     * @param processDefId 流程定义id
     * @param variables    变量集合,json对象
     */
    @PreAuthorize("@ss.hasPermi('workflow:process:start')")
    @PostMapping("/start/{processDefId}")
    @Operation(summary ="根据流程定义id启动流程实例")
    @Parameters({
            @Parameter(name = "processDefId", description = "流程定义id", required = true, in = ParameterIn.PATH),
            @Parameter(name = "variables", description = "变量集合,json对象", required = true, in = ParameterIn.DEFAULT)
    })
    public R<Void> start(@PathVariable(value = "processDefId") String processDefId, @RequestBody Map<String,Object> variables){
        processService.startProcessByDefId(processDefId, variables);
        return R.ok();

    }

    /**
     * 删除流程实例
     *
     * @param instanceIds 流程实例ID串
     */
    @DeleteMapping("/instance/{instanceIds}")
    @PreAuthorize("@ss.hasPermi('workflow:process:remove')")
    @Operation(summary ="删除流程实例")
    @Parameters({
            @Parameter(name = "instanceIds", description = "流程实例ID串", required = true, in = ParameterIn.PATH)
    })
    public R<Void> delete(@PathVariable String[] instanceIds){
        processService.deleteProcessByIds(instanceIds);
        return R.ok();
    }

    /**
     * 读取xml文件
     *
     * @param processDefId 流程定义ID
     */
    @GetMapping("/bpmnXml/{processDefId}")
    @Operation(summary ="读取xml文件")
    @Parameters({
            @Parameter(name = "processDefId", description = "流程定义ID", required = true, in = ParameterIn.PATH)
    })
    public AjaxResult getBpmnXml(@PathVariable(value = "processDefId") String processDefId){
        AjaxResult result = AjaxResult.success();
        result.put("bpmnXml", processService.queryBpmnXmlById(processDefId));
        return result;
    }
    /**
     * 查询流程详情信息
     *
     * @param procInsId 流程实例ID
     * @param taskId    任务ID
     */
    @GetMapping("/detail")
    @Operation(summary ="查询流程详情信息")
    @Parameters({
            @Parameter(name = "procInsId", description = "流程实例ID", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "taskId", description = "任务ID", required = true, in = ParameterIn.QUERY)
    })
    public R detail(String procInsId, String taskId){
        return R.ok(processService.queryProcessDetail(procInsId, taskId));
    }
}
