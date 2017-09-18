/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.buy.dao.IOrderPayDao;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.buy.service.IOrderPayService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

/**
 * 类OrderPayServiceImpl.java的实现描述：订单支付service实现
 * 
 * @author sz.gong 2016年11月10日 下午8:24:31
 */
@Component
public class OrderPayService implements IOrderPayService {

    @Autowired
    private IOrderPayDao orderPayDao;

    @Override
    @DataSource(value = "read")
    public List<OrderPay> queryOrderPay(String paymentNum) {
        return orderPayDao.queryByPaynum(paymentNum);
    }

    @Override
    @DataSource(value = "write")
    public void updateOrderPay(String paymentNum, OrderPay orderPay) {
        orderPayDao.updateByPaynum(paymentNum, orderPay);
    }

    @Override
    @DataSource(value = "write")
    public void saveOrderPay(OrderPay orderPay) {
        orderPayDao.savePay(orderPay);
    }

    @Override
    @DataSource(value = "read")
    public OrderPay queryOrderPayByOrder(String orderNum, String paymentNum) {
        return orderPayDao.queryByOrder(orderNum, paymentNum);
    }

}
