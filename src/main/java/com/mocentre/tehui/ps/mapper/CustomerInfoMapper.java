/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.ps.mapper;

import org.apache.commons.lang.StringUtils;

import com.mocentre.tehui.backend.model.CustomerInfoInstance;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.frontend.model.CustomerInfoFTInstance;
import com.mocentre.tehui.frontend.param.CustomerInfoParam;
import com.mocentre.tehui.ps.model.CustomerInfo;

/**
 * 类CustomerInfoMapper.java的实现描述：用户转换
 * 
 * @author sz.gong 2016年12月13日 下午12:02:44
 */
public class CustomerInfoMapper {

    public static CustomerInfoInstance toCustomerInfoInstance(CustomerInfo cumInfo) {
        CustomerInfoInstance cumInfoIns = new CustomerInfoInstance();
        cumInfoIns.setBirthday(cumInfo.getBirthday());
        cumInfoIns.setEmail(cumInfo.getEmail());
        cumInfoIns.setGmtCreated(cumInfo.getGmtCreated());
        cumInfoIns.setGrowth(cumInfo.getGrowth());
        cumInfoIns.setId(cumInfo.getId());
        cumInfoIns.setIntegral(cumInfo.getIntegral());
        cumInfoIns.setLastLoginTime(cumInfo.getLastLoginTime());
        cumInfoIns.setLevel(cumInfo.getLevel());
        cumInfoIns.setProfile(cumInfo.getProfile());
        cumInfoIns.setRegisterTime(cumInfo.getRegisterTime());
        cumInfoIns.setSex(cumInfo.getSex());
        cumInfoIns.setTelephone(cumInfo.getTelephone());
        cumInfoIns.setUserName(cumInfo.getUserName());
        return cumInfoIns;
    }

    public static CustomerInfoFTInstance toCustomerInfoFTInstance(CustomerInfo cumInfo) {
        CustomerInfoFTInstance cumInfoIns = new CustomerInfoFTInstance();
        cumInfoIns.setBirthday(cumInfo.getBirthday());
        cumInfoIns.setEmail(cumInfo.getEmail());
        cumInfoIns.setGmtCreated(cumInfo.getGmtCreated());
        cumInfoIns.setGrowth(cumInfo.getGrowth());
        cumInfoIns.setId(cumInfo.getId());
        cumInfoIns.setIntegral(cumInfo.getIntegral());
        cumInfoIns.setLastLoginTime(cumInfo.getLastLoginTime());
        cumInfoIns.setLevel(cumInfo.getLevel());
        cumInfoIns.setProfile(cumInfo.getProfile());
        cumInfoIns.setRegisterTime(cumInfo.getRegisterTime());
        cumInfoIns.setSex(cumInfo.getSex());
        cumInfoIns.setTelephone(cumInfo.getTelephone());
        cumInfoIns.setUserName(cumInfo.getUserName());
        cumInfoIns.setOpenid(cumInfo.getOpenid());
        cumInfoIns.setAbcaid(cumInfo.getAbcaid());
        if (StringUtils.isBlank(cumInfo.getPassword())) {
            cumInfoIns.setHaveSetPwd(false);
        } else {
            cumInfoIns.setHaveSetPwd(true);
        }
        return cumInfoIns;
    }

    public static CustomerInfo toCustomerInfo(CustomerInfoParam customerInfoParam) {
        CustomerInfo cumInfo = new CustomerInfo();
        if (StringUtils.isNotBlank(customerInfoParam.getBirthday())) {
            cumInfo.setBirthday(DateUtils.parseDate(customerInfoParam.getBirthday()));
        }
        cumInfo.setEmail(customerInfoParam.getEmail());
        cumInfo.setPassword(customerInfoParam.getPassword());
        cumInfo.setId(customerInfoParam.getId());
        cumInfo.setProfile(customerInfoParam.getProfile());
        cumInfo.setSex(customerInfoParam.getSex());
        cumInfo.setTelephone(customerInfoParam.getTelephone());
        cumInfo.setUserName(customerInfoParam.getUserName());
        return cumInfo;
    }

}
