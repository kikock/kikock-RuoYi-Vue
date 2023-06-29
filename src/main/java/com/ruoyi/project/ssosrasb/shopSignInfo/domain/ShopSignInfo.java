package com.ruoyi.project.ssosrasb.shopSignInfo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 店招信息对象 dz_shop_sign_info
 *
 * @author kikock
 * @date 2023-06-29
 */
public class ShopSignInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 店招id */
    private Long id;

    /** 店招名称,户外广告名称 */
    @Excel(name = "店招名称,户外广告名称")
    @ApiModelProperty(value = "店招名称,户外广告名称")
    private String name;

    /** 商户id */
    @Excel(name = "商户id")
    @ApiModelProperty(value = "商户id")
    private Long merchantInfoId;

    /** 模板id */
    @Excel(name = "模板id")
    @ApiModelProperty(value = "模板id")
    private Long shopSignTemplateId;

    /** 店招类别(0店招1大型户外广告等等) */
    @Excel(name = "店招类别(0店招1大型户外广告等等)")
    @ApiModelProperty(value = "店招类别(0店招1大型户外广告等等)")
    private String shopSignCategory;

    /** 编码 */
    @Excel(name = "编码")
    @ApiModelProperty(value = "编码")
    private String code;

    /** 尺寸 */
    @Excel(name = "尺寸")
    @ApiModelProperty(value = "尺寸")
    private String dimension;

    /** 材质 */
    @Excel(name = "材质")
    @ApiModelProperty(value = "材质")
    private String materialType;

    /** 地址 */
    @Excel(name = "地址")
    @ApiModelProperty(value = "地址")
    private String address;

    /** 管理单位 */
    @Excel(name = "管理单位")
    @ApiModelProperty(value = "管理单位")
    private String handling;

    /** 纬度 */
    @Excel(name = "纬度")
    @ApiModelProperty(value = "纬度")
    private String latitude;

    /** 经度 */
    @Excel(name = "经度")
    @ApiModelProperty(value = "经度")
    private String longitude;

    /** 备案码 */
    @Excel(name = "备案码")
    @ApiModelProperty(value = "备案码")
    private String recordCode;

    /** 经营开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "经营开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "经营开始时间")
    private Date beginTime;

    /** 经营结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "经营结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "经营结束时间")
    private Date endTime;

    /** 是否拆除 */
    @Excel(name = "是否拆除")
    @ApiModelProperty(value = "是否拆除")
    private String isRemove;

    /** 拆除时间 */
    @Excel(name = "拆除时间")
    @ApiModelProperty(value = "拆除时间")
    private String removeTime;

    /** 拆除单位 */
    @Excel(name = "拆除单位")
    @ApiModelProperty(value = "拆除单位")
    private String removeUnit;

    /** 是否到期 */
    @Excel(name = "是否到期")
    @ApiModelProperty(value = "是否到期")
    private String isExpire;

    /** 版本号 */
    @Excel(name = "版本号")
    @ApiModelProperty(value = "版本号")
    private String versionNo;

    /** 店招风格 */
    @Excel(name = "店招风格")
    @ApiModelProperty(value = "店招风格")
    private String styleType;

    /** 核验状态 */
    @Excel(name = "核验状态")
    @ApiModelProperty(value = "核验状态")
    private String shopSignStatus;

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
    public void setMerchantInfoId(Long merchantInfoId)
    {
        this.merchantInfoId = merchantInfoId;
    }

    public Long getMerchantInfoId()
    {
        return merchantInfoId;
    }
    public void setShopSignTemplateId(Long shopSignTemplateId)
    {
        this.shopSignTemplateId = shopSignTemplateId;
    }

    public Long getShopSignTemplateId()
    {
        return shopSignTemplateId;
    }
    public void setShopSignCategory(String shopSignCategory)
    {
        this.shopSignCategory = shopSignCategory;
    }

    public String getShopSignCategory()
    {
        return shopSignCategory;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setDimension(String dimension)
    {
        this.dimension = dimension;
    }

    public String getDimension()
    {
        return dimension;
    }
    public void setMaterialType(String materialType)
    {
        this.materialType = materialType;
    }

    public String getMaterialType()
    {
        return materialType;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setHandling(String handling)
    {
        this.handling = handling;
    }

    public String getHandling()
    {
        return handling;
    }
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLatitude()
    {
        return latitude;
    }
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLongitude()
    {
        return longitude;
    }
    public void setRecordCode(String recordCode)
    {
        this.recordCode = recordCode;
    }

    public String getRecordCode()
    {
        return recordCode;
    }
    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime()
    {
        return beginTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setIsRemove(String isRemove)
    {
        this.isRemove = isRemove;
    }

    public String getIsRemove()
    {
        return isRemove;
    }
    public void setRemoveTime(String removeTime)
    {
        this.removeTime = removeTime;
    }

    public String getRemoveTime()
    {
        return removeTime;
    }
    public void setRemoveUnit(String removeUnit)
    {
        this.removeUnit = removeUnit;
    }

    public String getRemoveUnit()
    {
        return removeUnit;
    }
    public void setIsExpire(String isExpire)
    {
        this.isExpire = isExpire;
    }

    public String getIsExpire()
    {
        return isExpire;
    }
    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    public String getVersionNo()
    {
        return versionNo;
    }
    public void setStyleType(String styleType)
    {
        this.styleType = styleType;
    }

    public String getStyleType()
    {
        return styleType;
    }
    public void setShopSignStatus(String shopSignStatus)
    {
        this.shopSignStatus = shopSignStatus;
    }

    public String getShopSignStatus()
    {
        return shopSignStatus;
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
            .append("merchantInfoId", getMerchantInfoId())
            .append("shopSignTemplateId", getShopSignTemplateId())
            .append("shopSignCategory", getShopSignCategory())
            .append("code", getCode())
            .append("dimension", getDimension())
            .append("materialType", getMaterialType())
            .append("address", getAddress())
            .append("handling", getHandling())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("recordCode", getRecordCode())
            .append("beginTime", getBeginTime())
            .append("endTime", getEndTime())
            .append("isRemove", getIsRemove())
            .append("removeTime", getRemoveTime())
            .append("removeUnit", getRemoveUnit())
            .append("isExpire", getIsExpire())
            .append("versionNo", getVersionNo())
            .append("styleType", getStyleType())
            .append("shopSignStatus", getShopSignStatus())
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
