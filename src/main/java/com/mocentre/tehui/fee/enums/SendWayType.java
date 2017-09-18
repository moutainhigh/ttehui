/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.fee.enums;


/**
 * 物流运送方式类型
 * 
 * @author Created by yukaiji on 2017年1月12日
 */
public enum SendWayType {

	EXPRESS("express", "快递 "),
    EMS("ems", "EMS"),
    MAIL("mail", "平邮");

    private String code;
    private String name;

    private SendWayType(String code, String name) {
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
        for (SendWayType type : SendWayType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
