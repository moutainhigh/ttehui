package com.mocentre.tehui.td.model;

import com.mocentre.tehui.core.model.BaseEntity;

public class ThirdGoodsAreas extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 商品id **/
    private Long   goodsId;
    /** 投放城市code **/
    private String cityCode;
    /** 投放城市名称 **/
    private String cityName;
    /** 经度 **/
    private Double longitude;
    /** 纬度 **/
    private Double latitude;
    /** 百度城市中心码 **/
    private String bdCityCode;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getBdCityCode() {
        return bdCityCode;
    }

    public void setBdCityCode(String bdCityCode) {
        this.bdCityCode = bdCityCode;
    }
}
