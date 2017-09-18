/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListResult;
import com.mocentre.common.PlainResult;
import com.mocentre.tehui.RuleManageService;
import com.mocentre.tehui.backend.model.RuleInstance;
import com.mocentre.tehui.backend.param.RuleParam;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.system.mapper.RuleMapper;
import com.mocentre.tehui.system.model.Rule;
import com.mocentre.tehui.system.service.IRuleService;

/**
 * 类RuleManageServiceImpl.java的实现描述：菜单接口实现
 * 
 * @author sz.gong 2016年11月8日 上午10:31:10
 */
public class RuleManageServiceImpl implements RuleManageService {

    @Autowired
    private IRuleService ruleService;

    @Override
    public ListResult<RuleInstance> queryList(String title, String requestId) {

        ListResult<RuleInstance> result = new ListResult<RuleInstance>();
        result.setRequestId(requestId);
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("title", title);
            result = ruleService.queryRuleList(paramMap);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryPage", e);
        }
        return result;
    }

    @Override
    public ListResult<RuleInstance> queryCascade(String requestId) {

        ListResult<RuleInstance> result = new ListResult<RuleInstance>();
        result.setRequestId(requestId);
        try {
            List<RuleInstance> ruleList = ruleService.queryRuleCascade();
            result.setData(ruleList);
        } catch (Exception e) {
            result.setErrorMessage("999", "系统异常");
            LoggerUtil.tehuiLog.error("queryRuleCascade", e);
        }
        return result;
    }

    @Override
    public BaseResult save(RuleParam ruleParam) {

        BaseResult result = new BaseResult();
        result.setRequestId(ruleParam.getRequestId());
        try {
            ruleService.saveOrUpdateRule(ruleParam);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("save", e);
        }
        return result;
    }

    @Override
    public BaseResult delete(List<Long> ids, String requestId) {

        BaseResult result = new BaseResult();
        result.setRequestId(requestId);
        try {
            for (Long id : ids) {
                ruleService.deleteRule(id);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("delete", e);
        }
        return result;
    }

    @Override
    public PlainResult<RuleInstance> queryById(Long id, String requestId) {

        PlainResult<RuleInstance> result = new PlainResult<RuleInstance>();
        result.setRequestId(requestId);
        try {
            Rule rule = ruleService.queryRuleByid(id);
            RuleInstance ruleIns = RuleMapper.toRuleInstance(rule);
            result.setData(ruleIns);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("queryById", e);
        }
        return result;
    }

}
