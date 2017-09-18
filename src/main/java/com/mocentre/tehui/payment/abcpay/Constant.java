/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.payment.abcpay;

/**
 * 类Constant.java的实现描述：abc常量
 * 
 * @author sz.gong 2016年12月22日 上午10:00:50
 */
public class Constant {

    //交易类型
    public static String PAY_TYPE_ID_IMMEDIATEPAY       = "ImmediatePay"; //直接支付
    public static String PAY_TYPE_ID_PREAUTHPAY         = "PreAuthPay";  //预授权支付
    public static String PAY_TYPE_ID_DIVIDEDPAY         = "DividedPay";  //分期支付

    //分期标识
    public static String INSTALL_MENT_MARK_YES          = "1";           //分期
    public static String INSTALL_MENT_MARK_NO           = "0";           //不分期

    //商品种类
    public static String COMMODITY_TYPE_CZL_ZFZHCZ      = "0101";        //支付账户充值
    public static String COMMODITY_TYPE_XFL_XNL         = "0201";        //虚拟类
    public static String COMMODITY_TYPE_XFL_CTL         = "0202";        //传统类
    public static String COMMODITY_TYPE_XFL_SML         = "0203";        //实名类
    public static String COMMODITY_TYPE_ZZL_BHZZ        = "0301";        //本行转账
    public static String COMMODITY_TYPE_ZZL_THZZ        = "0302";        //他行转账
    public static String COMMODITY_TYPE_JFL_SF          = "0401";        //水费
    public static String COMMODITY_TYPE_JFL_DF          = "0402";        //电费
    public static String COMMODITY_TYPE_JFL_MQF         = "0403";        //煤气费
    public static String COMMODITY_TYPE_YXDSF           = "0404";        //有线电视费
    public static String COMMODITY_TYPE_JFL_TXF         = "0405";        //通讯费
    public static String COMMODITY_TYPE_JFL_WYF         = "0406";        //物业费
    public static String COMMODITY_TYPE_JFL_BXF         = "0407";        //保险费
    public static String COMMODITY_TYPE_JFL_XZF         = "0408";        //行政费用
    public static String COMMODITY_TYPE_JFL_SUF         = "0409";        //税费
    public static String COMMODITY_TYPE_JFL_XF          = "0410";        //学费
    public static String COMMODITY_TYPE_JFL_QT          = "0499";        //其他
    public static String COMMODITY_TYPE_LCL_JJ          = "0501";        //基金
    public static String COMMODITY_TYPE_LCL_LCCP        = "0502";        //理财产品
    public static String COMMODITY_TYPE_LCL_QT          = "0599";        //其他

    //支付类型
    public static String PAYMENT_TYPE_NHKZF             = "1";           //农行卡支付
    public static String PAYMENT_TYPE_GJKZF             = "2";           //国际卡支付
    public static String PAYMENT_TYPE_NHHJKZJ           = "3";           //农行货记卡支付
    public static String PAYMENT_TYPE_JYDSFDKHZF        = "5";           //基于第三方的跨行支付
    public static String PAYMENT_TYPE_YLKHZF            = "6";           //银联跨行支付
    public static String PAYMENT_TYPE_DGH               = "7";           //对公户
    public static String PAYMENT_TYPE_ZFFSHB            = "A";           //支付方式合并

    //交易渠道
    public static String PAYMENT_LINK_TYPE_INTERNETWLJR = "1";           //internet网络接入
    public static String PAYMENT_LINK_TYPE_SJWLJR       = "2";           //手机网络接入
    public static String PAYMENT_LINK_TYPE_SZDSWLJR     = "3";           //数字电视网络接入
    public static String PAYMENT_LINK_TYPE_ZNKHD        = "4";           //智能客户端

    //银联跨行移动支付接入方式
    public static String UNION_PAY_LINK_TYPE_YMJR       = "0";           //页面接入
    public static String UNION_PAY_LINK_TYPE_KHDJR      = "1";           //客户端接入

    //通知方式
    public static String NOTIFY_TYPE_SYNC               = "0";           //页面通知
    public static String NOTIFY_TYPE_ASYNC              = "1";           //服务器通知

    //交易是否分账
    public static String IS_BREAK_ACCOUNT_NO            = "0";           //否
    public static String IS_BREAK_ACCOUNT_YES           = "1";           //是
}
