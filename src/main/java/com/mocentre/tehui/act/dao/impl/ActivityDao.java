package com.mocentre.tehui.act.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.act.dao.IActivityDao;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 活动数据库操作接口实现. Created by yukaiji on 2016/1/16.
 */
@Repository
public class ActivityDao extends BaseDao<Activity> implements IActivityDao {

    @Override
    public Activity getActivity(Map<String, Object> paramMap) {
        return super.queryUniquely(entityClass.getSimpleName() + POSTFIX_GET, paramMap);
    }

    @Override
    public List<Activity> getList(Map<String, Object> paramMap) {
        return super.queryList(paramMap);
    }

    @Override
    public int show(Map<String, Object> paramMap) {
        return super.update(entityClass.getSimpleName() + "_updateShow", paramMap);
    }

}
