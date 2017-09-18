package com.mocentre.tehui.goods.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.CategoryGoods;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分类商品dao Created by yukaiji on 2017/7/4.
 */
public interface ICategoryGoodsDao {

    /**
     * 分页查询
     *
     * @param require
     * @return
     */
    ListJsonResult<CategoryGoods> queryDatatablesPage(Requirement require);

    /**
     * 获取所有
     *
     * @return
     */
    List<CategoryGoods> getAll();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CategoryGoods get(Serializable id);

    /**
     * 根据条件查询
     *
     * @param paramMap
     * @return
     */
    List<CategoryGoods> queryList(Map<String, Object> paramMap);

    /**
     * 修改
     *
     * @param categoryGoods
     * @return
     */
    Long updateEntity(CategoryGoods categoryGoods);

    /**
     * 添加
     *
     * @param categoryGoods
     * @return
     */
    Long saveEntity(CategoryGoods categoryGoods);

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    int delById(Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int delById(List<Long> ids);

    /**
     * 根据id逻辑删除
     *
     * @param id
     * @return
     */
    int logicRemoveById(Serializable id);
}
