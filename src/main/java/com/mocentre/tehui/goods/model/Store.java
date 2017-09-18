package com.mocentre.tehui.goods.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 收藏表实体类 Created by yukaiji on 2016/12/07.
 */
public class Store extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 用户id */
	private Long customerId;
	/** 店铺id */
	private Long shopId;
	/** 店铺名称 */
	private String shopName;
	/** 商品id */
	private Long goodsId;
	/** 商品名称 */
	private String goodsName;
	/** 商品logo地址 */
	private String showLogo;
	/** 商品原价 */
	private String oldPrice;
	/** 商品售价 */
	private String sellPrice;
	/** 收藏时间 */
	private Date storeTime;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getShowLogo() {
		return showLogo;
	}

	public void setShowLogo(String showLogo) {
		this.showLogo = showLogo;
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

	public Date getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}

}
