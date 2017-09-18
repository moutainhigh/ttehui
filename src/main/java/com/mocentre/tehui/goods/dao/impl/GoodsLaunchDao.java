package com.mocentre.tehui.goods.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.IGoodsLaunchDao;
import com.mocentre.tehui.goods.model.GoodsLaunch;

/**
 * 商品投放表 Created by王雪莹 on 2016/11/16.
 */
@Repository
public class GoodsLaunchDao extends BaseDao<GoodsLaunch> implements IGoodsLaunchDao {

    @Override
    public Long saveEntity(List<GoodsLaunch> launchList) {
        Long count = 0l;
        for (GoodsLaunch goodsLaunch : launchList) {
            count += super.saveEntity(goodsLaunch);
        }
        return count;
    }

    @Override
    public List<GoodsLaunch> getListByGoods(Long goodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        List<GoodsLaunch> GoodsLaunches = super.queryList(paramMap);
        return GoodsLaunches;
    }

    @Override
    public int delByGoodsId(Long goodsId) {
        return super.delete("GoodsLaunch_del_goodsId", goodsId);
    }

    @Override
    public int delById(Serializable id) {
        return super.removeById(id);
    }
}
