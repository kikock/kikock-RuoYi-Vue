package com.ruoyi.project.file.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.file.domain.DocumentInfo;
import com.ruoyi.project.file.domain.vo.DocumentInfoVo;
import com.ruoyi.project.file.mapper.BusinessInfoMapper;
import com.ruoyi.project.file.mapper.DocumentInfoMapper;
import com.ruoyi.project.file.service.IDocumentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件信息Service业务层处理
 *
 * @author jxkj
 * @date 2022-06-23
 */
@Service
public class DocumentInfoServiceImpl implements IDocumentInfoService
{
    @Autowired
    private DocumentInfoMapper documentInfoMapper;

    @Autowired
    private BusinessInfoMapper businessInfoMapper;

    /**
     * 查询文件信息
     *
     * @param id 文件信息ID
     * @return 文件信息
     */
    @Override
    public DocumentInfo selectDocumentInfoById(String id)
    {
        return documentInfoMapper.selectDocumentInfoById(id);
    }

    /**
     * 查询文件信息列表
     *
     * @param documentInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<DocumentInfo> selectDocumentInfoList(DocumentInfo documentInfo)
    {
        return documentInfoMapper.selectDocumentInfoList(documentInfo);
    }

    /**
     * 新增文件信息
     *
     * @param documentInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertDocumentInfo(DocumentInfo documentInfo)
    {
        documentInfo.setCreateTime(DateUtils.getNowDate());
        return documentInfoMapper.insertDocumentInfo(documentInfo);
    }

    /**
     * 修改文件信息
     *
     * @param documentInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateDocumentInfo(DocumentInfo documentInfo)
    {
        return documentInfoMapper.updateDocumentInfo(documentInfo);
    }

    /**
     * 批量删除文件信息
     *
     * @param ids 需要删除的文件信息ID
     * @return 结果
     */
    @Override
    public int deleteDocumentInfoByIds(String[] ids)
    {
        businessInfoMapper.deleteBusinessInfoByDocIds(ids);
        return documentInfoMapper.deleteDocumentInfoByIds(ids);
    }

    /**
     * 删除文件信息信息
     *
     * @param id 文件信息ID
     * @return 结果
     */
    @Override
    public int deleteDocumentInfoById(String id)
    {
        return documentInfoMapper.deleteDocumentInfoById(id);
    }

    /**
     * 根据业务实体id查询
     * @param entityIdIndex 业务实体id
     * @return 结果
     */
    @Override
    public List<DocumentInfoVo> selectDocumentInfoByIndex(String entityIdIndex) {
        return documentInfoMapper.selectDocumentInfoByIndex(entityIdIndex);
    }
}
