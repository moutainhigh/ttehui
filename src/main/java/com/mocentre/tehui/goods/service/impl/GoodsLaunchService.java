package com.mocentre.tehui.goods.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.goods.dao.IGoodsLaunchDao;
import com.mocentre.tehui.goods.model.GoodsLaunch;
import com.mocentre.tehui.goods.service.IGoodsLaunchService;

/**
 * 商品投放 Created by 王雪莹 on 2016/11/17.
 */
@Component
public class GoodsLaunchService implements IGoodsLaunchService {
    @Autowired
    private IGoodsLaunchDao goodsLaunchDao;

    /**
     * 根据商品id和投放区域list批量添加
     * 
     * @param goodsId
     * @param areaList
     */
    @Override
    @DataSource(value = "write")
    public void addGoodsLaunch(Long goodsId, List<String> areaList) {
        List<GoodsLaunch> GoodsLaunchList = new ArrayList<>();
        for (int i = 0, length = areaList.size(); i < length; i++) {
            GoodsLaunch GoodsLaunch = new GoodsLaunch();
            GoodsLaunch.setGoodsId(goodsId);
            GoodsLaunch.setArea(areaList.get(i));
            GoodsLaunchList.add(GoodsLaunch);
        }
        goodsLaunchDao.saveEntity(GoodsLaunchList);
    }

    /**
     * 根据商品id删除关联表
     * 
     * @param goodsId
     */
    @Override
    @DataSource(value = "write")
    public void delGoodsLaunchByGoods(Long goodsId) {
        goodsLaunchDao.delByGoodsId(goodsId);
    }

    /**
     * 根据商品id获取投放信息
     * 
     * @param goodsId
     */
    @Override
    @DataSource(value = "read")
    public List<GoodsLaunch> queryByGoodsId(Long goodsId) {
        return goodsLaunchDao.getListByGoods(goodsId);
    }

}
