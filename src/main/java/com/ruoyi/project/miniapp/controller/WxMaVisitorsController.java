package com.ruoyi.project.miniapp.controller;


import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.file.service.IDocumentInfoService;
import com.ruoyi.project.miniapp.controller.vo.VisitorApplyVo;
import com.ruoyi.project.miniapp.controller.vo.WxDeptVo;
import com.ruoyi.project.miniapp.controller.vo.WxUserInfoVo;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import com.ruoyi.project.system.service.ISysDeptService;
import com.ruoyi.project.system.service.ISysUserService;
import com.ruoyi.project.visitor.domain.VisitorApplyRecord;
import com.ruoyi.project.visitor.domain.VisitorHealth;
import com.ruoyi.project.visitor.mapper.VisitorApplyRecordMapper;
import com.ruoyi.project.visitor.service.IVisitorHealthService;
import com.ruoyi.project.weixin.domain.SysWxUser;
import com.ruoyi.project.weixin.service.ISysWxUserService;
import com.ruoyi.project.weixin.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 微信小程序 访客相关接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Log4j2
@RestController
@RequestMapping("/app/visitors/")
public class WxMaVisitorsController extends BaseController {
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private VisitorApplyRecordMapper visitorApplyRecordMapper;
    @Autowired
    private IDocumentInfoService documentInfoService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysWxUserService sysWxUserService;
    @Autowired
    private IVisitorHealthService visitorHealthService;

    /**
     * 根据用户id获取微信小程序用户
     */
    @GetMapping("/wxInfo")
    public AjaxResult findWxUserByUserId(Long userId, String wxId) {
        // SysWxUser sysWxUser = userService.selectSysWxUserById(userId);
        if (userId != null && userId != 0) {
            WxUserInfoVo wxInfoByUserId = visitorApplyRecordMapper.findWxInfoByUserId(userId);
            if (Objects.isNull(wxInfoByUserId)) {
                //内部用户
                SysUser sysUser = userService.selectUserById(userId);
                if (Objects.nonNull(sysUser)) {
                    WxUserInfoVo userInfo = new WxUserInfoVo();
                    userInfo.setUserId(userId);
                    userInfo.setDeptId(sysUser.getDeptId());
                    userInfo.setWxType(sysUser.getUserType());
                    userInfo.setNickName(sysUser.getNickName());
                    userInfo.setName(sysUser.getNickName());
                    userInfo.setDeptName(sysUser.getDept().getDeptName());
                    userInfo.setPhone(sysUser.getPhonenumber());
                    userInfo.setWhole("1");
                    return AjaxResult.success(userInfo);
                }
            }
            return AjaxResult.success(wxInfoByUserId);
        }
        if (StringUtils.isNotEmpty(wxId)) {
            WxUserInfoVo wxInfoByUserId = visitorApplyRecordMapper.findWxInfoByWxId(wxId);
            return AjaxResult.success(wxInfoByUserId);
        }
        return AjaxResult.error("获取数据失败!");
    }

    /**
     * 根据用户id获取微信小程序用户
     * 个人中心 更新
     */
    @PostMapping("/updateWxUser")
    public AjaxResult updateWxUser(@RequestBody WxUserInfoVo vo) {
        if (StringUtils.isEmpty(String.valueOf(vo.getWxId()))) {
            return AjaxResult.error("保存失败,请重新登录再试试!");
        }
        SysWxUser sysWxUser = sysWxUserService.selectSysWxUserById(vo.getWxId());
        sysWxUser.setUserName(vo.getName());
        sysWxUser.setDeptName(vo.getDeptName());
        sysWxUser.setPhone(vo.getPhone());
        sysWxUser.setIdCard(vo.getIdCard());
        sysWxUser.setCardType(Integer.parseInt(vo.getCardType()));
        sysWxUser.setAddress(vo.getAddress());
        sysWxUser.setWhole("1");
        sysWxUserService.updateSysWxUser(sysWxUser);
        //获取用户
        WxUserInfoVo wxInfoByUserId = visitorApplyRecordMapper.findWxInfoByWxId(vo.getWxId());
        //批量修改申请单姓名
        visitorApplyRecordMapper.batchUpdateName(vo.getName(), vo.getWxId());

        return AjaxResult.success(wxInfoByUserId);
    }

    /**
     * 确认申请状态
     * status 状态
     */
    @PostMapping("/confirmWxUser")
    public AjaxResult confirmWxUser(@RequestBody VisitorApplyVo vo) {

        //获取申请单
        VisitorApplyRecord visitorApplyRecord = visitorApplyRecordMapper.selectVisitorApplyRecordById(vo.getWxId());
        if (Objects.isNull(visitorApplyRecord)) {
            return AjaxResult.error("更新数据失败");
        }
        //获取审批用户
        SysUser sysUser = userService.selectUserById(vo.getUserId());
        if (Objects.isNull(sysUser)) {
            return AjaxResult.error("更新数据失败");
        }
        if (vo.getType().equals("101")) {
            //体温
            visitorApplyRecord.setTemperature(vo.getTemperature());
            visitorApplyRecord.setGuardId(String.valueOf(sysUser.getUserId()));
            visitorApplyRecord.setGuardName(sysUser.getNickName());
        } else if (vo.getType().equals("102")) {
            visitorApplyRecord.setReviewedById(sysUser.getUserId());
            visitorApplyRecord.setReviewedByName(sysUser.getNickName());
            visitorApplyRecord.setReviewedByTime(new Date());
            visitorApplyRecord.setReviewedFailed(vo.getReviewedFailed());
        }
        visitorApplyRecord.setApplyState(String.valueOf(vo.getApplyState()));
        visitorApplyRecordMapper.updateVisitorApplyRecord(visitorApplyRecord);
        return AjaxResult.success(visitorApplyRecord);
    }

