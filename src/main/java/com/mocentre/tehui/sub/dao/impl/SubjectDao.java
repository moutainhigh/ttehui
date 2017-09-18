package com.mocentre.tehui.sub.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.sub.dao.ISubjectDao;
import com.mocentre.tehui.sub.model.Subject;

/**
 * 专题模板数据库操作接口实现. Created by yukaiji on 2016/12/2.
 */
@Repository
public class SubjectDao extends BaseDao<Subject> implements ISubjectDao {

    @Override
    public Subject getSubject(Long id, Long shopId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("shopId", shopId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public int show(Map<String, Object> paramMap) {
        return super.update(entityClass.getSimpleName() + "_updateShow", paramMap);
    }

    @Override
    public Subject getById(Long id) {
        return super.get(id);
    }

}
