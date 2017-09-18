package com.mocentre.tehui.ps.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.ps.dao.ICustomerInfoDao;
import com.mocentre.tehui.ps.model.CustomerInfo;

/**
 * 买家用户基本信息 Created by 王雪莹 on 2016/11/22.
 */
@Repository
public class CustomerInfoDao extends BaseDao<CustomerInfo> implements ICustomerInfoDao {

    @Override
    public int delById(Long id) {
        return super.logicRemoveById(id);
    }

    @Override
    public int delById(List<Long> ids) {
        return super.delete("CustomerInfo_del_ids", ids);
    }

    @Override
    public CustomerInfo getByOpenid(String openid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("openid", openid);
        return super.queryUniquely(paramMap);
    }

    @Override
    public CustomerInfo getByTelephone(String telephone) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tel", telephone);
        return super.queryUniquely(paramMap);
    }

    @Override
    public CustomerInfo getByabcaId(String abcaid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("abcaid", abcaid);
        return super.queryUniquely(paramMap);
    }

    @Override
    public CustomerInfo getByUserName(String userName) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", userName);
        return super.queryUniquely(paramMap);
    }

}
