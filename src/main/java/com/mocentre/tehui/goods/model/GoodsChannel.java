package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 商品频道关联表
 * Created by 王雪莹 on 2016/11/16.
 */
public class GoodsChannel extends BaseEntity {

	private static final long serialVersionUID = 8005152696267721780L;

	/** 商品id*/
    private Long 			goodsId;

    /** 频道id */
    private Long 			channelId;

    /** 商品在频道中的排序 */
    private String 			channelSorting;

    /** 商品在该频道下是否置顶*/
    private String 			isTop;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelSorting() {
        return channelSorting;
    }

    public void setChannelSorting(String channelSorting) {
        this.channelSorting = channelSorting;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }
}
