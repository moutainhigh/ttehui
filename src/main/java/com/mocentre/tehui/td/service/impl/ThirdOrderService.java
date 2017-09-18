/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.payment.abcpay.Constant;
import com.mocentre.tehui.td.dao.IThirdOrderDao;
import com.mocentre.tehui.td.dao.IThirdOrderPayDao;
import com.mocentre.tehui.td.dao.IThirdOrderRefundDao;
import com.mocentre.tehui.td.emnus.ThirdOrderStatus;
import com.mocentre.tehui.td.emnus.ThirdPayStatus;
import com.mocentre.tehui.td.model.ThirdOrder;
import com.mocentre.tehui.td.model.ThirdOrderPay;
import com.mocentre.tehui.td.model.ThirdOrderRefund;
import com.mocentre.tehui.td.service.IThirdOrderService;

/**
 * 类ThirdOrderService.java的实现描述：第三方订单
 * 
 * @author sz.gong 2017年6月20日 下午5:44:21
 */
@Component
public class ThirdOrderService implements IThirdOrderService {

    @Autowired
    private IThirdOrderDao       thirdOrderDao;
    @Autowired
    private IThirdOrderPayDao    thirdOrderPayDao;
    @Autowired
    private IThirdOrderRefundDao thirdOrderRefundDao;

    @Override
    @DataSource(value = "read")
    public ThirdOrder getThirdOrder(String orderNum, String appKey) {
        Assert.notNull(orderNum);
        Assert.notNull(appKey);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        paramMap.put("appKey", appKey);
        return thirdOrderDao.queryUniquely(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public ThirdOrder getThirdOrder(String paymentNum) {
        Assert.notNull(paymentNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("paymentNum", paymentNum);
        return thirdOrderDao.queryUniquely(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Long saveThirdOrder(ThirdOrder order, String payType, String paymentNum) {
        order.setPaymentNum(paymentNum);
        Long id = thirdOrderDao.saveEntity(order);
        ThirdOrderPay orderPay = new ThirdOrderPay();
        orderPay.setPaymentNum(paymentNum);
        orderPay.setOrderNum(order.getOrderNum());
        orderPay.setOrderAmount(order.getOrderAmount());
        orderPay.setPayDate(new Date());
        orderPay.setPayStatus(ThirdOrderStatus.WAIT.getCodeValue());
        orderPay.setPayType(payType);
        orderPay.setNotifyType(Constant.NOTIFY_TYPE_ASYNC);
        thirdOrderPayDao.saveEntity(orderPay);
        return id;
    }

    @Override
    @DataSource(value = "write")
    public Long updateThirdOrder(ThirdOrder order, String payType, String paymentNum) {
        Long id = order.getId();
        String orderNum = order.getOrderNum();
        Long orderAmount = order.getOrderAmount();
        Long count = thirdOrderDao.updatePaymentnum(id, paymentNum);
        ThirdOrderPay orderPay = new ThirdOrderPay();
        orderPay.setPaymentNum(paymentNum);
        orderPay.setOrderNum(orderNum);
        orderPay.setOrderAmount(orderAmount);
        orderPay.setPayDate(new Date());
        orderPay.setPayStatus(ThirdPayStatus.WAIT.getCodeValue());
        orderPay.setPayType(payType);
        thirdOrderPayDao.saveEntity(orderPay);
        return count;
    }

    @Override
    @DataSource(value = "write")
    public void updateThirdOrderAndPay(String paymentNum, String orderStatus, ThirdOrderPay orderPay) {
        int count = thirdOrderDao.updateOrderStatus(orderStatus, paymentNum);
        if (count > 0) {
            thirdOrderPayDao.updateOrderPay(paymentNum, orderPay);
        }
    }

    @Override
    @DataSource(value = "write")
    public void updateThirdOrderRefund(String orderNum, String appKey, ThirdOrderRefund orderRefund) {
        int count = thirdOrderDao.updateOrderStatusRefund(orderNum, appKey);
        if (count > 0) {
            thirdOrderRefundDao.saveEntity(orderRefund);
        }
    }

}
