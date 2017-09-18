/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

import java.io.Serializable;

/**
 * 类AbcData.java的实现描述：返回的结果
 * 
 * @author sz.gong 2016年12月22日 下午1:58:20
 */
public class AbcData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2135718425345405485L;

    private String            returnCode;

    private String            errorMessage;

    private String            paymentURL;

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

    public String getPaymentURL() {
        return paymentURL;
    }

    public void setPaymentURL(String paymentURL) {
        this.paymentURL = paymentURL;
    }

}
