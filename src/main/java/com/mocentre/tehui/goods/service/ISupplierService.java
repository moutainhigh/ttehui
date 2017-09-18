package com.mocentre.tehui.goods.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.SupplierParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Supplier;

/**
 * 供货商接口. Created by yukaiji on 2017/8/30.
 */
public interface ISupplierService {

    /**
     * 获取供货商(分页查询)
     * 
     * @return
     */
    ListJsonResult<Supplier> querySupplierPage(Requirement require);

    /**
     * 根据id获取
     * 
     * @param id
     * @return
     */
    Supplier getSupplierById(Long id);


    /**
     * 添加
     * 
     * @param supplierParam
     * @return
     */
    Supplier addSupplier(SupplierParam supplierParam);

    /**
     * 修改
     * 
     * @param supplierParam
     * @return
     */
    Long updateSupplier(SupplierParam supplierParam);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int delSupplierById(Long id);

}
