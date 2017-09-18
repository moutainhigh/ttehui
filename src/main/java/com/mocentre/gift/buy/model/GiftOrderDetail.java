package com.mocentre.gift.buy.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 订单详情
 * Created by 王雪莹 on 2017/4/6.
 */
public class GiftOrderDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 订单详情编号 **/
    private String            orderDetailNum;

    /** 订单编号 **/
    private String            orderNum;

    /** 订单id **/
    private Long              orderId;

    /** 商品id **/
    private Long              goodsId;

    /** 商品名称 **/
    private String            goodsName;

    /** 商品缩略图 **/
    private String            goodsImg;

    /** 商品原价 **/
    private Long              goodsPrice;

    /** 商品购买数量 **/
    private Integer           goodsAmount;

    public String getOrderDetailNum() {
        return orderDetailNum;
    }

    public void setOrderDetailNum(String orderDetailNum) {
        this.orderDetailNum = orderDetailNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }
}
