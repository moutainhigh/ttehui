package com.mocentre.gift.buy.enums;

/**
 * 订单状态
 * Created by 王雪莹 on 2017/4/13.
 */
public enum GiftOrderStatus {
//    订单状态（commit:已提交  wait_check:等待审核 cancel:已取消  send:已发货  success:已完成）

    COMMIT("commit", "已提交"),
    WAIT_CHECK("wait_check", "等待审核"),
    CANCEL("cancel", "已取消"),
    SEND("send", "已发货"),
    SUCCESS("success", "已完成 ");

    private String code;
    private String name;

    private GiftOrderStatus(String code, String name) {
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
        for (GiftOrderStatus status : GiftOrderStatus.values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return null;
    }
}
