package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysSocialApp;
import com.ruoyi.system.domain.vo.SocialAppVo;

import java.util.List;

/**
 * 社交应用参数Service接口
 *
 * @author kikock
 * @date 2023-12-04
 */
public interface ISysSocialAppService{
    /**
     * 查询社交应用参数
     *
     * @param id 社交应用参数主键
     * @return 社交应用参数
     */
    public SysSocialApp selectSysSocialAppById(Long id);

    /**
     * 查询社交应用参数列表
     *
     * @param sysSocialApp 社交应用参数
     * @return 社交应用参数集合
     */
    public List<SysSocialApp> selectSysSocialAppList(SysSocialApp sysSocialApp);

    /**
     * 新增社交应用参数
     *
     * @param sysSocialApp 社交应用参数
     * @return 结果
     */
    public int insertSysSocialApp(SysSocialApp sysSocialApp);

    /**
     * 修改社交应用参数
     *
     * @param sysSocialApp 社交应用参数
     * @return 结果
     */
    public int updateSysSocialApp(SysSocialApp sysSocialApp);

    /**
     * 批量删除社交应用参数
     *
     * @param ids 需要删除的社交应用参数主键集合
     * @return 结果
     */
    public int deleteSysSocialAppByIds(Long[] ids);

    /**
     * 删除社交应用参数信息
     *
     * @param id 社交应用参数主键
     * @return 结果
     */
    public int deleteSysSocialAppById(Long id);
    /**
     * 检查平台是否存在
     *
     * @param code 平台代码
     * @return 结果
     */
    public boolean checkCodeExists(String code);


    /**
     * 查询社交应用初始列表
     *
     * @return 社交应用参数集合
     */
    public List<SocialAppVo> getInitSysSocialAppList();


}
