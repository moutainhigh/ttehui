/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.service;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListResult;
import com.mocentre.tehui.backend.model.RuleInstance;
import com.mocentre.tehui.backend.param.RuleParam;
import com.mocentre.tehui.system.model.Rule;

/**
 * 类IRuleService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午3:50:12
 */
public interface IRuleService {

    ListResult<RuleInstance> queryRuleList(Map<String, Object> paramMap);

    List<Rule> queryRuleList();

    List<RuleInstance> queryRuleCascade();

    Rule queryRuleByid(Long id);

    void saveOrUpdateRule(RuleParam ruleParam);

    int deleteRule(Long id);

    /**
     * 通过id集合，查询列表
     * 
     * @param idList
     * @return
     */
    List<Rule> queryRuleByIds(List<Long> idList);

}
