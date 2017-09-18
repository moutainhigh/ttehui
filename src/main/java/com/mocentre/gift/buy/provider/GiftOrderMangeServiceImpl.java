package com.mocentre.gift.buy.provider;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.gift.backend.model.GiftOrderGoodsDetailInstance;
import com.mocentre.gift.backend.model.GiftOrderInfoInstance;
import com.mocentre.gift.backend.model.GiftOrderPageInstance;
import com.mocentre.gift.backend.param.GiftBillParam;
import com.mocentre.gift.backend.param.GiftExpParam;
import com.mocentre.gift.backend.param.GiftOrderDetailParam;
import com.mocentre.gift.backend.param.GiftOrderParam;
import com.mocentre.gift.backend.service.GiftOrderMangeService;
import com.mocentre.gift.buy.enums.GiftOrderStatus;
import com.mocentre.gift.buy.mapper.GiftOrderBillMapper;
import com.mocentre.gift.buy.mapper.GiftOrderDetailMapper;
import com.mocentre.gift.buy.mapper.GiftOrderMapper;
import com.mocentre.gift.buy.model.GiftOrder;
import com.mocentre.gift.buy.model.GiftOrderBill;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.gift.buy.service.IGiftOrderBillService;
import com.mocentre.gift.buy.service.IGiftOrderDetailService;
import com.mocentre.gift.buy.service.IGiftOrderService;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.gift.ps.service.IGiftCustomerService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理service Created by 王雪莹 on 2017/4/11.
 */
public class GiftOrderMangeServiceImpl implements GiftOrderMangeService {
    @Autowired
    private IGiftOrderService       orderService;
    @Autowired
    private IGiftOrderDetailService detailService;
    @Autowired
    private IGiftOrderBillService   billService;
    @Autowired
    private IGiftCustomerService    customerService;

    @Override
    public ListJsonResult<GiftOrderPageInstance> queryPage(GiftOrderParam orderQuery) {
        ListJsonResult<GiftOrderPageInstance> result = new ListJsonResult<GiftOrderPageInstance>();
        List<GiftOrderPageInstance> orderInsList = new ArrayList<GiftOrderPageInstance>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();

            //获取用户信息
            if (!StringUtils.isBlank(orderQuery.getCustomerName())
                    || !StringUtils.isBlank(orderQuery.getCustomerTelephone())) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("customerName", orderQuery.getCustomerName());
                map.put("telephone", orderQuery.getCustomerTelephone());
                List<GiftCustomer> customerList = customerService.selectCustomer(map);
                if (customerList.size() > 0) {
                    ArrayList<Long> customerIdList = new ArrayList<>();
                    for (GiftCustomer customer : customerList) {
                        customerIdList.add(customer.getId());
                    }
                    paramMap.put("customerIdList", customerIdList);
                } else {
                    result.setData(orderInsList);
                    return result;
                }
            }

            paramMap.put("orderNum", orderQuery.getOrderNum());
            paramMap.put("orderStatus", orderQuery.getOrderStatus());
            paramMap.put("column", orderQuery.getOrderColumn() == null ? "orderTime" : orderQuery.getOrderColumn());
            paramMap.put("orderBy", orderQuery.getOrderBy() == null ? "desc" : orderQuery.getOrderBy());
            Requirement require = new Requirement(orderQuery.getDraw(), orderQuery.getStart(), orderQuery.getLength(),
                    paramMap);
            ListJsonResult<GiftOrder> pageInfo = orderService.queryOrderPage(require);
            List<GiftOrder> orderList = pageInfo.getData();

