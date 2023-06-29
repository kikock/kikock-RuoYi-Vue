package com.ruoyi.project.company.mapper;

import java.util.List;
import com.ruoyi.project.company.domain.CompanyInfo;

/**
 * 企业信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-05-25
 */
public interface CompanyInfoMapper 
{
    /**
     * 查询企业信息
     * 
     * @param id 企业信息主键
     * @return 企业信息
     */
    public CompanyInfo selectCompanyInfoById(String id);

    /**
     * 查询企业信息列表
     * 
     * @param companyInfo 企业信息
     * @return 企业信息集合
     */
    public List<CompanyInfo> selectCompanyInfoList(CompanyInfo companyInfo);

    /**
     * 新增企业信息
     * 
     * @param companyInfo 企业信息
     * @return 结果
     */
    public int insertCompanyInfo(CompanyInfo companyInfo);

    /**
     * 修改企业信息
     * 
     * @param companyInfo 企业信息
     * @return 结果
     */
    public int updateCompanyInfo(CompanyInfo companyInfo);

    /**
     * 删除企业信息
     * 
     * @param id 企业信息主键
     * @return 结果
     */
    public int deleteCompanyInfoById(String id);

    /**
     * 批量删除企业信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompanyInfoByIds(String[] ids);

    /**
     * 根据用户id查询企业信息
     * @param userId 用户id
     * @return 企业信息
     */
    CompanyInfo selectCompanyInfoByUserId(Long userId);

    /**
     * 根据名称查询企业信息
     * @param name 企业名称
     * @return 企业信息
     */
    CompanyInfo selectCompanyInfoByName(String name);
}
