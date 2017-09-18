/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mocentre.tehui.buy.dao.IShoppingCartDao;
import com.mocentre.tehui.buy.model.ShoppingCart;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 类ShoppingCartDao.java的实现描述：购物车dao实现
 * 
 * @author sz.gong 2016年11月23日 下午3:15:11
 */
@Repository
public class ShoppingCartDao extends BaseDao<ShoppingCart> implements IShoppingCartDao {

    @Override
    public List<ShoppingCart> queryByCustomer(Long customerId) {
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        return super.queryList(paramMap);
    }

    @Override
    public Integer getCountByCustomer(Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        return super.getCount(paramMap);
    }

    @Override
    public Integer getSum(Long customerId, Long goodsId, Long actGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("goodsId", goodsId);
        paramMap.put("actGoodsId", actGoodsId);
        return super.queryUniquely("ShoppingCart_sum", paramMap);
    }

    @Override
    public ShoppingCart queryByCustomerGoods(Long customerId, Long goodsId, String goodsSku, Long actGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("goodsId", goodsId);
        paramMap.put("goodsSku", goodsSku);
        paramMap.put("actGoodsId", actGoodsId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public ShoppingCart queryByIdCustomer(Long id, Long customerId) {
        Assert.notNull(customerId);
        Assert.notNull(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("id", id);
        return super.queryUniquely(paramMap);
    }

    @Override
    public int deleteByCustomerGoods(Long customerId, Long goodsId, String goodsSku, Long actGoodsId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setCustomerId(customerId);
        cart.setGoodsId(goodsId);
        cart.setGoodsSku(goodsSku);
        cart.setActGoodsId(actGoodsId);
        int count = super.delete("ShoppingCart_delete_goods", cart);
        return count;
    }

    @Override
    public int deleteByCustomer(Long customerId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setCustomerId(customerId);
        int count = super.delete("ShoppingCart_delete_cum", cart);
        return count;
    }

    @Override
    public int deleteEntity(Long id, Long customerId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(id);
        cart.setCustomerId(customerId);
        return super.remove(cart);
    }

}
