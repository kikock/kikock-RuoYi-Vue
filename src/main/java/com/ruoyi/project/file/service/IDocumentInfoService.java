package com.ruoyi.project.file.service;

import com.ruoyi.project.file.domain.DocumentInfo;
import com.ruoyi.project.file.domain.vo.DocumentInfoVo;

import java.util.List;

/**
 * 文件信息Service接口
 *
 * @author jxkj
 * @date 2022-06-23
 */
public interface IDocumentInfoService
{
    /**
     * 查询文件信息
     *
     * @param id 文件信息ID
     * @return 文件信息
     */
    public DocumentInfo selectDocumentInfoById(String id);

    /**
     * 查询文件信息列表
     *
     * @param documentInfo 文件信息
     * @return 文件信息集合
     */
    public List<DocumentInfo> selectDocumentInfoList(DocumentInfo documentInfo);

    /**
     * 新增文件信息
     *
     * @param documentInfo 文件信息
     * @return 结果
     */
    public int insertDocumentInfo(DocumentInfo documentInfo);

    /**
     * 修改文件信息
     *
     * @param documentInfo 文件信息
     * @return 结果
     */
    public int updateDocumentInfo(DocumentInfo documentInfo);

    /**
     * 批量删除文件信息
     *
     * @param ids 需要删除的文件信息ID
     * @return 结果
     */
    public int deleteDocumentInfoByIds(String[] ids);

    /**
     * 删除文件信息信息
     *
     * @param id 文件信息ID
     * @return 结果
     */
    public int deleteDocumentInfoById(String id);

    /**
     * 根据业务实体id查询
     * @param entityIdIndex 业务实体id
     * @return 结果
     */
    List<DocumentInfoVo> selectDocumentInfoByIndex(String entityIdIndex);
}
