package com.ruoyi.project.file.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件信息对象 document_info
 *
 * @author jxkj
 * @date 2022-06-23
 */
@Data
public class DocumentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 文件全路径 */
    @Excel(name = "文件全路径")
    @ApiModelProperty(value = "文件全路径")
    private String fullPath;

    /** 文件名称 */
    @Excel(name = "文件名称")
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /** 上传用户ID */
    @Excel(name = "上传用户ID")
    @ApiModelProperty(value = "上传用户ID")
    private String uploadUserId;

    /** 上传用户账号 */
    @Excel(name = "上传用户账号")
    @ApiModelProperty(value = "上传用户账号")
    private String uploadUserAccount;

    /** 上传用户名字 */
    @Excel(name = "上传用户名字")
    @ApiModelProperty(value = "上传用户名字")
    private String uploadUserName;

    /** 文档下载地址 */
    @Excel(name = "文档下载地址")
    @ApiModelProperty(value = "文档下载地址")
    private String url;

    /** 公司id */
    @Excel(name = "公司id")
    @ApiModelProperty(value = "公司id")
    private String comId;

    /** 原文件名 */
    @Excel(name = "原文件名")
    @ApiModelProperty(value = "原文件名")
    private String oldFileName;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fullPath", getFullPath())
            .append("fileName", getFileName())
            .append("uploadUserId", getUploadUserId())
            .append("uploadUserAccount", getUploadUserAccount())
            .append("uploadUserName", getUploadUserName())
            .append("createTime", getCreateTime())
            .append("url", getUrl())
            .append("comId", getComId())
            .append("oldFileName", getOldFileName())
            .toString();
    }
}
