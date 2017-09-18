package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 投放区域表 Created by 王雪莹 on 2016/11/16.
 */
public class GoodsLaunch extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 8207616313687258212L;

    /**
     * 商品id
     */
    private Long              goodsId;

    /**
     * 投放省份
     */
    private String            province;

    /**
     * 投放城市
     */
    private String            city;

    /**
     * 投放区、县
     */
    private String            area;

    /**
     * 投放经度
     */
    private Double            longitude;

    /**
     * 投放纬度
     */
    private Double            latitude;

    /**
     * 投放区域编码
     */
    private String            areasCode;

    /**
     * 投放区域名称
     */
    private String            areasName;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getAreasCode() {
        return areasCode;
    }

    public void setAreasCode(String areasCode) {
        this.areasCode = areasCode;
    }

    public String getAreasName() {
        return areasName;
    }

    public void setAreasName(String areasName) {
        this.areasName = areasName;
    }
}
