/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao;

import java.util.Map;

import com.mocentre.tehui.td.model.ThirdOrder;

/**
 * 类IThirdOrderDao.java的实现描述：第三方订单
 * 
 * @author sz.gong 2017年6月20日 下午5:41:15
 */
public interface IThirdOrderDao {

    ThirdOrder queryUniquely(Map<String, Object> paramMap);

    Long saveEntity(ThirdOrder order);

    /**
     * 更新支付编号
     * 
     * @param id
     * @param paymentNum
     * @return
     */
    Long updatePaymentnum(Long id, String paymentNum);

    /**
     * 更新订单状态
     * 
     * @param orderStatus
     * @param paymentNum
     * @return
     */
    int updateOrderStatus(String orderStatus, String paymentNum);

    /**
     * 更新订单状态为已退款
     * 
     * @param orderNum
     * @param appKey
     * @return
     */
    int updateOrderStatusRefund(String orderNum, String appKey);

}
