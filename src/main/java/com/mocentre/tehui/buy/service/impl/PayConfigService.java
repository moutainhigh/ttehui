/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.buy.dao.IPayConfigDao;
import com.mocentre.tehui.buy.model.PayConfig;
import com.mocentre.tehui.buy.service.IPayConfigService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

/**
 * 类PayConfigService.java的实现描述：付款方式配置service实现
 * 
 * @author sz.gong 2016年12月14日 下午4:25:48
 */
@Component
public class PayConfigService implements IPayConfigService {

    @Autowired
    private IPayConfigDao payConfigDao;

    @Override
    @DataSource(value = "read")
    public List<PayConfig> queryOpenPay() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("open", 1);
        return payConfigDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public List<PayConfig> getAll() {
        return payConfigDao.queryList(new HashMap<String, Object>());
    }

    @Override
    @DataSource(value = "write")
    public Long updateOpenStatus(Long id, String status) {
        PayConfig payConfig = new PayConfig();
        payConfig.setId(id);
        if("0".equals(status)){
            payConfig.setOpen(0);
        }
        if("1" .equals(status)){
            payConfig.setOpen(1);
        }
        return payConfigDao.updateEntity(payConfig);
    }
}
