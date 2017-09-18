/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import com.mocentre.tehui.buy.model.OrderBill;

/**
 * 类IOrderBillService.java的实现描述：订单发票service
 * 
 * @author sz.gong 2016年12月16日 下午3:15:53
 */
public interface IOrderBillService {

    /**
     * 通过订单编号查询
     * 
     * @param orderNum
     * @return
     */
    OrderBill queryBillByOrder(String orderNum);

}
