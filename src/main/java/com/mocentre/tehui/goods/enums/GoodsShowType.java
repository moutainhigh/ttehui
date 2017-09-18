/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.goods.enums;

/**
 * 类GoodsShowType.java的实现描述：商品上架状态
 * 
 * @author sz.gong 2016年12月19日 上午11:18:35
 */
public enum GoodsShowType {

    NOTSHELF("notShelf", "未上架 "),
    ONSHELF("onShelf", "上架中 "),
    OFFSHELF("offShelf", "已下架");

    private String code;
    private String name;

    private GoodsShowType(String code, String name) {
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
        for (GoodsShowType type : GoodsShowType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
