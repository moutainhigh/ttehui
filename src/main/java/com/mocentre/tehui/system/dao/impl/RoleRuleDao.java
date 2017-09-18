/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.system.dao.IRoleRuleDao;
import com.mocentre.tehui.system.model.RoleRule;

/**
 * 类RuleRoleDao.java的实现描述：角色菜单
 * 
 * @author sz.gong 2016年4月20日 下午4:06:03
 */
@Repository
public class RoleRuleDao extends BaseDao<RoleRule> implements IRoleRuleDao {

    @Override
    public List<RoleRule> queryRoleRule(Map<String, Object> paramMap) {
        return this.queryList(paramMap);
    }

    @Override
    public int deleteRoleRule(Long roleId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roleId", roleId);
        return super.delete("RoleRule_delete_role", paramMap);
    }

    @Override
    public void batchSaveRoleRule(List<RoleRule> list) {
        super.insert("RoleRule_insert_batch", list);
    }

}
