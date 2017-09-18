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

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.RoleManageService;
import com.mocentre.tehui.backend.model.RoleInstance;
import com.mocentre.tehui.backend.model.RuleInstance;
import com.mocentre.tehui.backend.param.RoleParam;
import com.mocentre.tehui.backend.param.RoleQueryParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.mapper.RoleMapper;
import com.mocentre.tehui.system.mapper.RuleMapper;
import com.mocentre.tehui.system.model.Role;
import com.mocentre.tehui.system.model.Rule;
import com.mocentre.tehui.system.service.IRoleRuleService;
import com.mocentre.tehui.system.service.IRoleService;
import com.mocentre.tehui.system.service.IRuleService;

/**
 * 类RoleManageServiceImpl.java的实现描述：角色实现
 * 
 * @author sz.gong 2016年11月8日 下午5:07:59
 */
public class RoleManageServiceImpl implements RoleManageService {

    @Autowired
    private IRoleService     roleService;
    @Autowired
    private IRuleService     ruleService;
    @Autowired
    private IRoleRuleService roleRuleService;

    @Override
    public ListJsonResult<RoleInstance> queryPage(RoleQueryParam roleQueryParam) {

        ListJsonResult<RoleInstance> result = new ListJsonResult<RoleInstance>();
        result.setRequestId(roleQueryParam.getRequestId());
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderColumn", roleQueryParam.getOrderColumn());
            params.put("orderBy", roleQueryParam.getOrderBy());
            params.put("roleName", roleQueryParam.getRoleName());
            Requirement require = new Requirement(roleQueryParam.getDraw(), roleQueryParam.getStart(),
                    roleQueryParam.getLength(), params);
            result = roleService.queryRolePage(require);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<RoleInstance> queryEnableRole(String requestId) {

        ListResult<RoleInstance> result = new ListResult<RoleInstance>();
        result.setRequestId(requestId);
        try {
            List<RoleInstance> roleInsList = new ArrayList<RoleInstance>();
            List<Role> roleList = roleService.queryEnableRoleList();
            for (int i = 0; i < roleList.size(); i++) {
                Role role = roleList.get(i);
                RoleInstance roleIns = RoleMapper.toRoleInstance(role);
                roleInsList.add(roleIns);
            }
            result.setData(roleInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryEnableRole", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public ListResult<RuleInstance> preAdd(String requestId) {

        ListResult<RuleInstance> result = new ListResult<RuleInstance>();
        result.setRequestId(requestId);
        try {
            List<RuleInstance> ruleInsList = new ArrayList<RuleInstance>();
            List<Rule> ruleList = ruleService.queryRuleList();
            for (int i = 0; i < ruleList.size(); i++) {
                Rule rule = ruleList.get(i);
                RuleInstance ruleIns = RuleMapper.toRuleInstance(rule);
                ruleInsList.add(ruleIns);
            }
            result.setData(ruleInsList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryEnableRole", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public PlainResult<RoleInstance> preEdit(Long roleId, String requestId) {

        PlainResult<RoleInstance> result = new PlainResult<RoleInstance>();
        result.setRequestId(requestId);
        try {
            List<Long> roleList = new ArrayList<Long>();
            roleList.add(roleId);
            List<Long> ruleIdList = roleRuleService.queryRulesByRid(roleList);
            List<Rule> ruleList = ruleService.queryRuleList();
            List<RuleInstance> ruleInsList = new ArrayList<RuleInstance>();
            for (int i = 0; i < ruleList.size(); i++) {
                Rule rule = ruleList.get(i);
                RuleInstance ruleIns = RuleMapper.toRuleInstance(rule);
                ruleInsList.add(ruleIns);
            }
            Role role = roleService.queryRoleById(roleId);
            RoleInstance roleIns = RoleMapper.toRoleInstance(role);
            roleIns.setRuleIds(ruleIdList);
            roleIns.setRuleList(ruleInsList);
            result.setData(roleIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("preEdit", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult save(RoleParam roleParam) {

        BaseResult result = new BaseResult();
        result.setRequestId(roleParam.getRequestId());
        try {
            Role role = new Role();
            role.setId(roleParam.getId());
            role.setRoleName(roleParam.getRoleName());
            role.setStatus(roleParam.getStatus());
            String roleName = role.getRoleName();
            boolean exist = roleService.queryExistRole(roleName, role.getId());
            if (exist) {
                result.setErrorMessage("1001", "角色已存在");
                return result;
            }
            if (role.getId() == null) {
                roleService.saveRole(role);
            } else {
                roleService.updateRole(role);
            }
            Long roleId = role.getId();
            List<Long> ruleIdList = roleParam.getRuleIds();
            if (roleId != null && ruleIdList != null) {
                Long[] ruleIds = new Long[ruleIdList.size()];
                ruleIdList.toArray(ruleIds);
                roleRuleService.saveRoleRule(roleId, ruleIds);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("save", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult assignRule(RoleParam roleParam) {

        BaseResult result = new BaseResult();
        result.setRequestId(roleParam.getRequestId());
        try {
            Long roleId = roleParam.getId();
            List<Long> ruleIdList = roleParam.getRuleIds();
            if (roleId != null && ruleIdList != null) {
                Long[] ruleIds = new Long[ruleIdList.size()];
                ruleIdList.toArray(ruleIds);
                roleRuleService.saveRoleRule(roleId, ruleIds);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("save", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

    @Override
    public BaseResult delete(List<Long> idList, String requestId) {

        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            if (idList == null) {
                result.setErrorMessage("1000", "参数不能为空");
            }
            if (result.isSuccess()) {
                roleService.deleteRole(idList);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
            result.setErrorMessage("999", "系统错误");
        }
        return result;
    }

}
