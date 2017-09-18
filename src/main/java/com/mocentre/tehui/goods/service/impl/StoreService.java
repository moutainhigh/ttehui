package com.mocentre.tehui.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.goods.dao.IStoreDao;
import com.mocentre.tehui.goods.model.Store;
import com.mocentre.tehui.goods.service.IStoreService;

/**
 * 商品收藏接口实现 Created by yukaiji on 2016/12/07.
 */

@Component
public class StoreService implements IStoreService {

    @Autowired
    private IStoreDao storeDao;

    @Override
    @DataSource(value = "read")
    public Store getStore(Long id, Long customerId) {
        return storeDao.queryById(id, customerId);
    }

    @Override
    @DataSource(value = "read")
    public List<Store> getStoreList(Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        return storeDao.queryList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Store addStore(Store store) {
        storeDao.saveEntity(store);
        return store;
    }

    @Override
    @DataSource(value = "read")
    public Store getStoreByGoods(Long goodsId, Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("goodsId", goodsId);
        return storeDao.getUnique(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public int countStoreByGoods(Long goodsId, Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("goodsId", goodsId);
        Integer count = storeDao.getCount(paramMap);
        return count == null ? 0 : count;
    }

    @Override
    @DataSource(value = "write")
    public int deleteStore(Store store) {
        return storeDao.remove(store);
    }

}
