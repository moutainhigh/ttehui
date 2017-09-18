package com.mocentre.tehui;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;
import mockit.NonStrictExpectations;
import mockit.integration.junit4.JMockit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.alibaba.fastjson.JSON;
import com.mocentre.util.ApiClient;
import com.mocentre.util.BaseResult;

/**
 * Created by Arvin on 16/10/31.
 */
@RunWith(JMockit.class)
public class CheckLoginServiceTest extends TestCase {

    //public CheckLoginService checkLoginService;

    @Test
    public void testLogin() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-bean.xml");
        //checkLoginService = (CheckLoginService) context.getBean("checkLoginService");
        HttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = new MockHttpSession();
        HttpServletResponse response = new MockHttpServletResponse();

        final ApiClient apiClient = new ApiClient();
        new NonStrictExpectations() {
            {
                HttpServletRequest request = new MockHttpServletRequest();
                result = "asdfasf";

                Map<String, String> hm = new HashMap<String, String>();
                hm.put("id", "1");
                hm.put("phone", "15268187707");
                BaseResult br = new BaseResult();
                br.setData(hm);
                String jsonStr = JSON.toJSONString(br);
                apiClient.sendData(anyString, anyInt);
                result = jsonStr;
            }
        };

        //        boolean res = checkLoginService.checkLogin(request, session, response);
        //        Assert.assertTrue(res);

    }
}
