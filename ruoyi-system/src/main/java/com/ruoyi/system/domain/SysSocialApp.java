package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 社交应用参数对象 sys_social_app
 *
 * @author kikock
 * @date 2023-12-04
 */
public class SysSocialApp extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 主键(自增策略)
     */
    private Long id;

    /**
     * 应用名称
     */
    @Excel(name = "应用名称")
    private String name;

    /**
     * 社交平台的类型
     */
    @Excel(name = "社交平台的类型")
    private String type;

    /**
     * 平台应用appKey
     */
    @Excel(name = "平台应用appKey")
    private String appKey;

    /**
     * 平台应用 appSecret
     */
    @Excel(name = "平台应用 appSecret")
    private String appSecret;

    /**
     * 应用编码
     */
    @Excel(name = "应用编码")
    private String code;
    /**
     * 登录回调地址
     */
    @Excel(name = "登录回调地址")
    private String url;
    /**
     * 平台企业id
     */
    @Excel(name = "平台企业id")
    private String corpId;

    /**
     * 开启状态
     */
    @Excel(name = "开启状态")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setAppKey(String appKey){
        this.appKey = appKey;
    }

    public String getAppKey(){
        return appKey;
    }

    public void setAppSecret(String appSecret){
        this.appSecret = appSecret;
    }

    public String getAppSecret(){
        return appSecret;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setCorpId(String corpId){
        this.corpId = corpId;
    }

    public String getCorpId(){
        return corpId;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("type", getType())
                .append("appKey", getAppKey())
                .append("appSecret", getAppSecret())
                .append("code", getCode())
                .append("corpId", getCorpId())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
