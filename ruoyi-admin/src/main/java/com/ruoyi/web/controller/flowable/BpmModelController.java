package com.ruoyi.web.controller.flowable;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.flowable.domain.definition.BpmForm;
import com.ruoyi.flowable.domain.definition.BpmModel;
import com.ruoyi.flowable.domain.definition.vo.BpmModelVo;
import com.ruoyi.flowable.service.definition.IBpmModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @project_name: ruoyi-vue-master
 * @description: 流程模型
 * @create_name: kikock
 * @create_date: 2023-12-27 10:18
 **/
@RestController
@RequestMapping("/bpm/model")
public class BpmModelController extends BaseController{
    @Autowired
    private IBpmModelService modelService;

    /**
     * 查询流程模型列表
     */
    @PreAuthorize("@ss.hasPermi('bpm:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(BpmModel bpmForm){
        List<BpmModel> list = modelService.selectBpmModelList(bpmForm);
        return getDataTable(list);
    }

    /**
     * 新增流程模型
     */
    @PreAuthorize("@ss.hasPermi('bpm:model:add')")
    @Log(title = "创建流程模型", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    public AjaxResult add(@RequestBody BpmModelVo bpmModelVo){
        return modelService.createModel(bpmModelVo,null);
    }

    /**
     * 新增流程模型
     */
    @PreAuthorize("@ss.hasPermi('bpm:model:add')")
    @Log(title = "创建流程模型", businessType = BusinessType.INSERT)
    @PostMapping("/update")
    public AjaxResult updateModel(@RequestBody BpmModelVo bpmModelVo){
        return modelService.updateModel(bpmModelVo);
    }

    /**
     * 获取流程模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('bpm:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id){
        return success(modelService.selectBpmModelById(id));
    }


    /**
     * 更新工作流流程图信息
     */
    @PreAuthorize("@ss.hasPermi('bpm:form:add')")
    @Log(title = "更新工作流流程图信息", businessType = BusinessType.UPDATE)
    @PostMapping("/flowChart/update")
    public AjaxResult updateFlowChart(@RequestBody BpmModel bpmModel){
        return modelService.updateFlowChart(bpmModel);
    }
}
