package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.List;

import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * 商品库存DAO接口 Created by 22938 on 2016/11/5.
 */
public interface IGoodsStorageDao {

    void saveEntity(List<GoodsStorage> goodsStorageList);

    Long saveEntity(GoodsStorage goodsStorage);

    int delByGoodsId(Long goodsId);

    /**
     * 删除商品库存
     * 
     * @param goodsId
     * @param subGoodsId
     * @return
     */
    int delByGoodsId(Long goodsId, Long subGoodsId);

    GoodsStorage get(Serializable id);

    List<GoodsStorage> queryListByGoodsId(Long goodsId);

    /**
     * 直接更新库存
     * 
     * @param goodsId 商品id
     * @param standardCode 商品规格编码
     * @param subGoodsId 活动商品关联id
     * @param num 库存数量
     * @return
     */
    int updateStockNum(Long goodsId, String standardCode, Long subGoodsId, Long num);

    /**
     * 更新库存，在库存基础上增减数量
     * 
     * @param goodsId 商品id
     * @param standardCode 商品规格编码
     * @param subGoodsId 活动商品关联id
     * @param needNum 库存增减量
     * @return
     */
    int updateNeedStockNum(Long goodsId, String standardCode, Long subGoodsId, Long needNum);

    /**
     * 查询库存
     * 
     * @param goodsId 商品id
     * @param subGoodsId 活动商品关联id
     * @return
     */
    List<GoodsStorage> queryByGoods(Long goodsId, Long subGoodsId);

    /**
     * 获取商品库存总量
     * 
     * @param goodsId
     * @param actGoodsId
     * @return
     */
    Long queryTotalStocknumByGoosid(Long goodsId, Long actGoodsId);

    /**
     * 根据商品id和库存规格查询库存对象
     * 
     * @param goodsId
     * @param standardCode
     * @return
     */
    GoodsStorage queryByStandard(Long goodsId, String standardCode, Long subGoodsId);

    /**
     * 缓存中查询库存信息（库存基本信息和库存数量）
     * 
     * @param goodsId 商品id
     * @param standardCode 商品规格
     * @param subGoodsId 活动商品id
     * @return
     */
    GoodsStorage queryFromCache(Long goodsId, String standardCode, Long subGoodsId);

    /**
     * 更新库存基础数据到缓存
     * 
     * @param goodsStorage 库存对象
     */
    void updateToCache(GoodsStorage goodsStorage);

    /**
     * 更新缓存库存数量和数据表库存数量
     * 
     * @param goodsId 商品id
     * @param goodsSku 商品sku
     * @param actGoodsId 活动商品关联id
     * @param nums 库存增减量
     */
    void updateStockNumAndCache(Long goodsId, String goodsSku, Long actGoodsId, Long nums);

    /**
     * 从缓存中删除
     * 
     * @param goodsId 商品id
     * @param subGoodsId 活动商品关联id
     */
    void deleteFromCache(Long goodsId, Long subGoodsId);

    /**
     * 从缓存中删除
     * 
     * @param goodsStorageList
     */
    void deleteFromCache(List<GoodsStorage> goodsStorageList);

    /**
     * 缓存中查询库存数量
     * 
     * @param goodsId
     * @param standardCode
     * @param subGoodsId
     * @return
     */
    Long queryStockNumFromCache(Long goodsId, String standardCode, Long subGoodsId);

    /**
     * 缓存库存数量
     * 
     * @param goodsId
     * @param goodsSku
     * @param actGoodsId
     * @param nums
     */
    void updateStockNumToCache(Long goodsId, String goodsSku, Long actGoodsId, Long nums);

    /**
     * 批量更新库存基本信息和库存数量到缓存
     * 
     * @param goodsStorageList
     */
    void updateBatchToCache(List<GoodsStorage> goodsStorageList);

}
