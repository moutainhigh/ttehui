package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 频道表
 * Created by 王雪莹 on 2016/11/7.
 */
public class Channel extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /** 父id（默认0）**/
    private Long parentId;

    /** 店铺id **/
    private Long shopId;

    /** 名称 **/
    private String name;

    /** 展示的城市 **/
    private String beCity;

    /** 频道投放经度**/
    private String beLongitude;

    /** 频道投放纬度 **/
    private String beLatitude;

    /** 是否展示 **/
    private String isShow;

    /** 频道介绍 **/
    private String intro;

    /** 排序 **/
    private Integer sorted;

    /** banner */
    private String banner;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeCity() {
        return beCity;
    }

    public void setBeCity(String beCity) {
        this.beCity = beCity;
    }

    public String getBeLongitude() {
        return beLongitude;
    }

    public void setBeLongitude(String beLongitude) {
        this.beLongitude = beLongitude;
    }

    public String getBeLatitude() {
        return beLatitude;
    }

    public void setBeLatitude(String beLatitude) {
        this.beLatitude = beLatitude;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
