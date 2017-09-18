/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.td.model.ThirdGoods;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类IThirdGoodsDao.java的实现描述：第三方商品
 * 
 * @author yukaiji 2017年7月12日
 */
public interface IThirdGoodsDao {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ThirdGoods get(Serializable id);

    /**
     * 分页查询商品列表
     *
     * @return 商品列表
     */
    ListJsonResult<ThirdGoods> queryDatatablesPage(Requirement require);

    /**
     * 根据条件获取关联商品
     *
     * @param paramMap
     * @return
     */
    List<ThirdGoods> getList(Map<String, Object> paramMap);

    /**
     * 添加关联商品或活动
     *
     * @param thirdGoods
     */
    Long saveEntity(ThirdGoods thirdGoods);

    /**
     * 修改关联商品或活动
     *
     * @param thirdGoods
     */
    Long updateEntity(ThirdGoods thirdGoods);

    /**
     * 根据id删除
     *
     * @param id
     */
    int logicRemoveById(Serializable id);

    /**
     * 查询分页列表
     *
     * @return
     */
    List<ThirdGoods> getPage(String showLocal, Long bdCityCode);

    List<ThirdGoods> queryFromCache(String showLocal);

    ThirdGoods queryFromCacheById(String showLocal, Long id);

    boolean updateToCache(ThirdGoods thirdGoods);

    boolean deleteFromCache(Long id, String showLocal);

}
