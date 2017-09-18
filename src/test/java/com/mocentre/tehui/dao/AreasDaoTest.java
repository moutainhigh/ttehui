/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.dao;

import org.junit.Assert;
import org.junit.Test;

import com.mocentre.tehui.system.dao.impl.AreasDao;

/**
 * 类AreasDaoTest.java的实现描述：区域Dao测试
 * 
 * @author sz.gong 2017年1月13日 下午4:42:22
 */
public class AreasDaoTest extends BaseDaoTest {

    private AreasDao areasDao;

    public AreasDaoTest() {
        super();
        areasDao = (AreasDao) context.getBean("areasDao");
    }

    @Test
    public void cleanAreas() {
        areasDao.getAllToCache();
        Assert.assertNotEquals(1, 1);
    }

}
