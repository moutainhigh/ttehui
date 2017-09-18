package com.mocentre.tehui.goods.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Category;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 频道Dao接口 Created by 王雪莹 on 2016/11/4.
 */
public interface ICategoryDao {

    ListJsonResult<Category> queryDatatablesPage(Requirement require);

    List<Category> getAll();

    Category get(Serializable id);

    List<Category> queryList(Map<String, Object> paramMap);

    List<Category> queryListASC(Map<String, Object> paramMap);

    Long updateEntity(Category category);

    //int updateEntity(List<Category> categorys);

    Long saveEntity(Category category);

    int saveEntity(List<Category> categorys);

    int delById(Long id);

    int delById(List<Long> ids);

    /**
     * 查询分页列表
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<Category> getPage(Integer start, Integer length);
}
