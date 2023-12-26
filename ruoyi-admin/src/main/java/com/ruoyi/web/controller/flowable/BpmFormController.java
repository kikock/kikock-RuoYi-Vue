package com.ruoyi.web.controller.flowable;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.flowable.domain.definition.BpmForm;
import com.ruoyi.flowable.service.definition.IBpmFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工作流的单定义Controller
 *
 * @author kikock
 * @date 2023-12-26
 */
@RestController
@RequestMapping("/bpm/form")
public class BpmFormController extends BaseController{
    @Autowired
    private IBpmFormService bpmFormService;

    /**
     * 查询工作流的单定义列表
     */
    @PreAuthorize("@ss.hasPermi('bpm:form:list')")
    @GetMapping("/list")
    public TableDataInfo list(BpmForm bpmForm){
        startPage();
        List<BpmForm> list = bpmFormService.selectBpmFormList(bpmForm);
        return getDataTable(list);
    }

    /**
     * 导出工作流的单定义列表
     */
    @PreAuthorize("@ss.hasPermi('bpm:form:export')")
    @Log(title = "工作流的单定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BpmForm bpmForm){
        List<BpmForm> list = bpmFormService.selectBpmFormList(bpmForm);
        ExcelUtil<BpmForm> util = new ExcelUtil<BpmForm>(BpmForm.class);
        util.exportExcel(response, list, "工作流的单定义数据");
    }

    /**
     * 获取工作流的单定义详细信息
     */
    @PreAuthorize("@ss.hasPermi('bpm:form:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){
        return success(bpmFormService.selectBpmFormById(id));
    }

    /**
     * 新增工作流的单定义
     */
    @PreAuthorize("@ss.hasPermi('bpm:form:add')")
    @Log(title = "工作流的单定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BpmForm bpmForm){
        return toAjax(bpmFormService.insertBpmForm(bpmForm));
    }

    /**
     * 修改工作流的单定义
     */
    @PreAuthorize("@ss.hasPermi('bpm:form:edit')")
    @Log(title = "工作流的单定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BpmForm bpmForm){
        return toAjax(bpmFormService.updateBpmForm(bpmForm));
    }

    /**
     * 删除工作流的单定义
     */
    @PreAuthorize("@ss.hasPermi('bpm:form:remove')")
    @Log(title = "工作流的单定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){
        return toAjax(bpmFormService.deleteBpmFormByIds(ids));
    }
}
