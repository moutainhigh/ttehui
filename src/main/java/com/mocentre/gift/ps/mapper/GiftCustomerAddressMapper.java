/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.ps.mapper;

import com.mocentre.gift.backend.model.GiftCustomerAddressInstance;
import com.mocentre.gift.frontend.model.GiftCustomerAddressFTInstance;
import com.mocentre.gift.frontend.param.GiftCustomerAddsParam;
import com.mocentre.gift.ps.model.GiftCustomerAddress;

/**
 * 礼品平台 客户收货地址转换
 * @author liqifan
 * @date 创建时间：2017年4月13日 上午10:18:55
 */
public class GiftCustomerAddressMapper {

    public static GiftCustomerAddressInstance toGiftCustomerAddressInstance(GiftCustomerAddress cumAdds) {
        GiftCustomerAddressInstance cumAddsIns = new GiftCustomerAddressInstance();
        cumAddsIns.setAddress(cumAdds.getAddress());
        cumAddsIns.setArea(cumAdds.getArea());
        cumAddsIns.setCity(cumAdds.getCity());
        cumAddsIns.setCustomerId(cumAdds.getCustomerId());
        cumAddsIns.setId(cumAdds.getId());
        cumAddsIns.setIsDefault(cumAdds.getIsDefault());
        cumAddsIns.setPeriod(cumAddsIns.getPeriod());
        cumAddsIns.setPostCode(cumAdds.getPostCode());
        cumAddsIns.setProvince(cumAdds.getProvince());
        cumAddsIns.setRecipient(cumAdds.getRecipient());
        cumAddsIns.setTelephone(cumAdds.getTelephone());
        return cumAddsIns;
    }

    public static GiftCustomerAddressFTInstance toGiftCustomerAddressFTInstance(GiftCustomerAddress cumAdds) {
        GiftCustomerAddressFTInstance cumAddsIns = new GiftCustomerAddressFTInstance();
        cumAddsIns.setAddress(cumAdds.getAddress());
        cumAddsIns.setArea(cumAdds.getArea());
        cumAddsIns.setCity(cumAdds.getCity());
        cumAddsIns.setCustomerId(cumAdds.getCustomerId());
        cumAddsIns.setId(cumAdds.getId());
        cumAddsIns.setIsDefault(cumAdds.getIsDefault());
        cumAddsIns.setPeriod(cumAdds.getPeriod());
        cumAddsIns.setPostCode(cumAdds.getPostCode());
        cumAddsIns.setProvince(cumAdds.getProvince());
        cumAddsIns.setRecipient(cumAdds.getRecipient());
        cumAddsIns.setTelephone(cumAdds.getTelephone());
        return cumAddsIns;
    }

    public static GiftCustomerAddress toGiftCustomerAddress(GiftCustomerAddsParam giftCustomerAddsParam) {
        GiftCustomerAddress cumAdds = new GiftCustomerAddress();
        cumAdds.setId(giftCustomerAddsParam.getId());
        cumAdds.setAddress(giftCustomerAddsParam.getAddress());
        cumAdds.setArea(giftCustomerAddsParam.getArea());
        cumAdds.setCity(giftCustomerAddsParam.getCity());
        cumAdds.setCustomerId(giftCustomerAddsParam.getCustomerId());
        cumAdds.setIsDefault(giftCustomerAddsParam.getIsDefault());
        cumAdds.setPeriod(giftCustomerAddsParam.getPeriod());
        cumAdds.setPostCode(giftCustomerAddsParam.getPostCode());
        cumAdds.setProvince(giftCustomerAddsParam.getProvince());
        cumAdds.setRecipient(giftCustomerAddsParam.getRecipient());
        cumAdds.setTelephone(giftCustomerAddsParam.getTelephone());
        return cumAdds;
    }

}
