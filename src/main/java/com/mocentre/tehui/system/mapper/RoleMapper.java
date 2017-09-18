/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.mapper;

import com.mocentre.tehui.backend.model.RoleInstance;
import com.mocentre.tehui.system.model.Role;

/**
 * 类RoleMapper.java的实现描述：角色转换
 * 
 * @author sz.gong 2016年12月11日 下午7:16:11
 */
public class RoleMapper {

    public static RoleInstance toRoleInstance(Role role) {
        RoleInstance roleIns = new RoleInstance();
        roleIns.setGmtCreated(role.getGmtCreated());
        roleIns.setGmtModified(role.getGmtModified());
        roleIns.setId(role.getId());
        roleIns.setRoleName(role.getRoleName());
        roleIns.setStatus(role.getStatus());
        return roleIns;
    }

}
