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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Arvin on 16/12/13.
 */
public class BaseDaoTest {

    public ApplicationContext context;

    public BaseDaoTest() {
        init();
    }

    public void init() {
        this.context = new ClassPathXmlApplicationContext("spring/demo-applicationContext-dao.xml");
    }
}
