package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 导航栏接口 Created by 王雪莹 on 2016/12/20.
 */
public class Navigate extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 6583946329329673460L;

    /**
     * 店铺id
     */
    private Long              shopId;
    /**
     * 导航名称
     */
    private String            title;

    /**
     * 导航icon
     */
    private String            showImg;

    /**
     * 导航排序
     */
    private Integer           sorting;

    /**
     * 导航的类型（活动或者专题）
     */
    private String            type;

    /**
     * 关联的活动或者专题的id
     */
    private Long              typeId;

    /** 是否展示 **/
    private Integer           isShow;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

}
