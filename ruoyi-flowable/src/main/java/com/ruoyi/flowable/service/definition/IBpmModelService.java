package com.ruoyi.flowable.service.definition;

import cn.hutool.db.PageResult;
import com.ruoyi.flowable.domain.definition.model.*;
import org.flowable.bpmn.model.BpmnModel;

import javax.validation.Valid;
import java.util.List;

/**
 * Flowable流程模型接口
 *
 * @author yunlongn
 */
public interface IBpmModelService{
    /**
     * 获得流程模型列表数据
     *
     * @param pageVO 分页查询
     * @return 流程模型分页
     */
    public List<BpmModelPageItemRespVO> getModelPage(BpmModelPageReqVO pageVO);

    /**
     * 创建流程模型
     *
     * @param modelVO 创建信息
     * @param bpmnXml BPMN XML
     * @return 创建的流程模型的编号
     */
    public int createModel(@Valid BpmModelCreateReqVO modelVO, String bpmnXml);

    /**
     * 获得流程模块
     *
     * @param id 编号
     * @return 流程模型
     */
    public BpmModelRespVO getModel(String id);

    /**
     * 修改流程模型
     *
     * @param updateReqVO 更新信息
     */
    public  int updateModel(@Valid BpmModelUpdateReqVO updateReqVO);

    /**
     * 将流程模型，部署成一个流程定义
     *
     * @param id 编号
     */
    public void deployModel(String id);

    /**
     * 删除模型
     *
     * @param id 编号
     */
    public void deleteModel(String id);

    /**
     * 修改模型的状态，实际更新的部署的流程定义的状态
     *
     * @param id 编号
     * @param state 状态
     */
    public void updateModelState(String id, Integer state);

    /**
     * 获得流程模型编号对应的 BPMN Model
     *
     * @param id 流程模型编号
     * @return BPMN Model
     */
    public BpmnModel getBpmnModel(String id);

    /**
     * 获得流程定义编号对应的 BPMN Model
     *
     * @param processDefinitionId 流程定义编号
     * @return BPMN Model
     */
    public BpmnModel getBpmnModelByDefinitionId(String processDefinitionId);

}
