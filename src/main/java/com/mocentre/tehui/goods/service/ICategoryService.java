package com.mocentre.tehui.goods.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Category;

/**
 * 分类service接口 Created by 王雪莹 on 2016/11/5.
 */
public interface ICategoryService {
    /**
     * 添加一个新分类（店铺id不能为空）
     */
    Long addCategory(Category category);

    /**
     * 删除一个分类
     */
    int delCategory(Long id);

    /**
     * 批量删除多个分类
     */
    int delCategory(List<Long> ids);

    /**
     * 根据 id 修改分类属性
     */
    Long updateCategory(Category category);

    /**
     * 查询某一店铺展示的分类（店铺id）
     */
    List<Category> getShowCategory(Long shopId);

    /**
     * 根据id查询
     */
    Category getById(Long id);

    /**
     * 分页查询
     * 
     * @param require
     * @return
     */
    ListJsonResult<Category> getCategoryPage(Requirement require);

    /**
     * 查询所有显示的分类
     * 
     * @return
     */
    List<Category> getAllCategoryList();

    /**
     * 按顺序获取分类
     * 
     * @return
     */
    List<Category> getAllCategoryOrderBySort();

    /**
     * 分页查询
     *
     * @return
     */
    List<Category> getCategoryPage(Integer start, Integer length);
}
