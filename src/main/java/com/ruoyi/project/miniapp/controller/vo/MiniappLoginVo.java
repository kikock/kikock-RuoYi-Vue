package com.ruoyi.project.miniapp.controller.vo;

import java.io.Serializable;

/**
 * 小程序登录vo
 *
 * @author : dy
 * @since : 2022-05-25 16:24
 */
public class MiniappLoginVo implements Serializable {
    private static final long serialVersionUID = -7948479907769216685L;
    /**
     * jsCode 登录时获取的 code
     */
    private String code;
    /**
     * 数据签名
     */
    private String signature;
    /**
     * 微信用户基本信息
     */
    private String rawData;
    /**
     * 消息密文
     */
    private String encryptedData;
    /**
     * 加密算法的初始向量
     */
    private String iv;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
