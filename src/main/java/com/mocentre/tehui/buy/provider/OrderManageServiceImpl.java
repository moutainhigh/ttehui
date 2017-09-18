/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.provider;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.OrderManageService;
import com.mocentre.tehui.act.enums.GroupStatus;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.service.IGrouponService;
import com.mocentre.tehui.backend.model.OrderDetailInstance;
import com.mocentre.tehui.backend.model.OrderInstance;
import com.mocentre.tehui.backend.param.LogisticsParam;
import com.mocentre.tehui.backend.param.OrderQueryParam;
import com.mocentre.tehui.buy.enums.OrderBillType;
import com.mocentre.tehui.buy.enums.OrderRefundStatus;
import com.mocentre.tehui.buy.enums.OrderStatus;
import com.mocentre.tehui.buy.enums.OrderType;
import com.mocentre.tehui.buy.enums.PayType;
import com.mocentre.tehui.buy.enums.RefundStatus;
import com.mocentre.tehui.buy.mapper.OrderDetailMapper;
import com.mocentre.tehui.buy.mapper.OrderMapper;
import com.mocentre.tehui.buy.model.Logistics;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.model.OrderLogistics;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.buy.model.OrderRefund;
import com.mocentre.tehui.buy.service.ILogisticsService;
import com.mocentre.tehui.buy.service.IOrderBillService;
import com.mocentre.tehui.buy.service.IOrderDetailService;
import com.mocentre.tehui.buy.service.IOrderLogisticsService;
import com.mocentre.tehui.buy.service.IOrderPayService;
import com.mocentre.tehui.buy.service.IOrderRefundService;
import com.mocentre.tehui.buy.service.IOrderService;
import com.mocentre.tehui.common.SystemConfig;
import com.mocentre.tehui.common.constant.ConfigConstant;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.goods.service.IGoodsStorageService;
import com.mocentre.tehui.payment.abcpay.refund.RefundRequestHandler;
import com.mocentre.tehui.payment.abcpay.refund.RefundResultData;
import com.mocentre.tehui.payment.wxapay.util.WXUtil;
import com.mocentre.tehui.ps.service.ICustomerAddressService;
import com.mocentre.tehui.shop.service.IShopService;

/**
 * 类OrderManageServiceImpl.java的实现描述：订单provider
 * 
 * @author sz.gong 2016年11月10日 下午4:54:02
 */
public class OrderManageServiceImpl implements OrderManageService {

    @Autowired
    private IOrderService           orderService;
    @Autowired
    private IShopService            shopService;
    @Autowired
    private IGoodsService           goodsService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private IGoodsStorageService    goodsStorageService;
    @Autowired
    private IOrderPayService        orderPayService;
    @Autowired
    private IOrderDetailService     orderDetailService;
    @Autowired
    private IOrderBillService       orderBillService;
    @Autowired
    private IOrderLogisticsService  orderLogisticsService;
    @Autowired
    private IGrouponService         grouponService;
    @Autowired
    private IOrderRefundService     orderRefundService;
    @Autowired
    private ILogisticsService       logisticsService;

