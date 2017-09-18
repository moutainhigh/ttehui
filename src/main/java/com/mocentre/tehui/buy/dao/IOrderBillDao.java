/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import java.util.List;

import com.mocentre.tehui.buy.model.OrderBill;

/**
 * 类IOrderBillDao.java的实现描述：订单发票
 * 
 * @author sz.gong 2016年11月15日 下午5:19:25
 */
public interface IOrderBillDao {

    /**
     * 按订单号查询
     * 
     * @param orderNum
     * @return
     */
    OrderBill queryByOrder(String orderNum);

    /**
     * 保存
     * 
     * @param orderBillList
     */
    void saveBatchBill(List<OrderBill> orderBillList);

    /**
     * 更新
     * 
     * @param orderNum
     * @param orderBill
     */
    void updateByOrder(String orderNum, OrderBill orderBill);

    /**
     * 新增
     * 
     * @param orderBill
     */
    void saveBill(OrderBill orderBill);

}
