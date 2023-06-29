package com.ruoyi.project.ssosrasb.merchantInfo.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商户信息对象 dz_merchant_info
 *
 * @author kikock
 * @date 2023-06-29
 */
public class MerchantInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商户id */
    private Long id;

    /** 商户名称 */
    @Excel(name = "商户名称")
    @ApiModelProperty(value = "商户名称")
    private String name;

    /** 商户地址 */
    @Excel(name = "商户地址")
    @ApiModelProperty(value = "商户地址")
    private String address;

    /** 户主 */
    @Excel(name = "户主")
    @ApiModelProperty(value = "户主")
    private String householder;

    /** 户主电话 */
    @Excel(name = "户主电话")
    @ApiModelProperty(value = "户主电话")
    private String telPhone;

    /** 责任部门 */
    @Excel(name = "责任部门")
    @ApiModelProperty(value = "责任部门")
    private String deptId;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    /** 是否删除 */
    private String delFlag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setHouseholder(String householder)
    {
        this.householder = householder;
    }

    public String getHouseholder()
    {
        return householder;
    }
    public void setTelPhone(String telPhone)
    {
        this.telPhone = telPhone;
    }

    public String getTelPhone()
    {
        return telPhone;
    }
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("address", getAddress())
            .append("householder", getHouseholder())
            .append("telPhone", getTelPhone())
            .append("deptId", getDeptId())
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
