/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.gd.enums;

/**
 * 礼品审核状态.
 *
 * Created by yukaiji on 2017/4/6.
 */
public enum GoodsCheckedType {

    UNCHECK("unCheck", "未审核"),
    CHECKPASS("checkPass", "审核通过"),
    CHECKFAIL("checkFail", "审核驳回");

    private String code;
    private String name;

    private GoodsCheckedType(String code, String name) {
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
        for (GoodsCheckedType type : GoodsCheckedType.values()) {
            if (type.code == code) {
                return type.name;
            }
        }
        return null;
    }

}
