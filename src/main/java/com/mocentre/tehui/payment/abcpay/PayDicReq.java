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
 * 类PayDicReq.java的实现描述：支付请求对象
 * 
 * @author sz.gong 2016年12月22日 上午9:55:36
 */
public class PayDicReq {

    /** 支付类型（必须） **/
    private String PaymentType;
    /** 交易渠道（必须） **/
    private String PaymentLinkType;
    /** 银联跨行移动支付接入方式 **/
    private String UnionPayLinkType;
    /** 收款方账号 **/
    private String ReceiveAccount;
    /** 收款方户名 **/
    private String ReceiveAccName;
    /** 通知方式（必须） **/
    private String NotifyType;
    /** 通知URL地址（必须） **/
    private String ResultNotifyURL;
    /** 附言 **/
    private String MerchantRemarks;
    /** 交易是否分账（必须） **/
    private String IsBreakAccount;
    /** 模版编号 **/
    private String SplitAccTemplate;

    public PayDicReq(String PaymentType, String PaymentLinkType, String NotifyType, String resultNotifyURL,
                     String IsBreakAccount) {
        this.PaymentType = PaymentType;
        this.PaymentLinkType = PaymentLinkType;
        this.NotifyType = NotifyType;
        this.ResultNotifyURL = resultNotifyURL;
        this.IsBreakAccount = IsBreakAccount;
    }

    public Map<String, String> getDicReqMap() {
        Map<String, String> param = new LinkedHashMap<String, String>();
        param.put("PaymentType", this.PaymentType);
        param.put("PaymentLinkType", this.PaymentLinkType);
        param.put("NotifyType", this.NotifyType);
        param.put("ResultNotifyURL", this.ResultNotifyURL);
        param.put("IsBreakAccount", this.IsBreakAccount);
        if (Constant.PAYMENT_TYPE_YLKHZF.equals(PaymentType)
                && Constant.PAYMENT_LINK_TYPE_SJWLJR.equals(PaymentLinkType)) {
            param.put("UnionPayLinkType", Constant.UNION_PAY_LINK_TYPE_YMJR);
        }
        if (ReceiveAccount != null) {
            param.put("ReceiveAccount", this.ReceiveAccount);
        }
        if (ReceiveAccName != null) {
            param.put("ReceiveAccName", this.ReceiveAccName);
        }
        if (MerchantRemarks != null) {
            param.put("MerchantRemarks", this.MerchantRemarks);
        }
        if (SplitAccTemplate != null) {
            param.put("SplitAccTemplate", this.SplitAccTemplate);
        }
        return param;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getPaymentLinkType() {
        return PaymentLinkType;
    }

    public void setPaymentLinkType(String paymentLinkType) {
        PaymentLinkType = paymentLinkType;
    }

    public String getUnionPayLinkType() {
        return UnionPayLinkType;
    }

    public void setUnionPayLinkType(String unionPayLinkType) {
        UnionPayLinkType = unionPayLinkType;
    }

    public String getReceiveAccount() {
        return ReceiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        ReceiveAccount = receiveAccount;
    }

    public String getReceiveAccName() {
        return ReceiveAccName;
    }

    public void setReceiveAccName(String receiveAccName) {
        ReceiveAccName = receiveAccName;
    }

    public String getNotifyType() {
        return NotifyType;
    }

    public void setNotifyType(String notifyType) {
        NotifyType = notifyType;
    }

    public String getResultNotifyURL() {
        return ResultNotifyURL;
    }

    public void setResultNotifyURL(String resultNotifyURL) {
        ResultNotifyURL = resultNotifyURL;
    }

    public String getMerchantRemarks() {
        return MerchantRemarks;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        MerchantRemarks = merchantRemarks;
    }

    public String getIsBreakAccount() {
        return IsBreakAccount;
    }

    public void setIsBreakAccount(String isBreakAccount) {
        IsBreakAccount = isBreakAccount;
    }

    public String getSplitAccTemplate() {
        return SplitAccTemplate;
    }

    public void setSplitAccTemplate(String splitAccTemplate) {
        SplitAccTemplate = splitAccTemplate;
    }

}
