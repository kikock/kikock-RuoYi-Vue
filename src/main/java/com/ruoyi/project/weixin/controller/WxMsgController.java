package com.ruoyi.project.weixin.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.weixin.utils.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.weixin.domain.WxMsg;
import com.ruoyi.project.weixin.service.IWxMsgService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 微信消息Controller
 * 
 * @author ruoyi
 * @date 2022-04-20
 */
@Api("微信消息")
@RestController
@RequestMapping("/weixin/msg")
public class WxMsgController extends BaseController
{
    @Autowired
    private IWxMsgService wxMsgService;
    @Autowired
    private WxMpService wxService;

    /**
     * 查询微信消息列表
     */
    @ApiOperation("查询微信消息列表")
    @PreAuthorize("@ss.hasPermi('weixin:msg:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxMsg wxMsg)
    {
        startPage();
        List<WxMsg> list = wxMsgService.selectWxMsgList(wxMsg);
        return getDataTable(list);
    }

    /**
     * 导出微信消息列表
     */
    @ApiOperation("导出微信消息列表")
    @PreAuthorize("@ss.hasPermi('weixin:msg:export')")
    @Log(title = "微信消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxMsg wxMsg)
    {
        List<WxMsg> list = wxMsgService.selectWxMsgList(wxMsg);
        ExcelUtil<WxMsg> util = new ExcelUtil<WxMsg>(WxMsg.class);
        util.exportExcel(response, list, "微信消息数据");
    }

    /**
     * 获取微信消息详细信息
     */
    @ApiOperation("获取微信消息详细信息")
    @PreAuthorize("@ss.hasPermi('weixin:msg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(wxMsgService.selectWxMsgById(id));
    }

    /**
     * 新增微信消息
     */
    @ApiOperation("新增微信消息")
    @PreAuthorize("@ss.hasPermi('weixin:msg:add')")
    @Log(title = "微信消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxMsg wxMsg)
    {
        wxMsg.setCreateId(getUserId().toString());
        try {
//            WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage.TEXT().build();
//            wxMpKefuMessage.setContent(wxMsg.getContent());
//            WxMpKefuService wxMpKefuService = wxService.getKefuService();
//            wxMpKefuMessage.setToUser("oS-UD6Tk_oRicmC1m1SAHRM-Ztm4");
//            boolean b = wxMpKefuService.sendKefuMessage(wxMpKefuMessage);
//            logger.info("微信消息发送结果======"+ b);
            WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
            massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
            massMessage.setContent(wxMsg.getContent());
            massMessage.getToUsers().add("oS-UD6Tk_oRicmC1m1SAHRM-Ztm4");
            massMessage.getToUsers().add("oS-UD6bxE9-yIZex4B5B6TKt0uUA");
            WxMpMassSendResult massResult = wxService.getMassMessageService().massOpenIdsMessageSend(massMessage);
            logger.info("微信消息发送结果======"+ JsonUtils.toJson(massResult));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return toAjax(wxMsgService.insertWxMsg(wxMsg));
    }

    /**
     * 修改微信消息
     */
    @ApiOperation("修改微信消息")
    @PreAuthorize("@ss.hasPermi('weixin:msg:edit')")
    @Log(title = "微信消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxMsg wxMsg)
    {
        wxMsg.setUpdateId(getUserId().toString());
        return toAjax(wxMsgService.updateWxMsg(wxMsg));
    }

    /**
     * 删除微信消息
     */
    @ApiOperation("删除微信消息")
    @PreAuthorize("@ss.hasPermi('weixin:msg:remove')")
    @Log(title = "微信消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(wxMsgService.deleteWxMsgByIds(ids));
    }
}
