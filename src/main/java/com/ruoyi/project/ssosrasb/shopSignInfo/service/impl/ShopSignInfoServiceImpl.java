package com.ruoyi.project.ssosrasb.shopSignInfo.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.ssosrasb.shopSignInfo.mapper.ShopSignInfoMapper;
import com.ruoyi.project.ssosrasb.shopSignInfo.domain.ShopSignInfo;
import com.ruoyi.project.ssosrasb.shopSignInfo.service.IShopSignInfoService;

/**
 * 店招信息Service业务层处理
 * 
 * @author kikock
 * @date 2023-06-29
 */
@Service
public class ShopSignInfoServiceImpl implements IShopSignInfoService 
{
    @Autowired
    private ShopSignInfoMapper shopSignInfoMapper;

    /**
     * 查询店招信息
     * 
     * @param id 店招信息主键
     * @return 店招信息
     */
    @Override
    public ShopSignInfo selectShopSignInfoById(Long id)
    {
        return shopSignInfoMapper.selectShopSignInfoById(id);
    }

    /**
     * 查询店招信息列表
     * 
     * @param shopSignInfo 店招信息
     * @return 店招信息
     */
    @Override
    public List<ShopSignInfo> selectShopSignInfoList(ShopSignInfo shopSignInfo)
    {
        return shopSignInfoMapper.selectShopSignInfoList(shopSignInfo);
    }

    /**
     * 新增店招信息
     * 
     * @param shopSignInfo 店招信息
     * @return 结果
     */
    @Override
    public int insertShopSignInfo(ShopSignInfo shopSignInfo)
    {
        shopSignInfo.setCreateTime(DateUtils.getNowDate());
        return shopSignInfoMapper.insertShopSignInfo(shopSignInfo);
    }

    /**
     * 修改店招信息
     * 
     * @param shopSignInfo 店招信息
     * @return 结果
     */
    @Override
    public int updateShopSignInfo(ShopSignInfo shopSignInfo)
    {
        shopSignInfo.setUpdateTime(DateUtils.getNowDate());
        return shopSignInfoMapper.updateShopSignInfo(shopSignInfo);
    }

    /**
     * 批量删除店招信息
     * 
     * @param ids 需要删除的店招信息主键
     * @return 结果
     */
    @Override
    public int deleteShopSignInfoByIds(Long[] ids)
    {
        return shopSignInfoMapper.deleteShopSignInfoByIds(ids);
    }

    /**
     * 删除店招信息信息
     * 
     * @param id 店招信息主键
     * @return 结果
     */
    @Override
    public int deleteShopSignInfoById(Long id)
    {
        return shopSignInfoMapper.deleteShopSignInfoById(id);
    }
}
