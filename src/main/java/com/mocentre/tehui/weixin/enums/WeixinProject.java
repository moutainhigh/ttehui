package com.mocentre.tehui.weixin.enums;

/**
 * 微信项目code
 * Created by 王雪莹 on 2017/1/12.
 */
public enum WeixinProject {
    TTHUI("tthui", "天天特惠小程序版商城 ");

    private String projectCode;
    private String projectName;

    WeixinProject(String projectCode, String projectName) {
        this.projectCode = projectCode;
        this.projectName = projectName;
    }
    public String getProjectCodeValue() {
        return this.projectCode;
    }

    public String getProjectNameValue() {
        return this.projectName;
    }

    public static String getName(String code) {
        for (WeixinProject type : WeixinProject.values()) {
            if (type.projectCode == code) {
                return type.projectName;
            }
        }
        return null;
    }

}
