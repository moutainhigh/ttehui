package com.mocentre.tehui.sub.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 专题模板实体类. Created by yukaiji on 2016/12/02.
 */
public class SubjectTemplet extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 模板名称 */
    private String            name;
    /** 唯一编码（区分是那个画面） */
    private String            code;
    /** 图片宽 */
    private String            imgWidth;
    /** 图片高） */
    private String            imgHeight;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(String imgWidth) {
        this.imgWidth = imgWidth;
    }

    public String getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(String imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
