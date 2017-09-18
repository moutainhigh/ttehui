package com.mocentre.gift.mall.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 礼品平台 banner图实体类
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午3:45:37
 */
public class GiftBanner extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/** 名称 **/
	private String name;
	
	/** pc端图片 **/
	private String pcImg;
	
	/** 移动端图片 **/
	private String mobImg;
	
	/** 跳转链接 **/
	private String linkUrl;
	
	/** 排序 **/
	private Integer sort;

	/** 关联商品id **/
	private Integer goodsId;

	/** banner类型 **/
	private String  status;
	
	/** 是否展示 **/
	private Integer isShow;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPcImg() {
		return pcImg;
	}

	public void setPcImg(String pcImg) {
		this.pcImg = pcImg;
	}

	public String getMobImg() {
		return mobImg;
	}

	public void setMobImg(String mobImg) {
		this.mobImg = mobImg;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
}
