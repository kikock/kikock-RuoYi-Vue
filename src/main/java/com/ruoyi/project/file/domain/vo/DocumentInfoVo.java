package com.ruoyi.project.file.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文档信息vo
 *
 * @author : dy
 * @since : 2022-06-23 17:02
 */
@Data
public class DocumentInfoVo implements Serializable {
    private static final long serialVersionUID = -6494601690124066814L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private String id;

    /** 文件全路径 */
    @ApiModelProperty(value = "文件全路径")
    private String fullPath;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /** 上传用户ID */
    @ApiModelProperty(value = "上传用户ID")
    private String uploadUserId;

    /** 上传用户账号 */
    @ApiModelProperty(value = "上传用户账号")
    private String uploadUserAccount;

    /** 上传用户名字 */
    @ApiModelProperty(value = "上传用户名字")
    private String uploadUserName;

    /** 文档下载地址 */
    @ApiModelProperty(value = "文档下载地址")
    private String url;

    /** 公司id */
    @ApiModelProperty(value = "公司id")
    private String comId;

    /** 原文件名 */
    @ApiModelProperty(value = "原文件名")
    private String name;
}
