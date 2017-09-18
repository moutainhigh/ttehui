package com.mocentre.gift.ps.dao.impl;

import com.mocentre.gift.ps.dao.IGiftCustomerDao;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.tehui.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 礼品平台 客户Dao实现类
 * @author liqifan
 * @date 创建时间：2017年4月6日 上午11:15:14
 */
@Repository
public class GiftCustomerDao extends BaseDao<GiftCustomer> implements IGiftCustomerDao {

	@Override
	public GiftCustomer getByUserName(String userName) {
		return super.getSqlSession().selectOne(entityClass.getSimpleName() + "_getByUserName",userName);
	}

	@Override
	public int updateGiftCustomerPassWord(Map<String, Object> paramMap) {
		return super.getSqlSession().update(entityClass.getSimpleName() + "_updatePassWord", paramMap);
	}

}
