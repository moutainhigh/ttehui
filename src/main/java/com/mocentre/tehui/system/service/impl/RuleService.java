package com.mocentre.tehui.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mocentre.common.ListResult;
import com.mocentre.tehui.backend.model.RuleInstance;
import com.mocentre.tehui.backend.param.RuleParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.system.dao.IRuleDao;
import com.mocentre.tehui.system.mapper.RuleMapper;
import com.mocentre.tehui.system.model.Rule;
import com.mocentre.tehui.system.service.IRuleService;

/**
 * 类RuleService.java的实现描述：模块service
 * 
 * @author sz.gong 2016年4月20日 下午3:50:30
 */
@Component
public class RuleService implements IRuleService {

    @Autowired
    private IRuleDao ruleDao;

    @Override
    @DataSource(value = "read")
    public ListResult<RuleInstance> queryRuleList(Map<String, Object> paramMap) {
        ListResult<RuleInstance> resList = new ListResult<RuleInstance>();
        List<RuleInstance> ruleInsList = new ArrayList<RuleInstance>();
        List<Rule> allList = ruleDao.queryRule(paramMap);
        List<Rule> childList = this.getChildrenTreeList(allList, "0", 0);
        for (int i = 0; i < childList.size(); i++) {
            Rule rule = childList.get(i);
            RuleInstance ruleIns = RuleMapper.toRuleInstance(rule);
            ruleInsList.add(ruleIns);
        }
        if (childList != null && childList.size() > 0) {
            resList.setData(ruleInsList);
        } else {
            resList.setData(ruleInsList);
        }
        return resList;
    }

    @Override
    @DataSource(value = "read")
    public List<Rule> queryRuleList() {
        List<Rule> ruleList = ruleDao.queryRuleAll();
        return ruleList;
    }

    /**
     * 查询所有级联列表
     * 
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<RuleInstance> queryRuleCascade() {
        List<RuleInstance> ruleInsList = new ArrayList<RuleInstance>();
        List<Rule> list = ruleDao.queryRuleAll();
        List<Rule> ruleList = getChildrenTreeList(list, "0", 0);
        for (int i = 0; i < ruleList.size(); i++) {
            Rule rule = ruleList.get(i);
            RuleInstance ruleIns = RuleMapper.toRuleInstance(rule);
            ruleInsList.add(ruleIns);
        }
        return ruleInsList;
    }

    @Override
    @DataSource(value = "read")
    public List<Rule> queryRuleByIds(List<Long> idList) {
        Assert.notNull(idList);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ids", idList);
        paramMap.put("status", 1);
        List<Rule> ruleList = ruleDao.queryRule(paramMap);
        return ruleList;
    }

    /**
     * 查询所有父子关系列表的树
     * 
     * @return
     */
    @DataSource(value = "read")
    public List<Rule> getRuleTreeList() {
        List<Rule> list = ruleDao.queryRuleAll();
        return this.getRuleTree(list);
    }

    /*
     * 查询列表树
     */
    private List<Rule> getRuleTree(List<Rule> list) {

        List<Rule> parents = new ArrayList<Rule>();
        List<Rule> others = new ArrayList<Rule>();
        for (Rule rule : list) {
            if (rule.getPid() == 0) {
                rule.setChildren(new ArrayList<Rule>());
                parents.add(rule);
            } else {
                others.add(rule);
            }
        }
        this.buildTree(parents, others);
        return parents;
    }

    private void buildTree(List<Rule> parents, List<Rule> others) {

        List<Rule> record = new ArrayList<Rule>();
        for (Iterator<Rule> it = parents.iterator(); it.hasNext();) {
            Rule vi = it.next();
            if (vi.getId() != null) {
                for (Iterator<Rule> otherIt = others.iterator(); otherIt.hasNext();) {
                    Rule inVi = otherIt.next();
                    if (vi.getId().longValue() == inVi.getPid().longValue()) {
                        if (null == vi.getChildren()) {
                            vi.setChildren(new ArrayList<Rule>());
                        }
                        vi.getChildren().add(inVi);
                        record.add(inVi);
                        otherIt.remove();
                    }
                }
            }
        }
        if (others.size() == 0) {
            return;
        } else {
            buildTree(record, others);
        }
    }

    /*
     * 查询父节点下的所有子节点
     */
    private List<Rule> getChildrenTreeList(List<Rule> list, String fid, int lvl) {
        List<Rule> result = new ArrayList<Rule>();
        List<Rule> newlist = new Vector<Rule>();
        for (int i = 0; i < list.size(); i++) {
            Rule rule = list.get(i);
            String allfid = rule.getPid().toString();
            if (allfid.equals(fid)) {
                if (rule.getTitle() != null && !"".equals(rule.getTitle())) {
                    newlist.add(list.get(i));
                }
            }
        }
        for (int f = 0; f < newlist.size(); f++) {
            Rule rule = newlist.get(f);
            String name = rule.getTitle().toString();
            String id = rule.getId().toString();
            String pagenbsp = "";
            for (int l = 0; l < lvl; l++) {
                pagenbsp = pagenbsp + "-";
            }
            String pagename = pagenbsp + name;
            rule.setTitle(pagename);
            result.add(rule);
            result.addAll(getChildrenTreeList(list, id, lvl + 1));
        }
        return result;
    }

    @Override
    @DataSource(value = "read")
    public Rule queryRuleByid(Long id) {
        Rule rule = ruleDao.queryByid(id);
        return rule;
    }

    @Override
    @DataSource(value = "write")
    public void saveOrUpdateRule(RuleParam ruleParam) {
        Rule rule = RuleMapper.toRule(ruleParam);
        if (rule.getId() == null) {
            ruleDao.saveRule(rule);
        } else {
            ruleDao.updateRule(rule);
        }
    }

    @Override
    @DataSource(value = "write")
    public int deleteRule(Long id) {
        return ruleDao.logicDelete(id);
    }

}
