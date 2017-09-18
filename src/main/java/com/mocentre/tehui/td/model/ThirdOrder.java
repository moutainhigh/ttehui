package com.mocentre.tehui.td.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

public class ThirdOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id **/
    private String            abcaid;

    /** 订单编号 **/
    private String            orderNum;

    /** 订单金额（分） **/
    private Long              orderAmount;

    /** 订单状态(wait:待支付 success:支付成功 fail:支付失败 refund:已退款) **/
    private String            orderStatus;

    /** 订单日期时间 **/
    private Date              orderTimes;

    /** 订单日期（yyyy/MM/DD） **/
    private String            orderDate;

    /** 订单时间（HH:mm:ss） **/
    private String            orderTime;

    /** 订单来源 **/
    private String            orderSource;

    /** 商品json格式 **/
    private String            products;

    /** 通知地址 **/
    private String            notifyUrl;

    /** 支付编号 **/
    private String            paymentNum;

    /** 公钥 **/
    private String            appKey;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getOrderTimes() {
        return orderTimes;
    }

    public void setOrderTimes(Date orderTimes) {
        this.orderTimes = orderTimes;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(String paymentNum) {
        this.paymentNum = paymentNum;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAbcaid() {
        return abcaid;
    }

    public void setAbcaid(String abcaid) {
        this.abcaid = abcaid;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

}
