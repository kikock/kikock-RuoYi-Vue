package com.ruoyi.workflow.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.flowable.utils.ModelUtils;
import com.ruoyi.workflow.domain.WfDeployForm;
import com.ruoyi.workflow.domain.WfForm;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import com.ruoyi.workflow.mapper.WfDeployFormMapper;
import com.ruoyi.workflow.mapper.WfFormMapper;
import com.ruoyi.workflow.service.IWfDeployFormService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 流程实例关联单Service业务层处理
 *
 * @author kikock
 * @date 2024-02-02
 */
@Service
public class WfDeployFormServiceImpl implements IWfDeployFormService{
    @Autowired
    private WfDeployFormMapper wfDeployFormMapper;
    @Autowired
    private WfFormMapper formMapper;

    /**
     * 查询流程实例关联单
     *
     * @param deployId 流程实例关联单主键
     * @return 流程实例关联单
     */
    @Override
    public WfDeployForm selectWfDeployFormByDeployId(String deployId){
        return wfDeployFormMapper.selectWfDeployFormByDeployId(deployId);
    }

    /**
     * 查询流程实例关联单列表
     *
     * @param wfDeployForm 流程实例关联单
     * @return 流程实例关联单
     */
    @Override
    public List<WfDeployForm> selectWfDeployFormList(WfDeployForm wfDeployForm){
        return wfDeployFormMapper.selectWfDeployFormList(wfDeployForm);
    }

    /**
     * 新增流程实例关联单
     *
     * @param wfDeployForm 流程实例关联单
     * @return 结果
     */
    @Override
    public int insertWfDeployForm(WfDeployForm wfDeployForm){
        // 1.删除部署流程和表单的关联关系
        wfDeployFormMapper.deleteWfDeployFormByDeployId(wfDeployForm.getDeployId());
        // 2.新增部署流程和表单的关联关系
        return wfDeployFormMapper.insertWfDeployForm(wfDeployForm);
    }

    /**
     * 修改流程实例关联单
     *
     * @param wfDeployForm 流程实例关联单
     * @return 结果
     */
    @Override
    public int updateWfDeployForm(WfDeployForm wfDeployForm){
        return wfDeployFormMapper.updateWfDeployForm(wfDeployForm);
    }

    /**
     * 批量删除流程实例关联单
     *
     * @param deployIds 需要删除的流程实例关联单主键
     * @return 结果
     */
    @Override
    public int deleteWfDeployFormByDeployIds(String[] deployIds){
        return wfDeployFormMapper.deleteWfDeployFormByDeployIds(deployIds);
    }

    /**
     * 删除流程实例关联单信息
     *
     * @param deployId 流程实例关联单主键
     * @return 结果
     */
    @Override
    public int deleteWfDeployFormByDeployId(String deployId){
        return wfDeployFormMapper.deleteWfDeployFormByDeployId(deployId);
    }

    /**
     * 保存流程实例关联表单
     *
     * @param deployId  部署ID
     * @param bpmnModel bpmnModel对象
     * @return
     */
    @Override
    public int saveInternalDeployForm(String deployId, BpmnModel bpmnModel){
        List<WfDeployForm> deployFormList = new ArrayList<>();
        // 获取开始节点
        StartEvent startEvent = ModelUtils.getStartEvent(bpmnModel);
        if (ObjectUtil.isNull(startEvent)) {
            throw new RuntimeException("开始节点不存在，请检查流程设计是否有误！");
        }
        // 保存开始节点表单信息
        WfDeployForm startDeployForm = buildDeployForm(deployId, startEvent);
        if (ObjectUtil.isNotNull(startDeployForm)) {
            deployFormList.add(startDeployForm);
        }
        // 保存用户节点表单信息
        Collection<UserTask> userTasks = ModelUtils.getAllUserTaskEvent(bpmnModel);
        if (CollUtil.isNotEmpty(userTasks)) {
            for (UserTask userTask : userTasks) {
                WfDeployForm userTaskDeployForm = buildDeployForm(deployId, userTask);
                if (ObjectUtil.isNotNull(userTaskDeployForm)) {
                    deployFormList.add(userTaskDeployForm);
                }
            }
        }
        // 批量新增部署流程和表单关联信息
        return wfDeployFormMapper.insertBatchWfCopy(deployFormList);
    }

    /**
     * 查询流程挂着的表单
     *
     * @param
     * @return
     */
    @Override
    public WfFormVo selectDeployFormByDeployId(String deployId){
//        wrapper.eq("t2.deploy_id", deployId);
        List<WfFormVo> list = formMapper.selectWfFormVoList(deployId);
        if (ObjectUtil.isNotEmpty(list)) {
            if (list.size() != 1) {
                throw new ServiceException("表单信息查询错误");
            } else {
                return list.get(0);
            }
        } else {
            return null;
        }
    }

    /**
     * 构建部署表单关联信息对象
     * @param deployId 部署ID
     * @param node 节点信息
     * @return 部署表单关联对象。若无表单信息（formKey），则返回null
     */
    private WfDeployForm buildDeployForm(String deployId, FlowNode node) {
        String formKey = ModelUtils.getFormKey(node);
        if (StringUtils.isEmpty(formKey)) {
            return null;
        }
        Long formId = Convert.toLong(StringUtils.substringAfter(formKey, "key_"));
        WfForm wfForm = formMapper.selectWfFormByFormId(formId);
        if (ObjectUtil.isNull(wfForm)) {
            throw new ServiceException("表单信息查询错误");
        }
        WfDeployForm deployForm = new WfDeployForm();
        deployForm.setDeployId(deployId);
        deployForm.setFormKey(formKey);
        deployForm.setNodeKey(node.getId());
        deployForm.setFormType(wfForm.getFormType());
        deployForm.setFormViewPath(wfForm.getFormViewPath());
        deployForm.setFormCreatePath(wfForm.getFormCreatePath());
        deployForm.setFormName(wfForm.getFormName());
        deployForm.setNodeName(node.getName());
        deployForm.setContent(wfForm.getContent());
        deployForm.setConf(wfForm.getConf());
        deployForm.setFields(wfForm.getFields());
        return deployForm;
    }
}
