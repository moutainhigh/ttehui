package com.mocentre.tehui.goods.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 分类商品 Created by yukaiji on 2017/7/4.
 */
public class CategoryGoods extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/** 分类id **/
	private Long 		categoryId;
	/** 是否外链 **/
	private String 		isChain;
	/** 商品id **/
	private Long 		goodsId;
	/** 展示名称 **/
	private String 		showName;
	/** 展示图片 **/
	private String 		showImg;
	/** 跳转链接 **/
	private String 		linkUrl;
	/** 排序 **/
	private Integer 	sorting;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getIsChain() {
		return isChain;
	}

	public void setIsChain(String isChain) {
		this.isChain = isChain;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
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
}
