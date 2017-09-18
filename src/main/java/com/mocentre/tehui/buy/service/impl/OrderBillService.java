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

import com.mocentre.tehui.buy.dao.IOrderBillDao;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.buy.service.IOrderBillService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

/**
 * 类OrderBillService.java的实现描述：订单发票service实现
 * 
 * @author sz.gong 2016年12月16日 下午3:16:17
 */
@Component
public class OrderBillService implements IOrderBillService {

    @Autowired
    private IOrderBillDao orderBillDao;

    @Override
    @DataSource(value = "read")
    public OrderBill queryBillByOrder(String orderNum) {
        return orderBillDao.queryByOrder(orderNum);
    }

}
