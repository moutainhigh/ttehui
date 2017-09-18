package com.mocentre.tehui.goods.service.impl;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.SupplierParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.ISupplierDao;
import com.mocentre.tehui.goods.mapper.SupplierMapper;
import com.mocentre.tehui.goods.model.Supplier;
import com.mocentre.tehui.goods.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 供货商接口实现 Created by yukaiji on 2017/8/30.
 */

@Component
public class SupplierService implements ISupplierService {

    @Autowired
    private ISupplierDao supplierDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Supplier> querySupplierPage(Requirement require) {
        return supplierDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public Supplier getSupplierById(Long id) {
        return supplierDao.get(id);
    }

    @Override
    @DataSource(value = "write")
    public Supplier addSupplier(SupplierParam supplierParam) {
        Supplier supplier = SupplierMapper.toSupplier(supplierParam);
        supplierDao.saveEntity(supplier);
        return supplier;
    }

    @Override
    @DataSource(value = "write")
    public Long updateSupplier(SupplierParam supplierParam) {
        Supplier supplier =  SupplierMapper.toSupplier(supplierParam);
        return supplierDao.updateEntity(supplier);
    }

    @Override
    @DataSource(value = "write")
    public int delSupplierById(Long id) {
        return supplierDao.logicRemoveById(id);
    }
}
