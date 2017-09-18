/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.service;

import com.mocentre.tehui.td.model.ThirdOrderPay;

/**
 * 类IThirdOrderPayService.java的实现描述：订单支付service接口
 * 
 * @author sz.gong 2017年6月21日 下午5:45:03
 */
public interface IThirdOrderPayService {

    /**
     * 通过支付编号查询
     * 
     * @param paymentNum
     * @return
     */
    ThirdOrderPay getThirdOrderPay(String paymentNum);

}
