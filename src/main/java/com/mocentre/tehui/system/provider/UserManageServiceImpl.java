/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.UserManageService;
import com.mocentre.tehui.backend.model.RoleInstance;
import com.mocentre.tehui.backend.model.UserInstance;
import com.mocentre.tehui.backend.param.UserParam;
import com.mocentre.tehui.backend.param.UserQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.mapper.RoleMapper;
import com.mocentre.tehui.system.mapper.UserMapper;
import com.mocentre.tehui.system.model.Role;
import com.mocentre.tehui.system.model.User;
import com.mocentre.tehui.system.service.IRoleService;
import com.mocentre.tehui.system.service.IUserRoleService;
import com.mocentre.tehui.system.service.IUserService;

/**
 * 类UserManageServiceImpl.java的实现描述：用户dubbo实现
 *
 * @author sz.gong 2016年11月4日 下午4:48:31
 */
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private IUserService     userService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService     roleService;

    @Override
    public ListJsonResult<UserInstance> queryPage(UserQueryParam userQueryParam) {

        String requestId = userQueryParam.getRequestId();
        ListJsonResult<UserInstance> result = new ListJsonResult<UserInstance>();
        result.setRequestId(requestId);
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderColumn", userQueryParam.getOrderColumn());
            params.put("orderBy", userQueryParam.getOrderBy());
            params.put("userName", userQueryParam.getUserName());
            params.put("phone", userQueryParam.getPhone());
            params.put("qq", userQueryParam.getQq());
            params.put("email", userQueryParam.getEmail());
            Requirement require = new Requirement(userQueryParam.getDraw(), userQueryParam.getStart(),
                    userQueryParam.getLength(), params);
            result = userService.queryUserPage(require, requestId);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<UserInstance> queryUserRole(Long id, String requestId) {

        PlainResult<UserInstance> result = new PlainResult<UserInstance>();
        result.setRequestId(requestId);
        try {
            if (id == null) {
                result.setErrorMessage("1000", "id不能为空");
            }
            if (result.isSuccess()) {
                List<Long> rids = userRoleService.queryRoleIdByUid(id);
                List<Role> roleList = roleService.queryEnableRoleList();
                List<RoleInstance> roleInsList = new ArrayList<RoleInstance>();
                User user = userService.queryUser(id);
                UserInstance userIns = UserMapper.toUserInstance(user, rids);
                for (Role role : roleList) {
                    RoleInstance roleIns = RoleMapper.toRoleInstance(role);
                    roleInsList.add(roleIns);
                }
                userIns.setRoleInsList(roleInsList);
                result.setData(userIns);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryUserRole", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult save(UserParam mUser) {

        BaseResult result = new BaseResult();
        result.setRequestId(mUser.getRequestId());
        try {
            Long id = mUser.getId();
            String userName = mUser.getUserName();
            String password = mUser.getPassword();
            if (StringUtils.isBlank(userName)) {
                result.setErrorMessage("1000", "用户名不能为空");
            }
            if (id == null && StringUtils.isBlank(password)) {
                result.setErrorMessage("1000", "密码不能为空");
            }
            if (result.isSuccess()) {
                List<Long> roles = mUser.getRoles();
                Long[] roleIds = null;
                boolean exist = userService.queryExistUser(userName, id);
                if (exist) {
                    result.setErrorMessage("1001", "用户已存在");
                    return result;
                }
                if (roles != null) {
                    roleIds = new Long[roles.size()];
                    roles.toArray(roleIds);
                }
                if (id != null) {
                    userService.updateUser(mUser);
                } else {
                    id = userService.saveUser(mUser);
                }
                userRoleService.saveUserRole(id, roleIds);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("save", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult updateBase(UserParam user) {
        BaseResult result = new BaseResult();
        result.setRequestId(user.getRequestId());
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("updateBase", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    public BaseResult delete(List<Long> idList, String requestId) {

        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        if (idList == null) {
            result.setErrorMessage("1000", "参数不能为空");
        }
        try {
            if (result.isSuccess()) {
                for (Long id : idList) {
                    userService.logicDelete(id);
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
