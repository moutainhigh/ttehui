package com.mocentre.tehui.goods.dao.impl;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.ISupplierDao;
import com.mocentre.tehui.goods.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 供货商表数据库操作接口实现. Created by yukaiji on 2017/8/30.
 */
@Repository
public class SupplierDao extends BaseDao<Supplier> implements ISupplierDao {

    @Override
    public List<Supplier> getList(Map<String, Object> paramMap) {
        return super.queryList(paramMap);
    }
}
