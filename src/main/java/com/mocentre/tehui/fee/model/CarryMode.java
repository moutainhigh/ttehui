package com.mocentre.tehui.fee.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 运送方式实体类.
 * 
 * Created by yukaiji on 2016/11/04.
 */
public class CarryMode extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/** 运费模板id */
	private Long 								freightId; 
	/** 运送方式(express.快递 ems.ems mail.平邮) */
	private String 								sendWay; 
	/** 运送地区（省-市-区） */
	private String 								arriveArea; 
	/** 首单位数量（依据运费模板表计价方式来选择） */
	private String 								firstNum; 
	/** 首费用(单位分) */
	private Long 								firstFree; 
	/** 续单位数量（依据运费模板表计价方式来选择） */
	private String 								nextNum; 
	/** 续费用(单位分) */
	private Long 								nextFree; 
	/** 是否默认 */
	private Integer 							isDefault; 

	public Long getFreightId() {
		return freightId;
	}

	public void setFreightId(Long freightId) {
		this.freightId = freightId;
	}

	public String getSendWay() {
		return sendWay;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}

	public String getArriveArea() {
		return arriveArea;
	}

	public void setArriveArea(String arriveArea) {
		this.arriveArea = arriveArea;
	}

	public String getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(String firstNum) {
		this.firstNum = firstNum;
	}

	public Long getFirstFree() {
		return firstFree;
	}

	public void setFirstFree(Long firstFree) {
		this.firstFree = firstFree;
	}

	public String getNextNum() {
		return nextNum;
	}

	public void setNextNum(String nextNum) {
		this.nextNum = nextNum;
	}

	public Long getNextFree() {
		return nextFree;
	}

	public void setNextFree(Long nextFree) {
		this.nextFree = nextFree;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}
