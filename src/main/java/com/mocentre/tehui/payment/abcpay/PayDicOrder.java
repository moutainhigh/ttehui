/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类PayDicOrder.java的实现描述：订单对象属性
 * 
 * @author sz.gong 2016年12月21日 下午4:20:19
 */
public class PayDicOrder {

    private String PayTypeID;       //交易类型（必要信息）
    private String OrderDate;       //订单日期 （必要信息 - YYYY/MM/DD）
    private String OrderTime;       //订单时间 （必要信息 - HH:MM:SS）
    private String orderTimeoutDate; //订单有效期
    private String OrderNo;         //订单编号 （必要信息）
    private String CurrencyCode;    //交易币种（必要信息 - 156：人民币）
    private String OrderAmount;     //交易金额（必要信息 - 保留小数点后两位数字）
    private String Fee;             //手续费金额 （选填信息 - 保留小数点后两位数字）
    private String OrderDesc;       //订单说明
    private String OrderURL;        //订单地址
    private String ReceiverAddress; //收货地址
    private String InstallmentMark; //分期标识（必要信息 - 1：分期；0：不分期。）
    private String InstallmentCode; //分期代码（分期标识为1时，必须设定）
    private String InstallmentNum;  //分期期数（分期标识为1时，必须设定）
    private String CommodityType;   //商品种类（必要信息）
    private String BuyIP;           //IP
    private String ExpiredDate;     //订单保存时间，单位:天

    public PayDicOrder(String PayTypeID, String OrderDate, String OrderTime, String OrderNo, String OrderAmount,
                       String CommodityType, String InstallmentMark) {
        this.PayTypeID = PayTypeID;
        this.OrderDate = OrderDate;
        this.OrderTime = OrderTime;
        this.OrderNo = OrderNo;
        this.OrderAmount = OrderAmount;
        this.CommodityType = CommodityType;
        this.InstallmentMark = InstallmentMark;
    }

    public PayDicOrder(String PayTypeID, String OrderDate, String OrderTime, String OrderNo, String OrderAmount,
                       String CommodityType, String InstallmentMark, String InstallmentCode, String InstallmentNum) {
        new PayDicOrder(PayTypeID, OrderDate, OrderTime, OrderNo, OrderAmount, CommodityType, InstallmentMark);
        this.InstallmentCode = InstallmentCode;
        this.InstallmentNum = InstallmentNum;
    }

    public Map<String, String> getDicOrderMap() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("PayTypeID", this.PayTypeID);
        params.put("OrderDate", this.OrderDate);
        params.put("OrderTime", this.OrderTime);
        params.put("OrderNo", this.OrderNo);
        params.put("CurrencyCode", "156");
        params.put("OrderAmount", this.OrderAmount);
        params.put("InstallmentMark", this.InstallmentMark);
        params.put("CommodityType", this.CommodityType);
        if (orderTimeoutDate != null) {
            params.put("orderTimeoutDate", this.orderTimeoutDate);
        }
        if (Fee != null) {
            params.put("Fee", this.Fee);
        }
        if (OrderURL != null) {
            params.put("OrderURL", this.OrderURL);
        }
        if (ReceiverAddress != null) {
            params.put("ReceiverAddress", this.ReceiverAddress);
        }
        if ("1".equals(InstallmentMark)) {
            params.put("InstallmentCode", this.InstallmentCode);
            params.put("InstallmentNum", this.InstallmentNum);
        }
        if (BuyIP != null) {
            params.put("BuyIP", this.BuyIP);
        }
        if (ExpiredDate != null) {
            params.put("ExpiredDate", this.ExpiredDate);
        }
        return params;
    }

    public String getPayTypeID() {
        return PayTypeID;
    }

    public void setPayTypeID(String payTypeID) {
        PayTypeID = payTypeID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getOrderTimeoutDate() {
        return orderTimeoutDate;
    }

    public void setOrderTimeoutDate(String orderTimeoutDate) {
        this.orderTimeoutDate = orderTimeoutDate;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public String getOrderDesc() {
        return OrderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        OrderDesc = orderDesc;
    }

    public String getOrderURL() {
        return OrderURL;
    }

    public void setOrderURL(String orderURL) {
        OrderURL = orderURL;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getInstallmentMark() {
        return InstallmentMark;
    }

    public void setInstallmentMark(String installmentMark) {
        InstallmentMark = installmentMark;
    }

    public String getInstallmentCode() {
        return InstallmentCode;
    }

    public void setInstallmentCode(String installmentCode) {
        InstallmentCode = installmentCode;
    }

    public String getInstallmentNum() {
        return InstallmentNum;
    }

    public void setInstallmentNum(String installmentNum) {
        InstallmentNum = installmentNum;
    }

    public String getCommodityType() {
        return CommodityType;
    }

    public void setCommodityType(String commodityType) {
        CommodityType = commodityType;
    }

    public String getBuyIP() {
        return BuyIP;
    }

    public void setBuyIP(String buyIP) {
        BuyIP = buyIP;
    }

    public String getExpiredDate() {
        return ExpiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        ExpiredDate = expiredDate;
    }

}
