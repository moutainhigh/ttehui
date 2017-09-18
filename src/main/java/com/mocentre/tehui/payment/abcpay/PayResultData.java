/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

/**
 * 类PayResultData.java的实现描述：返回结果对象
 * 
 * @author sz.gong 2016年12月28日 上午10:27:51
 */
public class PayResultData {

    /** 是否成功 **/
    public boolean isSuccess;
    /** 返回code **/
    public String  returnCode;
    /** 失败原因 **/
    public String  errorMessage;
    /** 商户编号 **/
    public String  merchantID;
    /** 交易种类 **/
    public String  trxType;
    /** 订单号 **/
    public String  orderNo;
    /** 订单金额 **/
    public String  amount;
    /** 交易批次号 **/
    public String  batchNo;
    /** 交易凭证 **/
    public String  voucherNo;
    /** 银行交易日期 YYYY/MM/DD **/
    public String  hostDate;
    /** 银行交易时间 HH:MM:SS **/
    public String  hostTime;
    /** 商户备注信息 **/
    public String  merchantRemarks;
    /** 消费者支付方式，已 EP开始 **/
    public String  payType;
    /** 支付结果通知方式 0：页面通知 1：服务器通知 **/
    public String  notifyType;
    /** 交易流水号，可用户对账 **/
    public String  irspRef;

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getMerchantRemarks() {
        return merchantRemarks;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getIrspRef() {
        return irspRef;
    }

    public void setIrspRef(String irspRef) {
        this.irspRef = irspRef;
    }

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

}
