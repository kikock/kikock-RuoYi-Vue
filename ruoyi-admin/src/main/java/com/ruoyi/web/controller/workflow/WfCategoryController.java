package com.ruoyi.web.controller.workflow;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.workflow.domain.WfCategory;
import com.ruoyi.workflow.service.IWfCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 流程分类Controller
 *
 * @author kikock
 * @date 2024-02-02
 */
@RestController
@RequestMapping("/workflow/category")
public class WfCategoryController extends BaseController{
    @Autowired
    private IWfCategoryService wfCategoryService;

    /**
     * 查询流程分类列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(WfCategory wfCategory){
        startPage();
        List<WfCategory> list = wfCategoryService.selectWfCategoryList(wfCategory);
        return getDataTable(list);
    }

    /**
     * 导出流程分类列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:category:export')")
    @Log(title = "流程分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WfCategory wfCategory){
        List<WfCategory> list = wfCategoryService.selectWfCategoryList(wfCategory);
        ExcelUtil<WfCategory> util = new ExcelUtil<WfCategory>(WfCategory.class);
        util.exportExcel(response, list, "流程分类数据");
    }

    /**
     * 获取流程分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId){
        return success(wfCategoryService.selectWfCategoryByCategoryId(categoryId));
    }

    /**
     * 新增流程分类
     */
    @PreAuthorize("@ss.hasPermi('workflow:category:add')")
    @Log(title = "流程分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfCategory wfCategory){
        if (!wfCategoryService.checkCategoryCodeUnique(wfCategory)) {
            return AjaxResult.error("新增流程分类'" + wfCategory.getCategoryName() + "'失败，流程编码已存在");
        }
        return toAjax(wfCategoryService.insertWfCategory(wfCategory));
    }

    /**
     * 修改流程分类
     */
    @PreAuthorize("@ss.hasPermi('workflow:category:edit')")
    @Log(title = "流程分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfCategory wfCategory){
        if (!wfCategoryService.checkCategoryCodeUnique(wfCategory)) {
            return AjaxResult.error("修改流程分类'" + wfCategory.getCategoryName() + "'失败，流程编码已存在");
        }
        return toAjax(wfCategoryService.updateWfCategory(wfCategory));
    }

    /**
     * 删除流程分类
     */
    @PreAuthorize("@ss.hasPermi('workflow:category:remove')")
    @Log(title = "流程分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds){
        return toAjax(wfCategoryService.deleteWfCategoryByCategoryIds(categoryIds));
    }
}
