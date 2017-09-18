/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.ps.enums;

/**
 * @author Created by yukaiji on 2017年1月20日
 */
public enum CustomerStatus {

    ADD("add", "添加"),
    EDIT("edit", "修改"),
    EDITDEFAULT("editDefault", "修改默认地址");

    private String code;
    private String name;

    private CustomerStatus(String code, String name) {
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
        for (CustomerStatus status : CustomerStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }

}
