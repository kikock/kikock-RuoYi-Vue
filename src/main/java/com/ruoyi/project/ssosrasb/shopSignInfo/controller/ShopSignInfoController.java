package com.ruoyi.project.ssosrasb.shopSignInfo.controller;

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
import com.ruoyi.project.ssosrasb.shopSignInfo.domain.ShopSignInfo;
import com.ruoyi.project.ssosrasb.shopSignInfo.service.IShopSignInfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 店招信息Controller
 * 
 * @author kikock
 * @date 2023-06-29
 */
@Api(tags ="店招信息")
@RestController
@RequestMapping("/ssosrasb/shopSignInfo")
public class ShopSignInfoController extends BaseController
{
    @Autowired
    private IShopSignInfoService shopSignInfoService;

    /**
     * 查询店招信息列表
     */
    @ApiOperation("查询店招信息列表")
    @PreAuthorize("@ss.hasPermi('ssosrasb:shopSignInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopSignInfo shopSignInfo)
    {
        startPage();
        List<ShopSignInfo> list = shopSignInfoService.selectShopSignInfoList(shopSignInfo);
        return getDataTable(list);
    }

    /**
     * 导出店招信息列表
     */
    @ApiOperation("导出店招信息列表")
    @PreAuthorize("@ss.hasPermi('ssosrasb:shopSignInfo:export')")
    @Log(title = "店招信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopSignInfo shopSignInfo)
    {
        List<ShopSignInfo> list = shopSignInfoService.selectShopSignInfoList(shopSignInfo);
        ExcelUtil<ShopSignInfo> util = new ExcelUtil<ShopSignInfo>(ShopSignInfo.class);
        util.exportExcel(response, list, "店招信息数据");
    }

    /**
     * 获取店招信息详细信息
     */
    @ApiOperation("获取店招信息详细信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:shopSignInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(shopSignInfoService.selectShopSignInfoById(id));
    }

    /**
     * 新增店招信息
     */
    @ApiOperation("新增店招信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:shopSignInfo:add')")
    @Log(title = "店招信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopSignInfo shopSignInfo)
    {
        return toAjax(shopSignInfoService.insertShopSignInfo(shopSignInfo));
    }

    /**
     * 修改店招信息
     */
    @ApiOperation("修改店招信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:shopSignInfo:edit')")
    @Log(title = "店招信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopSignInfo shopSignInfo)
    {
        return toAjax(shopSignInfoService.updateShopSignInfo(shopSignInfo));
    }

    /**
     * 删除店招信息
     */
    @ApiOperation("删除店招信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:shopSignInfo:remove')")
    @Log(title = "店招信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shopSignInfoService.deleteShopSignInfoByIds(ids));
    }
}
