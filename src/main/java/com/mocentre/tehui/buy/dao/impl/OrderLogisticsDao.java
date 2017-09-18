/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mocentre.tehui.buy.dao.IOrderLogisticsDao;
import com.mocentre.tehui.buy.model.OrderLogistics;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 类LogisticsDao.java的实现描述：物流dao实现
 * 
 * @author sz.gong 2016年11月22日 下午1:50:25
 */
@Repository
public class OrderLogisticsDao extends BaseDao<OrderLogistics> implements IOrderLogisticsDao {

    @Override
    public void save(Long orderId, String company, String expNum, String isSms, String logisticsCode) {
        OrderLogistics lg = new OrderLogistics();
        lg.setCompany(company);
        lg.setExpNum(expNum);
        lg.setOrderId(orderId);
        lg.setIsSms(isSms);
        lg.setLogisticsCode(logisticsCode);
        super.saveEntity(lg);
    }

    @Override
    public int logicRemove(Long orderId) {
        return super.update("Logistics_logicDelete", orderId);
    }

    @Override
    public OrderLogistics queryByOrderId(Long orderId) {
        Assert.notNull(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public Long updateByOrderId(Long orderId, String company, String expNum, String isSms, String logisticsCode) {
        Assert.notNull(orderId);
        OrderLogistics lgs = new OrderLogistics();
        lgs.setOrderId(orderId);
        lgs.setIsSms(isSms);
        lgs.setCompany(company);
        lgs.setExpNum(expNum);
        lgs.setLogisticsCode(logisticsCode);
        return super.updateEntity(lgs);
    }

}
