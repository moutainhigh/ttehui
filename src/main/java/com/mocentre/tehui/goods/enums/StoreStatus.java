package com.mocentre.tehui.goods.enums;

/**
 * 商品收藏操作状态. Created by yukaiji on 2017/3/24.
 */
public enum StoreStatus {

    ADD("add", "添加收藏"),
    DELETE("delete", "删除收藏");

    private String code;
    private String name;

    private StoreStatus(String code, String name) {
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
        for (StoreStatus status : StoreStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }
}
