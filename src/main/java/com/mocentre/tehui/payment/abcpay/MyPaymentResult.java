/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

import java.io.UnsupportedEncodingException;

import com.abc.trustpay.client.Base64;
import com.abc.trustpay.client.LogWriter;
import com.abc.trustpay.client.MerchantConfig;
import com.abc.trustpay.client.TrxException;
import com.abc.trustpay.client.TrxResponse;
import com.abc.trustpay.client.XMLDocument;

/**
 * 类MpaymentResult.java的实现描述：农行回调验证
 * 
 * @author sz.gong 2017年4月25日 下午4:27:33
 */
public class MyPaymentResult extends TrxResponse {

    public MyPaymentResult(String aMessage) throws TrxException {
        super("Notify", aMessage);
        LogWriter tLogWriter = null;
        try {
            tLogWriter = new LogWriter();
            tLogWriter.logNewLine("TrustPayClient Java V2.0 交易开始==========================");
            tLogWriter.logNewLine("接收到的支付结果通知：\n[" + aMessage + "]");

            Base64 tBase64 = new Base64();
            String tMessage = new String(tBase64.decode(aMessage), "gbk");
            tLogWriter.logNewLine("经过Base64解码后的支付结果通知：\n[" + tMessage + "]");

            tLogWriter.logNewLine("验证支付结果通知的签名：");
            XMLDocument tResult = MerchantConfig.getUniqueInstance().verifySignXML(new XMLDocument(tMessage));
            tLogWriter.logNewLine("验证通过！\n 经过验证的支付结果通知：\n[" + tResult.toString() + "]");

            init(tResult);
        } catch (TrxException e) {
            setReturnCode(e.getCode());
            setErrorMessage(e.getMessage() + "-" + e.getDetailMessage());
            tLogWriter.log("验证失败！\n");
        } catch (UnsupportedEncodingException e) {
            setReturnCode("1400");
            setErrorMessage(e.getMessage() + "-" + "编码错误");
            tLogWriter.log("验证失败！\n");
        } finally {
            if (tLogWriter != null) {
                tLogWriter.logNewLine("交易结束==================================================");
                try {
                    tLogWriter.closeWriter(MerchantConfig.getTrxLogFile("PayResultLog"));
                } catch (Exception localException1) {
                }
            }
        }

    }

}
