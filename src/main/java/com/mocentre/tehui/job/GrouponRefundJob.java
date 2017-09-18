/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.service.IGrouponService;
import com.mocentre.tehui.buy.enums.OrderStatus;
import com.mocentre.tehui.buy.enums.OrderType;
import com.mocentre.tehui.buy.enums.PayStatus;
import com.mocentre.tehui.buy.enums.PayType;
import com.mocentre.tehui.buy.enums.RefundStatus;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.buy.model.OrderRefund;
import com.mocentre.tehui.buy.service.IOrderDetailService;
import com.mocentre.tehui.buy.service.IOrderPayService;
import com.mocentre.tehui.buy.service.IOrderRefundService;
import com.mocentre.tehui.buy.service.IOrderService;
import com.mocentre.tehui.common.SystemConfig;
import com.mocentre.tehui.common.constant.ConfigConstant;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.payment.wxapay.RefundRequestHandler;
import com.mocentre.tehui.payment.wxapay.util.WXUtil;

/**
 * 类GrouponRefundJob.java的实现描述：针对团购结束且未达到团购人数，退款job
 * 
 * @author sz.gong 2017年3月15日 下午4:42:29
 */
public class GrouponRefundJob extends QuartzJobBean {

    private IGrouponService     grouponService;
    private IOrderService       orderService;
    private IOrderDetailService orderDetailService;
    private IOrderPayService    orderPayService;
    private IOrderRefundService orderRefundService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            SchedulerContext ctx = context.getScheduler().getContext();
            grouponService = (IGrouponService) ctx.get("grouponService");
            orderService = (IOrderService) ctx.get("orderService");
            orderPayService = (IOrderPayService) ctx.get("orderPayService");
            orderDetailService = (IOrderDetailService) ctx.get("orderDetailService");
            orderRefundService = (IOrderRefundService) ctx.get("orderRefundService");
            List<Groupon> grouponList = grouponService.queryGrouponNeedRefund();
            if (grouponList != null) {
                for (Groupon groupon : grouponList) {
                    Long grouponId = groupon.getId();
                    List<Order> orderList = orderService.queryOrderListType(OrderType.GROUPON.getCodeValue(),
                            groupon.getId());
                    for (Order order : orderList) {
                        Long customerId = order.getCustomerId();
                        String orderNum = order.getOrderNum();
                        String orderStatus = order.getOrderStatus();
                        String payNum = order.getPaymentNum();
                        OrderPay orderPay = orderPayService.queryOrderPayByOrder(orderNum, payNum);
                        String payStatus = orderPay.getPayStatus();
                        String payType = orderPay.getPayType();
                        String transId = orderPay.getIrspRef();
                        Long totalAmount = orderPay.getAmount();
                        if (PayStatus.SUCCESS.getCodeValue().equals(payStatus)) {//交易成功
                            if (PayType.WXPAY.getCodeValue().equals(payType)) {//微信支付
                                if (OrderStatus.WAIT_SEND.getCodeValue().equals(orderStatus)) {//等待发货
                                    //String outRefundNo = orderNum + customerId;
                                    String refundNum = DateUtils.formatTime2(new Date())
                                            + String.valueOf(new Date().getTime()).substring(4, 10)
                                            + CommUtil.randomSixInt();
                                    String totFee = String.valueOf(totalAmount);
                                    String refFee = String.valueOf(totalAmount);
                                    Map resMap = this.getWxRefundData(transId, totFee, refFee, refundNum);
                                    if (resMap != null) {
                                        String returnCode = (String) resMap.get("return_code");
                                        String returnMsg = (String) resMap.get("return_msg");
                                        if ("SUCCESS".equals(returnCode.trim())) {
                                            String resultCode = (String) resMap.get("result_code");
                                            String appId = (String) resMap.get("appid");
                                            String mchId = (String) resMap.get("mch_id");
                                            String transactionId = (String) resMap.get("transaction_id");
                                            String refundId = (String) resMap.get("refund_id");
                                            Long refundFee = resMap.get("refund_fee") == null ? null : Long
                                                    .valueOf((String) resMap.get("refund_fee"));
                                            Long totalFee = resMap.get("total_fee") == null ? null : Long
                                                    .valueOf((String) resMap.get("total_fee"));
                                            Long cashFee = resMap.get("cash_fee") == null ? null : Long
                                                    .valueOf((String) resMap.get("cash_fee"));
                                            String times = DateUtils.formatTime(new Date());
                                            List<OrderRefund> orderRefundList = new ArrayList<OrderRefund>();
                                            List<OrderDetail> orderDetailList = orderDetailService
                                                    .queryOrderDetail(orderNum);
                                            if (orderDetailList != null && orderDetailList.size() > 0) {
                                                for (OrderDetail orderDetail : orderDetailList) {
                                                    OrderRefund orderRefund = new OrderRefund();
                                                    //orderRefund.setBatchNo(batchNo);
                                                    orderRefund.setRefundNum(refundNum);
                                                    orderRefund.setOrderDetailNum(orderDetail.getOrderDetailNum());
                                                    orderRefund.setOrderDetailId(orderDetail.getId());
                                                    orderRefund.setOrderNum(orderNum);
                                                    orderRefund.setRefundFee(refundFee);
                                                    orderRefund.setIrspRef(refundId);
                                                    orderRefund.setMchId(mchId);
                                                    orderRefund.setHostDate(times.substring(0, 10));
                                                    orderRefund.setHostTime(times.substring(times.length() - 8,
                                                            times.length()));
                                                    orderRefund.setTotalFee(totalFee);
                                                    orderRefund.setBatchStatus(resultCode);
                                                    orderRefund
                                                            .setRefundStatus("SUCCESS".equals(resultCode.trim()) ? RefundStatus.SUCCESS
                                                                    .getCodeValue() : RefundStatus.FAIL.getCodeValue());
                                                    orderRefundList.add(orderRefund);
                                                }
                                            }
                                            if ("SUCCESS".equals(resultCode.trim())) {//业务结果。退款成功
                                                orderService.orderRefundPay(orderNum, grouponId, orderRefundList);
                                            } else {//退款失败
                                                orderRefundService.saveOrderRefundBatch(orderRefundList);
                                            }
                                        } else {
                                            LoggerUtil.tehuiLog.info("团id：" + groupon.getId() + "，订单编号：" + orderNum
                                                    + "，于：" + DateUtils.formatTime(new Date()) + "退款失败。失败原因："
                                                    + returnMsg);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("团购退款任务异常", e);
        }
    }

    private Map getWxRefundData(String transactionId, String totalFee, String refundFee, String outRefundNo) {
        Map resultMap = new HashMap();
        try {
            String appId = SystemConfig.INSTANCE.getValue(ConfigConstant.APP_ID);
            String gateUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.REFUND_URL);
            String key = SystemConfig.INSTANCE.getValue(ConfigConstant.APP_KEY);
            String mchId = SystemConfig.INSTANCE.getValue(ConfigConstant.MCH_ID);
            String certPath = SystemConfig.INSTANCE.getValue(ConfigConstant.P12_CERT_FILE);
            RefundRequestHandler reqHandler = new RefundRequestHandler();
            reqHandler.setGateUrl(gateUrl);
            reqHandler.setKey(key);
            String noncestr = WXUtil.getNonceStr();
            reqHandler.setParameter("appid", appId);
            reqHandler.setParameter("mch_id", mchId);
            reqHandler.setParameter("nonce_str", noncestr);
            reqHandler.setParameter("sign_type", "MD5");
            reqHandler.setParameter("transaction_id", transactionId);
            reqHandler.setParameter("out_refund_no", outRefundNo);
            reqHandler.setParameter("total_fee", totalFee);
            reqHandler.setParameter("refund_fee", refundFee);
            reqHandler.setParameter("op_user_id", mchId);
            reqHandler.createMD5Sign();
            File certFile = new File(certPath);
            resultMap = reqHandler.sendRefund(certFile, mchId);
            String returnCode = (String) resultMap.get("return_code");
            String returnMsg = (String) resultMap.get("return_msg");
            LoggerUtil.tehuiLog.info("return_code:" + returnCode + " return_msg:" + returnMsg);
        } catch (Exception e) {
            return null;
        }
        return resultMap;
    }

}
