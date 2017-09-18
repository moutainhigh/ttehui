package com.mocentre.tehui.sub.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 专题商品关联实体类. Created by yukaiji on 2016/12/02.
 */
public class SubjectGoods extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 专题id */
    private Long              subjectId;
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
    /** 商品原价(可能是区间) */
    private String            oldPrice;
    /** 商品最低售价 */
    private Long              sellLowPrice;
    /** 商品最低原价 */
    private Long              oldLowPrice;
    /** 排序 */
    private Integer           sorting;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }
}
