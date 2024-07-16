package com.ruoyi.web.controller.workflow;


import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.vo.SelectMoreRequest;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.workflow.domain.WfUserGroup;
import com.ruoyi.workflow.service.IWfUserGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 用户组Controller
 *
 * @author kikock
 * @date 2023-12-27
 */
@RestController
@RequestMapping("/workflow/group")
@Tag(name = "用户组管理")
public class WfUserGroupController extends BaseController{
    @Autowired
    private IWfUserGroupService wfUserGroupService;

    /**
     * 查询用户组列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:group:list')")
    @GetMapping("/list")
    @Operation(summary = "查询用户组列表")
    public TableDataInfo list(WfUserGroup wfUserGroup) {
        startPage();
        return getDataTable(wfUserGroupService.selectWfUserGroupList(wfUserGroup));
    }

    /**
     * 导出用户组列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:group:export')")
    @PostMapping("/export")
    @Operation(summary = "导出用户组列表")
    public void export(HttpServletResponse response, WfUserGroup wfUserGroup) {
        List<WfUserGroup> list = wfUserGroupService.selectWfUserGroupList(wfUserGroup);
        ExcelUtil<WfUserGroup> util = new ExcelUtil<WfUserGroup>(WfUserGroup.class);
        util.exportExcel(response, list, "用户组数据");
    }

    /**
     * 获取用户组详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:group:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取用户组详细信息")
    @Parameters({
            @Parameter(name = "id", description = "用户组ID", required = true, in = ParameterIn.PATH)
    })
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(wfUserGroupService.selectWfUserGroupById(id));
    }

    /**
     * 新增用户组
     */
    @PreAuthorize("@ss.hasPermi('workflow:group:add')")
    @PostMapping
    @Operation(summary = "新增用户组")
    public AjaxResult add(@RequestBody WfUserGroup wfUserGroup) {
        return toAjax(wfUserGroupService.insertWfUserGroup(wfUserGroup));
    }

    /**
     * 修改用户组
     */
    @PreAuthorize("@ss.hasPermi('workflow:group:edit')")
    @PutMapping
    @Operation(summary = "修改用户组")
    public AjaxResult edit(@RequestBody WfUserGroup wfUserGroup) {
        return toAjax(wfUserGroupService.updateWfUserGroup(wfUserGroup));
    }

    /**
     * 删除用户组
     */
    @PreAuthorize("@ss.hasPermi('workflow:group:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户组")
    @Parameters({
            @Parameter(name = "ids", description = "用户组ID", required = true, in = ParameterIn.PATH)
    })
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(wfUserGroupService.deleteWfUserGroupByIds(ids));
    }

    /**
     * 分页获取组件下拉数据
     */
    @PostMapping("/simpleList")
    @Operation(summary = "分页获取下拉组件用户组数据")
    @Parameters({
            @Parameter(name = "keywords", description = "关键字", required = true, in = ParameterIn.QUERY)
    })
    public TableDataInfo simpleList(@RequestBody SelectMoreRequest request){
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<SelectMoreVo> list = wfUserGroupService.getSimpleList(request.getKeywords());
        return getDataTable(list);
    }
}
