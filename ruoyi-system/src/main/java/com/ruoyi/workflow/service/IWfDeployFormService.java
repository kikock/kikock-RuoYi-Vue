package com.ruoyi.workflow.service;


import com.ruoyi.workflow.domain.WfDeployForm;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import org.flowable.bpmn.model.BpmnModel;

import java.util.List;

/**
 * 流程实例关联单Service接口
 *
 * @author kikock
 * @date 2024-02-02
 */
public interface IWfDeployFormService{
    /**
     * 查询流程实例关联单
     *
     * @param deployId 流程实例关联单主键
     * @return 流程实例关联单
     */
    public WfDeployForm selectWfDeployFormByDeployId(String deployId);

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
     * 批量删除流程实例关联单
     *
     * @param deployIds 需要删除的流程实例关联单主键集合
     * @return 结果
     */
    public int deleteWfDeployFormByDeployIds(String[] deployIds);

    /**
     * 删除流程实例关联单信息
     *
     * @param deployId 流程实例关联单主键
     * @return 结果
     */
    public int deleteWfDeployFormByDeployId(String deployId);





    /**
     * 保存流程实例关联表单
     *
     * @param deployId  部署ID
     * @param bpmnModel bpmnModel对象
     * @return
     */
    public int saveInternalDeployForm(String deployId, BpmnModel bpmnModel);

    /**
     * 查询流程挂着的表单
     *
     * @param deployId
     * @return
     */
    @Deprecated
    public WfFormVo selectDeployFormByDeployId(String deployId);
}
