/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mocentre.tehui.buy.dao.IOrderBillDao;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 类OrderBillDao.java的实现描述：订单发票接口
 * 
 * @author sz.gong 2016年11月15日 下午5:21:03
 */
@Repository
public class OrderBillDao extends BaseDao<OrderBill> implements IOrderBillDao {

    @Override
    public OrderBill queryByOrder(String orderNum) {
        Assert.notNull(orderNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        return super.queryUniquely(paramMap);
    }

    @Override
    public void saveBatchBill(List<OrderBill> orderBillList) {
        Assert.notNull(orderBillList);
        super.insert("OrderBill_insert_batch", orderBillList);
    }

    @Override
    public void updateByOrder(String orderNum, OrderBill orderBill) {
        orderBill.setOrderNum(orderNum);
        super.update(orderBill);
    }

    @Override
    public void saveBill(OrderBill orderBill) {
        super.saveEntity(orderBill);
    }

}
