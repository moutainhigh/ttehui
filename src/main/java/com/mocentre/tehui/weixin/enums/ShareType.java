package com.mocentre.tehui.weixin.enums;

/**
 * 微信分享配置类型
 * Created by yukaiji on 2017/5/4.
 */
public enum ShareType {

    MUSIC("music", "音乐"),
    VIDEO("video", "视频"),
    LINK("link", "链接");

    private String code;
    private String name;

    private ShareType(String code, String name) {
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
        for (ShareType type : ShareType.values()) {
            if (type.code == code) {
                return type.name;
            }
        }
        return null;
    }

}
