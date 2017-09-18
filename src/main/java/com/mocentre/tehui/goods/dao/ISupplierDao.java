package com.mocentre.tehui.goods.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Supplier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 供货商表数据库操作接口. Created by yukaiji on 2017/8/30.
 */
public interface ISupplierDao {

    /**
     * 根据id查询
     * 
     * @param id
     * @return
     */
    Supplier get(Serializable id);

    /**
     * 获取供货商列表(分页查询)
     * 
     * @return 所有的活动
     */
    ListJsonResult<Supplier> queryDatatablesPage(Requirement require);

    /**
     * 根据条件获取供货商
     * 
     * @param paramMap
     * @return
     */
    List<Supplier> getList(Map<String, Object> paramMap);

    /**
     * 添加
     * 
     * @param Supplier
     */
    Long saveEntity(Supplier Supplier);

    /**
     * 修改
     * 
     * @param Supplier
     */
    Long updateEntity(Supplier Supplier);

    /**
     * 根据id删除
     * 
     * @param id
     */
    int logicRemoveById(Serializable id);

}
