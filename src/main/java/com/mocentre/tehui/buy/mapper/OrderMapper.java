/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.backend.model.LogisticsInstance;
import com.mocentre.tehui.backend.model.OrderDetailInstance;
import com.mocentre.tehui.backend.model.OrderInstance;
import com.mocentre.tehui.buy.enums.OrderStatus;
import com.mocentre.tehui.buy.enums.OrderType;
import com.mocentre.tehui.buy.model.Logistics;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.model.OrderLogistics;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.frontend.model.OrderDetailFTInstance;
import com.mocentre.tehui.frontend.model.OrderFTInstance;
import com.mocentre.tehui.frontend.model.OrderGoodsDetailFTInstance;
import com.mocentre.tehui.frontend.model.OrderGoodsFTInstance;
import com.mocentre.tehui.frontend.model.OrderGrouponFTInstance;
import com.mocentre.tehui.shop.model.Shop;

/**
 * 类OrderMapper.java的实现描述：订单转换
 * 
 * @author sz.gong 2016年12月12日 上午10:57:53
 */
public class OrderMapper {

    public static OrderInstance toOrderInstance(Order order, OrderPay orderPay, Groupon groupon) {
        OrderInstance orderIns = new OrderInstance();
        orderIns.setId(order.getId());
        orderIns.setDealStatus(order.getDealStatus());
        orderIns.setOrderNum(order.getOrderNum());
        orderIns.setOrderStatus(order.getOrderStatus());
        orderIns.setIsDeleted(order.getIsDeleted());
        if (orderPay != null) {
            orderIns.setPayStatus(orderPay.getPayStatus());
            orderIns.setPayType(orderPay.getPayType());
        }
        orderIns.setAddress(order.getAddress());
        orderIns.setRecipient(order.getRecipient());
        orderIns.setTelephone(order.getTelephone());
        orderIns.setTransFee(order.getTransFee());
        if (order.getTotalPrice() > 0) {
            orderIns.setTotalPrice(new BigDecimal(order.getTotalPrice()).divide(new BigDecimal(100), 2,
                    BigDecimal.ROUND_DOWN));
        } else {
            orderIns.setTotalPrice(new BigDecimal(0));
        }
        orderIns.setOrderTime(order.getOrderTime());
        orderIns.setOrderType(order.getOrderType());
        if (OrderType.GROUPON.getCodeValue().equals(order.getOrderType())) {
            orderIns.setGroupStatus(groupon.getGroupStatus());
        }
        return orderIns;
    }

    public static OrderInstance toOrderInstance(Order order, List<OrderDetail> detailList, OrderPay orderPay,
                                                List<Logistics> logisticsList, OrderLogistics Orderlogistics,
                                                OrderBill orderBill, Groupon groupon) {
        OrderInstance orderIns = new OrderInstance();
        BeanCopier cp = BeanCopier.create(Order.class, OrderInstance.class, false);
        cp.copy(order, orderIns, null);
        if (orderBill != null) {
            orderIns.setBillHeader(orderBill.getBillHeader());
            orderIns.setBillRemark(orderBill.getBillRemark());
            orderIns.setBillType(orderBill.getBillType());
        }
        if (orderPay != null) {
            orderIns.setIrspRef(orderPay.getIrspRef());
            orderIns.setPayType(orderPay.getPayType());
            orderIns.setPayStatus(orderPay.getPayStatus());
        }
        if (Orderlogistics != null) {
            orderIns.setCompany(Orderlogistics.getCompany());
            orderIns.setIsSms(Orderlogistics.getIsSms());
            orderIns.setExpNum(Orderlogistics.getExpNum());
            orderIns.setLogisticsCode(Orderlogistics.getLogisticsCode());
        }
        if (groupon != null) {
            orderIns.setGroupStatus(groupon.getGroupStatus());
        }
        List<OrderDetailInstance> detailInsList = new ArrayList<OrderDetailInstance>();
        Long goodsPrice = 0l;
        for (int i = 0; i < detailList.size(); i++) {
            OrderDetail detail = detailList.get(i);
            goodsPrice += detail.getGoodsRealPrice();
            OrderDetailInstance detailIns = OrderDetailMapper.toOrderDetailInstance(detail);
            detailInsList.add(detailIns);
        }
        List<LogisticsInstance> logisticsInstances = new ArrayList<LogisticsInstance>();
        for (int i = 0; i < logisticsList.size(); i++) {
            LogisticsInstance logisticsInstance = LogisticsMapper.toLogisticsInstance(logisticsList.get(i));
            logisticsInstances.add(logisticsInstance);
        }
        if (order.getTotalPrice() > 0) {
            orderIns.setTotalPrice(new BigDecimal(order.getTotalPrice()).divide(new BigDecimal(100)));
        } else {
            orderIns.setTotalPrice(new BigDecimal(0));
        }
        if (goodsPrice > 0) {
            orderIns.setGoodsPrice(new BigDecimal(goodsPrice).divide(new BigDecimal(100)));
        } else {
            orderIns.setGoodsPrice(new BigDecimal(0));
        }
        orderIns.setDetailInstanceList(detailInsList);
        orderIns.setLogisticsInstancesList(logisticsInstances);
        return orderIns;
    }

