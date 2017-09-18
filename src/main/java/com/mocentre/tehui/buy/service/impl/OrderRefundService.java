/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.buy.dao.IOrderRefundDao;
import com.mocentre.tehui.buy.model.OrderRefund;
import com.mocentre.tehui.buy.service.IOrderRefundService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

/**
 * 类OrderRefundService.java的实现描述：订单退款service
 * 
 * @author sz.gong 2017年3月17日 下午4:13:05
 */
@Component
public class OrderRefundService implements IOrderRefundService {

    @Autowired
    private IOrderRefundDao orderRefundDao;

    @Override
    @DataSource(value = "write")
    public void saveOrderRefund(OrderRefund orderRefund) {
        orderRefundDao.saveEntity(orderRefund);
    }

    @Override
    @DataSource(value = "write")
    public void saveOrderRefundBatch(List<OrderRefund> orderRefundList) {
        for (OrderRefund orderRefund : orderRefundList) {
            orderRefundDao.saveEntity(orderRefund);
        }
    }

}
