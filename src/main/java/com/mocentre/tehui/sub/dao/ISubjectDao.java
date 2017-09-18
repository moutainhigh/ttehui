package com.mocentre.tehui.sub.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.sub.model.Subject;

/**
 * 专题表数据库操作接口. Created by yukaiji on 2016/12/2.
 */
public interface ISubjectDao {

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    Subject getById(Long id);

    /**
     * 获取所有的专题(分页查询)
     * 
     * @return 所有的专题
     */
    ListJsonResult<Subject> queryDatatablesPage(Requirement require);

    /**
     * 根据id和店铺id获取专题
     * 
     * @param paramMap
     */
    Subject getSubject(Long id, Long shopId);

    List<Subject> queryList(Map<String, Object> paramMap);

    /**
     * 插入专题
     * 
     * @param subject
     */
    Long saveEntity(Subject subject);

    /**
     * 修改专题
     * 
     * @param subject
     */
    Long updateEntity(Subject subject);

    /**
     * 根据id删除
     * 
     * @param id
     */
    int logicRemoveById(Serializable id);

    /**
     * 改变展示状态
     * 
     * @param paramMap
     * @return
     */
    int show(Map<String, Object> paramMap);
}
