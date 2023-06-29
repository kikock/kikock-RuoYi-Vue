package com.ruoyi.project.company.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;
import com.ruoyi.project.weixin.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.company.domain.CompanyInfo;
import com.ruoyi.project.company.service.ICompanyInfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * 企业信息Controller
 * 
 * @author ruoyi
 * @date 2022-05-25
 */
@Api(tags = "企业信息")
@RestController
@RequestMapping("/company/info")
public class CompanyInfoController extends BaseController
{
    @Autowired
    private ICompanyInfoService companyInfoService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysWxUserService sysWxUserService;

    /**
     * 查询企业信息列表
     */
    @ApiOperation("查询企业信息列表")
    @PreAuthorize("@ss.hasPermi('company:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompanyInfo companyInfo)
    {
        startPage();
        List<CompanyInfo> list = companyInfoService.selectCompanyInfoList(companyInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业信息列表
     */
    @ApiOperation("导出企业信息列表")
    @PreAuthorize("@ss.hasPermi('company:info:export')")
    @Log(title = "企业信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompanyInfo companyInfo)
    {
        List<CompanyInfo> list = companyInfoService.selectCompanyInfoList(companyInfo);
        ExcelUtil<CompanyInfo> util = new ExcelUtil<CompanyInfo>(CompanyInfo.class);
        util.exportExcel(response, list, "企业信息数据");
    }

    /**
     * 获取企业信息详细信息
     */
    @ApiOperation("获取企业信息详细信息")
    @PreAuthorize("@ss.hasPermi('company:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(companyInfoService.selectCompanyInfoById(id));
    }

    /**
     * 新增企业信息
     */
    @ApiOperation("新增企业信息")
    @PreAuthorize("@ss.hasPermi('company:info:add')")
    @Log(title = "企业信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompanyInfo companyInfo)
    {
        logger.info("新增企业信息参数:{},", JsonUtils.toJson(companyInfo));
        Long userId = companyInfo.getUserId();
        if (Objects.isNull(userId)) {
            userId = getLoginUser().getUserId();
            companyInfo.setUserId(userId);
        }
        SysUser sysUser = userMapper.selectUserById(userId);
        if (Objects.isNull(sysUser)) {
            return AjaxResult.error("获取当前用户失败!");
        }
        CompanyInfo infoByUserId = companyInfoService.selectCompanyInfoByUserId(companyInfo.getUserId());
        if (Objects.nonNull(infoByUserId)) {
            return AjaxResult.error("当前用户已经注册了企业信息!");
        }
        CompanyInfo infoByName = companyInfoService.selectCompanyInfoByName(companyInfo.getName());
        if (Objects.nonNull(infoByName)) {
            return AjaxResult.error("企业名称不能重复!");
        }
        if ("1".equals(companyInfo.getType())) {
            // 微信号关联的手机号
            String phone = companyInfo.getDirectorPhone();
            if (StringUtils.isBlank(sysUser.getPhonenumber())) {
                sysUser.setPhonenumber(phone);
                userMapper.updateUser(sysUser);
            }
            SysWxUser sysWxUser = sysWxUserService.selectSysWxUserByOpenId(sysUser.getUserName());
            sysWxUser.setPhone(phone);
            sysWxUserService.updateSysWxUser(sysWxUser);
        }
        return toAjax(companyInfoService.insertCompanyInfo(companyInfo));
    }

    /**
     * 修改企业信息
     */
    @ApiOperation("修改企业信息")
    @PreAuthorize("@ss.hasPermi('company:info:edit')")
    @Log(title = "企业信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompanyInfo companyInfo)
    {
        CompanyInfo infoByName = companyInfoService.selectCompanyInfoByName(companyInfo.getName());
        if (Objects.nonNull(infoByName) && !companyInfo.getId().equals(infoByName.getId())) {
            return AjaxResult.error("企业名称不能重复!");
        }
        return toAjax(companyInfoService.updateCompanyInfo(companyInfo));
    }

    /**
     * 删除企业信息
     */
    @ApiOperation("删除企业信息")
    @PreAuthorize("@ss.hasPermi('company:info:remove')")
    @Log(title = "企业信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(companyInfoService.deleteCompanyInfoByIds(ids));
    }

    /**
     * 企业logo上传
     */
    @ApiOperation("企业logo上传")
    @Log(title = "企业logo", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadLogo")
    public AjaxResult uploadLogo(@RequestParam("logoFile") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            logger.info("【文件上传路径profile】{}",RuoYiConfig.getProfile());
            // 文件上传路径
            String filePath = RuoYiConfig.getProfile()+"/logo";
            String logo = FileUploadUtils.upload(filePath, file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", logo);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 分页查询企业信息列表
     */
    @ApiOperation("分页查询企业信息列表")
    @PostMapping("/listCompanyInfo")
    public TableDataInfo listCompanyInfo(@RequestBody CompanyInfo companyInfo)
    {
        startPage();
        List<CompanyInfo> list = companyInfoService.selectCompanyInfoList(companyInfo);
        return getDataTable(list);
    }

    /**
     * 根据用户id查询企业信息
     */
    @ApiOperation("根据用户id查询企业信息")
    @GetMapping(value = "/getByUserIdInfo")
    public AjaxResult getByUserIdInfo(@RequestParam("userId") Long userId)
    {
        return AjaxResult.success(companyInfoService.selectCompanyInfoByUserId(userId));
    }
}
