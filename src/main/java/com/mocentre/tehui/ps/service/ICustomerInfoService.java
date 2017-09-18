package com.mocentre.tehui.ps.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.CustomerInfoInstance;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.frontend.param.CustomerInfoParam;
import com.mocentre.tehui.ps.model.CustomerInfo;

/**
 * 买家用户service接口 Created by 王雪莹 on 2016/11/22.
 */
public interface ICustomerInfoService {
    /**
     * 查询所有用户
     */
    List<CustomerInfo> getAllCustomer();

    /**
     * 根据id查询具体信息
     */
    CustomerInfo getCustomerById(Long id);

    /**
     * 根据id删除
     */
    Long delCustomById(Long id);

    /**
     * 根据idList批量删除
     */
    Long delCustomById(List<Long> ids);

    /**
     * 分页查询
     * 
     * @param require
     * @return
     */
    ListJsonResult<CustomerInfoInstance> queryPage(Requirement require);

    /**
     * 根据用户名查询具体信息.
     */
    CustomerInfo getCustomerByUserName(String userName);

    /**
     * 根据openId查询具体信息.
     */
    CustomerInfo getCustomerByOpenid(String openid);

    /**
     * 添加用户个人信息
     * 
     * @param customerInfoParam
     * @return
     */
    Long addCustomerInfo(CustomerInfoParam customerInfoParam);

    /**
     * 添加用户个人信息
     *
     * @param customerInfo
     * @return
     */
    Long addCustomerInfo(CustomerInfo customerInfo);

    /**
     * 修改用户个人信息
     * 
     * @param customerInfoParam
     * @return
     */
    Long updateCustomerInfo(CustomerInfoParam customerInfoParam);

    /**
     * 修改用户个登入时间
     * 
     * @param id
     * @return
     */
    Long updateCustomerLoginTime(Long id);

    /**
     * 修改密码
     * 
     * @param id
     * @param encryptedPassword
     * @param randomNum
     * @return
     */
    Long updatePassWord(Long id, String encryptedPassword, String randomNum);

    /**
     * 根据手机号获取用户信息
     * 
     * @param telephone
     * @return
     */
    CustomerInfo getCustomerByTelephone(String telephone);

    /**
     * 根据 农行appid获取用户信息
     * @param id
     * @return
     */
    CustomerInfo getCustomerByAbcaid(String id);
}
