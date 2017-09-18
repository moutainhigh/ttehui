/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay.query;

/**
 * 类QueryResultData.java的实现描述：农行支付查询数据
 * 
 * @author sz.gong 2017年8月24日 下午4:48:12
 */
public class QueryResultData {

    /** 是否成功 */
    private Boolean isSuccess;

    private String  returnCode;

    private String  errorMessage;

    private String  payTypeID;

    /** 订单编号 **/
    private String  orderNo;
    /** 订单日期 **/
    private String  orderDate;
    /** 订单时间 **/
    private String  orderTime;
    /** 订单金额（元） **/
    private String  orderAmount;
    /** 订单状态 01：未支付 02：无回应 03：已请款 04：成功 05：已退款 07：授权确认成功 00：授权已取消 99：失败 **/
    private String  status;

    public String getPayTypeID() {
        return payTypeID;
    }

    public void setPayTypeID(String payTypeID) {
        this.payTypeID = payTypeID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
