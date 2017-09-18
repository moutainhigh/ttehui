/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.bak.enums;

/**
 * 农行掌银关联商品类型
 *
 * Created by yukaiji on 2017/5/17.
 */
public enum MallHomeGoodsType {

    SECKILL("seckill", "秒杀"),
    GROUPON("groupon", "团购"),
    COMMON("common", "普通");

    private String code;
    private String name;

    private MallHomeGoodsType(String code, String name) {
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
        for (MallHomeGoodsType type : MallHomeGoodsType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
