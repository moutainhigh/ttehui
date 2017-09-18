package com.mocentre.tehui.act.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 活动商品关联表实体类.
 * 
 * @author Created by yukaiji on 2017年1月16日
 */
public class ActivityGoods extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 活动id */
    private Long              activityId;
    /** 商品id */
    private Long              goodsId;
    /** 商品图片 */
    private String            goodsImg;
    /** 商品名称 */
    private String            goodsName;
    /** 宣传语 */
    private String            tagline;
    /** 商品售价(可能是区间) */
    private String            sellPrice;
    /** 商品售价最低价 **/
    private Long              sellLowPrice;
    /** 商品原价(可能是区间) */
    private String            oldPrice;
    /** 商品原价最低价 **/
    private Long              oldLowPrice;
    /** 拼团活动可参与人数 */
    private Integer           grouponNum;
    /** 真实跳转链接url */
    private String            actualUrl;
    /** 对外短链接url */
    private String            shortUrl;
    /** 开始时间 */
    private Date              beginTime;
    /** 结束时间 */
    private Date              endTime;
    /** 活动类型 */
    private String            activityType;
    /** 拥有的库存规格json串 */
    private String            holdStandard;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Integer getGrouponNum() {
        return grouponNum;
    }

    public void setGrouponNum(Integer grouponNum) {
        this.grouponNum = grouponNum;
    }

    public String getActualUrl() {
        return actualUrl;
    }

    public void setActualUrl(String actualUrl) {
        this.actualUrl = actualUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
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

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getHoldStandard() {
        return holdStandard;
    }

    public void setHoldStandard(String holdStandard) {
        this.holdStandard = holdStandard;
    }

    public Long getSellLowPrice() {
        return sellLowPrice;
    }

    public void setSellLowPrice(Long sellLowPrice) {
        this.sellLowPrice = sellLowPrice;
    }

    public Long getOldLowPrice() {
        return oldLowPrice;
    }

    public void setOldLowPrice(Long oldLowPrice) {
        this.oldLowPrice = oldLowPrice;
    }

}
