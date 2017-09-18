package com.mocentre.tehui.goods.service;

import java.util.List;

import com.mocentre.tehui.goods.model.GoodsLaunch;

/**
 * 商品投放service接口 Created by 王雪莹 on 2016/11/5.
 */
public interface IGoodsLaunchService {

    /**
     * 根据商品id和投放区域list批量添加
     * 
     * @param goodsId
     * @param areaList
     */
    void addGoodsLaunch(Long goodsId, List<String> areaList);

    /**
     * 根据商品id删除关联表
     * 
     * @param goodsId
     */
    void delGoodsLaunchByGoods(Long goodsId);

    /**
     * 根据商品id获取一条数据
     * 
     * @param goodsId
     */
    List<GoodsLaunch> queryByGoodsId(Long goodsId);
}
