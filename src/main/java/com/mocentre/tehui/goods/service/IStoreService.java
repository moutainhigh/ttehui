package com.mocentre.tehui.goods.service;

import java.util.List;

import com.mocentre.tehui.goods.model.Store;

/**
 * 商品收藏接口 Created by yukaiji on 2016/12/07.
 */
public interface IStoreService {

    /**
     * 根据id和用户id获取收藏信息
     * 
     * @return
     */
    Store getStore(Long id, Long customerId);

    /**
     * 根据用户id获取全部收藏信息
     * 
     * @param customerId
     * @return
     */
    List<Store> getStoreList(Long customerId);

    /**
     * 添加一个收藏
     * 
     * @param store
     * @return
     */
    Store addStore(Store store);

    /**
     * 通过商品id和用户id查询
     * 
     * @param goodsId
     * @param customerId
     * @return
     */
    Store getStoreByGoods(Long goodsId, Long customerId);

    /**
     * 通过商品id和用户id查询
     * 
     * @param goodsId
     * @param customerId
     * @return
     */
    int countStoreByGoods(Long goodsId, Long customerId);

    /**
     * 根据店铺id和商品id删除收藏
     *
     * @param store 商品id和店铺id
     * @return
     */
    int deleteStore(Store store);
}
