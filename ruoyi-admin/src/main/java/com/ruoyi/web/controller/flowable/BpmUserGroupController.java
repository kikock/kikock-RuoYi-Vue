package com.ruoyi.web.controller.flowable;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.vo.SelectMoreRequest;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.flowable.domain.definition.BpmUserGroup;
import com.ruoyi.flowable.service.definition.IBpmUserGroupService;
import com.ruoyi.system.domain.SysPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户组Controller
 *
 * @author kikock
 * @date 2023-12-27
 */
@RestController
@RequestMapping("/bpm/group")
public class BpmUserGroupController extends BaseController {
    @Autowired
    private IBpmUserGroupService bpmUserGroupService;

    /**
     * 查询用户组列表
     */
    @PreAuthorize("@ss.hasPermi('bpm:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(BpmUserGroup bpmUserGroup) {
        startPage();
        List<BpmUserGroup> list = bpmUserGroupService.selectBpmUserGroupList(bpmUserGroup);
        return getDataTable(list);
    }

    /**
     * 导出用户组列表
     */
    @PreAuthorize("@ss.hasPermi('bpm:group:export')")
    @Log(title = "用户组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BpmUserGroup bpmUserGroup) {
        List<BpmUserGroup> list = bpmUserGroupService.selectBpmUserGroupList(bpmUserGroup);
        ExcelUtil<BpmUserGroup> util = new ExcelUtil<BpmUserGroup>(BpmUserGroup.class);
        util.exportExcel(response, list, "用户组数据");
    }

    /**
     * 获取用户组详细信息
     */
    @PreAuthorize("@ss.hasPermi('bpm:group:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bpmUserGroupService.selectBpmUserGroupById(id));
    }

    /**
     * 新增用户组
     */
    @PreAuthorize("@ss.hasPermi('bpm:group:add')")
    @Log(title = "用户组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BpmUserGroup bpmUserGroup) {
        return toAjax(bpmUserGroupService.insertBpmUserGroup(bpmUserGroup));
    }

    /**
     * 修改用户组
     */
    @PreAuthorize("@ss.hasPermi('bpm:group:edit')")
    @Log(title = "用户组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BpmUserGroup bpmUserGroup) {
        return toAjax(bpmUserGroupService.updateBpmUserGroup(bpmUserGroup));
    }

    /**
     * 删除用户组
     */
    @PreAuthorize("@ss.hasPermi('bpm:group:remove')")
    @Log(title = "用户组", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bpmUserGroupService.deleteBpmUserGroupByIds(ids));
    }

    /**
     * 分页获取组件下拉数据
     */
    @PostMapping("/simpleList")
    public TableDataInfo simpleList(@RequestBody SelectMoreRequest request){
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<SelectMoreVo> list = bpmUserGroupService.getSimpleList(request.getKeywords());
        return getDataTable(list);
    }
}
