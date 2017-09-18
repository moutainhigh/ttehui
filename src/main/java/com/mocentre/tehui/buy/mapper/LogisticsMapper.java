/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.mapper;

import com.mocentre.tehui.backend.model.LogisticsInstance;
import com.mocentre.tehui.buy.model.Logistics;

/**
 * LogisticsMapper.java的实现描述：物流转换
 *
 * @author sz.gong 2017年3月22日 上午11:58:24
 */
public class LogisticsMapper {

    public static LogisticsInstance toLogisticsInstance(Logistics logistics) {
        LogisticsInstance logisticsInstance = new LogisticsInstance();
        logisticsInstance.setCode(logistics.getCode());
        logisticsInstance.setName(logistics.getName());
        return logisticsInstance;
    }

}
