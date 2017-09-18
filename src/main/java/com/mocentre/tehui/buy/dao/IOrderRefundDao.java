/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import com.mocentre.tehui.buy.model.OrderRefund;

/**
 * 类IOrderRefundDao.java的实现描述：订单退款
 * 
 * @author sz.gong 2017年3月17日 下午4:10:32
 */
public interface IOrderRefundDao {

    Long saveEntity(OrderRefund orderRefund);

}
