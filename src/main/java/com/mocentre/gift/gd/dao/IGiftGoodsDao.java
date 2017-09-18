package com.mocentre.gift.gd.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 礼品平台礼品数据库操作接口 Created by yukaiji on 2017/4/6.
 */
public interface IGiftGoodsDao {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    GiftGoods get(Serializable id);

    /**
     * 获取所有的商品(分页查询)
     *
     * @return 所有的商品
     */
    ListJsonResult<GiftGoods> queryDatatablesPage(Requirement require);

    /**
     * 根据条件获取商品
     *
     * @param paramMap
     * @return
     */
    List<GiftGoods> getGiftGoodsByParam(Map<String, Object> paramMap);

    /**
     * 根据分类id列表分页获取商品列表
     *
     * @param paramMap
     * @return
     */
    List<GiftGoods> getGiftGoodsListByCategoryId(Map<String, Object> paramMap);

    /**
     * 插入商品
     *
     * @param giftGoods
     */
    Long saveEntity(GiftGoods giftGoods);

    /**
     * 修改商品
     *
     * @param giftGoods
     */
    Long updateEntity(GiftGoods giftGoods);

    /**
     * 根据id删除
     *
     * @param id
     */
    int logicRemoveById(Serializable id);
}
