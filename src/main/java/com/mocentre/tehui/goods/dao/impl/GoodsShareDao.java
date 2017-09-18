package com.mocentre.tehui.goods.dao.impl;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.IGoodsShareDao;
import com.mocentre.tehui.goods.model.GoodsShare;

/**
 * 商品分享表 Created by 王雪莹 on 2016/11/16.
 */
@Repository
public class GoodsShareDao extends BaseDao<GoodsShare> implements IGoodsShareDao {

    @Override
    public GoodsShare getByGoodsId(Long goodsId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("goodsId", goodsId);
        GoodsShare share = super.queryUniquely(paramMap);
        return share;
    }

    @Override
    public int delByGoodsId(Long goodsId) {
        return super.delete("GoodsShare_del_goodsId", goodsId);
    }

    @Override
    public int delById(Serializable id) {
        return super.delete("GoodsShare_del_id", id);
    }

}
