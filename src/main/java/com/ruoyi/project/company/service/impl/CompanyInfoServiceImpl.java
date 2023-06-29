package com.ruoyi.project.company.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.company.mapper.CompanyInfoMapper;
import com.ruoyi.project.company.domain.CompanyInfo;
import com.ruoyi.project.company.service.ICompanyInfoService;

/**
 * 企业信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-05-25
 */
@Service
public class CompanyInfoServiceImpl implements ICompanyInfoService 
{
    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询企业信息
     * 
     * @param id 企业信息主键
     * @return 企业信息
     */
    @Override
    public CompanyInfo selectCompanyInfoById(String id)
    {
        return companyInfoMapper.selectCompanyInfoById(id);
    }

    /**
     * 查询企业信息列表
     * 
     * @param companyInfo 企业信息
     * @return 企业信息
     */
    @Override
    public List<CompanyInfo> selectCompanyInfoList(CompanyInfo companyInfo)
    {
        return companyInfoMapper.selectCompanyInfoList(companyInfo);
    }

    /**
     * 新增企业信息
     * 
     * @param companyInfo 企业信息
     * @return 结果
     */
    @Override
    public int insertCompanyInfo(CompanyInfo companyInfo)
    {
        companyInfo.setCreateTime(DateUtils.getNowDate());
        return companyInfoMapper.insertCompanyInfo(companyInfo);
    }

    /**
     * 修改企业信息
     * 
     * @param companyInfo 企业信息
     * @return 结果
     */
    @Override
    public int updateCompanyInfo(CompanyInfo companyInfo)
    {
        companyInfo.setUpdateTime(DateUtils.getNowDate());
        return companyInfoMapper.updateCompanyInfo(companyInfo);
    }

    /**
     * 批量删除企业信息
     * 
     * @param ids 需要删除的企业信息主键
     * @return 结果
     */
    @Override
    public int deleteCompanyInfoByIds(String[] ids)
    {
        return companyInfoMapper.deleteCompanyInfoByIds(ids);
    }

    /**
     * 删除企业信息信息
     * 
     * @param id 企业信息主键
     * @return 结果
     */
    @Override
    public int deleteCompanyInfoById(String id)
    {
        return companyInfoMapper.deleteCompanyInfoById(id);
    }

    @Override
    public CompanyInfo selectCompanyInfoByUserId(Long userId) {
        return companyInfoMapper.selectCompanyInfoByUserId(userId);
    }

    @Override
    public CompanyInfo selectCompanyInfoByName(String name) {
        return companyInfoMapper.selectCompanyInfoByName(name);
    }
}
