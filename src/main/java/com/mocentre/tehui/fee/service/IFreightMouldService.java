package com.mocentre.tehui.fee.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.FreightMouldParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.fee.model.FreightMould;
import com.mocentre.tehui.fee.model.FreightMouldAllMsg;
import com.mocentre.tehui.frontend.model.FreightFTInstance;
import com.mocentre.tehui.frontend.param.FreightCalculateParam;

/**
 * 邮费模板信息接口.
 * 
 * Created by yukaiji on 2016/11/04.
 */
public interface IFreightMouldService {

	/**
	 * 分页查询邮费模板
	 * 
	 * @param require
	 * @return
	 */
	ListJsonResult<FreightMould> queryFreightMouldPage(Requirement require);

	/**
	 * 根据店铺id和id查询具体模板信息
	 * 
	 * @param id
	 * @param shopId
	 * @return
	 */
	FreightMouldAllMsg getfreightMouldAllMsgById(Long id, Long shopId);

	/**
	 * 添加一个模板
	 * 
	 * @param freightMouldParam
	 * @return
	 */
	FreightMould addFreightMould(FreightMouldParam freightMouldParam);

	/**
	 * 修改一个模板
	 * 
	 * @param freightMouldParam
	 * @return
	 */
	FreightMould editFreightMould(FreightMouldParam freightMouldParam);

	/**
	 * 批量删除模板
	 * 
	 * @param idList
	 */
	int deleteFreightMould(List<Long> idList);

	/**
	 * 根据id查询模板信息
	 * 
	 * @param freightId
	 * @return
	 */
	FreightMould getFreightMould(Long freightId, Long shopId);

	/**
	 * 计算运送方式对应邮费
	 * 
	 * @param freightId 运费模板id
	 * 		  shopId    店铺id
	 * 		  freeNum   商品数量
	 * 		  areaId    地区code
	 * 
	 * @return 运送方式和对应邮费列表
	 */
	List<FreightFTInstance> getSendWayAndPriceList(FreightCalculateParam freightCalculateParam);
	
	/**
	 * 获取实际应支付运费
	 * 
	 * @param freightId 运费模板id
	 * 		  shopId    店铺id
	 * 		  freeNum   商品数量
	 * 		  free		单位
	 * 		  areaId    地区code
	 * 		  sendWay   运送方式
	 * 
	 * @return 运费结果
	 * 
	 */
	Long getPrice(FreightCalculateParam freightCalculateParam);
}
