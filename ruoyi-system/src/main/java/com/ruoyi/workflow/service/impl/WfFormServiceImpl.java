package com.ruoyi.workflow.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.workflow.domain.WfForm;
import com.ruoyi.workflow.domain.bo.WfFormBo;
import com.ruoyi.workflow.mapper.WfFormMapper;
import com.ruoyi.workflow.service.IWfFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 流程单信息Service业务层处理
 *
 * @author kikock
 * @date 2024-02-02
 */
@Service
public class WfFormServiceImpl implements IWfFormService{
    @Autowired
    private WfFormMapper wfFormMapper;

    /**
     * 查询流程单信息
     *
     * @param formId 流程单信息主键
     * @return 流程单信息
     */
    @Override
    public WfForm selectWfFormByFormId(Long formId){
        return wfFormMapper.selectWfFormByFormId(formId);
    }

    /**
     * 查询流程单信息列表
     *
     * @param wfForm 流程单信息
     * @return 流程单信息
     */
    @Override
    public List<WfForm> selectWfFormList(WfFormBo wfForm){
        return wfFormMapper.selectWfFormList(wfForm);
    }

    /**
     * 新增流程单信息
     *
     * @param wfForm 流程单信息
     * @return 结果
     */
    @Override
    public int insertWfForm(WfForm wfForm){
        wfForm.setCreateTime(DateUtils.getNowDate());
        List<String> fieldsArr = wfForm.getFieldsArr();
        if (CollectionUtil.isNotEmpty(fieldsArr)) {
            wfForm.setFields(fieldsArr.toString());
        }
        return wfFormMapper.insertWfForm(wfForm);
    }

    /**
     * 修改流程单信息
     *
     * @param wfForm 流程单信息
     * @return 结果
     */
    @Override
    public int updateWfForm(WfForm wfForm){
        wfForm.setUpdateTime(DateUtils.getNowDate());
        List<String> fieldsArr = wfForm.getFieldsArr();
        if (CollectionUtil.isNotEmpty(fieldsArr)) {
            wfForm.setFields(fieldsArr.toString());
        }
        return wfFormMapper.updateWfForm(wfForm);
    }

    /**
     * 批量删除流程单信息
     *
     * @param formIds 需要删除的流程单信息主键
     * @return 结果
     */
    @Override
    public int deleteWfFormByFormIds(Long[] formIds){
        return wfFormMapper.deleteWfFormByFormIds(formIds);
    }

    /**
     * 删除流程单信息信息
     *
     * @param formId 流程单信息主键
     * @return 结果
     */
    @Override
    public int deleteWfFormByFormId(Long formId){
        return wfFormMapper.deleteWfFormByFormId(formId);
    }
    @Override
    public List<SelectMoreVo> getSimpleList(String keywords){
        return wfFormMapper.getSimpleList(keywords);
    }
}