    /**
     * 获取部门列表
     */
    @GetMapping("/deptlist")
    public AjaxResult findWxDeptVoById(Long id) {
        List<WxDeptVo> deptList = visitorApplyRecordMapper.findDeptList(id);
        List<WxDeptVo> wxDeptVos = deptlistFormatter(deptList);
        return AjaxResult.success(wxDeptVos);
    }

    /**
     * 根据部门id 获取接待人
     */
    @GetMapping("/userlist")
    public AjaxResult findUserlistById(Long id) {
        List<WxUserInfoVo> deptList = visitorApplyRecordMapper.findUserList(id);
        return AjaxResult.success(deptList);
    }

    /**
     * 访客登记申请
     */
    @PostMapping("/registerInfo")
    public AjaxResult registerInfo(@RequestBody VisitorApplyVo vo) {
        System.out.println(JsonUtils.toJson(vo));
        if (Objects.isNull(vo)) {
            return AjaxResult.error("提交申请失败");
        }
        //提交保存申请单
        vo.setApplyState(1);
        visitorApplyRecordMapper.saveRegisterInfo(vo);
        return AjaxResult.success(true);
    }

    /**
     * 确认访客申请列表
     */
    @GetMapping("/confirmMaking")
    public AjaxResult confirmMaking(String wxId, String status, String visitTime, String guardId, String reviewedById) {
        if (StringUtils.isEmpty(visitTime)) {
            visitTime = null;
        }
        List<String> ids = new ArrayList<>();
        ids.add(status);
        //2 3 审批
        if (status.equals("2")) {
            ids.add("3");
        }
        return AjaxResult.success(visitorApplyRecordMapper.getMakingListByWxUserId(wxId, visitTime, status, guardId,
                reviewedById, ids));
    }

    /**
     * 查询访客申请记录列表
     */
    @GetMapping("/makingList")
    public AjaxResult makingList(String wxId, String visitTime) {
        if (StringUtils.isEmpty(wxId)) {
            return AjaxResult.error("无申请数据");
        }
        if (StringUtils.isEmpty(visitTime)) {
            visitTime = null;
        }
        List<String> ids = new ArrayList<>();
        return AjaxResult.success(visitorApplyRecordMapper.getMakingListByWxUserId(wxId, visitTime, null, null,
                null, ids));
    }

    /**
     * 查询访客申请记录列表
     */
    @GetMapping("/findVisitById")
    public AjaxResult findVisitById(String wxId) {
        if (StringUtils.isEmpty(wxId)) {
            return AjaxResult.error("查询数据失败");
        }
        VisitorApplyRecord visitorApplyRecord = visitorApplyRecordMapper.selectVisitorApplyRecordById(wxId);
        if (Objects.isNull(visitorApplyRecord)) {
            return AjaxResult.error("查询数据失败");
        }
        return AjaxResult.success(visitorApplyRecord);
    }

    /**
     * 查询核酸报告填报记录列表
     */
    @GetMapping("/findHealthList")
    public AjaxResult findHealthList(String time,String wxId) {
        return AjaxResult.success(visitorHealthService.getVisitorHealthList(time,wxId));
    }


    /**
     * 根据用户id获取微信小程序用户
     * 个人中心 更新
     */
    @PostMapping("/health/save")
    public AjaxResult healthSave(@RequestBody VisitorHealth visitorHealth) {
        if (Objects.isNull(visitorHealth)) {
            return AjaxResult.error("保存失败,请重新登录再试试!");
        }
        String id = IdUtils.simpleUUID();
        visitorHealth.setId(id);
        int rows = visitorHealthService.insertVisitorHealth(visitorHealth);
        return  rows > 0 ? AjaxResult.success(visitorHealthService.selectVisitorHealthById(id)) : AjaxResult.error();
    }






    //部门树形结构转换
    private List<WxDeptVo> deptlistFormatter(List<WxDeptVo> deptVos) {
        List<WxDeptVo> result = new ArrayList<>();
        Map<Long, WxDeptVo> cache = new HashMap<>();
        for (WxDeptVo vo : deptVos) {
            //遍历所有放进map缓存 <id menu>
            cache.put(vo.getId(), vo);
        }
        //获取祖籍列表构建全称 xx公司/xx部门/xxx   0,100,200
        for (WxDeptVo vo : deptVos) {
            String COMMA = "/";
            String ancestors = vo.getAncestors();
            List<String> collect = Arrays.stream(ancestors.split(",")).collect(toList());
            List<WxDeptVo> tmp = new ArrayList<>();
            for (String id : collect) {
                WxDeptVo parent = cache.get(Long.valueOf(id));
                if (Objects.nonNull(parent)) {
                    tmp.add(parent);
                }
            }
            //加上自己
            tmp.add(vo);
            if (CollectionUtils.isNotEmpty(tmp)) {
                String fullName = tmp.stream().map(WxDeptVo :: getName).collect(Collectors.joining(COMMA));
                vo.setFullName(fullName);
            }
        }

        for (WxDeptVo vo : deptVos) {
            if (vo.getParentId() == 0) {
                //一级菜单
                result.add(vo);
            } else {
                Long pid = vo.getParent().getId();
                WxDeptVo parent = cache.get(pid);
                List<WxDeptVo> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(vo);
            }
        }
        return result;
    }

}
