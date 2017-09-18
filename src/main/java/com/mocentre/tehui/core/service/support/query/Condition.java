package com.mocentre.tehui.core.service.support.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mocentre.tehui.core.model.BaseEntity;

public class Condition {

    //    protected String              orderByColumn;
    //    protected String              orderByClause;

    protected boolean             distinct;

    // 分页查询
    protected int                 start;
    protected int                 limit;

    protected List<Criteria>      oredCriteria;
    protected Map<String, Object> map = new HashMap<String, Object>();

    public Condition() {
        this(new HashMap<String, Object>());
    }

    public Condition(Map<String, Object> map) {
        this.map = map;
        oredCriteria = new ArrayList<Criteria>();
    }

    public Condition(Map<String, Object> map, String orderByColumn, String orderByClause) {
        this.map = map;
        oredCriteria = new ArrayList<Criteria>();
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or(Class<? extends BaseEntity> entityClass) {
        Criteria criteria = createCriteriaInternal(entityClass);
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria(Class<? extends BaseEntity> entityClass) {
        Criteria criteria = createCriteriaInternal(entityClass);
        oredCriteria.add(criteria);
        return criteria;
    }

    protected Criteria createCriteriaInternal(Class<? extends BaseEntity> entityClass) {
        Criteria criteria = new Criteria(entityClass);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        //orderByClause = null;
        distinct = false;
    }

    //    public void setOrderByClause(String orderByClause) {
    //        this.orderByClause = orderByClause;
    //    }
    //
    //    public String getOrderByClause() {
    //        return orderByClause;
    //    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Object get(String key) {
        return this.map.get(key);
    }

    public void put(String key, String value) {
        this.map.put(key, value);
    }

    public boolean isValid() {
        if (!oredCriteria.isEmpty()) {
            for (Criteria c : oredCriteria) {
                if (c.isValid())
                    return true;
            }
        }
        return false;
    }

    //    public String getOrderByColumn() {
    //        return orderByColumn;
    //    }
    //
    //    public void setOrderByColumn(String orderByColumn) {
    //        this.orderByColumn = orderByColumn;
    //    }

}
