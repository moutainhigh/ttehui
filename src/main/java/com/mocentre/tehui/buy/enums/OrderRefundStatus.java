/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类OrderRefundStatus.java的实现描述：订单退货状态
 * 
 * @author sz.gong 2017年3月15日 下午4:27:15
 */
public enum OrderRefundStatus {

    UNREFUND("unrefund", "无需退款"),
    REFUNDING("refunding", "退款中"),
    REFUNDED("refunded", "已退款");

    private String code;
    private String name;

    private OrderRefundStatus(String code, String name) {
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
        for (OrderRefundStatus status : OrderRefundStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
