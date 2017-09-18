package com.mocentre.tehui.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.act.dao.IActivityGoodsDao;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.goods.dao.IGoodsStorageDao;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.goods.service.IGoodsStorageService;

/**
 * 商品库存serivice实现 Created by 王雪莹 on 2016/11/5.
 */
@Component
public class GoodsStorageService implements IGoodsStorageService {

    @Autowired
    private IGoodsStorageDao  goodsStorageDao;
    @Autowired
    private IActivityGoodsDao activityGoodsDao;

    @Override
    @DataSource(value = "read")
    public List<GoodsStorage> queryGoodsStorageByGoosid(Long goodsId) {
        return goodsStorageDao.queryListByGoodsId(goodsId);
    }

    @Override
    @DataSource(value = "read")
    public List<GoodsStorage> queryGoodsStorageByGoosid(Long goodsId, Long subGoodsId) {
        return goodsStorageDao.queryByGoods(goodsId, subGoodsId);
    }

    @Override
    @DataSource(value = "write")
    public int delGoodsStorageByGoosid(Long goodsId) {
        return goodsStorageDao.delByGoodsId(goodsId);
    }

    @Override
    @DataSource(value = "write")
    public int delGoodsStorageByGoosid(Long goodsId, Long subGoodsId) {
        return goodsStorageDao.delByGoodsId(goodsId, subGoodsId);
    }

    @Override
    @DataSource(value = "write")
    public void delGoodsStorageByGoosids(List<Long> goodsIds) {
        for (Long goodsId : goodsIds) {
            goodsStorageDao.delByGoodsId(goodsId);
        }
    }

    @Override
    @DataSource(value = "read")
    public GoodsStorage queryGoodsStorageByStandard(Long goodsId, String standardCode, Long subGoodsId) {
        return goodsStorageDao.queryByStandard(goodsId, standardCode, subGoodsId);
    }

    @Override
    @DataSource(value = "read")
    public GoodsStorage queryGoodsStorageByStandard(Long goodsId, String standardCode) {
        return queryGoodsStorageByStandard(goodsId, standardCode, 0L);
    }

    @Override
    @DataSource(value = "read")
    public Long queryTotalStocknum(Long goodsId, Long actGoodsId) {
        return goodsStorageDao.queryTotalStocknumByGoosid(goodsId, actGoodsId);
    }

    @Override
    @DataSource(value = "write")
    public int updateGoodsStorageNeednum(Long goodsId, String standardCode, Long subGoodsId, Long needNum) {
        return goodsStorageDao.updateNeedStockNum(goodsId, standardCode, subGoodsId, needNum);
    }

    //////////////////////////////////缓存/////////////////////////////////
    @Override
    public GoodsStorage queryGoodsStorageFromCache(Long goodsId, String standardCode, Long subGoodsId) {
        GoodsStorage goodsStorage = goodsStorageDao.queryFromCache(goodsId, standardCode, subGoodsId);
        return goodsStorage;
    }

    @Override
    public Long queryGoodsStorageStockNumFromCache(Long goodsId, String standardCode, Long subGoodsId) {
        return goodsStorageDao.queryStockNumFromCache(goodsId, standardCode, subGoodsId);
    }

}
