package com.mocentre.tehui.shop.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.shop.dao.IShopDao;
import com.mocentre.tehui.shop.model.Shop;

/**
 * 店铺信息数据库操作实现类 Created by yukaiji on 2016/11/13.
 */
@Repository
public class ShopDao extends BaseDao<Shop> implements IShopDao {

    @Override
    public List<Shop> getAllShop() {
        return super.getList(entityClass.getSimpleName() + "_getAll");
    }

    @Override
    public List<Shop> getShopListInName(String name) {
        return super.getList(entityClass.getSimpleName() + "_getAllInName");
    }

    @Override
    public Long updateAS(Shop Shop) {
        super.update(entityClass.getSimpleName() + "_updateAS", Shop);
        return Shop.getId();
    }

    @Override
    public Long updateBS(Shop Shop) {
        super.update(entityClass.getSimpleName() + "_updateBS", Shop);
        return Shop.getId();
    }

}
