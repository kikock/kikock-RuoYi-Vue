package com.ruoyi.common.core.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @project_name: ssosrasb-service
 * @description: 下拉分页单选、多选组件返回数据
 * @create_name: kikock
 * @create_date: 2024-01-03 14:37
 **/
@Data
public class SelectMoreVo implements Serializable{
    /**
     * key
     */
    private Long id;
    /**
     * code
     */
    private String code;
    /**
     * value
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
}
