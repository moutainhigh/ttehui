package com.mocentre.gift.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 购物单
 * Created by 王雪莹 on 2017/4/6.
 */
public class GiftGiftSheet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id **/
    private Long              customerId;

    /** 商品id **/
    private Long              goodsId;

    /** 商品名称 **/
    private String            goodsName;

    /** 商品图片**/
    private String            goodsImg;

    /** 商品单价 **/
    private Long              price;

    /** 购买数量 **/
    private Integer           num;

    /** 最低购买量 **/
    private Integer           limitNum;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }
}
