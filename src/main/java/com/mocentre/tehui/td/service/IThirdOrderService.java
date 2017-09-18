/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.service;

import com.mocentre.tehui.td.model.ThirdOrder;
import com.mocentre.tehui.td.model.ThirdOrderPay;
import com.mocentre.tehui.td.model.ThirdOrderRefund;

/**
 * 类IThirdOrderService.java的实现描述：第三方订单
 * 
 * @author sz.gong 2017年6月20日 下午5:43:02
 */
public interface IThirdOrderService {

    /**
     * 查询订单
     * 
     * @param orderNum 订单编号
     * @param appKey 公钥
     * @return
     */
    ThirdOrder getThirdOrder(String orderNum, String appKey);

    /**
     * 查询订单
     * 
     * @param paymentNum 支付编号
     * @return
     */
    ThirdOrder getThirdOrder(String paymentNum);

    /**
     * 新建订单和支付
     * 
     * @param order 订单
     * @param payType 支付方式
     * @param paymentNum 支付编号
     * @return
     */
    Long saveThirdOrder(ThirdOrder order, String payType, String paymentNum);

    /**
     * 更新订单支付编号，新建支付
     * 
     * @param order 订单
     * @param payType 支付方式
     * @param paymentNum 支付编号
     * @return
     */
    Long updateThirdOrder(ThirdOrder order, String payType, String paymentNum);

    /**
     * 更新订单状态，更新支付信息
     * 
     * @param paymentNum 支付编号
     * @param orderStatus 订单状态
     * @param orderPay 支付
     */
    void updateThirdOrderAndPay(String paymentNum, String orderStatus, ThirdOrderPay orderPay);

    /**
     * 更新订单退款状态，新增退款信息
     * 
     * @param orderNum
     * @param appKey
     * @param orderRefund
     */
    void updateThirdOrderRefund(String orderNum, String appKey, ThirdOrderRefund orderRefund);

}
