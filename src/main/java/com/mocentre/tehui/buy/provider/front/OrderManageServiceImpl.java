/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.provider.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.abc.trustpay.client.TrxException;
import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.act.enums.ActivityType;
import com.mocentre.tehui.act.enums.GroupStatus;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.model.GrouponDetail;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.act.service.IGrouponDetailService;
import com.mocentre.tehui.act.service.IGrouponService;
import com.mocentre.tehui.buy.enums.NotifyType;
import com.mocentre.tehui.buy.enums.OrderBillType;
import com.mocentre.tehui.buy.enums.OrderRefundStatus;
import com.mocentre.tehui.buy.enums.OrderSourceType;
import com.mocentre.tehui.buy.enums.OrderStatus;
import com.mocentre.tehui.buy.enums.OrderType;
import com.mocentre.tehui.buy.enums.PayStatus;
import com.mocentre.tehui.buy.enums.PayType;
import com.mocentre.tehui.buy.enums.RefundReason;
import com.mocentre.tehui.buy.mapper.OrderBillMapper;
import com.mocentre.tehui.buy.mapper.OrderMapper;
import com.mocentre.tehui.buy.mapper.PayConfigMapper;
import com.mocentre.tehui.buy.model.CashGoodsDetail;
import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.model.OrderLogistics;
import com.mocentre.tehui.buy.model.OrderPay;
import com.mocentre.tehui.buy.model.PayConfig;
import com.mocentre.tehui.buy.service.IOrderBillService;
import com.mocentre.tehui.buy.service.IOrderDetailService;
import com.mocentre.tehui.buy.service.IOrderLogisticsService;
import com.mocentre.tehui.buy.service.IOrderPayService;
import com.mocentre.tehui.buy.service.IOrderService;
import com.mocentre.tehui.buy.service.IPayConfigService;
import com.mocentre.tehui.common.SystemConfig;
import com.mocentre.tehui.common.constant.ConfigConstant;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.frontend.model.ActCashGoodsFTInstance;
import com.mocentre.tehui.frontend.model.ActCashOrderFTInstance;
import com.mocentre.tehui.frontend.model.ApplyRefundFTInstance;
import com.mocentre.tehui.frontend.model.CashGoodsFTInstance;
import com.mocentre.tehui.frontend.model.CashOrderFTInstance;
import com.mocentre.tehui.frontend.model.CashPayFTInstance;
import com.mocentre.tehui.frontend.model.CustomerAddressFTInstance;
import com.mocentre.tehui.frontend.model.OrderBillFTInstance;
import com.mocentre.tehui.frontend.model.OrderCashDetailFTInstance;
import com.mocentre.tehui.frontend.model.OrderCashFTInstance;
import com.mocentre.tehui.frontend.model.OrderDetailFTInstance;
import com.mocentre.tehui.frontend.model.OrderFTInstance;
import com.mocentre.tehui.frontend.model.PayOrderFTInstance;
import com.mocentre.tehui.frontend.model.WxPayDataFTInstance;
import com.mocentre.tehui.frontend.model.WxaPayDataFTInstance;
import com.mocentre.tehui.frontend.param.ActCashGoodsParam;
import com.mocentre.tehui.frontend.param.ActOrderPayParam;
import com.mocentre.tehui.frontend.param.ActPayOrderParam;
import com.mocentre.tehui.frontend.param.ApplyRefundParam;
import com.mocentre.tehui.frontend.param.CashGoodsParam;
import com.mocentre.tehui.frontend.param.CashierParam;
import com.mocentre.tehui.frontend.param.OrderBillParam;
import com.mocentre.tehui.frontend.param.OrderPayParam;
import com.mocentre.tehui.frontend.param.OrderQueryParam;
import com.mocentre.tehui.frontend.param.SubmitOrderParam;
import com.mocentre.tehui.frontend.service.OrderManageService;
import com.mocentre.tehui.goods.enums.CouponRelateType;
import com.mocentre.tehui.goods.enums.CouponStatus;
import com.mocentre.tehui.goods.enums.GoodsCheckedType;
import com.mocentre.tehui.goods.enums.GoodsShowType;
import com.mocentre.tehui.goods.model.CouponDetail;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.goods.service.ICouponDetailService;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.goods.service.IGoodsStorageService;
import com.mocentre.tehui.ntc.service.INtcMailService;
import com.mocentre.tehui.payment.abcpay.AbcData;
import com.mocentre.tehui.payment.abcpay.AbcPayRequest;
import com.mocentre.tehui.payment.abcpay.AbcPayResult;
import com.mocentre.tehui.payment.abcpay.Constant;
import com.mocentre.tehui.payment.abcpay.PayDicOrder;
import com.mocentre.tehui.payment.abcpay.PayDicReq;
import com.mocentre.tehui.payment.abcpay.PayOrderItem;
import com.mocentre.tehui.payment.abcpay.PayResultData;
import com.mocentre.tehui.payment.wxapay.ClientRequestHandler;
import com.mocentre.tehui.payment.wxapay.PrepayIdRequestHandler;
import com.mocentre.tehui.payment.wxapay.client.ClientResponseHandler;
import com.mocentre.tehui.payment.wxapay.util.WXUtil;
import com.mocentre.tehui.ps.mapper.CustomerAddressMapper;
import com.mocentre.tehui.ps.model.CustomerAddress;
import com.mocentre.tehui.ps.service.ICustomerAddressService;
import com.mocentre.tehui.shop.model.Shop;
import com.mocentre.tehui.shop.service.IShopService;

/**
 * 类OrderManageServiceImpl.java的实现描述：订单服务接口
 * 
 * @author sz.gong 2016年12月14日 下午5:13:14
 */
public class OrderManageServiceImpl implements OrderManageService {

    @Autowired
    private IGoodsService           goodsService;
    @Autowired
    private ICustomerAddressService cumAddsService;
    @Autowired
    private IPayConfigService       payConfigService;
    @Autowired
    private IGoodsStorageService    goodsStorageService;
    @Autowired
    private IOrderService           orderService;
    @Autowired
    private IOrderDetailService     orderDetailService;
    @Autowired
    private IOrderPayService        orderPayService;
    @Autowired
    private IOrderBillService       orderBillService;
    @Autowired
    private ICouponDetailService    couponDetailService;
    @Autowired
    private IShopService            shopService;
    @Autowired
    private IActivityGoodsService   actGoodsService;
    @Autowired
    private IGrouponService         grouponService;
    @Autowired
    private IGrouponDetailService   grouponDetailService;
    @Autowired
    private CustomerAddressMapper   customerAddressMapper;
    @Autowired
    private IOrderLogisticsService  orderLogisticsService;
    @Autowired
    private INtcMailService         ntcMailService;

