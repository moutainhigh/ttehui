package com.mocentre.gift.gd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.backend.param.GiftGoodsParam;
import com.mocentre.gift.gd.dao.IGiftGoodsDao;
import com.mocentre.gift.gd.mapper.GiftGoodsMapper;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.gift.gd.service.IGiftGoodsService;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 礼品平台礼品接口实现类. Created by yukaiji on 2017/4/6.
 */

@Component
public class GiftGoodsService implements IGiftGoodsService {

    @Autowired
    private IGiftGoodsDao giftGoodsDao;

    @Override
    public ListJsonResult<GiftGoods> queryGiftGoodsPage(Requirement require) {
        return giftGoodsDao.queryDatatablesPage(require);
    }

    @Override
    public GiftGoods getGiftGoodsById(Long id) {
        return giftGoodsDao.get(id);
    }

    @Override
    public List<GiftGoods> getGiftGoodsByParam(Map<String, Object> param) {
        return giftGoodsDao.getGiftGoodsByParam(param);
    }

    @Override
    public List<GiftGoods> getGiftGoodsListByCategoryId(Map<String, Object> param) {
        return giftGoodsDao.getGiftGoodsListByCategoryId(param);
    }

    @Override
    public GiftGoods addGiftGoods(GiftGoodsParam giftGoodsParam) {
        GiftGoods giftGoods = GiftGoodsMapper.toGiftGoods(giftGoodsParam);
        giftGoodsDao.saveEntity(giftGoods);
        return giftGoods;
    }

    @Override
    public Long updateGiftGoods(GiftGoodsParam giftGoodsParam) {
        GiftGoods giftGoods = GiftGoodsMapper.toGiftGoods(giftGoodsParam);
        return giftGoodsDao.updateEntity(giftGoods);
    }

    @Override
    public int removeById(Long id) {
        return giftGoodsDao.logicRemoveById(id);
    }

    @Override
    public List<GiftGoods> selectGiftList() {
        Map<String, Object> map = new HashMap<>();
        map.put("isShow","onshelf");
        return giftGoodsDao.getGiftGoodsListByCategoryId(map);
    }
}
