package com.mocentre.gift.ps.service;

import java.util.List;

import com.mocentre.gift.ps.model.GiftCustomerAddress;

/**
 * 礼品平台 商城用户个人中心调用接口。
 * @author liqifan
 * @date 创建时间：2017年4月13日 上午10:19:52
 */
public interface IGiftCustomerAddressService {
	/**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    GiftCustomerAddress getAddressById(Long id);

    /**
     * 通过用户id和id查询
     * 
     * @param customerId 用户id
     * @param id
     * @return
     */
    GiftCustomerAddress getAddressByIdCum(Long customerId, Long id);

    /**
     * 根据用户id获取默认收货地址
     * 
     * @param customerId
     * @return
     */
    GiftCustomerAddress getDefAddressByCustomerId(Long customerId);

    /**
     * 根据用户id获取收货地址
     */
    List<GiftCustomerAddress> getAddressByCustomerId(Long customerId);

    /**
     * 添加收货地址
     */
    GiftCustomerAddress addAddress(GiftCustomerAddress cumAdds);

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
    GiftCustomerAddress editAddress(GiftCustomerAddress cumAdds);

    /**
     * 修改默认地址
     */
    Long editAddressDefault(Long id, Long customerId);
}
