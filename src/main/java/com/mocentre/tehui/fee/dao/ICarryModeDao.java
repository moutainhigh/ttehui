package com.mocentre.tehui.fee.dao;

import java.io.Serializable;
import java.util.List;

import com.mocentre.tehui.fee.model.CarryMode;

/**
 * 运送方式信息数据库操作接口 Created by yukaiji on 2016/11/04.
 */
public interface ICarryModeDao {

	/**
	 * 根据条件查找运送方式列表（包含默认与非默认）
	 * 
	 * @param paramMap
	 *            模板id和运送方式
	 * @return
	 */
	List<CarryMode> queryAll(Serializable id);

	/**
	 * 添加运送方式信息
	 * 
	 * @param carryModeList
	 * @return
	 */
	Long addCarryMode(List<CarryMode> carryModeList);
	
	/**
	 * 根据条件查找运送方式列表（包含默认与非默认）
	 * 
	 * @param paramMap
	 *            模板id和运送方式
	 * @return
	 */
	List<CarryMode> queryByParam(CarryMode carryMode);

	/**
	 * 根据id删除运送方式
	 * 
	 * @param id
	 * @return
	 */
	int removeById(Serializable id);

	/**
	 * 根据idList删除
	 * 
	 * @param freightIdList
	 * @return
	 */
	int delByFreightIdList(List<Long> freightIdList);
}
