/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.dao.IGrouponDao;
import com.mocentre.tehui.act.dao.IGrouponDetailDao;
import com.mocentre.tehui.act.enums.GroupStatus;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.model.GrouponDetail;
import com.mocentre.tehui.buy.dao.IOrderBillDao;
import com.mocentre.tehui.buy.dao.IOrderDao;
import com.mocentre.tehui.buy.dao.IOrderDetailDao;
import com.mocentre.tehui.buy.dao.IOrderLogisticsDao;
import com.mocentre.tehui.buy.dao.IOrderPayDao;
import com.mocentre.tehui.buy.dao.IOrderRefundDao;
import com.mocentre.tehui.buy.dao.IShoppingCartDao;
import com.mocentre.tehui.buy.enums.OrderRefundStatus;
import com.mocentre.tehui.buy.enums.OrderStatus;
import com.mocentre.tehui.buy.enums.OrderType;
import com.mocentre.tehui.buy.enums.PayStatus;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.buy.model.OrderRefund;
import com.mocentre.tehui.buy.service.IOrderService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.ICouponDetailDao;
import com.mocentre.tehui.goods.dao.IGoodsDao;
import com.mocentre.tehui.goods.dao.IGoodsStorageDao;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.job.queue.StorageMsgVo;
import com.mocentre.tehui.job.queue.StorageQueue;
import com.mocentre.tehui.job.queue.TokenQueue;
import com.mocentre.tehui.ps.dao.ICustomerAddressDao;

/**
 * 类OrderServiceImpl.java的实现描述：订单service实现
 * 
 * @author sz.gong 2016年11月10日 下午3:50:07
 */
@Component
public class OrderService implements IOrderService {

    @Autowired
    private IOrderDao                  orderDao;
    @Autowired
    private IOrderPayDao               orderPayDao;
    @Autowired
    private IOrderDetailDao            orderDetailDao;
    @Autowired
    private IOrderBillDao              orderBillDao;
    @Autowired
    private IOrderLogisticsDao         orderLogisticsDao;
    @Autowired
    private ICustomerAddressDao        customerAddressDao;
    @Autowired
    private IGoodsStorageDao           goodsStorageDao;
    @Autowired
    private IGoodsDao                  goodsDao;
    @Autowired
    private IGrouponDetailDao          grouponDetailDao;
    @Autowired
    private IGrouponDao                grouponDao;
    @Autowired
    private IOrderRefundDao            orderRefundDao;
    @Autowired
    private IShoppingCartDao           shopCartDao;
    @Autowired
    private ICouponDetailDao           couponDetailDao;
    @Autowired
    private StorageQueue<StorageMsgVo> storageQueue;
    @Autowired
    private TokenQueue                 tokenQueue;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Order> queryOrderPage(Requirement require) {
        ListJsonResult<Order> pageInfo = orderDao.queryAllDatatablesPage(require);
        return pageInfo;
    }

    @Override
    @DataSource(value = "read")
    public Order queryOrder(Long id, Long shopId) {
        Order order = orderDao.queryById(id, shopId);
        return order;
    }

    @Override
    @DataSource(value = "read")
    public List<Order> queryOrderListType(String orderType, Long typeId) {
        return orderDao.queryListByType(orderType, typeId);
    }

    @Override
    @DataSource(value = "write")
    public void deliverOrder(Long id, String compay, String expNum, String isSms, String logisticsCode) {
        orderLogisticsDao.save(id, compay, expNum, isSms, logisticsCode);
        orderDao.updateOrderStatus(id, OrderStatus.WAIT_ACCEPT.getCodeValue());
    }

    @Override
    @DataSource(value = "write")
    public void editDeliverOrder(Long id, String compay, String expNum, String isSms, String logisticsCode) {
        orderLogisticsDao.updateByOrderId(id, compay, expNum, isSms, logisticsCode);
    }

