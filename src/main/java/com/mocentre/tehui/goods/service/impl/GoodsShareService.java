package com.mocentre.tehui.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.goods.dao.IGoodsShareDao;
import com.mocentre.tehui.goods.model.GoodsShare;
import com.mocentre.tehui.goods.service.IGoodsShareService;

/**
 * 商品分享serivice实现 Created by 王雪莹 on 2016/11/16.
 */
@Component
public class GoodsShareService implements IGoodsShareService {

    @Autowired
    private IGoodsShareDao goodsShareDao;

    /**
     * 添加一个商品分享
     * 
     * @param goodsShare
     * @return
     */
    @Override
    @DataSource(value = "write")
    public Long addGoodShare(GoodsShare goodsShare) {
        return goodsShareDao.saveEntity(goodsShare);
    }

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    @Override
    @DataSource(value = "write")
    public int delById(Long id) {
        return goodsShareDao.delById(id);
    }

    /**
     * 更新商品分享信息
     * 
     * @param goodsShare
     * @return
     */
    @Override
    @DataSource(value = "write")
    public Long updateGoodsShare(GoodsShare goodsShare) {
        return goodsShareDao.updateEntity(goodsShare);
    }

    /**
     * 根据id获取详细信息
     * 
     * @param id
     * @return
     */
    @Override
    @DataSource(value = "read")
    public GoodsShare getGoodsShareByid(Long id) {
        return goodsShareDao.get(id);
    }

    /**
     * 根据商品id获取详细信息
     * 
     * @param goodsId
     * @return
     */
    @Override
    @DataSource(value = "read")
    public GoodsShare getGoodsShareByGoodsid(Long goodsId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("goodsId", goodsId);
        List<GoodsShare> goodsShareList = goodsShareDao.queryList(paramMap);
        if (goodsShareList != null && goodsShareList.size() > 0) {
            return goodsShareList.get(0);
        }
        return null;
    }

    /**
     * 根据条件查询
     * 
     * @param paramMap
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<GoodsShare> queryList(Map<String, Object> paramMap) {
        return goodsShareDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Long updateGoodsShare(Long goodsId, GoodsShare goodsShare) {
        goodsShare.setGoodsId(goodsId);
        Long count = 0l;
        GoodsShare oldShare = goodsShareDao.getByGoodsId(goodsId);
        if (oldShare != null) {
            goodsShare.setId(oldShare.getId());
            count = goodsShareDao.updateEntity(goodsShare);
        } else {
            count = goodsShareDao.saveEntity(goodsShare);
        }
        return count;
    }

}
