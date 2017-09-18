package com.mocentre.gift.gd.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 礼品平台分类数据库操作接口 Created by yukaiji on 2017/4/6.
 */
public interface IGiftCategoryDao {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    GiftCategory get(Serializable id);

    /**
     * 获取所有的分类(分页查询)
     *
     * @return 所有的分类
     */
    ListJsonResult<GiftCategory> queryDatatablesPage(Requirement require);

    /**
     * 根据条件获取分类
     *
     * @param paramMap
     * @return
     */
    List<GiftCategory> getGiftCategoryByParam(Map<String, Object> paramMap);

    /**
     * 插入分类
     *
     * @param GiftCategory
     */
    Long saveEntity(GiftCategory GiftCategory);

    /**
     * 修改分类
     *
     * @param GiftCategory
     */
    Long updateEntity(GiftCategory GiftCategory);

    /**
     * 根据id删除
     *
     * @param id
     */
    int logicRemoveById(Serializable id);
}