    @Override
    @DataSource(value = "write")
    public Boolean saveOrder(List<Order> orderList, List<OrderDetail> detailList, List<OrderBill> orderBillList,
                             List<OrderPay> orderPayList, List<GoodsStorage> storageList, String comefrom,
                             Long customerId, String couponSn) {
        Long allTotalPrice = 0l;
        for (Order order : orderList) {
            allTotalPrice += order.getTotalPrice();
        }
        if (allTotalPrice <= 0) {//0元订单。若使用优惠券，则将优惠券置为已使用
            for (Order order : orderList) {
                order.setOrderStatus(OrderStatus.WAIT_SEND.getCodeValue());
            }
            for (OrderPay orderPay : orderPayList) {
                orderPay.setPayStatus(PayStatus.SUCCESS.getCodeValue());
            }
            //优惠券置为已使用
            if (StringUtils.isNotBlank(couponSn)) {
                couponDetailDao.updateStatusUse(couponSn, customerId);
            }
        }
        orderDao.saveBatchOrder(orderList);
        orderDetailDao.saveBatchDetail(detailList);
        if (orderBillList != null) {
            orderBillDao.saveBatchBill(orderBillList);
        }
        orderPayDao.saveBatchPay(orderPayList);
        //针对来源购物车，需要删除掉
        if ("1".equals(comefrom)) {
            for (OrderDetail orderDetail : detailList) {
                Long goodsId = orderDetail.getGoodsId();
                String goodsSku = orderDetail.getGoodsStandard();
                Long actGoodsId = orderDetail.getGoodsActGoodsId();
                shopCartDao.deleteByCustomerGoods(customerId, goodsId, goodsSku, actGoodsId);
            }
        }
        for (OrderDetail detail : detailList) {
            Long goodsId = detail.getGoodsId();
            Long actGoodsId = detail.getGoodsActGoodsId();
            String goodsSku = detail.getGoodsStandard();
            Integer buyNum = detail.getGoodsAmount();
            //减掉库存redis
            goodsStorageDao.updateStockNumToCache(goodsId, goodsSku, actGoodsId, -buyNum.longValue());
            //存入同步库存队列
            StorageMsgVo storeMsg = new StorageMsgVo();
            storeMsg.setGoodsId(goodsId);
            storeMsg.setStandardCode(goodsSku);
            storeMsg.setSubGoodsId(actGoodsId);
            storeMsg.setNeedNum(-buyNum);
            LoggerUtil.tehuiLog.info("goodsId:" + goodsId + ",goodsSku:" + goodsSku + ",actGoodsId:" + actGoodsId
                    + ",goodsNum:" + -buyNum);
            storageQueue.pushFromTail(storeMsg);
        }
        return true;
    }

    @Override
    @DataSource(value = "write")
    public Boolean saveOrder(Order order, OrderDetail detail, OrderPay orderPay, GoodsStorage storage, Groupon groupon,
                             GrouponDetail grouponDetail, Boolean isTake) {
        Long goodsId = detail.getGoodsId();
        String goodsSku = detail.getGoodsStandard();
        Long actGoodsId = detail.getGoodsActGoodsId();
        if (goodsId == null || StringUtils.isBlank(goodsSku) || actGoodsId == null) {
            return false;
        }
        Integer buyNum = detail.getGoodsAmount();
        orderDao.saveOrder(order);
        orderDetailDao.saveDetail(detail);
        orderPayDao.savePay(orderPay);
        //减掉库存redis
        goodsStorageDao.updateStockNumToCache(goodsId, goodsSku, actGoodsId, -buyNum.longValue());
        //存入同步库存队列
        StorageMsgVo storeMsg = new StorageMsgVo();
        storeMsg.setGoodsId(storage.getGoodsId());
        storeMsg.setStandardCode(storage.getStandardCode());
        storeMsg.setSubGoodsId(storage.getSubGoodsId());
        storeMsg.setNeedNum(-buyNum);
        storageQueue.pushFromTail(storeMsg);
        //若团购商品
        if (groupon != null) {
            if (grouponDetail != null) {
                grouponDetailDao.saveEntity(grouponDetail);
            }
            if (isTake) {
                Long grouponId = grouponDetail.getGrouponId();
                String groupStatus = null;
                if (groupon.getTakeNum() + 1 == groupon.getGrouponNum()) {
                    groupStatus = GroupStatus.GROUPING.getCodeValue();
                } else {
                    groupStatus = GroupStatus.UNGROUP.getCodeValue();
                }
                grouponDao.updateStatusAndNum(grouponId, groupStatus, 1);
            }
        }
        return true;
    }

