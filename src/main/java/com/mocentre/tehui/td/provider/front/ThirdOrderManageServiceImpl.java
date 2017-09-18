/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.provider.front;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.abc.trustpay.client.Base64;
import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.ebus.QueryOrderRequest;
import com.abc.trustpay.client.ebus.RefundRequest;
import com.alibaba.fastjson.JSONArray;
import com.mocentre.common.BaseResult;
import com.mocentre.common.PlainResult;
import com.mocentre.commons.SignUtil;
import com.mocentre.commons.httpclient.HttpProtocolHandler;
import com.mocentre.commons.httpclient.HttpRequest;
import com.mocentre.commons.httpclient.HttpResultType;
import com.mocentre.tehui.buy.enums.NotifyType;
import com.mocentre.tehui.buy.enums.RefundStatus;
import com.mocentre.tehui.common.SystemConfig;
import com.mocentre.tehui.common.constant.ConfigConstant;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.frontend.model.OrderCashDetailFTInstance;
import com.mocentre.tehui.frontend.model.OrderCashFTInstance;
import com.mocentre.tehui.frontend.model.OrderQueryDataFTInstance;
import com.mocentre.tehui.frontend.model.ThirdNotifyDataFTInstance;
import com.mocentre.tehui.frontend.model.ThirdOrderResultFTInstance;
import com.mocentre.tehui.frontend.model.ThirdPayDataFTInstance;
import com.mocentre.tehui.frontend.model.WxPayDataFTInstance;
import com.mocentre.tehui.frontend.param.ThirdOrderParam;
import com.mocentre.tehui.frontend.param.ThirdOrderProductParam;
import com.mocentre.tehui.frontend.service.ThirdOrderManageService;
import com.mocentre.tehui.payment.abcpay.AbcData;
import com.mocentre.tehui.payment.abcpay.AbcPayRequest;
import com.mocentre.tehui.payment.abcpay.AbcPayResult;
import com.mocentre.tehui.payment.abcpay.Constant;
import com.mocentre.tehui.payment.abcpay.PayDicOrder;
import com.mocentre.tehui.payment.abcpay.PayDicReq;
import com.mocentre.tehui.payment.abcpay.PayOrderItem;
import com.mocentre.tehui.payment.abcpay.PayResultData;
import com.mocentre.tehui.payment.abcpay.query.QueryRequestHandler;
import com.mocentre.tehui.payment.abcpay.query.QueryResultData;
import com.mocentre.tehui.payment.abcpay.refund.RefundRequestHandler;
import com.mocentre.tehui.payment.abcpay.refund.RefundResultData;
import com.mocentre.tehui.payment.wxapay.ClientRequestHandler;
import com.mocentre.tehui.payment.wxapay.PrepayIdRequestHandler;
import com.mocentre.tehui.payment.wxapay.client.ClientResponseHandler;
import com.mocentre.tehui.payment.wxapay.util.WXUtil;
import com.mocentre.tehui.td.emnus.ThirdOrderStatus;
import com.mocentre.tehui.td.emnus.ThirdPayStatus;
import com.mocentre.tehui.td.emnus.ThirdPayType;
import com.mocentre.tehui.td.mapper.ThirdOrderMapper;
import com.mocentre.tehui.td.model.MemberAccount;
import com.mocentre.tehui.td.model.ThirdOrder;
import com.mocentre.tehui.td.model.ThirdOrderDetail;
import com.mocentre.tehui.td.model.ThirdOrderPay;
import com.mocentre.tehui.td.model.ThirdOrderRefund;
import com.mocentre.tehui.td.service.IMemberAccountService;
import com.mocentre.tehui.td.service.IThirdOrderPayService;
import com.mocentre.tehui.td.service.IThirdOrderRefundService;
import com.mocentre.tehui.td.service.IThirdOrderService;

/**
 * 类ThirdOrderManageService.java的实现描述：第三方订单支付
 * 
 * @author sz.gong 2017年6月20日 下午3:21:57
 */
public class ThirdOrderManageServiceImpl implements ThirdOrderManageService {

    @Autowired
    private IThirdOrderService       thirdOrderService;
    @Autowired
    private IThirdOrderPayService    thirdOrderPayService;
    @Autowired
    private IThirdOrderRefundService thirdOrderRefundService;
    @Autowired
    private IMemberAccountService    mebAccountService;

