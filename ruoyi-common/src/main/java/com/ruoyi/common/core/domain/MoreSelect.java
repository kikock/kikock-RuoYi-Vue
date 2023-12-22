package com.ruoyi.common.core.domain;

import java.io.Serializable;

/**
 *
 *  @description: 下拉分页单选、多选组件
 *  @create_name: kikock
 *  @create_date: 2023-08-03 14:37
 *
 **/
public class MoreSelect  implements Serializable{
    private static final long serialVersionUID = 1L;
    private String keywords;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
    private String params;

    public String getParams(){
        return params;
    }

    public void setParams(String params){
        this.params = params;
    }

    public String getKeywords(){
        return keywords;
    }

    public void setKeywords(String keywords){
        this.keywords = keywords;
    }

    public Integer getPageNum(){
        return pageNum;
    }

    public void setPageNum(Integer pageNum){
        this.pageNum = pageNum;
    }

    public Integer getPageSize(){
        return pageSize;
    }

    public void setPageSize(Integer pageSize){
        this.pageSize = pageSize;
    }
}
