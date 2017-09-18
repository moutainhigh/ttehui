package com.mocentre.gift.buy.dao.impl;

import com.mocentre.gift.buy.dao.IGiftOrderDao;
import com.mocentre.gift.buy.model.GiftOrder;
import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单dao
 * Created by 王雪莹 on 2017/4/10.
 */
@Repository
public class GiftOrderDao extends BaseDao<GiftOrder> implements IGiftOrderDao {
    @Override
    public void saveOrder(GiftOrder order) {
        super.save(order);
    }

    @Override
    public void upateById(GiftOrder order) {
        Assert.notNull(order.getId());
        super.updateEntity(order);
    }

    @Override
    public void updateOrderStatus(Long id, String status) {
        Assert.notNull(id);
        Assert.notNull(status);
        GiftOrder order = new GiftOrder();
        order.setId(id);
        order.setOrderStatus(status);
        super.updateEntity(order);
    }

    @Override
    public void updateCancelStatus(Long id, String status) {
        Assert.notNull(id);
        Assert.notNull(status);
        GiftOrder order = new GiftOrder();
        order.setId(id);
        super.updateEntity(order);
    }

    @Override
    public Integer logicDelete(String orderNum) {
        GiftOrder order = new GiftOrder();
        order.setOrderNum(orderNum);
        return super.getSqlSession().update("GiftOrder_logic_delete", order);
    }

    @Override
    public Integer logicDelete(Long id) {
        GiftOrder order = new GiftOrder();
        order.setId(id);
        return super.getSqlSession().update("GiftOrder_logic_delete", order);
    }

    @Override
    public PageInfo<GiftOrder> queryPageInfo(Long customerId, int start, int length) {
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("column", "orderTime");
        paramMap.put("orderBy", "desc");
        paramMap.put("customerId", customerId);
        paramMap.put("length", length);
        paramMap.put("start", start);
        PageInfo<GiftOrder> pageInfo = queryPaged(paramMap);
        return pageInfo;
    }

    @Override
    public GiftOrder queryByNumCustomer(String orderNum, Long customerId) {
        Assert.notNull(orderNum);
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        paramMap.put("customerId", customerId);
        return queryUniquely(paramMap);
    }

    @Override
    public GiftOrder queryByNum(String orderNum) {
        Assert.notNull(orderNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        return queryUniquely(paramMap);
    }

    @Override
    public GiftOrder queryById(Long id) {
        Assert.notNull(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return queryUniquely(paramMap);
    }
}
