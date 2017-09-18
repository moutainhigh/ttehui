package com.mocentre.gift.ps.dao.impl;

import com.mocentre.gift.ps.dao.IGiftCustomerAddressDao;
import com.mocentre.gift.ps.model.GiftCustomerAddress;
import com.mocentre.tehui.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

 /**
  * 礼品平台 商城用户个人中心地址数据库操作接口实现
  * @author liqifan
  * @date 创建时间：2017年4月13日 上午10:17:41
  */
@Repository
public class GiftCustomerAddressDao extends BaseDao<GiftCustomerAddress> implements IGiftCustomerAddressDao {

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int delById(Long id) {
		return getSqlSession().delete("GiftCustomerAddress_del_id", id);
	}

	/**
	 * 根据id批量删除
	 * 
	 * @param idList
	 * @return
	 */
	@Override
	public int delById(List<Long> idList) {
		return getSqlSession().delete("GiftCustomerAddress_del_ids", idList);
	}

	/**
	 * 根据用户id删除
	 * 
	 * @param customerId
	 * @return
	 */
	@Override
	public int delByCustomerId(Long customerId) {
		return getSqlSession().delete("GiftCustomerAddress_del_customerId", customerId);
	}

	/**
	 * 根据用户idList批量删除
	 * 
	 * @param customerIds
	 * @return
	 */
	@Override
	public int delByCustomerId(List<Long> customerIds) {
		return getSqlSession().delete("GiftCustomerAddress_del_customerIds", customerIds);
	}

	/**
	 * 将不需要改为默认的全部置为0
	 * 
	 * @param GiftCustomerAddress
	 * @return
	 */
	@Override
	public int updateDefult(GiftCustomerAddress GiftCustomerAddress) {
		return getSqlSession().update("GiftCustomerAddress_updateDefult", GiftCustomerAddress);
	}

}
