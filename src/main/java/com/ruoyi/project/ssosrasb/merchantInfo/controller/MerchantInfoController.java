package com.ruoyi.project.ssosrasb.merchantInfo.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.ssosrasb.merchantInfo.domain.MerchantInfo;
import com.ruoyi.project.ssosrasb.merchantInfo.service.IMerchantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商户信息Controller
 *
 * @author kikock
 * @date 2023-06-29
 */
@Api(tags ="商户信息")
@RestController
@RequestMapping("/ssosrasb/merchantInfo")
public class MerchantInfoController extends BaseController
{
    @Autowired
    private IMerchantInfoService merchantInfoService;

    /**
     * 查询商户信息列表
     */
    @ApiOperation("查询商户信息列表")
    @PreAuthorize("@ss.hasPermi('ssosrasb:merchantInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(MerchantInfo merchantInfo)
    {
        startPage();
        List<MerchantInfo> list = merchantInfoService.selectMerchantInfoList(merchantInfo);
        return getDataTable(list);
    }

    /**
     * 导出商户信息列表
     */
    @ApiOperation("导出商户信息列表")
    @PreAuthorize("@ss.hasPermi('ssosrasb:merchantInfo:export')")
    @Log(title = "商户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MerchantInfo merchantInfo)
    {
        List<MerchantInfo> list = merchantInfoService.selectMerchantInfoList(merchantInfo);
        ExcelUtil<MerchantInfo> util = new ExcelUtil<MerchantInfo>(MerchantInfo.class);
        util.exportExcel(response, list, "商户信息数据");
    }

    /**
     * 获取商户信息详细信息
     */
    @ApiOperation("获取商户信息详细信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:merchantInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(merchantInfoService.selectMerchantInfoById(id));
    }

    /**
     * 新增商户信息
     */
    @ApiOperation("新增商户信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:merchantInfo:add')")
    @Log(title = "商户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MerchantInfo merchantInfo)
    {
        return toAjax(merchantInfoService.insertMerchantInfo(merchantInfo));
    }

    /**
     * 修改商户信息
     */
    @ApiOperation("修改商户信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:merchantInfo:edit')")
    @Log(title = "商户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MerchantInfo merchantInfo)
    {
        return toAjax(merchantInfoService.updateMerchantInfo(merchantInfo));
    }

    /**
     * 删除商户信息
     */
    @ApiOperation("删除商户信息")
    @PreAuthorize("@ss.hasPermi('ssosrasb:merchantInfo:remove')")
    @Log(title = "商户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(merchantInfoService.deleteMerchantInfoByIds(ids));
    }
}
