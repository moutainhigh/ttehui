/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

import java.util.List;

import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.ebus.PaymentRequest;
import com.mocentre.common.PlainResult;

/**
 * 类AbcPayRequest.java的实现描述：abc生成对象
 * 
 * @author sz.gong 2016年12月22日 下午12:08:23
 */
public class AbcPayRequest {

    private PaymentRequest     paymentReq;
    /** 订单对象 **/
    private PayDicOrder        dicOrder;
    /** 订单明细 **/
    private List<PayOrderItem> orderItemList;
    /** 支付请求对象 **/
    private PayDicReq          dicReq;

    public AbcPayRequest(PayDicOrder dicOrder, List<PayOrderItem> orderItemList, PayDicReq dicReq) {
        this.paymentReq = new PaymentRequest();
        this.dicOrder = dicOrder;
        this.orderItemList = orderItemList;
        this.dicReq = dicReq;
    }

    @SuppressWarnings("unchecked")
    public PlainResult<AbcData> sendRequest() {
        PlainResult<AbcData> result = new PlainResult<AbcData>();
        AbcData data = new AbcData();
        paymentReq.dicOrder.putAll(dicOrder.getDicOrderMap());
        for (int i = 0; i < orderItemList.size(); i++) {
            PayOrderItem orderItem = orderItemList.get(i);
            paymentReq.orderitems.put(i + 1, orderItem.getOrderItemMap());
        }
        paymentReq.dicRequest.putAll(dicReq.getDicReqMap());
        JSON json = paymentReq.postRequest();
        String returnCode = json.GetKeyValue("ReturnCode");
        String errorMessage = json.GetKeyValue("ErrorMessage");
        String paymentUrl = json.GetKeyValue("PaymentURL");
        if (returnCode.equals("0000")) {
            data.setReturnCode(returnCode);
            data.setErrorMessage(errorMessage);
            data.setPaymentURL(paymentUrl);
        } else {
            data.setErrorMessage(errorMessage);
            data.setReturnCode(returnCode);
            result.setErrorMessage(returnCode, errorMessage);
        }
        result.setData(data);
        return result;
    }
}
