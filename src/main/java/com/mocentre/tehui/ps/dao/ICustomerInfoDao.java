package com.mocentre.tehui.ps.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.ps.model.CustomerInfo;

/**
 * 用户基本信息 Created by 王雪莹 on 2016/11/22.
 */
public interface ICustomerInfoDao {
    /**
     * 根据id获取
     */
    CustomerInfo get(Serializable id);

    /**
     * 根据用户名获取
     */
    CustomerInfo getByUserName(String userName);

    /**
     * 获取所有用户
     */
    List<CustomerInfo> getAll();

    /**
     * 修改基本信息
     */
    Long updateEntity(CustomerInfo customerInfo);

    /**
     * 删除一个用户
     */
    int delById(Long id);

    /**
     * 删除多个用户
     */
    int delById(List<Long> ids);

    /**
     * 添加一个用户
     */
    Long saveEntity(CustomerInfo customerInfo);

    /**
     * 根据参数查询
     */
    List<CustomerInfo> queryList(Map<String, Object> paramMap);

    /**
     * 分页查询
     * 
     * @param require
     * @return
     */
    ListJsonResult<CustomerInfo> queryDatatablesPage(Requirement require);

    /**
     * 根据openid获取用户信息
     * 
     * @param openid
     * @return
     */
    CustomerInfo getByOpenid(String openid);

    /**
     * 根据电话号码获取用户信息
     * 
     * @param telephone
     * @return
     */
    CustomerInfo getByTelephone(String telephone);

    /**
     * 根据农行app用户id获取用户信息
     * @param abcaid
     * @return
     */
    CustomerInfo getByabcaId(String abcaid);
}
