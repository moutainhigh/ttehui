package com.mocentre.gift.buy.provider.front;

import com.alibaba.fastjson.JSON;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.gift.buy.enums.GiftOrderBillStatus;
import com.mocentre.gift.buy.enums.GiftOrderStatus;
import com.mocentre.gift.buy.mapper.GiftOrderMapper;
import com.mocentre.gift.buy.model.GiftOrder;
import com.mocentre.gift.buy.model.GiftOrderBill;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.gift.buy.service.IGiftGiftSheetService;
import com.mocentre.gift.buy.service.IGiftOrderBillService;
import com.mocentre.gift.buy.service.IGiftOrderDetailService;
import com.mocentre.gift.buy.service.IGiftOrderService;
import com.mocentre.gift.frontend.model.GiftOrderDetailFTInstance;
import com.mocentre.gift.frontend.model.GiftOrderPageFTInstance;
import com.mocentre.gift.frontend.param.GiftGiftSheetFTParam;
import com.mocentre.gift.frontend.param.GiftOrderQueryParam;
import com.mocentre.gift.frontend.service.GiftOrderManageService;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.gift.gd.service.IGiftGoodsService;
import com.mocentre.gift.ps.model.GiftCustomerAddress;
import com.mocentre.gift.ps.service.IGiftCustomerAddressService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.system.service.IAreasService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 订单相关接口
 * Created by 王雪莹 on 2017/4/17.
 */
public class GiftOrderManageServiceImpl implements GiftOrderManageService {
    @Autowired
    private IGiftOrderService service;
    @Autowired
    private IGiftOrderBillService billService;
    @Autowired
    private IGiftOrderDetailService detailService;
    @Autowired
    private IGiftCustomerAddressService addressService;
    @Autowired
    private IGiftGoodsService goodsService;
    @Autowired
    private IGiftGiftSheetService giftSheetService;
    @Autowired
    private IAreasService areasService;
    @Override
    /**
     * 提交订单
     * 1）更新数据到订单表，orderNum，total_price，customer_id，order_status（commit），order_time，note
     * 2）giftSheetList 更新到订单详情表，同时删除礼品单对应的数据，
     * 3）如果addressId不为空，将adressId更新到订单表，同时还有地名联系人等信息
     * 4）如果billStr 不为空，在bill新建数据，注意 orderNum，oderid的值
     * @param customerId
     * @param giftSheetList
     * @param note
     * @param billStr
     * @param addressId
     *@param requestId  @return
     */
    public PlainResult<String> commitOrder(Long customerId, List<GiftGiftSheetFTParam> giftSheetParamList, String note, String billStr, String addressId, String requestId) {
        PlainResult<String> result = new PlainResult<>();
        try {
            GiftOrder giftOrder = new GiftOrder();
            // orderNum 14位时间+用户id+随机数;共计20位
            Random random = new Random();
            StringBuilder sb = new StringBuilder(DateUtils.formatTime2(new Date()));
            sb.append(customerId);
            for(int i= 0;i<20-sb.length();i++){
                sb.append(random.nextInt(10));
            }
            giftOrder.setOrderNum(sb.toString());
            giftOrder.setOrderStatus(GiftOrderStatus.COMMIT.getCodeValue());
            giftOrder.setCustomerId(customerId);
            giftOrder.setNote(note);
            if(addressId != null){
                giftOrder.setAddressId(Long.valueOf(addressId));
                GiftCustomerAddress address = addressService.getAddressById(Long.valueOf(addressId));
                String prov = areasService.getNameFromCache(address.getProvince());
                String city = areasService.getNameFromCache(address.getCity());
                String area = areasService.getNameFromCache(address.getArea());
                giftOrder.setAddress(prov+city+area+address.getAddress());
                giftOrder.setTelephone(address.getTelephone());
                giftOrder.setRecipient(address.getRecipient());
            }
            if (billStr != null){
                giftOrder.setBillStatus(GiftOrderBillStatus.NOOPEN.getCodeValue());
            }
            service.saveOrder(giftOrder);

            BigDecimal totalPrice = new BigDecimal(0);
            List<GiftOrderDetail> detailList = new ArrayList<>();
            List<Long> giftSheetList = new ArrayList<>();
            int goodsNum = 0;
            for(GiftGiftSheetFTParam giftSheetParam:giftSheetParamList){
                // 计算商品总价
                GiftGoods giftGoods = goodsService.getGiftGoodsById(giftSheetParam.getGoodsId());
                totalPrice = totalPrice.add(giftGoods.getPrice().multiply(new BigDecimal(giftSheetParam.getNum())));
                //计算商品总量
                goodsNum += giftSheetParam.getNum();
                // 构造订单详情
                GiftOrderDetail detail = new GiftOrderDetail();
                detail.setGoodsId(giftSheetParam.getGoodsId());
                detail.setGoodsName(giftGoods.getTitle());
                detail.setGoodsAmount(giftSheetParam.getNum());
                detail.setGoodsImg(giftGoods.getImgListPagePc());
                detail.setGoodsPrice(giftGoods.getPrice().longValue());
                detail.setOrderNum(giftOrder.getOrderNum());
                detail.setOrderId(giftOrder.getId());
                detail.setId(giftOrder.getId());
                detailList.add(detail);
                // 获取购物车idList
                giftSheetList.add(giftSheetParam.getId());
            }
            // 更新商品总价 商品数量 商品种类数量到数据库
            DecimalFormat df = new DecimalFormat("#.00");
            giftOrder.setTotalPrice(String.valueOf(df.format(totalPrice.longValue() / 100f)));
            giftOrder.setGoodsNum(goodsNum);
            giftOrder.setTypeNum(giftSheetParamList.size());
            service.updateOrder(giftOrder);
            // 更新订单项表
            detailService.saveOrderDetail(detailList);
            // 删除礼品单对应数据
            giftSheetService.deleteGiftList(customerId,giftSheetList);

            // 更新发票表
            if(billStr != null){
                GiftOrderBill bill = JSON.parseObject(billStr, GiftOrderBill.class);
                bill.setOrderId(giftOrder.getId());
                bill.setCustomerId(giftOrder.getCustomerId());
                bill.setOrderNum(giftOrder.getOrderNum());
                billService.saveBill(bill);
            }

            result.setData(giftOrder.getTotalPrice());
        } catch (Exception e) {
            result.setErrorMessage("999", "系统错误");
            LoggerUtil.tehuiLog.error("commitOrder", e);
        }
        return result;
    }

