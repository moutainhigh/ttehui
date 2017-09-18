/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.goods.enums;

/**
 * 类OrderStatus.java的实现描述：优惠券状态
 * 
 * @author sz.gong 2016年11月22日 下午3:09:44
 */
public enum CouponStatus {

    UNUSED("unused", "未使用 "),
    USED("used", "已使用");

    private String code;
    private String name;

    private CouponStatus(String code, String name) {
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
        for (CouponStatus status : CouponStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
