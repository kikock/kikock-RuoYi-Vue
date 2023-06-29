package com.ruoyi.project.file.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 业务实体关联文档对象 business_info
 *
 * @author jxkj
 * @date 2022-06-23
 */
@Data
public class BusinessInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 业务实体Id */
    @Excel(name = "业务实体Id")
    @ApiModelProperty(value = "业务实体Id")
    private String entityIdIndex;

    /** 文档Id */
    @Excel(name = "文档Id")
    @ApiModelProperty(value = "文档Id")
    private String documentId;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("entityIdIndex", getEntityIdIndex())
            .append("documentId", getDocumentId())
            .toString();
    }
}
