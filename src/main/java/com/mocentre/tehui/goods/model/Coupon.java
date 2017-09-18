package com.mocentre.tehui.goods.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 优惠券实体类 Created by yukaiji on 2016/11/24.
 */
public class Coupon extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 优惠券名称 */
    private String            couponName;
    /** 优惠券面额 */
    private Long              couponMoney;
    /** 是否限制数量（1.是 0.否） */
    private Integer           isLimit;
    /** 是否为外部领取（1.是 0.否） */
    private Integer           isOuter;
    /** 优惠券数量 */
    private Integer           couponNum;
    /** 优惠券起始编号 */
    private Long              firstNum;
    /** 店铺id */
    private Long              shopId;
    /** 关联类型（no:全类商品使用 goods:指定商品使用 category:指定分类使用 shop:指定店铺） **/
    private String            relateType;
    /** 商品id或分类id **/
    private String            typeIds;
    /** 优惠券/码 0:优惠券 1:优惠码 **/
    private Integer           couponType;
    /** 满多少可用 **/
    private Long              fullCut;
    /** 开始时间 */
    private Date              beginTime;
    /** 结束时间 */
    private Date              endTime;
    /** 优惠券sn */
    private String            couponSn;
    /** 优惠券说明 */
    private String            couponDes;
    /** 描述 */
    private String            description;
    /** 编码类型（1.随机 2.指定起始数字） **/
    private Integer           snType;

    public Integer getIsOuter() {
        return isOuter;
    }

    public void setIsOuter(Integer isOuter) {
        this.isOuter = isOuter;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Integer isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public Long getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(Long firstNum) {
        this.firstNum = firstNum;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public String getCouponDes() {
        return couponDes;
    }

    public void setCouponDes(String couponDes) {
        this.couponDes = couponDes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Long getFullCut() {
        return fullCut;
    }

    public void setFullCut(Long fullCut) {
        this.fullCut = fullCut;
    }

    public Long getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Long couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Integer getSnType() {
        return snType;
    }

    public void setSnType(Integer snType) {
        this.snType = snType;
    }

}
