/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao.impl;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.td.dao.IThirdOrderPayDao;
import com.mocentre.tehui.td.model.ThirdOrderPay;

/**
 * 类ThirdOrderPayDao.java的实现描述：第三方订单支付
 * 
 * @author sz.gong 2017年6月21日 上午10:50:29
 */
@Repository
public class ThirdOrderPayDao extends BaseDao<ThirdOrderPay> implements IThirdOrderPayDao {

    @Override
    public Long updateOrderPay(String paymentNum, ThirdOrderPay orderPay) {
        orderPay.setPaymentNum(paymentNum);
        return super.update(orderPay);
    }
}
