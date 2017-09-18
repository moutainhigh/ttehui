/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.UserInstance;
import com.mocentre.tehui.backend.param.UserParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.model.User;

/**
 * 类IUserService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午3:33:14
 */
public interface IUserService {

    ListJsonResult<UserInstance> queryUserPage(Requirement require, String requestId);

    User queryUser(Long id);

    Long saveUser(UserParam mUser);

    void updateUser(UserParam mUser);

    List<User> queryLoginVerify(String name, String password);

    boolean queryExistUser(String uname, Long id);

    void logicDelete(Long id);

}
