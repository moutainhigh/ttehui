package com.mocentre.tehui.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类ShoppingCart.java的实现描述：购物车
 * 
 * @author sz.gong 2016年11月23日 下午3:09:33
 */
public class ShoppingCart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id **/
    private Long              customerId;

    /** 店铺id **/
    private Long              shopId;

    /** 店铺名称 **/
    private String            shopName;

    /** 商品id **/
    private Long              goodsId;

    /** 商品名称 **/
    private String            goodsName;

    /** 商品logo地址 **/
    private String            showLogo;

    /** 商品原价 **/
    private Long              oldPrice;

    /** 商品售价 **/
    private Long              sellPrice;

    /** 购买数量 **/
    private Integer           num;

    /** 商品规格(指定格式) **/
    private String            goodsSku;

    /** 商品规格描述 **/
    private String            goodsSkuDes;

    /** 活动商品id **/
    private Long              actGoodsId;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setShowLogo(String showLogo) {
        this.showLogo = showLogo;
    }

    public String getShowLogo() {
        return showLogo;
    }

    public void setOldPrice(Long oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Long getOldPrice() {
        return oldPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public String getGoodsSku() {
        return goodsSku;
    }

    public String getGoodsSkuDes() {
        return goodsSkuDes;
    }

    public void setGoodsSkuDes(String goodsSkuDes) {
        this.goodsSkuDes = goodsSkuDes;
    }

    public Long getActGoodsId() {
        return actGoodsId;
    }

    public void setActGoodsId(Long actGoodsId) {
        this.actGoodsId = actGoodsId;
    }

}
