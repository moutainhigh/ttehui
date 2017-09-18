/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.buy.dao.IOrderDetailDao;
import com.mocentre.tehui.buy.dao.IOrderRefundDao;
import com.mocentre.tehui.buy.enums.RefundReason;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.service.IOrderDetailService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

/**
 * 类OrderDetailService.java的实现描述：订单详情service实现
 * 
 * @author sz.gong 2016年12月19日 下午4:43:36
 */
@Component
public class OrderDetailService implements IOrderDetailService {

    @Autowired
    private IOrderDetailDao orderDetailDao;
    @Autowired
    private IOrderRefundDao orderRefundDao;

    @Override
    @DataSource(value = "read")
    public List<OrderDetail> queryOrderDetail(String orderNum) {
        return orderDetailDao.queryByOrder(orderNum);
    }

    @Override
    @DataSource(value = "read")
    public OrderDetail getOrderDetail(String orderDetailNum) {
        return orderDetailDao.getByDetailNum(orderDetailNum);
    }

    @Override
    @DataSource(value = "read")
    public OrderDetail getOrderDetail(String orderNum, String orderDetailNum) {
        return orderDetailDao.getByNums(orderNum, orderDetailNum);
    }

    @Override
    @DataSource(value = "write")
    public int updateOrderRefundStatusBack(String orderDetailNum) {
        return orderDetailDao.updateRefundStatusBack(orderDetailNum);
    }

    @Override
    @DataSource(value = "write")
    public int updateOrderRefundStatusIng(String orderDetailNum, String refundReason, String refundDes) {
        RefundReason[] reasons = RefundReason.values();
        boolean exist = false;
        for (RefundReason reason : reasons) {
            if (refundReason.equals(reason.getCodeValue())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            refundReason = "";
        }
        int count = orderDetailDao.updateRefundStatusIng(orderDetailNum, refundReason, refundDes);
        return count;
    }

}
