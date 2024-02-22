package com.ruoyi.workflow.service;

import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.workflow.domain.WfForm;
import com.ruoyi.workflow.domain.bo.WfFormBo;

import java.util.List;

/**
 * 流程单信息Service接口
 *
 * @author kikock
 * @date 2024-02-02
 */
public interface IWfFormService{
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
     * 批量删除流程单信息
     *
     * @param formIds 需要删除的流程单信息主键集合
     * @return 结果
     */
    public int deleteWfFormByFormIds(Long[] formIds);

    /**
     * 删除流程单信息信息
     *
     * @param formId 流程单信息主键
     * @return 结果
     */
    public int deleteWfFormByFormId(Long formId);
    /**
     * 分页获取组件下拉数据
     * @param keywords 模糊筛选
     * @return 结果
     */
    public List<SelectMoreVo> getSimpleList(String keywords);
}
