package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.flowable.domain.definition.model.*;
import com.ruoyi.flowable.domain.definition.vo.BpmModelMetaInfoVo;
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
import java.util.Objects;


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

    /**
     * 创建流程模型
     *
     * @param modelVO 创建信息
     * @param bpmnXml BPMN XML
     * @return 创建的流程模型的编号
     */
    @Override
    public AjaxResult createModel(BpmModelCreateReqVO modelVO, String bpmnXml){
        //校验流程标识正则
        String regex = "^[a-zA-Z_][a-zA-Z0-9\\-_.]*$";
        if (!modelVO.getKey().matches(regex)) {
            throw new ServiceException("流程标识格式错误:以字母或下划线开头，后接任意字母、数字、中划线、下划线或句点", HttpStatus.ERROR);
        }
        // 校验流程标识已经存在
        Model keyModel = getModelByKey(modelVO.getKey());
        if (keyModel != null) {
            System.out.println(JSONUtil.toJsonStr(keyModel));
            throw new ServiceException("该流程已经存在", HttpStatus.ERROR);
        }
        // 创建流程定义
        Model model = repositoryService.newModel();

        model.setName(modelVO.getName());
        model.setKey(modelVO.getKey());
        model.setMetaInfo(buildMetaInfoStr(null, modelVO.getDescription(), null, null,
                null, null));
        // 保存流程定义
        repositoryService.saveModel(model);
        // 保存 BPMN XML
        saveModelBpmnXml(model, bpmnXml);
        //成功返回模板id
        return AjaxResult.success().put("id", model.getId());
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

    /**
     * 根据流程标识查询流程模型
     *
     * @param key 流程标识
     * @return 流程模型
     */
    private Model getModelByKey(String key){
        return repositoryService.createModelQuery().modelKey(key).singleResult();
    }

    /**
     * 保存流程模板
     *
     * @param model   模板对象
     * @param bpmnXml 模板对象
     */
    private void saveModelBpmnXml(Model model, String bpmnXml){
        if (StrUtil.isEmpty(bpmnXml)) {
            return;
        }
        repositoryService.addModelEditorSource(model.getId(), StrUtil.utf8Bytes(bpmnXml));
    }

    /**
     * 流程模板参数配置
     */
    private String buildMetaInfoStr(BpmModelMetaInfoVo metaInfo, String description, Integer formType,
                                    Long formId, String formCustomCreatePath, String formCustomViewPath){
        if (metaInfo == null) {
            metaInfo = new BpmModelMetaInfoVo();
        }
        // 只有非空，才进行设置，避免更新时的覆盖
        if (StrUtil.isNotEmpty(description)) {
            metaInfo.setDescription(description);
        }
        if (Objects.nonNull(formType)) {
            metaInfo.setFormType(formType);
            metaInfo.setFormId(formId);
            metaInfo.setFormCustomCreatePath(formCustomCreatePath);
            metaInfo.setFormCustomViewPath(formCustomViewPath);
        }
        return JSONUtil.toJsonStr(metaInfo);
    }
}
