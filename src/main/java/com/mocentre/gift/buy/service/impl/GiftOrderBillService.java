/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service.impl;

import com.mocentre.gift.buy.dao.IGiftOrderBillDao;
import com.mocentre.gift.buy.model.GiftOrderBill;
import com.mocentre.gift.buy.service.IGiftOrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发票service
 * Created by 王雪莹 on 2017/4/11.
 */
@Component
public class GiftOrderBillService implements IGiftOrderBillService {

    @Autowired
    private IGiftOrderBillDao orderBillDao;

    @Override
    public GiftOrderBill queryBillByOrder(String orderNum) {
        return orderBillDao.queryByOrderNum(orderNum);
    }

    @Override
    public GiftOrderBill queryBillById(Long orderId) {
        return orderBillDao.queryByOrderId(orderId);
    }

    @Override
    public void saveBill(GiftOrderBill bill) {
        orderBillDao.saveOrderBill(bill);
    }

    @Override
    public void upateBill(GiftOrderBill bill) {
        orderBillDao.updateOrderBill(bill);
    }

    @Override
    public void delById(Long id) {
        orderBillDao.logicDelete(id);
    }

}
