package com.mocentre.tehui.weixin.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 微信配置
 * Created by 王雪莹 on 2017/1/12.
 */
public class Config extends BaseEntity {
    // 项目code
    String code;
    // appid
    String appid;
    // secret
    String secret;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
