/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service;


import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.buy.model.GiftOrder;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 礼品订单service接口
 * Created by 王雪莹 on 2017/4/10.
 */
public interface IGiftOrderService {

    /**
     * 查询订单分页列表
     * @param require
     * @return
     */
    ListJsonResult<GiftOrder> queryOrderPage(Requirement require);

    /**
     * 查询订单
     * @param id 订单id
     * @return
     */
    GiftOrder queryOrder(Long id);

    /**
     * 查询订单
     * @param orderNum 订单num
     * @return
     */
    GiftOrder queryOrder(String orderNum);

    /**
     * 新增订单
     * @param order 订单
     * @param detail 订单详情
     */
    void saveOrder(GiftOrder order, GiftOrderDetail detail);

    /**
     * 新增订单
     * @param order 订单
     */
    void saveOrder(GiftOrder order);

    /**
     * 逻辑删除订单
     * @param id
     * @return
     */
    Integer delById(Long id);

    /**
     * 逻辑删除订单
     * @param orderNum
     * @return
     */
    Integer delByOrderNum(String orderNum);

    /**
     *
     * @param giftOrder
     */
    void updateOrder(GiftOrder giftOrder);

    /**
     *  前台分页
     * @param customerId
     * @param start
     * @param length
     * @return
     */
    PageInfo<GiftOrder> queryOrderPage(Long customerId, Integer start, Integer length);


//    /**
//     * 修改订单（订单详情，发票，支付）
//     * @param order
//     * @param orderDetailList
//     * @param orderBill
//     */
//    void updateOrder(GiftOrder order, List<GiftOrderDetail> orderDetailList, GiftOrderBill orderBill);
//
//    /**
//     * 更新订单状态为已发货；更新支付回调状态和支付信息
//     * @param orderNumList 订单编号集合
//     * @param paymentNum 支付编号
//     */
//    void updateOrderAndPay(List<String> orderNumList, String paymentNum);
//
//    /**
//     * 更新订单状态为：交易完成
//     * @param id
//     */
//    void updateOrderDeal(Long id);
//
//    /**
//     * 查询订单
//     * @param customerId
//     * @param orderNum
//     * @return
//     */
//    GiftOrder queryOrderByNumCustomer(Long customerId, String orderNum);
//


    }
