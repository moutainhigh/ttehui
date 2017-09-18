package com.mocentre.gift.ps.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.gift.ps.model.GiftCustomerAddress;

/**
 * 礼品平台 商城用户个人中心地址数据库操作接口。
 * @author liqifan
 * @date 创建时间：2017年4月13日 上午10:20:25
 */
public interface IGiftCustomerAddressDao {
	
	GiftCustomerAddress queryUniquely(Map<String, Object> paramMap);

    /**
     * 根据id获取
     */
    GiftCustomerAddress get(Serializable id);

    /**
     * 获取所有收货地址
     */
    List<GiftCustomerAddress> getAll();

    /**
     * 修改基本信息
     */
    Long updateEntity(GiftCustomerAddress GiftCustomerAddress);

    /**
     * 删除一个地址
     */
    int delById(Long id);

    /**
     * 删除多个地址
     */
    int delById(List<Long> ids);

    /**
     * 根据用户id删除
     */
    int delByCustomerId(Long id);

    /**
     * 根据用户id批量删除
     */
    int delByCustomerId(List<Long> id);

    /**
     * 添加一个地址
     */
    Long saveEntity(GiftCustomerAddress GiftCustomerAddress);

    /**
     * 修改默认地址（将不需要设为默认都改为0）
     */
    int updateDefult(GiftCustomerAddress GiftCustomerAddress);

    /**
     * 根据参数查询
     */
    List<GiftCustomerAddress> queryList(Map<String, Object> paramMap);
}
