/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.mapper;

import com.mocentre.tehui.backend.model.PayConfigInstance;
import com.mocentre.tehui.buy.enums.PayType;
import com.mocentre.tehui.buy.model.PayConfig;
import com.mocentre.tehui.frontend.model.CashPayFTInstance;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * 类PayConfigMapper.java的实现描述：付款方式转换
 * 
 * @author sz.gong 2016年12月27日 上午11:16:39
 */
public class PayConfigMapper {

    public static CashPayFTInstance toCashPayFTInstance(PayConfig payConfig) {
        CashPayFTInstance cashPay = new CashPayFTInstance();
        String payType = payConfig.getPayType();
        cashPay.setPayType(payType);
        cashPay.setPayName(PayType.getName(payType));
        return cashPay;
    }
    public static PayConfigInstance toPayConfigFTInstance(PayConfig payConfig) {
        PayConfigInstance instance = new PayConfigInstance();
        BeanCopier copier = BeanCopier.create(PayConfig.class, PayConfigInstance.class, false);
        copier.copy(payConfig, instance, null);
        return instance;
    }

    public static List<PayConfigInstance> toPayConfigFTInstanceList(List<PayConfig> payConfigList) {
        if (payConfigList == null) {
            return null;
        }
        List<PayConfigInstance> instanceList = new ArrayList<>();
        for (PayConfig payConfig : payConfigList) {
            instanceList.add(toPayConfigFTInstance(payConfig));
        }
        return instanceList;
    }

}
