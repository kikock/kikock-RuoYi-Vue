package com.ruoyi.web.controller.workflow;


import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.vo.SelectMoreRequest;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.workflow.domain.WfDeployForm;
import com.ruoyi.workflow.domain.WfForm;
import com.ruoyi.workflow.domain.bo.WfFormBo;
import com.ruoyi.workflow.service.IWfDeployFormService;
import com.ruoyi.workflow.service.IWfFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 流程表单Controller
 *
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@RestController
@RequestMapping("/workflow/form")
@Tag(name = "流程表单管理")
public class WfFormController extends BaseController{
    @Autowired
    private IWfFormService formService;
    @Autowired
    private IWfDeployFormService deployFormService;

    /**
     * 查询流程表单列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:form:list')")
    @GetMapping("/list")
    @Operation(summary = "流程表单列表")
    public TableDataInfo list(@Validated(QueryGroup.class) WfFormBo bo){
        startPage();
        List<WfForm> list = formService.selectWfFormList(bo);
        return getDataTable(list);
    }

    /**
     * 导出流程表单列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:form:export')")
    @Log(title = "流程表单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出流程表单列表")
    public void export(@Validated WfFormBo bo, HttpServletResponse response){
        List<WfForm> list = formService.selectWfFormList(bo);
        ExcelUtil<WfForm> util = new ExcelUtil<WfForm>(WfForm.class);
        util.exportExcel(response, list, "流程表单");
    }

    /**
     * 获取流程表单详细信息
     *
     * @param formId 主键
     */
    @PreAuthorize("@ss.hasPermi('workflow:form:query')")
    @GetMapping(value = "/{formId}")
    @Operation(summary = "获取流程表单详细信息")
    public R<WfForm> getInfo(@NotNull(message = "主键不能为空") @PathVariable("formId") Long formId){
        return R.ok(formService.selectWfFormByFormId(formId));
    }


    /**
     * 新增流程单信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:form:add')")
    @Log(title = "流程单信息", businessType = BusinessType.INSERT)
    @PostMapping
    @Operation(summary = "新增流程单信息")
    public AjaxResult add(@RequestBody WfForm wfForm){
        return toAjax(formService.insertWfForm(wfForm));
    }


    /**
     * 修改流程单信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:form:edit')")
    @Log(title = "流程单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改流程单信息")
    public AjaxResult edit(@RequestBody WfForm wfForm){
        return toAjax(formService.updateWfForm(wfForm));
    }

    /**
     * 删除流程单信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:form:remove')")
    @Log(title = "流程单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{formIds}")
    @Operation(summary = "删除流程单信息")
    @Parameters({
            @Parameter(name = "formIds", description = "流程单ID", required = true, in = ParameterIn.PATH)
    })
    public AjaxResult remove(@PathVariable Long[] formIds){
        return toAjax(formService.deleteWfFormByFormIds(formIds));
    }


    /**
     * 挂载流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping("/addDeployForm")
    @Operation(summary = "挂载流程表单")
    public AjaxResult addDeployForm(@RequestBody WfDeployForm deployForm){
        return toAjax(deployFormService.insertWfDeployForm(deployForm));
    }


    /**
     * 分页获取组件下拉数据
     */
    @PostMapping("/simpleList")
    @Operation(summary = "分页获取下拉组件流程表单数据")
    @Parameters({
            @Parameter(name = "keywords", description = "关键字", required = true, in = ParameterIn.QUERY)
    })
    public TableDataInfo simpleList(@RequestBody SelectMoreRequest request){
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<SelectMoreVo> list = formService.getSimpleList(request.getKeywords());
        return getDataTable(list);
    }
}
