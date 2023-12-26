package com.ruoyi.flowable.service.definition.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.flowable.domain.definition.BpmForm;
import com.ruoyi.flowable.mapper.definition.BpmFormMapper;
import com.ruoyi.flowable.service.definition.IBpmFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作流的单定义Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmFormServiceImpl implements IBpmFormService{
    @Autowired
    private BpmFormMapper bpmFormMapper;

    /**
     * 查询工作流的单定义
     *
     * @param id 工作流的单定义主键
     * @return 工作流的单定义
     */
    @Override
    public BpmForm selectBpmFormById(Long id){
        return bpmFormMapper.selectBpmFormById(id);
    }

    /**
     * 查询工作流的单定义列表
     *
     * @param bpmForm 工作流的单定义
     * @return 工作流的单定义
     */
    @Override
    public List<BpmForm> selectBpmFormList(BpmForm bpmForm){
        return bpmFormMapper.selectBpmFormList(bpmForm);
    }

    /**
     * 新增工作流的单定义
     *
     * @param bpmForm 工作流的单定义
     * @return 结果
     */
    @Override
    public int insertBpmForm(BpmForm bpmForm){
        bpmForm.setCreateTime(DateUtils.getNowDate());
        return bpmFormMapper.insertBpmForm(bpmForm);
    }

    /**
     * 修改工作流的单定义
     *
     * @param bpmForm 工作流的单定义
     * @return 结果
     */
    @Override
    public int updateBpmForm(BpmForm bpmForm){
        bpmForm.setUpdateTime(DateUtils.getNowDate());
        return bpmFormMapper.updateBpmForm(bpmForm);
    }

    /**
     * 批量删除工作流的单定义
     *
     * @param ids 需要删除的工作流的单定义主键
     * @return 结果
     */
    @Override
    public int deleteBpmFormByIds(Long[] ids){
        return bpmFormMapper.deleteBpmFormByIds(ids);
    }

    /**
     * 删除工作流的单定义信息
     *
     * @param id 工作流的单定义主键
     * @return 结果
     */
    @Override
    public int deleteBpmFormById(Long id){
        return bpmFormMapper.deleteBpmFormById(id);
    }
}
