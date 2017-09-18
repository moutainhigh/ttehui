package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类目 Created by 王雪莹 on 2016/11/7.
 */
public class Category extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 店铺id **/
    private Long              shopId;

    /** 名称 **/
    private String            name;

    /** 排序 **/
    private Integer           sorted;

    /** banner */
    private String            banner;

    /** 是否展示 */
    private String            isShow;

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

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
