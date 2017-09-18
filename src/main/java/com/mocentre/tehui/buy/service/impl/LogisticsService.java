/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.buy.dao.ILogisticsDao;
import com.mocentre.tehui.buy.model.Logistics;
import com.mocentre.tehui.buy.service.ILogisticsService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

/**
 * LogisticsService.java的实现描述：物流实现类
 *
 * @author yukaiji 2017年6月28日 上午11:35:53
 */
@Component
public class LogisticsService implements ILogisticsService {

    @Autowired
    private ILogisticsDao logisticsDao;

    @Override
    @DataSource(value = "read")
    public List<Logistics> getLogisticsList() {
        return logisticsDao.getAll();
    }

    @Override
    @DataSource(value = "read")
    public Logistics getLogisticsByCode(String code) {
        return logisticsDao.getByCode(code);
    }

    @Override
    @DataSource(value = "read")
    public Logistics getLogisticsByName(String name) {
        return logisticsDao.getByName(name);
    }
}
