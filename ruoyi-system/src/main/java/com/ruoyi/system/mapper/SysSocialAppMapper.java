package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysSocialApp;
import com.ruoyi.system.domain.vo.SocialAppVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 社交应用参数Mapper接口
 *
 * @author kikock
 * @date 2023-12-04
 */
public interface SysSocialAppMapper{
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
     * 删除社交应用参数
     *
     * @param id 社交应用参数主键
     * @return 结果
     */
    public int deleteSysSocialAppById(Long id);

    /**
     * 批量删除社交应用参数
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysSocialAppByIds(Long[] ids);

    /**
     *  检查code是否已存在
     *
     * @param code 编码
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
