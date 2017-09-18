package com.mocentre.tehui.sub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.sub.dao.ISubjectTempletDao;
import com.mocentre.tehui.sub.model.SubjectTemplet;

/**
 * 专题模板数据库操作接口实现. Created by yukaiji on 2016/12/2.
 */
@Repository
public class SubjectTempletDao extends BaseDao<SubjectTemplet> implements ISubjectTempletDao {

    @Override
    public SubjectTemplet getByCode(String code) {
        return super.queryUniquely(entityClass.getSimpleName() + "_getByCode", code);
    }

    @Override
    public List<SubjectTemplet> getAllSubjectTemplet() {
        return super.getList(entityClass.getSimpleName() + "_getAll");
    }

}
