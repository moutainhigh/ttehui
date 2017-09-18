package com.mocentre.tehui.sub.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.model.SubjectTemplet;

/**
 * 专题模板数据库操作接口. Created by yukaiji on 2016/12/2.
 */
public interface ISubjectTempletDao {

    /**
     * 获取所有的模板
     * 
     * @return 所有的模板
     */
    List<SubjectTemplet> getAllSubjectTemplet();

    /**
     * 获取所有的模板(分页查询)
     * 
     * @return 所有的模板
     */
    ListJsonResult<SubjectTemplet> queryDatatablesPage(Requirement require);

    /**
     * 根据条件查询
     * 
     * @param paramMap
     * @return
     */
    List<SubjectTemplet> queryList(Map<String, Object> paramMap);

    /**
     * 根据id获取模板
     * 
     * @param id
     */
    SubjectTemplet get(Serializable id);

    /**
     * 根据code获取模板
     * 
     * @param code
     */
    SubjectTemplet getByCode(String code);

    /**
     * 添加一个模板
     * 
     * @param subjectTemplet
     * @return id
     */
    Long saveEntity(SubjectTemplet subjectTemplet);

    /**
     * 修改一个模板
     * 
     * @param subjectTemplet
     * @return id
     */
    Long updateEntity(SubjectTemplet subjectTemplet);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int logicRemoveById(Serializable id);

}
