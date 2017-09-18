/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import java.util.List;

import com.mocentre.tehui.buy.model.ShoppingCart;

/**
 * 类IShoppingCartService.java的实现描述：购物车Service
 * 
 * @author sz.gong 2016年11月23日 下午3:16:24
 */
public interface IShoppingCartService {

    /**
     * 添加到购物车
     * 
     * @param shopCart
     */
    void saveShopCart(ShoppingCart shopCart);

    /**
     * 根据用户id查询购物车商品数量
     *
     * @param customerId 用户id
     * @return
     */
    Integer getShopCartCount(Long customerId);

    /**
     * 查询商品数量
     * 
     * @param customerId
     * @param goodsId
     * @param actGoodsId
     * @return
     */
    Integer getGoodsSum(Long customerId, Long goodsId, Long actGoodsId);

    /**
     * 查询用户购物车列表
     * 
     * @param customerId 用户id
     * @return
     */
    List<ShoppingCart> queryShopCartByCustomer(Long customerId);

    /**
     * 通过id和用户id查询购物车
     * 
     * @param id
     * @param customerId
     * @return
     */
    ShoppingCart getShopCartByIdCustomer(Long id, Long customerId);

    /**
     * 根据条件查询
     * 
     * @param customerId 用户id
     * @param goodsId 商品id
     * @param actGoodsId 活动商品关联id
     * @param goodsSku 商品sku
     * @return
     */
    ShoppingCart getShopCartUnique(Long customerId, Long goodsId, Long actGoodsId, String goodsSku);

    /**
     * 更新指定购物车商品数量
     * 
     * @param id 购物车id
     * @param num 更新数量
     * @return
     */
    Long updateShopCartNum(Long id, Integer num);

    /**
     * 删除购物车商品
     * 
     * @param customerId 用户id
     * @param id 购物车id
     * @return
     */
    int deleteShopCart(Long customerId, Long id);

    /**
     * 同步购物车
     * 
     * @param customerId
     * @param shopCartList
     * @return
     */
    int saveShopCart(Long customerId, List<ShoppingCart> shopCartList);

}
