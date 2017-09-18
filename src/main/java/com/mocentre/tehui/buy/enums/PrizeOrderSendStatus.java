/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类PrizeOrderSendStatus.java的实现描述：礼品发货类型
 * 
 * @author sz.gong 2017年8月14日 下午1:51:25
 */
public enum PrizeOrderSendStatus {

    WAIT_SEND("wait_send", "待发货"),
    HAVE_SEND("have_send", "已发货");

    private String code;
    private String name;

    private PrizeOrderSendStatus(String code, String name) {
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
        for (PrizeOrderSendStatus status : PrizeOrderSendStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
