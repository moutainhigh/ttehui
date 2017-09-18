package com.mocentre.gift.ps.mapper;

import com.mocentre.gift.backend.model.GiftCustomerInstance;
import com.mocentre.gift.backend.param.GiftCustomerParam;
import com.mocentre.gift.frontend.model.GiftCustomerFTInstance;
import com.mocentre.gift.ps.model.GiftCustomer;

import java.util.ArrayList;
import java.util.List;

public class GiftCustomerMapper {

	/**
     * 转前台返回实体类
     */
    public static GiftCustomerInstance toGiftCustomerInstance(GiftCustomer giftCustomer) {
        GiftCustomerInstance giftCustomerInsList = new GiftCustomerInstance();
		giftCustomerInsList.setCustomerName(giftCustomer.getCustomerName());
        giftCustomerInsList.setId(giftCustomer.getId());
        giftCustomerInsList.setUserName(giftCustomer.getUserName());
        giftCustomerInsList.setTelephone(giftCustomer.getTelephone());
        giftCustomerInsList.setOperator(giftCustomer.getOperator());
        giftCustomerInsList.setOptTelephone(giftCustomer.getOptTelephone());
        giftCustomerInsList.setOrganization(giftCustomer.getOrganization());
        giftCustomerInsList.setGmtCreated(giftCustomer.getGmtCreated());
        giftCustomerInsList.setGmtModified(giftCustomer.getGmtModified());
        return giftCustomerInsList;
    }
    
    /**
     * 转前台返回值列表
     */
    public static List<GiftCustomerInstance> toGiftCustomerInstanceList(List<GiftCustomer> giftCustomerList) {
        List<GiftCustomerInstance> giftCustomerInsList = new ArrayList<GiftCustomerInstance>();
        if (giftCustomerList == null || giftCustomerList.size() < 1) {
            return giftCustomerInsList;
        }

        for (GiftCustomer actGoods : giftCustomerList) {
        	giftCustomerInsList.add(toGiftCustomerInstance(actGoods));
        }
        return giftCustomerInsList;
    }

	public static GiftCustomer toGiftCustomer(GiftCustomerParam giftCustomerParam) {
		GiftCustomer giftCustomer = new GiftCustomer();
		giftCustomer.setCustomerName(giftCustomerParam.getCustomerName());
		giftCustomer.setId(giftCustomerParam.getId());
		giftCustomer.setTelephone(giftCustomerParam.getTelephone());
		giftCustomer.setUserName(giftCustomerParam.getUserName());
		giftCustomer.setPassword(giftCustomerParam.getPassword());
		giftCustomer.setRandomNum(giftCustomerParam.getRandomNum());
		giftCustomer.setOperator(giftCustomerParam.getOperator());
		giftCustomer.setOptTelephone(giftCustomerParam.getOptTelephone());
		giftCustomer.setOrganization(giftCustomerParam.getOrganization());
		return giftCustomer;
	}
	
	public static GiftCustomerParam toGiftCustomerParam(GiftCustomer giftCustomer) {
		GiftCustomerParam giftCustomerParam = new GiftCustomerParam();
		giftCustomerParam.setCustomerName(giftCustomer.getCustomerName());
		giftCustomerParam.setId(giftCustomer.getId());
		giftCustomerParam.setTelephone(giftCustomer.getTelephone());
		giftCustomerParam.setUserName(giftCustomer.getUserName());
		giftCustomerParam.setPassword(giftCustomer.getPassword());
		giftCustomerParam.setRandomNum(giftCustomer.getRandomNum());
		giftCustomerParam.setOperator(giftCustomer.getOperator());
		giftCustomerParam.setOptTelephone(giftCustomer.getOptTelephone());
		giftCustomerParam.setOrganization(giftCustomer.getOrganization());
		return giftCustomerParam;
	}

	public static GiftCustomerFTInstance toGiftCustomerFTInstance(GiftCustomer giftCustomer) {
		GiftCustomerFTInstance ftInstance = new GiftCustomerFTInstance();
		ftInstance.setCustomerName(giftCustomer.getCustomerName());
		ftInstance.setId(giftCustomer.getId());
		ftInstance.setUserName(giftCustomer.getUserName());
		ftInstance.setTelephone(giftCustomer.getTelephone());
		ftInstance.setOrganization(giftCustomer.getOrganization());
		return ftInstance;
	}
}
