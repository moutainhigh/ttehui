/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay.refund;

/**
 * 类RefundResultData.java的实现描述：退款返回结果对象
 * 
 * @author sz.gong 2017年6月23日 下午5:20:27
 */
public class RefundResultData {

    /** 是否成功 **/
    public boolean isSuccess;
    /** 返回code **/
    public String  returnCode;
    /** 失败原因 **/
    public String  errorMessage;
    /** 退款订单号 **/
    public String  orderNo;
    /** 退款流水号 **/
    public String  newOrderNo;
    /** 退款金额 **/
    public String  trxAmount;
    /** 交易批次号 **/
    public String  batchNo;
    /** 交易凭证 **/
    public String  voucherNo;
    /** 银行交易日期 YYYY/MM/DD **/
    public String  hostDate;
    /** 银行交易时间 HH:MM:SS **/
    public String  hostTime;
    /** 交易流水号，可用户对账 **/
    public String  irspRef;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getNewOrderNo() {
        return newOrderNo;
    }

    public void setNewOrderNo(String newOrderNo) {
        this.newOrderNo = newOrderNo;
    }

    public String getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(String trxAmount) {
        this.trxAmount = trxAmount;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getHostDate() {
        return hostDate;
    }

    public void setHostDate(String hostDate) {
        this.hostDate = hostDate;
    }

    public String getHostTime() {
        return hostTime;
    }

    public void setHostTime(String hostTime) {
        this.hostTime = hostTime;
    }

    public String getIrspRef() {
        return irspRef;
    }

    public void setIrspRef(String irspRef) {
        this.irspRef = irspRef;
    }

}
