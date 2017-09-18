package com.mocentre.tehui.sub.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.model.SubjectTemplet;

/**
 * 专题模板接口. Created by yukaiji on 2016/12/02.
 */
public interface ISubjectTempletService {

    /**
     * 获取所有的模板
     * 
     * @return 所有的模板
     */
    List<SubjectTemplet> queryAll();

    /**
     * 获取所有的模板(分页查询)
     * 
     * @return 所有的模板
     */
    ListJsonResult<SubjectTemplet> querySubjectTempletPage(Requirement require);

    /**
     * 根据id获取模板
     * 
     * @param id
     */
    SubjectTemplet getSubjectTempletByid(Long id);

    /**
     * 根据code获取模板
     * 
     * @param code
     */
    SubjectTemplet getSubjectTempletByCode(String code);

    /**
     * 添加一个模板
     * 
     * @param subjectTemplet
     * @return id
     */
    Long addSubjectTemplet(SubjectTemplet subjectTemplet);

    /**
     * 修改一个模板
     * 
     * @param subjectTemplet
     * @return id
     */
    Long updateSubjectTemplet(SubjectTemplet subjectTemplet);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int delById(Long id);

}
