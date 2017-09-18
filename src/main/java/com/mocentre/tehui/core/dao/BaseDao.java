package com.mocentre.tehui.core.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.model.BaseEntity;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.core.utils.GenericsUtils;

/**
 * 负责为单个Entity 提供CRUD操作的IBatis DAO基类. </p> 子类只要在类定义时指定所管理Entity的Class,
 * 即拥有对单个Entity对象的CRUD操作.
 * 
 * <pre>
 * public class UserDaoIbatisImp extends IBatisEntityDao&lt;User&gt; implement UserDao {
 * }
 * </pre>
 * 
 * @param <T>
 */
public abstract class BaseDao<T extends BaseEntity> extends MybatisGenericDao {

    /**
     * DAO所管理的Entity类型.
     */
    protected Class<T> entityClass;

    /**
     * 在构造函数中将泛型T.class赋给entityClass.
     */
    @SuppressWarnings("unchecked")
    public BaseDao() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 根据ID获取对象.
     */
    public T get(Serializable id) {
        return get(getEntityClass(), id);
    }

    /**
     * 根据ID删除对象.
     */
    public int removeById(Serializable id) {
        return removeById(getEntityClass(), id);
    }

    /**
     * 根据ID逻辑删除对象.
     */
    public int logicRemoveById(Serializable id) {
        return logicRemoveById(getEntityClass(), id);
    }

    /**
     * 获取全部对象.
     */
    public List<T> getAll() {
        return getAll(getEntityClass());
    }

    /**
     * 得到符合查询条件的记录数
     */
    public Integer getCount(Map<String, Object> paramMap) {
        return getCount(entityClass, paramMap);
    }

    /**
     * 查询jQuery Datatables分页列表（指定的sqlId=类名_list）
     * 
     * @param paramMap
     * @return
     */
    public ListJsonResult<T> queryDatatablesPage(Requirement require) {
        return queryDTPage(getEntityClass(), require);
    }

    /**
     * 查询jQuery Datatables分页列表
     * 
     * @param statement
     * @param require
     * @return
     */
    public ListJsonResult<T> queryDatatablesPage(String statement, Requirement require) {
        return queryDTPage(statement, require);
    }

    /**
     * 查询jQuery Datatables列表（指定的sqlId=类名_list）
     * 
     * @param paramMap
     * @return
     */
    public ListJsonResult<T> queryDatatablesList(Map<String, Object> paramMap) {
        return queryDTList(getEntityClass(), paramMap);
    }

    public ListJsonResult<T> queryDatatablesList(String statement, Map<String, Object> paramMap) {
        return queryDTList(statement, paramMap);
    }

    /**
     * 查询记录列表（指定的sqlId=类名_list）
     * 
     * @param paramMap
     * @return
     */
    public List<T> queryList(Map<String, Object> paramMap) {
        return query(getEntityClass(), paramMap);
    }

    public List<T> queryList(String statement, Map<String, Object> paramMap) {
        return query(statement, paramMap);
    }

    /**
     * 查询分页列表（指定的sqlId=类名_list）
     * 
     * @param paramMap 中包含对象各属性参数
     * @return
     */

    public PageInfo<T> queryPaged(Map<String, Object> paramMap) {
        return queryPaged(getEntityClass(), paramMap);
    }

    public Long saveEntity(T o) {
        o.setGmtCreated(new Date());
        o.setGmtModified(new Date());
        return super.save(o);
    }

    public Long updateEntity(T o) {
        o.setGmtModified(new Date());
        return super.update(o);
    }

    /**
     * 查询对象（指定的sqlId=类名_list）
     * 
     * @param paramMap
     * @return
     */
    public T queryUniquely(Map<String, Object> paramMap) {
        return super.queryUniquely(getEntityClass(), paramMap);
    }

    //    public List<T> query(Condition condition) {
    //        return query(getEntityClass(), condition);
    //    }

    //    public PageInfo<T> queryPaged(String statement, Condition condition) {
    //        Map<String, Object> parameterObject = new HashMap<String, Object>();
    //        parameterObject.putAll(condition.getMap());
    //        parameterObject.put("condition", condition);
    //        return queryPaged(statement, parameterObject);
    //    }
    //
    //    public PageInfo<T> queryPaged(Condition condition) {
    //        return queryPaged(entityClass.getSimpleName() + POSTFIX_SELECT, condition);
    //    }

    /**
     * 取得entityClass.
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }

}
