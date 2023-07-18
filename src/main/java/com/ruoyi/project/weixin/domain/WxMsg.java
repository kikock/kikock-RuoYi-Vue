package com.ruoyi.project.weixin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 微信消息对象 sys_wx_msg
 *
 * @author ruoyi
 * @date 2022-04-20
 */
public class WxMsg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 创建者 */
    @Excel(name = "创建者")
    private String createId;

    /** 更新者 */
    @Excel(name = "更新者")
    private String updateId;

    /** 逻辑删除标记（0：显示；1：隐藏） */
    private String delFlag;

    /** 公众号名称 */
    @Excel(name = "公众号名称")
    private String appName;

    /** 公众号logo */
    @Excel(name = "公众号logo")
    private String appLogo;

    /** 微信用户ID */
    @Excel(name = "微信用户ID")
    private String wxUserId;

    /** 微信用户昵称 */
    @Excel(name = "微信用户昵称")
    private String nickName;

    /** 微信用户头像 */
    @Excel(name = "微信用户头像")
    private String headimgUrl;

    /** 消息分类（1、用户发给公众号；2、公众号发给用户；） */
    @Excel(name = "消息分类", readConverterExp = "1=、用户发给公众号；2、公众号发给用户；")
    private String type;

    /** 消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置；music：音乐；news：图文；event：推送事件） */
    @Excel(name = "消息类型", readConverterExp = "t=ext：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置；music：音乐；news：图文；event：推送事件")
    private String repType;

    /** 事件类型（subscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件） */
    @Excel(name = "事件类型", readConverterExp = "s=ubscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件")
    private String repEvent;

    /** 回复类型文本保存文字、地理位置信息 */
    @Excel(name = "回复类型文本保存文字、地理位置信息")
    private String repContent;

    /** 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id */
    @Excel(name = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id")
    private String repMediaId;

    /** 回复的素材名、视频和音乐的标题 */
    @Excel(name = "回复的素材名、视频和音乐的标题")
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

    /** 图文消息的内容 */
    @Excel(name = "图文消息的内容")
    private String content;

    /** 缩略图的媒体id */
    @Excel(name = "缩略图的媒体id")
    private String repThumbMediaId;

    /** 缩略图url */
    @Excel(name = "缩略图url")
    private String repThumbUrl;

    /** 地理位置维度 */
    @Excel(name = "地理位置维度")
    private Long repLocationX;

    /** 地理位置经度 */
    @Excel(name = "地理位置经度")
    private Long repLocationY;

    /** 地图缩放大小 */
    @Excel(name = "地图缩放大小")
    private Long repScale;

    /** 已读标记（1：是；0：否） */
    @Excel(name = "已读标记", readConverterExp = "1=：是；0：否")
    private String readFlag;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }

    public String getCreateId()
    {
        return createId;
    }
    public void setUpdateId(String updateId)
    {
        this.updateId = updateId;
    }

    public String getUpdateId()
    {
        return updateId;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getAppName()
    {
        return appName;
    }
    public void setAppLogo(String appLogo)
    {
        this.appLogo = appLogo;
    }

    public String getAppLogo()
    {
        return appLogo;
    }
    public void setWxUserId(String wxUserId)
    {
        this.wxUserId = wxUserId;
    }

    public String getWxUserId()
    {
        return wxUserId;
    }
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }
    public void setHeadimgUrl(String headimgUrl)
    {
        this.headimgUrl = headimgUrl;
    }

    public String getHeadimgUrl()
    {
        return headimgUrl;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setRepType(String repType)
    {
        this.repType = repType;
    }

    public String getRepType()
    {
        return repType;
    }
    public void setRepEvent(String repEvent)
    {
        this.repEvent = repEvent;
    }

    public String getRepEvent()
    {
        return repEvent;
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
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
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
    public void setRepLocationX(Long repLocationX)
    {
        this.repLocationX = repLocationX;
    }

    public Long getRepLocationX()
    {
        return repLocationX;
    }
    public void setRepLocationY(Long repLocationY)
    {
        this.repLocationY = repLocationY;
    }

    public Long getRepLocationY()
    {
        return repLocationY;
    }
    public void setRepScale(Long repScale)
    {
        this.repScale = repScale;
    }

    public Long getRepScale()
    {
        return repScale;
    }
    public void setReadFlag(String readFlag)
    {
        this.readFlag = readFlag;
    }

    public String getReadFlag()
    {
        return readFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("createTime", getCreateTime())
            .append("updateId", getUpdateId())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("appName", getAppName())
            .append("appLogo", getAppLogo())
            .append("wxUserId", getWxUserId())
            .append("nickName", getNickName())
            .append("headimgUrl", getHeadimgUrl())
            .append("type", getType())
            .append("repType", getRepType())
            .append("repEvent", getRepEvent())
            .append("repContent", getRepContent())
            .append("repMediaId", getRepMediaId())
            .append("repName", getRepName())
            .append("repDesc", getRepDesc())
            .append("repUrl", getRepUrl())
            .append("repHqUrl", getRepHqUrl())
            .append("content", getContent())
            .append("repThumbMediaId", getRepThumbMediaId())
            .append("repThumbUrl", getRepThumbUrl())
            .append("repLocationX", getRepLocationX())
            .append("repLocationY", getRepLocationY())
            .append("repScale", getRepScale())
            .append("readFlag", getReadFlag())
            .toString();
    }
}
