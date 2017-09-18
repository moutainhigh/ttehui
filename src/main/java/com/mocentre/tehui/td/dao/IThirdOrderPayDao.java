/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao;

import java.util.Map;

import com.mocentre.tehui.td.model.ThirdOrderPay;

/**
 * 类IThirdOrderPay.java的实现描述：订单支付接口
 * 
 * @author sz.gong 2017年6月21日 上午10:45:30
 */
public interface IThirdOrderPayDao {

    ThirdOrderPay queryUniquely(Map<String, Object> paramMap);

    Long saveEntity(ThirdOrderPay orderPay);

    /**
     * 通过支付编号，更新支付信息
     * 
     * @param paymentNum 支付编号
     * @param orderPay 订单支付对象
     * @return
     */
    Long updateOrderPay(String paymentNum, ThirdOrderPay orderPay);

}
