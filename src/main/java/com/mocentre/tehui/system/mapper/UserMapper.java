/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.mapper;

import com.mocentre.tehui.backend.model.LoginUserInstance;
import com.mocentre.tehui.backend.model.RoleInstance;
import com.mocentre.tehui.backend.model.UserInstance;
import com.mocentre.tehui.backend.param.UserParam;
import com.mocentre.tehui.system.model.User;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * 类UserMapper.java的实现描述：后台用户转换
 * 
 * @author sz.gong 2016年12月11日 下午1:26:04
 */
public class UserMapper {

    public static LoginUserInstance toLoginUserInstance(User user) {
        LoginUserInstance userInstance = new LoginUserInstance();
        userInstance.setId(user.getId());
        userInstance.setHead(user.getHead());
        userInstance.setRealName(user.getRealName());
        userInstance.setUserName(user.getUserName());
        userInstance.setShopId(user.getShopId());
        return userInstance;
    }

    public static UserInstance toUserInstance(User user) {
        UserInstance userIns = new UserInstance();
        BeanCopier cp = BeanCopier.create(User.class, UserInstance.class, false);
        cp.copy(user, userIns, null);
        return userIns;
    }

    public static UserInstance toUserInstance(User user, List<Long> roleIds) {
        UserInstance userIns = new UserInstance();
        List<RoleInstance> roleInsList = new ArrayList<RoleInstance>();
        userIns.setBirthday(user.getBirthday());
        userIns.setEmail(user.getEmail());
        userIns.setGmtCreated(user.getGmtCreated());
        userIns.setGmtModified(user.getGmtModified());
        userIns.setHead(user.getHead());
        userIns.setId(user.getId());
        userIns.setPhone(user.getPhone());
        userIns.setQq(user.getQq());
        userIns.setRealName(user.getRealName());
        userIns.setSex(user.getSex());
        userIns.setShopId(user.getShopId());
        userIns.setUserName(user.getUserName());
        userIns.setRoles(roleIds);
        return userIns;
    }

    public static User toUser(UserParam userParam) {
        User user = new User();
        user.setUserName(userParam.getUserName());
        user.setBirthday(userParam.getBirthday());
        user.setEmail(userParam.getEmail());
        user.setHead(userParam.getHead());
        user.setId(userParam.getId());
        user.setPassword(userParam.getPassword());
        user.setPhone(userParam.getPhone());
        user.setQq(userParam.getQq());
        user.setRealName(userParam.getRealName());
        user.setSex(userParam.getSex());
        user.setShopId(userParam.getShopId());
        return user;
    }

}
