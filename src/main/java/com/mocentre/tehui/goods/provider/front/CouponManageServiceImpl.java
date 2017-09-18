package com.mocentre.tehui.goods.provider.front;

import static com.mocentre.tehui.core.utils.DateUtils.format;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.model.ShoppingCart;
import com.mocentre.tehui.buy.service.IOrderDetailService;
import com.mocentre.tehui.buy.service.IShoppingCartService;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.frontend.model.CouponDetailFTInstance;
import com.mocentre.tehui.frontend.model.CouponReceiveFTInstance;
import com.mocentre.tehui.frontend.param.CashCouponParam;
import com.mocentre.tehui.frontend.param.CashGoodsParam;
import com.mocentre.tehui.frontend.param.CashUseCouponParam;
import com.mocentre.tehui.frontend.param.CountCouponParam;
import com.mocentre.tehui.frontend.param.CouponBindParam;
import com.mocentre.tehui.frontend.param.CouponQueryParam;
import com.mocentre.tehui.frontend.param.SyncLotteryParam;
import com.mocentre.tehui.frontend.service.CouponManageService;
import com.mocentre.tehui.goods.enums.CouponRelateType;
import com.mocentre.tehui.goods.enums.CouponStatus;
import com.mocentre.tehui.goods.mapper.CouponMapper;
import com.mocentre.tehui.goods.model.Coupon;
import com.mocentre.tehui.goods.model.CouponDetail;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.service.ICouponDetailService;
import com.mocentre.tehui.goods.service.ICouponService;
import com.mocentre.tehui.goods.service.IGoodsService;
import com.mocentre.tehui.goods.service.IGoodsStorageService;
import com.mocentre.tehui.job.cpnqueue.CouponMsgVo;
import com.mocentre.tehui.job.cpnqueue.CouponQueue;

/**
 * 优惠券接口实现 Created by yukaiji on 2016/11/24.
 */
public class CouponManageServiceImpl implements CouponManageService {

    @Autowired
    private ICouponDetailService     couponDetailService;
    @Autowired
    private ICouponService           couponService;
    @Autowired
    private IGoodsService            goodsService;
    @Autowired
    private IGoodsStorageService     goodsStorageService;
    @Autowired
    private IShoppingCartService     shoppingCartService;
    @Autowired
    private IOrderDetailService      orderDetailService;
    @Autowired
    private CouponQueue<CouponMsgVo> couponQueue;

