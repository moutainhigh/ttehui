package com.mocentre.tehui.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类Order.java的实现描述：订单model
 * 
 * @author sz.gong 2016年11月10日 下午3:38:41
 */
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 店铺id **/
    private Long              shopId;

    /** 订单编号 **/
    private String            orderNum;

    /** 是否开发票 **/
    private Integer           isInvoice;

    /** 订单总价(实付总金额) **/
    private Long              totalPrice;

    /** 配送费 **/
    private Long              transFee;

    /** 下单时间 **/
    private java.util.Date    orderTime;

    /** 订单状态（等待买家付款 等待卖家发货 等待确认收货 交易成功 交易关闭） **/
    private String            orderStatus;

    /** 处理状态 **/
    private String            dealStatus;

    /** 用户备注 **/
    private String            remark;

    /** 收货地址id **/
    private Long              addressId;

    /** 用户id **/
    private Long              customerId;

    /** 收件人 **/
    private String            recipient;

    /** 联系电话 **/
    private String            telephone;

    /** 收货地址 **/
    private String            address;

    /** 使用优惠券编码 **/
    private String            couponSn;

    /** 优惠金额 **/
    private Long              couponMoney;

    /** 订单类型 **/
    private String            orderType;

    /** 不同类型的id **/
    private Long              typeId;

    /** 支付编号 **/
    private String            paymentNum;

    /** 来源类型（tehui:商城 abc:农行客户端） **/
    private String            sourceType;

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTransFee(Long transFee) {
        this.transFee = transFee;
    }

    public Long getTransFee() {
        return transFee;
    }

    public void setOrderTime(java.util.Date orderTime) {
        this.orderTime = orderTime;
    }

    public java.util.Date getOrderTime() {
        return orderTime;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public Long getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Long couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(String paymentNum) {
        this.paymentNum = paymentNum;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

}
