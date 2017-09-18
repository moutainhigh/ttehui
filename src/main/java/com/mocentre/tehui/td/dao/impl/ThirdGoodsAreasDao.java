/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.td.dao.IThirdGoodsAreasDao;
import com.mocentre.tehui.td.model.ThirdGoodsAreas;

/**
 * 第三方商品区域 Created by yukaiji on 2017/7/12.
 */
@Repository
public class ThirdGoodsAreasDao extends BaseRedisDao<ThirdGoodsAreas, String, ThirdGoodsAreas> implements
        IThirdGoodsAreasDao {

    @Override
    public Long saveEntity(List<ThirdGoodsAreas> thirdGoodsAreasList) {
        return (long) super.insert("ThirdGoodsAreas_insert", thirdGoodsAreasList);
    }

    @Override
    public List<ThirdGoodsAreas> queryByBdCityCode(String bdCityCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("bdCityCode", bdCityCode);
        return super.queryList(map);
    }

}
