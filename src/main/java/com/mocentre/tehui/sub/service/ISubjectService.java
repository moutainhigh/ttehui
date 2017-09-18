package com.mocentre.tehui.sub.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.SubjectInstance;
import com.mocentre.tehui.backend.param.SubjectParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.model.Subject;

/**
 * 专题接口. Created by yukaiji on 2016/12/02.
 */
public interface ISubjectService {

    /**
     * 获取所有的专题(分页查询)
     * 
     * @return 所有的专题
     */
    ListJsonResult<SubjectInstance> querySubjectPage(Requirement require);

    /**
     * 根据shopId获取显示的专题
     * 
     * @return
     */
    List<Subject> getShowSubjectList(Long shopId);

    /**
     * 查询显示的专题
     * 
     * @return
     */
    List<Subject> getShowSubjectList();

    /**
     * 根据id和店铺id获取专题
     * 
     * @param id, shopId
     */
    Subject getSubjectById(Long id, Long shopId);

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    Subject getSubjectById(Long id);

    /**
     * 添加一个专题
     * 
     * @param Subject
     * @return id
     */
    Subject addSubject(SubjectParam subjectParam);

    /**
     * 修改一个专题
     * 
     * @param Subject
     * @return id
     */
    Long updateSubject(SubjectParam subjectParam);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int delById(Long id);

    /**
     * 改变展示状态
     * 
     * @param id
     * @param isShow
     * @return
     */
    int show(Long id, String isShow);

}
