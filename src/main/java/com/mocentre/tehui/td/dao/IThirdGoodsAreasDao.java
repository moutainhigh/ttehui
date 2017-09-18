/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao;

import com.mocentre.tehui.td.model.ThirdGoodsAreas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 第三方商品投放地区 Created by yukaiji on 2017/7/12.
 */
public interface IThirdGoodsAreasDao {

    /**
     * 保存第三方账户
     * 
     * @return
     */
    Long saveEntity(List<ThirdGoodsAreas> thirdGoodsAreasList);

    /**
     * 根据参数获取list
     * 
     * @return
     */
    List<ThirdGoodsAreas> queryList(Map<String, Object> paramMap);

    List<ThirdGoodsAreas> queryByBdCityCode(String bdCityCode);

    /**
     * 根据三方商品id删除
     * 
     * @param goodsId
     * @return
     */
    int removeById(Serializable goodsId);

}