            if (orderList != null && orderList.size() > 0) {
                for (int i = 0; i < orderList.size(); i++) {
                    GiftOrder order = orderList.get(i);
                    GiftOrderPageInstance orderIns = GiftOrderMapper.toGiftOrderPageInstance(order);
                    // 获取用户信息
                    GiftCustomer customer = customerService.getGiftCustomer(order.getCustomerId());
                    orderIns.setCustomerTelephone(customer.getTelephone());
                    orderIns.setCustomerName(customer.getCustomerName());
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
    public BaseResult deleteById(Long id, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            orderService.delById(id);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteById", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

    @Override
    public PlainResult<GiftOrderInfoInstance> queryOrderDetail(Long id, String requestId) {
        PlainResult<GiftOrderInfoInstance> result = new PlainResult<>();
        try {
            if (id == null) {
                result.setErrorMessage("1000", "id不能为空");
            }
            if (result.isSuccess()) {
                GiftOrder order = orderService.queryOrder(id);
                if (order == null) {
                    result.setErrorMessage("1001", "订单不存在");
                    return result;
                }
                String orderNum = order.getOrderNum();
                List<GiftOrderDetail> detailList = detailService.queryOrderDetail(orderNum);
                GiftOrderBill bill = billService.queryBillByOrder(orderNum);
                GiftOrderInfoInstance orderIns = GiftOrderMapper.toGiftOrderInfoInstance(order, detailList, bill);
                GiftCustomer giftCustomer = customerService.getGiftCustomer(order.getCustomerId());
                orderIns.setCustomerTelephone(giftCustomer.getTelephone());
                orderIns.setCustomerName(giftCustomer.getCustomerName());

                result.setData(orderIns);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryOrderDetail", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult editSendOrder(GiftExpParam expParam) {
        BaseResult result = new BaseResult();
        try {
            GiftOrder giftOrder = new GiftOrder();
            giftOrder.setOrderStatus(GiftOrderStatus.SEND.getCodeValue());
            giftOrder.setId(expParam.getOrderId());
            giftOrder.setExpCompany(expParam.getExpCompany());
            giftOrder.setExpNum(expParam.getExpNum());
            if (giftOrder.getId() == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                GiftOrder order = orderService.queryOrder(giftOrder.getId());
                if (order == null) {
                    result.setErrorMessage("1001", "数据异常");
                    return result;
                }
                orderService.updateOrder(giftOrder);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editSendOrder", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult editStatus(Long id, String status, String requestId) {
        BaseResult result = new BaseResult();
        try {
            GiftOrder giftOrder = new GiftOrder();
            giftOrder.setId(id);
            giftOrder.setOrderStatus(status);
            if (giftOrder.getId() == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                orderService.updateOrder(giftOrder);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editStatus", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult editAddress(long id, String recipient, String telephone, String address, String requestId) {
        BaseResult result = new BaseResult();
        try {
            GiftOrder giftOrder = new GiftOrder();
            giftOrder.setId(id);
            giftOrder.setRecipient(recipient);
            giftOrder.setTelephone(telephone);
            giftOrder.setAddress(address);
            if (giftOrder.getId() == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                orderService.updateOrder(giftOrder);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editAddress", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    /**
     * 修改发票信息
     *
     * @param param
     * @return
     */
    @Override
    public BaseResult editBill(GiftBillParam param) {
        BaseResult result = new BaseResult();
        try {
            GiftOrderBill bill = GiftOrderBillMapper.toGiftOrderBill(param);
            if (bill.getOrderId() == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                GiftOrderBill giftOrderBill = billService.queryBillById(bill.getOrderId());
                if (giftOrderBill != null) {
                    billService.delById(giftOrderBill.getId());
                }
                billService.saveBill(bill);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editAddress", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult editDetail(GiftOrderDetailParam param) {
        BaseResult result = new BaseResult();
        try {
            GiftOrderDetail detail = GiftOrderDetailMapper.toGiftOrderDetail(param);
            if (detail.getOrderId() == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                if (detail.getId() != null) {
                    detailService.updateOrderDetail(detail);
                } else {
                    detailService.saveOrderDetail(detail);
                }

                //更新订单信息
                List<GiftOrderDetail> detailList = detailService.queryOrderDetail(param.getOrderNum());
                Long totalPrice = 0l;
                int goodsNum = 0;
                int typeNum = 0;
                for (GiftOrderDetail giftOrderDetail : detailList) {
                    //计算商品总量
                    totalPrice += giftOrderDetail.getGoodsPrice() * giftOrderDetail.getGoodsAmount();
                    goodsNum += giftOrderDetail.getGoodsAmount();
                    typeNum += 1;
                }
                GiftOrder giftOrder = new GiftOrder();
                giftOrder.setOrderNum(detail.getOrderNum());
                giftOrder.setId(detail.getOrderId());
                giftOrder.setTypeNum(typeNum);
                giftOrder.setGoodsNum(goodsNum);
                DecimalFormat df = new DecimalFormat("#.00");
                giftOrder.setTotalPrice(String.valueOf(df.format(totalPrice / 100)));
                orderService.updateOrder(giftOrder);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editAddress", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult editDetailList(List<GiftOrderGoodsDetailInstance> detailList, Long orderId, String requestId) {
        BaseResult result = new BaseResult();
        try {

            if (orderId == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            GiftOrder order = orderService.queryOrder(orderId);
            if (order == null) {
                result.setErrorMessage("1000", "参数错误");
            }
            if (result.isSuccess()) {
                ArrayList<GiftOrderDetail> GiftOrderDetailList = new ArrayList<>();
                for (GiftOrderGoodsDetailInstance instance : detailList) {
                    GiftOrderDetail giftOrderDetail = GiftOrderDetailMapper.toGiftOrderDetail(instance);
                    giftOrderDetail.setOrderId(orderId);
                    giftOrderDetail.setOrderNum(order.getOrderNum());
                    GiftOrderDetailList.add(giftOrderDetail);
                }
                detailService.saveOrderDetail(GiftOrderDetailList);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("editDetailList", e);
            result.setErrorMessage("999", "系统异常");
        }
        return result;
    }

    @Override
    public BaseResult deleteDetailById(Long id, String requestId) {
        BaseResult br = new BaseResult();
        br.setRequestId(requestId);
        try {
            detailService.removeById(id);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("deleteById", e);
            br.setErrorMessage("999", "系统错误");
        }
        return br;
    }

}
