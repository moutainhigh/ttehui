package com.mocentre.tehui.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类OrderBill.java的实现描述：订单发票
 * 
 * @author sz.gong 2016年11月15日 下午5:07:13
 */
public class OrderBill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id **/
    private Long              customerId;

    /** 订单id **/
    private Long              orderId;

    /** 订单编码 **/
    private String            orderNum;

    /** 发票抬头 **/
    private String            billHeader;

    /** 发票备注 **/
    private String            billRemark;

    /** 发票类型（personal:个人 compay:公司） **/
    private String            billType;

    /** 发票状态（have_open：已开发票 no_open：未开发票） **/
    private String            billStatus;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setBillHeader(String billHeader) {
        this.billHeader = billHeader;
    }

    public String getBillHeader() {
        return billHeader;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillStatus() {
        return billStatus;
    }

}
