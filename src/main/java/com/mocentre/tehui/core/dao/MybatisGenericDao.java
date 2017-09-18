package com.mocentre.tehui.core.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.model.BaseEntity;
import com.mocentre.tehui.core.service.support.paging.PageHelper;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * MyBatis Dao的泛型基类.
 * 继承于Spring的SqlMapClientDaoSupport,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 */
@SuppressWarnings("unchecked")
public abstract class MybatisGenericDao {

    //private static final Logger LOGGER                   = Logger.getLogger(MybatisGenericDao.class);

    public static final String POSTFIX_INSERT           = "_insert";
    public static final String POSTFIX_UPDATE           = "_update";
    public static final String POSTFIX_UPDATE_SELECTIVE = "_updateSelective";
    public static final String POSTFIX_LOGIC_DELETE     = "_logicDelete";
    public static final String POSTFIX_LOGIC_BYKEY      = "_logicDeleteByPrimaryKey";
    public static final String POSTFIX_DELETE           = "_delete";
    public static final String POSTFIX_DELETE_BYKEY     = "_deleteByPrimaryKey";
    public static final String POSTFIX_GET              = "_get";
    public static final String POSTFIX_SELECT           = "_list";
    public static final String POSTFIX_COUNT            = "_count";

    @Autowired
    private SqlSession         sqlSession;

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    // CRUD-------------------------------------------------------------------------
    /**
     * 根据ID获取对象. 如果对象不存在 ，则抛出ObjectRetrievalFailureException异常.
     * 
     * @throws
     */
    public <T> T get(Class<T> entityClass, Serializable id) {
        Assert.notNull(id);
        T o = (T) sqlSession.selectOne(entityClass.getSimpleName() + POSTFIX_GET, id);
        return o;
    }

    /**
     * 获取全部对象.
     */
    public <T> List<T> getAll(Class<T> entityClass) {
        return sqlSession.selectList(entityClass.getSimpleName() + POSTFIX_SELECT);
    }

    /**
     * 插入对象
     * 
     * @param o
     * @return
     */
    public Long save(BaseEntity o) {
        Assert.notNull(o);
        sqlSession.insert(o.getClass().getSimpleName() + POSTFIX_INSERT, o);
        return o.getId();
    }

    /**
     * 更新对象
     * 
     * @param o
     * @return
     */
    public Long update(BaseEntity o) {
        Assert.notNull(o);
        sqlSession.update(o.getClass().getSimpleName() + POSTFIX_UPDATE, o);
        return o.getId();
    }

    /**
     * 逻辑删除对象
     * 
     * @param entityClass
     * @param o
     * @return
     */
    public <T> int logicRemove(Class<T> entityClass, BaseEntity o) {
        Assert.notNull(o);
        return sqlSession.update(entityClass.getSimpleName() + POSTFIX_LOGIC_DELETE, o);
    }

    /**
     * 逻辑删除对象
     * 
     * @param entityClass
     * @param id
     * @return
     */
    public <T> int logicRemoveById(Class<T> entityClass, Serializable id) {
        Assert.notNull(id);
        return sqlSession.update(entityClass.getSimpleName() + POSTFIX_LOGIC_BYKEY, id);
    }

    /**
     * 逻辑删除对象
     * 
     * @param o
     * @return
     */
    public <T> int logicRemove(BaseEntity o) {
        Assert.notNull(o);
        return sqlSession.update(o.getClass().getSimpleName() + POSTFIX_LOGIC_DELETE, o);
    }

    /**
     * 删除对象.
     */
    public int remove(String statement, BaseEntity o) {
        Assert.notNull(o);
        return sqlSession.delete(statement, o);
    }

    /**
     * 删除对象.
     */
    public int remove(BaseEntity o) {
        Assert.notNull(o);
        return sqlSession.delete(o.getClass().getSimpleName() + POSTFIX_DELETE, o);
    }

    /**
     * 根据主键删除对象.
     */
    public <T> int removeById(Class<T> entityClass, Serializable id) {
        Assert.notNull(id);
        return sqlSession.delete(entityClass.getSimpleName() + POSTFIX_DELETE_BYKEY, id);
    }

    /**
     * 查询jQuery Datatables分页列表（指定的sqlId=类名_list）
     * 
     * @param entityClass
     * @param require
     * @return
     */
    public <T> ListJsonResult<T> queryDTPage(Class<T> entityClass, Requirement require) {
        return this.queryDTPage(entityClass.getSimpleName() + POSTFIX_SELECT, require);
    }

