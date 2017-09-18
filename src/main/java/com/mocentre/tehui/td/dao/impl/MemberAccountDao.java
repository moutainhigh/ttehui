/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.td.dao.IMemberAccountDao;
import com.mocentre.tehui.td.model.MemberAccount;

/**
 * 第三方账户管理 Created by 王雪莹 on 2017/6/20.
 */
@Repository
public class MemberAccountDao extends BaseRedisDao<MemberAccount, String, MemberAccount> implements IMemberAccountDao {

    @Override
    public Long saveAndCache(MemberAccount mebAccount) {
        String appKey = mebAccount.getAppKey();
        String appSecret = mebAccount.getAppSecret();
        if (StringUtils.isBlank(appKey) || StringUtils.isBlank(appSecret)) {
            return 0l;
        }
        Long count = super.saveEntity(mebAccount);
        if (count > 0) {
            this.mebAccountToCache(appKey, mebAccount);
        }
        return count;
    }

    @Override
    public Long updateAndCache(MemberAccount mebAccount) {
        mebAccount.setAppKey(null);
        mebAccount.setAppSecret(null);
        Long count = super.updateEntity(mebAccount);
        if (count > 0) {
            Long id = mebAccount.getId();
            MemberAccount oldMebAccount = super.get(id);
            if (oldMebAccount != null && StringUtils.isNotBlank(oldMebAccount.getAppKey())) {
                mebAccount.setAppKey(oldMebAccount.getAppKey());
                mebAccount.setAppSecret(oldMebAccount.getAppSecret());
                this.mebAccountToCache(oldMebAccount.getAppKey(), mebAccount);
            }
        }
        return count;
    }

    @Override
    public List<MemberAccount> getList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return super.queryList(paramMap);
    }

    @Override
    public int delById(Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return super.delete("MemberAccount_delete", paramMap);
    }

    @Override
    public MemberAccount getByAppKey(String appKey) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("appKey", appKey);
        return super.queryUniquely(paramMap);
    }

    @Override
    public MemberAccount getByKeymark(String keymark) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("keymark", keymark);
        return super.queryUniquely(paramMap);
    }

    @Override
    public MemberAccount getIsDeny() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isDeny", "1");
        return super.queryUniquely(paramMap);
    }

    @Override
    public Integer getKeymarkCount(String keymark, Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("keymark", keymark);
        paramMap.put("diffId", id);
        return super.getCount(paramMap);
    }

    @Override
    public MemberAccount getFromCache(String appKey) {
        String key = RedisKeyConstant.MEMBER_ACCOUNT + ":" + appKey;
        MemberAccount mebAccount = super.getCacheValue(key);
        if (mebAccount == null) {
            mebAccount = this.getByAppKey(appKey);
            if (mebAccount == null) {
                return null;
            }
            this.mebAccountToCache(appKey, mebAccount);
        }
        return mebAccount;
    }

    private void mebAccountToCache(String appKey, MemberAccount mebAccount) {
        String key = RedisKeyConstant.MEMBER_ACCOUNT + ":" + appKey;
        super.cacheValue(key, mebAccount);
    }

}
