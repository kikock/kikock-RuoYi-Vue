package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysSocialApp;
import com.ruoyi.system.service.ISysSocialAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 社交应用参数Controller
 *
 * @author kikock
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/system/socialApp")
@Tag(name = "社交应用参数")
public class SysSocialAppController extends BaseController{
    @Autowired
    private ISysSocialAppService sysSocialAppService;

    /**
     * 查询社交应用参数列表
     */
    @PreAuthorize("@ss.hasPermi('system:socialApp:list')")
    @GetMapping("/list")
    @Operation(summary = "社交应用参数列表")
    public TableDataInfo list(SysSocialApp sysSocialApp){
        startPage();
        List<SysSocialApp> list = sysSocialAppService.selectSysSocialAppList(sysSocialApp);
        return getDataTable(list);
    }

    /**
     * 导出社交应用参数列表
     */
    @PreAuthorize("@ss.hasPermi('system:socialApp:export')")
    @Log(title = "社交应用参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "社交应用参数导出")
    public void export(HttpServletResponse response, SysSocialApp sysSocialApp){
        List<SysSocialApp> list = sysSocialAppService.selectSysSocialAppList(sysSocialApp);
        ExcelUtil<SysSocialApp> util = new ExcelUtil<SysSocialApp>(SysSocialApp.class);
        util.exportExcel(response, list, "社交应用参数数据");
    }

    /**
     * 获取社交应用参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:socialApp:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "社交应用参数详细")
    @Parameters({
            @Parameter(name = "id", description = "主键", required = true, in = ParameterIn.PATH)
    })
    public AjaxResult getInfo(@PathVariable("id") Long id){
        return success(sysSocialAppService.selectSysSocialAppById(id));
    }

    /**
     * 新增社交应用参数
     */
    @PreAuthorize("@ss.hasPermi('system:socialApp:add')")
    @Log(title = "社交应用参数", businessType = BusinessType.INSERT)
    @PostMapping
    @Operation(summary = "社交应用参数新增")
    public AjaxResult add(@RequestBody SysSocialApp sysSocialApp){
        return toAjax(sysSocialAppService.insertSysSocialApp(sysSocialApp));
    }

    /**
     * 修改社交应用参数
     */
    @PreAuthorize("@ss.hasPermi('system:socialApp:edit')")
    @Log(title = "社交应用参数", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "社交应用参数修改")
    public AjaxResult edit(@RequestBody SysSocialApp sysSocialApp){
        return toAjax(sysSocialAppService.updateSysSocialApp(sysSocialApp));
    }

    /**
     * 删除社交应用参数
     */
    @PreAuthorize("@ss.hasPermi('system:socialApp:remove')")
    @Log(title = "社交应用参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "社交应用参数删除")
    @Parameters({
            @Parameter(name = "ids", description = "社交参数主键串", required = true, in = ParameterIn.PATH)
    })
    public AjaxResult remove(@PathVariable Long[] ids){
        return toAjax(sysSocialAppService.deleteSysSocialAppByIds(ids));
    }
}