    /**
     * 查询jQuery Datatables分页列表
     * 
     * @param statement
     * @param require
     * @return
     */
    public <T> ListJsonResult<T> queryDTPage(String statement, Requirement require) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("length", require.getLength());
        paramMap.put("start", require.getStart());
        paramMap.putAll(require.getMap());
        PageInfo<T> pageInfo = this.queryPaged(statement, paramMap);
        ListJsonResult<T> resList = new ListJsonResult<T>();
        if (pageInfo != null) {
            resList.setDraw(require.getDraw() == null ? 0 : require.getDraw());
            resList.setRecordsTotal(pageInfo.getTotal());
            resList.setRecordsFiltered(pageInfo.getTotal());
            List<T> list = pageInfo.getRows();
            resList.setData(list);
        }
        return resList;
    }

    /**
     * 查询jQuery Datatables分页列表（指定的sqlId=类名_list）
     * 
     * @param entityClass
     * @param paramMap
     * @return
     */
    public <T> ListJsonResult<T> queryDTList(Class<T> entityClass, Map<String, Object> paramMap) {
        return this.queryDTList(entityClass.getSimpleName() + POSTFIX_SELECT, paramMap);
    }

    /**
     * 查询jQuery Datatables列表
     * 
     * @param statement
     * @param paramMap
     * @return
     */
    public <T> ListJsonResult<T> queryDTList(String statement, Map<String, Object> paramMap) {
        String drawStr = String.valueOf(paramMap.get("draw"));
        int draw = StringUtils.isBlank(drawStr) ? 0 : Integer.parseInt(drawStr);

        List<T> list = query(statement, paramMap);
        ListJsonResult<T> resList = new ListJsonResult<T>();
        if (list != null) {
            resList.setDraw(draw);
            resList.setRecordsTotal(list.size());
            resList.setRecordsFiltered(list.size());
            resList.setData(list);
        }
        return resList;
    }

    /**
     * 查询列表（指定sqlId=类名_list）
     * 
     * @param parameterMap 包含各种属性的查询
     */
    public <T> List<T> query(Class<T> entityClass, Map parameterMap) {
        Assert.notNull(parameterMap);
        return this.sqlSession.selectList(entityClass.getSimpleName() + POSTFIX_SELECT, parameterMap);
    }

    /**
     * 查询List列表
     * 
     * @param statement sqlId
     * @param parameterMap 包含各种属性的查询
     * @return
     */
    public <T> List<T> query(String statement, Map parameterMap) {
        Assert.notNull(statement);
        Assert.notNull(parameterMap);
        return this.sqlSession.selectList(statement, parameterMap);
    }

    /**
     * 查询分页列表（指定的sqlId=类名_list）
     * 
     * @param entityClass
     * @param parameterMap
     * @return
     */
    public <T> PageInfo<T> queryPaged(Class<T> entityClass, Map<String, Object> parameterMap) {
        Assert.notNull(parameterMap);
        return this.queryPaged(entityClass.getSimpleName() + POSTFIX_SELECT, parameterMap);
    }

    /**
     * 查询分页列表
     * 
     * @param statement sqlId
     * @param parameterMap 包含各种属性的查询
     * @return
     */
    public <T> PageInfo<T> queryPaged(String statement, Map<String, Object> parameterMap) {
        Assert.notNull(statement);
        Assert.notNull(parameterMap);
        String rowsStr = parameterMap.get("length") == null ? null : String.valueOf(parameterMap.get("length"));
        String startStr = parameterMap.get("start") == null ? null : String.valueOf(parameterMap.get("start"));
        // 第几页
        int page = 1;
        // 每页显示数量
        int rows = 15;
        try {
            rows = rowsStr != null ? Integer.valueOf(rowsStr) : rows;
            page = startStr == null || "0".equals(startStr) ? page : (Integer.valueOf(startStr) + rows) / rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageHelper.startPage(page, rows);
        List<T> list = query(statement, parameterMap);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return pageInfo;
    }

    /**
     * 得到符合查询条件的记录数（指定的sqlId=类名_count）
     * 
     * @param entityClass
     * @param parameterMap
     * @return
     */
    public <T> Integer getCount(Class<T> entityClass, Map<String, Object> parameterMap) {
        Integer totalCount = (Integer) this.sqlSession.selectOne(entityClass.getSimpleName() + POSTFIX_COUNT,
                parameterMap);
        return totalCount;
    }

    /**
     * 查询唯一值（指定的sqlId=类名_list）
     * 
     * @param map 包含各种属性的查询
     */
    public <T> T queryUniquely(Class<T> entityClass, Map map) {
        Assert.notNull(map);
        return (T) sqlSession.selectOne(entityClass.getSimpleName() + POSTFIX_SELECT, map);
    }

    public <T> T queryUniquely(String statement, Object param) {
        Assert.notNull(statement);
        Assert.notNull(param);
        return (T) sqlSession.selectOne(statement, param);
    }

    public <T> T queryUniquely(String statement) {
        Assert.notNull(statement);
        return (T) sqlSession.selectOne(statement);
    }

    public <T> List<T> getList(String statement) {
        return sqlSession.selectList(statement);
    }

    public <T> List<T> getList(String statement, Object parameter) {
        return sqlSession.selectList(statement, parameter);
    }

    public <T> Map getMap(String statement, String mapKey) {
        return sqlSession.selectMap(statement, mapKey);
    }

    public <T> Map getMap(String statement, Object parameter, String mapKey) {
        return sqlSession.selectMap(statement, parameter, mapKey);
    }

    public <T> int delete(String statement) {
        return sqlSession.delete(statement);
    }

    public <T> int delete(String statement, Object parameter) {
        return sqlSession.delete(statement, parameter);
    }

    public <T> int update(String statement) {
        return sqlSession.update(statement);
    }

    public <T> int update(String statement, Object parameter) {
        return sqlSession.update(statement, parameter);
    }

    public <T> int insert(String statement) {
        return sqlSession.insert(statement);
    }

    public <T> int insert(String statement, Object parameter) {
        return sqlSession.insert(statement, parameter);
    }

    // CRUD-------------------------------------------------------------------------

    /**
     * 清空表，慎用，仅用于单元测试 truncate table
     * 
     * @param tableName
     */
    public Boolean truncate(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            return false;
        }
        Map<String, String> data = new HashMap<>();
        data.put("tableName", tableName);
        sqlSession.update("Common_truncate", data);
        return true;
    }
}
