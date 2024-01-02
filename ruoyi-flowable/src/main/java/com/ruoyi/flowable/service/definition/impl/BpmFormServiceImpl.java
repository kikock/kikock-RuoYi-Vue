package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmForm;
import com.ruoyi.flowable.mapper.definition.BpmFormMapper;
import com.ruoyi.flowable.service.definition.IBpmFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工作流的自定义表单Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmFormServiceImpl implements IBpmFormService{

    @Resource
    private BpmFormMapper bpmFormMapper;

    /**
     * 查询工作流的自定义表单
     *
     * @param id 工作流的自定义表单主键
     * @return 工作流的自定义表单
     */
    @Override
    public BpmForm selectBpmFormById(Long id){
        return bpmFormMapper.selectBpmFormById(id);
    }

    /**
     * 查询工作流的自定义表单列表
     *
     * @param bpmForm 工作流的自定义表单
     * @return 工作流的自定义表单
     */
    @Override
    public List<BpmForm> selectBpmFormList(BpmForm bpmForm){
        return bpmFormMapper.selectBpmFormList(bpmForm);
    }

    /**
     * 新增工作流的自定义表单
     *
     * @param bpmForm 工作流的自定义表单
     * @return 结果
     */
    @Override
    public int insertBpmForm(BpmForm bpmForm){
        bpmForm.setCreateTime(DateUtils.getNowDate());
        List<String> fieldsArr = bpmForm.getFieldsArr();
        if (CollectionUtil.isNotEmpty(fieldsArr)) {
            bpmForm.setFields(fieldsArr.toString());
        }
        return bpmFormMapper.insertBpmForm(bpmForm);
    }

    /**
     * 修改工作流的自定义表单
     *
     * @param bpmForm 工作流的自定义表单
     * @return 结果
     */
    @Override
    public int updateBpmForm(BpmForm bpmForm){
        bpmForm.setUpdateTime(DateUtils.getNowDate());
        List<String> fieldsArr = bpmForm.getFieldsArr();
        if (CollectionUtil.isNotEmpty(fieldsArr)) {
            bpmForm.setFields(fieldsArr.toString());
        }
        return bpmFormMapper.updateBpmForm(bpmForm);
    }

    /**
     * 批量删除工作流的自定义表单
     *
     * @param ids 需要删除的工作流的自定义表单主键
     * @return 结果
     */
    @Override
    public int deleteBpmFormByIds(Long[] ids){
        return bpmFormMapper.deleteBpmFormByIds(ids);
    }

    /**
     * 删除工作流的自定义表单信息
     *
     * @param id 工作流的自定义表单主键
     * @return 结果
     */
    @Override
    public int deleteBpmFormById(Long id){
        return bpmFormMapper.deleteBpmFormById(id);
    }

    @Override
    public List<BpmForm> getFormList(List<Long> ids){
        return bpmFormMapper.selectBatchIds(ids);
    }

    /**
     * 获得动态表单 Map
     *
     * @param formIds 表单id集合
     * @return 动态表单 Map
     */
    @Override
    public Map<Long,BpmForm> getFormMap(List<Long> formIds){
        if (CollUtil.isEmpty(formIds)) {
            return Collections.emptyMap();
        }
        return CollectionUtils.convertMap(getFormList(formIds), BpmForm::getId);
    }

    @Override
    public List<BpmForm> flowFormDatas(String keywords){
        return bpmFormMapper.flowFormDatas(keywords);
    }
}
