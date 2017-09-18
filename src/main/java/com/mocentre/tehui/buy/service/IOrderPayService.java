/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import java.util.List;

import com.mocentre.tehui.buy.model.OrderPay;

/**
 * 类IOrderPayService.java的实现描述：订单支付Service
 * 
 * @author sz.gong 2016年11月10日 下午8:23:41
 */
public interface IOrderPayService {

    /**
     * 通过支付编号查询
     * 
     * @param paymentNum
     * @return
     */
    List<OrderPay> queryOrderPay(String paymentNum);

    /**
     * 更新支付
     * 
     * @param paymentNum
     * @param orderPay
     */
    void updateOrderPay(String paymentNum, OrderPay orderPay);

    /**
     * 新增支付
     * 
     * @param orderPay
     */
    void saveOrderPay(OrderPay orderPay);

    /**
     * 通过订单编码和支付编码查询
     * 
     * @param orderNum
     * @param paymentNum
     * @return
     */
    OrderPay queryOrderPayByOrder(String orderNum, String paymentNum);

}
