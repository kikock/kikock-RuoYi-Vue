package com.ruoyi.workflow.service;

import com.ruoyi.flowable.core.domain.ProcessQuery;
import com.ruoyi.workflow.domain.vo.WfDeployVo;

import java.util.List;

/**
 * @author KonBAI
 * @createTime 2022/6/30 9:03
 */
public interface IWfDeployService {

    List<WfDeployVo> queryPageList(ProcessQuery processQuery);

    List<WfDeployVo> queryPublishList(String processKey);

    void updateState(String definitionId, String stateCode);

    String queryBpmnXmlById(String definitionId);

    void deleteByIds(List<String> deployIds);
}
