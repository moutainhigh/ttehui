/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import com.mocentre.tehui.buy.model.OrderLogistics;

/**
 * 类ILogisticsService.java的实现描述：物流service
 * 
 * @author sz.gong 2016年11月22日 下午2:02:00
 */
public interface IOrderLogisticsService {

    void updateByOrderId(Long orderId, String compay, String expNum, String isSms, String logisticsCode);

    OrderLogistics queryLogisticsByOrder(Long orderId);

}
