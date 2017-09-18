package com.mocentre.tehui.payment.wxapay;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdom.JDOMException;
import org.json.JSONException;

import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.payment.wxapay.client.TenpayHttpClient;
import com.mocentre.tehui.payment.wxapay.util.Sha1Util;
import com.mocentre.tehui.payment.wxapay.util.XMLUtil;

public class PrepayIdRequestHandler extends RequestHandler {

    public PrepayIdRequestHandler() {
        super();
    }

    /**
     * 创建签名SHA1
     * 
     * @param signParams
     * @return
     * @throws Exception
     */
    public String createSHA1Sign() {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        String appsign = Sha1Util.getSha1(params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;
    }

    /**
     * 创建签名MD5
     */
    public void createMD5Sign() {
        super.createMD5Sign();
    }

    /**
     * 提交预支付
     * 
     * @return prepayid
     * @throws JSONException
     */
    public String sendPrepay() throws JSONException {
        String prepayid = "";
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
        httpClient.setReqContent(requestUrl);
        String resContent = "";

        LoggerUtil.tehuiLog.info(this.getDebugInfo() + "\r\n" + "post data:" + params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);

        if (httpClient.callHttpPost(requestUrl, params)) {
            resContent = httpClient.getResContent();
            this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:" + resContent);
            try {
                Map result = XMLUtil.doXMLParse(resContent);
                String returnCode = (String) result.get("return_code");
                String returnMsg = (String) result.get("return_msg");
                if ("SUCCESS".equals(returnCode)) {
                    String resultCode = (String) result.get("result_code");
                    String errCodeDes = (String) result.get("err_code_des");
                    if ("SUCCESS".equals(resultCode)) {
                        //String tradeType = (String) result.get("trade_type");
                        prepayid = (String) result.get("prepay_id");
                    } else {
                        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "error_msg:" + errCodeDes);
                        LoggerUtil.tehuiLog.info(this.getDebugInfo() + "\r\n" + "error_msg:" + errCodeDes);
                    }
                } else {
                    this.setDebugInfo(this.getDebugInfo() + "\r\n" + "return_msg:" + returnMsg);
                    LoggerUtil.tehuiLog.info(this.getDebugInfo() + "\r\n" + "return_msg:" + returnMsg);
                }
            } catch (JDOMException | IOException e) {
                this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:" + resContent + "tryCatch");
                LoggerUtil.tehuiLog.info(this.getDebugInfo() + "\r\n" + "resContent:" + resContent + "tryCatch");
            }
        }
        return prepayid;
    }

}
