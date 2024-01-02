package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.PageResult;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmForm;
import com.ruoyi.flowable.domain.definition.BpmModel;
import com.ruoyi.flowable.domain.definition.vo.BpmModelMetaInfoVo;
import com.ruoyi.flowable.domain.definition.vo.BpmModelVo;
import com.ruoyi.flowable.service.definition.IBpmFormService;
import com.ruoyi.flowable.service.definition.IBpmModelService;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceExtService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.collection.CollectionUtils.convertMap;


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
    private IBpmProcessInstanceExtService processInstanceExtService;
    @Resource
    private IBpmProcessDefinitionService processDefinitionService;
    @Resource
    private IBpmFormService bpmFormService;
    @Resource
    private IBpmTaskAssignRuleService taskAssignRuleService;

    @Override
    public List<BpmModel> selectBpmModelList(BpmModel bpmModel){
        List<BpmModel> list = new ArrayList<>();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StrUtil.isNotBlank(bpmModel.getKey())) {
            modelQuery.modelKey(bpmModel.getKey());
        }
        if (StrUtil.isNotBlank(bpmModel.getName())) {
            modelQuery.modelNameLike("%" + bpmModel.getName() + "%"); // 模糊匹配
        }
        if (StrUtil.isNotBlank(bpmModel.getCategory())) {
            modelQuery.modelCategory(bpmModel.getCategory());
        }
        // 执行查询
        List<Model> models = modelQuery.orderByCreateTime().desc()
                .listPage(pageNum - 1, pageSize);
       // 获得表单id集合
        Set<Long> formIds = CollectionUtils.convertSet(models, model -> {
            BpmModelMetaInfoVo metaInfo = JSONUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoVo.class);
            return metaInfo != null ? metaInfo.getFormId() : null;
        });
        // 获得流程表单参数
        List<Long> formIdsList =  formIds.stream().collect(Collectors.toList());
        Map<Long,BpmForm> formMap = bpmFormService.getFormMap(formIdsList);
//        // 获得 Deployment Map
        Set<String> deploymentIds = new HashSet<>();
        models.forEach(model -> CollectionUtils.addIfNotNull(deploymentIds, model.getDeploymentId()));
        Map<String,Deployment> deploymentMap = processDefinitionService.getDeploymentMap(deploymentIds);
//        // 获得 ProcessDefinition Map 流程定义数据
        List<ProcessDefinition> processDefinitions = processDefinitionService.getProcessDefinitionListByDeploymentIds(deploymentIds);
        Map<String, ProcessDefinition> processDefinitionMap = convertMap(processDefinitions, ProcessDefinition::getDeploymentId);
