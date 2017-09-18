/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.mapper;

import java.math.BigDecimal;

import com.mocentre.tehui.backend.model.OrderDetailInstance;
import com.mocentre.tehui.buy.enums.RefundReason;
import com.mocentre.tehui.buy.model.OrderDetail;

/**
 * 类OrderDetailMapper.java的实现描述：订单详情转换
 * 
 * @author sz.gong 2016年12月12日 上午11:35:50
 */
public class OrderDetailMapper {

    public static OrderDetailInstance toOrderDetailInstance(OrderDetail detail) {
        OrderDetailInstance detailIns = new OrderDetailInstance();
        detailIns.setOrderDetailNum(detail.getOrderDetailNum());
        detailIns.setGoodsId(detail.getGoodsId());
        detailIns.setGoodsAmount(detail.getGoodsAmount());
        detailIns.setGoodsImg(detail.getGoodsImg());
        detailIns.setGoodsName(detail.getGoodsName());
        if (detail.getGoodsPrice() > 0) {
            detailIns.setGoodsPrice(new BigDecimal(detail.getGoodsPrice()).divide(new BigDecimal(100)));
        } else {
            detailIns.setGoodsPrice(new BigDecimal(0));
        }
        if (detail.getGoodsRealPrice() > 0) {
            detailIns.setGoodsRealPrice(new BigDecimal(detail.getGoodsRealPrice()).divide(new BigDecimal(100)));
        } else {
            detailIns.setGoodsRealPrice(new BigDecimal(0));
        }
        detailIns.setRefundReasonDes(RefundReason.getName(detail.getRefundReason()));
        detailIns.setRefundDes(detail.getRefundDes());
        detailIns.setApplyTime(detail.getApplyTime());
        detailIns.setRefundStatus(detail.getRefundStatus());
        detailIns.setRefundTime(detail.getRefundTime());
        detailIns.setGoodsStandard(detail.getGoodsStandard());
        detailIns.setGoodsStandardDes(detail.getGoodsStandardDes());
        detailIns.setCouponSn(detail.getCouponSn());
        if (detail.getCouponMoney() != null) {
            detailIns.setCouponMoney(new BigDecimal(detail.getCouponMoney()).divide(new BigDecimal(100)));
        }
        return detailIns;
    }
}
