package com.mocentre.gift.buy.enums;

/**
 * 发票状态
 * Created by 王雪莹 on 2017/4/17.
 */
public enum GiftOrderBillStatus {
   // 发票状态（have_open：已开发票 no_open：未开发票）

    HAVEOPEN("have_open", "已开发票"),
    NOOPEN("no_open", "未开发票");

    private String code;
    private String name;

    private GiftOrderBillStatus(String code, String name) {
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
        for (GiftOrderBillStatus status : GiftOrderBillStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }
}
