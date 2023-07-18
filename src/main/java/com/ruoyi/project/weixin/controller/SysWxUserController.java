package com.ruoyi.project.weixin.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;
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
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 微信用户Controller
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Api("微信用户")
@RestController
@RequestMapping("/weixin/user")
public class SysWxUserController extends BaseController
{
    @Autowired
    private ISysWxUserService sysWxUserService;

    /**
     * 查询微信用户列表
     */
    @ApiOperation("查询微信用户列表")
    @PreAuthorize("@ss.hasPermi('weixin:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxUser sysWxUser)
    {
        startPage();
        List<SysWxUser> list = sysWxUserService.selectSysWxUserList(sysWxUser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户列表
     */
    @ApiOperation("导出微信用户列表")
    @PreAuthorize("@ss.hasPermi('weixin:user:export')")
    @Log(title = "微信用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysWxUser sysWxUser)
    {
        List<SysWxUser> list = sysWxUserService.selectSysWxUserList(sysWxUser);
        ExcelUtil<SysWxUser> util = new ExcelUtil<SysWxUser>(SysWxUser.class);
        util.exportExcel(response, list, "微信用户数据");
    }

    /**
     * 获取微信用户详细信息
     */
    @ApiOperation("获取微信用户详细信息")
    @PreAuthorize("@ss.hasPermi('weixin:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(sysWxUserService.selectSysWxUserById(id));
    }

    /**
     * 新增微信用户
     */
    @ApiOperation("新增微信用户")
    @PreAuthorize("@ss.hasPermi('weixin:user:add')")
    @Log(title = "微信用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxUser sysWxUser)
    {
        return toAjax(sysWxUserService.insertSysWxUser(sysWxUser));
    }

    /**
     * 修改微信用户
     */
    @ApiOperation("修改微信用户")
    @PreAuthorize("@ss.hasPermi('weixin:user:edit')")
    @Log(title = "微信用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxUser sysWxUser)
    {
        return toAjax(sysWxUserService.updateSysWxUser(sysWxUser));
    }

    /**
     * 删除微信用户
     */
    @ApiOperation("删除微信用户")
    @PreAuthorize("@ss.hasPermi('weixin:user:remove')")
    @Log(title = "微信用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(sysWxUserService.deleteSysWxUserByIds(ids));
    }
}
