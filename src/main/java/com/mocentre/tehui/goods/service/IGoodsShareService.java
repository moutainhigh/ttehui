package com.mocentre.tehui.goods.service;

import java.util.List;
import java.util.Map;

import com.mocentre.tehui.goods.model.GoodsShare;

/**
 * 商品分享service接口 Created by 王雪莹 on 2016/11/5.
 */
public interface IGoodsShareService {

    /**
     * 添加一个商品分享
     * 
     * @param goodsShare
     * @return
     */
    Long addGoodShare(GoodsShare goodsShare);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int delById(Long id);

    /**
     * 更新商品分享信息
     * 
     * @param goodsShare
     * @return
     */
    Long updateGoodsShare(GoodsShare goodsShare);

    /**
     * 根据id获取详细信息
     * 
     * @param id
     * @return
     */
    GoodsShare getGoodsShareByid(Long id);

    /**
     * 根据商品id获取详细信息
     * 
     * @param goodsId
     * @return
     */
    GoodsShare getGoodsShareByGoodsid(Long goodsId);

    /**
     * 根据条件查询
     * 
     * @param paramMap
     * @return
     */
    List<GoodsShare> queryList(Map<String, Object> paramMap);

    Long updateGoodsShare(Long goodsId, GoodsShare goodsShare);

}
