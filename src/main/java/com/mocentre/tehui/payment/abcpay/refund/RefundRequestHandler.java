/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay.refund;

import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.ebus.RefundRequest;

/**
 * 类RefundRequestHandler.java的实现描述：实现退款
 * 
 * @author sz.gong 2017年6月23日 下午5:08:33
 */
public class RefundRequestHandler {

    /** 订单日期（必要信息）yyyy/MM/dd */
    private String orderDate;
    /** 订单时间（必要信息） HH:mm:ss */
    private String orderTime;
    /** 原交易编号（必要信息） */
    private String orderNo;
    /** 交易编号（必要信息） */
    private String newOrderNo;
    /** 退货金额 （必要信息） */
    private String trxAmount;
    /** 交易币种（必要信息） */
    private String currencyCode = "156";

    public RefundRequestHandler(String orderDate, String orderTime, String orderNo, String newOrderNo, String trxAmount) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderNo = orderNo;
        this.newOrderNo = newOrderNo;
        this.trxAmount = trxAmount;
    }

    public RefundResultData postRequest() {
        RefundResultData resData = new RefundResultData();
        //1、调用农行退款
        RefundRequest tRequest = new RefundRequest();
        tRequest.dicRequest.put("OrderDate", this.orderDate);
        tRequest.dicRequest.put("OrderTime", this.orderTime);
        tRequest.dicRequest.put("OrderNo", this.orderNo);
        tRequest.dicRequest.put("NewOrderNo", this.newOrderNo);
        tRequest.dicRequest.put("CurrencyCode", this.currencyCode);
        tRequest.dicRequest.put("TrxAmount", this.trxAmount);
        //2、传送退款请求并取得退货结果
        JSON json = tRequest.postRequest();
        //3、判断退款结果状态，进行后续操作
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        if (ReturnCode.equals("0000")) {
            //4、退款成功
            resData.setSuccess(true);
            resData.setOrderNo(json.GetKeyValue("OrderNo"));
            resData.setNewOrderNo(json.GetKeyValue("NewOrderNo"));
            resData.setTrxAmount(json.GetKeyValue("TrxAmount"));
            resData.setBatchNo(json.GetKeyValue("BatchNo"));
            resData.setVoucherNo(json.GetKeyValue("VoucherNo"));
            resData.setHostDate(json.GetKeyValue("HostDate"));
            resData.setHostTime(json.GetKeyValue("HostTime"));
            resData.setIrspRef(json.GetKeyValue("iRspRef"));
            resData.setErrorMessage(ErrorMessage);
            resData.setReturnCode(ReturnCode);
            //            System.out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            //            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
            //            System.out.println("OrderNo   = [" + json.GetKeyValue("OrderNo") + "]<br/>");
            //            System.out.println("NewOrderNo   = [" + json.GetKeyValue("NewOrderNo") + "]<br/>");
            //            System.out.println("TrxAmount = [" + json.GetKeyValue("TrxAmount") + "]<br/>");
            //            System.out.println("BatchNo   = [" + json.GetKeyValue("BatchNo") + "]<br/>");
            //            System.out.println("VoucherNo = [" + json.GetKeyValue("VoucherNo") + "]<br/>");
            //            System.out.println("HostDate  = [" + json.GetKeyValue("HostDate") + "]<br/>");
            //            System.out.println("HostTime  = [" + json.GetKeyValue("HostTime") + "]<br/>");
            //            System.out.println("iRspRef  = [" + json.GetKeyValue("iRspRef") + "]<br/>");
        } else {
            resData.setSuccess(false);
            resData.setReturnCode(ReturnCode);
            resData.setErrorMessage(ErrorMessage);
            //            System.out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            //            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
        return resData;
    }

}
