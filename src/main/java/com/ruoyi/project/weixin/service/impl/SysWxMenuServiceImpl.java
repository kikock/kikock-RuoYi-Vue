package com.ruoyi.project.weixin.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.weixin.domain.SysWxMenu;
import com.ruoyi.project.weixin.mapper.SysWxMenuMapper;
import com.ruoyi.project.weixin.service.ISysWxMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 自定义菜单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-14
 */
@Service
public class SysWxMenuServiceImpl implements ISysWxMenuService
{
    @Autowired
    private SysWxMenuMapper sysWxMenuMapper;

    /**
     * 查询自定义菜单
     *
     * @param id 自定义菜单主键
     * @return 自定义菜单
     */
    @Override
    public SysWxMenu selectSysWxMenuById(String id)
    {
        return sysWxMenuMapper.selectSysWxMenuById(id);
    }

    /**
     * 查询自定义菜单列表
     *
     * @param sysWxMenu 自定义菜单
     * @return 自定义菜单
     */
    @Override
    public List<SysWxMenu> selectSysWxMenuList(SysWxMenu sysWxMenu)
    {
        return sysWxMenuMapper.selectSysWxMenuList(sysWxMenu);
    }

    /**
     * 新增自定义菜单
     *
     * @param sysWxMenu 自定义菜单
     * @return 结果
     */
    @Override
    public int insertSysWxMenu(SysWxMenu sysWxMenu)
    {
        sysWxMenu.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isBlank(sysWxMenu.getParentId())) {
            sysWxMenu.setParentId(Constants.PARENT_ID);
        }
        return sysWxMenuMapper.insertSysWxMenu(sysWxMenu);
    }

    /**
     * 修改自定义菜单
     *
     * @param sysWxMenu 自定义菜单
     * @return 结果
     */
    @Override
    public int updateSysWxMenu(SysWxMenu sysWxMenu)
    {
        sysWxMenu.setUpdateTime(DateUtils.getNowDate());
        return sysWxMenuMapper.updateSysWxMenu(sysWxMenu);
    }

    /**
     * 批量删除自定义菜单
     *
     * @param ids 需要删除的自定义菜单主键
     * @return 结果
     */
    @Override
    public int deleteSysWxMenuByIds(String[] ids)
    {
        return sysWxMenuMapper.deleteSysWxMenuByIds(ids);
    }

    /**
     * 删除自定义菜单信息
     *
     * @param id 自定义菜单主键
     * @return 结果
     */
    @Override
    public int deleteSysWxMenuById(String id)
    {
        return sysWxMenuMapper.deleteSysWxMenuById(id);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysWxMenu> menus) {
        // List<SysWxMenu> sysWxMenus = buildMenuTree(menus);
        // return sysWxMenus.stream().map(TreeSelect::new).collect(Collectors.toList());
        return null;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<SysWxMenu> buildMenuTree(List<SysWxMenu> menus) {
        List<SysWxMenu> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (SysWxMenu menu : menus) {
            tempList.add(menu.getId());
        }
        for (Iterator<SysWxMenu> iterator = menus.iterator(); iterator.hasNext();) {
            SysWxMenu menu = iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId()))
            {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 是否存在菜单子节点
     * @param id 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean hasChildByMenuId(String id) {
        int result = sysWxMenuMapper.hasChildByMenuId(id);
        return result > 0 ? true : false;
    }

    /**
     * 根据父节点id查询菜单个数
     * @param parentId 父节点id
     * @return
     */
    @Override
    public int getMenuNum(String parentId) {
        return sysWxMenuMapper.getMenuNum(parentId);
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysWxMenu> list, SysWxMenu t)
    {
        // 得到子节点列表
        List<SysWxMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysWxMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysWxMenu> getChildList(List<SysWxMenu> list, SysWxMenu t)
    {
        List<SysWxMenu> tlist = new ArrayList<>();
        Iterator<SysWxMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysWxMenu n =  it.next();
            if (n.getParentId().equals(t.getId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysWxMenu> list, SysWxMenu t)
    {
        return getChildList(list, t).size() > 0;
    }
}
