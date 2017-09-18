/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.mapper;

import com.mocentre.tehui.backend.model.RuleInstance;
import com.mocentre.tehui.backend.param.RuleParam;
import com.mocentre.tehui.system.model.Rule;

/**
 * 类RuleMapper.java的实现描述：模块装换
 * 
 * @author sz.gong 2016年12月11日 下午7:47:21
 */
public class RuleMapper {

    public static RuleInstance toRuleInstance(Rule rule) {
        RuleInstance ruleIns = new RuleInstance();
        ruleIns.setId(rule.getId());
        ruleIns.setIcon(rule.getIcon());
        ruleIns.setIsShow(rule.getStatus());
        ruleIns.setOrderby(rule.getOrderby());
        ruleIns.setParentId(rule.getPid());
        ruleIns.setTips(rule.getTips());
        ruleIns.setTitle(rule.getTitle());
        ruleIns.setType(rule.getType());
        ruleIns.setUrl(rule.getUrl());
        ruleIns.setGmtCreated(rule.getGmtCreated());
        ruleIns.setGmtModified(rule.getGmtModified());
        return ruleIns;
    }

    public static Rule toRule(RuleParam ruleParam) {
        Rule rule = new Rule();
        rule.setId(ruleParam.getId());
        rule.setPid(ruleParam.getParentId());
        rule.setTitle(ruleParam.getTitle());
        rule.setType(ruleParam.getType());
        rule.setUrl(ruleParam.getUrl());
        rule.setIcon(ruleParam.getIcon());
        rule.setStatus(ruleParam.getIsShow());
        rule.setOrderby(ruleParam.getOrderby());
        rule.setTips(ruleParam.getTips());
        return rule;
    }

}
