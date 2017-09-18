package com.mocentre.tehui.td.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 第三方账户管理model
 */
public class MemberAccount extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -3370478393865787283L;

    /** appKey **/
    private String            appKey;

    /** appSecret **/
    private String            appSecret;

    /** 支付完成，跳转链接 **/
    private String            returnUrl;

    /** 是否启用 **/
    private Integer           isDeny;

    /** 备注 **/
    private String            remark;

    /** 活动标识 **/
    private String            keymark;

    /** 农行分配的appid **/
    private String            abcAppid;

    /** 农行分配的appSecret **/
    private String            abcAppsecret;

    /** 页面地址 **/
    private String            pageAddress;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Integer getIsDeny() {
        return isDeny;
    }

    public void setIsDeny(Integer isDeny) {
        this.isDeny = isDeny;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKeymark() {
        return keymark;
    }

    public void setKeymark(String keymark) {
        this.keymark = keymark;
    }

    public String getAbcAppid() {
        return abcAppid;
    }

    public void setAbcAppid(String abcAppid) {
        this.abcAppid = abcAppid;
    }

    public String getAbcAppsecret() {
        return abcAppsecret;
    }

    public void setAbcAppsecret(String abcAppsecret) {
        this.abcAppsecret = abcAppsecret;
    }

    public String getPageAddress() {
        return pageAddress;
    }

    public void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
