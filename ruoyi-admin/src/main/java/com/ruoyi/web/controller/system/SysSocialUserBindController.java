package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysSocialUserBind;
import com.ruoyi.system.service.ISysSocialUserBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 三方用户Controller
 *
 * @author kikock
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/system/socialUserBind")
public class SysSocialUserBindController extends BaseController{
    @Autowired
    private ISysSocialUserBindService sysSocialUserBindService;

    /**
     * 查询三方用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:socialUserBind:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSocialUserBind sysSocialUserBind){
        startPage();
        List<SysSocialUserBind> list = sysSocialUserBindService.selectSysSocialUserBindList(sysSocialUserBind);
        return getDataTable(list);
    }

    /**
     * 导出三方用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:socialUserBind:export')")
    @Log(title = "三方用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSocialUserBind sysSocialUserBind){
        List<SysSocialUserBind> list = sysSocialUserBindService.selectSysSocialUserBindList(sysSocialUserBind);
        ExcelUtil<SysSocialUserBind> util = new ExcelUtil<SysSocialUserBind>(SysSocialUserBind.class);
        util.exportExcel(response, list, "三方用户数据");
    }

    /**
     * 获取三方用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:socialUserBind:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){
        return success(sysSocialUserBindService.selectSysSocialUserBindById(id));
    }

    /**
     * 新增三方用户
     */
    @PreAuthorize("@ss.hasPermi('system:socialUserBind:add')")
    @Log(title = "三方用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSocialUserBind sysSocialUserBind){
        return toAjax(sysSocialUserBindService.insertSysSocialUserBind(sysSocialUserBind));
    }

    /**
     * 修改三方用户
     */
    @PreAuthorize("@ss.hasPermi('system:socialUserBind:edit')")
    @Log(title = "三方用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSocialUserBind sysSocialUserBind){
        return toAjax(sysSocialUserBindService.updateSysSocialUserBind(sysSocialUserBind));
    }

    /**
     * 删除三方用户
     */
    @PreAuthorize("@ss.hasPermi('system:socialUserBind:remove')")
    @Log(title = "三方用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){
        return toAjax(sysSocialUserBindService.deleteSysSocialUserBindByIds(ids));
    }



}