//
//        // 拼接结果
        return convertList(models, formMap, deploymentMap, processDefinitionMap);
    }

    /**
     * 创建流程模型
     *
     * @param modelVO 创建信息
     * @param bpmnXml BPMN XML
     * @return 创建的流程模型的编号
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 因为进行多个操作，所以开启事务
    public AjaxResult createModel(BpmModelVo modelVO, String bpmnXml){
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
    @Transactional(rollbackFor = Exception.class) // 因为进行多个操作，所以开启事务
    public AjaxResult updateModel(BpmModelVo modelVO){
        // 校验流程模型存在
        Model model = repositoryService.getModel(modelVO.getId());
        if (model == null) {
            throw new ServiceException("该流程模型不存在", HttpStatus.ERROR);
        }
        model.setCategory(modelVO.getCategory());
        //            将BpmModelVo 转换成 Model
        if(modelVO.getFormType()==10){
            model.setMetaInfo(buildMetaInfoStr(null, modelVO.getDescription(), modelVO.getFormType(), modelVO.getFormId(),
                    null, null));
        }else {
            model.setMetaInfo(buildMetaInfoStr(null, modelVO.getDescription(), modelVO.getFormType(), null,
                    modelVO.getFormCustomCreatePath(), modelVO.getFormCustomViewPath()));
        }
//        // 更新模型
        repositoryService.saveModel(model);
//        // 更新 BPMN XML
        saveModelBpmnXml(model, modelVO.getBpmnXml());
        return AjaxResult.success().put("id", model.getId());
    }

    @Override
    public BpmModel selectBpmModelById(String id){
        BpmModel modelVO = new BpmModel();
        Model model = repositoryService.getModel(id);
        if (model == null) {
            return modelVO;
        }
//        model 参数
        modelToBpmModel(model, modelVO,id);
        return modelVO;
    }

    @Override
    public AjaxResult updateFlowChart(BpmModel bpmModel){
        AjaxResult result =AjaxResult.success();
        // 校验流程模型存在
        Model model = repositoryService.getModel(bpmModel.getId());
        if (model == null) {
            throw new ServiceException("流程模型不存在!", HttpStatus.ERROR);
        }
        //保存参数构建
        bpmModelToModel(model,bpmModel);
        // 更新模型
        repositoryService.saveModel(model);
        System.out.println(JSONUtil.toJsonStr(bpmModel));
        // 更新 BPMN XML
        saveModelBpmnXml(model, bpmModel.getBpmnXml());
        return result;
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
        byte[] bpmnBytes = repositoryService.getModelEditorSource(id);
        if (ArrayUtil.isEmpty(bpmnBytes)) {
            return null;
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return converter.convertToBpmnModel(new BytesStreamSource(bpmnBytes), true, true);
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

     List<BpmModel> convertList(List<Model> list, Map<Long, BpmForm> formMap,
                                                     Map<String, Deployment> deploymentMap,
                                                     Map<String, ProcessDefinition> processDefinitionMap) {
        return CollectionUtils.convertList(list, model -> {
            BpmModelMetaInfoVo metaInfo = JSONUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoVo.class);
            BpmForm form = metaInfo != null ? formMap.get(metaInfo.getFormId()) : null;
            Deployment deployment = model.getDeploymentId() != null ? deploymentMap.get(model.getDeploymentId()) : null;
            ProcessDefinition processDefinition = model.getDeploymentId() != null ? processDefinitionMap.get(model.getDeploymentId()) : null;
            return convert(model, form, deployment, processDefinition);
        });
     }

    BpmModel convert(Model model, BpmForm form, Deployment deployment, ProcessDefinition processDefinition) {
        BpmModel modelVO = new BpmModel();
//        model 参数
        modelVO.setId(model.getId());
        modelVO.setCreateTime(model.getCreateTime());
        modelVO.setName(model.getName());
        modelVO.setKey(model.getKey());
        modelVO.setCategory(model.getCategory());
        BpmModelMetaInfoVo metaInfo = JSONUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoVo.class);
        modelVO.setDescription( metaInfo.getDescription() );
        modelVO.setFormType( metaInfo.getFormType() );
        modelVO.setFormId( metaInfo.getFormId() );
        modelVO.setFormCustomCreatePath( metaInfo.getFormCustomCreatePath() );
        modelVO.setFormCustomViewPath( metaInfo.getFormCustomViewPath() );

        // Form 参数
        if (form != null) {
            modelVO.setFormId(Long.valueOf(form.getId()));
            modelVO.setFormName(form.getName());
        }
        modelVO.setProcessDefinition(convertToBpmModelProcessDefinition(processDefinition));
        if (modelVO.getProcessDefinition() != null) {
            modelVO.getProcessDefinition().setSuspensionState(processDefinition.isSuspended() ?
                    SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode());
            modelVO.getProcessDefinition().setDeploymentTime(DateUtil.toLocalDateTime(deployment.getDeploymentTime()));
        }
        return modelVO;
    }


    public BpmModel.ProcessDefinition convertToBpmModelProcessDefinition(ProcessDefinition bean) {
        if ( bean == null ) {
            return null;
        }
        BpmModel.ProcessDefinition processDefinition = new BpmModel.ProcessDefinition();
        processDefinition.setId( bean.getId() );
        processDefinition.setVersion( bean.getVersion() );
        return processDefinition;
    }
    /**
     * flowable-model参数转 bpmModel 参数
     */
    private void modelToBpmModel(Model model, BpmModel modelVO,String id) {
        modelVO.setId(model.getId());
        modelVO.setCreateTime(model.getCreateTime());
        modelVO.setName(model.getName());
        modelVO.setKey(model.getKey());
        modelVO.setCategory(model.getCategory());
        BpmModelMetaInfoVo metaInfo = JSONUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoVo.class);
        modelVO.setDescription( metaInfo.getDescription() );
        modelVO.setFormType( metaInfo.getFormType() );
        modelVO.setFormId( metaInfo.getFormId() );
        //
        if( Objects.nonNull(metaInfo.getFormId())) {
            BpmForm bpmForm = bpmFormService.selectBpmFormById(metaInfo.getFormId());
            modelVO.setFormName(bpmForm.getName());
        }
        modelVO.setFormCustomCreatePath( metaInfo.getFormCustomCreatePath() );
        modelVO.setFormCustomViewPath( metaInfo.getFormCustomViewPath());
        // 拼接 bpmn XML
        byte[] bpmnBytes = repositoryService.getModelEditorSource(StringUtils.isNotEmpty(id) ? id : model.getId());
        modelVO.setBpmnXml(StrUtil.utf8Str(bpmnBytes));
    }
    /**
     * bpmModel参数转 flowable-model 参数
     */
    private void bpmModelToModel(Model model, BpmModel bpmModel) {
        model.setName(bpmModel.getName());
        model.setCategory(bpmModel.getCategory());
        model.setMetaInfo(buildMetaInfoStr(JSONUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoVo.class),
                bpmModel.getDescription(), bpmModel.getFormType(), bpmModel.getFormId(),
                bpmModel.getFormCustomCreatePath(), bpmModel.getFormCustomViewPath()));
    }


}
