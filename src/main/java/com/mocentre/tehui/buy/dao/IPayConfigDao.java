/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import java.util.List;
import java.util.Map;

import com.mocentre.tehui.buy.model.PayConfig;

/**
 * 类IPayConfigDao.java的实现描述：付款方式配置dao
 * 
 * @author sz.gong 2016年12月14日 下午4:21:08
 */
public interface IPayConfigDao {

    List<PayConfig> queryList(Map<String, Object> paramMap);

    Long updateEntity(PayConfig payConfig);
}
