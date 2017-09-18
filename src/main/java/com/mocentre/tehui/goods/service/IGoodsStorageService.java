package com.mocentre.tehui.goods.service;

import java.util.List;

import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * 商品库存service接口 Created by 王雪莹 on 2016/11/5.
 */
public interface IGoodsStorageService {

    /**
     * 根据商品idList 删除库存表信息
     * 
     * @param ids
     */
    void delGoodsStorageByGoosids(List<Long> ids);

    /**
     * 根据商品id和商品规格编码，查询商品库存
     * 
     * @param goodsId
     * @param standardCode
     * @return
     */
    GoodsStorage queryGoodsStorageByStandard(Long goodsId, String standardCode, Long subGoodsId);

    /**
     * 根据商品id和商品规格编码，查询商品库存（默认是未参加活动的商品） ok
     * 
     * @param goodsId
     * @param standardCode
     * @return
     */
    GoodsStorage queryGoodsStorageByStandard(Long goodsId, String standardCode);

    /**
     * 查询库存（所有库存，参加活动的库存和未参加活动的库存）
     * 
     * @param goodsId
     * @return
     */
    List<GoodsStorage> queryGoodsStorageByGoosid(Long goodsId);

    /**
     * 查询库存
     * 
     * @param goodsId
     * @param subGoodsId
     * @return
     */
    List<GoodsStorage> queryGoodsStorageByGoosid(Long goodsId, Long subGoodsId);

    /**
     * 删除某商品下的所有库存（所有库存，参加活动的库存和未参加活动的库存）
     */
    int delGoodsStorageByGoosid(Long goodsId);

    /**
     * 删除某商品下的所有库存（所有库存，参加活动的库存和未参加活动的库存）
     */
    int delGoodsStorageByGoosid(Long goodsId, Long subGoodsId);

    /**
     * 获取库存总量
     * 
     * @param goodsId
     * @param actGoodsId
     * @return
     */
    Long queryTotalStocknum(Long goodsId, Long actGoodsId);

    /**
     * 更新指定商品库存，在原有库存上增减
     * 
     * @param goodsId 商品id
     * @param standardCode 商品规格编码
     * @param subGoodsId 活动商品关联id
     * @param needNum 库存增减量
     * @return
     */
    int updateGoodsStorageNeednum(Long goodsId, String standardCode, Long subGoodsId, Long needNum);

    /**
     * 缓存中查询库存
     *
     * @param goodsId 商品id
     * @param standardCode 商品规格
     * @param subGoodsId 活动商品id
     * @return
     */
    GoodsStorage queryGoodsStorageFromCache(Long goodsId, String standardCode, Long subGoodsId);

    /**
     * 缓存中查询库存数量
     * 
     * @param goodsId 商品id
     * @param standardCode 商品规格
     * @param subGoodsId 活动商品id
     * @return
     */
    Long queryGoodsStorageStockNumFromCache(Long goodsId, String standardCode, Long subGoodsId);

}
