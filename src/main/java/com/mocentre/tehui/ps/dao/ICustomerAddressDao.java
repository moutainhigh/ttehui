package com.mocentre.tehui.ps.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.tehui.ps.model.CustomerAddress;

/**
 * 商城用户个人中心地址数据库操作接口。
 * 
 * @author update by yukaiji on 2017年1月19日
 */
public interface ICustomerAddressDao {

    CustomerAddress queryUniquely(Map<String, Object> paramMap);

    /**
     * 根据id获取
     */
    CustomerAddress get(Serializable id);

    /**
     * 获取所有收货地址
     */
    List<CustomerAddress> getAll();

    /**
     * 修改基本信息
     */
    Long updateEntity(CustomerAddress customerAddress);

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
    Long saveEntity(CustomerAddress customerAddress);

    /**
     * 修改默认地址（将不需要设为默认都改为0）
     */
    int updateDefult(CustomerAddress customerAddress);

    /**
     * 根据参数查询
     */
    List<CustomerAddress> queryList(Map<String, Object> paramMap);
}
