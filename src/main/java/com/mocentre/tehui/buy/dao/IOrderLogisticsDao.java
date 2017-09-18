/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import com.mocentre.tehui.buy.model.OrderLogistics;

/**
 * 类IIogisticsDao.java的实现描述：物流Dao
 * 
 * @author sz.gong 2016年11月22日 下午1:49:15
 */
public interface IOrderLogisticsDao {

    void save(Long orderId, String company, String expNum, String isSms, String logisticsCode);

    Long updateEntity(OrderLogistics lgis);

    Long updateByOrderId(Long orderId, String compay, String expNum, String isSms, String logisticsCode);

    int logicRemove(Long orderId);

    OrderLogistics queryByOrderId(Long orderId);
}
