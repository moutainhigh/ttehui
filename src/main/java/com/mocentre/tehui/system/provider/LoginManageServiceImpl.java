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

import com.mocentre.common.PlainResult;
import com.mocentre.tehui.LoginManageService;
import com.mocentre.tehui.backend.model.LoginUserInstance;
import com.mocentre.tehui.backend.model.RuleInstance;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.system.mapper.RuleMapper;
import com.mocentre.tehui.system.mapper.UserMapper;
import com.mocentre.tehui.system.model.Rule;
import com.mocentre.tehui.system.model.User;
import com.mocentre.tehui.system.service.IRoleRuleService;
import com.mocentre.tehui.system.service.IRuleService;
import com.mocentre.tehui.system.service.IUserRoleService;
import com.mocentre.tehui.system.service.IUserService;

/**
 * 类LoginManageServiceImpl.java的实现描述：用户登入
 * 
 * @author sz.gong 2016年11月7日 下午3:35:22
 */
public class LoginManageServiceImpl implements LoginManageService {

    @Autowired
    private IUserService     userService;
    @Autowired
    private IRuleService     ruleService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleRuleService roleRuleService;

    @Override
    public PlainResult<LoginUserInstance> login(String username, String password, String requestId) {

        PlainResult<LoginUserInstance> result = new PlainResult<LoginUserInstance>();
        result.setRequestId(requestId);
        try {
            if (StringUtils.isBlank(username)) {
                result.setErrorMessage("1000", "username不能为空");
            }
            if (StringUtils.isBlank(password)) {
                result.setErrorMessage("1000", "password不能为空");
            }
            if (result.isSuccess()) {
                List<User> list = userService.queryLoginVerify(username, password);
                if (list == null || list.size() > 1 || list.size() == 0) {
                    result.setErrorMessage("1001", "用户名或密码错误");
                } else {
                    List<RuleInstance> ruleInsList = new ArrayList<RuleInstance>();
                    User user = list.get(0);
                    if ("admin".equals(user.getUserName())) {
                        List<Rule> ruleList = ruleService.queryRuleList();
                        for (int i = 0; i < ruleList.size(); i++) {
                            Rule rule = ruleList.get(i);
                            RuleInstance ruleIns = RuleMapper.toRuleInstance(rule);
                            ruleInsList.add(ruleIns);
                        }
                    } else {
                        List<Long> roleIdList = userRoleService.queryRoleIdByUid(user.getId());
                        List<Long> ruleIdList = roleRuleService.queryRulesByRid(roleIdList);
                        List<Rule> ruleList = ruleService.queryRuleByIds(ruleIdList);
                        for (int i = 0; i < ruleList.size(); i++) {
                            Rule rule = ruleList.get(i);
                            RuleInstance ruleIns = RuleMapper.toRuleInstance(rule);
                            ruleInsList.add(ruleIns);
                        }
                    }
                    Map<String, Object> resMap = new HashMap<String, Object>();
                    resMap.put("ruleList", ruleInsList);
                    resMap.put("user", user);
                    LoginUserInstance loginUserIns = UserMapper.toLoginUserInstance(user);
                    loginUserIns.setRuleModelList(ruleInsList);
                    result.setData(loginUserIns);
                }
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("login", e);
        }
        return result;
    }

}