    @Override
    public ListJsonResult<OrderInstance> queryPage(OrderQueryParam orderQuery) {

        ListJsonResult<OrderInstance> result = new ListJsonResult<OrderInstance>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("recipient", orderQuery.getRecipient());
            paramMap.put("orderNum", orderQuery.getOrderNum());
            paramMap.put("orderStatus", orderQuery.getOrderStatus());
            paramMap.put("payStatus", orderQuery.getPayStatus());
            paramMap.put("telephone", orderQuery.getTelephone());
            paramMap.put("shopId", orderQuery.getShopId());
            paramMap.put("beginTime", orderQuery.getBeginTime());
            paramMap.put("endTime", orderQuery.getEndTime());
            paramMap.put("column", orderQuery.getOrderColumn() == null ? "orderTime" : orderQuery.getOrderColumn());
            paramMap.put("orderBy", orderQuery.getOrderBy() == null ? "desc" : orderQuery.getOrderBy());
            Requirement require = new Requirement(orderQuery.getDraw(), orderQuery.getStart(), orderQuery.getLength(),
                    paramMap);
            ListJsonResult<Order> pageInfo = orderService.queryOrderPage(require);
            List<Order> orderList = pageInfo.getData();
            List<OrderInstance> orderInsList = new ArrayList<OrderInstance>();
            if (orderList != null && orderList.size() > 0) {
                for (int i = 0; i < orderList.size(); i++) {
                    Order order = orderList.get(i);
                    OrderPay opay = orderPayService.queryOrderPayByOrder(order.getOrderNum(), order.getPaymentNum());
                    Groupon groupon = null;
                    if (OrderType.GROUPON.getCodeValue().equals(order.getOrderType())) {
                        groupon = grouponService.getGrouponById(order.getTypeId());
                    }
                    OrderInstance orderIns = OrderMapper.toOrderInstance(order, opay, groupon);
                    orderInsList.add(orderIns);
                }
            }
            result.setData(orderInsList);
            result.setDraw(pageInfo.getDraw());
            result.setRecordsFiltered(pageInfo.getRecordsFiltered());
            result.setRecordsTotal(pageInfo.getRecordsTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<OrderInstance> queryDetail(Long id, Long shopId) {

        PlainResult<OrderInstance> result = new PlainResult<OrderInstance>();
        try {
            if (id == null) {
                result.setErrorMessage("1000", "id不能为空");
            }
            if (shopId == null) {
                result.setErrorMessage("1000", "shopId不能为空");
            }
            if (result.isSuccess()) {
                Order order = orderService.queryOrder(id, shopId);
                if (order == null) {
                    result.setErrorMessage("1001", "订单不存在");
                    return result;
                }
                String orderNum = order.getOrderNum();
                List<OrderDetail> detailList = orderDetailService.queryOrderDetail(orderNum);
                OrderPay orderPay = orderPayService.queryOrderPayByOrder(orderNum, order.getPaymentNum());
                OrderBill orderBill = null;
                OrderLogistics logis = null;
                Groupon groupon = null;
                if (order.getIsInvoice() == 1) {
                    orderBill = orderBillService.queryBillByOrder(orderNum);
                }
                if (!OrderStatus.WAIT_PAY.getCodeValue().equals(order.getOrderStatus())) {
                    logis = orderLogisticsService.queryLogisticsByOrder(id);
                }
                if (OrderType.GROUPON.getCodeValue().equals(order.getOrderType())) {
                    groupon = grouponService.getGrouponById(order.getTypeId());
                }
                List<Logistics> logisticsList = logisticsService.getLogisticsList();
                OrderInstance orderIns = OrderMapper.toOrderInstance(order, detailList, orderPay, logisticsList, logis,
                        orderBill, groupon);
                result.setData(orderIns);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryDetail", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<String> deliver(LogisticsParam lgParam) {

        PlainResult<String> result = new PlainResult<String>();
        try {
            Long orderId = lgParam.getOrderId();
            String compay = lgParam.getCompany();
            String expNum = lgParam.getExpNum();
            String isSms = lgParam.getIsSms();
            String logisticsCode = lgParam.getLogisticsCode();
            Long shopId = lgParam.getShopId();
            if (orderId == null || shopId == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                Order order = orderService.queryOrder(orderId, shopId);
                if (order == null) {
                    result.setErrorMessage("1001", "数据异常");
                    return result;
                }
                String orderType = order.getOrderType();
                Long typeId = order.getTypeId();
                boolean send = true;
                if (OrderType.GROUPON.getCodeValue().equals(orderType)) {//针对团购，成团后方可发货
                    Groupon groupon = grouponService.getGrouponById(typeId);
                    String groupStatus = groupon.getGroupStatus();
                    if (!GroupStatus.GROUPED.getCodeValue().equals(groupStatus)) {
                        send = false;
                        result.setErrorMessage("1001", "该订单未成团");
                    }
                }
                if (send) {
                    orderService.deliverOrder(orderId, compay, expNum, isSms, logisticsCode);
                    result.setData(order.getTelephone());
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deliver", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult editDeliver(LogisticsParam lgParam) {

        BaseResult result = new BaseResult();
        try {
            Long orderId = lgParam.getOrderId();
            String compay = lgParam.getCompany();
            String expNum = lgParam.getExpNum();
            String isSms = lgParam.getIsSms();
            String logisticsCode = lgParam.getLogisticsCode();
            Long shopId = lgParam.getShopId();
            if (orderId == null || shopId == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                Order order = orderService.queryOrder(orderId, shopId);
                if (order == null) {
                    result.setErrorMessage("1001", "数据异常");
                    return result;
                }
                orderService.editDeliverOrder(orderId, compay, expNum, isSms, logisticsCode);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editDeliver", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public ListResult<OrderInstance> queryOrderList(String beginTime, String endTime, String payType, Long shopId) {
        ListResult<OrderInstance> result = new ListResult<OrderInstance>();
        try {
            List<Order> orderList = orderService.getPayOrderList(shopId, beginTime, endTime);
            List<OrderInstance> orderInsList = new ArrayList<OrderInstance>();
            if (orderList != null && orderList.size() > 0) {
                for (int i = 0; i < orderList.size(); i++) {
                    Order order = orderList.get(i);
                    OrderPay opay = orderPayService.queryOrderPayByOrder(order.getOrderNum(), order.getPaymentNum());
                    if ("all".equals(payType) || payType.equals(opay.getPayStatus())) {
                        Groupon groupon = null;
                        if (OrderType.GROUPON.getCodeValue().equals(order.getOrderType())) {
                            groupon = grouponService.getGrouponById(order.getTypeId());
                        }
                        OrderInstance orderIns = OrderMapper.toOrderInstance(order, opay, groupon);
                        orderIns.setIsInvoice(order.getIsInvoice());
                        List<OrderDetail> orderDetails = orderDetailService.queryOrderDetail(order.getOrderNum());
                        List<OrderDetailInstance> orderDetailInsList = new ArrayList<OrderDetailInstance>();
                        for (OrderDetail orderdetail : orderDetails) {
                            orderDetailInsList.add(OrderDetailMapper.toOrderDetailInstance(orderdetail));
                        }
                        orderIns.setDetailInstanceList(orderDetailInsList);
                        if (order.getIsInvoice() == 1) {
                            OrderBill orderBill = orderBillService.queryBillByOrder(order.getOrderNum());
                            orderIns.setBillHeader(orderBill.getBillHeader());
                            orderIns.setBillRemark(orderBill.getBillRemark());
                            orderIns.setBillType(OrderBillType.getName(orderBill.getBillType()));
                        }
                        orderInsList.add(orderIns);
                    }
                }
            }
            result.setData(orderInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryOrderList", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult orderRefund(String orderNum, String orderDetailNum, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            boolean paramValid = StringUtils.isBlank(orderNum) || StringUtils.isBlank(orderDetailNum);
            if (paramValid) {
                br.setErrorMessage("1000", "参数错误");
            }
            if (br.isSuccess()) {
                Order order = orderService.queryOrderByNum(orderNum);
                if (order == null) {
                    br.setErrorMessage("1001", "订单不存在");
                    return br;
                }
                String paymentNum = order.getPaymentNum();
                OrderPay orderPay = orderPayService.queryOrderPayByOrder(orderNum, paymentNum);
                if (orderPay == null) {
                    br.setErrorMessage("1001", "订单不存在");
                    return br;
                }
                OrderDetail orderDetail = orderDetailService.getOrderDetail(orderNum, orderDetailNum);
                if (orderDetail == null) {
                    br.setErrorMessage("1002", "订单不存在");
                    return br;
                }
                String refundStatus = orderDetail.getRefundStatus();
                if (OrderRefundStatus.REFUNDED.getCodeValue().equals(refundStatus)) {
                    br.setErrorMessage("1003", "订单已退款");
                    return br;
                }
                LoggerUtil.tehuiLog.info("订单编号：" + orderNum + "，订单详情编号：" + orderDetailNum + "，于："
                        + DateUtils.formatTime(new Date()) + "开始退款");
                String payType = orderPay.getPayType();
                Long amount = orderPay.getAmount();
                String trxAmount = "";//退款金额
                Long realPrice = orderDetail.getGoodsRealPrice();
                Integer goodsNums = orderDetail.getGoodsAmount();
                Long couMoney = orderDetail.getCouponMoney();
                if (PayType.KMPAY.getCodeValue().equals(payType)) {
                    if (couMoney == null) {
                        trxAmount = new BigDecimal(realPrice * goodsNums).divide(new BigDecimal(100)).toString();
                    } else {
                        trxAmount = new BigDecimal(realPrice * goodsNums - couMoney).divide(new BigDecimal(100))
                                .toString();
                    }
                    String refundNum = DateUtils.formatTime2(new Date())
                            + String.valueOf(new Date().getTime()).substring(4, 10) + CommUtil.randomSixInt();
                    String orderDate = orderPay.getHostDate();
                    String orderTime = orderPay.getHostTime();
                    Long orderAmount = orderPay.getOrderAmount();
                    //开始退款
                    RefundRequestHandler reqHandler = new RefundRequestHandler(orderDate, orderTime, paymentNum,
                            refundNum, trxAmount);
                    RefundResultData resData = reqHandler.postRequest();
                    if (resData.isSuccess) {
                        OrderRefund orderRefund = new OrderRefund();
                        orderRefund.setRefundNum(refundNum);
                        orderRefund.setOrderDetailNum(orderDetailNum);
                        orderRefund.setOrderDetailId(orderDetail.getId());
                        orderRefund.setOrderNum(orderNum);
                        orderRefund.setBatchNo(resData.getBatchNo());
                        orderRefund.setRefundFee(new BigDecimal(resData.getTrxAmount()).multiply(new BigDecimal(100))
                                .longValue());
                        orderRefund.setIrspRef(resData.getIrspRef());
                        orderRefund.setHostDate(resData.getHostDate());
                        orderRefund.setHostTime(resData.getHostTime());
                        orderRefund.setTotalFee(orderAmount);
                        orderRefund.setBatchStatus(resData.getReturnCode());
                        orderRefund.setRefundStatus(RefundStatus.SUCCESS.getCodeValue());
                        orderService.updateOrderRefund(orderNum, orderDetailNum, orderRefund);
                    } else {
                        OrderRefund orderRefund = new OrderRefund();
                        orderRefund.setRefundNum(refundNum);
                        orderRefund.setOrderDetailNum(orderDetailNum);
                        orderRefund.setOrderDetailId(orderDetail.getId());
                        orderRefund.setOrderNum(orderNum);
                        orderRefund.setRefundStatus(RefundStatus.FAIL.getCodeValue());
                        orderRefund.setRefundFee(new BigDecimal(resData.getTrxAmount()).multiply(new BigDecimal(100))
                                .longValue());
                        orderRefund.setTotalFee(orderAmount);
                        orderRefundService.saveOrderRefund(orderRefund);
                        br.setErrorMessage("1004", "退款失败");
                    }
                    LoggerUtil.tehuiLog.info("订单编号：" + orderNum + "，订单详情编号：" + orderDetail + "，于："
                            + DateUtils.formatTime(new Date()) + "退款。退款状态：" + resData.getErrorMessage());
                } else if (PayType.WXPAY.getCodeValue().equals(payType)) {
                    if (couMoney == null) {
                        trxAmount = new BigDecimal(realPrice * goodsNums).toString();
                    } else {
                        trxAmount = new BigDecimal(realPrice * goodsNums - couMoney).toString();
                    }
                    String maxRefFee = String.valueOf(amount);
                    Map resMap = this.getWxRefundData(paymentNum, orderDetailNum, maxRefFee, trxAmount);
                    if (resMap != null) {
                        String returnCode = (String) resMap.get("return_code");
                        String returnMsg = (String) resMap.get("return_msg");
                        if ("SUCCESS".equals(returnCode.trim())) {
                            String resultCode = (String) resMap.get("result_code");
                            String errCode = (String) resMap.get("err_code");
                            String appId = (String) resMap.get("appid");
                            String mchId = (String) resMap.get("mch_id");
                            String transactionId = (String) resMap.get("transaction_id");
                            String outTradeNo = (String) resMap.get("out_trade_no");
                            String outRefundNo = (String) resMap.get("out_refund_no");
                            String refundId = (String) resMap.get("refund_id");
                            Long refundFee = resMap.get("refund_fee") == null ? null : Long.valueOf((String) resMap
                                    .get("refund_fee"));
                            Long totalFee = resMap.get("total_fee") == null ? null : Long.valueOf((String) resMap
                                    .get("total_fee"));
                            Long cashFee = resMap.get("cash_fee") == null ? null : Long.valueOf((String) resMap
                                    .get("cash_fee"));
                            String times = DateUtils.formatTime(new Date());
                            OrderRefund orderRefund = new OrderRefund();
                            orderRefund.setRefundNum(paymentNum);
                            orderRefund.setOrderDetailNum(orderDetail.getOrderDetailNum());
                            orderRefund.setOrderDetailId(orderDetail.getId());
                            orderRefund.setOrderNum(orderNum);
                            orderRefund.setRefundFee(refundFee);
                            orderRefund.setIrspRef(refundId);
                            orderRefund.setMchId(mchId);
                            orderRefund.setHostDate(times.substring(0, 10));
                            orderRefund.setHostTime(times.substring(times.length() - 8, times.length()));
                            orderRefund.setTotalFee(totalFee);
                            orderRefund.setBatchStatus(resultCode);
                            orderRefund.setRefundStatus("SUCCESS".equals(resultCode.trim()) ? RefundStatus.SUCCESS
                                    .getCodeValue() : RefundStatus.FAIL.getCodeValue());
                            if ("SUCCESS".equals(resultCode.trim())) {
                                orderService.updateOrderRefund(orderNum, orderDetailNum, orderRefund);
                            } else {
                                orderRefund.setErrorCode(errCode);
                                orderRefundService.saveOrderRefund(orderRefund);
                                br.setErrorMessage("1004", "退款失败。resultCode：" + resultCode + "，errCode：" + errCode);
                                LoggerUtil.tehuiLog.info("订单编号：" + orderNum + "，订单详情编号：" + orderDetailNum + "，于："
                                        + DateUtils.formatTime(new Date()) + "退款失败。resultCode:" + resultCode
                                        + "errCode：" + errCode);
                            }
                        } else {
                            br.setErrorMessage("1005", "退款失败。returnCode：" + returnCode + "，returnMsg：" + returnMsg);
                            LoggerUtil.tehuiLog.info("订单编号：" + orderNum + "，订单详情编号：" + orderDetailNum + "，于："
                                    + DateUtils.formatTime(new Date()) + "退款失败。returnCode:" + returnCode + "returnMsg："
                                    + returnMsg);
                        }
                    } else {
                        br.setErrorMessage("1006", "退款失败，原因不详。");
                        LoggerUtil.tehuiLog.info("订单编号：" + orderNum + "，订单详情编号：" + orderDetailNum + "，于："
                                + DateUtils.formatTime(new Date()) + "退款失败。失败原因不详");
                    }
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderRefund", e);
            br.setErrorMessage("999", "接口异常");
        }
        return br;
    }

    private Map getWxRefundData(String tradeNo, String refundNo, String totalFee, String refundFee) {
        Map resultMap = new HashMap();
        try {
            String appId = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_APP_ID);
            String gateUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_REFUND_URL);
            String key = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_KEY);
            String mchId = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_MCH_ID);
            String certPath = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_P12_CERT_FILE);
            File certFile = new File(certPath);
            com.mocentre.tehui.payment.wxapay.RefundRequestHandler reqHandler = new com.mocentre.tehui.payment.wxapay.RefundRequestHandler();
            reqHandler.setGateUrl(gateUrl);
            reqHandler.setKey(key);
            String noncestr = WXUtil.getNonceStr();
            reqHandler.setParameter("appid", appId);
            reqHandler.setParameter("mch_id", mchId);
            reqHandler.setParameter("nonce_str", noncestr);
            reqHandler.setParameter("sign_type", "MD5");
            reqHandler.setParameter("out_trade_no", tradeNo);
            reqHandler.setParameter("out_refund_no", refundNo);
            reqHandler.setParameter("total_fee", totalFee);
            reqHandler.setParameter("refund_fee", refundFee);
            reqHandler.createMD5Sign();
            resultMap = reqHandler.sendRefund(certFile, mchId);
            String returnCode = (String) resultMap.get("return_code");
            String returnMsg = (String) resultMap.get("return_msg");
            LoggerUtil.tehuiLog.info("return_code:" + returnCode + " return_msg:" + returnMsg);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getWxRefundData", e);
            return null;
        }
        return resultMap;
    }
}
