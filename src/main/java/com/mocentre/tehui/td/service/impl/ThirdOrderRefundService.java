/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.td.dao.IThirdOrderRefundDao;
import com.mocentre.tehui.td.model.ThirdOrderRefund;
import com.mocentre.tehui.td.service.IThirdOrderRefundService;

/**
 * 类ThirdOrderRefund.java的实现描述：订单退款Service
 * 
 * @author sz.gong 2017年6月29日 下午4:49:48
 */
@Component
public class ThirdOrderRefundService implements IThirdOrderRefundService {

    @Autowired
    private IThirdOrderRefundDao orderRefundDao;

    @Override
    @DataSource(value = "write")
    public void saveOrderRefund(ThirdOrderRefund orderRefund) {
        orderRefundDao.saveEntity(orderRefund);
    }

}
