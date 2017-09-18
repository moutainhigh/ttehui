/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.act.enums;

/**
 * 类GroupStatus.java的实现描述：团购状态
 * 
 * @author sz.gong 2017年3月16日 下午1:59:45
 */
public enum GroupStatus {

    UNGROUP("ungroup", "未成团"),
    GROUPED("grouped:", "已成团"),
    GROUPING("grouping:", "成团中");

    private String code;
    private String name;

    private GroupStatus(String code, String name) {
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
        for (GroupStatus type : GroupStatus.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }

}
