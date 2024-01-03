package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmProcessDefinitionExt;
import com.ruoyi.flowable.domain.definition.vo.BpmProcessDefinitionVo;
import com.ruoyi.flowable.mapper.definition.BpmProcessDefinitionExtMapper;
import com.ruoyi.flowable.service.definition.IBpmFormService;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static com.ruoyi.common.utils.collection.CollectionUtils.convertList;
import static com.ruoyi.common.utils.collection.CollectionUtils.convertMap;
import static java.util.Collections.emptyList;

/**
 * 流程定义实现
 * 主要进行 Flowable {@link ProcessDefinition} 和 {@link Deployment} 的维护
 *
 * @author yunlongn
 * @author ZJQ
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class BpmProcessDefinitionServiceImpl implements IBpmProcessDefinitionService{

    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private BpmProcessDefinitionExtMapper processDefinitionMapper;

    @Resource
    private IBpmFormService formService;


    @Override
    public List<BpmProcessDefinitionVo> getProcessDefinitionList(BpmProcessDefinitionVo listReqVO){
        // 拼接查询条件
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        if (Objects.equals(SuspensionState.SUSPENDED.getStateCode(), listReqVO.getSuspensionState())) {
            definitionQuery.suspended();
        } else if (Objects.equals(SuspensionState.ACTIVE.getStateCode(), listReqVO.getSuspensionState())) {
            definitionQuery.active();
        }
        // 执行查询
        List<ProcessDefinition> processDefinitions = definitionQuery.list();
        if (CollUtil.isEmpty(processDefinitions)) {
            return Collections.emptyList();
        }
        // 获得 BpmProcessDefinitionDO Map
        List<BpmProcessDefinitionExt> processDefinitionDOs = processDefinitionMapper.selectListByProcessDefinitionIds(
                convertList(processDefinitions, ProcessDefinition::getId));
        Map<String,BpmProcessDefinitionExt> processDefinitionDOMap = convertMap(processDefinitionDOs,
                BpmProcessDefinitionExt::getProcessDefinitionId);
        // 执行查询，并返回
        List<BpmProcessDefinitionExt> list = new ArrayList<>();
        processDefinitionDOMap.values().forEach(list::add);
        System.out.println(list);
        return Collections.emptyList();
    }

    @Override
    public String createProcessDefinition(BpmProcessDefinitionVo createReqDTO){
        return null;
    }

    @Override
    public void updateProcessDefinitionState(String id, Integer state){

    }

    @Override
    public String getProcessDefinitionBpmnXML(String id){
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        if (bpmnModel == null) {
            return null;
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return StrUtil.utf8Str(converter.convertToXML(bpmnModel));
    }

    @Override
    public boolean isProcessDefinitionEquals(BpmProcessDefinitionVo createReqDTO){
        return false;
    }

    @Override
    public BpmProcessDefinitionVo getProcessDefinitionExt(String id){
        return null;
    }

    @Override
    public ProcessDefinition getProcessDefinition(String id){
        return null;
    }

    @Override
    public ProcessDefinition getProcessDefinition2(String id){
        return null;
    }

    @Override
    public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId){
        return null;
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitionListByDeploymentIds(Set<String> deploymentIds){
        return null;
    }

    @Override
    public ProcessDefinition getActiveProcessDefinition(String key){
        return null;
    }

    @Override
    public Map<String,Deployment> getDeploymentMap(Set<String> ids){
        return IBpmProcessDefinitionService.super.getDeploymentMap(ids);
    }

    @Override
    public List<Deployment> getDeployments(Set<String> ids){
        if (CollUtil.isEmpty(ids)) {
            return emptyList();
        }
        List<Deployment> list = new ArrayList<>(ids.size());
        for (String id : ids) {
            CollectionUtils.addIfNotNull(list, getDeployment(id));
        }
        return list;
    }

    @Override
    public Deployment getDeployment(String id){
        if (StrUtil.isEmpty(id)) {
            return null;
        }
        return repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
    }

    @Override
    public BpmnModel getBpmnModel(String processDefinitionId){
        return repositoryService.getBpmnModel(processDefinitionId);
    }


}
