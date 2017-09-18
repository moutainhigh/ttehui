/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay.query;

import com.abc.trustpay.client.Base64;
import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.ebus.QueryOrderRequest;

/**
 * 类QueryRequestHandler.java的实现描述：实现查询
 * 
 * @author sz.gong 2017年8月24日 下午5:04:59
 */
public class QueryRequestHandler {

    private String  payTypeId;

    /** 订单号 **/
    private String  orderNo;
    /** 查询详情 **/
    private Boolean isQueryDetail;

    public QueryRequestHandler(String payTypeId, String orderNo, Boolean isQueryDetail) {
        this.payTypeId = payTypeId;
        this.orderNo = orderNo;
        this.isQueryDetail = isQueryDetail;
    }

    public QueryResultData postRequest() {
        QueryResultData resData = new QueryResultData();
        QueryOrderRequest tReq = new QueryOrderRequest();
        tReq.queryRequest.put("PayTypeID", this.payTypeId);
        tReq.queryRequest.put("OrderNo", this.orderNo);
        if (isQueryDetail) {
            tReq.queryRequest.put("QueryDetail", 1);
        } else {
            tReq.queryRequest.put("QueryDetail", 0);
        }
        JSON json = tReq.postRequest();
        String returnCode = json.GetKeyValue("ReturnCode");
        String errorMessage = json.GetKeyValue("ErrorMessage");
        if (returnCode.equals("0000")) {
            String orderInfo = json.GetKeyValue("Order");
            if (orderInfo.length() < 1) {
                resData.setIsSuccess(false);
                resData.setReturnCode("0001");
                resData.setErrorMessage("查询结果为空");
                return resData;
            }
            Base64 base64 = new Base64();
            String orderDetail = new String(base64.decode(orderInfo));
            json.setJsonString(orderDetail);
            String tPayTypeId = json.GetKeyValue("PayTypeID");
            String tOrderNo = json.GetKeyValue("OrderNo");
            String tOrderDate = json.GetKeyValue("OrderDate");
            String tOrderTime = json.GetKeyValue("OrderTime");
            String tOrderAmount = json.GetKeyValue("OrderAmount");
            String tStatus = json.GetKeyValue("Status");
            resData.setPayTypeID(tPayTypeId);
            resData.setOrderNo(tOrderNo);
            resData.setOrderDate(tOrderDate);
            resData.setOrderTime(tOrderTime);
            resData.setOrderAmount(tOrderAmount);
            resData.setStatus(tStatus);
            resData.setIsSuccess(true);
        } else {
            resData.setIsSuccess(false);
        }
        resData.setReturnCode(returnCode);
        resData.setErrorMessage(errorMessage);
        return resData;
    }

}
