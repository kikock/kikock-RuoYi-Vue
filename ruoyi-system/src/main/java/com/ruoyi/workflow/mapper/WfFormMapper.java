package com.ruoyi.workflow.mapper;


import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.workflow.domain.WfDeployForm;
import com.ruoyi.workflow.domain.WfForm;
import com.ruoyi.workflow.domain.bo.WfFormBo;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程单信息Mapper接口
 *
 * @author kikock
 * @date 2024-02-02
 */
public interface WfFormMapper{
    /**
     * 查询流程单信息
     *
     * @param formId 流程单信息主键
     * @return 流程单信息
     */
    public WfForm selectWfFormByFormId(Long formId);

    /**
     * 查询流程单信息列表
     *
     * @param wfForm 流程单信息
     * @return 流程单信息集合
     */
    public List<WfForm> selectWfFormList(WfFormBo wfForm);

    /**
     * 新增流程单信息
     *
     * @param wfForm 流程单信息
     * @return 结果
     */
    public int insertWfForm(WfForm wfForm);

    /**
     * 修改流程单信息
     *
     * @param wfForm 流程单信息
     * @return 结果
     */
    public int updateWfForm(WfForm wfForm);

    /**
     * 删除流程单信息
     *
     * @param formId 流程单信息主键
     * @return 结果
     */
    public int deleteWfFormByFormId(Long formId);

    /**
     * 批量删除流程单信息
     *
     * @param formIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfFormByFormIds(Long[] formIds);

    /**
     * 查询流程单信息vo列表
     *
     * @param deployId 流程单关联信息
     * @return 流程单信息集合
     */
    public List<WfFormVo> selectWfFormVoList(@Param("deployId")String deployId);

    List<SelectMoreVo> getSimpleList(String keywords);
}
