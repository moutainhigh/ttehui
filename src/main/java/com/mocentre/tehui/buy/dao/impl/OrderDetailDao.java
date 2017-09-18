/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mocentre.tehui.buy.dao.IOrderDetailDao;
import com.mocentre.tehui.buy.enums.OrderRefundStatus;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 类OrderDetailDaoImpl.java的实现描述：订单详情接口实现
 * 
 * @author sz.gong 2016年11月15日 下午3:55:20
 */
@Repository
public class OrderDetailDao extends BaseDao<OrderDetail> implements IOrderDetailDao {

    @Override
    public OrderDetail getByDetailNum(String orderDetailNum) {
        Assert.notNull(orderDetailNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderDetailNum", orderDetailNum);
        return super.queryUniquely(paramMap);
    }

    @Override
    public OrderDetail getByNums(String orderNum, String orderDetailNum) {
        Assert.notNull(orderNum);
        Assert.notNull(orderDetailNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        paramMap.put("orderDetailNum", orderDetailNum);
        return super.queryUniquely(paramMap);
    }

    @Override
    public List<OrderDetail> queryByOrder(String orderNum) {
        Assert.notNull(orderNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        return super.queryList(paramMap);
    }

    @Override
    public void saveBatchDetail(List<OrderDetail> detailList) {
        super.insert("OrderDetail_insert_batch", detailList);
    }

    @Override
    public void saveDetail(OrderDetail detail) {
        super.save(detail);
    }

    @Override
    public void updateCoupon(Long id, String couponSn, Long couponMoney) {
        OrderDetail o = new OrderDetail();
        o.setId(id);
        o.setCouponMoney(couponMoney);
        o.setCouponSn(couponSn);
        super.updateEntity(o);
    }

    @Override
    public int updateRefundStatusBack(String orderDetailNum) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderDetailNum", orderDetailNum);
        paramMap.put("refundStatus", OrderRefundStatus.UNREFUND.getCodeValue());
        paramMap.put("oldRefundStatus", OrderRefundStatus.REFUNDING.getCodeValue());
        paramMap.put("refundReason", "");
        paramMap.put("refundDes", "");
        return super.update("OrderDetail_update_refund_ing", paramMap);
    }

    @Override
    public int updateRefundStatusIng(String orderDetailNum, String refundReason, String refundDes) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderDetailNum", orderDetailNum);
        paramMap.put("refundStatus", OrderRefundStatus.REFUNDING.getCodeValue());
        paramMap.put("oldRefundStatus", OrderRefundStatus.UNREFUND.getCodeValue());
        paramMap.put("refundReason", refundReason);
        paramMap.put("refundDes", refundDes);
        paramMap.put("applyTime", new Date());
        return super.update("OrderDetail_update_refund_ing", paramMap);
    }

    @Override
    public int updateRefundStatusSucByDnum(String orderDetailNum, Long refundMoney) {
        OrderDetail o = new OrderDetail();
        o.setOrderDetailNum(orderDetailNum);
        o.setRefundStatus(OrderRefundStatus.REFUNDED.getCodeValue());
        o.setRefundTime(new Date());
        o.setRefundMoney(refundMoney);
        return super.update("OrderDetail_update_refund_suc", o);
    }

    @Override
    public int updateRefundStatusSucByOnum(String orderNum) {
        OrderDetail o = new OrderDetail();
        o.setOrderNum(orderNum);
        o.setRefundStatus(OrderRefundStatus.REFUNDED.getCodeValue());
        o.setRefundTime(new Date());
        return super.update("OrderDetail_update_refund", o);
    }

}
