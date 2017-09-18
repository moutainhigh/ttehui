/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao.impl;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.buy.dao.ILogisticsDao;
import com.mocentre.tehui.buy.model.Logistics;
import com.mocentre.tehui.core.dao.BaseRedisDao;

/**
 * 类ImageDao.java的实现描述：图片实现类
 *
 * @author sz.gong 2016年11月17日 上午11:34:01
 */
@Repository
public class LogisticsDao extends BaseRedisDao<Logistics, String, Logistics> implements ILogisticsDao {

    @Override
    public Logistics getByCode(String code) {
        return super.queryUniquely(entityClass.getSimpleName() + "_getByCode", code);
    }

    @Override
    public Logistics getByName(String name) {
        return super.queryUniquely(entityClass.getSimpleName() + "_getByName", name);
    }
}
