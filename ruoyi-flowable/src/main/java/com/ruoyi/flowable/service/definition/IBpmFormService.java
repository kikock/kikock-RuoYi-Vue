package com.ruoyi.flowable.service.definition;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmForm;

import java.util.*;

/**
 * 工作流的自定义表单Service接口
 *
 * @author kikock
 * @date 2023-12-22
 */
public interface IBpmFormService{
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
     * 批量删除工作流的自定义表单
     *
     * @param ids 需要删除的工作流的自定义表单主键集合
     * @return 结果
     */
    public int deleteBpmFormByIds(Long[] ids);

    /**
     * 删除工作流的自定义表单信息
     *
     * @param id 工作流的自定义表单主键
     * @return 结果
     */
    public int deleteBpmFormById(Long id);
    /**
     * 获取工作流表单列表
     *
     * @param id 工作流的自定义表单主键
     * @return 结果
     */
    List<BpmForm> getFormList(Collection<Long> ids);

    /**
     * 获得动态自定义表单 Map
     *
     * @param formIds 编号集合
     * @return 动态表单 Map
     */
    public Map<Long,BpmForm> getFormMap(Set<Long> formIds);

}
