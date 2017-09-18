package com.mocentre.tehui.goods.service;

import com.mocentre.tehui.goods.model.GoodsParam;

import java.util.List;

/**
 * 商品参数管理service接口
 * Created by 王雪莹 on 2016/11/24.
 */
public interface IGoodsParamService {

    /**
     * 根据商品id查询所有
     * @param goodsId
     * @return
     */
    List<GoodsParam> getByGoodsId(Long goodsId);

    /**
     * 根据商品id删除
     * @param goodsId
     * @return
     */
    int delByGoodsId(Long goodsId);

    /**
     * 根据商品idList删除
     * @param goodsIds
     * @return
     */
    int delByGoodsId(List<Long> goodsIds);

    /**
     * 批量添加
     * @param goodsParams
     * @return
     */
    int saveEntity(List<GoodsParam> goodsParams);
}
