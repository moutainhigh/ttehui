package com.mocentre.gift.gd.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.backend.param.GiftCategoryParam;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.util.List;
import java.util.Map;

/**
 * 礼品平台分类操作service.
 *
 * Created by yukaiji on 2017/4/6.
 */
public interface IGiftCategoryService {


    /**
     * 获取所有的分类(分页查询)
     *
     * @param require
     */
    ListJsonResult<GiftCategory> queryGiftCategoryPage(Requirement require);

    /**
     * 根据分类id获取
     *
     * @param id
     */
    GiftCategory getGiftCategoryById(Long id);

    /**
     * 根据条件获取
     *
     * @param param
     */
    List<GiftCategory> getGiftCategoryByParam(Map<String,Object> param);

    /**
     * 添加一个分类
     *
     * @param giftCategoryParam
     */
    GiftCategory addGiftCategory(GiftCategoryParam giftCategoryParam);

    /**
     * 修改一个分类
     *
     * @param giftCategoryParam
     * @return id
     */
    Long updateGiftCategory(GiftCategoryParam giftCategoryParam);

    /**
     * 根据id删除一个分类
     *
     * @param id
     * @return
     */
    int removeById(Long id);
}
