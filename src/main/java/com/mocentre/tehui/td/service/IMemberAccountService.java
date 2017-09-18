/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.td.model.MemberAccount;

/**
 * 第三方用户管理service 接口
 */
public interface IMemberAccountService {

    /**
     * 获取list
     * 
     * @return
     */
    ListJsonResult<MemberAccount> getPage(Requirement require);

    /**
     * 根据id获取
     * 
     * @return
     */
    MemberAccount getById(Long id);

    /**
     * 根据id删除
     * 
     * @return
     */
    int delById(Long id);

    /**
     * 保存第三方账户
     * 
     * @param memberAccount
     * @return
     */
    Long saveMemberAccount(MemberAccount memberAccount);

    /**
     * 更新第三方账户信息
     * 
     * @param memberAccount
     * @return
     */
    Long updateMemberAccount(MemberAccount memberAccount);

    /**
     * 根据appKey查询
     * 
     * @param appKey
     * @return
     */
    MemberAccount getMemberAccountByAppKey(String appKey);

    /**
     * 根据keymark查询
     * 
     * @param keymark
     * @return
     */
    MemberAccount getMemberAccountByKeymark(String keymark);

    /**
     * 更新开启状态
     * 
     * @param id
     * @param code
     * @return
     */
    void updateIsDeny(Long id, String code);

    /**
     * 通过keymark查询是否存在，如果id不为空，则不包含此id
     * 
     * @param keymark
     * @param id
     * @return
     */
    Boolean getMemberAccountExist(String keymark, Long id);

    /**
     * 缓存中查询
     * 
     * @param appKey
     * @return
     */
    MemberAccount getMemberAccountFromCache(String appKey);

}
