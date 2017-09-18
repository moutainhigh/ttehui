/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service.impl;


import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.buy.dao.IGiftOrderDao;
import com.mocentre.gift.buy.model.GiftOrder;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.gift.buy.service.IGiftOrderDetailService;
import com.mocentre.gift.buy.service.IGiftOrderService;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单service
 * Created by 王雪莹 on 2017/4/11.
 */
@Component
public class GiftOrderService implements IGiftOrderService {

    @Autowired
    private IGiftOrderDao orderDao;
    @Autowired
    private IGiftOrderDetailService orderDetailService;


    @Override
    public ListJsonResult<GiftOrder> queryOrderPage(Requirement require) {
        ListJsonResult<GiftOrder> pageInfo = orderDao.queryDatatablesPage(require);
        return pageInfo;
    }

    @Override
    public GiftOrder queryOrder(Long id) {
        GiftOrder order = orderDao.queryById(id);
        return order;
    }

    @Override
    public GiftOrder queryOrder(String orderNum) {
        GiftOrder order = orderDao.queryByNum(orderNum);
        return order;
    }

    @Override
    public void saveOrder(GiftOrder order, GiftOrderDetail detail) {
        saveOrder(order);
        orderDetailService.saveOrderDetail(detail);
    }

    @Override
    public void saveOrder(GiftOrder order) {
        orderDao.saveOrder(order);
    }

    @Override
    public Integer delById(Long id) {
        orderDetailService.delByOrderId(id);
        return orderDao.logicDelete(id);
    }

    @Override
    public Integer delByOrderNum(String orderNum) {
        orderDetailService.delByOrderNum(orderNum);
        return orderDao.logicDelete(orderNum);
    }

    @Override
    public void updateOrder(GiftOrder giftOrder) {
        if(giftOrder.getId() == null && giftOrder.getExpNum() == null){
            return;
        }
        if(giftOrder.getId() == null ){
            GiftOrder queryOrder = queryOrder(giftOrder.getOrderNum());
            giftOrder.setId(queryOrder.getId());
        }
        orderDao.upateById(giftOrder);
    }

    @Override
    public PageInfo<GiftOrder> queryOrderPage(Long customerId, Integer start, Integer length) {
        return orderDao.queryPageInfo(customerId,  start, length);
    }

//    @Override
//    public void updateOrder(GiftOrder order, List<GiftOrderDetail> orderDetailList, GiftOrderBill orderBill) {
//
//    }
//
//    @Override
//    public void updateOrderAndPay(List<String> orderNumList, String paymentNum) {
//
//    }
//
//    @Override
//    public void updateOrderDeal(Long id) {
//
//    }
//
//    @Override
//    public GiftOrder queryOrderByNumCustomer(Long customerId, String orderNum) {
//        return null;
//    }
//
//    @Override
//    public Integer logicDeleteOrder(String orderNum) {
//        return null;
//    }
}
