package com.mocentre.tehui.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.system.dao.IRuleDao;
import com.mocentre.tehui.system.model.Rule;

/**
 * 类RuleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:49:16
 */
@Repository
public class RuleDao extends BaseDao<Rule> implements IRuleDao {

    @Override
    public List<Rule> queryRuleAll() {
        return super.getAll();
    }

    @Override
    public List<Rule> queryRule(Map<String, Object> paramMap) {
        return super.queryList(paramMap);
    }

    @Override
    public Rule queryByid(Long id) {
        return super.get(id);
    }

    @Override
    public Long saveRule(Rule rule) {
        return super.save(rule);
    }

    @Override
    public void updateRule(Rule rule) {
        super.update(rule);
    }

    @Override
    public int logicDelete(Long id) {
        return super.logicRemoveById(id);
    }

}
