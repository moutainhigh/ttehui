package com.mocentre.tehui.goods.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.CategoryGoodsParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.CategoryGoods;

import java.util.List;

/**
 * 分类商品service Created by yukaiji on 2017/7/4.
 */
public interface ICategoryGoodsService {

    /**
     * 获取所有的分类商品(分页查询)
     *
     * @return 所有的商品
     */
    ListJsonResult<CategoryGoods> queryCategoryGoodsPage(Requirement require);

    /**
     * 根据id获取
     *
     * @param id
     */
    CategoryGoods getCategoryGoodsById(Long id);

    /**
     * 根据分类Id获取
     *
     * @param categoryId
     */
    List<CategoryGoods> getCategoryGoodsListByCategoryId(Long categoryId);

    /**
     * 新增
     *
     * @param categoryGoodsParam
     * @return
     */
    Long addCategoryGoods(CategoryGoodsParam categoryGoodsParam);

    /**
     * 修改
     *
     * @param categoryGoodsParam
     * @return
     */
    Long updateCategoryGoods(CategoryGoodsParam categoryGoodsParam);

    /**
     * 根据id删除活动商品
     *
     * @param id
     * @return
     */
    int deleteCategoryGoodsById(Long id);
}
