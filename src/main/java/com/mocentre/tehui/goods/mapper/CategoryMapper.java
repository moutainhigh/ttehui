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

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mocentre.tehui.backend.model.CategoryInstance;
import com.mocentre.tehui.backend.param.CategoryParam;
import com.mocentre.tehui.frontend.model.CategoryWithGoodsFTInstance;
import com.mocentre.tehui.goods.model.Category;

/**
 * Created by Arvin on 16/12/12.
 */
public class CategoryMapper {

    public static CategoryInstance toInstance(Category category) {
        CategoryInstance categoryInstance = new CategoryInstance();
        BeanCopier copier = BeanCopier.create(Category.class, CategoryInstance.class, false);
        copier.copy(category, categoryInstance, null);
        return categoryInstance;
    }

    public static List<CategoryInstance> toInstanceList(List<Category> lists) {
        if (lists == null) {
            return null;
        }
        List<CategoryInstance> listResult = new ArrayList<CategoryInstance>();
        if (lists.isEmpty()) {
            return listResult;
        }
        for (Category category : lists) {
            CategoryInstance categoryInstance = CategoryMapper.toInstance(category);
            listResult.add(categoryInstance);
        }
        return listResult;
    }

    public static CategoryWithGoodsFTInstance toCategoryWithGoodsFTInstance(Category category) {
        CategoryWithGoodsFTInstance instance = new CategoryWithGoodsFTInstance();
        instance.setName(category.getName());
        instance.setBanner(category.getBanner());
        instance.setId(category.getId());
        return instance;
    }

    public static List<CategoryWithGoodsFTInstance> toCategoryWithGoodsFTInstanceList(List<Category> categoryList) {
        List<CategoryWithGoodsFTInstance> instanceList = new ArrayList<>();
        for (Category c : categoryList) {
            CategoryWithGoodsFTInstance instance = toCategoryWithGoodsFTInstance(c);
            instanceList.add(instance);
        }
        return instanceList;
    }

    public static Category toCategory(CategoryParam param) {
        Category category = new Category();
        category.setId(param.getId());
        category.setBanner(param.getBanner());
        category.setName(param.getName());
        category.setShopId(param.getShopId());
        category.setSorted(param.getSorted());
        category.setIsShow(param.getIsShow());
        return category;
    }
}
