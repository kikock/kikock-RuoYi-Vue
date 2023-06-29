package com.ruoyi.project.company.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.weixin.domain.SysWxMenu;
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
import com.ruoyi.project.company.domain.IndustryType;
import com.ruoyi.project.company.service.IIndustryTypeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 行业类型Controller
 * 
 * @author ruoyi
 * @date 2022-05-25
 */
@Api(tags ="行业类型")
@RestController
@RequestMapping("/company/type")
public class IndustryTypeController extends BaseController
{
    @Autowired
    private IIndustryTypeService industryTypeService;

    /**
     * 查询行业类型列表
     */
    @ApiOperation("查询行业类型列表")
    @PreAuthorize("@ss.hasPermi('company:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(IndustryType industryType)
    {
        startPage();
        List<IndustryType> list = industryTypeService.selectIndustryTypeList(industryType);
        return getDataTable(list);
    }

    /**
     * 导出行业类型列表
     */
    @ApiOperation("导出行业类型列表")
    @PreAuthorize("@ss.hasPermi('company:type:export')")
    @Log(title = "行业类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IndustryType industryType)
    {
        List<IndustryType> list = industryTypeService.selectIndustryTypeList(industryType);
        ExcelUtil<IndustryType> util = new ExcelUtil<IndustryType>(IndustryType.class);
        util.exportExcel(response, list, "行业类型数据");
    }

    /**
     * 获取行业类型详细信息
     */
    @ApiOperation("获取行业类型详细信息")
    @PreAuthorize("@ss.hasPermi('company:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(industryTypeService.selectIndustryTypeById(id));
    }

    /**
     * 新增行业类型
     */
    @ApiOperation("新增行业类型")
    @PreAuthorize("@ss.hasPermi('company:type:add')")
    @Log(title = "行业类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IndustryType industryType)
    {
        return toAjax(industryTypeService.insertIndustryType(industryType));
    }

    /**
     * 修改行业类型
     */
    @ApiOperation("修改行业类型")
    @PreAuthorize("@ss.hasPermi('company:type:edit')")
    @Log(title = "行业类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IndustryType industryType)
    {
        return toAjax(industryTypeService.updateIndustryType(industryType));
    }

    /**
     * 删除行业类型
     */
    @ApiOperation("删除行业类型")
    @PreAuthorize("@ss.hasPermi('company:type:remove')")
    @Log(title = "行业类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(industryTypeService.deleteIndustryTypeByIds(ids));
    }

    @ApiOperation("查询所有行业类型")
    @GetMapping("/listAll")
    public AjaxResult listAll(IndustryType industryType) {
        List<IndustryType> industryTypes = industryTypeService.selectIndustryTypeList(industryType);
        return AjaxResult.success(industryTypeService.buildIndustryTypeTree(industryTypes));
    }

    /**
     * 获取行业类型下拉树列表
     */
    @ApiOperation("获取行业类型下拉树列表")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(IndustryType industryType)
    {
        List<IndustryType> industryTypes = industryTypeService.selectIndustryTypeList(industryType);
        return AjaxResult.success(industryTypeService.buildMenuTreeSelect(industryTypes));
    }
}
