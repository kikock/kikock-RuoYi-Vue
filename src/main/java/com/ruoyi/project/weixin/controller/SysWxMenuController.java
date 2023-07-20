package com.ruoyi.project.weixin.controller;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.project.weixin.config.WxMpProperties;
import com.ruoyi.project.weixin.domain.SysWxMenu;
import com.ruoyi.project.weixin.service.ISysWxMenuService;
import com.ruoyi.project.weixin.utils.JsonUtils;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections.CollectionUtils;
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

/**
 * 微信公众号自定义菜单Controller
 *
 * @author ruoyi
 * @date 2022-04-14
 */
@RestController
@RequestMapping("/weixin/menu")
public class SysWxMenuController extends BaseController
{
    @Autowired
    private ISysWxMenuService sysWxMenuService;
    @Autowired
    private  WxMpService wxService;
    @Autowired
    private WxMpProperties properties;

    /**
     * 查询自定义菜单列表
     */
    @PreAuthorize("@ss.hasPermi('weixin:menu:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxMenu sysWxMenu)
    {
        startPage();
        List<SysWxMenu> list = sysWxMenuService.selectSysWxMenuList(sysWxMenu);
        return getDataTable(list);
    }

    /**
     * 导出自定义菜单列表
     */
    @PreAuthorize("@ss.hasPermi('weixin:menu:export')")
    @Log(title = "自定义菜单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysWxMenu sysWxMenu)
    {
        List<SysWxMenu> list = sysWxMenuService.selectSysWxMenuList(sysWxMenu);
        ExcelUtil<SysWxMenu> util = new ExcelUtil<SysWxMenu>(SysWxMenu.class);
        util.exportExcel(response, list, "自定义菜单数据");
    }

    /**
     * 获取自定义菜单详细信息
     */
    @PreAuthorize("@ss.hasPermi('weixin:menu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(sysWxMenuService.selectSysWxMenuById(id));
    }

    /**
     * 新增自定义菜单
     */
    @PreAuthorize("@ss.hasPermi('weixin:menu:add')")
    @Log(title = "自定义菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxMenu sysWxMenu)
    {
        // 是否一级菜单
        if (Constants.PARENT_ID.equals(sysWxMenu.getParentId())) {
            int menuNum = sysWxMenuService.getMenuNum(Constants.PARENT_ID);
            if (menuNum > 2) {
                return AjaxResult.error("新增菜单失败,只能添加三个一级菜单");
            }
        } else {
            // 父级菜单
            SysWxMenu parentSysWxMenu = sysWxMenuService.selectSysWxMenuById(sysWxMenu.getParentId());
            if (!Constants.PARENT_ID.equals(parentSysWxMenu.getParentId())) {
                return AjaxResult.error("新增菜单失败,只能创建一级或二级菜单");
            }
            int menuNum = sysWxMenuService.getMenuNum(sysWxMenu.getParentId());
            if (menuNum > 4) {
                return AjaxResult.error("新增菜单失败,只能添加五个二级菜单");
            }
        }
        return toAjax(sysWxMenuService.insertSysWxMenu(sysWxMenu));
    }

    /**
     * 修改自定义菜单
     */
    @PreAuthorize("@ss.hasPermi('weixin:menu:edit')")
    @Log(title = "自定义菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxMenu sysWxMenu)
    {
        return toAjax(sysWxMenuService.updateSysWxMenu(sysWxMenu));
    }

    /**
     * 删除自定义菜单
     */
    @PreAuthorize("@ss.hasPermi('weixin:menu:remove')")
    @Log(title = "自定义菜单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        if (ids == null || ids.length < 1) {
            return AjaxResult.success();
        }
        // 是否存在子节点
        boolean flag = false;
        for (String id : ids) {
            boolean child = sysWxMenuService.hasChildByMenuId(id);
            if (child) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return AjaxResult.error("存在子菜单,不允许删除!");
        }
        return toAjax(sysWxMenuService.deleteSysWxMenuByIds(ids));
    }

    /**
     * 查询所有自定义菜单列表
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(SysWxMenu sysWxMenu)
    {
        List<SysWxMenu> list = sysWxMenuService.selectSysWxMenuList(sysWxMenu);
        return AjaxResult.success(sysWxMenuService.buildMenuTree(list));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysWxMenu menu)
    {
        List<SysWxMenu> list = sysWxMenuService.selectSysWxMenuList(menu);
        return AjaxResult.success(sysWxMenuService.buildMenuTreeSelect(list));
    }

    /**
     * 自定义菜单创建接口
     */
    @GetMapping("/create")
    @PreAuthorize("@ss.hasPermi('weixin:menu:create')")
    public AjaxResult create() {
        List<SysWxMenu> list = sysWxMenuService.selectSysWxMenuList(null);
        if (CollectionUtils.isNotEmpty(list)) {
            List<SysWxMenu> menuTree = sysWxMenuService.buildMenuTree(list);
            WxMenu wxMenu = new WxMenu();
            for (SysWxMenu menu: menuTree) {
                // 一级菜单
                WxMenuButton parentMenuButton = builderWxMenuButton(menu);
                if (CollectionUtils.isNotEmpty(menu.getChildren())) {
                    for (SysWxMenu menu2: menu.getChildren()) {
                        // 二级菜单
                        WxMenuButton wxMenuButton = builderWxMenuButton(menu2);
                        parentMenuButton.getSubButtons().add(wxMenuButton);
                    }
                }
                wxMenu.getButtons().add(parentMenuButton);
            }
            logger.info("自定义菜单:"+ JsonUtils.toJson(wxMenu));
            wxService.switchover(properties.getConfigs().get(0).getAppId());
            try {
                String menuCreate = wxService.getMenuService().menuCreate(wxMenu);
                logger.info("创建菜单结果:"+menuCreate);
                JSONObject jsonObject = JSONObject.parseObject(menuCreate);
                if (Objects.nonNull(jsonObject)) {
                    return AjaxResult.error("创建菜单失败:"+jsonObject.getString("errmsg"));
                }
            } catch (Exception ex) {
                logger.info("创建菜单异常:"+ex.getMessage());
                return AjaxResult.error("创建菜单异常:"+ex.getMessage());
            }
        }
        return AjaxResult.success();
    }

    /**
     * 构造微信菜单按钮
     * @param menu 自定义菜单对象
     * @return 微信菜单按钮
     */
    private WxMenuButton builderWxMenuButton(SysWxMenu menu) {
        WxMenuButton button = new WxMenuButton();
        button.setName(menu.getName());
        button.setKey(menu.getId());
        button.setType(menu.getType());
        button.setUrl(menu.getUrl());
        button.setAppId(menu.getMaAppId());
        button.setPagePath(menu.getMaPagePath());
        return button;
    }
}