    public static OrderFTInstance toOrderFTInstance(Order order, List<OrderDetail> detailList, Shop shop) {
        OrderFTInstance orderIns = new OrderFTInstance();
        List<OrderGoodsFTInstance> orderGoodsInsList = new ArrayList<OrderGoodsFTInstance>();
        String orderNum = order.getOrderNum();
        orderIns.setBuyNums(detailList.size());
        if (order.getTransFee() > 0) {
            orderIns.setFeePrice(new BigDecimal(order.getTransFee()).divide(new BigDecimal(100)));
        } else {
            orderIns.setFeePrice(new BigDecimal(0));
        }
        orderIns.setOrderNum(orderNum);
        orderIns.setOrderStatus(order.getOrderStatus());
        orderIns.setOrderStatusStr(OrderStatus.getName(order.getOrderStatus()));
        Long totalPrice = order.getTotalPrice() + order.getTransFee();
        if (totalPrice > 0) {
            orderIns.setTotalPrice(new BigDecimal(totalPrice).divide(new BigDecimal(100)));
        } else {
            orderIns.setTotalPrice(new BigDecimal(0));
        }
        orderIns.setOrderTime(order.getOrderTime());
        orderIns.setOrderType(order.getOrderType());
        for (OrderDetail detail : detailList) {
            OrderGoodsFTInstance orderGoodsIns = new OrderGoodsFTInstance();
            orderGoodsIns.setGoodsStandardDes(detail.getGoodsStandardDes());
            orderGoodsIns.setBuyNum(detail.getGoodsAmount());
            orderGoodsIns.setGoodsName(detail.getGoodsName());
            if (detail.getGoodsRealPrice() > 0) {
                orderGoodsIns.setSellPrice(new BigDecimal(detail.getGoodsRealPrice()).divide(new BigDecimal(100)));
            } else {
                orderGoodsIns.setSellPrice(new BigDecimal(0));
            }
            orderGoodsIns.setShopName(shop.getName());
            orderGoodsIns.setShopId(shop.getId());
            orderGoodsIns.setGoodsImg(detail.getGoodsImg());
            orderGoodsInsList.add(orderGoodsIns);
        }
        orderIns.setOrderGoodsList(orderGoodsInsList);
        return orderIns;
    }

