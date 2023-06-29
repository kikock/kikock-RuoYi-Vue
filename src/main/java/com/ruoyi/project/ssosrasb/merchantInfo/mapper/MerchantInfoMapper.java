package com.ruoyi.project.ssosrasb.merchantInfo.mapper;

import com.ruoyi.project.ssosrasb.merchantInfo.domain.MerchantInfo;

import java.util.List;

/**
 * 商户信息Mapper接口
 *
 * @author kikock
 * @date 2023-06-29
 */
public interface MerchantInfoMapper
{
    /**
     * 查询商户信息
     *
     * @param id 商户信息主键
     * @return 商户信息
     */
    public MerchantInfo selectMerchantInfoById(Long id);

    /**
     * 查询商户信息列表
     *
     * @param merchantInfo 商户信息
     * @return 商户信息集合
     */
    public List<MerchantInfo> selectMerchantInfoList(MerchantInfo merchantInfo);

    /**
     * 新增商户信息
     *
     * @param merchantInfo 商户信息
     * @return 结果
     */
    public int insertMerchantInfo(MerchantInfo merchantInfo);

    /**
     * 修改商户信息
     *
     * @param merchantInfo 商户信息
     * @return 结果
     */
    public int updateMerchantInfo(MerchantInfo merchantInfo);

    /**
     * 删除商户信息
     *
     * @param id 商户信息主键
     * @return 结果
     */
    public int deleteMerchantInfoById(Long id);

    /**
     * 批量删除商户信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMerchantInfoByIds(Long[] ids);
}
