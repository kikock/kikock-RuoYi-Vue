package com.ruoyi.workflow.service;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.workflow.domain.WfCopy;
import com.ruoyi.workflow.domain.bo.WfTaskBo;

import java.util.List;

/**
 * 流程抄送Service接口
 *
 * @author kikock
 * @date 2024-02-02
 */
public interface IWfCopyService{
    /**
     * 查询流程抄送
     *
     * @param copyId 流程抄送主键
     * @return 流程抄送
     */
    public WfCopy selectWfCopyByCopyId(Long copyId);

    /**
     * 查询流程抄送列表
     *
     * @param wfCopy 流程抄送
     * @return 流程抄送集合
     */
    public List<WfCopy> selectWfCopyList(WfCopy wfCopy);

    /**
     * 新增流程抄送
     *
     * @param wfCopy 流程抄送
     * @return 结果
     */
    public int insertWfCopy(WfCopy wfCopy);

    /**
     * 修改流程抄送
     *
     * @param wfCopy 流程抄送
     * @return 结果
     */
    public int updateWfCopy(WfCopy wfCopy);

    /**
     * 批量删除流程抄送
     *
     * @param copyIds 需要删除的流程抄送主键集合
     * @return 结果
     */
    public int deleteWfCopyByCopyIds(Long[] copyIds);

    /**
     * 删除流程抄送信息
     *
     * @param copyId 流程抄送主键
     * @return 结果
     */
    public int deleteWfCopyByCopyId(Long copyId);

    /**
     * 抄送
     *
     * @param taskBo
     * @return
     */
    public AjaxResult makeCopy(WfTaskBo taskBo);

}
