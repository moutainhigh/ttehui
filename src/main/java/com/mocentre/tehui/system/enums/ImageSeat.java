/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.enums;

/**
 * 类ImageSeat.java的实现描述：图片位置枚举
 *
 * @author sz.gong 2016年11月17日 上午11:56:53
 */
public enum ImageSeat {

    GOODS("goods", "商品 "),
    DISCOVER("discover", "发现页"),
    ACTIVITY("activity", "活动"),
    MALLHOME("mallHome", "农行掌银客户端首页");

    private String code;
    private String name;

    private ImageSeat(String code, String name) {
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
        for (ImageSeat type : ImageSeat.values()) {
            if (type.code == code) {
                return type.name;
            }
        }
        return null;
    }

}
