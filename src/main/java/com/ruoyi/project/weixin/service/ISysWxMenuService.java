package com.ruoyi.project.weixin.service;

import java.util.List;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.weixin.domain.SysWxMenu;

/**
 * 自定义菜单Service接口
 *
 * @author ruoyi
 * @date 2022-04-14
 */
public interface ISysWxMenuService
{
    /**
     * 查询自定义菜单
     *
     * @param id 自定义菜单主键
     * @return 自定义菜单
     */
    public SysWxMenu selectSysWxMenuById(String id);

    /**
     * 查询自定义菜单列表
     *
     * @param sysWxMenu 自定义菜单
     * @return 自定义菜单集合
     */
    public List<SysWxMenu> selectSysWxMenuList(SysWxMenu sysWxMenu);

    /**
     * 新增自定义菜单
     *
     * @param sysWxMenu 自定义菜单
     * @return 结果
     */
    public int insertSysWxMenu(SysWxMenu sysWxMenu);

    /**
     * 修改自定义菜单
     *
     * @param sysWxMenu 自定义菜单
     * @return 结果
     */
    public int updateSysWxMenu(SysWxMenu sysWxMenu);

    /**
     * 批量删除自定义菜单
     *
     * @param ids 需要删除的自定义菜单主键集合
     * @return 结果
     */
    public int deleteSysWxMenuByIds(String[] ids);

    /**
     * 删除自定义菜单信息
     *
     * @param id 自定义菜单主键
     * @return 结果
     */
    public int deleteSysWxMenuById(String id);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysWxMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<SysWxMenu> buildMenuTree(List<SysWxMenu> menus);

    /**
     * 是否存在菜单子节点
     *
     * @param id 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByMenuId(String id);

    /**
     * 根据父节点id查询菜单个数
     * @param parentId 父节点id
     * @return
     */
    int getMenuNum(String parentId);

}
