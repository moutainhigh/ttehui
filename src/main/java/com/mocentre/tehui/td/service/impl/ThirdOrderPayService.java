/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.td.dao.IThirdOrderPayDao;
import com.mocentre.tehui.td.model.ThirdOrderPay;
import com.mocentre.tehui.td.service.IThirdOrderPayService;

/**
 * 类ThirdOrderPayService.java的实现描述：第三方订单支付service实现
 * 
 * @author sz.gong 2017年6月21日 下午5:45:31
 */
@Component
public class ThirdOrderPayService implements IThirdOrderPayService {

    @Autowired
    private IThirdOrderPayDao thirdOrderPayDao;

    @Override
    @DataSource(value = "read")
    public ThirdOrderPay getThirdOrderPay(String paymentNum) {
        Assert.notNull(paymentNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("paymentNum", paymentNum);
        return thirdOrderPayDao.queryUniquely(paramMap);
    }

}
