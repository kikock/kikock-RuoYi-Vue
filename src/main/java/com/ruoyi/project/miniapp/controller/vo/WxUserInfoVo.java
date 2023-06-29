package com.ruoyi.project.miniapp.controller.vo;

/**
 * @project_name: jx-sp-service
 * @description: 微信小程序用户信息
 * @create_name: kikock
 * @create_date: 2022-09-22 16:08
 **/
public class WxUserInfoVo {
    /**
     * 微信ID
     */
    private String wxId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户类型
     */
    private String wxType;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 证件类型
     */
    private String cardType;

    /**
     * 证件号码
     */
    private String idCard;

    /**
     * 单位名称
     */
    private String deptName;
    /**
     * 单位地址
     */
    private String address;
    /**
     * 资料是否完善
     */
    private String whole;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getWxType() {
        return wxType;
    }

    public void setWxType(String wxType) {
        this.wxType = wxType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWhole() {
        return whole;
    }

    public void setWhole(String whole) {
        this.whole = whole;
    }
}
