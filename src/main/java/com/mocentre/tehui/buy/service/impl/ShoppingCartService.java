/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.buy.dao.IShoppingCartDao;
import com.mocentre.tehui.buy.model.ShoppingCart;
import com.mocentre.tehui.buy.service.IShoppingCartService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.goods.dao.IGoodsDao;
import com.mocentre.tehui.goods.dao.IGoodsStorageDao;
import com.mocentre.tehui.shop.dao.IShopDao;

/**
 * 类ShoppingCartService.java的实现描述：购物车Service实现
 * 
 * @author sz.gong 2016年11月23日 下午3:16:49
 */
@Component
public class ShoppingCartService implements IShoppingCartService {

    @Autowired
    private IShoppingCartDao shopCartDao;
    @Autowired
    private IShopDao         shopDao;
    @Autowired
    private IGoodsDao        goodsDao;
    @Autowired
    private IGoodsStorageDao goodsStorageDao;

    @Override
    @DataSource(value = "write")
    public void saveShopCart(ShoppingCart shopCart) {
        shopCartDao.saveEntity(shopCart);
    }

    @Override
    @DataSource(value = "read")
    public Integer getShopCartCount(Long customerId) {
        return shopCartDao.getCountByCustomer(customerId);
    }

    @Override
    @DataSource(value = "read")
    public List<ShoppingCart> queryShopCartByCustomer(Long customerId) {
        List<ShoppingCart> list = shopCartDao.queryByCustomer(customerId);
        return list;
    }

    @Override
    @DataSource(value = "read")
    public ShoppingCart getShopCartByIdCustomer(Long id, Long customerId) {
        return shopCartDao.queryByIdCustomer(id, customerId);
    }

    @Override
    @DataSource(value = "read")
    public ShoppingCart getShopCartUnique(Long customerId, Long goodsId, Long actGoodsId, String goodsSku) {
        return shopCartDao.queryByCustomerGoods(customerId, goodsId, goodsSku, actGoodsId);
    }

    @Override
    @DataSource(value = "read")
    public Integer getGoodsSum(Long customerId, Long goodsId, Long actGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("goodsId", goodsId);
        paramMap.put("actGoodsId", actGoodsId);
        return shopCartDao.getSum(customerId, goodsId, actGoodsId);
    }

    @Override
    @DataSource(value = "write")
    public Long updateShopCartNum(Long id, Integer num) {
        ShoppingCart sc = new ShoppingCart();
        sc.setId(id);
        sc.setNum(num);
        Long count = shopCartDao.updateEntity(sc);
        return count;
    }

    @Override
    @DataSource(value = "write")
    public int deleteShopCart(Long customerId, Long id) {
        int count = shopCartDao.deleteEntity(id, customerId);
        return count;
    }

    @Override
    @DataSource(value = "write")
    public int saveShopCart(Long customerId, List<ShoppingCart> shopCartList) {
        int count = shopCartDao.deleteByCustomer(customerId);
        for (ShoppingCart shopCart : shopCartList) {
            this.saveShopCart(shopCart);
        }
        return count;
    }

}
