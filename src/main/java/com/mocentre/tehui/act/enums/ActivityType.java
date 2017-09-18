/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.act.enums;

/**
 * 类ActivityType.java的实现描述：活动类型枚举
 * 
 * @author sz.gong 2017年1月20日 上午10:52:31
 */
public enum ActivityType {

    SECKILL("seckill", "秒杀"),
    GROUPON("groupon", "团购");

    private String code;
    private String name;

    private ActivityType(String code, String name) {
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
        for (ActivityType type : ActivityType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
