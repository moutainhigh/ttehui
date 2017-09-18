/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.buy.dao.IOrderLogisticsDao;
import com.mocentre.tehui.buy.model.OrderLogistics;
import com.mocentre.tehui.buy.service.IOrderLogisticsService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

/**
 * 类ILogisticsServiceImpl.java的实现描述：物流service
 * 
 * @author sz.gong 2016年11月22日 下午2:02:43
 */
@Component
public class OrderLogisticsService implements IOrderLogisticsService {

    @Autowired
    private IOrderLogisticsDao orderLogisticsDao;

    @Override
    @DataSource(value = "write")
    public void updateByOrderId(Long orderId, String company, String expNum, String isSms, String logisticsCode) {
        orderLogisticsDao.updateByOrderId(orderId, company, expNum, isSms, logisticsCode);
    }

    @Override
    @DataSource(value = "read")
    public OrderLogistics queryLogisticsByOrder(Long orderId) {
        return orderLogisticsDao.queryByOrderId(orderId);
    }

}
