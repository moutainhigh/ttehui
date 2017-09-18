/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao;

import com.mocentre.tehui.td.model.ThirdOrderRefund;

/**
 * 类IThirdOrderRefund.java的实现描述：订单退款DAO
 * 
 * @author sz.gong 2017年6月29日 下午4:46:51
 */
public interface IThirdOrderRefundDao {

    Long saveEntity(ThirdOrderRefund orderRefund);

}
