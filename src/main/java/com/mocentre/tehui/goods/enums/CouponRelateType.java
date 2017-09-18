/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.goods.enums;

/**
 * 类OrderStatus.java的实现描述：优惠券关联类型
 * 
 * @author sz.gong 2016年11月22日 下午3:09:44
 */
public enum CouponRelateType {

    NO("no", "全类商品使用 "),
    CATEGORY("category", "指定分类使用 "),
    SHOP("shop", "指定店铺使用"),
    GOODS("goods", "指定商品使用");

    private String code;
    private String name;

    private CouponRelateType(String code, String name) {
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
        for (CouponRelateType type : CouponRelateType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
