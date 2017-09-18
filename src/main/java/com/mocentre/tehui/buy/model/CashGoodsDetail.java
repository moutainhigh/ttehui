/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.model;

import java.io.Serializable;

/**
 * 类CashGoodsDetail.java的实现描述：收银台商品对象
 * 
 * @author sz.gong 2016年12月19日 上午11:45:19
 */
public class CashGoodsDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8291676794376696075L;

    /** 商品id **/
    private Long              goodsId;

    /** 店铺id **/
    private Long              shopId;

    /** 商品购买数量 **/
    private Integer           buyNum;

    /** 商品规格编码 **/
    private String            goodsStandard;

    /** 商品规格编码描述 **/
    private String            goodsStandardDes;

    /** 活动商品关联id **/
    private Long              goodsActGoodsId;

    /** 商品购买价格 **/
    private Long              sellPrice;

    /** 商品原价 **/
    private Long              oldPrice;

    /** 商品缩略图 **/
    private String            goodsImg;

    /** 商品名称 **/
    private String            goodsName;

    /** 商品类型 **/
    private Long              goodsCategory;

    /** 优惠券编码 **/
    private String            couponSn;

    /** 优惠金额 **/
    private Long              couponMoney;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Long getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Long oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Long getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(Long goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public Long getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Long couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsStandard() {
        return goodsStandard;
    }

    public void setGoodsStandard(String goodsStandard) {
        this.goodsStandard = goodsStandard;
    }

    public String getGoodsStandardDes() {
        return goodsStandardDes;
    }

    public void setGoodsStandardDes(String goodsStandardDes) {
        this.goodsStandardDes = goodsStandardDes;
    }

    public Long getGoodsActGoodsId() {
        return goodsActGoodsId;
    }

    public void setGoodsActGoodsId(Long goodsActGoodsId) {
        this.goodsActGoodsId = goodsActGoodsId;
    }

}
