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

import com.mocentre.tehui.buy.dao.IOrderPayDao;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 类OrderPayDaoImpl.java的实现描述：订单支付dao实现
 * 
 * @author sz.gong 2016年11月10日 下午8:20:13
 */
@Repository
public class OrderPayDao extends BaseDao<OrderPay> implements IOrderPayDao {

    @Override
    public OrderPay queryByOrder(String orderNum) {
        Assert.notNull(orderNum);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNum", orderNum);
        return super.queryUniquely(map);
    }

    @Override
    public OrderPay queryByOrder(String orderNum, String paymentNum) {
        Assert.notNull(orderNum);
        Assert.notNull(paymentNum);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNum", orderNum);
        map.put("paymentNum", paymentNum);
        return super.queryUniquely(map);
    }

    @Override
    public void saveBatchPay(List<OrderPay> orderPayList) {
        super.insert("OrderPay_insert_batch", orderPayList);
    }

    @Override
    public List<OrderPay> queryByPaynum(String paymentNum) {
        Assert.notNull(paymentNum);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("paymentNum", paymentNum);
        return super.queryList(params);
    }

    @Override
    public void savePay(OrderPay orderPay) {
        super.saveEntity(orderPay);
    }

    @Override
    public void updateByPaynum(String paymentNum, OrderPay orderPay) {
        Assert.notNull(paymentNum);
        orderPay.setPaymentNum(paymentNum);
        super.update(orderPay);
    }

    @Override
    public void updateByOrdernum(String orderNum, OrderPay orderPay) {
        Assert.notNull(orderNum);
        orderPay.setOrderNum(orderNum);
        super.update("OrderPay_update_byorder", orderPay);
    }

}
