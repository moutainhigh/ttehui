/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.shop.enums;

/**
 * 类ShopStatus.java的实现描述：店铺审核状态
 * 
 * @author sz.gong 2016年11月18日 下午4:35:22
 */
public enum ShopAuditStatus {

    WAIT("wait", "待审核"),
    PASS("pass", "审核通过"),
    UNPASS("unpass", "审核驳回");

    private String code;
    private String name;

    private ShopAuditStatus(String code, String name) {
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
        for (ShopAuditStatus status : ShopAuditStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
