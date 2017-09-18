/*
 *
 *  * Copyright 2016 mocentre.com All right reserved. This software is the
 *  * confidential and proprietary information of mocentre.com ("Confidential
 *  * Information"). You shall not disclose such Confidential Information and shall
 *  * use it only in accordance with the terms of the license agreement you entered
 *  * into with mocentre.com .
 *
 */

package com.mocentre.tehui.goods.mapper;

import com.mocentre.tehui.backend.model.CategoryGoodsInstance;
import com.mocentre.tehui.backend.param.CategoryGoodsParam;
import com.mocentre.tehui.frontend.model.CategoryGoodsFTInstance;
import com.mocentre.tehui.goods.model.CategoryGoods;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类商品参数转换 Created by yukaiji on 2017/7/4.
 */
public class CategoryGoodsMapper {

    public static CategoryGoods toCategoryGoods(CategoryGoodsParam param) {
        CategoryGoods categoryGoods = new CategoryGoods();
        categoryGoods.setLinkUrl(param.getLinkUrl());
        categoryGoods.setCategoryId(param.getCategoryId());
        categoryGoods.setGoodsId(param.getGoodsId());
        categoryGoods.setIsChain(param.getIsChain());
        categoryGoods.setShowImg(param.getShowImg());
        categoryGoods.setShowName(param.getShowName());
        categoryGoods.setSorting(param.getSorting());
        categoryGoods.setId(param.getId());
        return categoryGoods;
    }

    public static CategoryGoodsInstance toCategoryGoodsIns(CategoryGoods categoryGoods) {
        CategoryGoodsInstance categoryGoodsIns = new CategoryGoodsInstance();
        categoryGoodsIns.setLinkUrl(categoryGoods.getLinkUrl());
        categoryGoodsIns.setCategoryId(categoryGoods.getCategoryId());
        categoryGoodsIns.setGoodsId(categoryGoods.getGoodsId());
        categoryGoodsIns.setIsChain(categoryGoods.getIsChain());
        categoryGoodsIns.setShowImg(categoryGoods.getShowImg());
        categoryGoodsIns.setSorting(categoryGoods.getSorting());
        categoryGoodsIns.setShowName(categoryGoods.getShowName());
        categoryGoodsIns.setId(categoryGoods.getId());
        return categoryGoodsIns;
    }

    public static CategoryGoodsFTInstance toCategoryGoodsFTIns(CategoryGoods categoryGoods) {
        CategoryGoodsFTInstance categoryGoodsIns = new CategoryGoodsFTInstance();
        categoryGoodsIns.setLinkUrl(categoryGoods.getLinkUrl());
        categoryGoodsIns.setCategoryId(categoryGoods.getCategoryId());
        categoryGoodsIns.setGoodsId(categoryGoods.getGoodsId());
        categoryGoodsIns.setIsChain(categoryGoods.getIsChain());
        categoryGoodsIns.setShowImg(categoryGoods.getShowImg());
        categoryGoodsIns.setSorting(categoryGoods.getSorting());
        categoryGoodsIns.setShowName(categoryGoods.getShowName());
        categoryGoodsIns.setId(categoryGoods.getId());
        return categoryGoodsIns;
    }

    public static List<CategoryGoodsFTInstance> toCategoryGoodsFTInsList(List<CategoryGoods> lists) {
        List<CategoryGoodsFTInstance> listResult = new ArrayList<CategoryGoodsFTInstance>();
        if (lists.size() < 1) {
            return listResult;
        }
        for (CategoryGoods categoryGoods : lists) {
            CategoryGoodsFTInstance categoryInstance = toCategoryGoodsFTIns(categoryGoods);
            listResult.add(categoryInstance);
        }
        return listResult;
    }
}
