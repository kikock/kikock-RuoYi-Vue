package com.ruoyi.project.ssosrasb.shopSignInfo.service;

import java.util.List;
import com.ruoyi.project.ssosrasb.shopSignInfo.domain.ShopSignInfo;

/**
 * 店招信息Service接口
 * 
 * @author kikock
 * @date 2023-06-29
 */
public interface IShopSignInfoService 
{
    /**
     * 查询店招信息
     * 
     * @param id 店招信息主键
     * @return 店招信息
     */
    public ShopSignInfo selectShopSignInfoById(Long id);

    /**
     * 查询店招信息列表
     * 
     * @param shopSignInfo 店招信息
     * @return 店招信息集合
     */
    public List<ShopSignInfo> selectShopSignInfoList(ShopSignInfo shopSignInfo);

    /**
     * 新增店招信息
     * 
     * @param shopSignInfo 店招信息
     * @return 结果
     */
    public int insertShopSignInfo(ShopSignInfo shopSignInfo);

    /**
     * 修改店招信息
     * 
     * @param shopSignInfo 店招信息
     * @return 结果
     */
    public int updateShopSignInfo(ShopSignInfo shopSignInfo);

    /**
     * 批量删除店招信息
     * 
     * @param ids 需要删除的店招信息主键集合
     * @return 结果
     */
    public int deleteShopSignInfoByIds(Long[] ids);

    /**
     * 删除店招信息信息
     * 
     * @param id 店招信息主键
     * @return 结果
     */
    public int deleteShopSignInfoById(Long id);
}
