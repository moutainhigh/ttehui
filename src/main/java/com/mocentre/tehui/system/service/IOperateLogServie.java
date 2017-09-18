/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.model.OperateLog;

/**
 * 类IOperateLogServie.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月21日 下午3:08:18
 */
public interface IOperateLogServie {

    ListJsonResult<OperateLog> queryPage(Requirement require);

    void saveOperateLog(OperateLog log);

}
