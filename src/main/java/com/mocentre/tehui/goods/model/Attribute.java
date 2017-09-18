package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 商品属性表 Created by 王雪莹 on 2016/11/4.
 */
public class Attribute extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 属性名称 */
    private String            name;
    /** 属性编码 */
    private String            code;
    /** 是否是图片 */
    private String            isImg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsImg() {
        return isImg;
    }

    public void setIsImg(String isImg) {
        this.isImg = isImg;
    }
}
