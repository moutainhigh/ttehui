/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import java.util.List;

import com.mocentre.tehui.buy.model.OrderPay;

/**
 * 类IOrderPayDao.java的实现描述：订单支付dao接口
 * 
 * @author sz.gong 2016年11月10日 下午8:19:08
 */
public interface IOrderPayDao {

    /**
     * 通过订单编码
     * 
     * @param orderNum
     * @return
     */
    OrderPay queryByOrder(String orderNum);

    /**
     * 通过订单编号和支付编号查询
     * 
     * @param orderNum
     * @param paymentNum
     * @return
     */
    OrderPay queryByOrder(String orderNum, String paymentNum);

    /**
     * 通过支付编号查询
     * 
     * @param paymentNum
     * @return
     */
    List<OrderPay> queryByPaynum(String paymentNum);

    /**
     * 批量保存
     * 
     * @param orderPayList
     */
    void saveBatchPay(List<OrderPay> orderPayList);

    /**
     * 新增
     * 
     * @param orderPay
     */
    void savePay(OrderPay orderPay);

    /**
     * 更新订单支付和支付信息
     * 
     * @param paymentNum 支付编号
     * @param orderPay
     */
    void updateByPaynum(String paymentNum, OrderPay orderPay);

    /**
     * 更新订单支付和支付信息
     * 
     * @param orderNum 订单编号
     * @param orderPay
     */
    void updateByOrdernum(String orderNum, OrderPay orderPay);

}
