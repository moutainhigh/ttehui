/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service;

import com.mocentre.gift.buy.model.GiftOrderBill;

/**
 * 发票service接口
 * Created by 王雪莹 on 2017/4/11.
 */
public interface IGiftOrderBillService {

    /**
     * 通过订单编号查询
     * 
     * @param orderNum
     * @return
     */
    GiftOrderBill queryBillByOrder(String orderNum);

    /**
     * 通过订单id查询
     * @param orderId
     * @return
     */
    GiftOrderBill queryBillById(Long orderId);

    /**
     * 保存订单信息
     * @param bill
     */
    void saveBill(GiftOrderBill bill);

    /**
     * 更新订单新息
     * @param bill
     */
    void upateBill(GiftOrderBill bill);

    /**
     * 根据订单id删除
     * @param id
     */
    void delById(Long id);
}
