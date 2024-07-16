package com.ruoyi.web.controller.workflow;


import cn.hutool.json.JSONUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.flowable.core.domain.ProcessQuery;
import com.ruoyi.workflow.domain.vo.WfDeployVo;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import com.ruoyi.workflow.service.IWfDeployFormService;
import com.ruoyi.workflow.service.IWfDeployService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 流程部署
 *
 * @author KonBAI
 * @createTime 2022/3/24 20:57
 */
@RestController
@RequestMapping("/workflow/deploy")
@Tag(name = "流程部署管理")
public class WfDeployController extends BaseController{
    @Autowired
    private IWfDeployService deployService;
    @Autowired
    private IWfDeployFormService deployFormService;

    /**
     * 查询流程部署列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:deploy:list')")
    @GetMapping("/list")
    @Operation(summary = "流程部署列表")
    public TableDataInfo list(ProcessQuery processQuery){
        startPage();
        List<WfDeployVo> list = deployService.queryPageList(processQuery);
        return getDataTable(list);
    }

    /**
     * 查询流程部署版本列表
     */

    @GetMapping("/publishList")
    @PreAuthorize("@ss.hasPermi('workflow:deploy:list')")
    @Operation(summary = "流程部署版本列表")
    @Parameters({
            @Parameter(name = "processKey", description = "流程key", required = true, in = ParameterIn.QUERY)
    })
    public TableDataInfo publishList(@RequestParam String processKey){
        startPage();
        List<WfDeployVo> list = deployService.queryPublishList(processKey);
        return getDataTable(list);
    }

    /**
     * 激活或挂起流程
     *
     * @param state        状态（active:激活 suspended:挂起）
     * @param definitionId 流程定义ID
     */
    @PreAuthorize("@ss.hasPermi('workflow:deploy:state')")
    @PutMapping(value = "/changeState")
    @Operation(summary = "激活或挂起流程")
    @Parameters({
            @Parameter(name = "state", description = "状态（active:激活 suspended:挂起）", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "definitionId", description = "流程定义ID", required = true, in = ParameterIn.QUERY)
    })
    public R<Void> changeState(@RequestParam String state, @RequestParam String definitionId){
        deployService.updateState(definitionId, state);
        return R.ok();
    }

    /**
     * 读取xml文件
     *
     * @param definitionId 流程定义ID
     * @return
     */
    @PreAuthorize("@ss.hasPermi('workflow:deploy:query')")
    @GetMapping("/bpmnXml/{definitionId}")
    @Operation(summary = "读取xml文件")
    @Parameters({
            @Parameter(name = "definitionId", description = "流程定义ID", required = true, in = ParameterIn.PATH)
    })
    public AjaxResult getBpmnXml(@PathVariable(value = "definitionId") String definitionId){
        AjaxResult result = AjaxResult.success();
        result.put("bpmnXml",deployService.queryBpmnXmlById(definitionId));
        return result;
    }

    /**
     * 删除流程模型
     *
     * @param deployIds 流程部署ids
     */
    @PreAuthorize("@ss.hasPermi('workflow:deploy:remove')")
    @Log(title = "删除流程部署", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deployIds}")
    @Operation(summary = "删除流程部署")
    @Parameters({
            @Parameter(name = "deployIds", description = "流程部署ids", required = true, in = ParameterIn.PATH)
    })
    public R<String> remove(@NotEmpty(message = "主键不能为空") @PathVariable String[] deployIds){
        deployService.deleteByIds(Arrays.asList(deployIds));
        return R.ok();
    }

    /**
     * 查询流程部署关联表单信息
     *
     * @param deployId 流程部署id
     */
    @GetMapping("/form/{deployId}")
    @Operation(summary = "查询流程部署关联表单信息")
    @Parameters({
            @Parameter(name = "deployId", description = "流程部署id", required = true, in = ParameterIn.PATH)
    })
    public R<?> start(@PathVariable(value = "deployId") String deployId){
        WfFormVo formVo = deployFormService.selectDeployFormByDeployId(deployId);
        if (Objects.isNull(formVo)) {
            return R.fail("请先配置流程表单");
        }
        return R.ok(JSONUtil.toBean(formVo.getContent(), Map.class));
    }
}
