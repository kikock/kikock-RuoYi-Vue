package com.ruoyi.project.company.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 企业信息对象 company_info
 * 
 * @author ruoyi
 * @date 2022-05-25
 */
public class CompanyInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String name;

    /** 行业类型id */
    @Excel(name = "行业类型id")
    private String industryTypeId;

    /** 员工规模 */
    @Excel(name = "员工规模")
    private String staffSize;

    /** 管理员姓名 */
    @Excel(name = "管理员姓名")
    private String directorName;

    /** 管理员电话 */
    @Excel(name = "管理员电话")
    private String directorPhone;

    /** 标志 */
    @Excel(name = "标志")
    private String logo;

    /**
     * 行业类型对象
     */
    private IndustryType industryType;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 注册类型 1 微信小程序 2 钉钉 3 企业微信 */
    @Excel(name = "性别", readConverterExp = "1=：微信小程序，2：钉钉 3 企业微信")
    private String type;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId() 
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
    public void setIndustryTypeId(String industryTypeId) 
    {
        this.industryTypeId = industryTypeId;
    }

    public String getIndustryTypeId() 
    {
        return industryTypeId;
    }
    public void setStaffSize(String staffSize) 
    {
        this.staffSize = staffSize;
    }

    public String getStaffSize() 
    {
        return staffSize;
    }
    public void setDirectorName(String directorName) 
    {
        this.directorName = directorName;
    }

    public String getDirectorName() 
    {
        return directorName;
    }
    public void setDirectorPhone(String directorPhone) 
    {
        this.directorPhone = directorPhone;
    }

    public String getDirectorPhone() 
    {
        return directorPhone;
    }
    public void setLogo(String logo) 
    {
        this.logo = logo;
    }

    public String getLogo() 
    {
        return logo;
    }

    public IndustryType getIndustryType() {
        return industryType;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("industryTypeId", getIndustryTypeId())
            .append("staffSize", getStaffSize())
            .append("directorName", getDirectorName())
            .append("directorPhone", getDirectorPhone())
            .append("logo", getLogo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
