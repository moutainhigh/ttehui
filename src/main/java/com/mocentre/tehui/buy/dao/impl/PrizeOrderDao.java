package com.mocentre.tehui.buy.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.buy.dao.IPrizeOrderDao;
import com.mocentre.tehui.buy.model.PrizeOrder;
import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.core.service.support.paging.PageInfo;

/**
 * 实物礼品订单dao实现 Created by wangxueying on 2017/8/10.
 */
@Repository
public class PrizeOrderDao extends BaseDao<PrizeOrder> implements IPrizeOrderDao {

    @Override
    public PrizeOrder getById(Long id) {
        return super.get(id);
    }

    @Override
    public PageInfo<PrizeOrder> queryPageInfo(Long customerId, Integer start, Integer length) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("column", "gmtCreated");
        paramMap.put("orderBy", "desc");
        paramMap.put("customerId", customerId);
        paramMap.put("length", length);
        paramMap.put("start", start);
        PageInfo<PrizeOrder> pageInfo = super.queryPaged(paramMap);
        return pageInfo;
    }
}