    @Override
    @DataSource(value = "write")
    public void updateOrder(Order order, List<OrderDetail> orderDetailList, OrderBill orderBill, OrderPay orderPay) {
        orderDao.upateById(order);
        if (orderDetailList != null) {
            for (OrderDetail orderDetail : orderDetailList) {
                if (StringUtils.isNotBlank(orderDetail.getCouponSn())) {
                    orderDetailDao.updateCoupon(orderDetail.getId(), orderDetail.getCouponSn(),
                            orderDetail.getCouponMoney());
                }
            }
        }
        String orderNum = order.getOrderNum();
        OrderBill oldOrderBill = orderBillDao.queryByOrder(orderNum);
        if (oldOrderBill == null) {
            if (orderBill != null) {
                orderBillDao.saveBill(orderBill);
            }
        } else {
            if (orderBill != null) {
                orderBillDao.updateByOrder(orderNum, orderBill);
            }
        }
        OrderPay oldOrderPay = orderPayDao.queryByOrder(orderNum);
        if (oldOrderPay == null) {
            orderPayDao.savePay(orderPay);
        } else {
            orderPayDao.updateByOrdernum(orderNum, orderPay);
        }
    }

    @Override
    @DataSource(value = "write")
    public void updateOrderAndPay(OrderPay orderPay, String paymentNum) {
        //使用过优惠券
        String couponSn = null;
        Long customerId = null;
        List<Order> orderList = orderDao.getByPayNumList(paymentNum);
        if (orderList != null && !orderList.isEmpty()) {
            for (Order order : orderList) {
                if (StringUtils.isNotBlank(order.getCouponSn())) {
                    couponSn = order.getCouponSn();
                    customerId = order.getCustomerId();
                    break;
                }
            }
        }
        if (StringUtils.isNotBlank(couponSn)) {
            couponDetailDao.updateStatusUse(couponSn, customerId);
        }
        //更新订单状态
        for (Order order : orderList) {
            String orderNum = order.getOrderNum();
            orderDao.updateOrderStatus(orderNum, OrderStatus.WAIT_SEND.getCodeValue());
        }
        //更新支付状态
        orderPayDao.updateByPaynum(paymentNum, orderPay);
    }

    @Override
    @DataSource(value = "write")
    public void updateOrderAndPay(String orderNum, OrderPay orderPay) {
        Order order = orderDao.queryByNum(orderNum);
        String paymentNum = order.getPaymentNum();
        //更新开团支付状态
        if (order != null) {
            String orderType = order.getOrderType();
            Long typeId = order.getTypeId();
            Long cumrId = order.getCustomerId();
            if (OrderType.GROUPON.getCodeValue().equals(orderType)) {
                Groupon groupon = grouponDao.get(typeId);
                Long userId = groupon.getOpenUserId();
                String groupStatus = null;
                if (groupon.getTakeNum() == groupon.getGrouponNum()) {
                    groupStatus = GroupStatus.GROUPED.getCodeValue();
                }
                //更新团购状态和支付状态
                if (cumrId == userId) {
                    grouponDao.updateStatusAndPay(typeId, groupStatus);
                } else {
                    grouponDao.updateStatus(typeId, groupStatus);
                }
                //参与团购支付状态
                grouponDetailDao.updateGrouponDetailPay(typeId, cumrId);
            }
        }
        orderDao.updateOrderStatus(orderNum, OrderStatus.WAIT_SEND.getCodeValue());
        orderPayDao.updateByPaynum(paymentNum, orderPay);
    }

    @Override
    @DataSource(value = "write")
    public void updateOrderAndPay(Long id, OrderPay orderPay) {
        String paymentNum = orderPay.getPaymentNum();
        orderDao.updatePaymentNum(id, paymentNum);
        orderPayDao.savePay(orderPay);
    }

    @Override
    @DataSource(value = "write")
    public void updateOrderDeal(Long id) {
        orderDao.updateOrderStatus(id, OrderStatus.DEAL_SUC.getCodeValue());
    }

    @Override
    @DataSource(value = "read")
    public PageInfo<Order> queryOrderPage(Long customerId, String type, Integer start, Integer length) {
        String orderStatus = null;
        if ("1".equals(type)) {
            orderStatus = OrderStatus.WAIT_PAY.getCodeValue();
        } else if ("2".equals(type)) {
            orderStatus = OrderStatus.WAIT_SEND.getCodeValue();
        } else if ("3".equals(type)) {
            orderStatus = OrderStatus.WAIT_ACCEPT.getCodeValue();
        } else if ("4".equals(type)) {
            orderStatus = OrderStatus.DEAL_SUC.getCodeValue();
        }
        return orderDao.queryPageInfo(customerId, orderStatus, start, length);
    }

    @Override
    @DataSource(value = "read")
    public Order queryOrderByNumCustomer(Long customerId, String orderNum) {
        return orderDao.queryByNumCustomer(orderNum, customerId);
    }

