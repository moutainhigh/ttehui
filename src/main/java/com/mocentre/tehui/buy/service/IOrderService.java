/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.model.GrouponDetail;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.buy.model.OrderRefund;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * 类IOrderService.java的实现描述：订单service接口
 * 
 * @author sz.gong 2016年11月10日 下午3:48:50
 */
public interface IOrderService {

    /**
     * 查询订单分页列表
     * 
     * @param require
     * @return
     */
    ListJsonResult<Order> queryOrderPage(Requirement require);

    /**
     * 通过订单类型和类型id查询订单
     * 
     * @param orderType 订单类型
     * @param typeId 类型id
     * @return
     */
    List<Order> queryOrderListType(String orderType, Long typeId);

    /**
     * 查询订单
     * 
     * @param id 订单id
     * @param shopId 店铺id
     * @return
     */
    Order queryOrder(Long id, Long shopId);

    /**
     * 物流发货
     * 
     * @param id
     * @param compay
     * @param expNum
     * @param isSms
     * @param logisticsCode
     */
    void deliverOrder(Long id, String compay, String expNum, String isSms, String logisticsCode);

    /**
     * 编辑物流
     * 
     * @param id
     * @param compay
     * @param expNum
     * @param isSms
     * @param logisticsCode
     */
    void editDeliverOrder(Long id, String compay, String expNum, String isSms, String logisticsCode);

    /**
     * 批量新增订单（包含订单，订单详情，订单支付，订单发票），减掉库存。只针对普通商品，不针对活动商品。
     * 
     * @param orderList 订单
     * @param detailList 订单详情
     * @param orderBillList 订单发票
     * @param orderPayList 订单支付
     * @param storageList 商品库存
     * @param comefrom 来源（1购物车. 2.商品）
     * @param customerId 用户id
     * @param couponSn 优惠券编码
     */
    Boolean saveOrder(List<Order> orderList, List<OrderDetail> detailList, List<OrderBill> orderBillList,
                      List<OrderPay> orderPayList, List<GoodsStorage> storageList, String comefrom, Long customerId,
                      String couponSn);

    /**
     * 新增订单（包含订单，订单详情，订单支付，如果是团购商品，增加参团），减掉库存。普通商品与活动商品都适合。
     * 
     * @param order 订单
     * @param detail 订单详情
     * @param orderPay 订单支付
     * @param storage 商品库存对象
     * @param groupon 团购对象
     * @param grouponDetail 参团对象
     * @param isTake 是否参团
     */
    Boolean saveOrder(Order order, OrderDetail detail, OrderPay orderPay, GoodsStorage storage, Groupon groupon,
                      GrouponDetail grouponDetail, Boolean isTake);

    /**
     * 修改订单（订单详情，发票，支付）
     * 
     * @param order
     * @param orderDetailList
     * @param orderBill
     * @param orderPay
     */
    void updateOrder(Order order, List<OrderDetail> orderDetailList, OrderBill orderBill, OrderPay orderPay);

    /**
     * 更新订单状态为已付款；更新支付回调状态和支付信息
     * 
     * @param orderPay 订单支付对象
     * @param paymentNum 支付编号
     */
    void updateOrderAndPay(OrderPay orderPay, String paymentNum);

    /**
     * 更新订单状态为已付款；更新支付回调状态和支付信息；若是开团，则更新开团状态；若使用优惠券，则将优惠券置为已使用
     * 
     * @param orderNum
     * @param orderPay
     */
    void updateOrderAndPay(String orderNum, OrderPay orderPay);

    /**
     * 更新支付编码，新增支付
     * 
     * @param id
     * @param orderPay
     */
    void updateOrderAndPay(Long id, OrderPay orderPay);

    /**
     * 更新订单状态为：交易完成
     * 
     * @param id
     */
    void updateOrderDeal(Long id);

    /**
     * 订单分页列表
     * 
     * @param customerId 用户id
     * @param type 类型
     * @param start
     * @param length
     * @return
     */
    PageInfo<Order> queryOrderPage(Long customerId, String type, Integer start, Integer length);

    /**
     * 查询订单
     * 
     * @param customerId
     * @param orderNum
     * @return
     */
    Order queryOrderByNumCustomer(Long customerId, String orderNum);

    /**
     * 查询订单
     * 
     * @param orderNum
     * @return
     */
    Order queryOrderByNum(String orderNum);

    /**
     * 查询支付成功订单
     * 
     * @param shopId 店铺id
     * @param beginTime 下单开始时间
     * @param endTime 下单结束时间
     * @return
     */
    List<Order> getPayOrderList(Long shopId, String beginTime, String endTime);

    /**
     * 查询已购买的商品数量
     * 
     * @param goodsId
     * @param actGoodsId
     * @param customerId
     * @return
     */
    Integer getOrderGoodsSum(Long goodsId, Long actGoodsId, Long customerId);

    /**
     * 查询指定时间内待支付订单
     * 
     * @param times
     * @return
     */
    List<Order> queryOrderListWaitPay(Integer times);

    /**
     * 团购自动退款操作。增加到退款表，更新商品详情退款状态，更新团购处理状态
     * 
     * @param orderNum
     * @param grouponId
     * @param orderRefundList
     */
    void orderRefundPay(String orderNum, Long grouponId, List<OrderRefund> orderRefundList);

    /**
     * 逻辑删除订单
     * 
     * @param orderNum
     * @return
     */
    int logicDeleteOrder(String orderNum);

    /**
     * 关闭订单，退货到缓存和数据库
     * 
     * @param orderList 订单集合
     * @param orderDetailMap 订单详情集合
     */
    void updateOrderCloseAndToCache(List<Order> orderList, Map<String, List<OrderDetail>> orderDetailMap);

    /**
     * 更新退款状态，新增退款记录
     * 
     * @param orderNum
     * @param orderDetailNum
     * @param orderRefund
     */
    void updateOrderRefund(String orderNum, String orderDetailNum, OrderRefund orderRefund);

    /**
     * 查询订单列表
     * 
     * @param customerId 用户滴
     * @param paymentNum 支付编号
     * @return
     */
    List<Order> getOrderList(Long customerId, String paymentNum);

}
