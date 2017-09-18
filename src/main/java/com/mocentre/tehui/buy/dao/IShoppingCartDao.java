/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import java.util.List;

import com.mocentre.tehui.buy.model.ShoppingCart;

/**
 * 类IShoppingCartDao.java的实现描述：购物车dao
 * 
 * @author sz.gong 2016年11月23日 下午3:14:24
 */
public interface IShoppingCartDao {

    Long saveEntity(ShoppingCart shopCart);

    Long updateEntity(ShoppingCart shopCart);

    int deleteEntity(Long id, Long customerId);

    /**
     * 通过用户id,查询购物车种类条数
     * 
     * @param customerId
     * @return
     */
    Integer getCountByCustomer(Long customerId);

    /**
     * 查询数量
     * 
     * @param customerId
     * @param goodsId
     * @param actGoodsId
     * @return
     */
    Integer getSum(Long customerId, Long goodsId, Long actGoodsId);

    /**
     * 根据用户id查询购物车列表
     * 
     * @param customerId
     * @return
     */
    List<ShoppingCart> queryByCustomer(Long customerId);

    /**
     * 根据条件查询购物车
     * 
     * @param customerId 用户id
     * @param goodsId 商品id
     * @param goodsSku 商品规格编码
     * @param actGoodsId 活动商品关联id
     * @return
     */
    ShoppingCart queryByCustomerGoods(Long customerId, Long goodsId, String goodsSku, Long actGoodsId);

    /**
     * 根据条件删除
     * 
     * @param customerId 用户id
     * @param goodsId 商品id
     * @param goodsSku 商品规格
     * @param actGoodsId 活动商品关联id
     * @return
     */
    int deleteByCustomerGoods(Long customerId, Long goodsId, String goodsSku, Long actGoodsId);

    /**
     * 根据用户id删除购物车
     * 
     * @param customerId
     * @return
     */
    int deleteByCustomer(Long customerId);

    /**
     * 根据id和用户id查询购物车
     * 
     * @param id
     * @param customerId
     * @return
     */
    ShoppingCart queryByIdCustomer(Long id, Long customerId);

}
