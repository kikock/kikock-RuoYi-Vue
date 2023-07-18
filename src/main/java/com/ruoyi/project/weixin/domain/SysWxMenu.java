package com.ruoyi.project.weixin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义菜单对象 sys_wx_menu
 *
 * @author ruoyi
 * @date 2022-04-14
 */
public class SysWxMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜单ID（click、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、pic_weixin、location_select：保存key） */
    private String id;

    /** 逻辑删除标记（0：显示；1：隐藏） */
    private String delFlag;

    /** 排序值 */
    @Excel(name = "排序值")
    private Long sort;

    /** 父菜单ID */
    @Excel(name = "父菜单ID")
    private String parentId;

    /** 菜单类型click、view、miniprogram、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、pic_weixin、location_select、media_id、view_limited等 */
    @Excel(name = "菜单类型click、view、miniprogram、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、pic_weixin、location_select、media_id、view_limited等")
    private String type;

    /** 菜单名 */
    @Excel(name = "菜单名")
    private String name;

    /** view、miniprogram保存链接 */
    @Excel(name = "view、miniprogram保存链接")
    private String url;

    /** 小程序的appid */
    @Excel(name = "小程序的appid")
    private String maAppId;

    /** 小程序的页面路径 */
    @Excel(name = "小程序的页面路径")
    private String maPagePath;

    /** 回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文） */
    @Excel(name = "回复消息类型", readConverterExp = "t=ext：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文")
    private String repType;

    /** Text:保存文字 */
    @Excel(name = "Text:保存文字")
    private String repContent;

    /** imge、voice、news、video：mediaID */
    @Excel(name = "imge、voice、news、video：mediaID")
    private String repMediaId;

    /** 素材名、视频和音乐的标题 */
    @Excel(name = "素材名、视频和音乐的标题")
    private String repName;

    /** 视频和音乐的描述 */
    @Excel(name = "视频和音乐的描述")
    private String repDesc;

    /** 链接 */
    @Excel(name = "链接")
    private String repUrl;

    /** 高质量链接 */
    @Excel(name = "高质量链接")
    private String repHqUrl;

    /** 缩略图的媒体id */
    @Excel(name = "缩略图的媒体id")
    private String repThumbMediaId;

    /** 缩略图url */
    @Excel(name = "缩略图url")
    private String repThumbUrl;

    /** 图文消息的内容 */
    @Excel(name = "图文消息的内容")
    private String content;

    /** 子菜单 */
    private List<SysWxMenu> children = new ArrayList<SysWxMenu>();

    /**
     * 是否授权 0 是 1 否
     */
    private String isOauth;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setMaAppId(String maAppId)
    {
        this.maAppId = maAppId;
    }

    public String getMaAppId()
    {
        return maAppId;
    }
    public void setMaPagePath(String maPagePath)
    {
        this.maPagePath = maPagePath;
    }

    public String getMaPagePath()
    {
        return maPagePath;
    }
    public void setRepType(String repType)
    {
        this.repType = repType;
    }

    public String getRepType()
    {
        return repType;
    }
    public void setRepContent(String repContent)
    {
        this.repContent = repContent;
    }

    public String getRepContent()
    {
        return repContent;
    }
    public void setRepMediaId(String repMediaId)
    {
        this.repMediaId = repMediaId;
    }

    public String getRepMediaId()
    {
        return repMediaId;
    }
    public void setRepName(String repName)
    {
        this.repName = repName;
    }

    public String getRepName()
    {
        return repName;
    }
    public void setRepDesc(String repDesc)
    {
        this.repDesc = repDesc;
    }

    public String getRepDesc()
    {
        return repDesc;
    }
    public void setRepUrl(String repUrl)
    {
        this.repUrl = repUrl;
    }

    public String getRepUrl()
    {
        return repUrl;
    }
    public void setRepHqUrl(String repHqUrl)
    {
        this.repHqUrl = repHqUrl;
    }

    public String getRepHqUrl()
    {
        return repHqUrl;
    }
    public void setRepThumbMediaId(String repThumbMediaId)
    {
        this.repThumbMediaId = repThumbMediaId;
    }

    public String getRepThumbMediaId()
    {
        return repThumbMediaId;
    }
    public void setRepThumbUrl(String repThumbUrl)
    {
        this.repThumbUrl = repThumbUrl;
    }

    public String getRepThumbUrl()
    {
        return repThumbUrl;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public List<SysWxMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysWxMenu> children) {
        this.children = children;
    }

    public String getIsOauth() {
        return isOauth;
    }

    public void setIsOauth(String isOauth) {
        this.isOauth = isOauth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("sort", getSort())
            .append("parentId", getParentId())
            .append("type", getType())
            .append("name", getName())
            .append("url", getUrl())
            .append("maAppId", getMaAppId())
            .append("maPagePath", getMaPagePath())
            .append("repType", getRepType())
            .append("repContent", getRepContent())
            .append("repMediaId", getRepMediaId())
            .append("repName", getRepName())
            .append("repDesc", getRepDesc())
            .append("repUrl", getRepUrl())
            .append("repHqUrl", getRepHqUrl())
            .append("repThumbMediaId", getRepThumbMediaId())
            .append("repThumbUrl", getRepThumbUrl())
            .append("content", getContent())
            .toString();
    }
}