    @Override
    @DataSource(value = "read")
    public Order queryOrderByNum(String orderNum) {
        return orderDao.queryByNum(orderNum);
    }

    @Override
    @DataSource(value = "read")
    public Integer getOrderGoodsSum(Long goodsId, Long actGoodsId, Long customerId) {
        return orderDao.getGoodsSum(goodsId, actGoodsId, customerId);
    }

    @Override
    @DataSource(value = "read")
    public List<Order> queryOrderListWaitPay(Integer times) {
        return orderDao.queryWaitPayList(times);
    }

    @Override
    @DataSource(value = "write")
    public void orderRefundPay(String orderNum, Long grouponId, List<OrderRefund> orderRefundList) {
        orderDetailDao.updateRefundStatusSucByOnum(orderNum);
        for (OrderRefund orderRefund : orderRefundList) {
            orderRefundDao.saveEntity(orderRefund);
        }
        grouponDao.updateIsDeal(grouponId);
    }

    @Override
    @DataSource(value = "write")
    public int logicDeleteOrder(String orderNum) {
        return orderDao.logicDelete(orderNum);
    }

    @Override
    @DataSource(value = "write")
    public void updateOrderCloseAndToCache(List<Order> orderList, Map<String, List<OrderDetail>> orderDetailMap) {
        if (orderList != null && orderList.size() > 0 && orderDetailMap != null && orderDetailMap.size() > 0) {
            for (Order order : orderList) {
                String orderNum = order.getOrderNum();
                String orderType = order.getOrderType();
                Long typeId = order.getTypeId();
                if (OrderType.GROUPON.getCodeValue().equals(orderType)) {//对于团购，减一人数
                    grouponDao.updateStatusAndNum(typeId, GroupStatus.UNGROUP.getCodeValue(), -1);
                }
                orderDao.updateOrderStatus(order.getOrderNum(), OrderStatus.DEAL_CLOSE.getCodeValue());
                List<OrderDetail> orderDetailList = orderDetailMap.get(orderNum);
                if (orderDetailList != null && orderDetailList.size() > 0) {
                    for (OrderDetail detail : orderDetailList) {
                        Long goodsId = detail.getGoodsId();
                        String goodsSku = detail.getGoodsStandard();
                        Long actGoodsId = detail.getGoodsActGoodsId();
                        Integer buyNum = detail.getGoodsAmount();
                        goodsStorageDao.updateStockNumAndCache(goodsId, goodsSku, actGoodsId, buyNum.longValue());
                        if (OrderType.SECKILL.getCodeValue().equals(orderType)) {//针对秒杀，增加一个令牌
                            String key = goodsId + goodsSku + actGoodsId;
                            tokenQueue.pushFromHead(key, buyNum);
                        }
                    }
                }
            }

        }
    }

    @Override
    @DataSource(value = "write")
    public void updateOrderRefund(String orderNum, String orderDetailNum, OrderRefund orderRefund) {
        List<OrderDetail> detailList = orderDetailDao.queryByOrder(orderNum);
        boolean isRefunded = true;
        for (OrderDetail orderDetail : detailList) {
            String refundStatus = orderDetail.getRefundStatus();
            String mOrderDetailNum = orderDetail.getOrderDetailNum();
            if (!mOrderDetailNum.equals(orderDetailNum)) {
                if (OrderRefundStatus.UNREFUND.getCodeValue().equals(refundStatus)) {
                    continue;
                }
                isRefunded = false;
            }
        }
        if (isRefunded) {//都已退款，更新订单状态
            orderDao.updateOrderStatus(orderNum, OrderStatus.DEAL_CLOSE.getCodeValue());
        }
        Long refundMoney = orderRefund.getRefundFee();
        orderDetailDao.updateRefundStatusSucByDnum(orderDetailNum, refundMoney);
        orderRefundDao.saveEntity(orderRefund);
    }

    @Override
    @DataSource(value = "read")
    public List<Order> getPayOrderList(Long shopId, String beginTime, String endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("beginTime", beginTime);
        paramMap.put("endTime", endTime);
        paramMap.put("shopId", shopId);
        List<Order> orderList = orderDao.queryList(paramMap);
        return orderList;
    }

    @Override
    @DataSource(value = "read")
    public List<Order> getOrderList(Long customerId, String paymentNum) {
        return orderDao.getByPayNumCumList(paymentNum, customerId);
    }

}
