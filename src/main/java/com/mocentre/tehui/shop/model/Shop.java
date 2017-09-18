package com.mocentre.tehui.shop.model;

import java.util.Date;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 店铺信息实体类.
 * 
 * Created by yukaiji on 2016/11/04.
 */
public class Shop extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String name;// 店铺名称
	private String img_logo;// 店铺logo
	private Integer level;// 店铺等级
	private String leader;// 店铺负责人
	private Date open_date;// 开店日期
	private String address;// 店铺地址
	private String buss_status;// 店铺状态（opening:营业中 stop:关闭 pause:暂停）
	private String audit_status;// 审核状态（wait:审核中 pass:审核通过 unpass:审核不通过）
	private String show_url;// 店铺url

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg_logo() {
		return img_logo;
	}

	public void setImg_logo(String img_logo) {
		this.img_logo = img_logo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public Date getOpen_date() {
		return open_date;
	}

	public void setOpen_date(Date open_date) {
		this.open_date = open_date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBuss_status() {
		return buss_status;
	}

	public void setBuss_status(String buss_status) {
		this.buss_status = buss_status;
	}

	public String getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

}
