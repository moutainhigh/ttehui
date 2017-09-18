/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao.impl;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.td.dao.IThirdOrderDao;
import com.mocentre.tehui.td.emnus.ThirdOrderStatus;
import com.mocentre.tehui.td.model.ThirdOrder;

/**
 * 类ThirdOrderDao.java的实现描述：第三方订单Dao
 * 
 * @author sz.gong 2017年6月20日 下午5:41:42
 */
@Repository
public class ThirdOrderDao extends BaseDao<ThirdOrder> implements IThirdOrderDao {

    @Override
    public Long updatePaymentnum(Long id, String paymentNum) {
        ThirdOrder o = new ThirdOrder();
        o.setId(id);
        o.setPaymentNum(paymentNum);
        return super.update(o);
    }

    @Override
    public int updateOrderStatus(String orderStatus, String paymentNum) {
        ThirdOrder o = new ThirdOrder();
        o.setOrderStatus(orderStatus);
        o.setPaymentNum(paymentNum);
        return super.update("ThirdOrder_status_update", o);
    }

    @Override
    public int updateOrderStatusRefund(String orderNum, String appKey) {
        ThirdOrder o = new ThirdOrder();
        o.setOrderNum(orderNum);
        o.setAppKey(appKey);
        o.setOrderStatus(ThirdOrderStatus.REFUND.getCodeValue());
        return super.update("ThirdOrder_status_update_refund", o);
    }

}
