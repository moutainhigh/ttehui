/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.common.constant;

/**
 * 类SystemConstant.java的实现描述：配置文件常量
 * 
 * @author sz.gong 2016年5月18日 上午10:50:35
 */
public class ConfigConstant {

    /***
     * 微信小程序
     */
    public static String APP_ID                     = "app_id";
    public static String APP_KEY                    = "app_key";
    public static String MCH_ID                     = "mch_id";
    public static String NOTIFY_URL                 = "notify_url";
    public static String UNIFIEDORDER_URL           = "unifiedorder_url";
    public static String REFUND_URL                 = "refund_url";
    public static String P12_CERT_FILE              = "p12_cert_file";

    /***
     * 农行支付(特惠商城)
     */
    public static String ABC_NOTIFY_ASYNC_URL       = "abc_notify_async_url";
    public static String ABC_NOTIFY_SYNC_URL        = "abc_notify_sync_url";

    /***
     * 农行支付(农行客户端商城)
     */
    public static String ABC_MALL_NOTIFY_ASYNC_URL  = "abc_mall_notify_async_url";
    public static String ABC_MALL_NOTIFY_SYNC_URL   = "abc_mall_notify_sync_url";

    /**
     * 农行支付(第三方支付)
     */
    public static String ABC_THIRD_NOTIFY_ASYNC_URL = "abc_third_notify_async_url";

    /***
     * 微信公众号支付
     */
    public static String WX_PAY_URL                 = "wx_pay_url";
    public static String WX_NOTIFY_URL              = "wx_notify_url";
    public static String WX_MCH_ID                  = "wx_mch_id";
    public static String WX_KEY                     = "wx_key";
    public static String WX_APP_ID                  = "wx_app_id";
    public static String WX_REFUND_URL              = "wx_refund_url";
    public static String WX_P12_CERT_FILE           = "wx_p12_cert_file";

    /**
     * 第三方微信支付URL
     */
    public static String WX_THIRD_PAY_URL           = "wx_third_pay_url";
    public static String WX_THIRD_NOTIFY_URL        = "wx_third_notify_url";

    /**
     * 第三方支付成功URL
     */
    public static String WX_THIRD_PAY_SUC_URL       = "wx_third_pay_suc_url";

}
