package com.mocentre.tehui.goods.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.ICategoryGoodsDao;
import com.mocentre.tehui.goods.model.CategoryGoods;

/**
 * 分类商品dao Created by yukaiji on 2017/7/4.
 */
@Repository
public class CategoryGoodsDao extends BaseDao<CategoryGoods> implements ICategoryGoodsDao {

    @Override
    public int delById(Long id) {
        return super.delete("CategoryGoods_del_id", id);
    }

    @Override
    public int delById(List<Long> ids) {
        return super.delete("CategoryGoods_del_ids", ids);
    }

}
