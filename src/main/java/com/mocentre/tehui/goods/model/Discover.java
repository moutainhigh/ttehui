package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 发现页 Created by 王雪莹 on 2016/11/16.
 */
public class Discover extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/** 店铺id */
	private Long 		shopId;
	/** 是否关联商品 */
	private Integer 	isGoods;
	/** 频道id */
	private Long 		goodsChannel;
	/** 商品id */
	private Long	 	goodsId;
	/** 名称 */
	private String 		actName;
	/** 跳转链接 */
	private String 		url;
	/** 展示图片 */
	private String 		showImg;
	/** 是否显示 */
	private Integer 	isShow;
	/** 排序 */
	private Integer 	sorting;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getIsGoods() {
		return isGoods;
	}

	public void setIsGoods(Integer isGoods) {
		this.isGoods = isGoods;
	}

	public Long getGoodsChannel() {
		return goodsChannel;
	}

	public void setGoodsChannel(Long goodsChannel) {
		this.goodsChannel = goodsChannel;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShowImg() {
		return showImg;
	}

	public void setShowImg(String showImg) {
		this.showImg = showImg;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getSorting() {
		return sorting;
	}

	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}

}
