package com.ruoyi.common.core.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @project_name: ssosrasb-service
 * @description: 下拉分页单选、多选组件接收参数
 * @create_name: kikock
 * @create_date: 2023-08-03 14:37
 **/
@Data
public class SelectMoreRequest implements Serializable{
    private String keywords;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
    private String params;
}
