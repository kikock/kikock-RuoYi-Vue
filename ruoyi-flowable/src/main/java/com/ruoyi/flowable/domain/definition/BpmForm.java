package com.ruoyi.flowable.domain.definition;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 工作流的单定义对象 bpm_form
 *
 * @author kikock
 * @date 2023-12-25
 */
public class BpmForm extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 表单名
     */
    @Excel(name = "表单名")
    private String name;

    /**
     * 开启状态
     */
    @Excel(name = "开启状态")
    private Integer status;

    /**
     * 表单的配置
     */
    @Excel(name = "表单的配置")
    @NotNull(message = "表单的配置不能为空")
    private String conf;

    /**
     * 表单项的数组
     */
    private String fields;

    @NotNull(message = "表单项的数组不能为空")
    private List<String> fieldsArr;
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

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setConf(String conf){
        this.conf = conf;
    }

    public String getConf(){
        return conf;
    }

    public void setFields(String fields){
        this.fields = fields;
    }

    public String getFields(){
        return fields;
    }

    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }

    public List<String> getFieldsArr(){
        return fieldsArr;
    }

    public void setFieldsArr(List<String> fieldsArr){
        this.fieldsArr = fieldsArr;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("status", getStatus())
                .append("conf", getConf())
                .append("fields", getFields())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