    @Override
    public PlainResult<GiftOrderDetailFTInstance> orderDetail(Long customerId, String orderNum, String requestId) {
        PlainResult<GiftOrderDetailFTInstance> result = new PlainResult();
        try {
            GiftOrder order = service.queryOrder(orderNum);
            GiftOrderBill bill = billService.queryBillByOrder(orderNum);
            List<GiftOrderDetail> detailList = detailService.queryOrderDetail(orderNum);
            GiftOrderDetailFTInstance instance = GiftOrderMapper.toGiftOrderDetailFTInstance(order,bill,detailList);
            result.setData(instance);
        } catch (Exception e) {
            result.setErrorMessage("999", "系统错误");
            LoggerUtil.tehuiLog.error("delete", e);
        }
        return result;
    }

    @Override
    public PlainResult<GiftOrderDetailFTInstance> backedCancel(Long customerId, String orderNum, String requestId) {
        PlainResult<GiftOrderDetailFTInstance> result = new PlainResult();
        GiftOrder giftOrder = new GiftOrder();
        giftOrder.setOrderNum(orderNum);
        giftOrder.setCustomerId(customerId);
        giftOrder.setOrderStatus(GiftOrderStatus.COMMIT.getCodeValue());
        GiftOrder order = service.queryOrder(orderNum);
        giftOrder.setId(order.getId());
        try {
            service.updateOrder(giftOrder);

            GiftOrderBill bill = billService.queryBillByOrder(orderNum);
            List<GiftOrderDetail> detailList = detailService.queryOrderDetail(orderNum);
            order.setOrderStatus(GiftOrderStatus.COMMIT.getCodeValue());
            GiftOrderDetailFTInstance instance = GiftOrderMapper.toGiftOrderDetailFTInstance(order,bill,detailList);
            result.setData(instance);
        } catch (Exception e) {
            result.setErrorMessage("999", "系统错误");
            LoggerUtil.tehuiLog.error("backedCancel", e);
        }
        return result;
    }

    @Override
    public PlainResult<GiftOrderDetailFTInstance> cancel(Long customerId, String orderNum, String requestId) {
        PlainResult<GiftOrderDetailFTInstance> result = new PlainResult();
        GiftOrder giftOrder = new GiftOrder();
        giftOrder.setOrderNum(orderNum);
        giftOrder.setCustomerId(customerId);
        giftOrder.setOrderStatus(GiftOrderStatus.WAIT_CHECK.getCodeValue());
        GiftOrder order = service.queryOrder(orderNum);
        giftOrder.setId(order.getId());
        try {
            service.updateOrder(giftOrder);
            GiftOrderBill bill = billService.queryBillByOrder(orderNum);
            List<GiftOrderDetail> detailList = detailService.queryOrderDetail(orderNum);
            order.setOrderStatus(GiftOrderStatus.WAIT_CHECK.getCodeValue());
            GiftOrderDetailFTInstance instance = GiftOrderMapper.toGiftOrderDetailFTInstance(order,bill,detailList);

            result.setData(instance);
        } catch (Exception e) {
            result.setErrorMessage("999", "系统错误");
            LoggerUtil.tehuiLog.error("cancel", e);
        }
        return result;
    }

    @Override
    public ListResult<GiftOrderPageFTInstance> orderList(GiftOrderQueryParam orderParam) {
        ListResult<GiftOrderPageFTInstance> result = new ListResult<GiftOrderPageFTInstance>();
        result.setRequestId(orderParam.getRequestId());
        try {
            List<GiftOrderPageFTInstance> orderInsList = new ArrayList<GiftOrderPageFTInstance>();
            Long customerId = orderParam.getCustomerId();
            Integer start = orderParam.getStart();
            Integer length = orderParam.getLength();
            PageInfo<GiftOrder> orderPage = service.queryOrderPage(customerId, start, length);
            List<GiftOrder> orderList = orderPage.getRows();
            if (orderList != null) {
                for (GiftOrder order : orderList) {
                    String orderNum = order.getOrderNum();
                    GiftOrderPageFTInstance ins = GiftOrderMapper.toGiftOrderPageFTInstance(order);
                    orderInsList.add(ins);
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
}
