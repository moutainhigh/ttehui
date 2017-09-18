package com.mocentre.tehui.fee.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 邮费模板实体类.
 * 
 * Created by yukaiji on 2016/11/04.
 */
public class FreightMould extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/** 模板名称 */
	private String  name;
	/** 店铺id */
	private Long    shopId;
	/** 发货地址 */
	private String  sendAddr;
	/** 发货时间（1.付款后12小时内；2.付款后24小时内...） */
	private String  sendDate;
	/** 是否包邮（free.卖家承担运费 nofree.自定义运费） */
	private String  isFree;
	/** 计价方式（num.按件数 weight.按重量 ） */
	private String  calcWay;
	/** 是否指定包邮条件 */
	private Integer postRequire;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getSendAddr() {
		return sendAddr;
	}

	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public String getCalcWay() {
		return calcWay;
	}

	public void setCalcWay(String calcWay) {
		this.calcWay = calcWay;
	}

	public Integer getPostRequire() {
		return postRequire;
	}

	public void setPostRequire(Integer postRequire) {
		this.postRequire = postRequire;
	}

}
