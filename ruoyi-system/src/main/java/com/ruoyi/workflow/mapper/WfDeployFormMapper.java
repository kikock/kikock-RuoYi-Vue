package com.ruoyi.workflow.mapper;


import com.ruoyi.workflow.domain.WfCopy;
import com.ruoyi.workflow.domain.WfDeployForm;
import com.ruoyi.workflow.domain.vo.WfDeployFormVo;

import java.util.List;

/**
 * 流程实例关联单Mapper接口
 *
 * @author kikock
 * @date 2024-02-02
 */
public interface WfDeployFormMapper{
    /**
     * 查询流程实例关联单
     *
     * @param deployId 流程实例关联单主键
     * @return 流程实例关联单
     */
    public WfDeployForm selectWfDeployFormByDeployId(String deployId);
    /**
     * 查询流程实例关联单
     *
     * @return 流程实例关联单
     */
    public WfDeployFormVo selectVoOneWfDeployForm(WfDeployForm wfDeployForm);
    /**
     * 查询流程实例关联单列表
     *
     * @param wfDeployForm 流程实例关联单
     * @return 流程实例关联单集合
     */
    public List<WfDeployForm> selectWfDeployFormList(WfDeployForm wfDeployForm);

    /**
     * 新增流程实例关联单
     *
     * @param wfDeployForm 流程实例关联单
     * @return 结果
     */
    public int insertWfDeployForm(WfDeployForm wfDeployForm);

    /**
     * 修改流程实例关联单
     *
     * @param wfDeployForm 流程实例关联单
     * @return 结果
     */
    public int updateWfDeployForm(WfDeployForm wfDeployForm);

    /**
     * 删除流程实例关联单
     *
     * @param deployId 流程实例关联单主键
     * @return 结果
     */
    public int deleteWfDeployFormByDeployId(String deployId);

    /**
     * 批量删除流程实例关联单
     *
     * @param deployIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfDeployFormByDeployIds(String[] deployIds);
    /**
     * 批量新增流程实例关联单
     *
     * @param wfDeployForms 流程实例关联
     * @return 结果
     */
    public int insertBatchWfCopy(List<WfDeployForm> wfDeployForms);
}
