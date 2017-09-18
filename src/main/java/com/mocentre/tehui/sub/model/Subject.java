package com.mocentre.tehui.sub.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 专题基本信息实体类.
 * 
 * Created by yukaiji on 2016/12/02.
 */
public class Subject extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/** 店铺id */
	private Long shopId;
	/** 专题名称 */
	private String title;
	/** 专题模板code */
	private String subTmpCode;
	/** 顶部banner */
	private String topBanner;
	/** 投放城市 */
	private String beCity;
	/** 投放经度 */
	private Double beLongitude;
	/** 投放纬度 */
	private Double beLatitude;
	/** 是否展示 */
	private Integer isShow;
	/** 专题简介 */
	private String intro;
	/** 专题排序 */
	private Integer sorting;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTmpCode() {
		return subTmpCode;
	}

	public void setSubTmpCode(String subTmpCode) {
		this.subTmpCode = subTmpCode;
	}

	public String getTopBanner() {
		return topBanner;
	}

	public void setTopBanner(String topBanner) {
		this.topBanner = topBanner;
	}

	public String getBeCity() {
		return beCity;
	}

	public void setBeCity(String beCity) {
		this.beCity = beCity;
	}

	public Double getBeLongitude() {
		return beLongitude;
	}

	public void setBeLongitude(Double beLongitude) {
		this.beLongitude = beLongitude;
	}

	public Double getBeLatitude() {
		return beLatitude;
	}

	public void setBeLatitude(Double beLatitude) {
		this.beLatitude = beLatitude;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getSorting() {
		return sorting;
	}

	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}

}
