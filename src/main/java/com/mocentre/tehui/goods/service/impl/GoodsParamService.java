package com.mocentre.tehui.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.goods.dao.IGoodsParamDao;
import com.mocentre.tehui.goods.model.GoodsParam;
import com.mocentre.tehui.goods.service.IGoodsParamService;

/**
 * 商品参数管理service实现 Created by 王雪莹 on 2016/11/24.
 */
@Component
public class GoodsParamService implements IGoodsParamService {

    @Autowired
    private IGoodsParamDao goodsParamDao;

    /**
     * 根据商品id查询所有
     * 
     * @param goodsId
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<GoodsParam> getByGoodsId(Long goodsId) {
        return goodsParamDao.getByGoodsId(goodsId);
    }

    /**
     * 根据商品id删除
     * 
     * @param goodsId
     * @return
     */
    @Override
    @DataSource(value = "write")
    public int delByGoodsId(Long goodsId) {
        return goodsParamDao.delByGoodsId(goodsId);
    }

    /**
     * 根据商品idList删除
     * 
     * @param goodsIds
     * @return
     */
    @Override
    @DataSource(value = "write")
    public int delByGoodsId(List<Long> goodsIds) {
        return goodsParamDao.delByGoodsId(goodsIds);
    }

    /**
     * 批量添加
     * 
     * @param goodsParams
     * @return
     */
    @Override
    @DataSource(value = "write")
    public int saveEntity(List<GoodsParam> goodsParams) {
        return goodsParamDao.saveEntity(goodsParams);
    }

}
