package com.mocentre.tehui.td.model;

import com.mocentre.tehui.core.model.BaseEntity;

public class ThirdGoods extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 商品名称 **/
    private String title;
    /** 原价区间 **/
    private String oldPrice;
    /** 售价区间 **/
    private String sellPrice;
    /** 购物车展示图片 **/
    private String showImg;
    /** 商品描述 **/
    private String describe;
    /** 跳转链接 **/
    private String linkUrl;
    /** 显示位置（act:活动精选 goods:爆品特卖） **/
    private String showLocal;
    /** 排序 **/
    private String sorting;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getShowLocal() {
        return showLocal;
    }

    public void setShowLocal(String showLocal) {
        this.showLocal = showLocal;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }
}
