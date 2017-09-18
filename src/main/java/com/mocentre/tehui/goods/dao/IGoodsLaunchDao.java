package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.tehui.goods.model.GoodsLaunch;

/**
 * 商品投放表 Created by 王雪莹 on 2016/11/16.
 */
public interface IGoodsLaunchDao {

    List<GoodsLaunch> getAll();

    /**
     * 按商品id，查询商品的投放信息
     * 
     * @param goodsId
     * @return
     */
    List<GoodsLaunch> getListByGoods(Long goodsId);

    GoodsLaunch get(Serializable id);

    Long updateEntity(GoodsLaunch goods);

    Long saveEntity(GoodsLaunch goods);

    Long saveEntity(List<GoodsLaunch> launchList);

    List<GoodsLaunch> queryList(Map<String, Object> paramMap);

    int delByGoodsId(Long goodsId);

    int delById(Serializable Id);
}
