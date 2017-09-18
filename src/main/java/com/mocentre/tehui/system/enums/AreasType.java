/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.enums;

/**
 * 类AreasType.java的实现描述：地区类型枚举
 * 
 * @author sz.gong 2016年11月17日 上午11:56:53
 */
public enum AreasType {

    COUNTRY("country", "国家"),
    PROVINCE("province", "省"),
    CITY("city", "市"),
    AREA("area", "区");

    private String code;
    private String name;

    private AreasType(String code, String name) {
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
        for (AreasType type : AreasType.values()) {
            if (type.code == code) {
                return type.name;
            }
        }
        return null;
    }

}
