package com.mocentre.tehui.ps.service;

import java.util.List;

import com.mocentre.tehui.ps.model.CustomerAddress;

/**
 * 商城用户个人中心调用接口。
 * 
 * @author update by yukaiji on 2017年1月19日
 */

public interface ICustomerAddressService {

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    CustomerAddress getAddressById(Long id);

    /**
     * 通过用户id和id查询
     * 
     * @param customerId 用户id
     * @param id
     * @return
     */
    CustomerAddress getAddressByIdCum(Long customerId, Long id);

    /**
     * 根据用户id获取默认收货地址
     * 
     * @param customerId
     * @return
     */
    CustomerAddress getDefAddressByCustomerId(Long customerId);

    /**
     * 根据用户id获取收货地址
     */
    List<CustomerAddress> getAddressByCustomerId(Long customerId);

    /**
     * 添加收货地址
     */
    CustomerAddress addAddress(CustomerAddress cumAdds);

    /**
     * 删除收货地址
     */
    Long delAddress(Long id);

    /**
     * 删除收货地址List
     */
    int delAddressList(List<Long> idList);

    /**
     * 根据用户id删除收货地址
     */
    Long delAddressByCustomerId(Long customerId);

    /**
     * 根据用户idList删除收货地址
     */
    Long delAddressByCustomerId(List<Long> customerIds);

    /**
     * 修改收货地址
     */
    CustomerAddress editAddress(CustomerAddress cumAdds);

    /**
     * 修改默认地址
     */
    Long editAddressDefault(Long id, Long customerId);
}
