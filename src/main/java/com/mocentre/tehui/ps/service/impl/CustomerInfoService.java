package com.mocentre.tehui.ps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.CustomerInfoInstance;
import com.mocentre.tehui.common.constant.CONSTANT;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.frontend.param.CustomerInfoParam;
import com.mocentre.tehui.ps.dao.ICustomerInfoDao;
import com.mocentre.tehui.ps.mapper.CustomerInfoMapper;
import com.mocentre.tehui.ps.model.CustomerInfo;
import com.mocentre.tehui.ps.service.ICustomerAddressService;
import com.mocentre.tehui.ps.service.ICustomerInfoService;

/**
 * 买家用户基本信息 Created by 王雪莹 on 2016/11/22.
 */
@Component
public class CustomerInfoService implements ICustomerInfoService {

    @Autowired
    private ICustomerInfoDao        customerInfoDao;
    @Autowired
    private ICustomerAddressService addressService;

    /**
     * 查询所有用户
     * 
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<CustomerInfo> getAllCustomer() {
        List<CustomerInfo> customerInfoList = customerInfoDao.getAll();
        return customerInfoList;
    }

    /**
     * 根据id查询详细信息
     * 
     * @param id
     * @return
     */
    @Override
    @DataSource(value = "read")
    public CustomerInfo getCustomerById(Long id) {
        return customerInfoDao.get(id);
    }

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    @Override
    @DataSource(value = "write")
    public Long delCustomById(Long id) {
        int i = customerInfoDao.delById(id);
        return new Long(i);
    }

    /**
     * 根据idList删除
     * 
     * @param ids
     * @return
     */
    @Override
    @DataSource(value = "write")
    public Long delCustomById(List<Long> ids) {
        int i = customerInfoDao.delById(ids);
        addressService.delAddressByCustomerId(ids);
        return new Long(i);
    }

    /**
     * 分页查询
     * 
     * @param require
     * @return
     */
    @Override
    @DataSource(value = "read")
    public ListJsonResult<CustomerInfoInstance> queryPage(Requirement require) {
        ListJsonResult<CustomerInfoInstance> result = new ListJsonResult<CustomerInfoInstance>();
        ListJsonResult<CustomerInfo> pageInfo = customerInfoDao.queryDatatablesPage(require);
        List<CustomerInfo> cumList = pageInfo.getData();
        List<CustomerInfoInstance> cumInsList = new ArrayList<CustomerInfoInstance>();
        if (cumList != null) {
            for (CustomerInfo cum : cumList) {
                CustomerInfoInstance cumIns = CustomerInfoMapper.toCustomerInfoInstance(cum);
                cumInsList.add(cumIns);
            }
        }
        result.setData(cumInsList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    @Override
    @DataSource(value = "read")
    public CustomerInfo getCustomerByUserName(String userName) {
        return customerInfoDao.getByUserName(userName);
    }

    @Override
    @DataSource(value = "read")
    public CustomerInfo getCustomerByOpenid(String openid) {
        return customerInfoDao.getByOpenid(openid);
    }

    @Override
    @DataSource(value = "write")
    public Long addCustomerInfo(CustomerInfoParam customerInfoParam) {
        CustomerInfo customerInfo = CustomerInfoMapper.toCustomerInfo(customerInfoParam);
        return addCustomerInfo(customerInfo);
    }

    @Override
    @DataSource(value = "write")
    public Long addCustomerInfo(CustomerInfo customerInfo) {
        customerInfo.setRegisterTime(new Date());
        customerInfo.setLastLoginTime(new Date());
        //添加一个用户如果用户头像为空则设置为默认头像
        if (StringUtils.isEmpty(customerInfo.getProfile())) {
            customerInfo.setProfile(CONSTANT.CUSTOMER_PROFILE_DEFAULT);
        }
        return customerInfoDao.saveEntity(customerInfo);
    }

    @Override
    @DataSource(value = "write")
    public Long updateCustomerInfo(CustomerInfoParam customerInfoParam) {
        CustomerInfo customerInfo = CustomerInfoMapper.toCustomerInfo(customerInfoParam);
        return customerInfoDao.updateEntity(customerInfo);
    }

    @Override
    @DataSource(value = "write")
    public Long updateCustomerLoginTime(Long id) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(id);
        customerInfo.setLastLoginTime(new Date());
        return customerInfoDao.updateEntity(customerInfo);
    }

    @Override
    @DataSource(value = "write")
    public Long updatePassWord(Long id, String encryptedPassword, String randomNum) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(id);
        customerInfo.setRandomNum(randomNum);
        customerInfo.setEncryptedPassword(encryptedPassword);
        return customerInfoDao.updateEntity(customerInfo);
    }

    @Override
    @DataSource(value = "read")
    public CustomerInfo getCustomerByTelephone(String telephone) {
        return customerInfoDao.getByTelephone(telephone);
    }

    /**
     * 根据农行app用户id 获取用户信息
     * 
     * @param abcaid 农行app用户id
     * @return
     */
    @Override
    @DataSource(value = "read")
    public CustomerInfo getCustomerByAbcaid(String abcaid) {
        return customerInfoDao.getByabcaId(abcaid);
    }

}
