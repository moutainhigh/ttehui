/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import com.mocentre.tehui.buy.model.Logistics;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ILogisticsDao.java的实现描述：物流
 *
 * @author yukaiji 2017年6月28日 上午11:35:53
 */
public interface ILogisticsDao {

    Long saveEntity(Logistics logistics);

    Logistics get(Serializable id);

    Logistics getByCode(String code);

    Logistics getByName(String name);

    Long updateEntity(Logistics img);

    List<Logistics> queryList(Map<String, Object> paramMap);

    int logicRemoveById(Serializable id);

    List<Logistics> getAll();
}
