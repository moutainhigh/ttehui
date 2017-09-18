package com.mocentre.gift.dma.mapper;

import com.mocentre.gift.backend.model.GiftDemandInstance;
import com.mocentre.gift.dma.model.GiftDemand;
import com.mocentre.gift.frontend.param.GiftDemandParam;
import com.mocentre.gift.ps.model.GiftCustomer;

public class GiftDemandMapper {

	/**
     * 转前台返回实体类
     */
    public static GiftDemandInstance toGiftDemandInstance(GiftDemand giftDemand, GiftCustomer giftCustomer) {
        GiftDemandInstance giftDemandInstance = new GiftDemandInstance();
        giftDemandInstance.setId(giftDemand.getId());
        giftDemandInstance.setCustomerId(giftCustomer.getId());
        giftDemandInstance.setCustomerName(giftCustomer.getUserName());
        giftDemandInstance.setCustomerOrg(giftCustomer.getOrganization());
        giftDemandInstance.setBudget(giftDemand.getBudget());
        giftDemandInstance.setGiftFeature(giftDemand.getGiftFeature());
        giftDemandInstance.setGiftNum(giftDemand.getGiftNum());
        giftDemandInstance.setScene(giftDemand.getScene());
        giftDemandInstance.setTelephone(giftDemand.getTelephone());
        giftDemandInstance.setStatus(giftDemand.getStatus());
        giftDemandInstance.setGmtCreated(giftDemand.getGmtCreated());
        return giftDemandInstance;
    }


    public static GiftDemand toGiftDemand(GiftDemandParam giftDemandParam) {
        GiftDemand giftDemand = new GiftDemand();
        giftDemand.setCustomerId(giftDemandParam.getCustomerId());
        giftDemand.setBudget(giftDemandParam.getBudget());
        giftDemand.setGiftFeature(giftDemandParam.getGiftFeature());
        giftDemand.setGiftNum(giftDemandParam.getGiftNum());
        giftDemand.setScene(giftDemandParam.getScene());
        giftDemand.setTelephone(giftDemandParam.getTelephone());
        return giftDemand;
    }
}
