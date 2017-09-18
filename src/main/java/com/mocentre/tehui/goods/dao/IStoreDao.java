package com.mocentre.tehui.goods.dao;

import java.util.List;
import java.util.Map;

import com.mocentre.tehui.goods.model.Store;

/**
 * 商品收藏dao接口 Created by yukaiji on 2016/12/07.
 */
public interface IStoreDao {

    /**
     * 查询商品收藏
     * 
     * @param id
     * @param customerId
     * @return
     */
    Store queryById(Long id, Long customerId);

    /**
     * 根据条件获取
     * 
     * @param paramMap 条件
     * @return
     */
    List<Store> queryList(Map<String, Object> paramMap);

    /**
     * 添加一个商品收藏
     * 
     * @param store
     * @return id
     */
    Long saveEntity(Store store);

    Integer getCount(Map<String, Object> paramMap);

    /**
     * 根据条件删除
     *
     * @param store
     * @return
     */
    int remove(Store store);

    /**
     * 查询收藏
     * 
     * @param paramMap
     * @return
     */
    Store getUnique(Map<String, Object> paramMap);
}
