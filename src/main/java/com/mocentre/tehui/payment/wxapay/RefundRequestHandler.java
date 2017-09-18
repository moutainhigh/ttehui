/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.wxapay;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdom.JDOMException;

import com.alibaba.fastjson.JSONException;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.payment.wxapay.client.TenpayHttpClient;
import com.mocentre.tehui.payment.wxapay.util.XMLUtil;

/**
 * 类RefundRequestHandler.java的实现描述：退款
 *
 * @author sz.gong 2017年3月15日 上午10:43:02
 */
public class RefundRequestHandler extends RequestHandler {

    public RefundRequestHandler() {
        super();
    }

    /**
     * 创建签名MD5
     */
    public void createMD5Sign() {
        super.createMD5Sign();
    }

    /**
     * 小程序/微信公众号提交退款
     *
     * @return
     * @throws JSONException
     */
    public Map sendRefund(File certFile, String certPasswd) throws JSONException {
        Map result = new HashMap();
        StringBuffer sb = new StringBuffer("<xml>");
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">" + v + "</" + k + ">" + "\r\n");
            }
        }
        sb.append("</xml>");
        String params = sb.toString();

        String requestUrl = super.getGateUrl();
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:" + requestUrl);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setCertInfo(certFile, certPasswd);
        httpClient.setReqContent(requestUrl + "?" + params);
        String resContent = "";

        LoggerUtil.tehuiLog.info(this.getDebugInfo() + "\r\n" + "post data:" + params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);

        if (httpClient.call()) {
            resContent = httpClient.getResContent();
            this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:" + resContent);
            try {
                result = XMLUtil.doXMLParse(resContent);
            } catch (JDOMException | IOException e) {
                this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:" + resContent + "tryCatch");
                LoggerUtil.tehuiLog.info(this.getDebugInfo() + "\r\n" + "resContent:" + resContent + "tryCatch");
            }
        }
        LoggerUtil.tehuiLog.info("TenpayHttpClient->errInfo=" + httpClient.getErrInfo());
        return result;
    }

    //    /**
    //     * 微信公众号提交退款
    //     *
    //     * @return
    //     * @throws Exception
    //     */
    //    public Map doRefund(File certFile, String certPasswd) throws Exception {
    //        Map result = new HashMap();
    //        StringBuffer sb = new StringBuffer("<xml>");
    //        Set es = super.getAllParameters().entrySet();
    //        Iterator it = es.iterator();
    //        while (it.hasNext()) {
    //            Map.Entry entry = (Map.Entry) it.next();
    //            String k = (String) entry.getKey();
    //            String v = (String) entry.getValue();
    //            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
    //                sb.append("<" + k + ">" + v + "</" + k + ">" + "\r\n");
    //            }
    //        }
    //        sb.append("</xml>");
    //        String params = sb.toString();
    //        KeyStore keyStore = KeyStore.getInstance("PKCS12");
    //        FileInputStream instream = new FileInputStream(certFile);//P12文件目录
    //        try {
    //            keyStore.load(instream, certPasswd.toCharArray());//这里写密码MCHID
    //        } finally {
    //            instream.close();
    //        }
    //        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPasswd.toCharArray()).build();
    //
    //        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
    //                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    //        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
    //        try {
    //            HttpPost httpost = new HttpPost(this.getGateUrl()); // 设置响应头信息
    //            httpost.addHeader("Connection", "keep-alive");
    //            httpost.addHeader("Accept", "*/*");
    //            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    //            httpost.addHeader("Host", "api.mch.weixin.qq.com");
    //            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
    //            httpost.addHeader("Cache-Control", "max-age=0");
    //            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
    //            httpost.setEntity(new StringEntity(params, "UTF-8"));
    //            CloseableHttpResponse response = httpclient.execute(httpost);
    //            try {
    //                HttpEntity entity = response.getEntity();
    //                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
    //                EntityUtils.consume(entity);
    //                result = XMLUtil.doXMLParse(jsonStr);
    //            } finally {
    //                response.close();
    //            }
    //        } finally {
    //            httpclient.close();
    //        }
    //        return result;
    //    }

}
