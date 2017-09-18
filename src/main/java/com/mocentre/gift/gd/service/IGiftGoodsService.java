package com.mocentre.gift.gd.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.backend.param.GiftGoodsParam;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.util.List;
import java.util.Map;

/**
 * 礼品平台礼品操作service.
 *
 * Created by yukaiji on 2017/4/9.
 */
public interface IGiftGoodsService {


    /**
     * 获取所有的商品(分页查询)
     *
     * @param require
     */
    ListJsonResult<GiftGoods> queryGiftGoodsPage(Requirement require);

    /**
     * 根据商品id获取
     *
     * @param id
     */
    GiftGoods getGiftGoodsById(Long id);

    /**
     * 根据条件获取
     *
     * @param param
     */
    List<GiftGoods> getGiftGoodsByParam(Map<String, Object> param);

    /**
     * 根据分类id列表查询商品列表
     *
     * @param param
     */
    List<GiftGoods> getGiftGoodsListByCategoryId(Map<String, Object> param);

    /**
     * 添加一个商品
     *
     * @param giftGoodsParam
     */
    GiftGoods addGiftGoods(GiftGoodsParam giftGoodsParam);

    /**
     * 修改一个商品
     *
     * @param giftGoodsParam
     * @return id
     */
    Long updateGiftGoods(GiftGoodsParam giftGoodsParam);

    /**
     * 根据id删除一个商品
     *
     * @param id
     * @return
     */
    int removeById(Long id);

    /**
     * 查询全部上架商品
     * @return
     */
    List<GiftGoods> selectGiftList();
}
