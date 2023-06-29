package com.ruoyi.project.miniapp.controller.vo;


import java.util.Date;

/**
 * 访客申请记录对象 visitor_apply_record
 *
 * @author ruoyi
 * @date 2022-09-19
 */
public class VisitorApplyVo {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;
    /**
     * 申请人微信id
     */
    private String wxUserId;

    /**
     * 申请人名称
     */
    private String wxUserName;
    /**
     * 申请时间
     */
    private Date applyTime = new Date();
    /**
     * 申请单状态
     */
    private Integer applyState;

    /**
     * 到访日期时间
     */
    private Date visitTime;

    /**
     * 访问部门id
     */
    private Long deptId;

    /**
     * 访问部门名称
     */
    private String deptName;

    /**
     * 访问事项
     */
    private String accessMatters;

    /**
     * 访问人数
     */
    private Long accessPeopleNum;

    /**
     * 接待人id
     */
    private Long receptionistId;

    /**
     * 接待人名称
     */
    private String receptionistName;


    /**  ############状态变动参数##########   */
    /**
     * 申请单id
     */
    private String type;

    /**
     * 申请单id
     */
    private String wxId;
    /**
     * 审核人id
     */
    private Long userId;

    /**
     * 体温
     */
    private String temperature;
    /**
     * 审核不同意原因
     */
    private String reviewedFailed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReviewedFailed() {
        return reviewedFailed;
    }

    public void setReviewedFailed(String reviewedFailed) {
        this.reviewedFailed = reviewedFailed;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWxUserName() {
        return wxUserName;
    }

    public void setWxUserName(String wxUserName) {
        this.wxUserName = wxUserName;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getApplyState() {
        return applyState;
    }

    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAccessMatters() {
        return accessMatters;
    }

    public void setAccessMatters(String accessMatters) {
        this.accessMatters = accessMatters;
    }

    public Long getAccessPeopleNum() {
        return accessPeopleNum;
    }

    public void setAccessPeopleNum(Long accessPeopleNum) {
        this.accessPeopleNum = accessPeopleNum;
    }

    public Long getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(Long receptionistId) {
        this.receptionistId = receptionistId;
    }

    public String getReceptionistName() {
        return receptionistName;
    }

    public void setReceptionistName(String receptionistName) {
        this.receptionistName = receptionistName;
    }

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }
}
