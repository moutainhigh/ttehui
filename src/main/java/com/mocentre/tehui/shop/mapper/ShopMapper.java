/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.shop.mapper;

import com.mocentre.tehui.backend.model.ShopInstance;
import com.mocentre.tehui.backend.param.ShopParam;
import com.mocentre.tehui.frontend.model.ShopHomInfoFTInstance;
import com.mocentre.tehui.shop.enums.ShopAuditStatus;
import com.mocentre.tehui.shop.enums.ShopBussStatus;
import com.mocentre.tehui.shop.model.Shop;

/**
 * 类ShopMapper.java的实现描述：店铺转换
 * 
 * @author sz.gong 2016年12月12日 下午2:20:20
 */
public class ShopMapper {

    public static ShopInstance toShopInstance(Shop shop) {
        ShopInstance shopIns = new ShopInstance();
        shopIns.setId(shop.getId());
        shopIns.setAddress(shop.getAddress());
        shopIns.setAudit_status(shop.getAudit_status());
        shopIns.setBuss_status(shop.getBuss_status());
        shopIns.setGmtCreated(shop.getGmtCreated());
        shopIns.setImg_logo(shop.getImg_logo());
        shopIns.setLeader(shop.getLeader());
        shopIns.setLevel(shop.getLevel());
        shopIns.setName(shop.getName());
        shopIns.setOpen_date(shop.getOpen_date());
        shopIns.setShow_url(shop.getShow_url());
        return shopIns;
    }

    public static Shop toShop(ShopParam shopParam) {
        Shop shop = new Shop();
        shop.setId(shopParam.getId());
        shop.setAddress(shopParam.getAddress());
        shop.setImg_logo(shopParam.getImg_logo());
        shop.setLeader(shopParam.getLeader());
        shop.setLevel(shopParam.getLevel());
        shop.setName(shopParam.getName());
        shop.setOpen_date(shopParam.getOpen_date());
        shop.setShow_url(shopParam.getShow_url());
        if (shopParam.getIs_admin() != null) {
            shop.setBuss_status(ShopBussStatus.OPENING.getCodeValue());
            shop.setAudit_status(ShopAuditStatus.PASS.getCodeValue());
        } else {
            shop.setBuss_status(ShopBussStatus.OPENING.getCodeValue());
            shop.setAudit_status(ShopAuditStatus.WAIT.getCodeValue());
        }
        return shop;
    }


    public static ShopHomInfoFTInstance toShopHomPageFTInstance(Shop shop) {
        ShopHomInfoFTInstance instance = new ShopHomInfoFTInstance();
        instance.setId(shop.getId());
        instance.setLogo(shop.getImg_logo());
        instance.setName(shop.getName());

        return instance;
    }
}
