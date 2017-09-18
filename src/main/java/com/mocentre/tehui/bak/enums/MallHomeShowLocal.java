/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.bak.enums;

/**
 * 类MallHomeShowLocal.java的实现描述：显示位置
 * 
 * @author sz.gong 2017年5月19日 下午3:31:23
 */
public enum MallHomeShowLocal {

    ACT("act", "精选活动"),
    SPECIAL("special", "爆品特卖");

    private String code;
    private String name;

    private MallHomeShowLocal(String code, String name) {
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
        for (MallHomeShowLocal type : MallHomeShowLocal.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
