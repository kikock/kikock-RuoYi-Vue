package com.ruoyi.project.flowable.controller;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.flowable.dto.SysDeployForm;
import com.ruoyi.project.flowable.dto.SysForm;
import com.ruoyi.project.flowable.service.ISysDeployFormService;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.project.flowable.service.ISysFormService;


/**
 * 流程表单Controller
 *
 * @author
 * @date 2021-04-03
 */
@RestController
@RequestMapping("/flowable/form")
public class SysFormController extends BaseController {
    @Autowired
    private ISysFormService SysFormService;

    @Autowired
    private ISysDeployFormService sysDeployFormService;

    /**
     * 查询流程表单列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysForm sysForm) {
        startPage();
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        return getDataTable(list);
    }

    /**
     * 导出流程表单列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:export')")
    @Log(title = "流程表单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysForm sysForm) {
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        ExcelUtil<SysForm> util = new ExcelUtil<SysForm>(SysForm.class);
        return util.exportExcel(list, "form");
    }

    /**
     * 获取流程表单详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:query')")
    @GetMapping(value = "/{formId}")
    public AjaxResult getInfo(@PathVariable("formId") Long formId) {
        return AjaxResult.success(SysFormService.selectSysFormById(formId));
    }

    /**
     * 新增流程表单
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:add')")
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.insertSysForm(sysForm));
    }

    /**
     * 修改流程表单
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:edit')")
    @Log(title = "流程表单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.updateSysForm(sysForm));
    }

    /**
     * 删除流程表单
     */
    @PreAuthorize("@ss.hasPermi('flowable:form:remove')")
    @Log(title = "流程表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{formIds}")
    public AjaxResult remove(@PathVariable Long[] formIds) {
        return toAjax(SysFormService.deleteSysFormByIds(formIds));
    }


    /**
     * 挂载流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping("/addDeployForm")
    public AjaxResult addDeployForm(@RequestBody SysDeployForm sysDeployForm) {
        return toAjax(sysDeployFormService.insertSysDeployForm(sysDeployForm));
    }
}
