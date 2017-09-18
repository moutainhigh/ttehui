package com.mocentre.gift.dma.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 礼品平台 极速获取方案实体类
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午1:37:39
 */
public class GiftDemand extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/** 用户id **/
	private Long customerId;
	
	/** 应用场景 **/
	private String scene;
	
	/** 礼品特征 **/
	private String giftFeature;
	
	/** 预算 **/
	private String budget;
	
	/** 礼品总量 **/
	private String giftNum;
	
	/** 联系电话 **/
	private String telephone;
	
	/** 是否已联系（1:已联系；2:未联系） **/
	private String status;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getGiftFeature() {
		return giftFeature;
	}

	public void setGiftFeature(String giftFeature) {
		this.giftFeature = giftFeature;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getGiftNum() {
		return giftNum;
	}

	public void setGiftNum(String giftNum) {
		this.giftNum = giftNum;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
