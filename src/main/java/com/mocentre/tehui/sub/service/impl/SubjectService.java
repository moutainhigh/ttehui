package com.mocentre.tehui.sub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.SubjectInstance;
import com.mocentre.tehui.backend.param.SubjectParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.dao.ISubjectDao;
import com.mocentre.tehui.sub.dao.ISubjectGoodsDao;
import com.mocentre.tehui.sub.mapper.SubjectMapper;
import com.mocentre.tehui.sub.model.Subject;
import com.mocentre.tehui.sub.service.ISubjectService;

/**
 * 专题接口实现 Created by yukaiji on 2016/12/02.
 */

@Component
public class SubjectService implements ISubjectService {

    @Autowired
    private ISubjectDao      subjectDao;
    @Autowired
    private ISubjectGoodsDao subjectGoodsDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<SubjectInstance> querySubjectPage(Requirement require) {
        ListJsonResult<SubjectInstance> result = new ListJsonResult<SubjectInstance>();
        ListJsonResult<Subject> pageInfo = subjectDao.queryDatatablesPage(require);
        List<Subject> subList = pageInfo.getData();
        List<SubjectInstance> subInsList = new ArrayList<SubjectInstance>();
        if (subList != null) {
            for (Subject sub : subList) {
                SubjectInstance subIns = SubjectMapper.toSubjectInstance(sub);
                subInsList.add(subIns);
            }
        }
        result.setData(subInsList);
        result.setDraw(pageInfo.getDraw());
        result.setRecordsFiltered(pageInfo.getRecordsFiltered());
        result.setRecordsTotal(pageInfo.getRecordsTotal());
        return result;
    }

    @Override
    @DataSource(value = "read")
    public List<Subject> getShowSubjectList(Long shopId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("shopId", shopId);
        paramMap.put("isShow", "1");
        return subjectDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public List<Subject> getShowSubjectList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isShow", "1");
        return subjectDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public Subject getSubjectById(Long id, Long shopId) {
        return subjectDao.getSubject(id, shopId);
    }

    @Override
    @DataSource(value = "read")
    public Subject getSubjectById(Long id) {
        return subjectDao.getById(id);
    }

    @Override
    @DataSource(value = "write")
    public Subject addSubject(SubjectParam subjectParam) {
        Subject subject = SubjectMapper.toSubject(subjectParam);
        subjectDao.saveEntity(subject);
        return subject;
    }

    @Override
    @DataSource(value = "write")
    public Long updateSubject(SubjectParam subjectParam) {
        Subject subject = SubjectMapper.toSubject(subjectParam);
        return subjectDao.updateEntity(subject);
    }

    @Override
    @DataSource(value = "write")
    public int show(Long id, String isShow) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("isShow", isShow);
        return subjectDao.show(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public int delById(Long id) {
        int count = subjectDao.logicRemoveById(id);
        subjectGoodsDao.logicRemoveBySubject(id);
        return count;
    }

}
