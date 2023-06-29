package com.ruoyi.project.file.mapper;

import com.ruoyi.project.file.domain.BusinessInfo;

import java.util.List;

/**
 * 业务实体关联文档Mapper接口
 *
 * @author jxkj
 * @date 2022-06-23
 */
public interface BusinessInfoMapper
{
    /**
     * 查询业务实体关联文档
     *
     * @param id 业务实体关联文档ID
     * @return 业务实体关联文档
     */
    public BusinessInfo selectBusinessInfoById(String id);

    /**
     * 查询业务实体关联文档列表
     *
     * @param businessInfo 业务实体关联文档
     * @return 业务实体关联文档集合
     */
    public List<BusinessInfo> selectBusinessInfoList(BusinessInfo businessInfo);

    /**
     * 新增业务实体关联文档
     *
     * @param businessInfo 业务实体关联文档
     * @return 结果
     */
    public int insertBusinessInfo(BusinessInfo businessInfo);

    /**
     * 修改业务实体关联文档
     *
     * @param businessInfo 业务实体关联文档
     * @return 结果
     */
    public int updateBusinessInfo(BusinessInfo businessInfo);

    /**
     * 删除业务实体关联文档
     *
     * @param id 业务实体关联文档ID
     * @return 结果
     */
    public int deleteBusinessInfoById(String id);

    /**
     * 批量删除业务实体关联文档
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessInfoByIds(String[] ids);

    /**
     * 根据业务实体Id查询
     * @param entityIdIndex 业务实体Id
     * @return 结果
     */
    List<BusinessInfo> selectBusinessInfoByIndex(String entityIdIndex);

    /**
     * 批量新增
     * @param infoList 业务实体关联文档对象
     * @return 结果
     */
    int batchBusinessInfo(List<BusinessInfo> infoList);

    /**
     * 根据文档ids删除
     * @param ids 文档id列表
     * @return 结果
     */
    int deleteBusinessInfoByDocIds(String[] ids);
}
