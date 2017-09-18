package com.mocentre.tehui.system.model;

import com.mocentre.tehui.core.model.BaseEntity;

import java.util.List;

/**
 * 类Areas.java的实现描述：地区实体类
 * 
 * @author sz.gong 2016年11月17日 上午11:19:03
 */
public class Areas extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 编码(不可重复) **/
    private String            code;

    /** 名称 **/
    private String            name;

    /** 父编码 **/
    private String            pCode;

    /** 类型(province:省 city:市 area:区) **/
    private String            type;

    /** 百度城市码 **/
    private String            bdCityCode;

    /** 经度 **/
    private Double            longitude;

    /** 纬度 **/
    private Double            latitude;

    /** 子区域 **/
    private List<Areas>       childrenList;

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

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBdCityCode() {
        return bdCityCode;
    }

    public void setBdCityCode(String bdCityCode) {
        this.bdCityCode = bdCityCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<Areas> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Areas> childrenList) {
        this.childrenList = childrenList;
    }

}
