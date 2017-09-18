/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.td.dao.IMemberAccountDao;
import com.mocentre.tehui.td.model.MemberAccount;
import com.mocentre.tehui.td.service.IMemberAccountService;

/**
 * 第三方账户管理 Created by 王雪莹 on 2017/6/20.
 */
@Component
public class MemberAccountService implements IMemberAccountService {

    @Autowired
    private IMemberAccountDao memberAccountDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<MemberAccount> getPage(Requirement require) {
        ListJsonResult<MemberAccount> pageInfo = memberAccountDao.queryDatatablesPage(require);
        return pageInfo;
    }

    @Override
    @DataSource(value = "read")
    public MemberAccount getById(Long id) {
        return memberAccountDao.get(id);
    }

    @Override
    @DataSource(value = "write")
    public int delById(Long id) {
        return memberAccountDao.delById(id);
    }

    @Override
    @DataSource(value = "write")
    public Long saveMemberAccount(MemberAccount memberAccount) {
        return memberAccountDao.saveAndCache(memberAccount);
    }

    @Override
    @DataSource(value = "write")
    public Long updateMemberAccount(MemberAccount memberAccount) {
        return memberAccountDao.updateAndCache(memberAccount);
    }

    @Override
    @DataSource(value = "read")
    public MemberAccount getMemberAccountByAppKey(String appKey) {
        return memberAccountDao.getByAppKey(appKey);
    }

    @Override
    @DataSource(value = "read")
    public MemberAccount getMemberAccountByKeymark(String keymark) {
        return memberAccountDao.getByKeymark(keymark);
    }

    @Override
    @DataSource(value = "write")
    public void updateIsDeny(Long id, String code) {
        MemberAccount memberAccount = new MemberAccount();
        memberAccount.setId(id);
        if (StringUtils.equals("1", code)) {
            memberAccount.setIsDeny(1);
        } else if (StringUtils.equals("0", code)) {
            memberAccount.setIsDeny(0);
        }
        memberAccountDao.updateEntity(memberAccount);
    }

    @Override
    @DataSource(value = "read")
    public Boolean getMemberAccountExist(String keymark, Long id) {
        Integer count = memberAccountDao.getKeymarkCount(keymark, id);
        if (count == 0) {
            return false;
        }
        return true;
    }

    @Override
    @DataSource(value = "read")
    public MemberAccount getMemberAccountFromCache(String appKey) {
        return memberAccountDao.getFromCache(appKey);
    }

}
