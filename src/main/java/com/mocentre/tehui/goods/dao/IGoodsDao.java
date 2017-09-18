package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Goods;

/**
 * 商品dao接口 Created by 王雪莹 on 2016/11/10.
 */
public interface IGoodsDao {

    List<Goods> getAll();

    Goods get(Serializable id);

    /**
     * 按分类id查询列表
     * 
     * @param categoryId
     * @return
     */
    List<Goods> queryByCategory(Long categoryId);

    List<Goods> queryList(Map<String, Object> paramMap);

    Long updateEntity(Goods goods);

    Long saveEntity(Goods goods);

    int logicRemove(Long id, Long shopId);

    ListJsonResult<Goods> queryDatatablesPage(Requirement require);

    /**
     * 通过id和店铺id查询
     * 
     * @param id
     * @param shopId
     * @return
     */
    Goods get(Long id, Long shopId);

    /**
     * 按条件 按顺序 取前n件
     * 
     * @param param
     * @return
     */
    List<Goods> getTopN(Map<String, Object> param);

    /**
     * 通过id，从缓存中查询商品
     * 
     * @param id
     * @return
     */
    Goods queryFromCache(Long id);

    /**
     * 更新商品到缓存
     * 
     * @param goods
     * @return
     */
    boolean updateToCache(Goods goods);

    /**
     * 从缓存中移除
     * 
     * @param id
     * @return
     */
    boolean deleteFromCache(Long id);
}
