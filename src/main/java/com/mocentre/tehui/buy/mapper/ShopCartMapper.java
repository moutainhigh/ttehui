/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.mapper;

import java.math.BigDecimal;

import com.mocentre.tehui.buy.enums.ShopCartGoodsStatus;
import com.mocentre.tehui.buy.model.ShoppingCart;
import com.mocentre.tehui.frontend.model.ShopCartDetailFTInstance;
import com.mocentre.tehui.frontend.model.ShopCartNumFTInstance;
import com.mocentre.tehui.frontend.param.ShopCartDetailParam;
import com.mocentre.tehui.goods.enums.GoodsCheckedType;
import com.mocentre.tehui.goods.enums.GoodsShowType;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * 类ShopCartMapper.java的实现描述：购物车转换
 * 
 * @author sz.gong 2016年12月14日 下午2:35:45
 */
public class ShopCartMapper {

    public static ShoppingCart toShoppingCart(Long customerId, ShopCartDetailParam shopCartDetailParam) {
        ShoppingCart shopCart = new ShoppingCart();
        shopCart.setActGoodsId(shopCartDetailParam.getActGoodsId());
        shopCart.setCustomerId(customerId);
        shopCart.setGoodsId(shopCartDetailParam.getGoodsId());
        shopCart.setGoodsName(shopCartDetailParam.getGoodsName());
        shopCart.setGoodsSku(shopCartDetailParam.getGoodsSku());
        shopCart.setGoodsSkuDes(shopCartDetailParam.getGoodsSkuDes());
        shopCart.setNum(shopCartDetailParam.getBuyNum());
        shopCart.setOldPrice(shopCartDetailParam.getOldPrice() == null ? 0l : shopCartDetailParam.getOldPrice()
                .multiply(new BigDecimal(0)).longValue());
        shopCart.setSellPrice(shopCartDetailParam.getSellPrice() == null ? 0l : shopCartDetailParam.getSellPrice()
                .multiply(new BigDecimal(0)).longValue());
        shopCart.setShopId(shopCartDetailParam.getShopId());
        shopCart.setShopName(shopCartDetailParam.getShopName());
        shopCart.setShowLogo(shopCartDetailParam.getShowLogo());
        return shopCart;
    }

    public static ShopCartNumFTInstance toShopCartNumFTInstance(ShoppingCart shopCart, Integer totalNum) {
        ShopCartNumFTInstance scnFtIns = new ShopCartNumFTInstance();
        ShopCartDetailFTInstance scdFtIns = new ShopCartDetailFTInstance();
        scdFtIns.setActGoodsId(shopCart.getActGoodsId());
        scdFtIns.setBuyNum(shopCart.getNum());
        scdFtIns.setGoodsId(shopCart.getGoodsId());
        scdFtIns.setGoodsName(shopCart.getGoodsName());
        scdFtIns.setGoodsSku(shopCart.getGoodsSku());
        scdFtIns.setGoodsSkuDes(shopCart.getGoodsSkuDes());
        scdFtIns.setShopId(shopCart.getShopId());
        scdFtIns.setShopName(shopCart.getShopName());
        scnFtIns.setShopCartDetail(scdFtIns);
        scnFtIns.setTotalNum(totalNum);
        return scnFtIns;
    }

    public static ShopCartDetailFTInstance toShopCartDetailFTInstance(ShoppingCart shopCart, Goods goods,
                                                                      GoodsStorage goodsStorage) {
        ShopCartDetailFTInstance cartIns = new ShopCartDetailFTInstance();
        cartIns.setActGoodsId(shopCart.getActGoodsId());
        cartIns.setGoodsId(shopCart.getGoodsId());
        cartIns.setGoodsName(shopCart.getGoodsName());
        cartIns.setGoodsSku(shopCart.getGoodsSku());
        cartIns.setGoodsSkuDes(shopCart.getGoodsSkuDes());
        cartIns.setId(shopCart.getId());
        if (goods != null) {
            String show = goods.getIsShow();
            String checked = goods.getIsChecked();
            if (GoodsShowType.OFFSHELF.getCodeValue().equals(show)
                    || GoodsShowType.NOTSHELF.getCodeValue().equals(show)) {
                cartIns.setGoodsStatus(ShopCartGoodsStatus.OFFSHELF.getCodeValue());
                cartIns.setGoodsStatusStr(ShopCartGoodsStatus.OFFSHELF.getNameValue());
            } else if (GoodsCheckedType.CHECKFAIL.getCodeValue().equals(checked)
                    || GoodsCheckedType.UNCHECK.getCodeValue().equals(show)) {
                cartIns.setGoodsStatus(GoodsCheckedType.CHECKFAIL.getCodeValue());
                cartIns.setGoodsStatusStr(GoodsCheckedType.CHECKFAIL.getNameValue());
            } else {
                if (goodsStorage != null) {
                    Integer buyNum = shopCart.getNum();
                    Long storage = goodsStorage.getStockNum();
                    if (storage.intValue() < buyNum) {
                        cartIns.setGoodsStatus(ShopCartGoodsStatus.SOLDOUT.getCodeValue());
                        cartIns.setGoodsStatusStr(ShopCartGoodsStatus.SOLDOUT.getNameValue());
                    } else {
                        cartIns.setGoodsStatus(ShopCartGoodsStatus.NORMAL.getCodeValue());
                        cartIns.setGoodsStatusStr(ShopCartGoodsStatus.NORMAL.getNameValue());
                    }
                } else {
                    cartIns.setGoodsStatus(ShopCartGoodsStatus.SOLDOUT.getCodeValue());
                    cartIns.setGoodsStatusStr(ShopCartGoodsStatus.SOLDOUT.getNameValue());
                }
            }
        } else {
            cartIns.setGoodsStatus(ShopCartGoodsStatus.LOSE.getCodeValue());
            cartIns.setGoodsStatusStr(ShopCartGoodsStatus.LOSE.getNameValue());
        }
        if (goodsStorage != null) {
            Long oldPrice = goodsStorage.getOldPrice();
            Long salePrice = goodsStorage.getSalePrice();
            cartIns.setOldPrice(oldPrice == null ? new BigDecimal(0) : new BigDecimal(oldPrice).divide(new BigDecimal(
                    100)));
            cartIns.setSellPrice(salePrice == null ? new BigDecimal(0) : new BigDecimal(salePrice)
                    .divide(new BigDecimal(100)));
        } else {
            Long oldPrice = shopCart.getOldPrice();
            Long salePrice = shopCart.getSellPrice();
            cartIns.setOldPrice(oldPrice == null ? new BigDecimal(0) : new BigDecimal(oldPrice).divide(new BigDecimal(
                    100)));
            cartIns.setSellPrice(salePrice == null ? new BigDecimal(0) : new BigDecimal(salePrice)
                    .divide(new BigDecimal(100)));
        }
        cartIns.setBuyNum(shopCart.getNum());
        cartIns.setShowLogo(goods == null ? shopCart.getShowLogo() : goods.getImgCart());
        return cartIns;
    }
}
