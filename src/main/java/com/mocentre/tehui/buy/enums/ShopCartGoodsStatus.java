/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.enums;

/**
 * 类ShopCartGoodsStatus.java的实现描述：购物车商品状态
 * 
 * @author sz.gong 2016年11月22日 下午3:09:44
 */
public enum ShopCartGoodsStatus {

    OFFSHELF("offshelf", "已下架"),
    SOLDOUT("soldout", "已售罄"),
    LOSE("lose", "已失效"),
    NORMAL("normal", "正常");

    private String code;
    private String name;

    private ShopCartGoodsStatus(String code, String name) {
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
        for (ShopCartGoodsStatus status : ShopCartGoodsStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
