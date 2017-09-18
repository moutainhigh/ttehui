package com.mocentre.tehui.buy.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类OrderDetail.java的实现描述：订单详情
 * 
 * @author sz.gong 2016年11月15日 下午3:37:23
 */
public class OrderDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 订单编号 **/
    private String            orderNum;

    /** 订单详情编号 **/
    private String            orderDetailNum;

    /** 商品id **/
    private Long              goodsId;

    /** 商品名称 **/
    private String            goodsName;

    /** 商品缩略图 **/
    private String            goodsImg;

    /** 商品规格 **/
    private String            goodsStandard;

    /** 商品规格描述 **/
    private String            goodsStandardDes;

    /** 活动商品关联id **/
    private Long              goodsActGoodsId;

    /** 商品分类id **/
    private Long              goodsCategory;

    /** 商品原价 **/
    private Long              goodsPrice;

    /** 商品购买价格 **/
    private Long              goodsRealPrice;

    /** 商品购买数量 **/
    private Integer           goodsAmount;

    /** 申请退款时间 **/
    private Date              applyTime;

    /** 退货状态 **/
    private String            refundStatus;

    /** 退货时间 **/
    private Date              refundTime;

    /** 退货金额 **/
    private Long              refundMoney;

    /** 退款理由 **/
    private String            refundReason;

    /** 退款说明 **/
    private String            refundDes;

    /** 使用的优惠码 **/
    private String            couponSn;

    /** 使用优惠券金额 **/
    private Long              couponMoney;

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

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsStandard(String goodsStandard) {
        this.goodsStandard = goodsStandard;
    }

    public String getGoodsStandard() {
        return goodsStandard;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsRealPrice(Long goodsRealPrice) {
        this.goodsRealPrice = goodsRealPrice;
    }

    public Long getGoodsRealPrice() {
        return goodsRealPrice;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Long getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Long refundMoney) {
        this.refundMoney = refundMoney;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoodsStandardDes() {
        return goodsStandardDes;
    }

    public void setGoodsStandardDes(String goodsStandardDes) {
        this.goodsStandardDes = goodsStandardDes;
    }

    public Long getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(Long goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public Long getGoodsActGoodsId() {
        return goodsActGoodsId;
    }

    public void setGoodsActGoodsId(Long goodsActGoodsId) {
        this.goodsActGoodsId = goodsActGoodsId;
    }

    public String getOrderDetailNum() {
        return orderDetailNum;
    }

    public void setOrderDetailNum(String orderDetailNum) {
        this.orderDetailNum = orderDetailNum;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundDes() {
        return refundDes;
    }

    public void setRefundDes(String refundDes) {
        this.refundDes = refundDes;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

}
