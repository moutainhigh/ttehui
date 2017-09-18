/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类RefundReason.java的实现描述：退款理由
 * 
 * @author sz.gong 2016年11月22日 下午3:09:44
 */
public enum RefundReason {

    ONE("one", "商品质量问题"),
    TWO("two", "商品发错"),
    THREE("three", "商品漏发"),
    FOUR("four", "商品与页面描述不符"),
    FIVE("five", "收到商品破损"),
    SIX("six", "个人原因退货"),
    SEVEN("seven", "未收到货，不想要了"),
    EIGHT("eight", "我已拒收");

    private String code;
    private String name;

    private RefundReason(String code, String name) {
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
        for (RefundReason status : RefundReason.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
