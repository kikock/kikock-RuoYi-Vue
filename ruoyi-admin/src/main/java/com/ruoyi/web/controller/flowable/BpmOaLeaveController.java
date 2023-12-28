package com.ruoyi.web.controller.flowable;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.flowable.domain.leave.BpmOaLeave;
import com.ruoyi.flowable.service.leave.IBpmOaLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * OA 请假申请Controller
 *
 * @author kikock
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/bpm/oa/leave")
public class BpmOaLeaveController extends BaseController {
    @Autowired
    private IBpmOaLeaveService bpmOaLeaveService;

    /**
     * 查询OA 请假申请列表
     */
    @PreAuthorize("@ss.hasPermi('system:leave:list')")
    @GetMapping("/list")
    public TableDataInfo list(BpmOaLeave bpmOaLeave) {
        startPage();
        List<BpmOaLeave> list = bpmOaLeaveService.selectBpmOaLeaveList(bpmOaLeave);
        return getDataTable(list);
    }

    /**
     * 导出OA 请假申请列表
     */
    @PreAuthorize("@ss.hasPermi('system:leave:export')")
    @Log(title = "OA 请假申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BpmOaLeave bpmOaLeave) {
        List<BpmOaLeave> list = bpmOaLeaveService.selectBpmOaLeaveList(bpmOaLeave);
        ExcelUtil<BpmOaLeave> util = new ExcelUtil<BpmOaLeave>(BpmOaLeave.class);
        util.exportExcel(response, list, "OA 请假申请数据");
    }

    /**
     * 获取OA 请假申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:leave:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bpmOaLeaveService.selectBpmOaLeaveById(id));
    }

    /**
     * 新增OA 请假申请
     */
    @PreAuthorize("@ss.hasPermi('system:leave:add')")
    @Log(title = "OA 请假申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BpmOaLeave bpmOaLeave) {
        bpmOaLeave.setUserId(getUserId());
        return toAjax(bpmOaLeaveService.insertBpmOaLeave(bpmOaLeave));
    }

    /**
     * 修改OA 请假申请
     */
    @PreAuthorize("@ss.hasPermi('system:leave:edit')")
    @Log(title = "OA 请假申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BpmOaLeave bpmOaLeave) {
        return toAjax(bpmOaLeaveService.updateBpmOaLeave(bpmOaLeave));
    }

    /**
     * 删除OA 请假申请
     */
    @PreAuthorize("@ss.hasPermi('system:leave:remove')")
    @Log(title = "OA 请假申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bpmOaLeaveService.deleteBpmOaLeaveByIds(ids));
    }
}
