package com.mocentre.tehui.fee.model;

import java.util.List;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 邮费模板全部信息实体类.
 * 
 * Created by yukaiji on 2016/12/14.
 */
public class FreightMouldAllMsg extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private FreightMould 		freightMould;
	private List<CarryMode> 	carryModeList;

	public FreightMould getFreightMould() {
		return freightMould;
	}

	public void setFreightMould(FreightMould freightMould) {
		this.freightMould = freightMould;
	}

	public List<CarryMode> getCarryModeList() {
		return carryModeList;
	}

	public void setCarryModeList(List<CarryMode> carryModeList) {
		this.carryModeList = carryModeList;
	}

}
