package com.mocentre.tehui.payment.wxapay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.payment.wxapay.util.MD5Util;

/**
 * 请求处理类
 * <p>
 * 请求处理类继承此类，重写createSign方法即可。
 */
public class RequestHandler {

    /** 网关url地址 */
    private String    gateUrl;

    /** 密钥 */
    private String    key;

    /** 请求的参数 */
    private SortedMap parameters;

    /** debug信息 */
    private String    debugInfo;

    /**
     * 构造函数
     */
    public RequestHandler() {
        this.gateUrl = "";
        this.parameters = new TreeMap();
        this.key = "";
        this.debugInfo = "";
    }

    /**
     * 初始化函数。
     */
    public void init() {
        //nothing to do
    }

    /**
     * 获取参数值
     * 
     * @param parameter 参数名称
     * @return String
     */
    public String getParameter(String parameter) {
        String s = (String) this.parameters.get(parameter);
        return (null == s) ? "" : s;
    }

    /**
     * 设置参数值
     * 
     * @param parameter 参数名称
     * @param parameterValue 参数值
     */
    public void setParameter(String parameter, String parameterValue) {
        String v = "";
        if (null != parameterValue) {
            v = parameterValue.trim();
        }
        this.parameters.put(parameter, v);
    }

    /**
     * 返回所有的参数
     * 
     * @return SortedMap
     */
    public SortedMap getAllParameters() {
        return this.parameters;
    }

    /**
     * 获取debug信息
     */
    public String getDebugInfo() {
        return debugInfo;
    }

    /**
     * 获取带参数的请求URL
     * 
     * @return String
     * @throws UnsupportedEncodingException
     */
    public String getRequestURL() throws UnsupportedEncodingException {
        this.createMD5Sign();
        StringBuffer sb = new StringBuffer();
        //TenpayUtil.getCharacterEncoding(null, null);
        String enc = "UTF-8";
        Set es = this.parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();

            if (!"spbill_create_ip".equals(k)) {
                sb.append(k + "=" + URLEncoder.encode(v, enc) + "&");
            } else {
                sb.append(k + "=" + v.replace("\\.", "%2E") + "&");
            }
        }

        //去掉最后一个&
        String reqPars = sb.substring(0, sb.lastIndexOf("&"));
        return this.getGateUrl() + "?" + reqPars;
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public String getMD5Sign() {
        StringBuffer sb = new StringBuffer();
        Set es = this.parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + this.getKey());

        String enc = "UTF-8";//TenpayUtil.getCharacterEncoding(null, null);
        String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();

        //debug信息
        this.setDebugInfo(sb.toString() + " => sign:" + sign);
        LoggerUtil.tehuiLog.info(sb.toString() + " => sign:" + sign);

        return sign;
    }

    /**
     * 创建md5摘要，放入参数中。
     */
    protected void createMD5Sign() {
        String sign = getMD5Sign();
        this.setParameter("sign", sign);
    }

    /**
     * 设置debug信息
     */
    protected void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    /**
     * 获取入口地址,不包含参数值
     */
    public String getGateUrl() {
        return gateUrl;
    }

    /**
     * 设置入口地址,不包含参数值
     */
    public void setGateUrl(String gateUrl) {
        this.gateUrl = gateUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