    @Override
    public PlainResult<CashOrderFTInstance> submitToCashier(CashierParam buyParam) {

        PlainResult<CashOrderFTInstance> result = new PlainResult<CashOrderFTInstance>();
        result.setRequestId(buyParam.getRequestId());
        try {
            CashOrderFTInstance cashOrder = new CashOrderFTInstance();
            Long goodsId = buyParam.getGoodsId();
            Integer buyNum = buyParam.getBuyNum();
            Long customerId = buyParam.getCustomerId();
            String standardCode = buyParam.getGoodsSku();
            Long actGoodsId = buyParam.getActGoodsId();
            Long grouponId = buyParam.getGrouponId();
            boolean paramValid = buyNum == null || standardCode == null;
            paramValid = goodsId == null && actGoodsId == null;//商品和活动商品选其一
            if (paramValid) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            //1.是否活动商品
            if (actGoodsId != null && actGoodsId > 0) {
                //                ActivityGoods actGoods = actGoodsService.getActivityGoodsById(actGoodsId);
                ActivityGoods actGoods = actGoodsService.getActivityGoodsFromCache(actGoodsId);
                if (actGoods == null) {
                    result.setErrorMessage("1005", "不存在的活动商品");
                    return result;
                }
                Long gsId = actGoods.getGoodsId();
                if (goodsId != null && !goodsId.equals(gsId)) {
                    result.setErrorMessage("1005", "不存在的活动商品");
                    return result;
                }
                cashOrder.setOrderType(actGoods.getActivityType());
                cashOrder.setActGoodsId(actGoodsId);
                cashOrder.setGrouponId(grouponId);
            }
            //2.查询商品
            //Goods goods = goodsService.getGoodsById(goodsId);
            Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
            if (goods == null) {
                result.setErrorMessage("1001", "商品不存在");
                return result;
            }
            boolean goodsPass = GoodsShowType.ONSHELF.getCodeValue().equals(goods.getIsShow())
                    && GoodsCheckedType.CHECKPASS.getCodeValue().equals(goods.getIsChecked());
            if (!goodsPass) {
                result.setErrorMessage("1002", "商品已下架");
                return result;
            }
            String limitBuy = goods.getIsLimitBuy();
            if ("1".equals(limitBuy)) {
                Integer limitNums = goods.getLimitNums();
                if (limitNums == null || buyNum.intValue() > limitNums.intValue()) {
                    result.setErrorMessage("1003", "商品限制购买数量：" + limitNums);
                    return result;
                }
            }
            // 3.查询库存
            //GoodsStorage storage = goodsStorageService.queryGoodsStorageByStandard(goodsId, standardCode);
            actGoodsId = actGoodsId == null ? 0l : actGoodsId;
            GoodsStorage storage = goodsStorageService.queryGoodsStorageFromCache(goodsId, standardCode, actGoodsId);
            if (storage == null || storage.getStockNum() == 0) {
                result.setErrorMessage("1004", "商品已售罄");
                return result;
            }
            Long price = storage.getSalePrice();
            List<CashGoodsFTInstance> cashGoodsList = new ArrayList<CashGoodsFTInstance>();
            CashGoodsFTInstance cashGoods = new CashGoodsFTInstance();
            cashGoods.setGoodsId(goods.getId());
            cashGoods.setShowImg(goods.getImgCart());
            cashGoods.setCategoryId(goods.getCategoryId());
            cashGoods.setBuyNum(buyNum);
            cashGoods.setStandardCode(standardCode);
            cashGoods.setSellPrice(new BigDecimal(price).divide(new BigDecimal(100)));
            cashGoods.setActGoodsId(actGoodsId);
            cashGoodsList.add(cashGoods);
            //String standardJson = goods.getStandard();
            //String standard = goodsService.getGoodsStandardByCode(standardJson, standardCode);
            //4.查询地址
            CustomerAddressFTInstance cumAdsIns = null;
            if (customerId != null) {
                cashOrder.setIsLogin(1);
                CustomerAddress cumAds = cumAddsService.getDefAddressByCustomerId(customerId);
                if (cumAds != null) {
                    cumAdsIns = customerAddressMapper.toCustomerAddressFTInstance(cumAds);
                }
            } else {
                cashOrder.setIsLogin(0);
            }
            cashOrder.setCustomerAddress(cumAdsIns);
            cashOrder.setCashGoodsList(cashGoodsList);
            cashOrder.setGoodsNum(1);
            cashOrder.setTotalPrice(new BigDecimal(price * buyNum).divide(new BigDecimal(100)));
            //5.查询付款方式
            List<PayConfig> payConfigList = payConfigService.queryOpenPay();
            List<CashPayFTInstance> cashPayList = new ArrayList<CashPayFTInstance>();
            if (payConfigList != null) {
                for (PayConfig payConf : payConfigList) {
                    CashPayFTInstance cashPay = PayConfigMapper.toCashPayFTInstance(payConf);
                    cashPayList.add(cashPay);
                }
            }
            cashOrder.setPayList(cashPayList);
            cashOrder.setComefrom("2");
            result.setData(cashOrder);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("submitToCashier", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<String> submitOrder(SubmitOrderParam payParam) {

        PlainResult<String> result = new PlainResult<String>();
        result.setRequestId(payParam.getRequestId());
        Long customerId = payParam.getCustomerId();
        Long addsId = payParam.getAddressId();
        Long grouponId = payParam.getGrouponId();
        String comefrom = payParam.getComefrom();
        String flag = payParam.getFlag();
        OrderBillParam billParam = payParam.getBill();
        List<CashGoodsParam> goodsList = payParam.getCashGoodsList();
        boolean paramValid = StringUtils.isBlank(comefrom) || addsId == null || customerId == null || goodsList == null
                || goodsList.size() == 0;
        if (paramValid) {
            result.setErrorMessage("1000", "参数错误");
            return result;
        }
        try {
            Date nowDate = new Date();
            String paymentNum = DateUtils.formatTime2(nowDate) + String.valueOf(new Date().getTime()).substring(4, 10)
                    + CommUtil.randomSixInt();
            //临时变量针对秒杀
            String actType = null;
            Long goodsIdTemp = null;
            String goodsSkuTemp = null;
            Long actGoodsIdTemp = null;
            //所有商品库存集合
            List<GoodsStorage> storageList = new ArrayList<GoodsStorage>();
            //存放购买商品详情
            List<CashGoodsDetail> goodsDetailList = new ArrayList<CashGoodsDetail>();
            for (CashGoodsParam goodsParam : goodsList) {
                CashGoodsDetail goodsDetail = new CashGoodsDetail();
                Long goodsId = goodsParam.getGoodsId();
                String goodsSku = goodsParam.getStandardCode();
                Integer buyNum = goodsParam.getBuyNum();
                Long actGoodsId = goodsParam.getActGoodsId();
                actGoodsId = actGoodsId == null ? 0l : actGoodsId;
                if (goodsId == null || StringUtils.isBlank(goodsSku) || buyNum == null || buyNum == 0) {
                    result.setErrorMessage("1000", "参数错误");
                    return result;
                }
                //1.查询商品
                Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
                String showTitle = goods.getTitle().length() > 20 ? goods.getTitle().indexOf(0, 20) + "..." : goods
                        .getTitle();
                boolean goodsPass = GoodsShowType.ONSHELF.getCodeValue().equals(goods.getIsShow())
                        && GoodsCheckedType.CHECKPASS.getCodeValue().equals(goods.getIsChecked());
                if (!goodsPass) {
                    result.setErrorMessage("1001", "商品" + showTitle + "已下架");
                    return result;
                }
                String limitBuy = goods.getIsLimitBuy();
                if ("1".equals(limitBuy)) {
                    Integer limitNums = goods.getLimitNums();
                    if (limitNums == null || buyNum.intValue() > limitNums.intValue()) {
                        result.setErrorMessage("1002", "商品" + showTitle + "限制购买数量：" + limitNums);
                        return result;
                    }
                    //注意：对于判断购买商品数量，执行效率低
                    if (limitNums != null) {
                        Integer haveNum = orderService.getOrderGoodsSum(goodsId, actGoodsId, customerId);
                        if (buyNum.intValue() + haveNum.intValue() > limitNums.intValue()) {
                            result.setErrorMessage("1011", "商品" + showTitle + "已达购买上限");
                            return result;
                        }
                    }
                }
                //2.查询是不是参与活动商品
                if (actGoodsId != null && actGoodsId > 0) {
                    ActivityGoods actGoods = actGoodsService.getActivityGoodsFromCache(actGoodsId);
                    if (actGoods == null) {
                        result.setErrorMessage("1004", "未找到参与活动商品");
                        return result;
                    }
                    Date actBeginTime = actGoods.getBeginTime();
                    Date actEndTime = actGoods.getEndTime();
                    if (nowDate.before(actBeginTime) || nowDate.after(actEndTime)) {
                        result.setErrorMessage("1005", "活动已失效");
                        return result;
                    }
                    actType = actGoods.getActivityType();
                    if (ActivityType.SECKILL.getCodeValue().equals(actType)) {//秒杀只允许一种类型的商品
                        goodsIdTemp = goodsId;
                        goodsSkuTemp = goodsSku;
                        actGoodsIdTemp = actGoodsId;
                        if (goodsList.size() > 1) {
                            result.setErrorMessage("1008", "秒杀活动只允许购买一种商品");
                            return result;
                        }
                    }
                    //针对秒杀商品，判断是否能获取令牌
                    if (ActivityType.SECKILL.getCodeValue().equals(actType)) {
                        if (buyNum > 1) {//秒杀只允许一件商品
                            result.setErrorMessage("1007", "只允许购买一件商品");
                            return result;
                        }
                        boolean isExists = actGoodsService.getStorageToken(goodsId, goodsSku, actGoodsId);
                        if (!isExists) {
                            result.setErrorMessage("1006", showTitle + "购买人数过多，请稍后再试");
                            return result;
                        }
                    }
                }
                //3.查询库存
                GoodsStorage goodsStorage = goodsStorageService.queryGoodsStorageFromCache(goodsId, goodsSku,
                        actGoodsId);
                if (goodsStorage == null || goodsStorage.getStockNum().longValue() == 0) {
                    result.setErrorMessage("1003", "商品" + showTitle + "已售罄");
                    return result;
                }
                if (goodsStorage.getStockNum().longValue() < buyNum.longValue()) {
                    result.setErrorMessage("1003", "商品" + showTitle + "数量不足");
                    return result;
                }
                goodsDetail.setBuyNum(goodsParam.getBuyNum());
                goodsDetail.setGoodsId(goods.getId());
                goodsDetail.setGoodsImg(goods.getImgCart());
                goodsDetail.setShopId(goods.getShopId());
                goodsDetail.setSellPrice(goodsStorage.getSalePrice() == null ? 0l : goodsStorage.getSalePrice());
                goodsDetail.setOldPrice(goodsStorage.getOldPrice());
                goodsDetail.setGoodsCategory(goods.getCategoryId());
                goodsDetail.setGoodsStandard(goodsStorage.getStandardCode());
                goodsDetail.setGoodsStandardDes(goodsStorage.getDescribe());
                goodsDetail.setGoodsActGoodsId(goodsStorage.getSubGoodsId());
                goodsDetail.setGoodsName(goods.getTitle());
                goodsDetailList.add(goodsDetail);
                storageList.add(goodsStorage);
            }
            //4.查询地址
            CustomerAddress cumAddress = cumAddsService.getAddressById(addsId);
            if (cumAddress == null) {
                if (ActivityType.SECKILL.getCodeValue().equals(actType)) {//秒杀商品归还令牌
                    actGoodsService.putStorageToken(goodsIdTemp, goodsSkuTemp, actGoodsIdTemp);
                }
                result.setErrorMessage("1008", "收货地址为空");
                return result;
            }
            CustomerAddressFTInstance cumAddressIns = customerAddressMapper.toCustomerAddressFTInstance(cumAddress);
            //5.判断优惠券
            String couponSn = payParam.getCouponSn();
            if (StringUtils.isNotBlank(couponSn)) {
                CouponDetail couponDetail = couponDetailService.queryUnusedCoupon(customerId, couponSn);
                if (couponDetail == null) {
                    if (ActivityType.SECKILL.getCodeValue().equals(actType)) {//秒杀商品归还令牌
                        actGoodsService.putStorageToken(goodsIdTemp, goodsSkuTemp, actGoodsIdTemp);
                    }
                    result.setErrorMessage("1009", "优惠券不可用");
                    return result;
                } else {
                    String couponStatus = couponDetail.getStatus();
                    Date beginTime = couponDetail.getBeginTime();
                    Date endTime = couponDetail.getEndTime();
                    Date nowTime = new Date();
                    if (CouponStatus.USED.getCodeValue().equals(couponStatus)) {
                        if (ActivityType.SECKILL.getCodeValue().equals(actType)) {//秒杀商品归还令牌
                            actGoodsService.putStorageToken(goodsIdTemp, goodsSkuTemp, actGoodsIdTemp);
                        }
                        result.setErrorMessage("1009", "优惠券不可用");
                        return result;
                    }
                    if (nowTime.compareTo(beginTime) < 0 || nowTime.compareTo(endTime) > 0) {
                        if (ActivityType.SECKILL.getCodeValue().equals(actType)) {//秒杀商品归还令牌
                            actGoodsService.putStorageToken(goodsIdTemp, goodsSkuTemp, actGoodsIdTemp);
                        }
                        result.setErrorMessage("1009", "优惠券已过期");
                        return result;
                    }
                }
                Long couponMoney = couponDetail.getCouponMoney();//优惠券金额
                if (couponMoney > 0) {
                    String relType = couponDetail.getRelateType();
                    String typeIds = couponDetail.getTypeIds();
                    Long fullCut = couponDetail.getFullCut();
                    List<String> typeIdList = new ArrayList<String>();
                    if (StringUtils.isNotBlank(typeIds)) {
                        String[] typeIdArr = typeIds.split(",");
                        typeIdList = Arrays.asList(typeIdArr);
                    }
                    //存放无需计算优惠金额的实体
                    List<CashGoodsDetail> notCalcGoodsList = new ArrayList<CashGoodsDetail>();
                    //存放需要计算优惠金额的实体
                    List<CashGoodsDetail> needCalcGoodsList = new ArrayList<CashGoodsDetail>();
                    Long totalPrice = 0l;
                    if (CouponRelateType.GOODS.getCodeValue().equals(relType)) {//指定商品
                        //其中一件商品能使用优惠券，防止相同商品使用同一张优惠券
                        boolean pass = true;
                        for (CashGoodsDetail goodsDetail : goodsDetailList) {
                            Long goodsId = goodsDetail.getGoodsId();
                            Long realTotalPrice = goodsDetail.getSellPrice() * goodsDetail.getBuyNum();
                            if (pass && typeIdList.contains(String.valueOf(goodsId)) && realTotalPrice >= fullCut) {
                                goodsDetail.setCouponSn(couponSn);
                                goodsDetail.setCouponMoney(couponMoney);
                                needCalcGoodsList.add(goodsDetail);
                                pass = false;
                            } else {
                                notCalcGoodsList.add(goodsDetail);
                            }
                        }
                        if (pass) {//不满足条件，则使用的是无效优惠券，将优惠券编码置为null
                            couponSn = null;
                        }
                    } else if (CouponRelateType.CATEGORY.getCodeValue().equals(relType)) {//指定分类
                        for (CashGoodsDetail goodsDetail : goodsDetailList) {
                            Long categoryId = goodsDetail.getGoodsCategory();
                            if (typeIdList.contains(String.valueOf(categoryId))) {
                                totalPrice += goodsDetail.getSellPrice() * goodsDetail.getBuyNum();
                                needCalcGoodsList.add(goodsDetail);
                            } else {
                                notCalcGoodsList.add(goodsDetail);
                            }
                        }
                    } else if (CouponRelateType.NO.getCodeValue().equals(relType)) {//全类
                        for (CashGoodsDetail goodsDetail : goodsDetailList) {
                            totalPrice += goodsDetail.getSellPrice() * goodsDetail.getBuyNum();
                            needCalcGoodsList.add(goodsDetail);
                        }
                    } else if (CouponRelateType.SHOP.getCodeValue().equals(relType)) {//指定店铺
                        for (CashGoodsDetail goodsDetail : goodsDetailList) {
                            Long shopId = goodsDetail.getShopId();
                            if (typeIdList.contains(String.valueOf(shopId))) {
                                totalPrice += goodsDetail.getSellPrice() * goodsDetail.getBuyNum();
                                needCalcGoodsList.add(goodsDetail);
                            } else {
                                notCalcGoodsList.add(goodsDetail);
                            }
                        }
                    }
                    if (CouponRelateType.CATEGORY.getCodeValue().equals(relType)
                            || CouponRelateType.NO.getCodeValue().equals(relType)
                            || CouponRelateType.SHOP.getCodeValue().equals(relType)) {//按比例分配优惠券的金额
                        if (totalPrice >= fullCut) {//判断是否满足满减条件，若满足条件则赋予指定优惠券
                            BigDecimal totalPriceBd = new BigDecimal(totalPrice);
                            BigDecimal couponMoneyBd = new BigDecimal(couponMoney);
                            Long nextPrice = 0L;
                            for (int i = 0; i < needCalcGoodsList.size(); i++) {
                                CashGoodsDetail needGoodsDetail = needCalcGoodsList.get(i);
                                Long sellTotalPrice = needGoodsDetail.getSellPrice() * needGoodsDetail.getBuyNum();
                                BigDecimal sellTotalPriceBd = new BigDecimal(sellTotalPrice);
                                if (i == needCalcGoodsList.size() - 1) {
                                    needGoodsDetail.setCouponMoney(couponMoney - nextPrice);
                                } else {
                                    Long calcMoney = Math.round(sellTotalPriceBd
                                            .divide(totalPriceBd, 2, BigDecimal.ROUND_HALF_UP).multiply(couponMoneyBd)
                                            .doubleValue());
                                    nextPrice += calcMoney;
                                    needGoodsDetail.setCouponMoney(calcMoney);
                                }
                                needGoodsDetail.setCouponSn(couponSn);
                            }
                        } else {//不满足条件，则使用的是无效优惠券，将优惠券编码置为null
                            couponSn = null;
                        }
                    }
                    goodsDetailList.clear();
                    goodsDetailList.addAll(notCalcGoodsList);
                    goodsDetailList.addAll(needCalcGoodsList);
                }
            }
            //将同一店铺下的商品放在一起
            Map<Long, List<CashGoodsDetail>> goodsMap = new HashMap<Long, List<CashGoodsDetail>>();
            for (CashGoodsDetail goodsDetail : goodsDetailList) {
                Long shopId = goodsDetail.getShopId();
                List<CashGoodsDetail> godsList = goodsMap.get(shopId);
                if (godsList == null || godsList.size() == 0) {
                    godsList = new ArrayList<CashGoodsDetail>();
                }
                godsList.add(goodsDetail);
                goodsMap.put(shopId, godsList);
            }
            String prov = cumAddressIns.getProvinceName();
            String city = cumAddressIns.getCityName();
            String area = cumAddressIns.getAreaName();
            String adds = cumAddressIns.getAddress();
            //支付
            List<OrderPay> orderPayList = new ArrayList<OrderPay>();
            //发票
            List<OrderBill> orderBillList = null;
            //订单列表
            List<Order> orderList = new ArrayList<Order>();
            //订单详情列表
            List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
            for (Map.Entry<Long, List<CashGoodsDetail>> entry : goodsMap.entrySet()) {
                Long shopId = entry.getKey();
                String orderNum = DateUtils.formatTime2(nowDate)
                        + String.valueOf(new Date().getTime()).substring(4, 10) + CommUtil.randomSixInt();
                List<CashGoodsDetail> godsList = entry.getValue();
                Order order = new Order();
                if ("1".equals(flag)) {
                    order.setSourceType(OrderSourceType.TEHUI.getCodeValue());
                } else if ("2".equals(flag)) {
                    order.setSourceType(OrderSourceType.ABC.getCodeValue());
                }
                order.setAddress(prov + city + area + adds);
                order.setTelephone(cumAddress.getTelephone());
                order.setAddressId(cumAddress.getId());
                order.setRecipient(cumAddress.getRecipient());
                order.setOrderNum(orderNum);
                order.setOrderStatus(OrderStatus.WAIT_PAY.getCodeValue());
                order.setOrderTime(nowDate);
                order.setRemark(payParam.getNote());
                order.setShopId(shopId);
                order.setTransFee(0L);//配送费
                order.setCustomerId(customerId);
                order.setPaymentNum(paymentNum);
                if (OrderType.SECKILL.getCodeValue().equals(actType)
                        || OrderType.GROUPON.getCodeValue().equals(actType)) {
                    order.setOrderType(actType);
                    order.setTypeId(actGoodsIdTemp);
                } else {
                    order.setOrderType(OrderType.OTHER.getCodeValue());
                }
                if (billParam == null) {
                    order.setIsInvoice(0);
                } else {
                    orderBillList = new ArrayList<OrderBill>();
                    order.setIsInvoice(1);
                    OrderBill orderBill = OrderBillMapper.toOrderBill(billParam, customerId, orderNum);
                    orderBillList.add(orderBill);
                }
                Long couponTotalMoney = 0l;//优惠总金额
                Long sellTotalPrice = 0l;//购买总金额
                boolean isUseCoupon = false;
                if (godsList != null) {
                    for (CashGoodsDetail goodsDetail : godsList) {
                        OrderDetail orderDetail = new OrderDetail();
                        Long goodsId = goodsDetail.getGoodsId();
                        String couponsn = goodsDetail.getCouponSn();//可能为空
                        String orderDetailNum = DateUtils.formatTime2(nowDate)
                                + String.valueOf(new Date().getTime()).substring(4, 10) + CommUtil.randomSixInt();
                        Long cMoney = goodsDetail.getCouponMoney() == null ? 0l : goodsDetail.getCouponMoney();
                        Long sellPrice = goodsDetail.getSellPrice() == null ? 0 : goodsDetail.getSellPrice();
                        Integer buyNum = goodsDetail.getBuyNum();
                        Long tPrice = sellPrice * buyNum;
                        if (cMoney > tPrice) {//防止购买指定商品优惠券金额大于购买金额，使得超出部分的金额用于购买其他商品
                            couponTotalMoney += tPrice;
                        } else {
                            couponTotalMoney += cMoney;
                        }
                        sellTotalPrice += tPrice;
                        orderDetail.setOrderNum(orderNum);
                        orderDetail.setGoodsId(goodsId);
                        orderDetail.setCouponSn(couponsn);
                        orderDetail.setCouponMoney(cMoney);
                        orderDetail.setGoodsAmount(buyNum);
                        orderDetail.setGoodsRealPrice(sellPrice);
                        orderDetail.setGoodsPrice(goodsDetail.getOldPrice());
                        orderDetail.setGoodsImg(goodsDetail.getGoodsImg());
                        orderDetail.setGoodsStandard(goodsDetail.getGoodsStandard());
                        orderDetail.setGoodsStandardDes(goodsDetail.getGoodsStandardDes());
                        orderDetail.setGoodsCategory(goodsDetail.getGoodsCategory());
                        orderDetail.setGoodsName(goodsDetail.getGoodsName());
                        orderDetail.setGoodsActGoodsId(goodsDetail.getGoodsActGoodsId());
                        orderDetail.setOrderDetailNum(orderDetailNum);
                        orderDetail.setGmtCreated(nowDate);
                        orderDetail.setGmtModified(nowDate);
                        orderDetail.setRefundStatus(OrderRefundStatus.UNREFUND.getCodeValue());
                        if (StringUtils.isNotBlank(couponsn)) {//订单详情中是否有用优惠券的
                            isUseCoupon = true;
                        }
                        orderDetailList.add(orderDetail);
                    }
                }
                if (isUseCoupon) {//订单详情中是否使用过优惠券。若使用，则订单使用过优惠券
                    order.setCouponSn(couponSn);
                    order.setCouponMoney(couponTotalMoney);
                }
                Long orderPayTotalPrice = sellTotalPrice - couponTotalMoney;
                order.setTotalPrice(orderPayTotalPrice < 0l ? 0l : orderPayTotalPrice);
                order.setGmtCreated(nowDate);
                order.setGmtModified(nowDate);
                orderList.add(order);
                OrderPay orderPay = new OrderPay();
                orderPay.setOrderNum(orderNum);
                orderPay.setPaymentNum(paymentNum);
                orderPay.setPayType(payParam.getPayType());
                orderPay.setPayStatus(PayStatus.WAIT.getCodeValue());
                orderPay.setPayDate(nowDate);
                orderPay.setOrderAmount(orderPayTotalPrice < 0l ? 0l : orderPayTotalPrice);
                orderPay.setGmtCreated(nowDate);
                orderPay.setGmtModified(nowDate);
                orderPayList.add(orderPay);
            }
            Boolean suc = orderService.saveOrder(orderList, orderDetailList, orderBillList, orderPayList, storageList,
                    comefrom, customerId, couponSn);
            if (!suc) {
                result.setErrorMessage("1010", "操作失败");
            }
            result.setData(paymentNum);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("submitOrder", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<PayOrderFTInstance> payment(String paymentNum, String flag, String requestId) {

        PlainResult<PayOrderFTInstance> result = new PlainResult<PayOrderFTInstance>();
        result.setRequestId(requestId);
        PayOrderFTInstance payOrderIns = new PayOrderFTInstance();
        LoggerUtil.tehuiLog.info("支付编码：" + paymentNum);
        try {
            //判断是否支付成功
            List<OrderPay> orderPayList = orderPayService.queryOrderPay(paymentNum);
            if (orderPayList != null) {
                Boolean havePayed = false;
                Date payDate = null;
                Long money = 0l;
                String payType = null;
                for (OrderPay orderPay : orderPayList) {
                    payDate = orderPay.getPayDate();
                    if (PayStatus.SUCCESS.getCodeValue().equals(orderPay.getPayStatus())) {
                        havePayed = true;
                    }
                    money += orderPay.getOrderAmount();
                    payType = orderPay.getPayType();
                }
                BigDecimal amount = new BigDecimal(money).divide(new BigDecimal(100));
                payOrderIns.setHavePayed(havePayed);
                payOrderIns.setTotalMoney(amount);
                //是否已经支付
                if (havePayed) {
                    result.setData(payOrderIns);
                    return result;
                }
                //判断是否需要跳转到支付页面
                if (money == 0) {
                    result.setData(payOrderIns);
                    return result;
                }
                //k码支付三步
                if (PayType.KMPAY.getCodeValue().equals(payType)) {
                    String PayTypeID = Constant.PAY_TYPE_ID_IMMEDIATEPAY;
                    String OrderDate = DateUtils.format(payDate, "yyyy/MM/dd");
                    String OrderTime = DateUtils.format(payDate, "HH:mm:ss");
                    String OrderNo = paymentNum;
                    String OrderAmount = String.valueOf(amount);
                    String CommodityType = Constant.COMMODITY_TYPE_XFL_CTL;
                    String InstallmentMark = Constant.INSTALL_MENT_MARK_NO;
                    //1
                    PayDicOrder dicOrder = new PayDicOrder(PayTypeID, OrderDate, OrderTime, OrderNo, OrderAmount,
                            CommodityType, InstallmentMark);
                    //2
                    List<PayOrderItem> orderItemList = new ArrayList<PayOrderItem>();
                    for (OrderPay orderPay : orderPayList) {
                        String orderNum = orderPay.getOrderNum();
                        List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(orderNum);
                        if (orderDetailList != null) {
                            for (OrderDetail orderDetail : orderDetailList) {
                                PayOrderItem orderItem = new PayOrderItem(orderDetail.getGoodsName());
                                orderItemList.add(orderItem);
                            }
                        }
                    }
                    //3
                    String PaymentType = Constant.PAYMENT_TYPE_ZFFSHB;
                    String PaymentLinkType = Constant.PAYMENT_LINK_TYPE_SJWLJR;
                    String NotifyType = Constant.NOTIFY_TYPE_ASYNC;
                    String IsBreakAccount = Constant.IS_BREAK_ACCOUNT_NO;
                    String ResultNotifyURL = "";
                    LoggerUtil.tehuiLog.info("flag:" + flag);
                    if ("1".equals(flag)) {
                        ResultNotifyURL = SystemConfig.INSTANCE.getValue(ConfigConstant.ABC_NOTIFY_ASYNC_URL);
                    } else if ("2".equals(flag)) {
                        ResultNotifyURL = SystemConfig.INSTANCE.getValue(ConfigConstant.ABC_MALL_NOTIFY_ASYNC_URL);
                    }
                    LoggerUtil.tehuiLog.info("ResultNotifyURL：" + ResultNotifyURL);
                    PayDicReq dicReq = new PayDicReq(PaymentType, PaymentLinkType, NotifyType, ResultNotifyURL,
                            IsBreakAccount);
                    AbcPayRequest payReq = new AbcPayRequest(dicOrder, orderItemList, dicReq);
                    PlainResult<AbcData> dataResult = payReq.sendRequest();
                    AbcData data = dataResult.getData();
                    payOrderIns.setHavePayed(false);
                    payOrderIns.setErrorMessage(data.getErrorMessage());
                    payOrderIns.setReturnCode(data.getReturnCode());
                    if (dataResult.isSuccess()) {
                        payOrderIns.setPaymentURL(data.getPaymentURL());
                    } else {
                        result.setErrorMessage(data.getReturnCode(), data.getErrorMessage());
                    }
                    LoggerUtil.tehuiLog.info("支付编码：" + paymentNum + "，支付时间" + DateUtils.formatTime(payDate) + "，支付金额"
                            + amount + "，支付状态：" + dataResult.isSuccess() + "，失败编码原因：(" + data.getReturnCode() + ")"
                            + data.getErrorMessage());
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("payment", e);
            result.setErrorMessage("999", "系统异常");
        }
        result.setData(payOrderIns);
        return result;
    }

    @Override
    public ListResult<OrderFTInstance> orderList(OrderQueryParam orderParam) {
        ListResult<OrderFTInstance> result = new ListResult<OrderFTInstance>();
        result.setRequestId(orderParam.getRequestId());
        try {
            List<OrderFTInstance> orderInsList = new ArrayList<OrderFTInstance>();
            Long customerId = orderParam.getCustomerId();
            String type = orderParam.getType();
            Integer start = orderParam.getStart();
            Integer length = orderParam.getLength();
            PageInfo<Order> orderPage = orderService.queryOrderPage(customerId, type, start, length);
            List<Order> orderList = orderPage.getRows();
            if (orderList != null) {
                for (Order order : orderList) {
                    Long shopId = order.getShopId();
                    String orderNum = order.getOrderNum();
                    Shop shop = shopService.queryShop(shopId);
                    List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(orderNum);
                    OrderFTInstance orderIns = OrderMapper.toOrderFTInstance(order, orderDetailList, shop);
                    orderInsList.add(orderIns);
                }
            }
            result.setData(orderInsList);
            result.setCount((int) orderPage.getTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderList", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<CashOrderFTInstance> orderSubmit(String orderNum, Long customerId, String requestId) {

        PlainResult<CashOrderFTInstance> result = new PlainResult<CashOrderFTInstance>();
        result.setRequestId(requestId);
        try {
            CashOrderFTInstance cashOrder = new CashOrderFTInstance();
            Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
            if (order == null) {
                result.setErrorMessage("1000", "订单不存在");
                return result;
            }
            String status = order.getOrderStatus();
            if (!OrderStatus.WAIT_PAY.getCodeValue().equals(status)) {
                result.setErrorMessage("1001", "订单不能支付");
                return result;
            }
            CustomerAddressFTInstance cumAdsIns = null;
            CustomerAddress cumAds = cumAddsService.getAddressById(order.getAddressId());
            if (cumAds != null) {
                cumAdsIns = customerAddressMapper.toCustomerAddressFTInstance(cumAds);
            }
            List<CashGoodsFTInstance> cashGoodsList = new ArrayList<CashGoodsFTInstance>();
            List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(orderNum);
            Long totalPrice = 0l;//商品实际购买价格
            for (OrderDetail orderDetail : orderDetailList) {
                Long grPrice = orderDetail.getGoodsRealPrice();
                Integer buyNum = orderDetail.getGoodsAmount();
                CashGoodsFTInstance cashGoods = new CashGoodsFTInstance();
                cashGoods.setBuyNum(buyNum);
                cashGoods.setGoodsId(orderDetail.getGoodsId());
                cashGoods.setSellPrice(new BigDecimal(grPrice).divide(new BigDecimal(100)));//商品购买价
                cashGoods.setShowImg(orderDetail.getGoodsImg());
                cashGoods.setCategoryId(orderDetail.getGoodsCategory());
                cashGoods.setStandardCode(orderDetail.getGoodsStandard());
                cashGoodsList.add(cashGoods);
                totalPrice += grPrice * buyNum;
            }
            List<PayConfig> payConfigList = payConfigService.queryOpenPay();
            List<CashPayFTInstance> cashPayList = new ArrayList<CashPayFTInstance>();
            if (payConfigList != null) {
                for (PayConfig payConf : payConfigList) {
                    CashPayFTInstance cashPay = PayConfigMapper.toCashPayFTInstance(payConf);
                    cashPayList.add(cashPay);
                }
            }
            OrderBill bill = orderBillService.queryBillByOrder(orderNum);
            OrderBillFTInstance billIns = null;
            if (bill != null) {
                billIns = OrderBillMapper.toOrderBillFTInstance(bill);
            }
            cashOrder.setCustomerAddress(cumAdsIns);
            cashOrder.setGoodsNum(orderDetailList.size());
            cashOrder.setTotalPrice(new BigDecimal(totalPrice).divide(new BigDecimal(100)));
            cashOrder.setCashGoodsList(cashGoodsList);
            cashOrder.setPayList(cashPayList);
            cashOrder.setBill(billIns);
            result.setData(cashOrder);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderSubmit", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<String> orderPay(OrderPayParam payParam) {

        PlainResult<String> result = new PlainResult<String>();
        result.setRequestId(payParam.getRequestId());
        try {
            Date nowDate = new Date();
            Long customerId = payParam.getCustomerId();
            String orderNum = payParam.getOrderNum();
            Long addsId = payParam.getAddressId();
            boolean paramValid = customerId == null || StringUtils.isBlank(orderNum) || addsId == null;
            if (paramValid) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            OrderBillParam billParam = payParam.getBill();
            Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
            if (order == null) {
                result.setErrorMessage("1001", "订单不存在");
                return result;
            }
            String status = order.getOrderStatus();
            if (!OrderStatus.WAIT_PAY.getCodeValue().equals(status)) {
                result.setErrorMessage("1002", "订单不能支付");
                return result;
            }
            CustomerAddress cumAddress = cumAddsService.getAddressById(addsId);
            if (cumAddress == null) {
                result.setErrorMessage("1003", "收货地址为空");
                return result;
            }
            CustomerAddressFTInstance cumAddressIns = customerAddressMapper.toCustomerAddressFTInstance(cumAddress);
            List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(orderNum);
            //判断优惠券
            String couponSn = payParam.getCouponSn();
            couponSn = StringUtils.isBlank(couponSn) ? "" : couponSn;
            Long couponMoney = 0L;
            if (StringUtils.isNotBlank(couponSn)) {
                CouponDetail couponDetail = couponDetailService.queryUnusedCoupon(customerId, couponSn);
                if (couponDetail == null) {
                    result.setErrorMessage("1004", "优惠券不可用");
                    return result;
                } else {
                    String couponStatus = couponDetail.getStatus();
                    Date beginTime = couponDetail.getBeginTime();
                    Date endTime = couponDetail.getEndTime();
                    Date nowTime = new Date();
                    if (CouponStatus.USED.getCodeValue().equals(couponStatus)) {
                        result.setErrorMessage("1004", "优惠券不可用");
                        return result;
                    }
                    if (nowTime.compareTo(beginTime) < 0 || nowTime.compareTo(endTime) > 0) {
                        result.setErrorMessage("1005", "优惠券已过期");
                        return result;
                    }
                }
                couponMoney = couponDetail.getCouponMoney();//优惠券金额
                Long fullCut = couponDetail.getFullCut();
                if (couponMoney > 0) {
                    //分开计算优惠金额-按照付款比例计算优惠金额
                    String relType = couponDetail.getRelateType();
                    String typeIds = couponDetail.getTypeIds();
                    List<String> typeIdList = new ArrayList<String>();
                    if (StringUtils.isNotBlank(typeIds)) {
                        String[] typeIdArr = typeIds.split(",");
                        typeIdList = Arrays.asList(typeIdArr);
                    }
                    //存放无需计算优惠金额的实体
                    List<OrderDetail> notCalcList = new ArrayList<OrderDetail>();
                    //存放需要计算优惠金额的实体
                    List<OrderDetail> needCalcList = new ArrayList<OrderDetail>();
                    Long totalPrice = 0l;
                    if (CouponRelateType.GOODS.getCodeValue().equals(relType)) {//指定商品
                        //其中一件商品能使用优惠券，防止相同商品使用同一张优惠券
                        boolean pass = true;
                        for (OrderDetail orderDetail : orderDetailList) {
                            Long goodsId = orderDetail.getGoodsId();
                            Long realTotalPrice = orderDetail.getGoodsRealPrice() * orderDetail.getGoodsAmount();
                            if (pass && typeIdList.contains(String.valueOf(goodsId)) && realTotalPrice >= fullCut) {
                                orderDetail.setCouponSn(couponSn);
                                orderDetail.setCouponMoney(couponMoney);
                                needCalcList.add(orderDetail);
                                pass = false;
                            } else {
                                notCalcList.add(orderDetail);
                            }
                        }
                        //                        if (pass) {//不满足条件，则使用的是无效优惠券，将优惠券编码置为""
                        //                            couponSn = "";
                        //                        }
                    } else if (CouponRelateType.CATEGORY.getCodeValue().equals(relType)) {//指定分类
                        for (OrderDetail orderDetail : orderDetailList) {
                            Long categoryId = orderDetail.getGoodsCategory();
                            if (typeIdList.contains(String.valueOf(categoryId))) {
                                totalPrice += orderDetail.getGoodsRealPrice() * orderDetail.getGoodsAmount();
                                needCalcList.add(orderDetail);
                            } else {
                                notCalcList.add(orderDetail);
                            }
                        }
                    } else if (CouponRelateType.NO.getCodeValue().equals(relType)) {//全类
                        for (OrderDetail orderDetail : orderDetailList) {
                            totalPrice += orderDetail.getGoodsRealPrice() * orderDetail.getGoodsAmount();
                            needCalcList.add(orderDetail);
                        }
                    } else if (CouponRelateType.SHOP.getCodeValue().equals(relType)) {//指定店铺
                        Long shopId = order.getShopId();
                        Long cMoney = order.getCouponMoney() == null ? 0l : order.getCouponMoney();
                        totalPrice = order.getTotalPrice() + cMoney;
                        if (typeIdList.contains(String.valueOf(shopId))) {
                            needCalcList.addAll(orderDetailList);
                        } else {
                            notCalcList.addAll(orderDetailList);
                        }
                    }
                    if (CouponRelateType.CATEGORY.getCodeValue().equals(relType)
                            || CouponRelateType.NO.getCodeValue().equals(relType)
                            || CouponRelateType.SHOP.getCodeValue().equals(relType)) {//按比例分配优惠券的金额
                        if (totalPrice >= fullCut) {//判断是否满足满减条件，若满足条件则赋予指定优惠券
                            BigDecimal totalPriceBd = new BigDecimal(totalPrice);
                            BigDecimal couponMoneyBd = new BigDecimal(couponMoney);
                            Long nextPrice = 0L;
                            for (int i = 0; i < needCalcList.size(); i++) {
                                OrderDetail needOrderDetail = needCalcList.get(i);
                                Long sellPrice = needOrderDetail.getGoodsRealPrice();
                                BigDecimal sellPriceBd = new BigDecimal(sellPrice);
                                if (i == needCalcList.size() - 1) {
                                    needOrderDetail.setCouponMoney(couponMoney - nextPrice);
                                } else {
                                    Long calcMoney = Math.round(sellPriceBd
                                            .divide(totalPriceBd, 2, BigDecimal.ROUND_HALF_UP).multiply(couponMoneyBd)
                                            .doubleValue());
                                    nextPrice += calcMoney;
                                    needOrderDetail.setCouponMoney(calcMoney);
                                }
                                needOrderDetail.setCouponSn(couponSn);
                            }
                            orderDetailList.clear();
                            orderDetailList.addAll(notCalcList);
                            orderDetailList.addAll(needCalcList);
                        } else {
                            LoggerUtil.tehuiLog.info("订单编号：" + orderNum + "使用无效优惠券");
                        }
                    }
                }
            }
            OrderBill orderBill = null;
            if (billParam == null) {
                order.setIsInvoice(0);
            } else {
                order.setIsInvoice(1);
                orderBill = new OrderBill();
                orderBill.setBillHeader(billParam.getBillHeader());
                orderBill.setBillRemark(billParam.getBillRemark());
                if ("1".equals(billParam.getBillType())) {
                    orderBill.setBillType(OrderBillType.PERSONAL.getCodeValue());
                } else if ("2".equals(billParam.getBillType())) {
                    orderBill.setBillType(OrderBillType.COMPAY.getCodeValue());
                }
                orderBill.setCustomerId(customerId);
                orderBill.setOrderNum(orderNum);
            }
            String prov = cumAddressIns.getProvinceName();
            String city = cumAddressIns.getCityName();
            String area = cumAddressIns.getAreaName();
            String adds = cumAddressIns.getAddress();
            String paymentNum = DateUtils.formatTime2(nowDate) + String.valueOf(new Date().getTime()).substring(4, 10)
                    + CommUtil.randomSixInt();
            Long orderTotalPrice = 0l;//订单总金额 
            Long couponTotalMoney = 0l;//优惠总金额
            //重新计算订单价格
            for (OrderDetail orderDetail : orderDetailList) {
                Long cMoney = 0l;
                if (StringUtils.isNotBlank(couponSn)) {//使用优惠券
                    cMoney = orderDetail.getCouponMoney() == null ? 0l : orderDetail.getCouponMoney();
                } else {
                    orderDetail.setCouponMoney(0l);
                    orderDetail.setCouponSn("");
                }
                Long sellPrice = orderDetail.getGoodsRealPrice() == null ? 0 : orderDetail.getGoodsRealPrice();
                Integer buyNum = orderDetail.getGoodsAmount();
                Long tPrice = sellPrice * buyNum;
                orderTotalPrice += tPrice;
                if (cMoney > tPrice) {//防止购买指定商品优惠券金额大于购买金额，使得超出部分的金额用于购买其他商品
                    couponTotalMoney += tPrice;
                } else {
                    couponTotalMoney += cMoney;
                }
            }
            Long orderPayTotalPrice = orderTotalPrice - couponTotalMoney;//订单实际支付金额
            order.setTotalPrice(orderPayTotalPrice);
            order.setRemark(payParam.getNote());
            order.setTransFee(0L);//配送费
            order.setCouponSn(couponSn);
            order.setCouponMoney(couponTotalMoney);
            order.setAddress(prov + city + area + adds);
            order.setTelephone(cumAddress.getTelephone());
            order.setAddressId(cumAddress.getId());
            order.setRecipient(cumAddress.getRecipient());
            order.setPaymentNum(paymentNum);
            OrderPay orderPay = new OrderPay();
            orderPay.setOrderNum(orderNum);
            orderPay.setPaymentNum(paymentNum);
            orderPay.setPayType(payParam.getPayType());
            orderPay.setPayStatus(PayStatus.WAIT.getCodeValue());
            orderPay.setPayDate(new Date());
            orderPay.setOrderAmount(orderPayTotalPrice);
            orderService.updateOrder(order, orderDetailList, orderBill, orderPay);
            result.setData(paymentNum);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderPay", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<OrderDetailFTInstance> orderDetail(Long customerId, String orderNum, String requestId) {

        PlainResult<OrderDetailFTInstance> result = new PlainResult<OrderDetailFTInstance>();
        result.setRequestId(requestId);
        try {
            OrderDetailFTInstance orderDetailIns = new OrderDetailFTInstance();
            Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
            if (order == null) {
                result.setErrorMessage("1000", "订单查询失败");
                return result;
            }
            Long shopId = order.getShopId();
            List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(orderNum);
            Shop shop = shopService.queryShop(shopId);
            Groupon groupon = null;
            List<String> imgList = new ArrayList<String>();
            if (OrderType.GROUPON.getCodeValue().equals(order.getOrderType())) {//团购订单
                groupon = grouponService.getGrouponById(order.getTypeId());
                if (groupon.getTakeNum().intValue() != groupon.getGrouponNum()) {
                    List<GrouponDetail> grouponDetailList = grouponDetailService.getByGourponId(groupon.getId());
                    if (grouponDetailList != null) {
                        for (GrouponDetail gd : grouponDetailList) {
                            imgList.add(gd.getTakeProfile());
                        }
                    }
                }
            }
            OrderLogistics logistics = null;
            Long orderId = order.getId();
            String orderStatus = order.getOrderStatus();
            if (OrderStatus.WAIT_ACCEPT.getCodeValue().equals(orderStatus)
                    || OrderStatus.DEAL_SUC.getCodeValue().equals(orderStatus)) {
                logistics = orderLogisticsService.queryLogisticsByOrder(orderId);
            }
            orderDetailIns = OrderMapper.toOrderDetailFTInstance(order, orderDetailList, logistics, shop, groupon,
                    imgList);
            result.setData(orderDetailIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderDetail", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<String> abcAsyncNotify(String msg) {
        PlainResult<String> result = new PlainResult<String>();
        try {
            AbcPayResult payResult = new AbcPayResult(msg);
            PayResultData resultData = payResult.getResult();
            boolean success = resultData.isSuccess();
            OrderPay orderPay = new OrderPay();
            String paymentNum = resultData.getOrderNo();
            orderPay.setPaymentNum(paymentNum);
            LoggerUtil.tehuiLog.info("农行支付回调。订单编号：" + paymentNum + "，回调付款状态：" + success + "，returnCode："
                    + resultData.getReturnCode() + "，errorMsg：" + resultData.getErrorMessage());
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
                List<OrderPay> orderPayList = orderPayService.queryOrderPay(paymentNum);
                if (orderPayList == null) {
                    return result;
                }
                for (OrderPay op : orderPayList) {
                    if (PayStatus.SUCCESS.getCodeValue().equals(op.getPayStatus())) {
                        return result;
                    }
                }
                orderPay.setPayStatus(PayStatus.SUCCESS.getCodeValue());
                orderService.updateOrderAndPay(orderPay, paymentNum);
                //发送邮件
                List<String> orderNumList = new ArrayList<String>();
                for (OrderPay pay : orderPayList) {
                    orderNumList.add(pay.getOrderNum());
                }
                ntcMailService.createBuyNotice(orderNumList);
                result.setData(amount);
            } else {
                orderPay.setPayStatus(PayStatus.FAIL.getCodeValue());
                orderPayService.updateOrderPay(paymentNum, orderPay);
                result.setErrorMessage("1000", "付款失败");
            }
        } catch (TrxException e) {
            LoggerUtil.tehuiLog.error("abcAsyncNotify", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<ActCashOrderFTInstance> actSubmitToCashier(CashierParam buyParam) {

        PlainResult<ActCashOrderFTInstance> result = new PlainResult<ActCashOrderFTInstance>();
        result.setRequestId(buyParam.getRequestId());
        try {
            ActCashOrderFTInstance cashOrder = new ActCashOrderFTInstance();
            Long goodsId = buyParam.getGoodsId();
            Integer buyNum = buyParam.getBuyNum();
            Long customerId = buyParam.getCustomerId();
            String standardCode = buyParam.getGoodsSku();
            Long actGoodsId = buyParam.getActGoodsId();
            Long grouponId = buyParam.getGrouponId();
            //Goods goods = goodsService.getGoodsById(goodsId);
            Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
            if (goods == null) {
                result.setErrorMessage("1001", "商品不存在");
                return result;
            }
            //GoodsStorage storage = goodsStorageService.queryGoodsStorageByStandard(goodsId, standardCode, actGoodsId);
            GoodsStorage storage = goodsStorageService.queryGoodsStorageFromCache(goodsId, standardCode, actGoodsId);
            if (storage == null || storage.getStockNum() == 0) {
                result.setErrorMessage("1002", "商品已售罄");
                return result;
            }
            //商品单价
            Long price = storage.getSalePrice();
            ActCashGoodsFTInstance cashGoods = new ActCashGoodsFTInstance();
            cashGoods.setGoodsId(goods.getId());
            cashGoods.setShowImg(goods.getImgCart());
            cashGoods.setBuyNum(buyNum);
            cashGoods.setStandardCode(standardCode);
            cashGoods.setSellPrice(new BigDecimal(price).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN));
            //活动类型（秒杀、团购）
            //ActivityGoods actGoods = actGoodsService.getActivityGoodsById(actGoodsId);
            ActivityGoods actGoods = actGoodsService.getActivityGoodsFromCache(actGoodsId);
            if (actGoods == null) {
                result.setErrorMessage("1003", "不存在的活动商品");
                return result;
            }
            String actType = actGoods.getActivityType();
            //查询未参加团购的价格
            if (actType.equals(ActivityType.GROUPON.getCodeValue())) {
                //GoodsStorage noStorage = goodsStorageService.queryGoodsStorageByStandard(goodsId, standardCode, 0l);
                //GoodsStorage noStorage = goodsStorageService.queryGoodsStorageFromCache(goodsId, standardCode, 0l);
                //Long noPrice = noStorage.getSalePrice();
                //Long disAmount = noPrice - price;
                Long disAmount = storage.getOldPrice() - storage.getSalePrice();
                if (disAmount > 0) {
                    cashOrder.setDisAmount(new BigDecimal(disAmount).divide(new BigDecimal(100), 2,
                            BigDecimal.ROUND_DOWN));
                } else {
                    cashOrder.setDisAmount(new BigDecimal(0));
                }
            }
            cashOrder.setOrderType(actType);
            CustomerAddressFTInstance cumAdsIns = null;
            CustomerAddress cumAds = cumAddsService.getDefAddressByCustomerId(customerId);
            if (cumAds != null) {
                cumAdsIns = customerAddressMapper.toCustomerAddressFTInstance(cumAds);
            }
            cashOrder.setCustomerAddress(cumAdsIns);
            cashOrder.setCashGoods(cashGoods);
            cashOrder.setTotalPrice(new BigDecimal(price).multiply(new BigDecimal(buyNum)));
            cashOrder.setPayType(PayType.WXPAY.getCodeValue());
            cashOrder.setGrouponId(grouponId);
            cashOrder.setActGoodsId(actGoodsId);
            result.setData(cashOrder);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("actSubmitToCashier", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public PlainResult<WxaPayDataFTInstance> actSubmitOrder(ActPayOrderParam payOrderParam) {

        PlainResult<WxaPayDataFTInstance> result = new PlainResult<WxaPayDataFTInstance>();
        result.setRequestId(payOrderParam.getRequestId());
        try {
            String ip = payOrderParam.getIp();
            String openid = payOrderParam.getOpenid();
            Long addsId = payOrderParam.getAddressId();
            ActCashGoodsParam cashGoods = payOrderParam.getCashGoods();
            Long customerId = payOrderParam.getCustomerId();
            String profile = payOrderParam.getProfile();
            if (addsId == null || customerId == null || cashGoods == null) {
                result.setErrorMessage("1000", "参数无效");
            }
            Long grouponId = payOrderParam.getGrouponId();
            Long actGoodsId = payOrderParam.getActGoodsId();
            Integer buyNum = cashGoods.getBuyNum();
            Long goodsId = cashGoods.getGoodsId();
            String goodsStcode = cashGoods.getStandardCode();
            if (actGoodsId == null || buyNum == null || goodsId == null || StringUtils.isBlank(goodsStcode)) {
                result.setErrorMessage("1000", "参数无效");
            }
            if (result.isSuccess()) {
                //1.查询商品
                //Goods goods = goodsService.getGoodsById(goodsId);
                Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
                if (goods == null) {
                    result.setErrorMessage("1001", "商品不存在");
                    return result;
                }
                Long shopId = goods.getShopId();
                boolean goodsPass = GoodsShowType.ONSHELF.getCodeValue().equals(goods.getIsShow())
                        && GoodsCheckedType.CHECKPASS.getCodeValue().equals(goods.getIsChecked());
                if (!goodsPass) {
                    result.setErrorMessage("1002", "商品" + goods.getTitle() + "已下架");
                    return result;
                }
                //2.查询库存
                //GoodsStorage storage = goodsStorageService.queryGoodsStorageByStandard(goodsId, goodsStcode, actGoodsId);
                GoodsStorage storage = goodsStorageService.queryGoodsStorageFromCache(goodsId, goodsStcode, actGoodsId);
                if (storage == null || storage.getStockNum().longValue() == 0
                        || storage.getStockNum().longValue() < buyNum.longValue()) {
                    result.setErrorMessage("1003", "商品已售罄");
                    return result;
                }
                //3.查询地址
                CustomerAddress cumAddress = cumAddsService.getAddressById(addsId);
                if (cumAddress == null) {
                    result.setErrorMessage("1004", "收货地址为空");
                    return result;
                }
                CustomerAddressFTInstance cumAddressIns = customerAddressMapper.toCustomerAddressFTInstance(cumAddress);
                //4.查询是不是参与活动商品
                //ActivityGoods actGoods = actGoodsService.getActivityGoodsById(actGoodsId);
                ActivityGoods actGoods = actGoodsService.getActivityGoodsFromCache(actGoodsId);
                if (actGoods == null) {
                    result.setErrorMessage("1005", "未找到参与活动商品");
                    return result;
                }
                Date beginTime = actGoods.getBeginTime();
                Date endTime = actGoods.getEndTime();
                Date nowDate = new Date();
                if (nowDate.before(beginTime) || nowDate.after(endTime)) {
                    result.setErrorMessage("1006", "活动已失效");
                    return result;
                }
                //5.是否团购商品
                GrouponDetail grouponDetail = null;
                Groupon groupon = null;
                Boolean isTake = false;
                Order order = new Order();
                String actType = actGoods.getActivityType();
                //String attach = "type=" + actType;
                if (actType.equals(ActivityType.GROUPON.getCodeValue())) {//团购
                    grouponDetail = new GrouponDetail();
                    Integer groupNum = actGoods.getGrouponNum();
                    if (grouponId != null) {//参加拼团
                        isTake = true;
                        groupon = grouponService.getGrouponById(grouponId);
                        if (groupon == null) {
                            result.setErrorMessage("1007", "团购已失效");
                            return result;
                        }
                        Integer takeNum = groupon.getTakeNum();
                        if (groupNum <= takeNum) {
                            result.setErrorMessage("1008", "团购人数已达上限");
                            return result;
                        }
                        Date closeTime = groupon.getCloseTime();
                        if (nowDate.after(closeTime)) {
                            result.setErrorMessage("1009", "团购已失效");
                            return result;
                        }
                        grouponDetail.setIsHead(0);
                        //attach += ";take=0";
                    } else {//创建拼团
                        Groupon mGroupon = new Groupon();
                        mGroupon.setActGoodsId(actGoodsId);
                        mGroupon.setBeginTime(beginTime);
                        mGroupon.setEndTime(endTime);
                        mGroupon.setOpenTime(nowDate);
                        mGroupon.setOpenUserId(customerId);
                        mGroupon.setGrouponNum(actGoods.getGrouponNum());
                        mGroupon.setTakeNum(1);
                        mGroupon.setIsPay(0);
                        mGroupon.setIsDeal(0);
                        if (actGoods.getGrouponNum() == 1) {
                            mGroupon.setGroupStatus(GroupStatus.GROUPING.getCodeValue());
                        } else {
                            mGroupon.setGroupStatus(GroupStatus.UNGROUP.getCodeValue());
                        }
                        groupon = grouponService.saveGroupon(mGroupon);
                        grouponId = groupon.getId();
                        grouponDetail.setIsHead(1);
                        //attach += ";take=1";
                    }
                    grouponDetail.setTakeProfile(profile);
                    grouponDetail.setActGoodsId(actGoodsId);
                    grouponDetail.setGrouponId(grouponId);
                    grouponDetail.setTakeTime(nowDate);
                    grouponDetail.setTakeUserId(customerId);
                    grouponDetail.setIsPay(0);
                    order.setOrderType(OrderType.GROUPON.getCodeValue());
                    order.setTypeId(grouponId);
                    //attach += ";id=" + grouponId;
                } else if (actType.equals(ActivityType.SECKILL.getCodeValue())) {
                    order.setOrderType(OrderType.SECKILL.getCodeValue());
                    order.setTypeId(actGoodsId);
                    //attach += ";take=0;id=" + actGoodsId;
                } else {
                    order.setOrderType(OrderType.OTHER.getCodeValue());
                    order.setTypeId(actGoodsId);
                    //attach += ";take=0;id=" + actGoodsId;
                }
                Long salePrice = storage.getSalePrice();
                Long totalPrice = salePrice * buyNum;
                String paymentNum = DateUtils.formatTime2(nowDate)
                        + String.valueOf(new Date().getTime()).substring(4, 10) + CommUtil.randomSixInt();
                String orderNum = DateUtils.formatTime2(nowDate)
                        + String.valueOf(new Date().getTime()).substring(4, 10) + CommUtil.randomSixInt();
                String prov = cumAddressIns.getProvinceName();
                String city = cumAddressIns.getCityName();
                String area = cumAddressIns.getAreaName();
                String adds = cumAddressIns.getAddress();
                order.setAddress(prov + city + area + adds);
                order.setAddressId(addsId);
                order.setCustomerId(customerId);
                order.setIsInvoice(0);
                order.setOrderNum(orderNum);
                order.setOrderStatus(OrderStatus.WAIT_PAY.getCodeValue());
                order.setOrderTime(nowDate);
                order.setRecipient(cumAddress.getRecipient());
                order.setShopId(shopId);
                order.setTelephone(cumAddress.getTelephone());
                order.setTotalPrice(totalPrice);
                order.setTransFee(0l);
                order.setGmtCreated(nowDate);
                order.setGmtModified(nowDate);
                order.setPaymentNum(paymentNum);
                OrderDetail detail = new OrderDetail();
                String orderDetailNum = DateUtils.formatTime2(nowDate)
                        + String.valueOf(new Date().getTime()).substring(4, 10) + CommUtil.randomSixInt();
                detail.setOrderNum(orderNum);
                detail.setOrderDetailNum(orderDetailNum);
                detail.setGoodsId(goodsId);
                detail.setGoodsAmount(buyNum);
                detail.setGoodsImg(goods.getImgCart());
                detail.setGoodsPrice(storage.getOldPrice());
                detail.setGoodsRealPrice(salePrice);
                detail.setGoodsStandard(storage.getStandardCode());
                detail.setGoodsStandardDes(storage.getDescribe());
                detail.setGoodsActGoodsId(storage.getSubGoodsId());
                detail.setGoodsCategory(goods.getCategoryId());
                detail.setGoodsName(goods.getTitle());
                detail.setRefundStatus(OrderRefundStatus.UNREFUND.getCodeValue());
                detail.setGmtCreated(nowDate);
                detail.setGmtModified(nowDate);
                OrderPay orderPay = new OrderPay();
                orderPay.setOrderNum(orderNum);
                orderPay.setPaymentNum(paymentNum);
                orderPay.setPayType(PayType.WXPAY.getCodeValue());
                orderPay.setPayStatus(PayStatus.WAIT.getCodeValue());
                orderPay.setPayDate(nowDate);
                orderPay.setOrderAmount(totalPrice < 0l ? 0l : totalPrice);
                //创建支付
                WxaPayDataFTInstance wxPayData = this.getWxaPayData(paymentNum, totalPrice, ip, openid);
                if (wxPayData == null) {
                    LoggerUtil.tehuiLog
                            .info("用户:" + customerId + "，于" + DateUtils.formatTime(nowDate) + "创建prepayid失败");
                    result.setErrorMessage("1010", "支付生成prepayid错误");
                    return result;
                }
                //6.插入订单表，订单详情表，支付表，减掉库存，如若参团插入参团表
                Boolean suc = orderService.saveOrder(order, detail, orderPay, storage, groupon, grouponDetail, isTake);
                if (!suc) {
                    result.setErrorMessage("1011", "操作失败");
                }
                result.setData(wxPayData);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("actSubmitOrder", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public PlainResult<WxaPayDataFTInstance> actOrderPay(ActOrderPayParam payParam) {
        PlainResult<WxaPayDataFTInstance> result = new PlainResult<WxaPayDataFTInstance>();
        result.setRequestId(payParam.getRequestId());
        try {
            Long customerId = payParam.getCustomerId();
            String orderNum = payParam.getOrderNum();
            Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
            if (order == null) {
                result.setErrorMessage("1000", "订单不存在");
                return result;
            }
            String orderStatus = order.getOrderStatus();
            if (!OrderStatus.WAIT_PAY.getCodeValue().equals(orderStatus)) {
                result.setErrorMessage("1001", "订单不能支付");
                return result;
            }
            Date nowTime = new Date();
            String orderType = order.getOrderType();
            Long typeId = order.getTypeId();
            //String attach = "type=" + orderType;
            if (orderType.equals(ActivityType.GROUPON.getCodeValue())) {
                Groupon groupon = grouponService.getGrouponById(typeId);
                Date closeTime = groupon.getCloseTime();
                if (nowTime.after(closeTime)) {
                    result.setErrorMessage("1002", "活动结束，无法支付");
                    return result;
                }
                Long userId = groupon.getOpenUserId();
                if (userId == customerId) {
                    //attach += ";take=1";
                } else {
                    //attach += ";take=0";
                }
            } else {
                //attach += ";take=0";
            }
            //attach += ";id=" + typeId;
            String paymentNum = DateUtils.formatTime2(nowTime) + String.valueOf(new Date().getTime()).substring(4, 10)
                    + CommUtil.randomSixInt();
            Long totalPrice = order.getTotalPrice();
            String openid = payParam.getOpenid();
            String billIp = payParam.getIp();
            OrderPay orderPay = new OrderPay();
            orderPay.setOrderNum(orderNum);
            orderPay.setPaymentNum(paymentNum);
            orderPay.setPayType(PayType.WXPAY.getCodeValue());
            orderPay.setPayStatus(PayStatus.WAIT.getCodeValue());
            orderPay.setPayDate(nowTime);
            orderPay.setOrderAmount(totalPrice < 0l ? 0l : totalPrice);
            WxaPayDataFTInstance wxPayDate = this.getWxaPayData(paymentNum, totalPrice, billIp, openid);
            if (wxPayDate == null) {
                LoggerUtil.tehuiLog.info("用户:" + customerId + "，于" + DateUtils.formatTime(nowTime) + "创建prepayid失败");
                result.setErrorMessage("1003", "支付生成prepayid错误");
                return result;
            }
            Long id = order.getId();
            orderService.updateOrderAndPay(id, orderPay);
            result.setData(wxPayDate);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("actOrderPay", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    /*
     * 创建小程序支付数据
     */
    private WxaPayDataFTInstance getWxaPayData(String paymentNum, Long totalPrice, String billIp, String openid) {
        String key = SystemConfig.INSTANCE.getValue(ConfigConstant.APP_KEY);
        String unifiedorderUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.UNIFIEDORDER_URL);
        PrepayIdRequestHandler prepayHandler = new PrepayIdRequestHandler();
        prepayHandler.setKey(key);
        prepayHandler.setGateUrl(unifiedorderUrl);
        ClientRequestHandler clientHandler = new ClientRequestHandler();
        clientHandler.setKey(key);
        String noncestr = WXUtil.getNonceStr();
        String appId = SystemConfig.INSTANCE.getValue(ConfigConstant.APP_ID);
        String mchId = SystemConfig.INSTANCE.getValue(ConfigConstant.MCH_ID);
        String notifyUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.NOTIFY_URL);
        prepayHandler.setParameter("appid", appId);
        prepayHandler.setParameter("mch_id", mchId);
        prepayHandler.setParameter("nonce_str", noncestr);
        prepayHandler.setParameter("body", "摩森特（北京）科技有限公司-指定购买商品");
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
        WxaPayDataFTInstance wxPayData = new WxaPayDataFTInstance();
        wxPayData.setNonceStr(nonceStrs);
        wxPayData.setPackages(packages);
        wxPayData.setPaySign(paySign);
        wxPayData.setSignType(signType);
        wxPayData.setTimeStamp(timeStamp);
        return wxPayData;
    }

    @Override
    public PlainResult<OrderCashFTInstance> getOrderCashDetail(String paymentNum, Long customerId, String requestId) {
        PlainResult<OrderCashFTInstance> result = new PlainResult<OrderCashFTInstance>();
        result.setRequestId(requestId);
        try {
            List<Order> orderList = orderService.getOrderList(customerId, paymentNum);
            if (orderList == null || orderList.isEmpty()) {
                result.setErrorMessage("1001", "订单不存在");
                return result;
            }
            Long totalMoney = 0L;
            OrderCashFTInstance orderCash = new OrderCashFTInstance();
            List<OrderCashDetailFTInstance> ocDetailList = new ArrayList<OrderCashDetailFTInstance>();
            for (Order order : orderList) {
                String orderNum = order.getOrderNum();
                List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(orderNum);
                if (orderDetailList != null) {
                    for (OrderDetail orderDetail : orderDetailList) {
                        OrderCashDetailFTInstance ocDetail = new OrderCashDetailFTInstance();
                        ocDetail.setGoodsName(orderDetail.getGoodsName());
                        ocDetail.setBuyNum(orderDetail.getGoodsAmount());
                        ocDetailList.add(ocDetail);
                    }
                }
                totalMoney += order.getTotalPrice();
            }
            orderCash.setDetailList(ocDetailList);
            orderCash.setTotalMoney(new BigDecimal(totalMoney).divide(new BigDecimal(100)));
            result.setData(orderCash);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getOrderCashDetail", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public PlainResult<WxPayDataFTInstance> wxPayment(String paymentNum, Long customerId, String openid, String billIp,
                                                      String requestId) {
        PlainResult<WxPayDataFTInstance> result = new PlainResult<WxPayDataFTInstance>();
        result.setRequestId(requestId);
        try {
            List<Order> orderList = orderService.getOrderList(customerId, paymentNum);
            if (orderList == null || orderList.isEmpty()) {
                result.setErrorMessage("1001", "订单不存在");
                return result;
            }
            Long totalMoney = 0L;
            for (Order order : orderList) {
                totalMoney += order.getTotalPrice();
            }
            WxPayDataFTInstance wxPayData = getWxPayData(paymentNum, totalMoney, billIp, openid);
            if (wxPayData == null) {
                LoggerUtil.tehuiLog.info("用户:" + customerId + "，于" + DateUtils.formatTime(new Date()) + "创建prepayid失败");
                result.setErrorMessage("1002", "支付生成prepayid错误");
                return result;
            }
            result.setData(wxPayData);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("wxPayment", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    /*
     * 创建公众号支付数据
     */
    private WxPayDataFTInstance getWxPayData(String paymentNum, Long totalPrice, String billIp, String openid) {
        String key = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_KEY);
        String appId = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_APP_ID);
        String mchId = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_MCH_ID);
        String notifyUrl = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_NOTIFY_URL);
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
        prepayHandler.setParameter("body", "摩森特（北京）科技有限公司-指定购买商品");
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
    public BaseResult wxAsyncNotify(String xmlData) {
        BaseResult result = new BaseResult();
        try {
            String key = SystemConfig.INSTANCE.getValue(ConfigConstant.WX_KEY);
            ClientResponseHandler resHandler = new ClientResponseHandler();
            resHandler.setCharset("UTF-8");
            resHandler.setContent(xmlData);
            resHandler.setKey(key);
            boolean isTenPay = resHandler.isTenpaySign();
            if (!isTenPay) {
                result.setErrorMessage("1001", "签名验证不通过");
                return result;
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
            LoggerUtil.tehuiLog.info("微信支付回调。订单编号：" + outTradeNo + "，回调付款状态：" + resultCode + "，returnCode："
                    + returnCode + "，errorMsg：" + returnMsg);
            if ("FAIL".equals(returnCode.trim())) {
                result.setErrorMessage("1002", returnMsg);
                return result;
            }
            String paymentNum = outTradeNo.trim();
            List<OrderPay> orderPayList = orderPayService.queryOrderPay(paymentNum);
            if (orderPayList == null || orderPayList.isEmpty()) {
                return result;
            }
            OrderPay orderPay = orderPayList.get(0);
            if (!PayStatus.WAIT.getCodeValue().equals(orderPay.getPayStatus())) {
                return result;
            }
            OrderPay newOrderPay = new OrderPay();
            newOrderPay.setAmount(Long.valueOf(totalFee));
            newOrderPay.setHostDate(timeEnd.substring(0, 4) + "-" + timeEnd.substring(4, 6) + "-"
                    + timeEnd.substring(6, 8));
            newOrderPay.setHostTime(timeEnd.substring(8, 10) + ":" + timeEnd.substring(10, 12) + ":"
                    + timeEnd.substring(12, 14));
            newOrderPay.setNotifyType(NotifyType.ASYNC.getCodeValue());
            newOrderPay.setIrspRef(transId);
            newOrderPay.setMchId(mchId);
            newOrderPay.setBatchStatus(resultCode);
            newOrderPay.setPaymentNum(paymentNum);
            if ("SUCCESS".equals(resultCode.trim())) {
                newOrderPay.setPayStatus(PayStatus.SUCCESS.getCodeValue());
                orderService.updateOrderAndPay(newOrderPay, paymentNum);
                //发送邮件
                List<String> orderNumList = new ArrayList<String>();
                for (OrderPay pay : orderPayList) {
                    orderNumList.add(pay.getOrderNum());
                }
                ntcMailService.createBuyNotice(orderNumList);
            } else {
                newOrderPay.setPayStatus(PayStatus.FAIL.getCodeValue());
                orderPayService.updateOrderPay(paymentNum, newOrderPay);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("wxAsyncNotify", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public BaseResult orderConfirm(String orderNum, Long customerId, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(orderNum)) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
                if (order == null) {
                    result.setErrorMessage("1001", "订单不存在");
                    return result;
                }
                orderService.updateOrderDeal(order.getId());
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderConfirm", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public BaseResult wxaAsyncNotify(String xmlData) {

        BaseResult result = new BaseResult();
        try {
            String key = SystemConfig.INSTANCE.getValue(ConfigConstant.APP_KEY);
            ClientResponseHandler resHandler = new ClientResponseHandler();
            resHandler.setCharset("UTF-8");
            resHandler.setContent(xmlData);
            resHandler.setKey(key);
            boolean isTenPay = resHandler.isTenpaySign();
            if (!isTenPay) {
                result.setErrorMessage("1001", "签名验证不通过");
                return result;
            }
            String returnCode = resHandler.getParameter("return_code");
            String appid = resHandler.getParameter("appid");
            String mchId = resHandler.getParameter("mch_id");
            String nonceStr = resHandler.getParameter("nonce_str");
            String resultCode = resHandler.getParameter("result_code");//业务结果 SUCCESS/FAIL
            String openid = resHandler.getParameter("openid");
            String tradeType = resHandler.getParameter("trade_type");
            String bankType = resHandler.getParameter("bank_type");
            Integer totalFee = StringUtils.isBlank(resHandler.getParameter("total_fee")) ? null : Integer
                    .valueOf(resHandler.getParameter("total_fee"));//int 订单总金额，单位为分
            Integer cashFee = StringUtils.isBlank(resHandler.getParameter("cash_fee")) ? null : Integer
                    .valueOf(resHandler.getParameter("cash_fee")); //int 现金支付金额 
            String transId = resHandler.getParameter("transaction_id");// 微信支付订单号
            String outTradeNo = resHandler.getParameter("out_trade_no");// 商户系统的订单号，与请求一致。
            String attach = resHandler.getParameter("attach");// 商家数据包，原样返回
            String timeEnd = resHandler.getParameter("time_end");// 支付完成时间
            if ("FAIL".equals(returnCode.trim())) {
                LoggerUtil.tehuiLog.info("小程序支付回调通信失败");
                String returnMsg = resHandler.getParameter("return_msg");
                result.setErrorMessage("1002", returnMsg);
                return result;
            }
            List<OrderPay> orderPayList = orderPayService.queryOrderPay(outTradeNo.trim());
            //对于活动商品，只存在一条支付记录
            OrderPay orderPay = orderPayList.get(0);
            if (!PayStatus.WAIT.getCodeValue().equals(orderPay.getPayStatus())) {
                return result;
            }
            orderPay.setAmount(Long.valueOf(totalFee));
            //orderPay.setBatchNo();
            //orderPay.setVoucherNo();
            orderPay.setHostDate(timeEnd.substring(0, 4) + "-" + timeEnd.substring(4, 6) + "-"
                    + timeEnd.substring(6, 8));
            orderPay.setHostTime(timeEnd.substring(8, 10) + ":" + timeEnd.substring(10, 12) + ":"
                    + timeEnd.substring(12, 14));
            //orderPay.setRemark(attach);
            orderPay.setNotifyType(NotifyType.ASYNC.getCodeValue());
            orderPay.setIrspRef(transId);
            orderPay.setMchId(mchId);
            orderPay.setBatchStatus(resultCode);
            if ("SUCCESS".equals(resultCode.trim())) {//支付成功
                orderPay.setPayStatus(PayStatus.SUCCESS.getCodeValue());
                orderService.updateOrderAndPay(orderPay.getOrderNum(), orderPay);
            } else {//支付失败
                orderPay.setPayStatus(PayStatus.FAIL.getCodeValue());
                orderPayService.updateOrderPay(outTradeNo, orderPay);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("wxaAsyncNotify", e);
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public BaseResult deleteOrder(String orderNum, Long customerId, String requestId) {
        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(orderNum)) {
                result.setErrorMessage("1000", "参数错误");
                return result;
            }
            Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
            if (order == null) {
                result.setErrorMessage("1001", "删除失败");
                return result;
            }
            Integer count = orderService.logicDeleteOrder(orderNum);
            if (count == 0) {
                result.setErrorMessage("1001", "删除失败");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMessage("999", "接口异常");
        }
        return result;
    }

    @Override
    public PlainResult<String> getOrderResult(String paymentNum, String requestId) {
        PlainResult<String> pr = new PlainResult<String>();
        pr.setRequestId(requestId);
        try {
            List<OrderPay> orderPayList = orderPayService.queryOrderPay(paymentNum);
            if (orderPayList == null) {
                pr.setErrorMessage("1001", "数据不存在");
                return pr;
            }
            Long money = 0l;
            boolean success = false;
            for (OrderPay orderPay : orderPayList) {
                if (PayStatus.SUCCESS.getCodeValue().equals(orderPay.getPayStatus())) {
                    success = true;
                }
                money += orderPay.getOrderAmount();
            }
            BigDecimal amount = new BigDecimal(money).divide(new BigDecimal(100));
            if (success) {
                pr.setData(amount.toString());
                return pr;
            }
            pr.setErrorMessage("1002", "支付失败");
        } catch (Exception e) {
            e.printStackTrace();
            pr.setErrorMessage("999", "接口异常");
        }
        return pr;
    }

    @Override
    public PlainResult<ApplyRefundFTInstance> previewOrderRefund(String orderNum, String orderDetailNum,
                                                                 Long customerId, String requestId) {
        PlainResult<ApplyRefundFTInstance> pr = new PlainResult<ApplyRefundFTInstance>();
        pr.setRequestId(requestId);
        try {
            boolean paramValid = StringUtils.isBlank(orderNum) || StringUtils.isBlank(orderDetailNum)
                    || customerId == null || customerId == null;
            if (paramValid) {
                pr.setErrorMessage("1000", "参数错误");
            }
            if (pr.isSuccess()) {
                Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
                if (order == null) {
                    pr.setErrorMessage("1001", "订单不存在");
                    return pr;
                }
                OrderDetail orderDetail = orderDetailService.getOrderDetail(orderDetailNum);
                if (orderDetail == null) {
                    pr.setErrorMessage("1002", "订单不存在");
                    return pr;
                }
                ApplyRefundFTInstance refundData = new ApplyRefundFTInstance();
                Long realPrice = orderDetail.getGoodsRealPrice();
                Integer nums = orderDetail.getGoodsAmount();
                String reason = orderDetail.getRefundReason();
                String refdes = orderDetail.getRefundDes();
                Long couMoney = orderDetail.getCouponMoney();
                String refStatus = orderDetail.getRefundStatus();
                if (OrderRefundStatus.UNREFUND.getCodeValue().equals(refStatus)) {
                    refundData.setApplied(false);
                } else {
                    refundData.setApplied(true);
                }
                String refundMoney = null;
                if (couMoney == null) {
                    refundMoney = new BigDecimal(realPrice * nums).divide(new BigDecimal(100)).toString();
                } else {
                    Long money = realPrice * nums - couMoney;
                    if (money < 0) {
                        money = 0l;
                    }
                    refundMoney = new BigDecimal(money).divide(new BigDecimal(100)).toString();
                }
                refundData.setRefundMoney(refundMoney);
                RefundReason[] reasons = RefundReason.values();
                Map<String, String> refundReasonMap = new HashMap<String, String>();
                for (RefundReason refundReason : reasons) {
                    refundReasonMap.put(refundReason.getCodeValue(), refundReason.getNameValue());
                }
                refundData.setRefundReasonList(refundReasonMap);
                refundData.setCurReason(reason);
                refundData.setRefundDes(refdes);
                pr.setData(refundData);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("previewOrderRefund", e);
            pr.setErrorMessage("999", "接口异常");
        }
        return pr;
    }

    @Override
    public BaseResult applyOrderRefund(ApplyRefundParam refundParam) {
        BaseResult br = new BaseResult();
        br.setRequestId(refundParam.getRequestId());
        try {
            String orderNum = refundParam.getOrderNum();
            String orderDetailNum = refundParam.getOrderDetailNum();
            Long customerId = refundParam.getCustomerId();
            boolean paramValid = StringUtils.isBlank(orderNum) || StringUtils.isBlank(orderDetailNum)
                    || customerId == null;
            if (paramValid) {
                br.setErrorMessage("1000", "参数错误");
            }
            if (br.isSuccess()) {
                Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
                if (order == null) {
                    br.setErrorMessage("1001", "订单不存在");
                    return br;
                }
                OrderDetail orderDetail = orderDetailService.getOrderDetail(orderDetailNum);
                if (orderDetail == null) {
                    br.setErrorMessage("1002", "订单不存在");
                    return br;
                }
                String refundStatus = orderDetail.getRefundStatus();
                if (!OrderRefundStatus.UNREFUND.getCodeValue().equals(refundStatus)) {
                    br.setErrorMessage("1003", "订单退款中或已退款");
                    return br;
                }
                String refundReason = refundParam.getRefundReason();
                String refundDes = refundParam.getRefundDes();
                int count = orderDetailService.updateOrderRefundStatusIng(orderDetailNum, refundReason, refundDes);
                if (count == 0) {
                    br.setErrorMessage("1004", "操作失败");
                    return br;
                }
                //发送邮件
                ntcMailService.createRefundNotice(orderNum);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("applyRefundOrder", e);
            br.setErrorMessage("999", "接口异常");
        }
        return br;
    }

    @Override
    public BaseResult revokeOrderRefund(String orderNum, String orderDetailNum, Long customerId, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            boolean paramValid = StringUtils.isBlank(orderNum) || StringUtils.isBlank(orderDetailNum)
                    || customerId == null;
            if (paramValid) {
                br.setErrorMessage("1000", "参数错误");
            }
            if (br.isSuccess()) {
                Order order = orderService.queryOrderByNumCustomer(customerId, orderNum);
                if (order == null) {
                    br.setErrorMessage("1001", "订单不存在");
                    return br;
                }
                OrderDetail orderDetail = orderDetailService.getOrderDetail(orderDetailNum);
                if (orderDetail == null) {
                    br.setErrorMessage("1002", "订单不存在");
                    return br;
                }
                String refundStatus = orderDetail.getRefundStatus();
                if (OrderRefundStatus.REFUNDED.getCodeValue().equals(refundStatus)) {
                    br.setErrorMessage("1003", "订单已退款");
                    return br;
                }
                int count = orderDetailService.updateOrderRefundStatusBack(orderDetailNum);
                if (count == 0) {
                    br.setErrorMessage("1004", "操作失败");
                    return br;
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("revokeOrderRefund", e);
            br.setErrorMessage("999", "接口异常");
        }
        return br;
    }

    //    @Override
    //    public PlainResult<PayOrderFTInstance> testOrder() {
    //        PlainResult<PayOrderFTInstance> result = new PlainResult<PayOrderFTInstance>();
    //        PayOrderFTInstance orderIns = new PayOrderFTInstance();
    //        try {
    //            String PayTypeID = Constant.PAY_TYPE_ID_IMMEDIATEPAY;
    //            String OrderDate = "2014/09/23";
    //            String OrderTime = "11:55:30";
    //            String OrderNo = "abcd" + new Date().getTime();
    //            String OrderAmount = "0.01";
    //            String CommodityType = Constant.COMMODITY_TYPE_XFL_CTL;
    //            String InstallmentMark = Constant.INSTALL_MENT_MARK_NO;
    //            //1
    //            PayDicOrder dicOrder = new PayDicOrder(PayTypeID, OrderDate, OrderTime, OrderNo, OrderAmount,
    //                    CommodityType, InstallmentMark);
    //            List<PayOrderItem> orderItemList = new ArrayList<>();
    //            PayOrderItem orderItem = new PayOrderItem("一本好书");
    //            orderItemList.add(orderItem);
    //            String PaymentType = Constant.PAYMENT_TYPE_ZFFSHB;
    //            String PaymentLinkType = Constant.PAYMENT_LINK_TYPE_SJWLJR;
    //            String NotifyType = Constant.NOTIFY_TYPE_ASYNC;
    //            String IsBreakAccount = Constant.IS_BREAK_ACCOUNT_NO;
    //            //2
    //            PayDicReq dicReq = new PayDicReq(PaymentType, PaymentLinkType, NotifyType, IsBreakAccount);
    //            //3
    //            AbcPayRequest payReq = new AbcPayRequest(dicOrder, orderItemList, dicReq);
    //            PlainResult<AbcData> data = payReq.sendRequest();
    //            AbcData abc = data.getData();
    //            String rcode = abc.getReturnCode();
    //            String emsg = abc.getErrorMessage();
    //            if (data.isSuccess()) {
    //                orderIns.setReturnCode(rcode);
    //                orderIns.setErrorMessage(emsg);
    //                orderIns.setPaymentURL(abc.getPaymentURL());
    //            } else {
    //                result.setErrorMessage(rcode, emsg);
    //                orderIns.setReturnCode(rcode);
    //                orderIns.setErrorMessage(emsg);
    //            }
    //            System.out.println(data.isSuccess());
    //            System.out.println(abc.getPaymentURL());
    //            System.out.println(rcode + "=" + emsg);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        result.setData(orderIns);
    //        return result;
    //    }
    //
    //    @Override
    //    public PlainResult<WxaPayDataFTInstance> testWx(String openid, String ip) {
    //        PlainResult<WxaPayDataFTInstance> result = new PlainResult<WxaPayDataFTInstance>();
    //        try {
    //            String key = "00ec0c4572dc49b06c2a6ad1bdd08936";
    //            PrepayIdRequestHandler prepayHandler = new PrepayIdRequestHandler();
    //            prepayHandler.setKey(key);
    //            ClientRequestHandler clientHandler = new ClientRequestHandler();
    //            clientHandler.setKey(key);
    //            String noncestr = WXUtil.getNonceStr();
    //            String appId = "wxec15b7df611fa1ec";
    //            String mchId = "1413668402";
    //            String notifyUrl = "https://sptest.mocentre.com/wxa/notify/async.htm";
    //            prepayHandler.setParameter("appid", appId);
    //            prepayHandler.setParameter("mch_id", mchId);
    //            prepayHandler.setParameter("nonce_str", noncestr);
    //            prepayHandler.setParameter("body", "摩森特（北京）科技有限公司-指定购买商品");
    //            prepayHandler.setParameter("attach", "123");
    //            prepayHandler.setParameter("out_trade_no", new Date().getTime() + "");
    //            prepayHandler.setParameter("total_fee", "10");
    //            prepayHandler.setParameter("spbill_create_ip", ip);
    //            prepayHandler.setParameter("notify_url", notifyUrl);
    //            prepayHandler.setParameter("trade_type", "JSAPI");
    //            prepayHandler.setParameter("openid", openid);
    //            prepayHandler.createMD5Sign();
    //            String prepayid = prepayHandler.sendPrepay();
    //            if (StringUtils.isEmpty(prepayid)) {
    //                LoggerUtil.tehuiLog.info("测试支付生成prepay错误");
    //                result.setErrorMessage("1009", "支付错误");
    //                return result;
    //            }
    //            String timeStamp = WXUtil.getTimeStamp();
    //            String nonceStrs = WXUtil.getNonceStr();
    //            String signType = "MD5";
    //            String packages = "prepay_id=" + prepayid;
    //            clientHandler.setParameter("appId", appId);
    //            clientHandler.setParameter("timeStamp", timeStamp);
    //            clientHandler.setParameter("nonceStr", nonceStrs);
    //            clientHandler.setParameter("package", packages);
    //            clientHandler.setParameter("signType", signType);
    //            String paySign = clientHandler.getMD5Sign();
    //            WxaPayDataFTInstance wxPayData = new WxaPayDataFTInstance();
    //            wxPayData.setNonceStr(nonceStrs);
    //            wxPayData.setPackages(packages);
    //            wxPayData.setPaySign(paySign);
    //            wxPayData.setSignType(signType);
    //            wxPayData.setTimeStamp(timeStamp);
    //            result.setData(wxPayData);
    //        } catch (Exception e) {
    //        }
    //        return result;
    //    }
    //
    //    @Override
    //    public BaseResult testWxRefund() {
    //        BaseResult br = new BaseResult();
    //        try {
    //            String gateUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //            String key = "00ec0c4572dc49b06c2a6ad1bdd08936";
    //            RefundRequestHandler reqHandler = new RefundRequestHandler();
    //            reqHandler.setGateUrl(gateUrl);
    //            reqHandler.setKey(key);
    //            String noncestr = WXUtil.getNonceStr();
    //            String appId = "wxec15b7df611fa1ec";
    //            String mchId = "1413668402";
    //            reqHandler.setParameter("appid", appId);
    //            reqHandler.setParameter("mch_id", mchId);
    //            reqHandler.setParameter("nonce_str", noncestr);
    //            reqHandler.setParameter("sign_type", "MD5");
    //            reqHandler.setParameter("transaction_id", "4008042001201703032099384466");
    //            reqHandler.setParameter("out_refund_no", "201703031951253711488541885998");
    //            reqHandler.setParameter("total_fee", "1");
    //            reqHandler.setParameter("refund_fee", "1");
    //            reqHandler.setParameter("op_user_id", mchId);
    //            reqHandler.createMD5Sign();
    //            File certFile = new File("E:\\work\\wxa\\cert\\apiclient_cert.p12");
    //            Map resultMap = reqHandler.sendRefund(certFile, mchId);
    //            String returnCode = (String) resultMap.get("return_code");
    //            String returnMsg = (String) resultMap.get("return_msg");
    //            System.out.println("return_code:" + returnCode + " return_msg:" + returnMsg);
    //            br.setMessage(JSON.toJSONString(resultMap));
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return br;
    //    }
}
