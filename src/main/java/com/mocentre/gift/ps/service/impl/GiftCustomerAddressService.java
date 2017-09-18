package com.mocentre.gift.ps.service.impl;

import com.mocentre.gift.ps.dao.IGiftCustomerAddressDao;
import com.mocentre.gift.ps.model.GiftCustomerAddress;
import com.mocentre.gift.ps.service.IGiftCustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商城用户个人中心地址接口实现。
 * 
 * @author update by yukaiji on 2017年1月19日
 */

/**
 * 礼品平台 商城用户个人中心地址接口实现。
 * @author liqifan
 * @date 创建时间：2017年4月13日 上午10:19:15
 */
@Component
public class GiftCustomerAddressService implements IGiftCustomerAddressService {

    @Autowired
    private IGiftCustomerAddressDao GiftCustomerAddressDao;

    @Override
    public GiftCustomerAddress getAddressById(Long id) {
        return GiftCustomerAddressDao.get(id);
    }

    @Override
    public GiftCustomerAddress getAddressByIdCum(Long customerId, Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("id", id);
        return GiftCustomerAddressDao.queryUniquely(paramMap);
    }

    @Override
    public GiftCustomerAddress getDefAddressByCustomerId(Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("isDefault", 1);
        GiftCustomerAddress cumAds = GiftCustomerAddressDao.queryUniquely(paramMap);
        return cumAds;
    }

    @Override
    public List<GiftCustomerAddress> getAddressByCustomerId(Long customerId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId);
        return GiftCustomerAddressDao.queryList(paramMap);
    }

    @Override
    public GiftCustomerAddress addAddress(GiftCustomerAddress cumAdds) {
        GiftCustomerAddressDao.saveEntity(cumAdds);
        return cumAdds;
    }

    @Override
    public Long delAddress(Long id) {
        int i = GiftCustomerAddressDao.delById(id);
        return new Long(i);
    }

    @Override
    public int delAddressList(List<Long> idList) {
        return GiftCustomerAddressDao.delById(idList);
    }

    @Override
    public Long delAddressByCustomerId(Long customerId) {
        int i = GiftCustomerAddressDao.delByCustomerId(customerId);
        return new Long(i);
    }

    @Override
    public Long delAddressByCustomerId(List<Long> customerIds) {
        int i = GiftCustomerAddressDao.delByCustomerId(customerIds);
        return new Long(i);
    }

    @Override
    public GiftCustomerAddress editAddress(GiftCustomerAddress cumAdds) {
        GiftCustomerAddressDao.updateEntity(cumAdds);
        return cumAdds;
    }

    @Override
    public Long editAddressDefault(Long id, Long customerId) {
        GiftCustomerAddress GiftCustomerAddress = new GiftCustomerAddress();
        GiftCustomerAddress.setCustomerId(customerId);
        GiftCustomerAddress.setId(id);
        GiftCustomerAddress.setIsDefault("1");
        GiftCustomerAddressDao.updateDefult(GiftCustomerAddress);
        return GiftCustomerAddressDao.updateEntity(GiftCustomerAddress);
    }

}
