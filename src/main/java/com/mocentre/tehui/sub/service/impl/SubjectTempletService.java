package com.mocentre.tehui.sub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.dao.ISubjectTempletDao;
import com.mocentre.tehui.sub.model.SubjectTemplet;
import com.mocentre.tehui.sub.service.ISubjectTempletService;

/**
 * 专题模板接口实现 Created by yukaiji on 2016/12/02.
 */

@Component
public class SubjectTempletService implements ISubjectTempletService {

    @Autowired
    private ISubjectTempletDao subjectTempletDao;

    @Override
    @DataSource(value = "read")
    public List<SubjectTemplet> queryAll() {
        return subjectTempletDao.getAllSubjectTemplet();
    }

    @Override
    @DataSource(value = "read")
    public ListJsonResult<SubjectTemplet> querySubjectTempletPage(Requirement require) {
        return subjectTempletDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public SubjectTemplet getSubjectTempletByid(Long id) {
        return subjectTempletDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public SubjectTemplet getSubjectTempletByCode(String code) {
        return subjectTempletDao.getByCode(code);
    }

    @Override
    @DataSource(value = "write")
    public Long addSubjectTemplet(SubjectTemplet subjectTemplet) {
        return subjectTempletDao.saveEntity(subjectTemplet);
    }

    @Override
    @DataSource(value = "write")
    public Long updateSubjectTemplet(SubjectTemplet subjectTemplet) {
        return subjectTempletDao.updateEntity(subjectTemplet);
    }

    @Override
    @DataSource(value = "write")
    public int delById(Long id) {
        return subjectTempletDao.logicRemoveById(id);
    }

}
