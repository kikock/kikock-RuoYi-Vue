package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.flowable.domain.definition.model.*;
import com.ruoyi.flowable.service.definition.IBpmFormService;
import com.ruoyi.flowable.service.definition.IBpmModelService;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceExtService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


/**
 * Flowable流程模型实现
 * 主要进行 Flowable {@link Model} 的维护
 */
@Service
@Validated
@Slf4j
public class BpmModelServiceImpl implements IBpmModelService{

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private IBpmProcessInstanceExtService processDefinitionService;
    @Resource
    private IBpmFormService bpmFormService;
    @Resource
    private IBpmTaskAssignRuleService taskAssignRuleService;

    @Override
    public List<BpmModelPageItemRespVO> getModelPage(BpmModelPageReqVO pageVO){
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StrUtil.isNotBlank(pageVO.getKey())) {
            modelQuery.modelKey(pageVO.getKey());
        }
        if (StrUtil.isNotBlank(pageVO.getName())) {
            modelQuery.modelNameLike("%" + pageVO.getName() + "%"); // 模糊匹配
        }
        if (StrUtil.isNotBlank(pageVO.getCategory())) {
            modelQuery.modelCategory(pageVO.getCategory());
        }
        // 执行查询
//        List<Model> models = modelQuery.orderByCreateTime().desc()
//                .listPage(PageUtils.getStart(pageVO), pageVO.getPageSize());
//
//        // 获得 Form Map
//        Set<Long> formIds = CollectionUtils.convertSet(models, model -> {
//            BpmModelMetaInfoRespDTO metaInfo = JsonUtils.parseObject(model.getMetaInfo(), BpmModelMetaInfoRespDTO.class);
//            return metaInfo != null ? metaInfo.getFormId() : null;
//        });
//        Map<Long, BpmFormDO> formMap = bpmFormService.getFormMap(formIds);
//
//        // 获得 Deployment Map
//        Set<String> deploymentIds = new HashSet<>();
//        models.forEach(model -> CollectionUtils.addIfNotNull(deploymentIds, model.getDeploymentId()));
//        Map<String, Deployment> deploymentMap = processDefinitionService.getDeploymentMap(deploymentIds);
//        // 获得 ProcessDefinition Map
//        List<ProcessDefinition> processDefinitions = processDefinitionService.getProcessDefinitionListByDeploymentIds(deploymentIds);
//        Map<String, ProcessDefinition> processDefinitionMap = convertMap(processDefinitions, ProcessDefinition::getDeploymentId);
//
//        // 拼接结果
//        long modelCount = modelQuery.count();
//        return new PageResult<>(BpmModelConvert.INSTANCE.convertList(models, formMap, deploymentMap, processDefinitionMap), modelCount);
        return null;
    }

    @Override
    public int createModel(BpmModelCreateReqVO modelVO, String bpmnXml){
        return 0;
    }

    @Override
    public BpmModelRespVO getModel(String id){
        return null;
    }

    @Override
    public int updateModel(BpmModelUpdateReqVO updateReqVO){
        return 0;
    }

    @Override
    public void deployModel(String id){

    }

    @Override
    public void deleteModel(String id){

    }

    @Override
    public void updateModelState(String id, Integer state){

    }

    @Override
    public BpmnModel getBpmnModel(String id){
        return null;
    }

    @Override
    public BpmnModel getBpmnModelByDefinitionId(String processDefinitionId){
        return null;
    }


}
