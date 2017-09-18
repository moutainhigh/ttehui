/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类OrderType.java的实现描述：订单类型
 * 
 * @author sz.gong 2017年1月22日 下午5:52:59
 */
public enum OrderType {

    SECKILL("seckill", "秒杀订单"),
    GROUPON("groupon", "团购订单"),
    OTHER("other", "其他订单");

    private String code;
    private String name;

    private OrderType(String code, String name) {
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
        for (OrderType status : OrderType.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
