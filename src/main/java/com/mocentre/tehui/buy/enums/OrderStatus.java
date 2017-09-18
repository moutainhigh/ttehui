/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类OrderStatus.java的实现描述：订单状态
 * 
 * @author sz.gong 2016年11月22日 下午3:09:44
 */
public enum OrderStatus {

    WAIT_PAY("wait_pay", "等待买家付款"),
    WAIT_SEND("wait_send", "等待卖家发货"),
    WAIT_ACCEPT("wait_accept", "等待确认收货"),
    DEAL_SUC("deal_suc", "交易成功"),
    DEAL_CLOSE("deal_close", "交易关闭 ");

    private String code;
    private String name;

    private OrderStatus(String code, String name) {
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
        for (OrderStatus status : OrderStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
