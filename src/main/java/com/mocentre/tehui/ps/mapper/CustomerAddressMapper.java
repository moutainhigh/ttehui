/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.ps.mapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.backend.model.CustomerAddressInstance;
import com.mocentre.tehui.frontend.model.CustomerAddressFTInstance;
import com.mocentre.tehui.frontend.param.CustomerAddsParam;
import com.mocentre.tehui.ps.model.CustomerAddress;
import com.mocentre.tehui.system.dao.IAreasDao;
import com.mocentre.tehui.system.model.Areas;

/**
 * 类CustomerAddressMapper.java的实现描述：客户收货地址转换
 * 
 * @author sz.gong 2016年12月13日 下午12:16:00
 */
@Component
public class CustomerAddressMapper {

    @Autowired
    private IAreasDao areasDao;

    public CustomerAddressInstance toCustomerAddressInstance(CustomerAddress cumAdds) {
        String province = cumAdds.getProvince();
        String city = cumAdds.getCity();
        String area = cumAdds.getArea();
        CustomerAddressInstance cumAddsIns = new CustomerAddressInstance();
        cumAddsIns.setAddress(cumAdds.getAddress());
        cumAddsIns.setArea(area);
        cumAddsIns.setCity(city);
        cumAddsIns.setCustomerId(cumAdds.getCustomerId());
        cumAddsIns.setId(cumAdds.getId());
        cumAddsIns.setIsDefault(cumAdds.getIsDefault());
        cumAddsIns.setPeriod(cumAddsIns.getPeriod());
        cumAddsIns.setPostCode(cumAdds.getPostCode());
        cumAddsIns.setProvince(province);
        cumAddsIns.setRecipient(cumAdds.getRecipient());
        cumAddsIns.setTelephone(cumAdds.getTelephone());
        if (StringUtils.isNotBlank(province)) {
            Areas areas = areasDao.getFromCache(province);
            if (areas != null) {
                cumAddsIns.setProvinceName(areas.getName());
            }
        }
        if (StringUtils.isNotBlank(city)) {
            Areas areas = areasDao.getFromCache(city);
            if (areas != null) {
                cumAddsIns.setCityName(areas.getName());
            }
        }
        if (StringUtils.isNotBlank(area)) {
            Areas areas = areasDao.getFromCache(area);
            if (areas != null) {
                cumAddsIns.setAreaName(areas.getName());
            }
        }
        return cumAddsIns;
    }

    public CustomerAddressFTInstance toCustomerAddressFTInstance(CustomerAddress cumAdds) {
        if (cumAdds == null) {
            return null;
        }
        String province = cumAdds.getProvince();
        String city = cumAdds.getCity();
        String area = cumAdds.getArea();
        CustomerAddressFTInstance cumAddsIns = new CustomerAddressFTInstance();
        cumAddsIns.setAddress(cumAdds.getAddress());
        cumAddsIns.setArea(area);
        cumAddsIns.setCity(city);
        cumAddsIns.setCustomerId(cumAdds.getCustomerId());
        cumAddsIns.setId(cumAdds.getId());
        cumAddsIns.setIsDefault(cumAdds.getIsDefault());
        cumAddsIns.setPeriod(cumAdds.getPeriod());
        cumAddsIns.setPostCode(cumAdds.getPostCode());
        cumAddsIns.setProvince(province);
        cumAddsIns.setRecipient(cumAdds.getRecipient());
        cumAddsIns.setTelephone(cumAdds.getTelephone());
        if (StringUtils.isNotBlank(province)) {
            Areas areas = areasDao.getFromCache(province);
            if (areas != null) {
                cumAddsIns.setProvinceName(areas.getName());
            }
        }
        if (StringUtils.isNotBlank(city)) {
            Areas areas = areasDao.getFromCache(city);
            if (areas != null) {
                cumAddsIns.setCityName(areas.getName());
            }
        }
        if (StringUtils.isNotBlank(area)) {
            Areas areas = areasDao.getFromCache(area);
            if (areas != null) {
                cumAddsIns.setAreaName(areas.getName());
            }
        }
        return cumAddsIns;
    }

    public CustomerAddress toCustomerAddress(CustomerAddsParam cumAddsParam) {
        CustomerAddress cumAdds = new CustomerAddress();
        cumAdds.setId(cumAddsParam.getId());
        cumAdds.setAddress(cumAddsParam.getAddress());
        cumAdds.setArea(cumAddsParam.getArea());
        cumAdds.setCity(cumAddsParam.getCity());
        cumAdds.setCustomerId(cumAddsParam.getCustomerId());
        cumAdds.setIsDefault(cumAddsParam.getIsDefault());
        cumAdds.setPeriod(cumAddsParam.getPeriod());
        cumAdds.setPostCode(cumAddsParam.getPostCode());
        cumAdds.setProvince(cumAddsParam.getProvince());
        cumAdds.setRecipient(cumAddsParam.getRecipient());
        cumAdds.setTelephone(cumAddsParam.getTelephone());
        return cumAdds;
    }

}
