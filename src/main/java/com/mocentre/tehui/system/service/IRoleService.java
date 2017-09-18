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
import com.mocentre.tehui.backend.model.RoleInstance;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.model.Role;

/**
 * 类IRoleService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午3:42:01
 */
public interface IRoleService {

    ListJsonResult<RoleInstance> queryRolePage(Requirement require);

    List<Role> queryEnableRoleList();

    boolean queryExistRole(String roleName, Long id);

    Role queryRoleById(Long id);

    Long updateRole(Role role);

    void saveRole(Role role);

    void deleteRole(List<Long> roleIdList);
}
