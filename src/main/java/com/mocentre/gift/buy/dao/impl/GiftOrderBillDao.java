package com.mocentre.gift.buy.dao.impl;

import com.mocentre.gift.buy.dao.IGiftOrderBillDao;
import com.mocentre.gift.buy.model.GiftOrderBill;
import com.mocentre.tehui.core.dao.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票信息dao
 * Created by 王雪莹 on 2017/4/10.
 */
@Repository
public class GiftOrderBillDao extends BaseDao<GiftOrderBill> implements IGiftOrderBillDao {
    @Override
    public void saveOrderBill(GiftOrderBill bill) {
        super.save(bill);
    }

    @Override
    public void updateOrderBill(GiftOrderBill bill) {
        Assert.notNull(bill.getId());
        super.updateEntity(bill);
    }

    @Override
    public GiftOrderBill getById(Long id) {
        Assert.notNull(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return queryUniquely(paramMap);
    }

    @Override
    public List<GiftOrderBill> getByCustomerId(Long customerId) {
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", customerId);
        return queryList(paramMap);
    }

    @Override
    public Integer logicDelete(Long id) {
        GiftOrderBill bill = new GiftOrderBill();
        bill.setId(id);
        return super.getSqlSession().update(entityClass.getSimpleName() +"_logicDelete", bill);
    }

    @Override
    public GiftOrderBill queryByOrderNum(String orderNum) {
        Assert.notNull(orderNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        return queryUniquely(paramMap);
    }

    @Override
    public GiftOrderBill queryByOrderId(Long orderId) {
        Assert.notNull(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        return queryUniquely(paramMap);
    }
}
