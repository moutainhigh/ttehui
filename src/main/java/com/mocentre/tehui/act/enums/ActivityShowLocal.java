/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.act.enums;

/**
 * 类ActivityShowLocal.java的实现描述：活动展示位置枚举
 * 
 * @author sz.gong 2017年5月17日 下午4:33:50
 */
public enum ActivityShowLocal {

    MALL("mall", "商城"),
    ABC("abc", "农行客户端");

    private String code;
    private String name;

    private ActivityShowLocal(String code, String name) {
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
        for (ActivityShowLocal type : ActivityShowLocal.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
