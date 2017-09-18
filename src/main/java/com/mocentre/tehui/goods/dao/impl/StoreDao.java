package com.mocentre.tehui.goods.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.IStoreDao;
import com.mocentre.tehui.goods.model.Store;

/**
 * 商品收藏dao接口 Created by yukaiji on 2016/12/07.
 */
@Repository
public class StoreDao extends BaseDao<Store> implements IStoreDao {

    @Override
    public Store queryById(Long id, Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("customerId", customerId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public Store getUnique(Map<String, Object> paramMap) {
        return super.queryUniquely(paramMap);
    }

    @Override
    public int remove(Store store) {
        return super.remove(store);
    }

}
