package com.mocentre.tehui.shop.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.shop.model.Shop;

/**
 * 店铺信息数据库操作接口 Created by yukaiji on 2016/11/13.
 */
public interface IShopDao {

    ListJsonResult<Shop> queryDatatablesPage(Requirement require);

    /**
     * 获取所有店铺信息
     * 
     * @return
     */
    List<Shop> getAllShop();

    /**
     * 根据id获取店铺信息
     * 
     * @param id
     * @return
     */
    Shop get(Serializable id);

    /**
     * 根据店铺名称获取店铺信息
     * 
     * @param name 店铺名称
     * @return
     */
    List<Shop> getShopListInName(String name);

    /**
     * 根据条件查询店铺信息
     * 
     * @param paramMap
     * @return
     */
    List<Shop> queryList(Map<String, Object> paramMap);

    /**
     * 修改店铺信息对象
     * 
     * @param Shop
     * @return
     */
    Long updateEntity(Shop Shop);

    /**
     * 根据id删除店铺
     * 
     * @param id
     * @return
     */
    int removeById(Serializable id);

    /**
     * 添加一个店铺
     * 
     * @param Shop
     * @return
     */
    Long saveEntity(Shop Shop);

    /**
     * 审核一个店铺
     * 
     * @param Shop
     * @return
     */
    Long updateAS(Shop Shop);

    /**
     * 关闭/开启一个店铺
     * 
     * @param Shop
     * @return
     */
    Long updateBS(Shop Shop);

}
