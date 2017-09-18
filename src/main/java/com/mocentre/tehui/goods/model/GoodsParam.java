package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 商品参数 Created by 王雪莹 on 2016/11/24.
 */
public class GoodsParam extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    private Long              shopId;

    /**
     * 商品id
     */
    private Long              goodsId;

    /**
     * 参数key
     */
    private String            goodsKey;

    /**
     * 参数value
     */
    private String            goodsVal;

    /**
     * 是否置顶
     */
    private boolean           isTop;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsKey() {
        return goodsKey;
    }

    public void setGoodsKey(String goodsKey) {
        this.goodsKey = goodsKey;
    }

    public String getGoodsVal() {
        return goodsVal;
    }

    public void setGoodsVal(String goodsVal) {
        this.goodsVal = goodsVal;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }
}
