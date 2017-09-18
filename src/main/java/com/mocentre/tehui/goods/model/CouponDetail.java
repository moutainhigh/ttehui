package com.mocentre.tehui.goods.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 优惠券详情实体类 Created by yukaiji on 2016/11/24.
 */
public class CouponDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 优惠券id */
    private Long              couponId;
    /** 优惠券编码 */
    private String            couponSn;
    /** 关联类型（no:全场通用 goods:指定商品 category:指定分类） **/
    private String            relateType;
    /** 商品id或分类id **/
    private String            typeIds;
    /** 满多少可用 **/
    private Long              fullCut;
    /** 优惠券状态(unused:未使用 used:已使用) */
    private String            status;
    /** 开始时间 **/
    private Date              beginTime;
    /** 结束时间 **/
    private Date              endTime;
    /** 优惠券说明 **/
    private String            couponDes;
    /** 优惠券面额 */
    private Long              couponMoney;
    /** 使用时间 */
    private Date              useTime;
    /** 用户id */
    private Long              customerId;
    /** 领取时间 */
    private Date              receiveTime;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getRelateType() {
        return relateType;
    }

    public void setRelateType(String relateType) {
        this.relateType = relateType;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }

    public Long getFullCut() {
        return fullCut;
    }

    public void setFullCut(Long fullCut) {
        this.fullCut = fullCut;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCouponDes() {
        return couponDes;
    }

    public void setCouponDes(String couponDes) {
        this.couponDes = couponDes;
    }

    public Long getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Long couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

}
