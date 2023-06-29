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
import com.ruoyi.project.visitor.domain.VisitorApplyRecord;
import com.ruoyi.project.visitor.service.IVisitorApplyRecordService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 访客申请记录Controller
 *
 * @author ruoyi
 * @date 2022-09-19
 */
@Api(tags ="访客申请记录")
@RestController
@RequestMapping("/visitor/visitor")
public class VisitorApplyRecordController extends BaseController
{
    @Autowired
    private IVisitorApplyRecordService visitorApplyRecordService;

    /**
     * 查询访客申请记录列表
     */
    @ApiOperation("查询访客申请记录列表")
    @PreAuthorize("@ss.hasPermi('visitor:visitor:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisitorApplyRecord visitorApplyRecord)
    {
        startPage();
        List<VisitorApplyRecord> list = visitorApplyRecordService.selectVisitorApplyRecordList(visitorApplyRecord);
        return getDataTable(list);
    }

    /**
     * 导出访客申请记录列表
     */
    @ApiOperation("导出访客申请记录列表")
    @PreAuthorize("@ss.hasPermi('visitor:visitor:export')")
    @Log(title = "访客申请记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitorApplyRecord visitorApplyRecord)
    {
        List<VisitorApplyRecord> list = visitorApplyRecordService.selectVisitorApplyRecordList(visitorApplyRecord);
        ExcelUtil<VisitorApplyRecord> util = new ExcelUtil<VisitorApplyRecord>(VisitorApplyRecord.class);
        util.exportExcel(response, list, "访客申请记录数据");
    }

    /**
     * 获取访客申请记录详细信息
     */
    @ApiOperation("获取访客申请记录详细信息")
    @PreAuthorize("@ss.hasPermi('visitor:visitor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(visitorApplyRecordService.selectVisitorApplyRecordById(id));
    }

    /**
     * 新增访客申请记录
     */
    @ApiOperation("新增访客申请记录")
    @PreAuthorize("@ss.hasPermi('visitor:visitor:add')")
    @Log(title = "访客申请记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitorApplyRecord visitorApplyRecord)
    {
        return toAjax(visitorApplyRecordService.insertVisitorApplyRecord(visitorApplyRecord));
    }

    /**
     * 修改访客申请记录
     */
    @ApiOperation("修改访客申请记录")
    @PreAuthorize("@ss.hasPermi('visitor:visitor:edit')")
    @Log(title = "访客申请记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitorApplyRecord visitorApplyRecord)
    {
        return toAjax(visitorApplyRecordService.updateVisitorApplyRecord(visitorApplyRecord));
    }

    /**
     * 删除访客申请记录
     */
    @ApiOperation("删除访客申请记录")
    @PreAuthorize("@ss.hasPermi('visitor:visitor:remove')")
    @Log(title = "访客申请记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(visitorApplyRecordService.deleteVisitorApplyRecordByIds(ids));
    }
}
