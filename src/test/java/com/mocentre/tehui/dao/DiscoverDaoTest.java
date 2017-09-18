/*
 *
 *  * Copyright 2016 mocentre.com All right reserved. This software is the
 *  * confidential and proprietary information of mocentre.com ("Confidential
 *  * Information"). You shall not disclose such Confidential Information and shall
 *  * use it only in accordance with the terms of the license agreement you entered
 *  * into with mocentre.com .
 *
 */

package com.mocentre.tehui.dao;

import com.mocentre.tehui.goods.dao.impl.DiscoverDao;
import com.mocentre.tehui.goods.model.Discover;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Arvin on 16/12/13.
 */
public class DiscoverDaoTest extends BaseDaoTest {

    private DiscoverDao discoverDao;

    private String tableName = "gd_t_discover";


    public DiscoverDaoTest(){
        super();
        discoverDao = (DiscoverDao)context.getBean("discoverDao");
    }

    private void _initData(){

    }

    private void _clearData(){
        discoverDao.truncate(this.tableName);
    }

    @Test
    public void testAdd(){
        this._initData();
        Discover discover = new Discover();
        discover.setShopId(1L);
        discover.setActName("test");
        discover.setIsGoods(0);
        discover.setIsShow(1);
        discover.setUrl("http://shop.mocentre.com");

        Long res = discoverDao.saveEntity(discover);
        Assert.assertNotEquals("success", (long) 0l, res.longValue());

        this._clearData();
    }
}
