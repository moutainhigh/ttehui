/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service;

import com.mocentre.gift.buy.model.GiftOrderDetail;

import java.util.List;

/**
 * 订单详情service接口
 * Created by 王雪莹 on 2017/4/11.
 */
public interface IGiftOrderDetailService {

    /**
     * 通过订单号，查询订单详情
     * 
     * @param orderNum
     * @return
     */
    List<GiftOrderDetail> queryOrderDetail(String orderNum);

    /**
     * 批量保存订单项
     * @param detailList
     */
    void saveOrderDetail(List<GiftOrderDetail> detailList);

    /**
     * 保存订单项
     * @param detail
     */
    void saveOrderDetail(GiftOrderDetail detail);

    /**
     * 根据订单id逻辑删除订单
     * @param orderId
     */
    void delByOrderId(Long orderId);

    /**
     * 根据订单号逻辑删除订单
     * @param orderNum
     */
    void delByOrderNum(String orderNum);

    /**
     * 更新订单单项
     * @param detail
     */
    void updateOrderDetail(GiftOrderDetail detail);

    /**
     * 根据id删除一个订单项
     *
     * @param id
     * @return
     */
    int removeById(Long id);
}
