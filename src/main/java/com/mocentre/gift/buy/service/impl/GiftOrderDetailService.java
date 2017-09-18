/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service.impl;


import com.mocentre.gift.buy.dao.IGiftOrderDetailDao;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.gift.buy.service.IGiftOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 发票详情service
 * Created by 王雪莹 on 2017/4/11.
 */
@Component
public class GiftOrderDetailService implements IGiftOrderDetailService {

    @Autowired
    private IGiftOrderDetailDao orderDetailDao;

    @Override
    public List<GiftOrderDetail> queryOrderDetail(String orderNum) {
        return orderDetailDao.queryByOrder(orderNum);
    }

    @Override
    public void saveOrderDetail(List<GiftOrderDetail> detailList) {
            orderDetailDao.saveOrderDetail(detailList);
    }

    @Override
    public void saveOrderDetail(GiftOrderDetail detail) {
        List<GiftOrderDetail> giftOrderDetailArrayList = new ArrayList<>();
        giftOrderDetailArrayList.add(detail);
        orderDetailDao.saveOrderDetail(giftOrderDetailArrayList);
    }

    @Override
    public void delByOrderId(Long orderId) {
        orderDetailDao.deleteByOrderId(orderId);
    }

    @Override
    public void delByOrderNum(String orderNum) {
        orderDetailDao.deleteByOrderNum(orderNum);
    }

    @Override
    public void updateOrderDetail(GiftOrderDetail detail) {
        orderDetailDao.updateDetail(detail);
    }

    @Override
    public int removeById(Long id) {
        return orderDetailDao.logicDelete(id);
    }

}
