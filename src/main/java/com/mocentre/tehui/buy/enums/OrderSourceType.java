/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类OrderFromType.java的实现描述：订单来源
 * 
 * @author sz.gong 2017年5月18日 下午5:54:34
 */
public enum OrderSourceType {

    TEHUI("tehui", "特惠商城"),
    ABC("abc", "农行客户端");

    private String code;
    private String name;

    private OrderSourceType(String code, String name) {
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
        for (OrderSourceType type : OrderSourceType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
