package com.mocentre.tehui.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.CategoryGoodsParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.ICategoryGoodsDao;
import com.mocentre.tehui.goods.mapper.CategoryGoodsMapper;
import com.mocentre.tehui.goods.model.CategoryGoods;
import com.mocentre.tehui.goods.service.ICategoryGoodsService;

/**
 * 分类商品service Created by yukaiji on 2017/7/4.
 */
@Component
public class CategoryGoodsService implements ICategoryGoodsService {

    @Autowired
    private ICategoryGoodsDao categoryGoodsDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<CategoryGoods> queryCategoryGoodsPage(Requirement require) {
        return categoryGoodsDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public CategoryGoods getCategoryGoodsById(Long id) {
        return categoryGoodsDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<CategoryGoods> getCategoryGoodsListByCategoryId(Long categoryId) {
        Map<String, Object> param = new HashMap<>();
        param.put("categoryId", categoryId);
        return categoryGoodsDao.queryList(param);
    }

    @Override
    @DataSource(value = "write")
    public Long addCategoryGoods(CategoryGoodsParam categoryGoodsParam) {
        CategoryGoods categoryGoods = CategoryGoodsMapper.toCategoryGoods(categoryGoodsParam);
        categoryGoodsDao.saveEntity(categoryGoods);
        return categoryGoods.getId();
    }

    @Override
    @DataSource(value = "write")
    public Long updateCategoryGoods(CategoryGoodsParam categoryGoodsParam) {
        CategoryGoods categoryGoods = CategoryGoodsMapper.toCategoryGoods(categoryGoodsParam);
        return categoryGoodsDao.updateEntity(categoryGoods);
    }

    @Override
    @DataSource(value = "write")
    public int deleteCategoryGoodsById(Long id) {
        return categoryGoodsDao.logicRemoveById(id);
    }

}
