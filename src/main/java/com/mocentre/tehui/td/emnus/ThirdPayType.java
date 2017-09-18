/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.emnus;

/**
 * 类ThirdPayType.java的实现描述：付款方式
 * 
 * @author sz.gong 2017年6月21日 上午11:16:40
 */
public enum ThirdPayType {

    ALIPAY("abc_alipay", "支付宝支付 "),
    WXPAY("abc_wxpay", "微信支付"),
    ABCPAY("abc_pay", "农行客户端支付"),
    KMPAY("abc_kmpay", "农行掌银支付");

    private String code;
    private String name;

    private ThirdPayType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCodeValue() {
        return this.code;
    }

    public String getNameValue() {
        return this.name;
    }

    public static String getName(String code) {
        for (ThirdPayType type : ThirdPayType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
