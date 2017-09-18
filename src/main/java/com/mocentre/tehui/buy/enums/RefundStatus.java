/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类RefundStatus.java的实现描述：退款状态
 * 
 * @author sz.gong 2016年11月22日 下午3:09:44
 */
public enum RefundStatus {

    SUCCESS("success", "付款成功"),
    FAIL("fail", "付款失败");

    private String code;
    private String name;

    private RefundStatus(String code, String name) {
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
        for (RefundStatus status : RefundStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
