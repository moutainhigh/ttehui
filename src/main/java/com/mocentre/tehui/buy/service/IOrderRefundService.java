/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import java.util.List;

import com.mocentre.tehui.buy.model.OrderRefund;

/**
 * 类IOrderRefundService.java的实现描述：订单退款service接口
 * 
 * @author sz.gong 2017年3月17日 下午4:12:31
 */
public interface IOrderRefundService {

    /**
     * 新建
     * 
     * @param orderRefund
     */
    void saveOrderRefund(OrderRefund orderRefund);

    /**
     * 批量新建
     * 
     * @param orderRefundList
     */
    void saveOrderRefundBatch(List<OrderRefund> orderRefundList);

}
