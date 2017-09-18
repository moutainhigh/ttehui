/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.dao;

import org.junit.Test;

import com.mocentre.tehui.goods.dao.impl.GoodsStorageDao;

/**
 * 类GoodsStorageDaoTest.java的实现描述：库存测试DAO
 * 
 * @author sz.gong 2017年1月16日 下午6:12:04
 */
public class GoodsStorageDaoTest extends BaseDaoTest {

    private GoodsStorageDao storageDao;

    public GoodsStorageDaoTest() {
        super();
        storageDao = (GoodsStorageDao) context.getBean("goodsStorageDao");
    }

    @Test
    public void cleanAreas() {
        storageDao.queryFromCache(1l, "zzz", 2l);
    }

}
