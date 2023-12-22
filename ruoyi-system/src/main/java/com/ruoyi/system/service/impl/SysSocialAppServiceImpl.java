package com.ruoyi.system.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysSocialApp;
import com.ruoyi.system.domain.vo.SocialAppVo;
import com.ruoyi.system.mapper.SysSocialAppMapper;
import com.ruoyi.system.service.ISysSocialAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社交应用参数Service业务层处理
 *
 * @author kikock
 * @date 2023-12-04
 */
@Service
public class SysSocialAppServiceImpl implements ISysSocialAppService{

    @Autowired
    private SysSocialAppMapper sysSocialAppMapper;

    /**
     * 查询社交应用参数
     *
     * @param id 社交应用参数主键
     * @return 社交应用参数
     */
    @Override
    public SysSocialApp selectSysSocialAppById(Long id){
        return sysSocialAppMapper.selectSysSocialAppById(id);
    }

    /**
     * 查询社交应用参数列表
     *
     * @param sysSocialApp 社交应用参数
     * @return 社交应用参数
     */
    @Override
    public List<SysSocialApp> selectSysSocialAppList(SysSocialApp sysSocialApp){
        return sysSocialAppMapper.selectSysSocialAppList(sysSocialApp);
    }

    /**
     * 新增社交应用参数
     *
     * @param sysSocialApp 社交应用参数
     * @return 结果
     */
    @Override
    public int insertSysSocialApp(SysSocialApp sysSocialApp){
        // 检查code是否已存在
        if (checkCodeExists(sysSocialApp.getCode())) {
            // 抛出异常或返回错误信息，表示code已存在
            throw new ServiceException("应用编码已经存在");
        }
        sysSocialApp.setCreateTime(DateUtils.getNowDate());
        return sysSocialAppMapper.insertSysSocialApp(sysSocialApp);
    }

    /**
     * 修改社交应用参数
     *
     * @param sysSocialApp 社交应用参数
     * @return 结果
     */
    @Override
    public int updateSysSocialApp(SysSocialApp sysSocialApp){
        sysSocialApp.setUpdateTime(DateUtils.getNowDate());
        return sysSocialAppMapper.updateSysSocialApp(sysSocialApp);
    }

    /**
     * 批量删除社交应用参数
     *
     * @param ids 需要删除的社交应用参数主键
     * @return 结果
     */
    @Override
    public int deleteSysSocialAppByIds(Long[] ids){
        return sysSocialAppMapper.deleteSysSocialAppByIds(ids);
    }

    /**
     * 删除社交应用参数信息
     *
     * @param id 社交应用参数主键
     * @return 结果
     */
    @Override
    public int deleteSysSocialAppById(Long id){
        return sysSocialAppMapper.deleteSysSocialAppById(id);
    }

    /**
     * 检查平台code是否已存在
     *
     * @param code 平台代码
     * @return 结果
     */
    @Override
    public boolean checkCodeExists(String code){
        return sysSocialAppMapper.checkCodeExists(code);
    }

    @Override
    public List<SocialAppVo> getInitSysSocialAppList(){
        return sysSocialAppMapper.getInitSysSocialAppList() ;
    }


}
