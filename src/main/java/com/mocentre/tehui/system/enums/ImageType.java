/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.enums;

/**
 * 类ImageType.java的实现描述：图片类型枚举
 * 
 * @author sz.gong 2016年11月17日 上午11:51:43
 */
public enum ImageType {

    LOGO("logo", "logo图"),
    THUMB("thumbnail", "缩略图"),
    BANNER("banner", "banner图"),
    OTHER("other", "其他");

    private String code;
    private String name;

    private ImageType(String code, String name) {
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
        for (ImageType type : ImageType.values()) {
            if (type.code == code) {
                return type.name;
            }
        }
        return null;
    }

}
