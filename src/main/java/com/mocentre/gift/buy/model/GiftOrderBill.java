package com.mocentre.gift.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 发票表
 * Created by 王雪莹 on 2017/4/6.
 */
public class GiftOrderBill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id **/
    private Long              customerId;
    /** 订单id **/
    private Long               orderId;
    /** 订单编号 **/
    private String            orderNum;

    /** 是否为纸质（paper：纸质发票  electronic ：电子发票） **/
    private String            isPaper;

    /** 是否是增值税发票（VAT：增值税发票  general：普通发票） **/
    private String            isVAT;

    /** 纳税人识别号 **/
    private String            taxId;

    /** 名称 **/
    private String            header;

    /** 地址 **/
    private String            address;

    /** 联系电话 **/
    private String            telephone;

    /** 开户行 **/
    private String            bank;

    /** 银行卡卡号 **/
    private String            accountNum;

    /** 发票类型（personal:个人  compay:公司） **/
    private String            isCompay;

    /** 是否为默认 **/
    private String            isDefault;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getIsPaper() {
        return isPaper;
    }

    public void setIsPaper(String isPaper) {
        this.isPaper = isPaper;
    }

    public String getIsVAT() {
        return isVAT;
    }

    public void setIsVAT(String isVAT) {
        this.isVAT = isVAT;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getIsCompay() {
        return isCompay;
    }

    public void setIsCompay(String isCompay) {
        this.isCompay = isCompay;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
