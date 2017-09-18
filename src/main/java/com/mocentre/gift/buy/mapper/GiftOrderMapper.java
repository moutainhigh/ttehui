package com.mocentre.gift.buy.mapper;

import com.mocentre.gift.backend.model.GiftOrderInfoInstance;
import com.mocentre.gift.backend.model.GiftOrderPageInstance;
import com.mocentre.gift.buy.enums.GiftOrderStatus;
import com.mocentre.gift.buy.model.GiftOrder;
import com.mocentre.gift.buy.model.GiftOrderBill;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.gift.frontend.model.GiftBillFTInstance;
import com.mocentre.gift.frontend.model.GiftGoodsPageFTInstance;
import com.mocentre.gift.frontend.model.GiftOrderDetailFTInstance;
import com.mocentre.gift.frontend.model.GiftOrderPageFTInstance;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

/**
 * GiftOrderMapper
 * Created by 王雪莹 on 2017/4/11.
 */
public class GiftOrderMapper {
    public static GiftOrderPageInstance toGiftOrderPageInstance(GiftOrder order) {
        GiftOrderPageInstance instance = new GiftOrderPageInstance();
        instance.setId(order.getId());
        instance.setOrderNum(order.getOrderNum());
        instance.setOrderStatus(order.getOrderStatus());
        instance.setOrderTime(order.getOrderTime());
        instance.setTotalPrice(order.getTotalPrice());
        return instance;
    }

    public static GiftOrderInfoInstance toGiftOrderInfoInstance(GiftOrder order, List<GiftOrderDetail> detailList, GiftOrderBill bill) {
        GiftOrderInfoInstance instance = new GiftOrderInfoInstance();
        if( order != null ){
            instance.setId(order.getId());
            instance.setCustomerId(order.getCustomerId());
            instance.setOrderNum(order.getOrderNum());
            instance.setOrderTime(order.getOrderTime());
            instance.setOrderStatus(order.getOrderStatus());
            instance.setTotalPrice(order.getTotalPrice());
            instance.setRecipient(order.getRecipient());
            instance.setTelephone(order.getTelephone());
            instance.setAddress(order.getAddress());
            instance.setNote(order.getNote());
            instance.setExpCompany(order.getExpCompany());
            instance.setExpNum(order.getExpNum());
        }
        if(detailList != null && detailList.size()>0){
            instance.setOrderGoodsDetailList(GiftOrderDetailMapper.toGiftOrderGoodsDetailInstanceList(detailList));
        }
        if(bill != null){
            instance.setBill(GiftOrderBillMapper.toGiftOrderBillInstance(bill));
        }
        return instance;
    }

    public static GiftOrderDetailFTInstance toGiftOrderDetailFTInstance(GiftOrder order, GiftOrderBill bill, List<GiftOrderDetail> detailList) {
        GiftOrderDetailFTInstance instance = new GiftOrderDetailFTInstance();
        BeanCopier copier = BeanCopier.create(GiftOrder.class, GiftOrderDetailFTInstance.class,false);
        copier.copy(order,instance,  null);
        instance.setTotalPrice(order.getTotalPrice());
        if(bill != null){
            GiftBillFTInstance billInstance = GiftOrderBillMapper.toGiftBillFTInstance(bill);
            instance.setBill(billInstance);
        }
       if(detailList !=null){
           List<GiftGoodsPageFTInstance> detailInstanceList = GiftOrderDetailMapper.toGiftGoodsPageFTInstanceList(detailList);
           instance.setOrderGoodsDetailList(detailInstanceList);
       }
        instance.setOrderStatusStr(GiftOrderStatus.getName(order.getOrderStatus()));
        return instance;
    }
    public static GiftOrderPageFTInstance toGiftOrderPageFTInstance(GiftOrder order) {
        GiftOrderPageFTInstance instance = new GiftOrderPageFTInstance();
        instance.setOrderStatus(order.getOrderStatus());
        instance.setOrderNum(order.getOrderNum());
        instance.setOrderTime(order.getOrderTime());

        instance.setOrderStatusStr(GiftOrderStatus.getName(order.getOrderStatus()));
        instance.setTotalPrice(order.getTotalPrice());
        instance.setGoodsNum(order.getGoodsNum());
        instance.setTypeNum(order.getTypeNum());
        return instance;
    }


}
