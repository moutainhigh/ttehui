package com.mocentre.tehui.goods.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.ICategoryDao;
import com.mocentre.tehui.goods.model.Category;
import com.mocentre.tehui.goods.service.ICategoryService;

/**
 * 商品库存serivice实现 Created by 王雪莹 on 2016/11/5.
 */
@Component
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    @DataSource(value = "write")
    public Long addCategory(Category category) {
        return categoryDao.saveEntity(category);
    }

    @Override
    @DataSource(value = "write")
    public int delCategory(Long id) {
        return categoryDao.delById(id);
    }

    @Override
    @DataSource(value = "write")
    public int delCategory(List<Long> ids) {
        return categoryDao.delById(ids);
    }

    @Override
    @DataSource(value = "write")
    public Long updateCategory(Category category) {
        return categoryDao.updateEntity(category);
    }

    @Override
    @DataSource(value = "read")
    public List<Category> getShowCategory(Long shopId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("shopId", shopId);
        return categoryDao.queryListASC(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public Category getById(Long id) {
        Category category = categoryDao.get(id);
        return category;
    }

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Category> getCategoryPage(Requirement require) {
        return categoryDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public List<Category> getAllCategoryList() {
        return categoryDao.getAll();
    }

    /**
     * 按顺序获取分类
     * 
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Category> getAllCategoryOrderBySort() {
        Map<String, Object> map = new HashMap<>();
        map.put("isShow", "1");
        map.put("orderColumn", "sorted");
        return categoryDao.queryList(map);
    }

    @Override
    @DataSource(value = "read")
    public List<Category> getCategoryPage(Integer start, Integer length) {
        List<Category> categories = new ArrayList<>();
        PageInfo<Category> pageInfo = categoryDao.getPage(start, length);
        categories = pageInfo.getRows();
        return categories;
    }

}
