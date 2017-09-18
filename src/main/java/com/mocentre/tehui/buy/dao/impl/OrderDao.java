/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mocentre.tehui.buy.dao.IOrderDao;
import com.mocentre.tehui.buy.enums.OrderStatus;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.core.service.support.paging.PageInfo;

/**
 * 类OrderServiceImpl.java的实现描述：订单Dao实现
 * 
 * @author sz.gong 2016年11月10日 下午3:47:06
 */
@Repository
public class OrderDao extends BaseDao<Order> implements IOrderDao {

    @Override
    public Order queryById(Long id, Long shopId) {
        Assert.notNull(id);
        Assert.notNull(shopId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("shopId", shopId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public List<Order> queryListByType(String orderType, Long typeId) {
        Assert.notNull(orderType);
        Assert.notNull(typeId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderType", orderType);
        paramMap.put("typeId", typeId);
        return super.queryList(paramMap);
    }

    @Override
    public void updateOrderStatus(Long id, String status) {
        Assert.notNull(id);
        Assert.notNull(status);
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus(status);
        super.updateEntity(order);
    }

    @Override
    public void updateOrderStatus(String orderNum, String status) {
        Assert.notNull(orderNum);
        Assert.notNull(status);
        Order order = new Order();
        order.setOrderNum(orderNum);
        order.setOrderStatus(status);
        super.update("Order_update_status", order);
    }

    @Override
    public void updatePaymentNum(Long id, String paymentNum) {
        Assert.notNull(id);
        Assert.notNull(paymentNum);
        Order order = new Order();
        order.setId(id);
        order.setPaymentNum(paymentNum);
        super.updateEntity(order);
    }

    @Override
    public void saveBatchOrder(List<Order> orderList) {
        super.insert("Order_insert_batch", orderList);
    }

    @Override
    public void saveOrder(Order order) {
        super.save(order);
    }

    @Override
    public int logicDelete(String orderNum) {
        Order order = new Order();
        order.setOrderNum(orderNum);
        return super.update("Order_logic_delete", order);
    }

    @Override
    public PageInfo<Order> queryPageInfo(Long customerId, String orderStatus, int start, int length) {
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("column", "orderTime");
        paramMap.put("orderBy", "desc");
        paramMap.put("customerId", customerId);
        paramMap.put("orderStatus", orderStatus);
        paramMap.put("length", length);
        paramMap.put("start", start);
        PageInfo<Order> pageInfo = super.queryPaged(paramMap);
        return pageInfo;
    }

    @Override
    public Order queryByNumCustomer(String orderNum, Long customerId) {
        Assert.notNull(orderNum);
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        paramMap.put("customerId", customerId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public Order queryByNum(String orderNum) {
        Assert.notNull(orderNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        return super.queryUniquely(paramMap);
    }

    @Override
    public void upateById(Order order) {
        Assert.notNull(order.getId());
        super.updateEntity(order);
    }

    @Override
    public Integer getGoodsSum(Long goodsId, Long actGoodsId, Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("orderType", OrderStatus.DEAL_CLOSE.getCodeValue());
        paramMap.put("goodsId", goodsId);
        paramMap.put("actGoodsId", actGoodsId);
        return super.queryUniquely("Order_goods_sum", paramMap);
    }

    @Override
    public List<Order> queryWaitPayList(Integer times) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderStatus", OrderStatus.WAIT_PAY.getCodeValue());
        paramMap.put("times", times);
        return super.queryList(paramMap);
    }

    @Override
    public void updateStatusClose(Integer times) {
        String sqlId = "Order_close";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("closeStatus", OrderStatus.DEAL_CLOSE.getCodeValue());
        paramMap.put("waitStatus", OrderStatus.WAIT_PAY.getCodeValue());
        paramMap.put("times", times);
        super.update(sqlId, paramMap);
    }

    @Override
    public List<Order> getByPayNumCumList(String paymentNum, Long customerId) {
        Assert.notNull(paymentNum);
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("paymentNum", paymentNum);
        paramMap.put("customerId", customerId);
        return super.queryList(paramMap);
    }

    @Override
    public List<Order> getByPayNumList(String paymentNum) {
        Assert.notNull(paymentNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("paymentNum", paymentNum);
        return super.queryList(paramMap);
    }

    @Override
    public ListJsonResult<Order> queryAllDatatablesPage(Requirement require) {
        return super.queryDTPage("OrderAll" + POSTFIX_SELECT, require);
    }

}
