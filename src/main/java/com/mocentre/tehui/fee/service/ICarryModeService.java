package com.mocentre.tehui.fee.service;

import java.util.List;

import com.mocentre.tehui.backend.param.CarryModeParam;
import com.mocentre.tehui.fee.model.CarryMode;

/**
 * 运送方式信息接口.
 * 
 * Created by yukaiji on 2016/11/04.
 */
public interface ICarryModeService {

	/**
	 * 根据模板id获取运送方式信息
	 * 
	 * @param freightId
	 * @return
	 */
	List<CarryMode> getAllCarryMode(Long freightId);

	/**
	 * 插入运送方式信息
	 * 
	 * @param carryModeParamList
	 * @return
	 */
	Long addCarryMode(List<CarryModeParam> carryModeParamList);

	/**
	 * 修改运送方式信息（先删除，再添加）
	 * 
	 * @param carryModeParamList
	 * @param freightId
	 * @return
	 */
	Long editCarryMode(List<CarryModeParam> carryModeParamList, Long freightId);

	/**
	 * 根据运费模板id删除
	 * 
	 * @param freight_id
	 */
	void deleteCarryMode(Long freightId);

	/**
	 * 根据运费模板idList批量删除
	 * 
	 * @param freightIdList
	 */
	void delByFreightIdList(List<Long> freightIdList);

	/**
	 * 根据条件获取运送方式信息
	 * 
	 * @param freight_id
	 * @param send_way
	 * @return
	 */
	List<CarryMode> getCarryModeByParam(CarryModeParam carryModeParam);
}
