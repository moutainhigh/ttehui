package com.mocentre.tehui.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类Logistics.java的实现描述：物流
 * 
 * @author sz.gong 2017年3月22日 上午11:58:24
 */
public class Logistics extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 编码 **/
    private String            code;

    /** 名称 **/
    private String            name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
