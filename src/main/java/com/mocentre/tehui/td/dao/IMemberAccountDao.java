/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.dao;

import java.io.Serializable;
import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.td.model.MemberAccount;

/**
 * 第三方账户管理 Created by 王雪莹 on 2017/6/20.
 */
public interface IMemberAccountDao {

    /**
     * 保存并缓存
     * 
     * @param mebAccount
     * @return
     */
    Long saveAndCache(MemberAccount mebAccount);

    /**
     * 更新并缓存
     * 
     * @param mebAccount
     * @return
     */
    Long updateAndCache(MemberAccount mebAccount);

    /**
     * 获取全部第三方账户
     * 
     * @return
     */
    List<MemberAccount> getAll();

    /**
     * 保存第三方账户
     * 
     * @return
     */
    Long saveEntity(MemberAccount memberAccount);

    /**
     * 根据参数获取list
     * 
     * @return
     */
    List<MemberAccount> getList();

    /**
     * 根据id获取
     * 
     * @return
     */
    MemberAccount get(Serializable id);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int delById(Long id);

    /**
     * 更新
     * 
     * @param memberAccount
     * @return
     */
    Long updateEntity(MemberAccount memberAccount);

    /**
     * 分页查询
     * 
     * @param require
     * @return
     */
    ListJsonResult<MemberAccount> queryDatatablesPage(Requirement require);

    /**
     * 根据appKey查询
     * 
     * @param appKey
     * @return
     */
    MemberAccount getByAppKey(String appKey);

    /**
     * 根据keymark查询
     * 
     * @param keymark
     * @return
     */
    MemberAccount getByKeymark(String keymark);

    /**
     * 获取启用的账户信息
     * 
     * @return
     */
    MemberAccount getIsDeny();

    /**
     * 通过keymark，查询不是此id的数量
     * 
     * @param keymark
     * @param id
     * @return
     */
    Integer getKeymarkCount(String keymark, Long id);

    /**
     * 缓存中查询
     * 
     * @param appKey
     * @return
     */
    MemberAccount getFromCache(String appKey);

}
