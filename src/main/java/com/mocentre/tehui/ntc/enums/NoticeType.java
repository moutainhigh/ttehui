package com.mocentre.tehui.ntc.enums;

/**
 * 通知类型类
 * Created by wangxueying on 2017/8/9.
 */
public enum NoticeType {

    REFUND("refund", "退款通知"),
    BUY("buy", "下单通知");

    private String code;
    private String name;

    private NoticeType(String code, String name) {
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
        for (NoticeType type : NoticeType.values()) {
            if (type.code.equals(code)) {
                return type.name;
            }
        }
        return null;
    }
}
