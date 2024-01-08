package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.PageResult;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmForm;
import com.ruoyi.flowable.domain.definition.BpmModel;
import com.ruoyi.flowable.domain.definition.BpmProcessDefinitionExt;
import com.ruoyi.flowable.domain.definition.vo.BpmModelMetaInfoVo;
import com.ruoyi.flowable.domain.definition.vo.BpmProcessDefinitionVo;
import com.ruoyi.flowable.mapper.definition.BpmProcessDefinitionExtMapper;
import com.ruoyi.flowable.service.definition.IBpmFormService;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.geometry.spherical.oned.ArcsSet;
import org.apache.ibatis.jdbc.SQL;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.collection.CollectionUtils.*;
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
    public List<BpmProcessDefinitionVo> getProcessDefinitionPage(BpmProcessDefinitionVo processDefinitionVo) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StrUtil.isNotBlank(processDefinitionVo.getKey())) {
            definitionQuery.processDefinitionKey(processDefinitionVo.getKey());
        }

        // 执行查询
        List<ProcessDefinition> processDefinitions = definitionQuery.orderByProcessDefinitionVersion().desc()
                .listPage(pageNum - 1, pageSize);

        // 获得 Deployment Map
        Set<String> deploymentIds =new HashSet<>();

        processDefinitions.forEach(definition -> addIfNotNull(deploymentIds, definition.getDeploymentId()));

        // 获得 BpmProcessDefinitionDO Map
        Map<String, Deployment> deploymentMap = getDeploymentMap(deploymentIds);
        List<String> processDefinitionIds = convertList(processDefinitions, ProcessDefinition::getId);
        List<BpmProcessDefinitionExt> bpmProcessDefinitionExts =new ArrayList<>();
        if (CollUtil.isNotEmpty(processDefinitionIds) && processDefinitionIds.size()>0){
            bpmProcessDefinitionExts = processDefinitionMapper.selectListByProcessDefinitionIds(
                    processDefinitionIds);

        }

        Map<String, BpmProcessDefinitionExt> processDefinitionDOMap = convertMap(bpmProcessDefinitionExts,
                BpmProcessDefinitionExt::getProcessDefinitionId);

        // 获得 Form Map
        List<Long> formIdsList = convertList(bpmProcessDefinitionExts, BpmProcessDefinitionExt::getFormId);
        // 获得 Form Map
        Map<Long, BpmForm> formMap = formService.getFormMap(formIdsList);

        long definitionCount = definitionQuery.count();

        return  convertBpmProcessDefinitionVoList(processDefinitions, deploymentMap,
                processDefinitionDOMap,formMap);

    }

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
    public String createProcessDefinition(BpmProcessDefinitionVo bpmProcessDefinitionVo){
        // 创建 Deployment 部署
        Deployment deploy = repositoryService.createDeployment()
                .key(bpmProcessDefinitionVo.getKey()).name(bpmProcessDefinitionVo.getName()).category(bpmProcessDefinitionVo.getCategory())
                .addBytes(bpmProcessDefinitionVo.getKey() + BPMN_FILE_SUFFIX, bpmProcessDefinitionVo.getBpmnBytes())
                .deploy();

        // 设置 ProcessDefinition 的 category 分类
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId()).singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), bpmProcessDefinitionVo.getCategory());
        // 注意 1，ProcessDefinition 的 key 和 name 是通过 BPMN 中的 <bpmn2:process /> 的 id 和 name 决定
        // 注意 2，目前该项目的设计上，需要保证 Model、Deployment、ProcessDefinition 使用相同的 key，保证关联性。
        //          否则，会导致 ProcessDefinition 的分页无法查询到。
        if (!Objects.equals(definition.getKey(), bpmProcessDefinitionVo.getKey())) {
//            流程定义的标识期望是({})，当前是({})，请修改 BPMN 流程图
            throw new ServiceException(String.format("部署流程失败，原因：流程定义的标识期望是(%s)，当前是(%s)，请修改 BPMN 流程图!", bpmProcessDefinitionVo.getKey(), definition.getKey()), HttpStatus.ERROR);
        }
        if (!Objects.equals(definition.getName(), bpmProcessDefinitionVo.getName())) {
//            流程定义的名字期望是({})，当前是({})，请修改 BPMN 流程图
            throw new ServiceException(String.format("部署流程失败，原因：流程定义的名称期望是(%s)，当前是(%s)，请修改 BPMN 流程图!", bpmProcessDefinitionVo.getName(), definition.getName()), HttpStatus.ERROR);
        }

        BpmProcessDefinitionExt bpmProcessDefinitionExt =new BpmProcessDefinitionExt();
        bpmProcessDefinitionExt.setProcessDefinitionId(definition.getId());
        bpdVOToBpdeData(bpmProcessDefinitionExt,bpmProcessDefinitionVo);
        // 插入数据
       processDefinitionMapper.insertBpmProcessDefinitionExt(bpmProcessDefinitionExt);
        return definition.getId();
    }

    private void bpdVOToBpdeData(BpmProcessDefinitionExt ext, BpmProcessDefinitionVo vo){
        ext.setModelId( vo.getModelId() );
        ext.setDescription( vo.getDescription() );
        ext.setFormType( vo.getFormType() );
        ext.setFormId( vo.getFormId() );
        ext.setFormConf( vo.getFormConf() );
        List<String> list = vo.getFormFields();
        if ( !CollUtil.isEmpty(list) && list.size()>0 ) {
            ext.setFormFields(String.join(",", list));
        }
        ext.setFormCustomCreatePath( vo.getFormCustomCreatePath() );
        ext.setFormCustomViewPath( vo.getFormCustomViewPath() );
    }

    @Override
    public void updateProcessDefinitionState(String id, Integer state){
        // 激活
        if (Objects.equals(SuspensionState.ACTIVE.getStateCode(), state)) {
            repositoryService.activateProcessDefinitionById(id, false, null);
            return;
        }
        // 挂起
        if (Objects.equals(SuspensionState.SUSPENDED.getStateCode(), state)) {
            // suspendProcessInstances = false，进行中的任务，不进行挂起。
            // 原因：只要新的流程不允许发起即可，老流程继续可以执行。
            repositoryService.suspendProcessDefinitionById(id, false, null);
            return;
        }
        log.error("[updateProcessDefinitionState][流程定义({}) 修改未知状态({})]", id, state);
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
    public boolean isProcessDefinitionEquals(BpmProcessDefinitionVo bpmProcessDefinitionVo){
        return false;
    }

    @Override
    public BpmProcessDefinitionVo getProcessDefinitionExt(String id){
        return null;
    }

    @Override
    public ProcessDefinition getProcessDefinition(String id){
        return repositoryService.getProcessDefinition(id);
    }

    @Override
    public ProcessDefinition getProcessDefinition2(String id){
        return null;
    }

    @Override
    public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId){
        if (StrUtil.isEmpty(deploymentId)) {
            return null;
        }
        return repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitionListByDeploymentIds(Set<String> deploymentIds){
        if (CollUtil.isEmpty(deploymentIds)) {
            return emptyList();
        }
        return repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).list();
    }

    @Override
    public ProcessDefinition getActiveProcessDefinition(String key){
        return repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).active().singleResult();
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
            addIfNotNull(list, getDeployment(id));
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

    private List<BpmProcessDefinitionVo> convertBpmProcessDefinitionVoList(List<ProcessDefinition> list,
                                                                           Map<String, Deployment> deploymentMap,
                                                                           Map<String, BpmProcessDefinitionExt> processDefinitionDOMap,
                                                                           Map<Long, BpmForm> formMap) {
        return CollectionUtils.convertList(list, definition -> {
            Deployment deployment = definition.getDeploymentId() != null ? deploymentMap.get(definition.getDeploymentId()) : null;
            BpmProcessDefinitionExt definitionDO = processDefinitionDOMap.get(definition.getId());
            BpmForm form = definitionDO != null ? formMap.get(definitionDO.getFormId()) : null;
            BpmProcessDefinitionVo vo = new BpmProcessDefinitionVo();
            vo.setId( definition.getId() );
            vo.setVersion( definition.getVersion() );
            vo.setName( definition.getName() );
            vo.setDescription( definition.getDescription() );
            vo.setCategory( definition.getCategory() );
            vo.setSuspensionState(definition.isSuspended() ? SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode());
            if (deployment != null) {
                vo.setDeploymentTime(LocalDateTimeUtil.of(deployment.getDeploymentTime()));
            }
            if (form != null) {
                vo.setFormName(form.getName());
            }
            if ( definitionDO != null ) {
                vo.setDescription( definitionDO.getDescription() );
                vo.setFormType( definitionDO.getFormType() );
                vo.setFormId( definitionDO.getFormId() );
                vo.setFormConf( definitionDO.getFormConf() );
                if ( vo.getFormFields() != null ) {
                    String formFields = definitionDO.getFormFields();
                    if ( StringUtils.isNotBlank(formFields) ) {
                        List<String> list1 = Arrays.asList(formFields.split(","));
                        vo.getFormFields().clear();
                        vo.getFormFields().addAll(list1);
                    }else {
                        vo.setFormFields( null );
                    }
                }
                else {
                    String formFields = definitionDO.getFormFields();
                    if ( StringUtils.isNotBlank(formFields) ) {
                    List<String> list2 = Arrays.asList(formFields.split(","));
                        vo.setFormFields( new ArrayList<String>( list2 ) );
                    }
                }
                vo.setFormCustomCreatePath( definitionDO.getFormCustomCreatePath() );
                vo.setFormCustomViewPath( definitionDO.getFormCustomViewPath() );
            }

            return vo;

        });
    }

}
