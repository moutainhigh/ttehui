package com.mocentre.tehui.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类PayConfig.java的实现描述：付款方式配置
 * 
 * @author sz.gong 2016年12月14日 下午3:58:17
 */
public class PayConfig extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 6130284064660006596L;

    /** 商家商户号（mch_id） **/
    private String            partner;

    /** 商户名称 **/
    private String            partnerName;

    /** 收款账号 **/
    private String            sellerId;

    /** 私钥路径(支付宝) **/
    private String            privateKey;

    /** 公钥路径(支付宝) **/
    private String            publicKey;

    /** 签名方式(支付宝默认RSA) **/
    private String            signType;

    /** 编码方式，默认utf-8 **/
    private String            charset;

    /** ca证书路径 **/
    private String            cacertPath;

    /** 访问方式(http) **/
    private String            transport;

    /** 支付类型(alipay,wxpay,kcode) **/
    private String            payType;

    /** 通知方式 **/
    private String            notifyType;

    /** appid **/
    private String            appid;

    /** appid对应的接口密钥 **/
    private String            appsecret;

    /** 是否开启(0,不开启) **/
    private Integer           open;

    /** 签名密钥(微信) **/
    private String            signKey;

    /** 扩展配置项(json存储) **/
    private String            extKey;

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignType() {
        return signType;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

    public void setCacertPath(String cacertPath) {
        this.cacertPath = cacertPath;
    }

    public String getCacertPath() {
        return cacertPath;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getTransport() {
        return transport;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getOpen() {
        return open;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setExtKey(String extKey) {
        this.extKey = extKey;
    }

    public String getExtKey() {
        return extKey;
    }
}