    /**
     * 前台订单详情转换
     * 
     * @param order
     * @param orderDetailList
     * @param shop
     * @return
     */
    public static OrderDetailFTInstance toOrderDetailFTInstance(Order order, List<OrderDetail> orderDetailList,
                                                                OrderLogistics logistics, Shop shop, Groupon groupon,
                                                                List<String> imgList) {
        OrderDetailFTInstance orderDetailIns = new OrderDetailFTInstance();
        orderDetailIns.setOrderType(order.getOrderType());
        orderDetailIns.setShopId(shop.getId());
        orderDetailIns.setShopName(shop.getName());
        if (order.getTransFee() > 0) {
            orderDetailIns.setFeePrice(new BigDecimal(order.getTransFee()).divide(new BigDecimal(100), 2,
                    BigDecimal.ROUND_DOWN));
        } else {
            orderDetailIns.setFeePrice(new BigDecimal(0));
        }
        Long totalPrice = order.getTotalPrice() + order.getTransFee();
        if (totalPrice > 0) {
            orderDetailIns.setRealPrice(new BigDecimal(totalPrice)
                    .divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN));
        } else {
            orderDetailIns.setRealPrice(new BigDecimal(0));
        }
        orderDetailIns.setOrderNum(order.getOrderNum());
        orderDetailIns.setOrderStatus(order.getOrderStatus());
        orderDetailIns.setOrderStatusStr(OrderStatus.getName(order.getOrderStatus()));
        orderDetailIns.setOrderTime(order.getOrderTime());
        orderDetailIns.setRecipient(order.getRecipient());
        orderDetailIns.setTelephone(order.getTelephone());
        orderDetailIns.setAddress(order.getAddress());
        List<OrderGoodsDetailFTInstance> orderGoodsDetailList = new ArrayList<OrderGoodsDetailFTInstance>();
        if (orderDetailList != null) {
            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                OrderGoodsDetailFTInstance orderGoodsDetail = new OrderGoodsDetailFTInstance();
                orderGoodsDetail.setGoodsId(orderDetail.getGoodsId());
                orderGoodsDetail.setGoodsImg(orderDetail.getGoodsImg());
                orderGoodsDetail.setGoodsName(orderDetail.getGoodsName());
                orderGoodsDetail.setGoodsStandardDes(orderDetail.getGoodsStandardDes());
                if (orderDetail.getGoodsRealPrice() > 0) {
                    orderGoodsDetail.setSellPrice(new BigDecimal(orderDetail.getGoodsRealPrice()).divide(
                            new BigDecimal(100), 2, BigDecimal.ROUND_DOWN));
                } else {
                    orderGoodsDetail.setSellPrice(new BigDecimal(0));
                }
                orderGoodsDetail.setBuyNum(orderDetail.getGoodsAmount());
                orderGoodsDetail.setRefundStatus(orderDetail.getRefundStatus());
                orderGoodsDetail.setOrderDetailNum(orderDetail.getOrderDetailNum());
                orderGoodsDetailList.add(orderGoodsDetail);
            }
        }
        Long disPrice = order.getCouponMoney();
        if (disPrice != null) {
            orderDetailIns.setDisPrice(new BigDecimal(disPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN));
        } else {
            orderDetailIns.setDisPrice(new BigDecimal(0));
        }
        orderDetailIns.setOrderGoodsDetailList(orderGoodsDetailList);
        if (logistics != null) {
            orderDetailIns.setLogisticsNum(logistics.getExpNum());
            orderDetailIns.setLogisticsCode(logistics.getLogisticsCode());
        }
        if (OrderType.GROUPON.getCodeValue().equals(order.getOrderType())) {
            Integer takeNum = groupon.getTakeNum();
            Integer grouponNum = groupon.getGrouponNum();
            if (takeNum.intValue() == grouponNum.intValue()) {
                orderDetailIns.setIsGroupon(true);
                orderDetailIns.setGrouponStr("已成团");
            } else {
                orderDetailIns.setIsGroupon(false);
                orderDetailIns.setGrouponStr("未成团");
                OrderGrouponFTInstance grouponInfo = new OrderGrouponFTInstance();
                grouponInfo.setGrouponNum(grouponNum);
                grouponInfo.setRestNum(grouponNum - takeNum);
                grouponInfo.setOpenTime(groupon.getOpenTime());
                grouponInfo.setCloseTime(groupon.getCloseTime());
                grouponInfo.setEndTime(groupon.getEndTime());
                grouponInfo.setShowImgs(imgList);
                grouponInfo.setGrouponId(groupon.getId());
                orderDetailIns.setGrouponInfo(grouponInfo);
            }
            orderDetailIns.setActGoodsId(groupon.getActGoodsId());
        } else if (OrderType.SECKILL.getCodeValue().equals(order.getOrderType())) {
            orderDetailIns.setActGoodsId(order.getTypeId());
        }
        return orderDetailIns;
    }
}
