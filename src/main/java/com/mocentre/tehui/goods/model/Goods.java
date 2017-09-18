package com.mocentre.tehui.goods.model;

import java.util.Date;
import java.util.List;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 商品表商品model Created by 王雪莹 on 2016/11/4.
 */
public class Goods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名
     */
    private String            title;

    /**
     * 店铺id
     */
    private Long              shopId;

    /**
     * 分类id
     */
    private Long              categoryId;

    /**
     * 原价(展示用，可能是区间)
     */
    private String            oldPrice;

    /**
     * 售价(展示用，可能是区间)
     */
    private String            sellPrice;

    /**
     * 最低原价
     */
    private Long              oldLowPrice;

    /**
     * 最低售价
     */
    private Long              sellLowPrice;

    /**
     * 是否限制购买数
     */
    private String            isLimitBuy;

    /**
     * 限制购买是数量
     */
    private Integer           limitNums;

    /**
     * 积分
     */
    private Integer           score;

    /**
     * 上下架状态
     */
    private String            isShow;

    /**
     * 审核状态
     */
    private String            isChecked;
    /**
     * 上架时间
     */
    private Date              onShelfTime;
    /**
     * 天猫价(展示用)
     */
    private String            tmPrice;

    /**
     * 京东价(展示用)
     */
    private String            jdPrice;

    /**
     * 描述
     */
    private String            describe;

    /**
     * 商品在店铺的排序
     */
    private Integer           shopSorting;

    /**
     * 顶部详细bannner
     */
    private String            imgBanner;

    /**
     * 详情介绍页
     */
    private String            details;

    /**
     * 总库存
     */
    private Long              storeTotal;

    /**
     * 总销量
     */
    private Long              saleTotal;

    /**
     * 商品规格（注：属性和属性值的json串）
     */
    private String            standard;

    /**
     * 拥有的规格json串
     */
    private String            holdStandard;

    /**
     * 地域List
     */
    private List<String>      areaList;

    /**
     * 运费模版
     */
    private String            freightMould;

    /**
     * 查询列表页展示的图片
     */
    private String            imgListPage;

    /**
     * 购物车使用的图片
     */
    private String            imgCart;

    /**
     * 是否可使用优惠劵
     */
    private String            isCoupon;

    /**
     * 允许优惠劵使用的数量
     */
    private String            couponNum;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTmPrice() {
        return tmPrice;
    }

    public void setTmPrice(String tmPrice) {
        this.tmPrice = tmPrice;
    }

    public String getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(String jdPrice) {
        this.jdPrice = jdPrice;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getIsLimitBuy() {
        return isLimitBuy;
    }

    public void setIsLimitBuy(String isLimitBuy) {
        this.isLimitBuy = isLimitBuy;
    }

    public Integer getLimitNums() {
        return limitNums;
    }

    public void setLimitNums(Integer limitNums) {
        this.limitNums = limitNums;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public Date getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(Date onShelfTime) {
        this.onShelfTime = onShelfTime;
    }

    public Integer getShopSorting() {
        return shopSorting;
    }

    public void setShopSorting(Integer shopSorting) {
        this.shopSorting = shopSorting;
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

    public Long getStoreTotal() {
        return storeTotal;
    }

    public void setStoreTotal(Long storeTotal) {
        this.storeTotal = storeTotal;
    }

    public Long getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Long saleTotal) {
        this.saleTotal = saleTotal;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }

    public String getFreightMould() {
        return freightMould;
    }

    public void setFreightMould(String freightMould) {
        this.freightMould = freightMould;
    }

    public String getImgListPage() {
        return imgListPage;
    }

    public void setImgListPage(String imgListPage) {
        this.imgListPage = imgListPage;
    }

    public String getImgCart() {
        return imgCart;
    }

    public void setImgCart(String imgCart) {
        this.imgCart = imgCart;
    }

    public String getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(String isCoupon) {
        this.isCoupon = isCoupon;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
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

    public Long getOldLowPrice() {
        return oldLowPrice;
    }

    public void setOldLowPrice(Long oldLowPrice) {
        this.oldLowPrice = oldLowPrice;
    }

    public Long getSellLowPrice() {
        return sellLowPrice;
    }

    public void setSellLowPrice(Long sellLowPrice) {
        this.sellLowPrice = sellLowPrice;
    }

    public String getHoldStandard() {
        return holdStandard;
    }

    public void setHoldStandard(String holdStandard) {
        this.holdStandard = holdStandard;
    }
}