    @Override
    public ListResult<CouponDetailFTInstance> queryCouponDeatilByCustomerId(CouponQueryParam couponParam) {
        ListResult<CouponDetailFTInstance> result = new ListResult<CouponDetailFTInstance>();
        result.setRequestId(couponParam.getRequestId());
        String type = couponParam.getType();
        Long customerId = couponParam.getCustomerId();
        Integer start = couponParam.getStart();
        Integer length = couponParam.getLength();
        if (StringUtils.isBlank(type) || customerId == null) {
            result.setErrorMessage("1000", "参数错误");
            return result;
        }
        List<CouponDetailFTInstance> detailInsList = null;
        PageInfo<CouponDetail> pages = new PageInfo<>(new ArrayList<CouponDetail>());
        try {
            if ("1".equals(type)) {//未使用
                pages = couponDetailService.getUnusedCouponDetailPage(customerId, start, length);
            } else if ("2".equals(type)) {//已使用
                pages = couponDetailService.getUsedCouponDetailPage(customerId, start, length);
            } else if ("3".equals(type)) {//已过期
                pages = couponDetailService.getExpriedCouponDetailPage(customerId, start, length);
            }
            List<CouponDetail> list = pages.getRows();
            detailInsList = CouponMapper.toDetailFTInstanceList(list);
            result.setData(detailInsList);
            result.setCount((int) pages.getTotal());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryCouponDeatilByCustomerId", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<Integer> countUsedCoupon(CountCouponParam couponParam) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        result.setRequestId(couponParam.getRequestId());
        String comefrom = couponParam.getComefrom();
        Long customerId = couponParam.getCustomerId();
        Long goodsId = couponParam.getGoodsId();
        String stcode = couponParam.getStandardCode();
        Integer buyNum = couponParam.getBuyNum();
        String orderNum = couponParam.getOrderNum();
        boolean paramValid = StringUtils.isBlank(comefrom)
                || !("1".equals(comefrom) || "2".equals(comefrom) || "3".equals(comefrom)) || customerId == null;
        if (paramValid) {
            result.setErrorMessage("1000", "参数错误");
            return result;
        }
        paramValid = "2".equals(comefrom) && (goodsId == null || StringUtils.isBlank(stcode) || buyNum == null);
        if (paramValid) {
            result.setErrorMessage("1000", "参数错误");
            return result;
        }
        paramValid = "3".equals(comefrom) && StringUtils.isBlank(orderNum);
        if (paramValid) {
            result.setErrorMessage("1000", "参数错误");
            return result;
        }
        try {
            List<Map<String, Object>> goodsMapList = new ArrayList<Map<String, Object>>();
            if ("1".equals(comefrom)) {//来源购物车
                List<ShoppingCart> shopCartList = shoppingCartService.queryShopCartByCustomer(customerId);
                if (shopCartList != null && !shopCartList.isEmpty()) {
                    for (ShoppingCart shopCart : shopCartList) {
                        Map<String, Object> goodsMap = new HashMap<String, Object>();
                        goodsMap.put("goodsId", shopCart.getGoodsId());
                        goodsMap.put("stcode", shopCart.getGoodsSku());
                        goodsMap.put("buyNum", shopCart.getNum());
                        goodsMapList.add(goodsMap);
                    }
                }
            } else if ("2".equals(comefrom)) {//来源详情
                Map<String, Object> goodsMap = new HashMap<String, Object>();
                goodsMap.put("goodsId", goodsId);
                goodsMap.put("stcode", stcode);
                goodsMap.put("buyNum", buyNum);
                goodsMapList.add(goodsMap);
            } else if ("3".equals(comefrom)) {//来源已生成未支付的订单
                List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(orderNum);
                if (orderDetailList != null && !orderDetailList.isEmpty()) {
                    for (OrderDetail orderDetail : orderDetailList) {
                        Map<String, Object> goodsMap = new HashMap<String, Object>();
                        goodsMap.put("goodsId", orderDetail.getGoodsId());
                        goodsMap.put("stcode", orderDetail.getGoodsStandard());
                        goodsMap.put("buyNum", orderDetail.getGoodsAmount());
                        goodsMapList.add(goodsMap);
                    }
                }
            }
            Integer num = couponDetailService.updateCustomerCouponToCache(customerId, goodsMapList);
            result.setData(num);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("countUsedCoupon", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListResult<CouponDetailFTInstance> queryUsedCoupon(CashCouponParam cashCouponParam) {

        ListResult<CouponDetailFTInstance> result = new ListResult<CouponDetailFTInstance>();
        Long count = 0l;
        List<CouponDetail> couponDetailList = null;
        List<CouponDetailFTInstance> couponDetailInsList = new ArrayList<CouponDetailFTInstance>();
        Long customerId = cashCouponParam.getCustomerId();
        String type = cashCouponParam.getType();
        Integer start = cashCouponParam.getStart();
        Integer length = cashCouponParam.getLength();
        boolean paramValid = customerId == null || StringUtils.isBlank(type);
        if (paramValid) {
            result.setErrorMessage("1000", "参数错误");
            return result;
        }
        try {
            long begin = start == null ? 0 : start - 1;
            long end = length == null ? 0 : begin + length - 1;
            if ("use".equals(type)) {
                Map<String, Object> couponMap = couponDetailService.queryUseFromCache(customerId, begin, end);
                couponDetailList = couponMap.get("couponDetailList") == null ? null : (List<CouponDetail>) couponMap
                        .get("couponDetailList");
                count = couponMap.get("count") == null ? 0l : (Long) couponMap.get("count");
            } else if ("unused".equals(type)) {
                Map<String, Object> couponMap = couponDetailService.queryUnusedFromCache(customerId, begin, end);
                couponDetailList = couponMap.get("couponDetailList") == null ? null : (List<CouponDetail>) couponMap
                        .get("couponDetailList");
                count = couponMap.get("count") == null ? 0l : (Long) couponMap.get("count");
            }
            if (couponDetailList != null) {
                couponDetailInsList = CouponMapper.toDetailFTInstanceList(couponDetailList);
            }
            result.setData(couponDetailInsList);
            result.setCount(count.intValue());
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryUsedCoupon", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<CouponDetailFTInstance> bindCoupon(String code, Long customerId, String requestId) {
        PlainResult<CouponDetailFTInstance> pr = new PlainResult<CouponDetailFTInstance>();
        pr.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(code) || customerId == null) {
                pr.setErrorMessage("1000", "参数错误");
                return pr;
            }
            CouponDetail couponDetail = couponDetailService.getCouponDetailBySn(code);
            if (couponDetail == null) {
                pr.setErrorMessage("1001", "不存在的优惠码");
                return pr;
            }
            Long cumId = couponDetail.getCustomerId();
            if (cumId != null) {
                pr.setErrorMessage("1002", "优惠码已兑换");
                return pr;
            }
            Date endTime = couponDetail.getEndTime();
            Date nowTime = new Date();
            String endTimeStr = format(endTime, DateUtils.DATE_FORMAT_STR);
            String nowTimeStr = format(nowTime, DateUtils.DATE_FORMAT_STR);
            int time = nowTimeStr.compareTo(endTimeStr);
            if (time > 0) {
                pr.setErrorMessage("1003", "优惠码已过期");
                return pr;
            }
            String status = couponDetail.getStatus();
            if (CouponStatus.USED.getCodeValue().equals(status)) {
                pr.setErrorMessage("1004", "优惠码已使用");
                return pr;
            }
            if (pr.isSuccess()) {
                int count = couponDetailService.updateCouponDetailCustomer(code, customerId);
                if (count == 0) {
                    pr.setErrorMessage("1005", "兑换失败");
                    return pr;
                }
                CouponDetailFTInstance detailIns = CouponMapper.toDetailFTInstance(couponDetail);
                pr.setData(detailIns);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("bindCoupon", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public PlainResult<CouponDetailFTInstance> bindCashCoupon(CouponBindParam couponParam) {
        PlainResult<CouponDetailFTInstance> pr = new PlainResult<CouponDetailFTInstance>();
        pr.setRequestId(couponParam.getRequestId());
        String code = couponParam.getCode();
        Long customerId = couponParam.getCustomerId();
        List<CashGoodsParam> cashGoodsList = couponParam.getCashGoodsList();
        try {
            if (StringUtils.isBlank(code) || customerId == null || cashGoodsList == null) {
                pr.setErrorMessage("1000", "参数错误");
                return pr;
            }
            CouponDetail couponDetail = couponDetailService.getCouponDetailBySn(code);
            if (couponDetail == null) {
                pr.setErrorMessage("1001", "不存在的优惠码");
                return pr;
            }
            Long cumId = couponDetail.getCustomerId();
            if (cumId != null) {
                pr.setErrorMessage("1002", "优惠码已兑换");
                return pr;
            }
            Date endTime = couponDetail.getEndTime();
            Date nowTime = new Date();
            String endTimeStr = format(endTime, DateUtils.DATE_FORMAT_STR);
            String nowTimeStr = format(nowTime, DateUtils.DATE_FORMAT_STR);
            int time = nowTimeStr.compareTo(endTimeStr);
            if (time > 0) {
                pr.setErrorMessage("1003", "优惠码已过期");
                return pr;
            }
            String status = couponDetail.getStatus();
            if (CouponStatus.USED.getCodeValue().equals(status)) {
                pr.setErrorMessage("1004", "优惠码已使用");
                return pr;
            }
            if (pr.isSuccess()) {
                List<Map<String, Object>> goodsMapList = new ArrayList<Map<String, Object>>();
                for (CashGoodsParam cashGoods : cashGoodsList) {
                    Map<String, Object> goodsMap = new HashMap<String, Object>();
                    goodsMap.put("goodsId", cashGoods.getGoodsId());
                    goodsMap.put("stcode", cashGoods.getStandardCode());
                    goodsMap.put("buyNum", cashGoods.getBuyNum());
                    goodsMapList.add(goodsMap);
                }
                int count = couponDetailService.updateCouponDetailCustomerToCache(couponDetail, customerId,
                        goodsMapList);
                if (count == 0) {
                    pr.setErrorMessage("1005", "兑换失败");
                    return pr;
                }
                CouponDetailFTInstance detailIns = CouponMapper.toDetailFTInstance(couponDetail);
                pr.setData(detailIns);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("bindCashCoupon", e);
            pr.setErrorMessage("999", "系统错误");
        }
        return pr;
    }

    @Override
    public ListResult<CouponReceiveFTInstance> getReceiveCouponList(Long goodsId, String requestId) {
        ListResult<CouponReceiveFTInstance> lr = new ListResult<CouponReceiveFTInstance>();
        lr.setRequestId(requestId);
        boolean paramValid = goodsId == null;
        if (paramValid) {
            lr.setErrorMessage("1000", "参数错误");
            return lr;
        }
        try {
            List<CouponReceiveFTInstance> couponReceiveList = new ArrayList<CouponReceiveFTInstance>();
            List<Coupon> couponList = couponService.getNotExpiredAndNotOuterCouponList();
            if (couponList != null) {
                Goods goods = goodsService.getGoodsBaseInfoFromCache(goodsId);
                if (goods != null) {
                    Long shopId = goods.getShopId();
                    for (Coupon coupon : couponList) {
                        String relType = coupon.getRelateType();
                        String typeIds = coupon.getTypeIds();
                        if (CouponRelateType.NO.getCodeValue().equals(relType)) {
                            CouponReceiveFTInstance cr = CouponMapper.toCouponReceiveFTInstance(coupon);
                            couponReceiveList.add(cr);
                        } else if (CouponRelateType.CATEGORY.getCodeValue().equals(relType)) {
                            Long categoryId = goods.getCategoryId();
                            String[] typeIdsArr = typeIds.split(",");
                            for (int i = 0; i < typeIdsArr.length; i++) {
                                String typeId = typeIdsArr[i];
                                if (String.valueOf(categoryId).equals(typeId)) {
                                    CouponReceiveFTInstance cr = CouponMapper.toCouponReceiveFTInstance(coupon);
                                    couponReceiveList.add(cr);
                                    break;
                                }
                            }
                        } else if (CouponRelateType.GOODS.getCodeValue().equals(relType)) {
                            Long gid = goods.getId();
                            String[] typeIdsArr = typeIds.split(",");
                            for (int i = 0; i < typeIdsArr.length; i++) {
                                String typeId = typeIdsArr[i];
                                if (String.valueOf(gid).equals(typeId)) {
                                    CouponReceiveFTInstance cr = CouponMapper.toCouponReceiveFTInstance(coupon);
                                    couponReceiveList.add(cr);
                                    break;
                                }
                            }
                        } else if (CouponRelateType.SHOP.getCodeValue().equals(relType)) {
                            if (String.valueOf(shopId).equals(typeIds)) {
                                CouponReceiveFTInstance cr = CouponMapper.toCouponReceiveFTInstance(coupon);
                                couponReceiveList.add(cr);
                            }
                        }
                    }
                }
            }
            lr.setData(couponReceiveList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getReceiveCouponList", e);
            lr.setErrorMessage("999", "系统错误");
        }
        return lr;
    }

    @Override
    public BaseResult receiveCoupon(Long couponId, Long customerId, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        boolean paramValid = couponId == null || customerId == null;
        if (paramValid) {
            br.setErrorMessage("1000", "参数错误");
            return br;
        }
        try {
            Coupon coupon = couponService.getCouponById(couponId);
            if (coupon == null) {
                br.setErrorMessage("1001", "优惠券不存在");
                return br;
            }
            Integer limit = coupon.getIsLimit();
            List<CouponDetail> couponDetailList = couponDetailService.getCouponDetailList(customerId, couponId);
            if (couponDetailList != null && couponDetailList.size() > 0) {
                br.setErrorMessage("1002", "已领过该优惠券");
                return br;
            }
            if (limit == 1) {
                CouponDetail couponDetail = couponDetailService.getOneUnReceiveCouponDetail(couponId);
                if (couponDetail == null) {
                    br.setErrorMessage("1004", "优惠券已发完");
                    return br;
                }
                String couponSn = couponDetail.getCouponSn();
                int count = couponDetailService.updateCouponDetailCustomer(couponSn, customerId);
                if (count == 0) {
                    br.setErrorMessage("1003", "优惠券领取失败");
                    return br;
                }
            } else {
                CouponDetail couponDetail = new CouponDetail();
                couponDetail.setBeginTime(coupon.getBeginTime());
                couponDetail.setEndTime(coupon.getEndTime());
                couponDetail.setCouponSn(CommUtil.generateShortUUID());
                couponDetail.setCouponMoney(coupon.getCouponMoney());
                couponDetail.setCouponDes(coupon.getCouponDes());
                couponDetail.setStatus(CouponStatus.UNUSED.getCodeValue());
                couponDetail.setFullCut(coupon.getFullCut());
                couponDetail.setRelateType(coupon.getRelateType());
                couponDetail.setTypeIds(coupon.getTypeIds());
                couponDetail.setCustomerId(customerId);
                couponDetail.setCouponId(couponId);
                couponDetail.setReceiveTime(new Date());
                Long id = couponDetailService.addOneCouponDetail(couponDetail);
                if (id == null) {
                    br.setErrorMessage("1003", "优惠券领取失败");
                    return br;
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("receiveCoupon", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public BaseResult addCouponQueue(SyncLotteryParam lotteryParam) {
        BaseResult br = new BaseResult();
        try {
            String telephone = lotteryParam.getTelephone();
            String type = lotteryParam.getType();
            String couponSn = lotteryParam.getCouponSn();
            String prizeName = lotteryParam.getPrizeName();
            String prizeImg = lotteryParam.getPrizeImg();
            String startTime = lotteryParam.getStartTime();
            String endTime = lotteryParam.getEndTime();
            boolean paramValid = StringUtils.isBlank(telephone) || StringUtils.isBlank(type)
                    || StringUtils.isBlank(endTime) || (!"3".equals(type) && !"4".equals(type));
            if (!paramValid) {//实物
                paramValid = "3".equals(type) && (StringUtils.isBlank(prizeName) || StringUtils.isBlank(prizeImg));
            }
            if (!paramValid) {//优惠券
                paramValid = "4".equals(type) && StringUtils.isBlank(couponSn);
            }
            if (paramValid) {
                br.setErrorMessage("1000", "参数错误");
            }
            CouponMsgVo msg = new CouponMsgVo();
            msg.setTelephone(telephone);
            if ("3".equals(type)) {
                msg.setType("1");
            } else if ("4".equals(type)) {
                msg.setType("2");
            }
            msg.setCouponCode(couponSn);
            msg.setPrizeName(prizeName);
            msg.setPrizeImg(prizeImg);
            msg.setStartTime(startTime);
            msg.setEndTime(endTime);
            couponQueue.pushFromTail(msg);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("addCouponQueue", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public PlainResult<String> orderUseCoupon(CashUseCouponParam useCouponParam) {
        PlainResult<String> pr = new PlainResult<String>();
        pr.setRequestId(useCouponParam.getRequestId());
        try {
            Long customerId = useCouponParam.getCustomerId();
            String couponCode = useCouponParam.getCouponCode();
            List<CashGoodsParam> cashGoodsList = useCouponParam.getCashGoodsList();
            if (customerId == null || cashGoodsList == null || cashGoodsList.isEmpty()) {
                pr.setErrorMessage("1000", "参数错误");
            }
            if (pr.isSuccess()) {
                List<Map<String, Object>> goodsMapList = new ArrayList<Map<String, Object>>();
                for (CashGoodsParam cashGoods : cashGoodsList) {
                    Map<String, Object> goodsMap = new HashMap<String, Object>();
                    goodsMap.put("goodsId", cashGoods.getGoodsId());
                    goodsMap.put("stcode", cashGoods.getStandardCode());
                    goodsMap.put("buyNum", cashGoods.getBuyNum());
                    goodsMapList.add(goodsMap);
                }
                CouponDetail couponDetail = null;
                if (StringUtils.isNotBlank(couponCode)) {
                    couponDetail = couponDetailService.queryUnusedCoupon(customerId, couponCode);
                }
                Long totalPrice = couponDetailService.calcTotalPrice(couponDetail, goodsMapList);
                pr.setData(new BigDecimal(totalPrice).divide(new BigDecimal(100)).toString());
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("orderUseCoupon", e);
            pr.setErrorMessage("999", "接口异常");
        }
        return pr;
    }

}
