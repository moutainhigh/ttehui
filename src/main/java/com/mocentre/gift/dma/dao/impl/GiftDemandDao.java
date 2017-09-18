package com.mocentre.gift.dma.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.gift.dma.dao.IGiftDemandDao;
import com.mocentre.gift.dma.model.GiftDemand;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 礼品平台 极速获取方案dao实现类
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午2:35:13
 */
@Repository
public class GiftDemandDao extends BaseDao<GiftDemand> implements IGiftDemandDao {

	@Override
	public int changeGiftDemandStatus(Map<String, Object> paramMap) {
		return super.getSqlSession().update(entityClass.getSimpleName() + "_changeStatus", paramMap);
	}

}
