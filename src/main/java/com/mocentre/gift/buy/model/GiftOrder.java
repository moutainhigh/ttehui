package com.mocentre.gift.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

import java.util.Date;

/**
 * 订单
 * Created by 王雪莹 on 2017/4/6.
 */
public class GiftOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 订单编号 **/
    private String            orderNum;

    /** 商品总价 **/
    private String              totalPrice;

    /** 下单时间 **/
    private Date              orderTime;

    /** 订单状态（等待买家付款 等待卖家发货 等待确认收货 交易成功 交易关闭） **/
    private String            orderStatus;

    /** 收货地址id **/
    private Long              addressId;

    /** 用户id **/
    private Long              customerId;

    /** 发票状态（have_open：已开发票 no_open：未开发票） **/
    private String            billStatus;

    /** 收件人 **/
    private String            recipient;

    /** 联系电话 **/
    private String            telephone;

    /** 收货地址 **/
    private String            address;
    /** 备注 **/
    private String            note;
    /** 快递公司 **/
    private String            expCompany;
    /** 快递单号 **/
    private String            expNum;
    /** 商品类型数量 **/
    private Integer            typeNum;
    /** 商品数量 **/
    private Integer            goodsNum;

    public String getExpCompany() {
        return expCompany;
    }

    public void setExpCompany(String expCompany) {
        this.expCompany = expCompany;
    }

    public String getExpNum() {
        return expNum;
    }

    public void setExpNum(String expNum) {
        this.expNum = expNum;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(Integer typeNum) {
        this.typeNum = typeNum;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
