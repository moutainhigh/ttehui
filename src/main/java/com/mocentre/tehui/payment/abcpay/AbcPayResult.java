/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

import com.abc.trustpay.client.TrxException;

/**
 * 类AbcPayResult.java的实现描述：支付结果
 * 
 * @author sz.gong 2016年12月28日 上午10:23:57
 */
public class AbcPayResult {

    private String message;

    public AbcPayResult(String message) {
        this.message = message;
    }

    /**
     * 返回支付结果数据
     * 
     * @return
     * @throws TrxException
     */
    public PayResultData getResult() throws TrxException {
        //PaymentResult payResult = new PaymentResult(message);
        MyPaymentResult payResult = new MyPaymentResult(message);
        String merchantID = payResult.getValue("MerchantID");
        String trxType = payResult.getValue("TrxType");
        String orderNo = payResult.getValue("OrderNo");
        String amount = payResult.getValue("Amount");
        String batchNo = payResult.getValue("BatchNo");
        String voucherNo = payResult.getValue("VoucherNo");
        String hostDate = payResult.getValue("HostDate");
        String hostTime = payResult.getValue("HostTime");
        String merchantRemarks = payResult.getValue("MerchantRemarks");
        String payType = payResult.getValue("PayType");
        String notifyType = payResult.getValue("NotifyType");
        String iRspRef = payResult.getValue("iRspRef");
        PayResultData resultData = new PayResultData();
        resultData.setAmount(amount);
        resultData.setBatchNo(batchNo);
        resultData.setHostDate(hostDate);
        resultData.setHostTime(hostTime);
        resultData.setIrspRef(iRspRef);
        resultData.setMerchantID(merchantID);
        resultData.setMerchantRemarks(merchantRemarks);
        resultData.setNotifyType(notifyType);
        resultData.setOrderNo(orderNo);
        resultData.setPayType(payType);
        resultData.setTrxType(trxType);
        resultData.setVoucherNo(voucherNo);
        resultData.setSuccess(payResult.isSuccess());
        resultData.setReturnCode(payResult.getReturnCode());
        resultData.setErrorMessage(payResult.getErrorMessage());
        return resultData;
    }

}
