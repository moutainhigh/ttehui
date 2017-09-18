package com.mocentre.gift.gd.model;

import com.mocentre.tehui.core.model.BaseEntity;

import java.math.BigDecimal;

/**
 * 礼品平台礼品实体类.
 *
 * Created by yukaiji on 2017/4/6.
 */
public class GiftGoods extends BaseEntity{

    private static final long   serialVersionUID = 1L;
    /** 分类id */
    private Long                categoryId;
    /** 价格 */
    private BigDecimal          price;
    /** 商品名称 */
    private String              title;
    /** 是否限制购买 */
    private Integer             isLimitBuy;
    /** 限制购买数量 */
    private Integer             limitNums;
    /** 是否通过审核（待审核:uncheck，已通过:checkpass，未通过:checkfail） */
    private String              isChecked;
    /** 上下架状态（待上架:notshelf，已上架:onshelf，已下架:offshelf） */
    private String              isShow;
    /** 商品描述 */
    private String              describe;
    /** 发货方式(直充:dr,物流快递:exp电子兑换码:eec) */
    private String              deliveryType;
    /** 排序 */
    private Integer             sorting;
    /** 列表页展示图片PC */
    private String              imgListPagePc;
    /** 列表页展示图片移动 */
    private String              imgListPageMobile;
    /** 详细页顶部banner */
    private String              imgBanner;
    /** 图文详细页 */
    private String              details;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsLimitBuy() {
        return isLimitBuy;
    }

    public void setIsLimitBuy(Integer isLimitBuy) {
        this.isLimitBuy = isLimitBuy;
    }

    public Integer getLimitNums() {
        return limitNums;
    }

    public void setLimitNums(Integer limitNums) {
        this.limitNums = limitNums;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public String getImgListPagePc() {
        return imgListPagePc;
    }

    public void setImgListPagePc(String imgListPagePc) {
        this.imgListPagePc = imgListPagePc;
    }

    public String getImgListPageMobile() {
        return imgListPageMobile;
    }

    public void setImgListPageMobile(String imgListPageMobile) {
        this.imgListPageMobile = imgListPageMobile;
    }

    public String getImgBanner() {
        return imgBanner;
    }

    public void setImgBanner(String imgBanner) {
        this.imgBanner = imgBanner;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
