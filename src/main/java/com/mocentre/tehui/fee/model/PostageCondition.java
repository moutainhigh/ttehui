package com.mocentre.tehui.fee.model;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 包邮条件表实体类.
 * 
 * Created by yukaiji on 2016/11/04.
 */
public class PostageCondition extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long freight_id;// 运费模板id
	private String post_area;// 包邮地区
	private Integer post_way;// 包邮方式（money.金额 num.件数 weight.重量）
	private Integer need_con;// 条件（gt:大于 lt:小于）
	private String con_value;// 条件值
	private String send_way;// 运送方式(express.快递 ems.ems mail.平邮)

	public Long getFreight_id() {
		return freight_id;
	}

	public void setFreight_id(Long freight_id) {
		this.freight_id = freight_id;
	}

	public String getPost_area() {
		return post_area;
	}

	public void setPost_area(String post_area) {
		this.post_area = post_area;
	}

	public Integer getPost_way() {
		return post_way;
	}

	public void setPost_way(Integer post_way) {
		this.post_way = post_way;
	}

	public Integer getNeed_con() {
		return need_con;
	}

	public void setNeed_con(Integer need_con) {
		this.need_con = need_con;
	}

	public String getCon_value() {
		return con_value;
	}

	public void setCon_value(String con_value) {
		this.con_value = con_value;
	}

	public String getSend_way() {
		return send_way;
	}

	public void setSend_way(String send_way) {
		this.send_way = send_way;
	}

}
