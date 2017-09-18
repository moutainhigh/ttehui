package com.mocentre.gift.ps.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 礼品平台 客户实体类
 * 
 * @author liqifan
 * @date 创建时间：2017年4月6日 上午10:39:18
 */
public class GiftCustomer extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/** 手机号 **/
	private String            telephone;

	/** 用户名 **/
	private String            customerName;

	/** 登录名 **/
	private String            userName;

	/** 机构名称 **/
	private String            organization;

	/** 运营人员 **/
	private String            operator;

	/** 运营人员电话 **/
	private String            optTelephone;

	/** 注册密码 **/
	private String            password;

	/** 密码随机数 **/
	private String            randomNum;

	/** 注册时间 **/
	private Date              registerTime;

	/** 最近一次登入时间 **/
	private Date              lastLoginTime;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOptTelephone() {
		return optTelephone;
	}

	public void setOptTelephone(String optTelephone) {
		this.optTelephone = optTelephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
