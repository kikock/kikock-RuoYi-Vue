package com.ruoyi.project.visitor.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.visitor.domain.VisitorHealth;
import com.ruoyi.project.visitor.service.IVisitorHealthService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 核酸报告填报记录Controller
 *
 * @author ruoyi
 * @date 2022-10-10
 */
@Api(tags ="核酸报告填报记录")
@RestController
@RequestMapping("/visitor/health")
public class VisitorHealthController extends BaseController
{
    @Autowired
    private IVisitorHealthService visitorHealthService;

    /**
     * 查询核酸报告填报记录列表
     */
    @ApiOperation("查询核酸报告填报记录列表")
    @PreAuthorize("@ss.hasPermi('visitor:health:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisitorHealth visitorHealth)
    {
        startPage();
        List<VisitorHealth> list = visitorHealthService.selectVisitorHealthList(visitorHealth);
        return getDataTable(list);
    }

    /**
     * 导出核酸报告填报记录列表
     */
    @ApiOperation("导出核酸报告填报记录列表")
    @PreAuthorize("@ss.hasPermi('visitor:health:export')")
    @Log(title = "核酸报告填报记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitorHealth visitorHealth)
    {
        List<VisitorHealth> list = visitorHealthService.selectVisitorHealthList(visitorHealth);
        ExcelUtil<VisitorHealth> util = new ExcelUtil<VisitorHealth>(VisitorHealth.class);
        util.exportExcel(response, list, "核酸报告填报记录数据");
    }

    /**
     * 获取核酸报告填报记录详细信息
     */
    @ApiOperation("获取核酸报告填报记录详细信息")
    @PreAuthorize("@ss.hasPermi('visitor:health:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(visitorHealthService.selectVisitorHealthById(id));
    }

    /**
     * 新增核酸报告填报记录
     */
    @ApiOperation("新增核酸报告填报记录")
    @PreAuthorize("@ss.hasPermi('visitor:health:add')")
    @Log(title = "核酸报告填报记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitorHealth visitorHealth)
    {
        return toAjax(visitorHealthService.insertVisitorHealth(visitorHealth));
    }

    /**
     * 修改核酸报告填报记录
     */
    @ApiOperation("修改核酸报告填报记录")
    @PreAuthorize("@ss.hasPermi('visitor:health:edit')")
    @Log(title = "核酸报告填报记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitorHealth visitorHealth)
    {
        return toAjax(visitorHealthService.updateVisitorHealth(visitorHealth));
    }

    /**
     * 删除核酸报告填报记录
     */
    @ApiOperation("删除核酸报告填报记录")
    @PreAuthorize("@ss.hasPermi('visitor:health:remove')")
    @Log(title = "核酸报告填报记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(visitorHealthService.deleteVisitorHealthByIds(ids));
    }
}
