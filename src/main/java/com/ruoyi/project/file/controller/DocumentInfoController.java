package com.ruoyi.project.file.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.file.domain.DocumentInfo;
import com.ruoyi.project.file.service.IDocumentInfoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件信息Controller
 *
 * @author jxkj
 * @date 2022-06-23
 */
@Api(tags ="文件信息")
@RestController
@RequestMapping("/file/info")
public class DocumentInfoController extends BaseController
{
    @Autowired
    private IDocumentInfoService documentInfoService;

    /**
     * 查询文件信息列表
     */
    @ApiOperation("查询文件信息列表")
    @PreAuthorize("@ss.hasPermi('file:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocumentInfo documentInfo)
    {
        startPage();
        List<DocumentInfo> list = documentInfoService.selectDocumentInfoList(documentInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @ApiOperation("导出文件信息列表")
    @PreAuthorize("@ss.hasPermi('file:info:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocumentInfo documentInfo)
    {
        List<DocumentInfo> list = documentInfoService.selectDocumentInfoList(documentInfo);
        ExcelUtil<DocumentInfo> util = new ExcelUtil<DocumentInfo>(DocumentInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 获取文件信息详细信息
     */
    @ApiOperation("获取文件信息详细信息")
    @PreAuthorize("@ss.hasPermi('file:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(documentInfoService.selectDocumentInfoById(id));
    }

    /**
     * 新增文件信息
     */
    @ApiOperation("新增文件信息")
    @PreAuthorize("@ss.hasPermi('file:info:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocumentInfo documentInfo)
    {
        return toAjax(documentInfoService.insertDocumentInfo(documentInfo));
    }

    /**
     * 修改文件信息
     */
    @ApiOperation("修改文件信息")
    @PreAuthorize("@ss.hasPermi('file:info:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocumentInfo documentInfo)
    {
        return toAjax(documentInfoService.updateDocumentInfo(documentInfo));
    }

    /**
     * 删除文件信息
     */
    @ApiOperation("删除文件信息")
    @PreAuthorize("@ss.hasPermi('file:info:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(documentInfoService.deleteDocumentInfoByIds(ids));
    }

    @ApiOperation("删除文件信息")
    @GetMapping("/deleteDocumentInfo")
    public AjaxResult deleteDocumentInfo(@RequestParam("ids") String[] ids) {
        if (StringUtils.isEmpty(ids)) {
            return AjaxResult.success();
        }
        return toAjax(documentInfoService.deleteDocumentInfoByIds(ids));
    }
}
