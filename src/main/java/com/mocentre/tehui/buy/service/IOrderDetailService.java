/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import java.util.List;

import com.mocentre.tehui.buy.model.OrderDetail;

/**
 * 类IOrderDetailService.java的实现描述：订单详情service
 * 
 * @author sz.gong 2016年12月19日 下午4:42:39
 */
public interface IOrderDetailService {

    /**
     * 通过订单号，查询订单详情
     * 
     * @param orderNum
     * @return
     */
    List<OrderDetail> queryOrderDetail(String orderNum);

    /**
     * 通过详情编号，查询订单详情
     * 
     * @param orderDetailNum
     * @return
     */
    OrderDetail getOrderDetail(String orderDetailNum);

    /**
     * 通过订单编号和订单详情编号查询
     * 
     * @param orderNum
     * @param orderDetailNum
     * @return
     */
    OrderDetail getOrderDetail(String orderNum, String orderDetailNum);

    /**
     * 更新订单状态为：无需退款
     * 
     * @param orderDetailNum
     * @return
     */
    int updateOrderRefundStatusBack(String orderDetailNum);

    /**
     * 更新订单退款状态为：退款中
     * 
     * @param orderDetailNum
     * @param refundReason
     * @param refundDes
     * @return
     */
    int updateOrderRefundStatusIng(String orderDetailNum, String refundReason, String refundDes);

}
