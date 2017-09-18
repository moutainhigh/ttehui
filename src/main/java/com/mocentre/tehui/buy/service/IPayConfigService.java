/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.service;

import java.util.List;

import com.mocentre.tehui.buy.model.PayConfig;

/**
 * 类IPayConfigService.java的实现描述：付款方式配置service
 * 
 * @author sz.gong 2016年12月14日 下午4:25:17
 */
public interface IPayConfigService {

    /**
     * 查询可以付款的方式
     * 
     * @return
     */
    List<PayConfig> queryOpenPay();

    /**
     * 查询全部付款的方式
     *
     * @return
     */
    List<PayConfig> getAll();

    /**
     * 更新开启状态
     *
     * @return
     */
    Long updateOpenStatus(Long id, String status);
}
