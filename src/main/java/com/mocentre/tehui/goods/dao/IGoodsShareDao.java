package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.goods.model.GoodsShare;

/**
 * 商品分享dao Created by 王雪莹 on 2016/11/16.
 */
@Repository
public interface IGoodsShareDao {

    GoodsShare getByGoodsId(Long goodsId);

    Long saveEntity(GoodsShare goodsShare);

    int delByGoodsId(Long goodsId);

    int delById(Serializable id);

    Long updateEntity(GoodsShare goodsShare);

    GoodsShare get(Serializable id);

    List<GoodsShare> getAll();

    List<GoodsShare> queryList(Map<String, Object> paramMap);
}
