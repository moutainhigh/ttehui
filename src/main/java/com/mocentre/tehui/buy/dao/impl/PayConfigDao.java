/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao.impl;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.buy.dao.IPayConfigDao;
import com.mocentre.tehui.buy.model.PayConfig;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 类PayConfigDao.java的实现描述：付款方式dao实现
 * 
 * @author sz.gong 2016年12月14日 下午4:22:02
 */
@Repository
public class PayConfigDao extends BaseDao<PayConfig> implements IPayConfigDao {

}
