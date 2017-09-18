/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import com.mocentre.tehui.buy.model.Logistics;

import java.util.List;

/**
 * ILogisticsService.java的实现描述：物流
 * 
 * @author yukaiji 2017年6月28日 上午11:35:53
 */
public interface ILogisticsService {

    /**
     * 获取物流公司
     * 
     * @return
     */
    List<Logistics> getLogisticsList();

    /**
     * 根据code获取物流公司
     *
     * @return
     */
    Logistics getLogisticsByCode(String code);

    /**
     * 根据name获取物流公司
     *
     * @return
     */
    Logistics getLogisticsByName(String name);
}