    @Override
    public PlainResult<String> orderPayment(ThirdOrderParam orderParam) {
        PlainResult<String> pr = new PlainResult<String>();
        pr.setRequestId(orderParam.getRequestId());
        String payType = orderParam.getPayType();
        String orderNum = orderParam.getOrderNum();
        String appKey = orderParam.getAppKey();
        String resultUrl = orderParam.getResultUrl();
        Long orderAmount = orderParam.getOrderAmount();
        String orderDate = orderParam.getOrderDate();
        String orderTime = orderParam.getOrderTime();
        String notifyUrl = orderParam.getNotifyUrl();
        String abcaid = orderParam.getAbcaid();
        try {
            Date nowDate = new Date();
            List<ThirdOrderProductParam> productList = orderParam.getProductList();
            String paymentNum = DateUtils.formatTime2(nowDate) + String.valueOf(new Date().getTime()).substring(4, 10)
                    + CommUtil.randomSixInt();
            boolean paramValid = StringUtils.isBlank(orderNum) || orderAmount == null || orderAmount < 0
                    || StringUtils.isBlank(orderDate) || StringUtils.isBlank(orderTime) || StringUtils.isBlank(appKey)
                    || StringUtils.isBlank(notifyUrl);
            if (paramValid) {
                pr.setErrorMessage("1000", "参数错误");
                return pr;
            }
            if (StringUtils.isBlank(ThirdPayType.getName(payType))) {
                pr.setErrorMessage("1000", "参数错误");
                return pr;
            }
            ThirdOrder thirdOrder = thirdOrderService.getThirdOrder(orderNum, appKey);
            if (thirdOrder != null && thirdOrder.getId() != null) {
                Long oAmount = thirdOrder.getOrderAmount();
                String oStatus = thirdOrder.getOrderStatus();
                if (orderAmount.longValue() != oAmount.longValue()) {
                    pr.setErrorMessage("1001", "多次支付订单总金额不同，不允许支付");
                    return pr;
                }
                if (oStatus.equals(ThirdOrderStatus.SUCCESS.getCodeValue())) {
                    pr.setErrorMessage("1002", "订单已支付");
                    return pr;
                }
                if (oStatus.equals(ThirdOrderStatus.REFUND.getCodeValue())) {
                    pr.setErrorMessage("1003", "订单已退款");
                    return pr;
                }
                Long count = thirdOrderService.updateThirdOrder(thirdOrder, payType, paymentNum);
                if (count == 0) {
                    pr.setErrorMessage("1004", "订单支付失败");
                    return pr;
                }
            } else {
                ThirdOrder order = ThirdOrderMapper.toThirdOrder(orderParam);
                Long id = thirdOrderService.saveThirdOrder(order, payType, paymentNum);
                if (id == null) {
                    pr.setErrorMessage("1004", "订单支付失败");
                    return pr;
                }
            }
            MemberAccount mebAccount = mebAccountService.getMemberAccountFromCache(appKey);
            if (orderAmount == 0) {//针对0元订单
                String returnURL = URLEncoder.encode(mebAccount.getReturnUrl(), "UTF-8");
                StringBuffer url = new StringBuffer();
                String paySucUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_THIRD_PAY_SUC_URL);
                url.append(paySucUrl);
                url.append("?");
                url.append("total=0");
                url.append("&returnUrl=" + returnURL);
                pr.setData(url.toString());
                return pr;
            }
            if (ThirdPayType.ABCPAY.getCodeValue().equals(payType) || ThirdPayType.KMPAY.getCodeValue().equals(payType)) {
                //1
                String PayTypeID = Constant.PAY_TYPE_ID_IMMEDIATEPAY;
                String OrderDate = DateUtils.format(nowDate, "yyyy/MM/dd");
                String OrderTime = DateUtils.format(nowDate, "HH:mm:ss");
                String OrderNo = paymentNum;
                String OrderAmount = new BigDecimal(orderAmount).divide(new BigDecimal(100)).toString();
                String CommodityType = Constant.COMMODITY_TYPE_XFL_CTL;
                String InstallmentMark = Constant.INSTALL_MENT_MARK_NO;
                PayDicOrder dicOrder = new PayDicOrder(PayTypeID, OrderDate, OrderTime, OrderNo, OrderAmount,
                        CommodityType, InstallmentMark);
                //2
                List<PayOrderItem> orderItemList = new ArrayList<PayOrderItem>();
                if (productList == null || productList.size() == 0) {
                    String remark = mebAccount.getRemark();
                    PayOrderItem orderItem = new PayOrderItem(remark);
                    orderItemList.add(orderItem);
                } else {
                    String remark = mebAccount.getRemark();
                    for (ThirdOrderProductParam product : productList) {
                        String productName = product.getProductName();
                        productName = StringUtils.isBlank(productName) ? remark : productName;
                        PayOrderItem orderItem = new PayOrderItem(productName);
                        orderItem.setProductID(product.getProductId());
                        orderItem.setQty(product.getProductNum() == null ? null : String.valueOf(product
                                .getProductNum()));
                        orderItem.setUnitPrice(product.getProductPrice() == null ? null : new BigDecimal(product
                                .getProductPrice()).divide(new BigDecimal(100)).toString());
                        orderItemList.add(orderItem);
                    }
                }
                //3
                String PaymentType = Constant.PAYMENT_TYPE_ZFFSHB;
                String PaymentLinkType = Constant.PAYMENT_LINK_TYPE_SJWLJR;
                String NotifyType = Constant.NOTIFY_TYPE_ASYNC;
                String IsBreakAccount = Constant.IS_BREAK_ACCOUNT_NO;
                String ResultNotifyURL = SystemConfig.INSTANCE.getValue(ConfigConstant.ABC_THIRD_NOTIFY_ASYNC_URL);
                PayDicReq dicReq = new PayDicReq(PaymentType, PaymentLinkType, NotifyType, ResultNotifyURL,
                        IsBreakAccount);
                AbcPayRequest payReq = new AbcPayRequest(dicOrder, orderItemList, dicReq);
                PlainResult<AbcData> dataResult = payReq.sendRequest();
                AbcData data = dataResult.getData();
                if (dataResult.isSuccess()) {
                    String payUrl = data.getPaymentURL();
                    if (ThirdPayType.ABCPAY.getCodeValue().equals(payType)) {
                        String token = payUrl.substring(payUrl.indexOf("TOKEN") + 6, payUrl.length());
                        resultUrl = URLEncoder.encode(resultUrl, "UTF-8");
                        String nonUrl = "https://mocentre_pay/abcpay%7C" + token + "&1110&new*" + resultUrl;
                        pr.setData(nonUrl);
                    } else if (ThirdPayType.KMPAY.getCodeValue().equals(payType)) {
                        pr.setData(payUrl);
                    }
                } else {
                    pr.setErrorMessage(data.getReturnCode(), data.getErrorMessage());
                }
                LoggerUtil.tehuiLog.info("农行预支付。第三方支付编码：" + paymentNum + "，订单编号：" + orderNum + "，支付时间"
                        + DateUtils.formatTime(nowDate) + "，支付金额" + orderAmount + "，支付状态：" + dataResult.isSuccess()
                        + "，失败编码原因：(" + data.getReturnCode() + ")" + data.getErrorMessage());
            } else if (ThirdPayType.WXPAY.getCodeValue().equals(payType)) {
                StringBuffer url = new StringBuffer();
                String wxPayUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_THIRD_PAY_URL);
                url.append(wxPayUrl);
                url.append("?");
                url.append("orderNum=" + orderNum);
                url.append("&appKey=" + appKey);
                String appSercet = mebAccount.getAppSecret();
                Map<String, String> sArray = new HashMap<String, String>();
                sArray.put("orderNum", orderNum);
                sArray.put("appKey", appKey);
                String sign = SignUtil.buildMysign(appSercet, sArray);
                url.append("&sign=" + sign);
                pr.setData(url.toString());
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderPayment", e);
            pr.setErrorMessage("999", "系统异常");
        }
        return pr;
    }

    @Override
    public PlainResult<ThirdOrderResultFTInstance> getOrderResult(String orderNum, String appKey, String requestId) {
        PlainResult<ThirdOrderResultFTInstance> result = new PlainResult<ThirdOrderResultFTInstance>();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(orderNum) || StringUtils.isBlank(appKey)) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            ThirdOrder order = thirdOrderService.getThirdOrder(orderNum, appKey);
            if (order == null) {
                result.setErrorMessage("1001", "订单不存在");
                return result;
            }
            ThirdOrderResultFTInstance resultData = new ThirdOrderResultFTInstance();
            resultData.setAbcaid(order.getAbcaid());
            resultData.setTotalMoney(new BigDecimal(order.getOrderAmount()).divide(new BigDecimal(100)).toString());
            if (ThirdOrderStatus.WAIT.getCodeValue().equals(order.getOrderStatus())) {
                String paymentNum = order.getPaymentNum();
                String payTypeID = Constant.PAY_TYPE_ID_IMMEDIATEPAY;
                QueryRequestHandler queryHandler = new QueryRequestHandler(payTypeID, paymentNum, true);
                QueryResultData resData = queryHandler.postRequest();
                if (resData.getIsSuccess()) {
                    String tStatus = resData.getStatus();
                    if ("04".equals(tStatus)) {
                        resultData.setResultCode("SUCCESS");
                    } else {
                        resultData.setResultCode("FAIL");
                    }
                }
            } else if (ThirdOrderStatus.SUCCESS.getCodeValue().equals(order.getOrderStatus())) {
                resultData.setResultCode("SUCCESS");
            } else {
                resultData.setResultCode("FAIL");
            }
            MemberAccount mebAccount = mebAccountService.getMemberAccountFromCache(appKey);
            if (mebAccount != null) {
                resultData.setReturnUrl(mebAccount.getReturnUrl());
            }
            result.setData(resultData);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getOrderResult", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public PlainResult<ThirdPayDataFTInstance> getOrderPayData(String orderNum, String appKey, String billIp,
                                                               String openid, String requestId) {
        PlainResult<ThirdPayDataFTInstance> result = new PlainResult<ThirdPayDataFTInstance>();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(orderNum) || StringUtils.isBlank(appKey) || StringUtils.isBlank(billIp)
                    || StringUtils.isBlank(openid)) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            ThirdOrder order = thirdOrderService.getThirdOrder(orderNum, appKey);
            if (order == null) {
                result.setErrorMessage("1001", "订单不存在");
                return result;
            }
            Long orderAmount = order.getOrderAmount();
            String paymentNum = order.getPaymentNum();
            ThirdPayDataFTInstance payData = new ThirdPayDataFTInstance();
            OrderCashFTInstance orderCash = new OrderCashFTInstance();
            List<OrderCashDetailFTInstance> ocDetailList = new ArrayList<OrderCashDetailFTInstance>();
            String products = order.getProducts();
            List<ThirdOrderDetail> detailList = JSONArray.parseArray(products, ThirdOrderDetail.class);
            for (ThirdOrderDetail detail : detailList) {
                OrderCashDetailFTInstance ocDetail = new OrderCashDetailFTInstance();
                ocDetail.setGoodsName(detail.getProductName());
                ocDetail.setBuyNum(detail.getProductNum());
                ocDetailList.add(ocDetail);
            }
            orderCash.setDetailList(ocDetailList);
            orderCash.setTotalMoney(new BigDecimal(orderAmount).divide(new BigDecimal(100)));
            WxPayDataFTInstance wxPayData = getWxPayData(paymentNum, orderAmount, billIp, openid);
            if (wxPayData == null) {
                LoggerUtil.tehuiLog.info("商户:" + appKey + "，于" + DateUtils.formatTime(new Date()) + "创建prepayid失败");
                result.setErrorMessage("1002", "支付生成prepayid错误");
                return result;
            }
            payData.setOrderCash(orderCash);
            payData.setWxPayData(wxPayData);
            MemberAccount mebAccount = mebAccountService.getMemberAccountFromCache(appKey);
            if (mebAccount != null) {
                payData.setReturnUrl(mebAccount.getReturnUrl());
            }
            result.setData(payData);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getOrderPayData", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    private WxPayDataFTInstance getWxPayData(String paymentNum, Long totalPrice, String billIp, String openid) {
        String key = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_KEY);
        String appId = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_APP_ID);
        String mchId = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_MCH_ID);
        String notifyUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_THIRD_NOTIFY_URL);
        String sendUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_PAY_URL);
        PrepayIdRequestHandler prepayHandler = new PrepayIdRequestHandler();
        prepayHandler.setKey(key);
        prepayHandler.setGateUrl(sendUrl);
        ClientRequestHandler clientHandler = new ClientRequestHandler();
        clientHandler.setKey(key);
        String noncestr = WXUtil.getNonceStr();
        prepayHandler.setParameter("appid", appId);
        prepayHandler.setParameter("mch_id", mchId);
        prepayHandler.setParameter("nonce_str", noncestr);
        prepayHandler.setParameter("body", paymentNum);
        prepayHandler.setParameter("attach", "");//附加数据
        prepayHandler.setParameter("detail", "");//商品详情
        prepayHandler.setParameter("out_trade_no", paymentNum);//商户订单号
        prepayHandler.setParameter("total_fee", totalPrice + "");
        prepayHandler.setParameter("spbill_create_ip", billIp);
        prepayHandler.setParameter("notify_url", notifyUrl);
        prepayHandler.setParameter("trade_type", "JSAPI");
        prepayHandler.setParameter("openid", openid);
        prepayHandler.createMD5Sign();
        String prepayid = prepayHandler.sendPrepay();
        if (StringUtils.isEmpty(prepayid)) {
            return null;
        }
        String timeStamp = WXUtil.getTimeStamp();
        String nonceStrs = WXUtil.getNonceStr();
        String signType = "MD5";
        String packages = "prepay_id=" + prepayid;
        clientHandler.setParameter("appId", appId);
        clientHandler.setParameter("timeStamp", timeStamp);
        clientHandler.setParameter("nonceStr", nonceStrs);
        clientHandler.setParameter("package", packages);
        clientHandler.setParameter("signType", signType);
        String paySign = clientHandler.getMD5Sign();
        WxPayDataFTInstance wxPayData = new WxPayDataFTInstance();
        wxPayData.setAppId(appId);
        wxPayData.setNonceStr(nonceStrs);
        wxPayData.setPackageStr(packages);
        wxPayData.setPaySign(paySign);
        wxPayData.setSignType(signType);
        wxPayData.setTimeStamp(timeStamp);
        return wxPayData;
    }

    @Override
    public PlainResult<OrderQueryDataFTInstance> orderQuery(String orderNum, String appKey, String requestId) {
        PlainResult<OrderQueryDataFTInstance> pr = new PlainResult<OrderQueryDataFTInstance>();
        pr.setRequestId(requestId);
        OrderQueryDataFTInstance orderData = new OrderQueryDataFTInstance();
        try {
            boolean paramValid = StringUtils.isBlank(orderNum) || StringUtils.isBlank(appKey);
            if (paramValid) {
                pr.setErrorMessage("1000", "参数错误");
                return pr;
            }
            ThirdOrder order = thirdOrderService.getThirdOrder(orderNum, appKey);
            if (order == null) {
                pr.setErrorMessage("1001", "不存在的订单");
                return pr;
            }
            String orderStatus = order.getOrderStatus();
            Long orderAmount = order.getOrderAmount();
            String orderDate = order.getOrderDate();
            String orderTime = order.getOrderTime();
            orderData.setOrderNo(orderNum);
            orderData.setOrderAmount(new BigDecimal(orderAmount).divide(new BigDecimal(100)).toString());
            orderData.setOrderDate(orderDate);
            orderData.setOrderTime(orderTime);
            if (orderStatus.equals(ThirdOrderStatus.SUCCESS.getCodeValue())) {
                orderData.setOrderStatus("04");
            } else if (orderStatus.equals(ThirdOrderStatus.FAIL.getCodeValue())) {
                orderData.setOrderStatus("99");
            } else if (orderStatus.equals(ThirdOrderStatus.WAIT.getCodeValue())) {
                String paymentNum = order.getPaymentNum();
                String payTypeID = Constant.PAY_TYPE_ID_IMMEDIATEPAY;
                QueryRequestHandler queryHandler = new QueryRequestHandler(payTypeID, paymentNum, true);
                QueryResultData resData = queryHandler.postRequest();
                String returnCode = resData.getReturnCode();
                String errorMessage = resData.getErrorMessage();
                if (resData.getIsSuccess()) {
                    String tStatus = resData.getStatus();
                    orderData.setOrderStatus(tStatus);
                } else {
                    pr.setErrorMessage(returnCode, errorMessage);
                }
            } else if (orderStatus.equals(ThirdOrderStatus.REFUND.getCodeValue())) {
                orderData.setOrderStatus("05");
            }
            pr.setData(orderData);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderQuery", e);
            pr.setErrorMessage("999", "系统异常");
        }
        return pr;
    }

    @Override
    public PlainResult<ThirdNotifyDataFTInstance> abcAsyncNotify(String msg, String requestId) {
        PlainResult<ThirdNotifyDataFTInstance> pr = new PlainResult<ThirdNotifyDataFTInstance>();
        pr.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(msg)) {
                pr.setErrorMessage("1000", "参数错误");
            }
            if (pr.isSuccess()) {
                ThirdNotifyDataFTInstance resData = new ThirdNotifyDataFTInstance();
                AbcPayResult payResult = new AbcPayResult(msg);
                PayResultData resultData = payResult.getResult();
                ThirdOrderPay orderPay = new ThirdOrderPay();
                String paymentNum = resultData.getOrderNo();
                orderPay.setPaymentNum(paymentNum);
                ThirdOrder thirdOrder = thirdOrderService.getThirdOrder(paymentNum);
                if (thirdOrder == null) {
                    pr.setErrorMessage("1001", "订单不存在");
                    return pr;
                }
                String appKey = thirdOrder.getAppKey();
                MemberAccount mebAccount = mebAccountService.getMemberAccountFromCache(appKey);
                if (mebAccount != null) {
                    resData.setReturnUrl(mebAccount.getReturnUrl());
                }
                resData.setTotalMoney(new BigDecimal(thirdOrder.getOrderAmount()).divide(new BigDecimal(100))
                        .toString());
                pr.setData(resData);
                if (ThirdOrderStatus.SUCCESS.getCodeValue().equals(thirdOrder.getOrderStatus())) {
                    return pr;
                }
                boolean success = resultData.isSuccess();
                if (success) {
                    String amount = resultData.getAmount();
                    orderPay.setAmount((new BigDecimal(amount).multiply(new BigDecimal(100))).longValue());
                    orderPay.setBatchNo(resultData.getBatchNo());
                    orderPay.setVoucherNo(resultData.getVoucherNo());
                    orderPay.setHostDate(resultData.getHostDate());
                    orderPay.setHostTime(resultData.getHostTime());
                    orderPay.setRemark(resultData.getMerchantRemarks());
                    orderPay.setNotifyType(resultData.getNotifyType());
                    orderPay.setIrspRef(resultData.getIrspRef());
                    orderPay.setMchId(resultData.getMerchantID());
                    orderPay.setPayStatus(ThirdPayStatus.SUCCESS.getCodeValue());
                    thirdOrderService.updateThirdOrderAndPay(paymentNum, ThirdOrderStatus.SUCCESS.getCodeValue(),
                            orderPay);
                } else {
                    orderPay.setPayStatus(ThirdPayStatus.FAIL.getCodeValue());
                    thirdOrderService
                            .updateThirdOrderAndPay(paymentNum, ThirdOrderStatus.FAIL.getCodeValue(), orderPay);
                    pr.setErrorMessage("1001", "付款失败");
                }
                LoggerUtil.tehuiLog.info("requestId：" + requestId + "。第三方农行支付回调。支付编号：" + paymentNum + "，订单编号："
                        + thirdOrder.getOrderNum() + "，回调付款状态：" + success + "，returnCode：" + resultData.getReturnCode()
                        + "，errorMsg：" + resultData.getErrorMessage());
                //通知第三方
                String orderNum = thirdOrder.getOrderNum();
                String notifyUrl = thirdOrder.getNotifyUrl();
                String appSercet = "";
                Map<String, String> sArray = new HashMap<String, String>();
                int index = notifyUrl.indexOf("?");
                if (index > 0) {
                    String params = notifyUrl.substring(index + 1, notifyUrl.length());
                    if (StringUtils.isNotBlank(params)) {
                        String[] paramsArry = params.split("&");
                        for (int i = 0; i < paramsArry.length; i++) {
                            String[] paramArray = paramsArry[i].split("=");
                            if (paramArray.length == 2) {
                                sArray.put(paramArray[0].trim(), paramArray[1].trim());
                            }
                        }
                    }
                    notifyUrl = notifyUrl.substring(0, index);
                }
                sArray.put("appKey", thirdOrder.getAppKey());
                sArray.put("orderNum", orderNum);
                if (success) {
                    sArray.put("status", "00");
                } else {
                    sArray.put("status", "01");
                }
                String sign = SignUtil.buildMysign(appSercet, sArray);
                sArray.put("sign", sign);
                HttpProtocolHandler handler = HttpProtocolHandler.getInstance();
                HttpRequest req = new HttpRequest(HttpResultType.STRING);
                req.setUrl(notifyUrl);
                req.setMethod(HttpRequest.METHOD_POST);
                req.setCharset("utf-8");
                req.setParameters(sArray);
                handler.execute(req);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("abcAsyncNotify", e);
            pr.setErrorMessage("999", "系统异常");
        }
        return pr;
    }

    @Override
    public BaseResult wxAsyncNotify(String xmlData, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(xmlData)) {
                br.setErrorMessage("1000", "参数错误");
            }
            if (br.isSuccess()) {
                ThirdOrderPay orderPay = new ThirdOrderPay();
                String key = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_KEY);
                ClientResponseHandler resHandler = new ClientResponseHandler();
                resHandler.setCharset("UTF-8");
                resHandler.setContent(xmlData);
                resHandler.setKey(key);
                boolean isTenPay = resHandler.isTenpaySign();
                if (!isTenPay) {
                    br.setErrorMessage("1001", "签名验证不通过");
                    return br;
                }
                String returnCode = resHandler.getParameter("return_code");
                String returnMsg = resHandler.getParameter("return_msg");
                String mchId = resHandler.getParameter("mch_id");
                String resultCode = resHandler.getParameter("result_code");
                Integer totalFee = StringUtils.isBlank(resHandler.getParameter("total_fee")) ? null : Integer
                        .valueOf(resHandler.getParameter("total_fee"));
                String transId = resHandler.getParameter("transaction_id");
                String outTradeNo = resHandler.getParameter("out_trade_no");
                String timeEnd = resHandler.getParameter("time_end");
                if ("FAIL".equals(returnCode.trim())) {
                    LoggerUtil.tehuiLog.info("requestId：" + requestId + "第三方微信支付回调。支付编号：" + outTradeNo + "，回调付款状态："
                            + resultCode + "，returnCode：" + returnCode + "，errorMsg：" + returnMsg);
                    br.setErrorMessage("1002", returnMsg);
                    return br;
                }
                String paymentNum = outTradeNo.trim();
                ThirdOrder thirdOrder = thirdOrderService.getThirdOrder(paymentNum);
                if (thirdOrder == null) {
                    br.setErrorMessage("1001", "订单不存在");
                    return br;
                }
                if (ThirdOrderStatus.SUCCESS.getCodeValue().equals(thirdOrder.getOrderStatus())) {
                    return br;
                }
                LoggerUtil.tehuiLog.info("requestId：" + requestId + "。第三方微信支付回调。支付编号：" + outTradeNo + "，订单编号："
                        + thirdOrder.getOrderNum() + "，回调付款状态：" + resultCode + "，returnCode：" + returnCode
                        + "，errorMsg：" + returnMsg);
                orderPay.setAmount(Long.valueOf(totalFee));
                //orderPay.setBatchNo(resultData.getBatchNo());
                //orderPay.setVoucherNo(resultData.getVoucherNo());
                orderPay.setHostDate(timeEnd.substring(0, 4) + "-" + timeEnd.substring(4, 6) + "-"
                        + timeEnd.substring(6, 8));
                orderPay.setHostTime(timeEnd.substring(8, 10) + ":" + timeEnd.substring(10, 12) + ":"
                        + timeEnd.substring(12, 14));
                //orderPay.setRemark(resultData.getMerchantRemarks());
                orderPay.setNotifyType(NotifyType.ASYNC.getCodeValue());
                orderPay.setIrspRef(transId);
                orderPay.setMchId(mchId);
                orderPay.setPaymentNum(paymentNum);
                if ("SUCCESS".equals(resultCode.trim())) {
                    orderPay.setPayStatus(ThirdPayStatus.SUCCESS.getCodeValue());
                    thirdOrderService.updateThirdOrderAndPay(paymentNum, ThirdOrderStatus.SUCCESS.getCodeValue(),
                            orderPay);
                } else {
                    orderPay.setPayStatus(ThirdPayStatus.FAIL.getCodeValue());
                    thirdOrderService
                            .updateThirdOrderAndPay(paymentNum, ThirdOrderStatus.FAIL.getCodeValue(), orderPay);
                    br.setErrorMessage("1001", "付款失败");
                }
                //通知第三方
                String orderNum = thirdOrder.getOrderNum();
                String notifyUrl = thirdOrder.getNotifyUrl();
                String appSercet = "";
                Map<String, String> sArray = new HashMap<String, String>();
                int index = notifyUrl.indexOf("?");
                if (index > 0) {
                    String params = notifyUrl.substring(index + 1, notifyUrl.length());
                    if (StringUtils.isNotBlank(params)) {
                        String[] paramsArry = params.split("&");
                        for (int i = 0; i < paramsArry.length; i++) {
                            String[] paramArray = paramsArry[i].split("=");
                            if (paramArray.length == 2) {
                                sArray.put(paramArray[0].trim(), paramArray[1].trim());
                            }
                        }
                    }
                    notifyUrl = notifyUrl.substring(0, index);
                }
                sArray.put("appKey", thirdOrder.getAppKey());
                sArray.put("orderNum", orderNum);
                if ("SUCCESS".equals(resultCode.trim())) {
                    sArray.put("status", "00");
                } else {
                    sArray.put("status", "01");
                }
                String sign = SignUtil.buildMysign(appSercet, sArray);
                sArray.put("sign", sign);
                HttpProtocolHandler handler = HttpProtocolHandler.getInstance();
                HttpRequest req = new HttpRequest(HttpResultType.STRING);
                req.setUrl(notifyUrl);
                req.setMethod(HttpRequest.METHOD_POST);
                req.setCharset("utf-8");
                req.setParameters(sArray);
                handler.execute(req);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("abcAsyncNotify", e);
            br.setErrorMessage("999", "系统异常");
        }
        return br;
    }

    @Override
    public BaseResult orderRefund(String orderNum, String appKey, Long trxAmount, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            boolean paramValid = StringUtils.isBlank(orderNum) || StringUtils.isBlank(appKey) || trxAmount == null
                    || trxAmount <= 0;
            if (paramValid) {
                br.setErrorMessage("1000", "参数错误");
                return br;
            }
            ThirdOrder order = thirdOrderService.getThirdOrder(orderNum, appKey);
            if (order == null) {
                br.setErrorMessage("1001", "不存在的订单");
                return br;
            }
            String orderStatus = order.getOrderStatus();
            if (ThirdOrderStatus.WAIT.getCodeValue().equals(orderStatus)
                    || ThirdOrderStatus.FAIL.getCodeValue().equals(orderStatus)) {
                br.setErrorMessage("1002", "订单未完成支付");
                return br;
            }
            String paymentNum = order.getPaymentNum();
            ThirdOrderPay orderPay = thirdOrderPayService.getThirdOrderPay(paymentNum);
            if (orderPay == null) {
                br.setErrorMessage("1002", "订单未完成支付");
                return br;
            }
            String refundNum = DateUtils.formatTime2(new Date())
                    + String.valueOf(new Date().getTime()).substring(4, 10) + CommUtil.randomSixInt();
            String orderDate = orderPay.getHostDate();
            String orderTime = orderPay.getHostTime();
            Long orderAmount = orderPay.getOrderAmount();
            String mTrxAmount = new BigDecimal(trxAmount).divide(new BigDecimal(100)).toString();
            //开始退款
            RefundRequestHandler reqHandler = new RefundRequestHandler(orderDate, orderTime, paymentNum, refundNum,
                    mTrxAmount);
            RefundResultData resData = reqHandler.postRequest();
            if (resData.isSuccess) {
                ThirdOrderRefund orderRefund = new ThirdOrderRefund();
                orderRefund.setRefundNum(refundNum);
                orderRefund.setOrderNum(orderNum);
                orderRefund.setAppKey(appKey);
                orderRefund.setBatchNo(resData.getBatchNo());
                orderRefund.setRefundFee(new BigDecimal(resData.getTrxAmount()).multiply(new BigDecimal(100))
                        .longValue());
                orderRefund.setIrspRef(resData.getIrspRef());
                orderRefund.setHostDate(resData.getHostDate());
                orderRefund.setHostTime(resData.getHostTime());
                orderRefund.setTotalFee(orderAmount);
                orderRefund.setBatchStatus(resData.getReturnCode());
                orderRefund.setRefundStatus(RefundStatus.SUCCESS.getCodeValue());
                thirdOrderService.updateThirdOrderRefund(orderNum, appKey, orderRefund);
            } else {
                ThirdOrderRefund orderRefund = new ThirdOrderRefund();
                orderRefund.setRefundNum(refundNum);
                orderRefund.setOrderNum(orderNum);
                orderRefund.setAppKey(appKey);
                orderRefund.setRefundStatus(RefundStatus.FAIL.getCodeValue());
                orderRefund.setRefundFee(new BigDecimal(resData.getTrxAmount()).multiply(new BigDecimal(100))
                        .longValue());
                orderRefund.setTotalFee(orderAmount);
                thirdOrderRefundService.saveOrderRefund(orderRefund);
                br.setErrorMessage("1003", "退款失败");
            }
            LoggerUtil.tehuiLog.info("第三方订单编号：" + orderNum + "，appKey：" + appKey + "，于："
                    + DateUtils.formatTime(new Date()) + "退款。退款状态：" + resData.getErrorMessage());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderRefund", e);
            br.setErrorMessage("999", "系统异常");
        }
        return br;
    }

    public void orderQueryTest(String orderNum) {
        try {
            String paymentNum = orderNum;
            String PayTypeID = Constant.PAY_TYPE_ID_IMMEDIATEPAY;
            QueryOrderRequest tReq = new QueryOrderRequest();
            tReq.queryRequest.put("PayTypeID", PayTypeID);
            tReq.queryRequest.put("OrderNo", paymentNum);
            tReq.queryRequest.put("QueryDetail", 1);
            JSON json = tReq.postRequest();
            String returnCode = json.GetKeyValue("ReturnCode");
            String errorMessage = json.GetKeyValue("ErrorMessage");
            if (returnCode.equals("0000")) {
                String orderInfo = json.GetKeyValue("Order");
                if (orderInfo.length() < 1) {
                    return;
                }
                Base64 base64 = new Base64();
                String orderDetail = new String(base64.decode(orderInfo));
                json.setJsonString(orderDetail);
                String tPayTypeId = json.GetKeyValue("PayTypeID");
                String tOrderNo = json.GetKeyValue("OrderNo");
                String tOrderDate = json.GetKeyValue("OrderDate");
                String tOrderTime = json.GetKeyValue("OrderTime");
                String tOrderAmount = json.GetKeyValue("OrderAmount");//元
                String tStatus = json.GetKeyValue("Status");
                System.out.println("tOrderNo:" + tOrderNo);
                System.out.println("tOrderDate:" + tOrderDate);
                System.out.println("tOrderTime:" + tOrderTime);
                System.out.println("tOrderAmount:" + tOrderAmount);
                System.out.println("tStatus:" + tStatus);
            } else {
                System.out.println(returnCode + ":" + errorMessage);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderQueryTest", e);
        }
    }

    public void orderRefundTest(String orderNum) {
        RefundRequest tRequest = new RefundRequest();
        tRequest.dicRequest.put("OrderDate", "2017/06/21"); //订单日期（必要信息）
        tRequest.dicRequest.put("OrderTime", "16:10:01"); //订单时间（必要信息）
        tRequest.dicRequest.put("OrderNo", "2017062116100103260137546313"); //原交易编号（必要信息）
        tRequest.dicRequest.put("NewOrderNo", "2017062116100103260137546316"); //交易编号（必要信息）
        tRequest.dicRequest.put("CurrencyCode", "156"); //交易币种（必要信息）
        tRequest.dicRequest.put("TrxAmount", "0.01"); //退货金额 （必要信息）
        //2、传送退款请求并取得退货结果
        JSON json = tRequest.postRequest();
        //3、判断退款结果状态，进行后续操作
        StringBuilder strMessage = new StringBuilder("");
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        if (ReturnCode.equals("0000")) {
            //4、退款成功
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
            System.out.println("OrderNo   = [" + json.GetKeyValue("OrderNo") + "]<br/>");
            System.out.println("NewOrderNo   = [" + json.GetKeyValue("NewOrderNo") + "]<br/>");
            System.out.println("TrxAmount = [" + json.GetKeyValue("TrxAmount") + "]<br/>");
            System.out.println("BatchNo   = [" + json.GetKeyValue("BatchNo") + "]<br/>");
            System.out.println("VoucherNo = [" + json.GetKeyValue("VoucherNo") + "]<br/>");
            System.out.println("HostDate  = [" + json.GetKeyValue("HostDate") + "]<br/>");
            System.out.println("HostTime  = [" + json.GetKeyValue("HostTime") + "]<br/>");
            System.out.println("iRspRef  = [" + json.GetKeyValue("iRspRef") + "]<br/>");
        } else {
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    }

}
