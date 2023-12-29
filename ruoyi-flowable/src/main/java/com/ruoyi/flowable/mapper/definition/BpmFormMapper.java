package com.ruoyi.flowable.mapper.definition;

import com.ruoyi.flowable.domain.definition.BpmForm;

import java.util.Collection;
import java.util.List;

/**
 * 工作流的自定义表单Mapper接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface BpmFormMapper{
    /**
     * 查询工作流的自定义表单
     *
     * @param id 工作流的自定义表单主键
     * @return 工作流的自定义表单
     */
    public BpmForm selectBpmFormById(Long id);

    /**
     * 查询工作流的自定义表单列表
     *
     * @param bpmForm 工作流的自定义表单
     * @return 工作流的自定义表单集合
     */
    public List<BpmForm> selectBpmFormList(BpmForm bpmForm);

    /**
     * 新增工作流的自定义表单
     *
     * @param bpmForm 工作流的自定义表单
     * @return 结果
     */
    public int insertBpmForm(BpmForm bpmForm);

    /**
     * 修改工作流的自定义表单
     *
     * @param bpmForm 工作流的自定义表单
     * @return 结果
     */
    public int updateBpmForm(BpmForm bpmForm);

    /**
     * 删除工作流的自定义表单
     *
     * @param id 工作流的自定义表单主键
     * @return 结果
     */
    public int deleteBpmFormById(Long id);

    /**
     * 批量删除工作流的自定义表单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBpmFormByIds(Long[] ids);
    /**
     * 批量查询工作流的自定义表单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public List<BpmForm> selectBatchIds(Collection<Long> ids);
}
