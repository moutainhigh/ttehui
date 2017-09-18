package com.mocentre.tehui.bak.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 农行掌银客户端首页实体类。 Created by yukaiji on 2017/5/17.
 */
public class MallHome extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 位置类型（act:活动精选 goods:爆品特卖） **/
    private String            showLocal;
    /** 是否外链（0:否 1:是） **/
    private String            isChain;
    /** 商品id **/
    private Long              goodsId;
    /** 活动id **/
    private Long              activityId;
    /** 活动商品id **/
    private Long              actGoodsId;
    /** 类型（seckill:秒杀 groupon:团购 common:普通） **/
    private String            goodsType;
    /** 展示名称 **/
    private String            showName;
    /** 展示图片 **/
    private String            showImg;
    /** 展示价格 **/
    private String            showPrice;
    /** 商品原价 **/
    private String            oldPrice;
    /** 描述 **/
    private String            showDes;
    /** 跳转链接 **/
    private String            linkUrl;
    /** 排序 **/
    private Integer           sorting;
    /** 活动精选展示图片 **/
    private String            tagImg;

    public String getShowLocal() {
        return showLocal;
    }

    public void setShowLocal(String showLocal) {
        this.showLocal = showLocal;
    }

    public String getIsChain() {
        return isChain;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActGoodsId() {
        return actGoodsId;
    }

    public void setActGoodsId(Long actGoodsId) {
        this.actGoodsId = actGoodsId;
    }

    public void setIsChain(String isChain) {
        this.isChain = isChain;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public String getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(String showPrice) {
        this.showPrice = showPrice;
    }

    public String getShowDes() {
        return showDes;
    }

    public void setShowDes(String showDes) {
        this.showDes = showDes;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getTagImg() {
        return tagImg;
    }

    public void setTagImg(String tagImg) {
        this.tagImg = tagImg;
    }
}
