package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 商品库存表 Created by 王雪莹 on 2016/11/5.
 */
public class GoodsStorage extends BaseEntity {

    private static final long serialVersionUID = -1161946123441458623L;

    /** 商品id **/
    private Long              goodsId;

    /** 规格编码 **/
    private String            standardCode;

    /** 专题商品关联表id */
    private Long              subGoodsId;

    /** 库存量 **/
    private Long              stockNum;

    /** 实际价格（分） **/
    private Long              salePrice;

    /** 原价格 **/
    private Long              oldPrice;

    /** 数据版本号（防止库存覆盖更新） **/
    private Long              version;

    /** 库存描述 */
    private String            describe;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public Long getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(Long subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Long getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Long oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
