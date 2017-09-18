package com.mocentre.tehui.dubbo;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

/**
 * Created by Arvin on 16/12/9.
 */
public class BaseDubboServiceTest{

    public ApplicationContext context;

    public BaseDubboServiceTest(){
        init();
    }

    public void init(){
        this.context = new ClassPathXmlApplicationContext("spring/demo-applicationContext-dubbo.xml");
    }

    public String generageRequestId(){
        return UUID.randomUUID().toString();
    }
}
