package com.mocentre.tehui.goods.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.IGoodsParamDao;
import com.mocentre.tehui.goods.model.GoodsParam;

/**
 * 商品参数dao实现 Created by 王雪莹 on 2016/11/24.
 */
@Repository
public class GoodsParamDao extends BaseDao<GoodsParam> implements IGoodsParamDao {

    /**
     * 根据商品id查询所有
     * 
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsParam> getByGoodsId(Long goodsId) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", goodsId);
        return super.getList("GoodsParam_list", map);
    }

    /**
     * 根据商品id删除
     * 
     * @param goodsId
     * @return
     */
    @Override
    public int delByGoodsId(Long goodsId) {
        int i = super.delete("GoodsParam_del_goodsId", goodsId);
        return i;
    }

    /**
     * 根据商品idList删除
     * 
     * @param goodsIds
     * @return
     */
    @Override
    public int delByGoodsId(List<Long> goodsIds) {
        int i = super.delete("GoodsParam_del_goodsIds", goodsIds);
        return i;
    }

    /**
     * 批量添加
     * 
     * @param goodsParams
     * @return
     */
    @Override
    public int saveEntity(List<GoodsParam> goodsParams) {
        return super.insert("GoodsParam_insert_list", goodsParams);
    }
}
