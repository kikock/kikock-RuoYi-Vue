package com.ruoyi.project.weixin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 微信用户对象 sys_wx_user
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public class SysWxUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createId;

    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String updateId;

    /**
     * 逻辑删除标记（0：显示；1：隐藏）
     */
    private String delFlag;

    /**
     * 应用类型(1:小程序，2:公众号)
     */
    @Excel(name = "应用类型(1:小程序，2:公众号)")
    private String appType;

    /**
     * 是否订阅（1：是；0：否；2：网页授权用户）
     */
    @Excel(name = "是否订阅", readConverterExp = "1=：是；0：否；2：网页授权用户")
    private String subscribe;

    /**
     * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     */
    @Excel(name = "返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他")
    private String subscribeScene;

    /**
     * 关注时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "关注时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subscribeTime;

    /**
     * 关注次数
     */
    @Excel(name = "关注次数")
    private Long subscribeNum;

    /**
     * 取消关注时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "取消关注时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelSubscribeTime;

    /**
     * 用户标识
     */
    @Excel(name = "用户标识")
    private String openId;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 性别（0：男，1：女，2：未知）
     */
    @Excel(name = "性别", readConverterExp = "0=：男，1：女，2：未知")
    private String sex;

    /**
     * 所在城市
     */
    @Excel(name = "所在城市")
    private String city;

    /**
     * 所在国家
     */
    @Excel(name = "所在国家")
    private String country;

    /**
     * 所在省份
     */
    @Excel(name = "所在省份")
    private String province;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 用户语言
     */
    @Excel(name = "用户语言")
    private String language;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headimgUrl;

    /**
     * union_id
     */
    @Excel(name = "union_id")
    private String unionId;

    /**
     * 用户组
     */
    @Excel(name = "用户组")
    private String groupId;

    /**
     * 标签列表
     */
    @Excel(name = "标签列表")
    private String tagidList;

    /**
     * 二维码扫码场景
     */
    @Excel(name = "二维码扫码场景")
    private String qrSceneStr;

    /**
     * 地理位置纬度
     */
    @Excel(name = "地理位置纬度")
    private Long latitude;

    /**
     * 地理位置经度
     */
    @Excel(name = "地理位置经度")
    private Long longitude;

    /**
     * 地理位置精度
     */
    @Excel(name = "地理位置精度")
    private Long precision;

    /**
     * 会话密钥
     */
    @Excel(name = "会话密钥")
    private String sessionKey;
    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;
    /**
     * 用户姓名
     */
    @Excel(name = "用户姓名")
    private String userName;
    /**
     * 微信用户类型同步与人员类型 100访客,101门禁管理人员,102审核人员,103工作人员
     */
    private String wxType;
    /**
     * 证件类型
     */
    private int cardType;
    /**
     * 证件号码
     */
    private String idCard;
    /**
     * 单位地址
     */
    @Excel(name = "单位地址")
    private String address;
    /**
     * 单位名称
     */
    @Excel(name = "单位地址")
    private String deptName;
    /**
     * 访客资料状态（0：未完成,1完成）
     */
    private String whole;

    public String getWhole() {
        return whole;
    }

    public void setWhole(String whole) {
        this.whole = whole;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWxType() {
        return wxType;
    }

    public void setWxType(String wxType) {
        this.wxType = wxType;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppType() {
        return appType;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeNum(Long subscribeNum) {
        this.subscribeNum = subscribeNum;
    }

    public Long getSubscribeNum() {
        return subscribeNum;
    }

    public void setCancelSubscribeTime(Date cancelSubscribeTime) {
        this.cancelSubscribeTime = cancelSubscribeTime;
    }

    public Date getCancelSubscribeTime() {
        return cancelSubscribeTime;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setTagidList(String tagidList) {
        this.tagidList = tagidList;
    }

    public String getTagidList() {
        return tagidList;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setPrecision(Long precision) {
        this.precision = precision;
    }

    public Long getPrecision() {
        return precision;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createId", getCreateId())
                .append("createTime", getCreateTime())
                .append("updateId", getUpdateId())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("delFlag", getDelFlag())
                .append("appType", getAppType())
                .append("subscribe", getSubscribe())
                .append("subscribeScene", getSubscribeScene())
                .append("subscribeTime", getSubscribeTime())
                .append("subscribeNum", getSubscribeNum())
                .append("cancelSubscribeTime", getCancelSubscribeTime())
                .append("openId", getOpenId())
                .append("nickName", getNickName())
                .append("sex", getSex())
                .append("city", getCity())
                .append("country", getCountry())
                .append("province", getProvince())
                .append("phone", getPhone())
                .append("language", getLanguage())
                .append("headimgUrl", getHeadimgUrl())
                .append("unionId", getUnionId())
                .append("groupId", getGroupId())
                .append("tagidList", getTagidList())
                .append("qrSceneStr", getQrSceneStr())
                .append("latitude", getLatitude())
                .append("longitude", getLongitude())
                .append("precision", getPrecision())
                .append("sessionKey", getSessionKey())
                .toString();
    }
}
