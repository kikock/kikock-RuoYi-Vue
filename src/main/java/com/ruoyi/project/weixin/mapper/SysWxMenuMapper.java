package com.ruoyi.project.weixin.mapper;

import java.util.List;
import com.ruoyi.project.weixin.domain.SysWxMenu;

/**
 * 自定义菜单Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-14
 */
public interface SysWxMenuMapper 
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
     * 删除自定义菜单
     * 
     * @param id 自定义菜单主键
     * @return 结果
     */
    public int deleteSysWxMenuById(String id);

    /**
     * 批量删除自定义菜单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysWxMenuByIds(String[] ids);

    /**
     * 是否存在子节点
     * @param id 菜单id
     * @return 结果
     */
    public int hasChildByMenuId(String id);

    /**
     * 根据父节点id查询菜单个数
     * @param parentId 父节点id
     * @return
     */
    int getMenuNum(String parentId);
}
