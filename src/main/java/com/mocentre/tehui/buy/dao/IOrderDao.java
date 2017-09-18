/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 类IOrderService.java的实现描述：订单dao接口
 * 
 * @author sz.gong 2016年11月10日 下午3:38:26
 */
public interface IOrderDao {

    ListJsonResult<Order> queryDatatablesPage(Requirement require);

    /**
     * 通过订单id和店铺id查询订单
     * 
     * @param id 订单id
     * @param shopId 店铺id
     * @return
     */
    Order queryById(Long id, Long shopId);

    /**
     * 通过订单类型查询
     * 
     * @param orderType 订单类型
     * @param typeId 类型id
     * @return
     */
    List<Order> queryListByType(String orderType, Long typeId);

    /**
     * 更新订单状态
     * 
     * @param id
     * @param status
     */
    void updateOrderStatus(Long id, String status);

    /**
     * 更新订单状态
     * 
     * @param orderNum
     * @param status
     */
    void updateOrderStatus(String orderNum, String status);

    /**
     * 更新支付编码
     * 
     * @param id
     * @param paymentNum
     */
    void updatePaymentNum(Long id, String paymentNum);

    /**
     * 批量新增订单
     * 
     * @param orderList
     */
    void saveBatchOrder(List<Order> orderList);

    /**
     * 新增订单
     * 
     * @param order
     */
    void saveOrder(Order order);

    /**
     * 逻辑删除
     * 
     * @param orderNum
     * @return
     */
    int logicDelete(String orderNum);

    /**
     * 订单分页查询
     * 
     * @param customerId 用户id
     * @param orderStatus 订单状态
     * @return
     */
    PageInfo<Order> queryPageInfo(Long customerId, String orderStatus, int start, int length);

    /**
     * 查询订单
     * 
     * @param orderNum 订单编号
     * @param customerId 用户id
     * @return
     */
    Order queryByNumCustomer(String orderNum, Long customerId);

    /**
     * 按订单编号查询
     * 
     * @param orderNum
     * @return
     */
    Order queryByNum(String orderNum);

    /**
     * 修改订单
     * 
     * @param order
     */
    void upateById(Order order);

    /**
     * 查询已购买的数量
     * 
     * @param goodsId
     * @param actGoodsId
     * @param customerId
     * @return
     */
    Integer getGoodsSum(Long goodsId, Long actGoodsId, Long customerId);

    /**
     * 查询指定内时间未付款订单
     * 
     * @param orderStatus
     * @param times
     * @return
     */
    List<Order> queryWaitPayList(Integer times);

    /**
     * 将未支付且超出times秒的订单更新状态为：关闭
     * 
     * @param times 秒
     */
    void updateStatusClose(Integer times);

    List<Order> queryList(Map<String, Object> paramMap);

    /**
     * 查询订单列表
     * 
     * @param paymentNum 支付编号
     * @param customerId 用户id
     * @return
     */
    List<Order> getByPayNumCumList(String paymentNum, Long customerId);

    /**
     * 查询订单列表
     * 
     * @param paymentNum
     * @return
     */
    List<Order> getByPayNumList(String paymentNum);

    ListJsonResult<Order> queryAllDatatablesPage(Requirement require);
}
