package com.mocentre.tehui.ps.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.ps.dao.ICustomerAddressDao;
import com.mocentre.tehui.ps.model.CustomerAddress;
import com.mocentre.tehui.ps.service.ICustomerAddressService;

/**
 * 商城用户个人中心地址接口实现。
 * 
 * @author update by yukaiji on 2017年1月19日
 */

@Component
public class CustomerAddressService implements ICustomerAddressService {

    @Autowired
    private ICustomerAddressDao customerAddressDao;

    @Override
    @DataSource(value = "read")
    public CustomerAddress getAddressById(Long id) {
        return customerAddressDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public CustomerAddress getAddressByIdCum(Long customerId, Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("id", id);
        return customerAddressDao.queryUniquely(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public CustomerAddress getDefAddressByCustomerId(Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("isDefault", 1);
        CustomerAddress cumAds = customerAddressDao.queryUniquely(paramMap);
        return cumAds;
    }

    @Override
    @DataSource(value = "read")
    public List<CustomerAddress> getAddressByCustomerId(Long customerId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId);
        return customerAddressDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public CustomerAddress addAddress(CustomerAddress cumAdds) {
        if (cumAdds.getIsDefault().equals("1")) {
            customerAddressDao.updateDefult(cumAdds);
        }
        customerAddressDao.saveEntity(cumAdds);
        return cumAdds;
    }

    @Override
    @DataSource(value = "write")
    public Long delAddress(Long id) {
        int i = customerAddressDao.delById(id);
        return new Long(i);
    }

    @Override
    @DataSource(value = "write")
    public int delAddressList(List<Long> idList) {
        return customerAddressDao.delById(idList);
    }

    @Override
    @DataSource(value = "write")
    public Long delAddressByCustomerId(Long customerId) {
        int i = customerAddressDao.delByCustomerId(customerId);
        return new Long(i);
    }

    @Override
    @DataSource(value = "write")
    public Long delAddressByCustomerId(List<Long> customerIds) {
        int i = customerAddressDao.delByCustomerId(customerIds);
        return new Long(i);
    }

    @Override
    @DataSource(value = "write")
    public CustomerAddress editAddress(CustomerAddress cumAdds) {
        if (cumAdds.getIsDefault().equals("1")) {
            customerAddressDao.updateDefult(cumAdds);
        }
        customerAddressDao.updateEntity(cumAdds);
        return cumAdds;
    }

    @Override
    @DataSource(value = "write")
    public Long editAddressDefault(Long id, Long customerId) {
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerId(customerId);
        customerAddress.setId(id);
        customerAddress.setIsDefault("1");
        customerAddressDao.updateDefult(customerAddress);
        return customerAddressDao.updateEntity(customerAddress);
    }

}
