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
    public R<?> start(@PathVariable(value = "deployId") String deployId){
        WfFormVo formVo = deployFormService.selectDeployFormByDeployId(deployId);
        if (Objects.isNull(formVo)) {
            return R.fail("请先配置流程表单");
        }
        return R.ok(JSONUtil.toBean(formVo.getContent(), Map.class));
    }
}
