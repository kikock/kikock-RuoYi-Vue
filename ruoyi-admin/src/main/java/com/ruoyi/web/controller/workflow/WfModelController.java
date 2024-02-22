package com.ruoyi.web.controller.workflow;


import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.excel.ExcelUtils;
import com.ruoyi.workflow.domain.WfCategory;
import com.ruoyi.workflow.domain.bo.WfModelBo;
import com.ruoyi.workflow.domain.vo.WfCategoryVo;
import com.ruoyi.workflow.domain.vo.WfModelExportVo;
import com.ruoyi.workflow.domain.vo.WfModelVo;
import com.ruoyi.workflow.service.IWfCategoryService;
import com.ruoyi.workflow.service.IWfModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工作流流程模型管理
 *
 * @author KonBAI
 * @createTime 2022/6/21 9:09
 */
@RestController
@RequestMapping("/workflow/model")
public class WfModelController extends BaseController{
    @Autowired
    private  IWfModelService modelService;
    @Autowired
    private  IWfCategoryService categoryService;

    /**
     * 查询流程模型列表
     *
     * @param modelBo 流程模型对象
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(WfModelBo modelBo) {
        startPage();
        List<WfModelVo> list = modelService.list(modelBo);
        return getDataTable(list);
    }

    /**
     * 查询流程模型列表
     *
     * @param modelBo 流程模型对象
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:list')")
    @GetMapping("/historyList")
    public TableDataInfo historyList(WfModelBo modelBo) {
        startPage();
        List<WfModelVo> list = modelService.historyList(modelBo);
        return getDataTable(list);
    }

    /**
     * 获取流程模型详细信息
     *
     * @param modelId 模型主键
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:query')")
    @GetMapping(value = "/{modelId}")
    public R<WfModelVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable("modelId") String modelId) {
        return R.ok(modelService.getModel(modelId));
    }

    /**
     * 获取流程表单详细信息
     *
     * @param modelId 模型主键
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:query')")
    @GetMapping(value = "/bpmnXml/{modelId}")
    public AjaxResult getBpmnXml(@NotNull(message = "主键不能为空") @PathVariable("modelId") String modelId) {
        AjaxResult result = AjaxResult.success();
        result.put("bpmnXml",modelService.queryBpmnXmlById(modelId));
        return result;
    }

    /**
     * 新增流程模型
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:add')")
    @Log(title = "流程模型", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfModelBo modelBo) {
        modelService.insertModel(modelBo);
        return R.ok();
    }

    /**
     * 修改流程模型
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:edit')")
    @Log(title = "流程模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfModelBo modelBo) {
        modelService.updateModel(modelBo);
        return R.ok();
    }

    /**
     * 保存流程模型
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:save')")
    @Log(title = "保存流程模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/save")
    public R<String> save(@RequestBody WfModelBo modelBo) {
        modelService.saveModel(modelBo);
        return R.ok();
    }

    /**
     * 设为最新流程模型
     * @param modelId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:save')")
    @Log(title = "设为最新流程模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/latest")
    public R<?> latest(@RequestParam String modelId) {
        modelService.latestModel(modelId);
        return R.ok();
    }

    /**
     * 删除流程模型
     *
     * @param modelIds 流程模型主键串
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:remove')")
    @Log(title = "删除流程模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modelIds}")
    public R<String> remove(@NotEmpty(message = "主键不能为空") @PathVariable String[] modelIds) {
        modelService.deleteByIds(Arrays.asList(modelIds));
        return R.ok();
    }

    /**
     * 部署流程模型
     *
     * @param modelId 流程模型主键
     */
    @PreAuthorize("@ss.hasPermi('workflow:model:deploy')")
    @Log(title = "部署流程模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/deploy")
    public AjaxResult deployModel(@RequestParam String modelId) {
        return toAjax(modelService.deployModel(modelId));
    }

    /**
     * 导出流程模型数据
     */
    @Log(title = "导出流程模型数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('workflow:model:export')")
    @PostMapping("/export")
    public void export(WfModelBo modelBo, HttpServletResponse response) {
        List<WfModelVo> list =  modelService.list(modelBo);
        List<WfModelExportVo> listVo = BeanUtil.copyToList(list, WfModelExportVo.class);
        List<WfCategory> categoryVos = categoryService.selectWfCategoryList(new WfCategory());
        Map<String, String> categoryMap = categoryVos.stream()
            .collect(Collectors.toMap(WfCategory::getCode, WfCategory::getCategoryName));
        for (WfModelExportVo exportVo : listVo) {
            exportVo.setCategoryName(categoryMap.get(exportVo.getCategory()));
        }
        ExcelUtils.exportExcel(listVo, "流程模型数据", WfModelExportVo.class, response);
    }
}
