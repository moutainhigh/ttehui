package com.mocentre.tehui.goods.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.goods.dao.IGoodsDao;
import com.mocentre.tehui.goods.model.Goods;

/**
 * 商品dao接口 Created by 王雪莹 on 2016/11/10.
 */
@Repository
public class GoodsDao extends BaseRedisDao<Goods, String, Goods> implements IGoodsDao {

    @Override
    public List<Goods> queryByCategory(Long categoryId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("categoryId", categoryId);
        return super.queryList(paramMap);
    }

    @Override
    public Goods get(Long id, Long shopId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("shopId", shopId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public int logicRemove(Long id, Long shopId) {
        Goods o = new Goods();
        o.setId(id);
        o.setShopId(shopId);
        return super.logicRemove(o);
    }

    @Override
    public List<Goods> getTopN(Map<String, Object> param) {
        return super.getList("Goods_Top_List", param);
    }

    @Override
    public Goods queryFromCache(Long id) {
        String key = RedisKeyConstant.GOODS_LIST + ":" + id;
        Goods goods = getCacheValue(key);
        if (goods == null) {
            goods = this.get(id);
            if (goods != null) {
                this.updateToCache(goods);
            }
        }
        return goods;
    }

    @Override
    public boolean updateToCache(Goods goods) {
        String key = RedisKeyConstant.GOODS_LIST + ":" + goods.getId();
        boolean suc = cacheValue(key, goods);
        return suc;
    }

    @Override
    public boolean deleteFromCache(Long id) {
        String key = RedisKeyConstant.GOODS_LIST + ":" + id;
        boolean suc = removeCacheValue(key);
        return suc;
    }

}
